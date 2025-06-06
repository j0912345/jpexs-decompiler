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

import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.AVM2SourceGenerator;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.flash.ecma.EcmaType;
import com.jpexs.decompiler.flash.ecma.ObjectType;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SimpleValue;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Get property.
 *
 * @author JPEXS
 */
public class GetPropertyAVM2Item extends AVM2Item {

    /**
     * Object
     */
    public GraphTargetItem object;

    /**
     * Property name
     */
    public GraphTargetItem propertyName;

    /**
     * Type
     */
    public GraphTargetItem type;

    /**
     * Call type
     */
    public GraphTargetItem callType;

    /**
     * Is static
     */
    public boolean isStatic;
    
    /**
     * Is null condition - ?.
     */
    public boolean nullCondition = false;

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(object);
        visitor.visit(propertyName);
    }

    @Override
    public boolean isConvertedCompileTime(Set<GraphTargetItem> dependencies) {
        if (object != null && object.isCompileTime() && (propertyName instanceof FullMultinameAVM2Item) && (((FullMultinameAVM2Item) propertyName).name != null) && (((FullMultinameAVM2Item) propertyName).name.isCompileTime()) && "constructor".equals(((FullMultinameAVM2Item) propertyName).name.getResult())) {
            Object obj = object.getResult();
            EcmaType t = EcmaScript.type(obj);
            if (t != EcmaType.OBJECT) {
                if (t.getClassName() != null) {
                    //Note: it's for toString only :-(
                    return true;
                }
            }
            if (obj instanceof ObjectType) {
                //Note: it's for toString only :-(
                return true;
            }
        }
        return isCompileTime(dependencies);
    }

    @Override
    public boolean isCompileTime(Set<GraphTargetItem> dependencies) {
        if ((object instanceof SimpleValue) && (((SimpleValue) object).isSimpleValue())) {
            return true;
        }
        if (object instanceof NewArrayAVM2Item) {
            if (((NewArrayAVM2Item) object).values.isEmpty()) {
                return true;
            }
        }
        if (object instanceof NewObjectAVM2Item) {
            if (((NewObjectAVM2Item) object).pairs.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getResultAsString() {
        if (object.isCompileTime() && (propertyName instanceof FullMultinameAVM2Item) && ((FullMultinameAVM2Item) propertyName).name != null && (((FullMultinameAVM2Item) propertyName).name.isCompileTime()) && "constructor".equals(((FullMultinameAVM2Item) propertyName).name.getResult())) {
            Object obj = object.getResult();
            EcmaType t = EcmaScript.type(obj);
            if (t != EcmaType.OBJECT) {
                if (t.getClassName() != null) {
                    return "[class " + t.getClassName() + "]";
                }
            }
            if (obj instanceof ObjectType) {
                return "[class " + ((ObjectType) obj).getTypeName() + "]";
            }
        }
        return super.getResultAsString();
    }

    @Override
    public Object getResult() {
        Object ores = object.getResult();
        EcmaType type = EcmaScript.type(ores);
        if (type != EcmaType.OBJECT && (propertyName instanceof FullMultinameAVM2Item) && (((FullMultinameAVM2Item) propertyName).resolvedMultinameName != null)) {
            return type.getProperty(ores, ((FullMultinameAVM2Item) propertyName).resolvedMultinameName);
        }
        if (object instanceof NewArrayAVM2Item) {
            if (((NewArrayAVM2Item) object).values.isEmpty()) {
                return Undefined.INSTANCE;
            }
        }
        if (object instanceof NewObjectAVM2Item) {
            if (((NewObjectAVM2Item) object).pairs.isEmpty()) {
                return Undefined.INSTANCE;
            }
        }
        return null;
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param object Object
     * @param propertyName Property name
     * @param type Type
     * @param callType Call type
     * @param isStatic Is static
     */
    public GetPropertyAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem object, GraphTargetItem propertyName, GraphTargetItem type, GraphTargetItem callType, boolean isStatic) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.object = object;
        this.propertyName = propertyName;
        this.type = type;
        this.callType = callType;
        this.isStatic = isStatic;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        return formatProperty(writer, object, propertyName, localData, isStatic, nullCondition);
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        return toSourceMerge(localData, generator, object,
                new AVM2Instruction(0, AVM2Instructions.GetProperty, new int[]{((AVM2SourceGenerator) generator).propertyName(propertyName)})
        );
    }

    @Override
    public GraphTargetItem returnType() {
        if (object instanceof FindPropertyAVM2Item) {
            FindPropertyAVM2Item fprop = (FindPropertyAVM2Item) object;
            if (fprop.propertyName instanceof FullMultinameAVM2Item) {
                FullMultinameAVM2Item fmul = (FullMultinameAVM2Item) fprop.propertyName;
                if (this.propertyName.equals(fmul)) {
                    switch (fmul.resolvedMultinameName) {
                        case "NaN":
                            return TypeItem.NUMBER;
                        case "undefined":
                            return TypeItem.UNDEFINED;
                    }
                }
            }
        }
        return type;
        //return TypeItem.UNBOUNDED;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.object);
        hash = 97 * hash + Objects.hashCode(this.propertyName);
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
        final GetPropertyAVM2Item other = (GetPropertyAVM2Item) obj;
        if (!Objects.equals(this.object, other.object)) {
            return false;
        }
        if (!Objects.equals(this.propertyName, other.propertyName)) {
            return false;
        }
        return true;
    }
}
