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
package com.jpexs.decompiler.flash.action.model;

import com.jpexs.decompiler.flash.IdentifiersDeobfuscation;
import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.action.swf5.ActionDefineLocal;
import com.jpexs.decompiler.flash.action.swf5.ActionDefineLocal2;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.flash.helpers.hilight.HighlightData;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphSourceItemPos;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.List;
import java.util.Objects;

/**
 * Define local variable.
 *
 * @author JPEXS
 */
public class DefineLocalActionItem extends ActionItem implements SetTypeActionItem {

    /**
     * name
     */
    public GraphTargetItem name;

    /**
     * Temp register
     */
    private int tempRegister = -1;

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(name);
        if (value != null) {
            visitor.visit(value);
        }
    }

    @Override
    public void setValue(GraphTargetItem value) {
        this.value = value;
    }

    @Override
    public int getTempRegister() {
        return tempRegister;
    }

    @Override
    public void setTempRegister(int tempRegister) {
        this.tempRegister = tempRegister;
    }

    @Override
    public GraphTargetItem getValue() {
        return value;
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param name Name
     * @param value Value
     */
    public DefineLocalActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem name, GraphTargetItem value) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY, value);
        this.name = name;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {

        writer.append("var ");

        HighlightData srcData = getSrcData();
        srcData.localName = name.toStringNoQuotes(localData);
        srcData.declaration = true;
        if (((name instanceof DirectValueActionItem)) && (((DirectValueActionItem) name).isString()) && (!IdentifiersDeobfuscation.isValidName(false, ((DirectValueActionItem) name).toStringNoQuotes(localData),
                "this", "super", "true", "false", "NaN", "null", "newline", "Infinity", "undefined", "get", "set", "each"))) {
            IdentifiersDeobfuscation.appendObfuscatedIdentifier(localData.swf, localData.usedDeobfuscations, ((DirectValueActionItem) name).toStringNoQuotes(localData), writer);
        } else {
            stripQuotes(name, localData, writer);
        }
        if (value == null || ((value instanceof DirectValueActionItem) && ((DirectValueActionItem) value).value == Undefined.INSTANCE)) {
            return writer;
        }
        writer.append(" = ");
        return value.toString(writer, localData);
    }

    @Override
    public List<GraphSourceItemPos> getNeededSources() {
        List<GraphSourceItemPos> ret = super.getNeededSources();
        ret.addAll(value.getNeededSources());
        ret.addAll(name.getNeededSources());
        return ret;
    }

    @Override
    public GraphTargetItem getObject() {
        return new DefineLocalActionItem(getSrc(), getLineStartItem(), name, null);
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        if (value == null || (value instanceof DirectValueActionItem && ((DirectValueActionItem) value).value == Undefined.INSTANCE)) {
            return toSourceMerge(localData, generator, name, new ActionDefineLocal2());
        } else {
            return toSourceMerge(localData, generator, name, value, new ActionDefineLocal());
        }

    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final DefineLocalActionItem other = (DefineLocalActionItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
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
        final DefineLocalActionItem other = (DefineLocalActionItem) obj;
        if (!GraphTargetItem.objectsValueEquals(this.name, other.name)) {
            return false;
        }
        if (!GraphTargetItem.objectsValueEquals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

    @Override
    public GraphTargetItem getCompoundValue() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setCompoundValue(GraphTargetItem value) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setCompoundOperator(String operator) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public String getCompoundOperator() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
