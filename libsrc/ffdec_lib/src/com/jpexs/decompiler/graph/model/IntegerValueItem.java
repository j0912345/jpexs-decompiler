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
package com.jpexs.decompiler.graph.model;

import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetDialect;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TypeItem;
import java.util.Set;

/**
 * Integer value.
 *
 * @author JPEXS
 */
public class IntegerValueItem extends GraphTargetItem implements IntegerValueTypeItem {

    /**
     * Integer value
     */
    private final int intValue;

    /**
     * Constructor.
     * 
     * @param dialect Dialect
     * @param src Source
     * @param lineStartIns Line start instruction
     * @param value Value
     */
    public IntegerValueItem(GraphTargetDialect dialect, GraphSourceItem src, GraphSourceItem lineStartIns, int value) {
        super(dialect, src, lineStartIns, PRECEDENCE_PRIMARY);
        this.intValue = value;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) {
        return writer.append(intValue);
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        return true;
    }

    @Override
    public Object getResult() {
        return (long) intValue;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.UNBOUNDED;
    }

    @Override
    public int intValue() {
        return intValue;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.intValue;
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
        final IntegerValueItem other = (IntegerValueItem) obj;
        if (this.intValue != other.intValue) {
            return false;
        }
        return true;
    }

}
