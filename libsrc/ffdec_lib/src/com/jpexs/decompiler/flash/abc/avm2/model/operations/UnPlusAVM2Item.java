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
package com.jpexs.decompiler.flash.abc.avm2.model.operations;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.abc.avm2.graph.AVM2GraphTargetDialect;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.UnaryOpItem;
import java.util.List;

/**
 * Unary plus.
 *
 * @author JPEXS
 */
public class UnPlusAVM2Item extends UnaryOpItem {

    /**
     * Constructor.
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param value Value
     */
    public UnPlusAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem value) {
        super(AVM2GraphTargetDialect.INSTANCE, instruction, lineStartIns, PRECEDENCE_UNARY, value, "+", "Number");
    }

    @Override
    public Object getResult() {
        return value.getResultAsNumber();
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {                
        return toSourceMerge(localData, generator, value,
                new AVM2Instruction(0, AVM2Instructions.UnPlus, null)
        );
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.NUMBER;
    }
}
