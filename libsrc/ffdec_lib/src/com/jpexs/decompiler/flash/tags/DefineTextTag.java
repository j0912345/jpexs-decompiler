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
import com.jpexs.decompiler.flash.tags.base.StaticTextTag;
import com.jpexs.decompiler.flash.types.MATRIX;
import com.jpexs.decompiler.flash.types.RECT;
import com.jpexs.decompiler.flash.types.TEXTRECORD;
import com.jpexs.decompiler.flash.types.annotations.SWFVersion;
import com.jpexs.helpers.ByteArrayRange;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DefineText tag - defines text.
 *
 * @author JPEXS
 */
@SWFVersion(from = 1)
public class DefineTextTag extends StaticTextTag {

    public static final int ID = 11;

    public static final String NAME = "DefineText";

    /**
     * Constructor
     *
     * @param swf SWF
     */
    public DefineTextTag(SWF swf) {
        super(swf, ID, NAME, null);
        characterID = swf.getNextCharacterId();
        textBounds = new RECT();
        textMatrix = new MATRIX();
        textRecords = new ArrayList<>();
        glyphBits = 0;
        advanceBits = 0;
    }

    public DefineTextTag(SWF swf, int characterID, RECT textBounds, MATRIX textMatrix, List<TEXTRECORD> textRecords) {
        super(swf, ID, NAME, null);
        this.characterID = characterID;
        this.textBounds = textBounds;
        this.textMatrix = textMatrix;
        this.textRecords = textRecords;
        this.glyphBits = 0;
        this.advanceBits = 0;
    }

    /**
     * Constructor
     *
     * @param sis SWF input stream
     * @param data Data
     * @throws IOException On I/O error
     */
    public DefineTextTag(SWFInputStream sis, ByteArrayRange data) throws IOException {
        super(sis.getSwf(), ID, NAME, data);
        readData(sis, data, 0, false, false, false);
    }           

    @Override
    public int getTextNum() {
        return 1;
    }

    @Override
    public RECT getRectWithStrokes() {
        return getRect();
    }
}
