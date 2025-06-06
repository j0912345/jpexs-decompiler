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
package com.jpexs.decompiler.flash.treeitems;

import com.jpexs.decompiler.flash.SWF;

/**
 * SWF header TreeItem
 *
 * @author JPEXS
 */
public class HeaderItem implements TreeItem {

    /**
     * SWF
     */
    private final SWF swf;

    /**
     * Name for toString
     */
    private final String name;

    public HeaderItem(SWF swf, String name) {
        this.swf = swf;
        this.name = name;
    }

    /**
     * Gets openable.
     *
     * @return Openable
     */
    @Override
    public Openable getOpenable() {
        return swf;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Gets modified flag.
     *
     * @return Modified flag
     */
    @Override
    public boolean isModified() {
        return swf.isHeaderModified();
    }
}
