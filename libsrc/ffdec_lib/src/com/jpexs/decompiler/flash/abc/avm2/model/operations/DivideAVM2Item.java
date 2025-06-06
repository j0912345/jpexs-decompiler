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
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.BinaryOpItem;
import com.jpexs.decompiler.graph.model.CompoundableBinaryOp;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.List;

/**
 * Divide two values.
 *
 * @author JPEXS
 */
public class DivideAVM2Item extends BinaryOpItem implements CompoundableBinaryOp {

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param leftSide Left side
     * @param rightSide Right side
     */
    public DivideAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem leftSide, GraphTargetItem rightSide) {
        super(AVM2GraphTargetDialect.INSTANCE, instruction, lineStartIns, PRECEDENCE_MULTIPLICATIVE, leftSide, rightSide, "/", "Number", "Number");
    }

    @Override
    public Object getResult() {
        Double leftResult = leftSide.getResultAsNumber();
        Double rightResult = rightSide.getResultAsNumber();
        if (Double.compare(rightResult, 0) == 0) {
            return leftResult < 0 ? Double.NEGATIVE_INFINITY
                    : leftResult > 0 ? Double.POSITIVE_INFINITY : Double.NaN;
        }
        return leftResult / rightResult;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (rightSide.getPrecedence() >= precedence) { // >=  add or subtract too
            if (leftSide.getPrecedence() > precedence) {
                writer.append("(");
                leftSide.toString(writer, localData);
                writer.append(")");
            } else {
                leftSide.toString(writer, localData);
            }
            writer.append(" ");
            writer.append(operator);
            writer.append(" ");

            writer.append("(");
            rightSide.toString(writer, localData);
            return writer.append(")");
        } else {
            return super.appendTo(writer, localData);
        }
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        if (localData.numberContext != null) {
            return toSourceMerge(localData, generator, leftSide, rightSide,
                new AVM2Instruction(0, AVM2Instructions.DivideP, new int[] {localData.numberContext})
            );
        }        
        
        return toSourceMerge(localData, generator, leftSide, rightSide,
                new AVM2Instruction(0, AVM2Instructions.Divide, null)
        );
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.NUMBER;
        //return TypeItem.UNBOUNDED;
    }

    @Override
    public List<GraphSourceItem> getOperatorInstruction() {
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(new AVM2Instruction(0, AVM2Instructions.Divide, null));
        return ret;
    }
}
