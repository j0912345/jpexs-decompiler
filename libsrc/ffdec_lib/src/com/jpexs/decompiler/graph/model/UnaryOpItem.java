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
import com.jpexs.decompiler.graph.GraphSourceItemPos;
import com.jpexs.decompiler.graph.GraphTargetDialect;
import com.jpexs.decompiler.graph.GraphTargetItem;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Unary operator.
 *
 * @author JPEXS
 */
public abstract class UnaryOpItem extends GraphTargetItem implements UnaryOp {

    /**
     * Operator
     */
    public String operator;

    /**
     * Coerce value to this type
     */
    protected String coerce;

    /**
     * Constructor.
     *
     * @param dialect Dialect
     * @param instruction Instruction
     * @param lineStartItem Line start item
     * @param precedence Precedence
     * @param value Value
     * @param operator Operator
     * @param coerce Coerce
     */
    public UnaryOpItem(GraphTargetDialect dialect, GraphSourceItem instruction, GraphSourceItem lineStartItem, int precedence, GraphTargetItem value, String operator, String coerce) {
        super(dialect, instruction, lineStartItem, precedence, value);
        this.operator = operator;
        this.coerce = coerce;
    }

    @Override
    public GraphTargetItem simplify(String implicitCoerce) {
        GraphTargetItem r = clone();
        r.value = r.value.simplify(coerce);
        if (r.value == this.value) {
            r = this;
        }
        return simplifySomething(r, implicitCoerce);
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append(operator);
        if (value != null) {
            if (value.getPrecedence() > precedence) {
                writer.append("(");
                operandToString(value, writer, localData);
                writer.append(")");
            } else {
                operandToString(value, writer, localData);
            }
        } else {
            writer.append("null");
        }
        return writer;
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        if (dependencies.contains(value)) {
            return false;
        }
        dependencies.add(value);
        return value.isConvertedCompileTime(dependencies);
    }

    @Override
    public boolean isVariableComputed() {
        return value.isVariableComputed();
    }

    @Override
    public List<GraphSourceItemPos> getNeededSources() {
        List<GraphSourceItemPos> ret = super.getNeededSources();
        ret.addAll(value.getNeededSources());
        return ret;
    }

    @Override
    public boolean hasSideEffect() {
        return value.hasSideEffect();
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }

    @Override
    public GraphTargetItem getValue() {
        return value;
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
    public boolean valueEquals(GraphTargetItem obj) {
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
        if (!GraphTargetItem.objectsValueEquals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Converts operand to string.
     *
     * @param operand Operand
     * @param writer Writer
     * @param localData Local data
     * @throws InterruptedException On interrupt
     */
    protected void operandToString(GraphTargetItem operand, GraphTextWriter writer, LocalData localData) throws InterruptedException {
        operand.toString(writer, localData, "");
    }
}
