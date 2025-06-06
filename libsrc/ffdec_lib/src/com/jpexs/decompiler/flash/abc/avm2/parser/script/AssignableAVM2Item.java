/*
 *  Copyright (C) 2010-2025 JPEXS, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.jpexs.decompiler.flash.abc.avm2.parser.script;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.abc.avm2.model.AVM2Item;
import com.jpexs.decompiler.flash.abc.avm2.model.CoerceAVM2Item;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.OrItem;
import com.jpexs.helpers.Reference;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignable.
 *
 * @author JPEXS
 */
public abstract class AssignableAVM2Item extends AVM2Item {

    /**
     * Assigned value
     */
    protected GraphTargetItem assignedValue;

    /**
     * Makes coerced.
     * @param assignedValue Assigned value
     * @param targetType Target type
     * @return Coerced value
     */
    protected GraphTargetItem makeCoerced(GraphTargetItem assignedValue, GraphTargetItem targetType) {
        if (assignedValue instanceof OrItem) {
            OrItem oi = (OrItem) assignedValue;
            return new OrItem(dialect, assignedValue.getSrc(), assignedValue.getLineStartItem(), makeCoerced(oi.leftSide, targetType), makeCoerced(oi.rightSide, targetType));
        }
        //TODO: Is it needed for AndItem too?
        return new CoerceAVM2Item(null, null, assignedValue, targetType);
    }

    /**
     * Constructor.
     */
    public AssignableAVM2Item() {
        this(null);
    }

    /**
     * Copis assignable.
     * @return Copied assignable
     */
    public abstract AssignableAVM2Item copy();

    /**
     * Constructor.
     * @param storeValue Store value
     */
    public AssignableAVM2Item(GraphTargetItem storeValue) {
        super(null, null, PRECEDENCE_PRIMARY);
        this.assignedValue = storeValue;
    }

    /**
     * To source with change (post/pre increment, decrement).
     * @param localData Local data
     * @param generator Generator
     * @param post Post
     * @param decrement Decrement
     * @param needsReturn Needs return
     * @return Source change
     * @throws CompilationException On compilation error
     */
    public abstract List<GraphSourceItem> toSourceChange(SourceGeneratorLocalData localData, SourceGenerator generator, boolean post, boolean decrement, boolean needsReturn) throws CompilationException;

    /**
     * Gets assigned value.
     * @return Assigned value
     */
    public GraphTargetItem getAssignedValue() {
        return assignedValue;
    }

    /**
     * Sets assigned value.
     * @param storeValue Store value
     */
    public void setAssignedValue(GraphTargetItem storeValue) {
        this.assignedValue = storeValue;
    }

    /**
     * Duplicated and set temp register.
     * @param localData Local data
     * @param generator Generator
     * @param register Register
     * @return Source
     */
    public static List<GraphSourceItem> dupSetTemp(SourceGeneratorLocalData localData, SourceGenerator generator, Reference<Integer> register) {
        register.setVal(getFreeRegister(localData, generator));
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(ins(AVM2Instructions.Dup));
        ret.add(generateSetLoc(register.getVal()));
        return ret;
    }

    /**
     * Sets temp register.
     * @param localData Local data
     * @param generator Generator
     * @param register Register
     * @return Source
     */
    public static List<GraphSourceItem> setTemp(SourceGeneratorLocalData localData, SourceGenerator generator, Reference<Integer> register) {
        register.setVal(getFreeRegister(localData, generator));
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(generateSetLoc(register.getVal()));
        return ret;
    }

    /**
     * Gets temp register.
     * @param localData Local data
     * @param generator Generator
     * @param register Register
     * @return Source
     */
    public static List<GraphSourceItem> getTemp(SourceGeneratorLocalData localData, SourceGenerator generator, Reference<Integer> register) {
        if (register.getVal() < 0) {
            return new ArrayList<>();
        }
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(generateGetLoc(register.getVal()));
        return ret;
    }

    /*protected List<GraphSourceItem> getAndKillTemp(SourceGeneratorLocalData localData, SourceGenerator generator, Reference<Integer> register) {
     killRegister(localData, generator, register.getVal());
     List<GraphSourceItem> ret = new ArrayList<>();
     ret.add(generateGetLoc(register.getVal()));
     ret.add(ins(AVM2Instructions.Kill, register.getVal()));
     return ret;
     }*/

    /**
     * Kills temp register.
     * @param localData Local data
     * @param generator Generator
     * @param registers Registers
     * @return Source
     */
    public static List<GraphSourceItem> killTemp(SourceGeneratorLocalData localData, SourceGenerator generator, List<Reference<Integer>> registers) {
        List<GraphSourceItem> ret = new ArrayList<>();
        for (Reference<Integer> register : registers) {
            if (register.getVal() < 0) {
                continue;
            }
            killRegister(localData, generator, register.getVal());

            ret.add(ins(AVM2Instructions.Kill, register.getVal()));
        }
        return ret;
    }

    /**
     * Generate set local.
     * @param regNumber Register number
     * @return Instruction
     */
    public static AVM2Instruction generateSetLoc(int regNumber) {
        switch (regNumber) {
            case -1:
                return null;
            case 0:
                return ins(AVM2Instructions.SetLocal0);
            case 1:
                return ins(AVM2Instructions.SetLocal1);
            case 2:
                return ins(AVM2Instructions.SetLocal2);
            case 3:
                return ins(AVM2Instructions.SetLocal3);
            default:
                return ins(AVM2Instructions.SetLocal, regNumber);
        }
    }

    /**
     * Generate get local.
     * @param regNumber Register number
     * @return Instruction
     */
    public static AVM2Instruction generateGetLoc(int regNumber) {
        switch (regNumber) {
            case -1:
                return null;
            case 0:
                return ins(AVM2Instructions.GetLocal0);
            case 1:
                return ins(AVM2Instructions.GetLocal1);
            case 2:
                return ins(AVM2Instructions.GetLocal2);
            case 3:
                return ins(AVM2Instructions.GetLocal3);
            default:
                return ins(AVM2Instructions.GetLocal, regNumber);
        }
    }

    /**
     * Generate get slot.
     * @param slotScope Slot scope
     * @param slotNumber Slot number
     * @return Source
     */
    public static List<GraphSourceItem> generateGetSlot(int slotScope, int slotNumber) {
        if (slotNumber == -1) {
            return null;
        }
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(ins(AVM2Instructions.GetScopeObject, slotScope));
        ret.add(ins(AVM2Instructions.GetSlot, slotNumber));
        return ret;
    }

    /**
     * Generate set slot.
     * @param localData Local data
     * @param generator Generator
     * @param val Value
     * @param slotScope Slot scope
     * @param slotNumber Slot number
     * @return Source
     * @throws CompilationException On compilation error
     */
    public static List<GraphSourceItem> generateSetSlot(SourceGeneratorLocalData localData, SourceGenerator generator, GraphTargetItem val, int slotScope, int slotNumber) throws CompilationException {
        if (slotNumber == -1) {
            return null;
        }
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(ins(AVM2Instructions.GetScopeObject, slotScope));
        ret.addAll(val.toSource(localData, generator));
        ret.add(ins(AVM2Instructions.SetSlot, slotNumber));
        return ret;
    }
}
