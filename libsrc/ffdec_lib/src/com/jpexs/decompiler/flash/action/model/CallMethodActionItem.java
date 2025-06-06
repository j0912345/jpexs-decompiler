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
import com.jpexs.decompiler.flash.action.swf4.RegisterNumber;
import com.jpexs.decompiler.flash.action.swf5.ActionCallMethod;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphSourceItemPos;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.List;
import java.util.Objects;

/**
 * Call method.
 *
 * @author JPEXS
 */
public class CallMethodActionItem extends ActionItem {

    /**
     * Method name
     */
    public GraphTargetItem methodName;

    /**
     * Script object
     */
    public GraphTargetItem scriptObject;

    /**
     * Arguments
     */
    public List<GraphTargetItem> arguments;

    /**
     * Special - getter
     */
    public static int SPECIAL_GETTER = 1;
    /**
     * Special - setter
     */
    public static int SPECIAL_SETTER = 2;

    /**
     * Special
     */
    private int special = 0;

    /**
     * Setter/getter variable name
     */
    private String setterGetterVarName = null;

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visitAll(arguments);
        visitor.visit(scriptObject);
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param scriptObject Script object
     * @param methodName Method name
     * @param arguments Arguments
     */
    public CallMethodActionItem(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem scriptObject, GraphTargetItem methodName, List<GraphTargetItem> arguments) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.methodName = methodName;
        this.arguments = arguments;
        this.scriptObject = scriptObject;

        if (methodName instanceof DirectValueActionItem) {
            DirectValueActionItem dv = (DirectValueActionItem) methodName;
            if (dv.isString()) {
                String methodNameStr = dv.getAsString();
                if (methodNameStr.startsWith("__get__") && arguments.isEmpty()) {
                    special = SPECIAL_GETTER;
                    setterGetterVarName = methodNameStr.substring(7);
                } else if (methodNameStr.startsWith("__set__") && arguments.size() == 1) {
                    special = SPECIAL_SETTER;
                    setterGetterVarName = methodNameStr.substring(7);
                    precedence = PRECEDENCE_ASSIGNMENT;
                }
            }
        }
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (special == SPECIAL_GETTER) {
            if (scriptObject.getPrecedence() > this.precedence) {
                writer.append("(");
                scriptObject.toString(writer, localData);
                writer.append(")");
            } else {
                scriptObject.toString(writer, localData);
            }
            writer.allowWrapHere().append(".");
            writer.append(setterGetterVarName);
            return writer;
        } else if (special == SPECIAL_SETTER) {
            if (scriptObject.getPrecedence() > this.precedence) {
                writer.append("(");
                scriptObject.toString(writer, localData);
                writer.append(")");
            } else {
                scriptObject.toString(writer, localData);
            }
            writer.allowWrapHere().append(".");
            writer.append(setterGetterVarName);
            writer.append(" = ");
            arguments.get(0).toStringNL(writer, localData);
            return writer;
        }
        if (methodName instanceof DirectValueActionItem) {
            boolean blankMethod = false;

            if (((DirectValueActionItem) methodName).value == Undefined.INSTANCE) {
                blankMethod = true;
            }
            if (((DirectValueActionItem) methodName).value instanceof String) {
                if (((DirectValueActionItem) methodName).value.equals("")) {
                    blankMethod = true;
                }
            }

            if (!blankMethod) {
                if (scriptObject.getPrecedence() > this.precedence
                        || ((scriptObject instanceof DirectValueActionItem) && (((DirectValueActionItem) scriptObject).value instanceof Long))) {
                    writer.append("(");
                    scriptObject.toString(writer, localData);
                    writer.append(")");
                } else {
                    scriptObject.toString(writer, localData);
                }
                if (!(((DirectValueActionItem) methodName).value instanceof RegisterNumber)
                        && IdentifiersDeobfuscation.isValidName(false, methodName.toStringNoQuotes(localData))) {
                    writer.allowWrapHere().append(".");
                    methodName.toStringNoQuotes(writer, localData);
                } else {
                    writer.append("[");
                    methodName.toString(writer, localData);
                    writer.append("]");
                }
                //writer.append(IdentifiersDeobfuscation.printIdentifier(false, methodName.toStringNoQuotes(localData)));
            } else {
                scriptObject.toString(writer, localData);
            }
        } else {
            if (scriptObject.getPrecedence() > this.precedence) {
                writer.append("(");
                scriptObject.toString(writer, localData);
                writer.append(")");
            } else {
                scriptObject.toString(writer, localData);
            }

            writer.append("[");
            methodName.appendTry(writer, localData);
            writer.append("]");
        }

        writer.spaceBeforeCallParenthesis(arguments.size());
        writer.append("(");
        for (int t = 0; t < arguments.size(); t++) {
            if (t > 0) {
                writer.allowWrapHere().append(",");
            }
            arguments.get(t).toStringNL(writer, localData);
        }

        return writer.append(")");
    }

    @Override
    public List<GraphSourceItemPos> getNeededSources() {
        List<GraphSourceItemPos> ret = super.getNeededSources();
        ret.addAll(methodName.getNeededSources());
        ret.addAll(scriptObject.getNeededSources());
        for (GraphTargetItem ti : arguments) {
            ret.addAll(ti.getNeededSources());
        }
        return ret;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, toSourceCall(localData, generator, arguments), scriptObject, methodName, new ActionCallMethod());
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.methodName);
        hash = 79 * hash + Objects.hashCode(this.scriptObject);
        hash = 79 * hash + Objects.hashCode(this.arguments);
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
        final CallMethodActionItem other = (CallMethodActionItem) obj;
        if (!Objects.equals(this.methodName, other.methodName)) {
            return false;
        }
        if (!Objects.equals(this.scriptObject, other.scriptObject)) {
            return false;
        }
        if (!Objects.equals(this.arguments, other.arguments)) {
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
        final CallMethodActionItem other = (CallMethodActionItem) obj;
        if (!GraphTargetItem.objectsValueEquals(this.methodName, other.methodName)) {
            return false;
        }
        if (!GraphTargetItem.objectsValueEquals(this.scriptObject, other.scriptObject)) {
            return false;
        }
        if (!GraphTargetItem.objectsValueEquals(this.arguments, other.arguments)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasSideEffect() {
        return true;
    }
}
