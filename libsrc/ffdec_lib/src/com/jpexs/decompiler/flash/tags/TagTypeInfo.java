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
package com.jpexs.decompiler.flash.tags;

/**
 * Tag type info.
 *
 * @author JPEXS
 */
public class TagTypeInfo {

    private final int id;

    private final Class cls;

    private final String name;

    public TagTypeInfo(int id, Class cls, String name) {
        this.id = id;
        this.cls = cls;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Class getCls() {
        return cls;
    }

    public String getName() {
        return name;
    }
}
