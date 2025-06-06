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
package com.jpexs.decompiler.flash.abc.avm2.instructions.jumps;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.AVM2LocalData;
import com.jpexs.decompiler.flash.abc.avm2.AVM2Code;
import com.jpexs.decompiler.flash.abc.avm2.AVM2ConstantPool;
import com.jpexs.decompiler.flash.abc.avm2.LocalDataArea;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.IfTypeIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;
import com.jpexs.decompiler.flash.abc.avm2.model.operations.GtAVM2Item;
import com.jpexs.decompiler.flash.abc.avm2.model.operations.LeAVM2Item;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TranslateStack;
import java.util.HashMap;
import java.util.List;

/**
 * ifngt instruction - if value is not greater, jump.
 *
 * @author JPEXS
 */
public class IfNGtIns extends InstructionDefinition implements IfTypeIns {

    /**
     * Constructor
     */
    public IfNGtIns() {
        super(0x0e, "ifngt", new int[]{AVM2Code.DAT_OFFSET}, true);
    }

    @Override
    public boolean execute(LocalDataArea lda, AVM2ConstantPool constants, AVM2Instruction ins) {
        Object rightObj = lda.operandStack.pop();
        Object leftObj = lda.operandStack.pop();

        Object cmp = EcmaScript.compare(leftObj, rightObj);
        if (cmp == Undefined.INSTANCE || ((int) cmp) != 1) {
            lda.jump = ins.getParamAsLong(constants, 0);
        }

        return true;
    }

    @Override
    public void translate(AVM2LocalData localData, TranslateStack stack, AVM2Instruction ins, List<GraphTargetItem> output, String path) {
        GraphTargetItem v2 = stack.pop();
        GraphTargetItem v1 = stack.pop();
        stack.push(new LeAVM2Item(ins, localData.lineStartInstruction, v1, v2));
    }

    @Override
    public void translateInverted(AVM2LocalData localData, HashMap<Integer, GraphTargetItem> localRegs, TranslateStack stack, AVM2Instruction ins) {
        GraphTargetItem v2 = stack.pop();
        GraphTargetItem v1 = stack.pop();
        stack.push(new GtAVM2Item(ins, localData.lineStartInstruction, v1, v2));
    }

    @Override
    public int getStackPopCount(AVM2Instruction ins, ABC abc) {
        return 2;
    }
}
