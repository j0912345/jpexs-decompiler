/*
 *  Copyright (C) 2010-2024 JPEXS, All rights reserved.
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

/**
 * Decrement local register by 1.
 *
 * @author JPEXS
 */
public class DecLocalAVM2Item extends AVM2Item {

    public int regIndex;

    public DecLocalAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, int regIndex) {
        super(instruction, lineStartIns, PRECEDENCE_POSTFIX);
        this.regIndex = regIndex;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) {
        writer.append(localRegName(localData.localRegNames, regIndex));
        return writer.append("--");
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
        int hash = 3;
        hash = 73 * hash + this.regIndex;
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
        final DecLocalAVM2Item other = (DecLocalAVM2Item) obj;
        if (this.regIndex != other.regIndex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
