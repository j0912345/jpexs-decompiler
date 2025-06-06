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
import com.jpexs.decompiler.flash.action.swf5.ActionDelete;
import com.jpexs.decompiler.flash.action.swf5.ActionDelete2;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
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
 * Delete property.
 *
 * @author JPEXS
 */
public class DeleteActionItem extends ActionItem {

    /**
     * Object
     */
    public GraphTargetItem object;

    /**
     * Property name
     */
    public GraphTargetItem propertyName;

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(object);
        visitor.visit(propertyName);
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param object Object
     * @param propertyName Property name
     */
    public DeleteActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem object, GraphTargetItem propertyName) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.object = object;
        this.propertyName = propertyName;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append("delete ");
        if (object != null) {
            object.toStringNoQuotes(writer, localData);
            if ((propertyName instanceof DirectValueActionItem) && ((DirectValueActionItem) propertyName).isString()
                    && (IdentifiersDeobfuscation.isValidName(false, propertyName.toStringNoQuotes(localData)))) {
                writer.append(".");
                propertyName.toStringNoQuotes(writer, localData);
            } else {
                writer.append("[");
                propertyName.toString(writer, localData);
                writer.append("]");
            }
            return writer;
        }

        if (propertyName.getPrecedence() > getPrecedence()) {
            writer.append("(");
        }
        if (IdentifiersDeobfuscation.isValidName(false, propertyName.toStringNoQuotes(localData))) {
            propertyName.toStringNoQuotes(writer, localData);
        } else {
            propertyName.toString(writer, localData);
        }
        if (propertyName.getPrecedence() > getPrecedence()) {
            writer.append(")");
        }
        return writer;
    }

    @Override
    public List<GraphSourceItemPos> getNeededSources() {
        List<GraphSourceItemPos> ret = super.getNeededSources();
        ret.addAll(object.getNeededSources());
        ret.addAll(propertyName.getNeededSources());
        return ret;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        if (object == null) {
            return toSourceMerge(localData, generator, propertyName, new ActionDelete2());
        }
        return toSourceMerge(localData, generator, object, propertyName, new ActionDelete());
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.object);
        hash = 97 * hash + Objects.hashCode(this.propertyName);
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
        final DeleteActionItem other = (DeleteActionItem) obj;
        if (!Objects.equals(this.object, other.object)) {
            return false;
        }
        if (!Objects.equals(this.propertyName, other.propertyName)) {
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
        final DeleteActionItem other = (DeleteActionItem) obj;
        if (!GraphTargetItem.objectsValueEquals(this.object, other.object)) {
            return false;
        }
        if (!GraphTargetItem.objectsValueEquals(this.propertyName, other.propertyName)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }

}
