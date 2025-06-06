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
package com.jpexs.decompiler.flash.dumpview;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.treeitems.Openable;

/**
 * Dump info for SWF node.
 *
 * @author JPEXS
 */
public class DumpInfoSwfNode extends DumpInfo {

    private final Openable openable;

    public DumpInfoSwfNode(Openable openable, String name, String type, Object value, long startByte, long lengthBytes) {
        super(name, type, value, startByte, lengthBytes);
        this.openable = openable;
    }

    @Override
    public Openable getOpenable() {
        return openable;
    }

    public SWF getSwf() {
        return (SWF) openable;
    }

    public static DumpInfoSwfNode getSwfNode(DumpInfo dumpInfo) {
        while (!(dumpInfo instanceof DumpInfoSwfNode)) {
            dumpInfo = dumpInfo.parent;
        }
        return (DumpInfoSwfNode) dumpInfo;
    }
}
