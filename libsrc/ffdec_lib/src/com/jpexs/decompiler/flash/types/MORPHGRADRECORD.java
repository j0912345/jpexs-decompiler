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
package com.jpexs.decompiler.flash.types;

import com.jpexs.decompiler.flash.types.annotations.SWFType;
import java.io.Serializable;

/**
 * Morph gradient record.
 *
 * @author JPEXS
 */
public class MORPHGRADRECORD implements Serializable {

    /**
     * Start ratio
     */
    @SWFType(BasicType.UI8)
    public int startRatio;

    /**
     * Start color
     */
    public RGBA startColor;

    /**
     * End ratio
     */
    @SWFType(BasicType.UI8)
    public int endRatio;

    /**
     * End color
     */
    public RGBA endColor;

    /**
     * Gets start record.
     * @return Start record
     */
    public GRADRECORD getStartRecord() {
        GRADRECORD ret = new GRADRECORD();
        ret.ratio = startRatio;
        ret.color = startColor;
        return ret;
    }

    /**
     * Gets end record.
     * @return End record
     */
    public GRADRECORD getEndRecord() {
        GRADRECORD ret = new GRADRECORD();
        ret.ratio = endRatio;
        ret.color = endColor;
        return ret;
    }
}
