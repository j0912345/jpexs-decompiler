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
package com.jpexs.decompiler.flash.abc.avm2.model;

import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.Objects;
import java.util.Set;

/**
 * Increment by 1.
 *
 * @author JPEXS
 */
public class IncrementAVM2Item extends AVM2Item {

    /**
     * Constructor.
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param object Object
     */
    public IncrementAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem object) {
        super(instruction, lineStartIns, PRECEDENCE_ADDITIVE, object);
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (value.getPrecedence() > precedence) {
            writer.append("(");
            value.toString(writer, localData);
            writer.append(")");
        } else {
            value.toString(writer, localData);
        }
        return writer.append(" + 1");
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        if (dependencies.contains(value)) {
            return false;
        }
        dependencies.add(value);
        return value.isCompileTime(dependencies);
    }

    @Override
    public Object getResult() {
        return value.getResultAsNumber() + 1;
    }

    @Override
    public GraphTargetItem returnType() {
        if (value.returnType().equals(TypeItem.INT)) {
            return TypeItem.INT;
        }
        if (value.returnType().equals(TypeItem.UINT)) {
            return TypeItem.UINT;
        }

        if (value.returnType().equals(TypeItem.NUMBER)) {
            return TypeItem.NUMBER;
        }

        return TypeItem.NUMBER;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
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
        final GraphTargetItem other = (GraphTargetItem) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
