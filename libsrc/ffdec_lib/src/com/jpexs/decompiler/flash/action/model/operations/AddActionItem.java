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
import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.ActionGraphTargetDialect;
import com.jpexs.decompiler.flash.action.model.CompoundableBinaryOpAs12;
import com.jpexs.decompiler.flash.action.swf4.ActionAdd;
import com.jpexs.decompiler.flash.action.swf5.ActionAdd2;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.flash.ecma.EcmaType;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.BinaryOpItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.List;

/**
 * Add.
 *
 * @author JPEXS
 */
public class AddActionItem extends BinaryOpItem implements CompoundableBinaryOpAs12 {

    /**
     * Version 2.
     */
    boolean version2;

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param leftSide Left side
     * @param rightSide Right side
     * @param version2 Version 2
     */
    public AddActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem leftSide, GraphTargetItem rightSide, boolean version2) {
        super(ActionGraphTargetDialect.INSTANCE, instruction, lineStartIns, PRECEDENCE_ADDITIVE, leftSide, rightSide, "+", "", "");
        this.version2 = version2;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (rightSide.getPrecedence() >= precedence) { //string + vs number +
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
    public Object getResult() {
        return getResult(rightSide.getResult(), leftSide.getResult(), version2);
    }

    /**
     * Gets result.
     *
     * @param rightResult Right result
     * @param leftResult Left result
     * @param version2 Version 2
     * @return Result
     */
    public static Object getResult(Object rightResult, Object leftResult, boolean version2) {
        if (version2) {
            if (EcmaScript.type(leftResult) == EcmaType.STRING || EcmaScript.type(rightResult) == EcmaType.STRING) {
                return EcmaScript.toString(leftResult) + EcmaScript.toString(rightResult);
            }
            return EcmaScript.toNumberAs2(leftResult) + EcmaScript.toNumberAs2(rightResult);
        } else {
            return Action.toFloatPoint(leftResult) + Action.toFloatPoint(rightResult);
        }
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, leftSide, rightSide, version2 ? new ActionAdd2() : new ActionAdd());
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.BOOLEAN;
    }

    @Override
    public List<GraphSourceItem> getOperatorInstruction() {
        List<GraphSourceItem> ret = new ArrayList<>();
        ret.add(new ActionAdd2());
        return ret;
    }
}
