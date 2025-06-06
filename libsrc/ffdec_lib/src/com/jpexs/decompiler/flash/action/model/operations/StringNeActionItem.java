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
package com.jpexs.decompiler.flash.action.model.operations;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.action.ActionGraphTargetDialect;
import com.jpexs.decompiler.flash.action.swf4.ActionNot;
import com.jpexs.decompiler.flash.action.swf4.ActionStringEquals;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.BinaryOpItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * String not equal.
 *
 * @author JPEXS
 */
public class StringNeActionItem extends BinaryOpItem implements Inverted {

    /**
     * Constructor.
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param leftSide Left side
     * @param rightSide Right side
     */
    public StringNeActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem leftSide, GraphTargetItem rightSide) {
        super(ActionGraphTargetDialect.INSTANCE, instruction, lineStartIns, PRECEDENCE_EQUALITY, leftSide, rightSide, "ne", "String", "String");
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        return false;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, leftSide, rightSide, new ActionStringEquals(), new ActionNot());
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.BOOLEAN;
    }

    @Override
    public GraphTargetItem invert(GraphSourceItem negSrc) {
        return new StringEqActionItem(getSrc(), getLineStartItem(), leftSide, rightSide);
    }

    @Override
    public List<GraphSourceItem> getOperatorInstruction() {
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(new ActionStringEquals());
        ret.add(new ActionNot());
        return ret;
    }
}
