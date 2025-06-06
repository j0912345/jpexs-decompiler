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
package com.jpexs.decompiler.flash.types.sound;

import com.jpexs.decompiler.flash.SWFInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Sound decoder that does nothing, just copies the data.
 *
 * @author JPEXS
 */
public class NoDecoder extends SoundDecoder {

    public NoDecoder(SoundFormat soundFormat) {
        super(soundFormat);
    }

    @Override
    public void decode(SWFInputStream sis, OutputStream os) throws IOException {
        os.write(sis.readBytesEx(sis.available(), "soundStream"));
    }
}
