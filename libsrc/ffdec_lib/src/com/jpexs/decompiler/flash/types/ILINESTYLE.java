/*
 *  Copyright (C) 2010-2024 JPEXS, All rights reserved.
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

import com.jpexs.decompiler.flash.tags.base.NeedsCharacters;
import com.jpexs.helpers.ConcreteClasses;
import java.io.Serializable;

/**
 * Interface for LINESTYLE and LINESTYLE2.
 *
 * @author JPEXS
 */
@ConcreteClasses({LINESTYLE.class, LINESTYLE2.class})
public interface ILINESTYLE extends NeedsCharacters, Serializable {

    /**
     * Gets version of LINESTYLE.
     *
     * @return
     */
    public int getNum();

    /**
     * Gets color.
     *
     * @return
     */
    public RGB getColor();

    /**
     * Gets width.
     *
     * @return
     */
    public int getWidth();

    /**
     * Sets color.
     *
     * @param color
     */
    public void setColor(RGB color);

    /**
     * Sets width.
     *
     * @param width
     */
    public void setWidth(int width);
}
