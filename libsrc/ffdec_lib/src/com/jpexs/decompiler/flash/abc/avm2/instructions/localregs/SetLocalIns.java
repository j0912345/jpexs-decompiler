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
package com.jpexs.decompiler.flash.abc.avm2.instructions.localregs;

import com.jpexs.decompiler.flash.abc.avm2.AVM2Code;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;

/**
 * setlocal instruction - set local register value.
 *
 * @author JPEXS
 */
public class SetLocalIns extends SetLocalTypeIns {

    /**
     * Constructor
     */
    public SetLocalIns() {
        super(0x63, "setlocal", new int[]{AVM2Code.DAT_LOCAL_REG_INDEX}, false);
    }

    @Override
    public int getRegisterId(AVM2Instruction ins) {
        return ins.operands[0];
    }
}
