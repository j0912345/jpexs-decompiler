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
package com.jpexs.decompiler.flash.abc.avm2.instructions.other.unknown;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2InstructionFlag;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;

/**
 * getpropertylate - undocumented opcode.
 *
 * @author JPEXS
 * <p>
 * source:
 * https://github.com/magicalhobo/SWFWire/blob/master/SWFWireDecompiler/src/com/swfwire/decompiler/abc/ABCInstructions.as
 * collides with getouterscope
 */
public class GetPropertyLateIns extends InstructionDefinition {

    /**
     * Constructor
     */
    public GetPropertyLateIns() {
        super(0x67, "getpropertylate", new int[]{}, true /*?*/, AVM2InstructionFlag.UNDOCUMENTED, AVM2InstructionFlag.UNKNOWN_STACK, AVM2InstructionFlag.NO_FLASH_PLAYER);
    }

    @Override
    public int getStackPopCount(AVM2Instruction ins, ABC abc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getStackPushCount(AVM2Instruction ins, ABC abc) {
        throw new UnsupportedOperationException();
    }
}
