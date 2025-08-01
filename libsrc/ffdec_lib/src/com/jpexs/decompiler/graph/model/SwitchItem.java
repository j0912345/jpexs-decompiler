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
package com.jpexs.decompiler.graph.model;

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.flash.helpers.LoopWithType;
import com.jpexs.decompiler.flash.helpers.NulWriter;
import com.jpexs.decompiler.graph.Block;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetDialect;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.Loop;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Switch statement.
 *
 * @author JPEXS
 */
public class SwitchItem extends LoopItem implements Block {

    /**
     * Switched object
     */
    public GraphTargetItem switchedObject;

    /**
     * Case values
     */
    public List<GraphTargetItem> caseValues;

    /**
     * Case commands
     */
    public List<List<GraphTargetItem>> caseCommands;

    /**
     * Values mapping
     */
    public List<Integer> valuesMapping;

    /**
     * Label used
     */
    private boolean labelUsed;

    @Override
    public List<List<GraphTargetItem>> getSubs() {
        List<List<GraphTargetItem>> ret = new ArrayList<>();
        ret.addAll(caseCommands);
        return ret;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(switchedObject);
        visitor.visitAll(caseValues);
        for (List<GraphTargetItem> c : caseCommands) {
            visitor.visitAll(c);
        }
    }

    @Override
    public void visitNoBlock(GraphTargetVisitorInterface visitor) {
        visitor.visit(switchedObject);
        visitor.visitAll(caseValues);
    }

    /**
     * Constructor.
     * @param dialect Dialect
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param loop Loop
     * @param switchedObject Switched object
     * @param caseValues Case values
     * @param caseCommands Case commands
     * @param valuesMapping Values mapping
     */
    public SwitchItem(GraphTargetDialect dialect, GraphSourceItem instruction, GraphSourceItem lineStartIns, Loop loop, GraphTargetItem switchedObject, List<GraphTargetItem> caseValues, List<List<GraphTargetItem>> caseCommands, List<Integer> valuesMapping) {
        super(dialect, instruction, lineStartIns, loop);
        this.switchedObject = switchedObject;
        this.caseValues = caseValues;
        this.caseCommands = caseCommands;
        this.valuesMapping = valuesMapping;
    }

    @Override
    public boolean needsSemicolon() {
        return false;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (writer instanceof NulWriter) {
            ((NulWriter) writer).startLoop(loop.id, LoopWithType.LOOP_TYPE_SWITCH);
        }
        if (labelUsed) {
            writer.append("loop").append(loop.id).append(":").newLine();
        }
        writer.append("switch");
        if (writer.getFormatting().spaceBeforeParenthesesSwitchParentheses) {
            writer.append(" ");
        }
        writer.append("(");
        switchedObject.toString(writer, localData);
        writer.append(")").startBlock();
        for (int i = 0; i < caseCommands.size(); i++) {

            //if last is default and is empty, ignore it
            /*if (i == caseCommands.size() - 1) {
                if (caseCommands.get(i).isEmpty()) {
                    boolean hasDefault = false;
                    boolean hasNonDefault = false;
                    for (int k = 0; k < valuesMapping.size(); k++) {
                        if (valuesMapping.get(k) == i) {
                            if (caseValues.get(k) instanceof DefaultItem) {
                                hasDefault = true;
                            } else {
                                hasNonDefault = true;
                            }
                        }
                    }
                    if (hasDefault && !hasNonDefault) {
                        continue;
                    }
                }
            }*/
            for (int k = 0; k < valuesMapping.size(); k++) {
                if (valuesMapping.get(k) == i) {
                    if (!(caseValues.get(k) instanceof DefaultItem)) {
                        writer.append("case ");
                    }
                    caseValues.get(k).toString(writer, localData);
                    writer.append(":").newLine();
                }
            }
            writer.indent();
            for (int j = 0; j < caseCommands.get(i).size(); j++) {
                if (!caseCommands.get(i).get(j).isEmpty()) {
                    caseCommands.get(i).get(j).toStringSemicoloned(writer, localData).newLine();
                }
            }
            writer.unindent();
        }
        writer.endBlock();
        if (writer instanceof NulWriter) {
            LoopWithType loopOjb = ((NulWriter) writer).endLoop(loop.id);
            labelUsed = loopOjb.used;
        }
        return writer;
    }

    @Override
    public List<ContinueItem> getContinues() {
        List<ContinueItem> ret = new ArrayList<>();

        for (List<GraphTargetItem> onecase : caseCommands) {
            for (GraphTargetItem ti : onecase) {
                if (ti instanceof ContinueItem) {
                    ret.add((ContinueItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getContinues());
                }
            }
        }
        return ret;
    }
    
    @Override
    public List<BreakItem> getBreaks() {
        List<BreakItem> ret = new ArrayList<>();

        for (List<GraphTargetItem> onecase : caseCommands) {
            for (GraphTargetItem ti : onecase) {
                if (ti instanceof BreakItem) {
                    ret.add((BreakItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getBreaks());
                }
            }
        }
        return ret;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return generator.generate(localData, this);
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public GraphTargetItem returnType() {
        return TypeItem.UNBOUNDED;
    }

    @Override
    public boolean hasBaseBody() {
        return false;
    }

    @Override
    public List<GraphTargetItem> getBaseBodyCommands() {
        return null;
    }
    
    public void removeValue(int index) {
        int m = valuesMapping.get(index);
        caseValues.remove(index);
        valuesMapping.remove(index);
        boolean otherFound = false;
        for (int i = 0; i < valuesMapping.size(); i++) {
            if (valuesMapping.get(i) == m) {
                return;
            }
        }
        caseCommands.remove(m);
        for (int i = 0; i < valuesMapping.size(); i++) {
            if (valuesMapping.get(i) > m) {
                valuesMapping.set(i, valuesMapping.get(i) - 1);
            }
        }
    }
}
