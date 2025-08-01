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

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.SWFInputStream;
import com.jpexs.decompiler.flash.SWFOutputStream;
import com.jpexs.helpers.ByteArrayRange;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Non-resolved tag stub. Used for (non-yet)unknown tags.
 *
 * @author JPEXS
 */
public class TagStub extends Tag {

    private final SWFInputStream dataStream;

    /**
     * Constructor
     *
     * @param swf SWF
     * @param id ID
     * @param data Data
     * @param name Name
     * @param sis SWF input stream
     */
    public TagStub(SWF swf, int id, String name, ByteArrayRange data, SWFInputStream sis) {
        super(swf, id, name, data);
        dataStream = sis;
    }

    @Override
    public final void readData(SWFInputStream sis, ByteArrayRange data, int level, boolean parallel, boolean skipUnusualTags, boolean lazy) throws IOException {
    }

    /**
     * Gets data bytes
     *
     * @param sos SWF output stream
     */
    @Override
    public void getData(SWFOutputStream sos) {
        try {
            sos.write(getOriginalData());
        } catch (IOException ex) {
            Logger.getLogger(TagStub.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SWFInputStream getDataStream() {
        return dataStream;
    }

    /*@Override
    public String toString() {
        return tagName + " (tid = " + id + ")";
    }*/

    @Override
    public Map<String, String> getNameProperties() {
        Map<String, String> ret = super.getNameProperties();
        Map<Integer, TagTypeInfo> classes = Tag.getKnownClasses();
        if (classes.containsKey(id)) {
            ret.put("tcl", classes.get(id).getName());
        }
        ret.put("tid", "" + id);
        return ret;
    }
}
