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
package com.jpexs.decompiler.graph.model;

import java.util.List;

/**
 * Callable interface.
 *
 * @author JPEXS
 */
public interface Callable {

    /**
     * Call a method with a name and arguments.
     * @param methodName Method name
     * @param args Arguments
     * @return Result of the method call
     */
    public Object call(String methodName, List<Object> args);

    /**
     * Call with arguments.
     * @param args Arguments
     * @return Result of the call
     */
    public Object call(List<Object> args);
}
