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

import com.jpexs.decompiler.flash.abc.types.Multiname;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.Objects;

/**
 * Find definition.
 *
 * @author JPEXS
 */
public class FindDefAVM2Item extends AVM2Item {

    /**
     * Property name
     */
    public Multiname propertyName;

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param propertyName Property name
     */
    public FindDefAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, Multiname propertyName) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.propertyName = propertyName;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) {
        return writer.append(propertyName.getNamespace(localData.constantsAvm2).getName(localData.constantsAvm2).toPrintableString(localData.usedDeobfuscations, localData.swf, true)); //assume not null name
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.UNBOUNDED;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.propertyName);
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
        final FindDefAVM2Item other = (FindDefAVM2Item) obj;
        if (!Objects.equals(this.propertyName, other.propertyName)) {
            return false;
        }
        return true;
    }

}
