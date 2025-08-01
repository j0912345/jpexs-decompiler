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
package com.jpexs.decompiler.flash.action;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.action.model.ConstantPool;
import com.jpexs.decompiler.graph.TranslateStack;
import java.util.Set;

/**
 * Store type action interface.
 *
 * @author JPEXS
 */
public interface StoreTypeAction {

    /**
     * Gets variable name.
     *
     * @param usedDeobfuscations Used deobufscations
     * @param stack Stack
     * @param cpool Constant pool
     * @param swf SWF
     * @return Variable name
     */
    public String getVariableName(Set<String> usedDeobfuscations, TranslateStack stack, ConstantPool cpool, SWF swf);
}
