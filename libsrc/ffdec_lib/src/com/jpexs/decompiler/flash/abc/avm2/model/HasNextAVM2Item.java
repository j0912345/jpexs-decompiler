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

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.List;
import java.util.Objects;

/**
 * Determine if the given object has any more properties.
 *
 * @author JPEXS
 */
public class HasNextAVM2Item extends AVM2Item {

    /**
     * Index
     */
    public GraphTargetItem index;

    /**
     * Object
     */
    public GraphTargetItem obj;

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param index Index
     * @param obj Object
     */
    public HasNextAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem index, GraphTargetItem obj) {
        super(instruction, lineStartIns, NOPRECEDENCE);
        this.index = index;
        this.obj = obj;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(index);
        visitor.visit(obj);
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        if ((index instanceof LocalRegAVM2Item) && (obj instanceof LocalRegAVM2Item)) {
            int indexReg = ((LocalRegAVM2Item) index).regIndex;
            int objectReg = ((LocalRegAVM2Item) obj).regIndex;
            return toSourceMerge(localData, generator, ins(AVM2Instructions.HasNext2, objectReg, indexReg));
        }
        return toSourceMerge(localData, generator, obj, index, ins(AVM2Instructions.HasNext));
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {

        writer.append("§§hasnext(");
        if (obj != null) {
            obj.appendTry(writer, localData);
        } else {
            writer.append("null");
        }
        writer.append(",");
        if (index != null) {
            index.appendTry(writer, localData);
        } else {
            writer.append("null");
        }
        writer.append(")");
        return writer;
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.BOOLEAN;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.index);
        hash = 29 * hash + Objects.hashCode(this.obj);
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
        final HasNextAVM2Item other = (HasNextAVM2Item) obj;
        if (!Objects.equals(this.index, other.index)) {
            return false;
        }
        if (!Objects.equals(this.obj, other.obj)) {
            return false;
        }
        return true;
    }

}
