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
package com.jpexs.decompiler.flash.action.model;

import com.jpexs.decompiler.graph.GraphTargetItem;

/**
 * Set type action item interface.
 *
 * @author JPEXS
 */
public interface SetTypeActionItem {

    public GraphTargetItem getObject();

    public GraphTargetItem getValue();

    public void setTempRegister(int regIndex);

    public int getTempRegister();

    public void setValue(GraphTargetItem value);

    public GraphTargetItem getCompoundValue();

    public void setCompoundValue(GraphTargetItem value);

    public void setCompoundOperator(String operator);

    public String getCompoundOperator();
}
