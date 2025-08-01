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
import com.jpexs.decompiler.flash.action.parser.script.ActionSourceGenerator;
import com.jpexs.decompiler.flash.action.swf4.ActionPush;
import com.jpexs.decompiler.flash.action.swf5.ActionInitObject;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphSourceItemPos;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.LocalData;
import com.jpexs.decompiler.graph.model.TernarOpItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Initialize object.
 *
 * @author JPEXS
 */
public class InitObjectActionItem extends ActionItem {

    /**
     * Names
     */
    public List<GraphTargetItem> names;

    /**
     * Values
     */
    public List<GraphTargetItem> values;

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visitAll(names);
        visitor.visitAll(values);
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param names Names
     * @param values Values
     */
    public InitObjectActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, List<GraphTargetItem> names, List<GraphTargetItem> values) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.values = values;
        this.names = names;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append("{");
        for (int i = values.size() - 1; i >= 0; i--) {
            if (i < values.size() - 1) {
                writer.append(",");
            }
            //AS1/2 does not allow quotes in name here            
            if ((names.get(i) instanceof DirectValueActionItem)
                    && (((DirectValueActionItem) names.get(i)).isSimpleValue())) {
                writer.append(IdentifiersDeobfuscation.printIdentifier(localData.swf, localData.usedDeobfuscations, false, names.get(i).toStringNoQuotes(localData)));
            } else {
                writer.append("(");
                names.get(i).appendTo(writer, localData);
                writer.append(")");
            }
            writer.append(":");
            if (values.get(i) instanceof TernarOpItem) { //Ternar operator contains ":"
                writer.append("(");
                values.get(i).toString(writer, localData);
                writer.append(")");
            } else {
                values.get(i).toString(writer, localData);
            }
        }
        return writer.append("}");
    }

    @Override
    public List<GraphSourceItemPos> getNeededSources() {
        List<GraphSourceItemPos> ret = super.getNeededSources();
        for (GraphTargetItem name : names) {
            ret.addAll(name.getNeededSources());
        }
        for (GraphTargetItem value : values) {
            ret.addAll(value.getNeededSources());
        }
        return ret;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        ActionSourceGenerator asGenerator = (ActionSourceGenerator) generator;
        String charset = asGenerator.getCharset();
        List<GraphSourceItem> ret = new ArrayList<>();
        for (int i = values.size() - 1; i >= 0; i--) {
            ret.addAll(names.get(i).toSource(localData, generator));
            ret.addAll(values.get(i).toSource(localData, generator));
        }
        ret.add(new ActionPush((Long) (long) values.size(), charset));
        ret.add(new ActionInitObject());
        return ret;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.names);
        hash = 61 * hash + Objects.hashCode(this.values);
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
        final InitObjectActionItem other = (InitObjectActionItem) obj;
        if (!Objects.equals(this.names, other.names)) {
            return false;
        }
        if (!Objects.equals(this.values, other.values)) {
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
        final InitObjectActionItem other = (InitObjectActionItem) obj;
        if (!GraphTargetItem.objectsValueEquals(this.names, other.names)) {
            return false;
        }
        if (!GraphTargetItem.objectsValueEquals(this.values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        for (GraphTargetItem n : names) {
            if (n.hasSideEffect()) {
                return true;
            }
        }
        for (GraphTargetItem v : values) {
            if (v.hasSideEffect()) {
                return true;
            }
        }
        return false;
    }

}
