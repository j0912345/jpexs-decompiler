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
package com.jpexs.decompiler.flash.abc.avm2.instructions.executing;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.AVM2LocalData;
import com.jpexs.decompiler.flash.abc.avm2.AVM2ConstantPool;
import com.jpexs.decompiler.flash.abc.avm2.LocalDataArea;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.other.GetPropertyIns;
import com.jpexs.decompiler.flash.abc.avm2.model.CallPropertyAVM2Item;
import com.jpexs.decompiler.flash.abc.avm2.model.FullMultinameAVM2Item;
import com.jpexs.decompiler.flash.ecma.NotCompileTime;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TranslateStack;
import com.jpexs.helpers.Reference;
import java.util.ArrayList;
import java.util.List;

/**
 * callproplex instruction - Call a property, lexically.
 *
 * @author JPEXS
 */
public class CallPropLexIns extends CallPropertyIns {

    /**
     * Constructor
     */
    public CallPropLexIns() {
        instructionName = "callproplex";
        instructionCode = 0x4c;
    }

    @Override
    public boolean isNotCompileTimeSupported() {
        return true;
    }

    @Override
    public boolean execute(LocalDataArea lda, AVM2ConstantPool constants, AVM2Instruction ins) {
        int multinameIndex = ins.operands[0];
        int argCount = ins.getParamAsLong(constants, 1).intValue();
        /*List<Object> passArguments = new ArrayList<Object>();
         for (int i = argCount - 1; i >= 0; i--) {
         passArguments.set(i, lda.operandStack.pop());
         }*/
        for (int i = 0; i < argCount; i++) {
            lda.operandStack.pop();
        }

        //if multiname[multinameIndex] is runtime
        //pop(name) pop(ns)
        resolveMultiname(lda, constants, multinameIndex);
        Object obj = lda.operandStack.pop();

        lda.operandStack.push(NotCompileTime.INSTANCE);
        //lda.executionException = "Call to unknown property";
        return true;
    }

    @Override
    public void translate(AVM2LocalData localData, TranslateStack stack, AVM2Instruction ins, List<GraphTargetItem> output, String path) {
        int multinameIndex = ins.operands[0];
        int argCount = ins.operands[1];
        List<GraphTargetItem> args = new ArrayList<>();
        for (int a = 0; a < argCount; a++) {
            args.add(0, stack.pop());
        }

        FullMultinameAVM2Item multiname = resolveMultiname(localData, true, stack, localData.getConstants(), multinameIndex, ins);
        GraphTargetItem obj = stack.pop();

        Reference<Boolean> isStatic = new Reference<>(false);
        Reference<GraphTargetItem> type = new Reference<>(null);
        Reference<GraphTargetItem> callType = new Reference<>(null);
        GetPropertyIns.resolvePropertyType(localData, obj, multiname, isStatic, type, callType);

        stack.push(new CallPropertyAVM2Item(ins, localData.lineStartInstruction, false, obj, multiname, args, callType.getVal(), isStatic.getVal()));
    }

    @Override
    public int getStackPopCount(AVM2Instruction ins, ABC abc) {
        int multinameIndex = ins.operands[0];
        return ins.operands[1] + 1 + getMultinameRequiredStackSize(abc.constants, multinameIndex);
    }

    @Override
    public int getStackPushCount(AVM2Instruction ins, ABC abc) {
        return 1;
    }
}
