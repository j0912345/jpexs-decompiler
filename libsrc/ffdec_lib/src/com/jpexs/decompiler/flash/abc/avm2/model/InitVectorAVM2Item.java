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
import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.avm2.AVM2ConstantPool;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instructions;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.AVM2SourceGenerator;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.AbcIndexing;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.NamespaceItem;
import com.jpexs.decompiler.flash.abc.types.Multiname;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.DottedChain;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Initialize vector.
 *
 * @author JPEXS
 */
public class InitVectorAVM2Item extends AVM2Item {

    /**
     * Vector package name
     */
    public static final DottedChain VECTOR_PACKAGE = new DottedChain(new String[]{"__AS3__", "vec"});

    /**
     * Vector fully qualified name
     */
    public static final DottedChain VECTOR_FQN = new DottedChain(new String[]{"__AS3__", "vec", "Vector"});

    /**
     * Vector of int fully qualified name
     */
    public static final DottedChain VECTOR_INT = new DottedChain(new String[]{"__AS3__", "vec", "Vector$int"});

    /**
     * Vector of double fully qualified name
     */
    public static final DottedChain VECTOR_DOUBLE = new DottedChain(new String[]{"__AS3__", "vec", "Vector$double"});

    /**
     * Vector of uint fully qualified name
     */
    public static final DottedChain VECTOR_UINT = new DottedChain(new String[]{"__AS3__", "vec", "Vector$uint"});

    /**
     * Vector of object fully qualified name
     */
    public static final DottedChain VECTOR_OBJECT = new DottedChain(new String[]{"__AS3__", "vec", "Vector$object"});

    /**
     * Subtype
     */
    public GraphTargetItem subtype;

    /**
     * Arguments
     */
    public List<GraphTargetItem> arguments;

    /**
     * Opened namespaces
     */
    List<NamespaceItem> openedNamespaces;

    private int allNsSet(AbcIndexing abc) throws CompilationException {
        return NamespaceItem.getCpoolSetIndex(abc, openedNamespaces);
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        visitor.visit(subtype);
        visitor.visitAll(arguments);
    }

    /**
     * Constructor.
     *
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     * @param subtype Subtype
     * @param arguments Arguments
     */
    public InitVectorAVM2Item(GraphSourceItem instruction, GraphSourceItem lineStartIns, GraphTargetItem subtype, List<GraphTargetItem> arguments) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.subtype = subtype;
        this.arguments = arguments;
    }

    /**
     * Constructor.
     *
     * @param subtype Subtype
     * @param arguments Arguments
     * @param openedNamespaces Opened namespaces
     */
    public InitVectorAVM2Item(GraphTargetItem subtype, List<GraphTargetItem> arguments, List<NamespaceItem> openedNamespaces) {
        super(null, null, PRECEDENCE_PRIMARY);
        this.subtype = subtype;
        this.arguments = arguments;
        this.openedNamespaces = openedNamespaces;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append("<");
        subtype.appendTry(writer, localData);
        writer.append(">");
        writer.append("[");
        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                writer.append(",");
            }
            arguments.get(i).appendTry(writer, localData);
        }
        writer.append("]");
        return writer;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public GraphTargetItem returnType() {
        List<GraphTargetItem> pars = new ArrayList<>();
        pars.add(subtype);
        return new ApplyTypeAVM2Item(null, null, new TypeItem(VECTOR_FQN), pars);
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        AVM2SourceGenerator g = (AVM2SourceGenerator) generator;
        ABC abc = g.abcIndex.getSelectedAbc();
        AVM2ConstantPool constants = abc.constants;
        List<GraphSourceItem> ret = toSourceMerge(localData, generator,
                new TypeItem(VECTOR_FQN),
                subtype,
                ins(AVM2Instructions.ApplyType, 1),
                new IntegerValueAVM2Item(null, null, arguments.size()),
                ins(AVM2Instructions.Construct, 1)
        );
        for (int i = 0; i < arguments.size(); i++) {
            ret.addAll(toSourceMerge(localData, generator,
                    ins(AVM2Instructions.Dup),
                    new IntegerValueAVM2Item(null, null, i),
                    arguments.get(i),
                    ins(AVM2Instructions.SetProperty, constants.getMultinameId(Multiname.createMultinameL(false, NamespaceItem.getCpoolSetIndex(g.abcIndex, openedNamespaces)), true))
            ));
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.subtype);
        hash = 31 * hash + Objects.hashCode(this.arguments);
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
        final InitVectorAVM2Item other = (InitVectorAVM2Item) obj;
        if (!Objects.equals(this.subtype, other.subtype)) {
            return false;
        }
        if (!Objects.equals(this.arguments, other.arguments)) {
            return false;
        }
        return true;
    }

}
