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
 * Store value to memory.
 *
 * @author JPEXS
 */
public class AlchemyStoreAVM2Item extends AVM2Item {

    private final String type;

    private final int size;

    private final GraphTargetItem ofs;

    /**
     * Constructor.
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param value Value
     * @param ofs Offset
     * @param type Type
     * @param size Size
     */
    public AlchemyStoreAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem value, GraphTargetItem ofs, String type, int size) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY, value);
        this.ofs = ofs;
        this.type = type;
        this.size = size;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(ofs);
        if (value != null) {
            visitor.visit(value);
        }
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        String ts = "" + type + size;
        if (type.equals("f4")) {
            ts = "f32x4";
        }
        writer.append("s").append(ts).append("(");
        value.toString(writer, localData);
        writer.append(",");
        ofs.toString(writer, localData);
        return writer.append(")");
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.UNBOUNDED;
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        String ts = "" + type + size;
        if (type.equals("f4")) {
            ts = "f32x4";
        }
        int code = 0;
        switch (ts) {
            case "i8":
                code = AVM2Instructions.Si8;
                break;
            case "i16":
                code = AVM2Instructions.Si16;
                break;
            case "i32":
                code = AVM2Instructions.Si32;
                break;
            case "f32":
                code = AVM2Instructions.Sf32;
                break;
            case "f32x4":
                code = AVM2Instructions.Sf32x4;
                break;
            case "f64":
                code = AVM2Instructions.Sf64;
                break;
        }
        return toSourceMerge(localData, generator, ofs, ins(code));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + this.size;
        hash = 97 * hash + Objects.hashCode(this.ofs);
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
        final AlchemyStoreAVM2Item other = (AlchemyStoreAVM2Item) obj;
        if (this.size != other.size) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.ofs, other.ofs)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
