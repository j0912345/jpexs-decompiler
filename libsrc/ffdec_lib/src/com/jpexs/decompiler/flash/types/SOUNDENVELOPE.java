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
 * Sound envelope.
 *
 * @author JPEXS
 */
public class SOUNDENVELOPE implements Serializable {

    /**
     * Position in 44 kHz samples
     */
    @SWFType(BasicType.UI32)
    public long pos44;

    /**
     * Left level
     */
    @SWFType(BasicType.UI16)
    public int leftLevel;

    /**
     * Right level
     */
    @SWFType(BasicType.UI16)
    public int rightLevel;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.pos44 ^ (this.pos44 >>> 32));
        hash = 29 * hash + this.leftLevel;
        hash = 29 * hash + this.rightLevel;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SOUNDENVELOPE other = (SOUNDENVELOPE) obj;
        if (this.pos44 != other.pos44) {
            return false;
        }
        if (this.leftLevel != other.leftLevel) {
            return false;
        }
        if (this.rightLevel != other.rightLevel) {
            return false;
        }
        return true;
    }

}
