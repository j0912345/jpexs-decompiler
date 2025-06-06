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
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.ecma.ArrayType;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * New array.
 *
 * @author JPEXS
 */
public class NewArrayAVM2Item extends AVM2Item {

    /**
     * Values.
     */
    public List<GraphTargetItem> values;

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param values Values
     */
    public NewArrayAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, List<GraphTargetItem> values) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.values = values;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visitAll(values);
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append("[");
        for (int a = 0; a < values.size(); a++) {
            if (a > 0) {
                writer.allowWrapHere().append(",");
            }
            values.get(a).toString(writer, localData);
        }
        return writer.append("]");
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.ARRAY;
    }

    @Override
    public Object getResult() {
        List<Object> ovalues = new ArrayList<>();
        for (GraphTargetItem it : values) {
            Object o = it.getResult();
            if (o == null) {
                return null;
            }
            ovalues.add(o);
        }
        return new ArrayType(ovalues);
    }

    @Override
    public GraphTargetItem simplify(String implicitCoerce) {
        if (implicitCoerce.isEmpty()) {
            return this;
        }
        return super.simplify(implicitCoerce);
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        for (GraphTargetItem v : values) {
            if (!v.isCompileTime()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, values,
                new AVM2Instruction(0, AVM2Instructions.NewArray, new int[]{values.size()})
        );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.values);
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
        final NewArrayAVM2Item other = (NewArrayAVM2Item) obj;
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        for (GraphTargetItem v : values) {
            if (v.hasSideEffect()) {
                return true;
            }
        }
        return false;
    }

}
