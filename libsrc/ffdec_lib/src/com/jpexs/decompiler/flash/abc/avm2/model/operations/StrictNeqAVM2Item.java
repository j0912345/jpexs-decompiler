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
package com.jpexs.decompiler.flash.abc.avm2.model.operations;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.abc.avm2.graph.AVM2GraphTargetDialect;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.NotEqualsTypeItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.BinaryOpItem;
import com.jpexs.decompiler.graph.model.LogicalOpItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Strict not equal.
 *
 * @author JPEXS
 */
public class StrictNeqAVM2Item extends BinaryOpItem implements LogicalOpItem, IfCondition, NotEqualsTypeItem {

    /**
     * Constructor.
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param leftSide Left side
     * @param rightSide Right side
     */
    public StrictNeqAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem leftSide, GraphTargetItem rightSide) {
        super(AVM2GraphTargetDialect.INSTANCE, instruction, lineStartIns, PRECEDENCE_EQUALITY, leftSide, rightSide, "!==", "", "");
    }

    @Override
    public int getIfDefinition() {
        return AVM2Instructions.IfStrictNe;
    }

    @Override
    public int getIfNotDefinition() {
        return AVM2Instructions.IfStrictEq;
    }

    @Override
    public GraphTargetItem invert(GraphSourceItem neqSrc) {
        return new StrictEqAVM2Item(getSrc(), getLineStartItem(), leftSide, rightSide);
    }

    @Override
    public Object getResult() {
        Object x = leftSide.getResult();
        Object y = rightSide.getResult();
        return EcmaScript.type(x) != EcmaScript.type(y)
                || (!EcmaScript.equals(x, y));
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, leftSide, rightSide,
                new AVM2Instruction(0, AVM2Instructions.StrictEquals, null),
                new AVM2Instruction(0, AVM2Instructions.Not, null)
        );
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.BOOLEAN;
    }

    @Override
    public List<GraphSourceItem> getOperatorInstruction() {
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(new AVM2Instruction(0, AVM2Instructions.StrictEquals, null));
        ret.add(new AVM2Instruction(0, AVM2Instructions.Not, null));
        return ret;
    }
}
