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
package com.jpexs.decompiler.flash.configuration.enums;

/**
 * Guides snap accuracy.
 * @author JPEXS
 */
public enum GuidesSnapAccuracy {
    MUST_BE_CLOSE(5),
    NORMAL(10),
    CAN_BE_DISTANT(15);

    private final int distance;

    private GuidesSnapAccuracy(int value) {
        this.distance = value;
    }

    public int getDistance() {
        return distance;
    }
}
