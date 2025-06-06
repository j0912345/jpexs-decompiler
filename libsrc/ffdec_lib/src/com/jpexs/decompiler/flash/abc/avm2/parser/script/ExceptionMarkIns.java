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

import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;

/**
 * Exception mark instruction.
 *
 * @author JPEXS
 */
public class ExceptionMarkIns extends InstructionDefinition {

    private static final ExceptionMarkIns instance = new ExceptionMarkIns();

    /**
     * Gets the instance of this class.
     * @return The instance of this class
     */
    public static final ExceptionMarkIns getInstance() {
        return instance;
    }

    private ExceptionMarkIns() {
        super(0, "--mark", new int[0], false /*?*/);
    }
}
