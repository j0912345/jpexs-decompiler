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
import com.jpexs.decompiler.flash.tags.base.ShapeTag;
import com.jpexs.decompiler.flash.types.RECT;
import com.jpexs.decompiler.flash.types.SHAPEWITHSTYLE;
import com.jpexs.decompiler.flash.types.annotations.SWFVersion;
import com.jpexs.helpers.ByteArrayRange;
import java.io.IOException;

/**
 * DefineShape tag - defines shape.
 *
 * @author JPEXS
 */
@SWFVersion(from = 1)
public class DefineShapeTag extends ShapeTag {

    public static final int ID = 2;

    public static final String NAME = "DefineShape";

    /**
     * Constructor
     *
     * @param swf SWF
     */
    public DefineShapeTag(SWF swf) {
        super(swf, ID, NAME, null);
        shapeId = swf.getNextCharacterId();
        shapeBounds = new RECT();
        shapes = SHAPEWITHSTYLE.createEmpty(1);
    }

    public DefineShapeTag(SWFInputStream sis, ByteArrayRange data, boolean lazy) throws IOException {
        super(sis.getSwf(), ID, NAME, data);
        readData(sis, data, 0, false, false, lazy);
    }

    @Override
    public final void readData(SWFInputStream sis, ByteArrayRange data, int level, boolean parallel, boolean skipUnusualTags, boolean lazy) throws IOException {
        shapeId = sis.readUI16("shapeId");
        shapeBounds = sis.readRECT("shapeBounds");
        if (!lazy) {
            shapes = sis.readSHAPEWITHSTYLE(getShapeNum(), false, "shapes");
        } else {
            shapeData = new ByteArrayRange(data.getArray(), (int) sis.getPos(), sis.available());
            sis.skipBytes(sis.available());
        }
    }

    /**
     * Gets data bytes
     *
     * @param sos SWF output stream
     * @throws IOException On I/O error
     */
    @Override
    public void getData(SWFOutputStream sos) throws IOException {
        sos.writeUI16(shapeId);
        sos.writeRECT(shapeBounds);
        sos.writeSHAPEWITHSTYLE(getShapes(), getShapeNum());
    }

    @Override
    public int getShapeNum() {
        return 1;
    }

    @Override
    public int getWindingRule() {
        return WIND_EVEN_ODD;
    }
}
