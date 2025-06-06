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
package com.jpexs.decompiler.flash.abc.avm2.instructions.other;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.AVM2LocalData;
import com.jpexs.decompiler.flash.abc.avm2.AVM2Code;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;
import com.jpexs.decompiler.flash.abc.avm2.model.HasNextAVM2Item;
import com.jpexs.decompiler.flash.abc.avm2.model.LocalRegAVM2Item;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TranslateStack;
import com.jpexs.decompiler.graph.TypeItem;
import java.util.List;

/**
 * hasnext2 instruction - determine if the given object has any more properties.
 *
 * @author JPEXS
 */
public class HasNext2Ins extends InstructionDefinition {

    /**
     * Constructor
     */
    public HasNext2Ins() {
        super(0x32, "hasnext2", new int[]{AVM2Code.DAT_LOCAL_REG_INDEX, AVM2Code.DAT_LOCAL_REG_INDEX}, true);
    }

    @Override
    public void translate(AVM2LocalData localData, TranslateStack stack, AVM2Instruction ins, List<GraphTargetItem> output, String path) {
        int objectReg = ins.operands[0];
        int indexReg = ins.operands[1];
        //stack.push("_loc_" + objectReg + ".hasNext(cnt=_loc_" + indexReg + ")");
        stack.push(new HasNextAVM2Item(ins, localData.lineStartInstruction,
                new LocalRegAVM2Item(ins, localData.lineStartInstruction, indexReg, localData.localRegs.get(indexReg), TypeItem.UNBOUNDED /*?*/),
                new LocalRegAVM2Item(ins, localData.lineStartInstruction, objectReg, localData.localRegs.get(objectReg), TypeItem.UNBOUNDED /*?*/)
        ));
    }

    @Override
    public int getStackPushCount(AVM2Instruction ins, ABC abc) {
        return 1;
    }
}
