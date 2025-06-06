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
package com.jpexs.decompiler.flash.helpers;

/**
 * Provides methods for highlighting positions of instructions in the text.
 *
 * @author JPEXS
 */
public class LoopWithType {

    /**
     * Loop type - loop
     */
    public static int LOOP_TYPE_LOOP = 0;

    /**
     * Loop type - switch
     */
    public static int LOOP_TYPE_SWITCH = 1;

    /**
     * Loop id
     */
    public long loopId;

    /**
     * Loop type
     */
    public int type;

    /**
     * Used flag
     */
    public boolean used;
}
