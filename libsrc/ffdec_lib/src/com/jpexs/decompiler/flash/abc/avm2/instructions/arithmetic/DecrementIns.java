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
package com.jpexs.decompiler.flash.abc.avm2.instructions.arithmetic;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.AVM2LocalData;
import com.jpexs.decompiler.flash.abc.avm2.AVM2ConstantPool;
import com.jpexs.decompiler.flash.abc.avm2.LocalDataArea;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;
import com.jpexs.decompiler.flash.abc.avm2.model.DecrementAVM2Item;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TranslateStack;
import java.util.List;

/**
 * decrement instruction - Decrement a number.
 *
 * @author JPEXS
 */
public class DecrementIns extends InstructionDefinition {

    /**
     * Constructor
     */
    public DecrementIns() {
        super(0x93, "decrement", new int[]{}, true);
    }

    @Override
    public boolean execute(LocalDataArea lda, AVM2ConstantPool constants, AVM2Instruction ins) {
        Object obj = lda.operandStack.pop();
        lda.operandStack.push(EcmaScript.toNumber(obj) - 1);
        return true;
    }

    @Override
    public void translate(AVM2LocalData localData, TranslateStack stack, AVM2Instruction ins, List<GraphTargetItem> output, String path) {
        stack.push(new DecrementAVM2Item(ins, localData.lineStartInstruction, stack.pop()));
    }

    @Override
    public int getStackPopCount(AVM2Instruction ins, ABC abc) {
        return 1;
    }

    @Override
    public int getStackPushCount(AVM2Instruction ins, ABC abc) {
        return 1;
    }
}
