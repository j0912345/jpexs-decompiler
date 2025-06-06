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
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.List;
import java.util.Objects;

/**
 * Call super.
 *
 * @author JPEXS
 */
public class CallSuperAVM2Item extends AVM2Item {

    /**
     * Receiver
     */
    public GraphTargetItem receiver;

    /**
     * Multiname
     */
    public GraphTargetItem multiname;

    /**
     * Arguments
     */
    public List<GraphTargetItem> arguments;

    /**
     * Is void
     */
    public boolean isVoid;

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param isVoid Is void
     * @param receiver Receiver
     * @param multiname Multiname
     * @param arguments Arguments
     */
    public CallSuperAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, boolean isVoid, GraphTargetItem receiver, GraphTargetItem multiname, List<GraphTargetItem> arguments) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.receiver = receiver;
        this.multiname = multiname;
        this.arguments = arguments;
        this.isVoid = isVoid;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(receiver);
        visitor.visit(multiname);
        visitor.visitAll(arguments);
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (!receiver.toString().equals("this") && !(receiver.getThroughDuplicate() instanceof FindPropertyAVM2Item)) {
            receiver.toString(writer, localData);
            writer.append(".");
        }
        writer.append("super.");
        multiname.toString(writer, localData);
        writer.append("(");
        String args = "";
        for (int a = 0; a < arguments.size(); a++) {
            if (a > 0) {
                writer.append(",");
            }
            arguments.get(a).toString(writer, localData);
        }
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
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.receiver);
        hash = 53 * hash + Objects.hashCode(this.multiname);
        hash = 53 * hash + Objects.hashCode(this.arguments);
        hash = 53 * hash + (this.isVoid ? 1 : 0);
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
        final CallSuperAVM2Item other = (CallSuperAVM2Item) obj;
        if (this.isVoid != other.isVoid) {
            return false;
        }
        if (!Objects.equals(this.receiver, other.receiver)) {
            return false;
        }
        if (!Objects.equals(this.multiname, other.multiname)) {
            return false;
        }
        if (!Objects.equals(this.arguments, other.arguments)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
