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

import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;

/**
 * getlocal2 instruction - get local register 2 value.
 *
 * @author JPEXS
 */
public class GetLocal2Ins extends GetLocalTypeIns {

    /**
     * Constructor
     */
    public GetLocal2Ins() {
        super(0xd2, "getlocal2", new int[]{}, false);
    }

    @Override
    public int getRegisterId(AVM2Instruction par0) {
        return 2;
    }
}
