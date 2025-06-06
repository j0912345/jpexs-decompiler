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
import com.jpexs.decompiler.flash.abc.types.Namespace;
import com.jpexs.decompiler.flash.configuration.Configuration;
import com.jpexs.decompiler.flash.ecma.ArrayType;
import com.jpexs.decompiler.flash.ecma.EcmaScript;
import com.jpexs.decompiler.flash.ecma.Null;
import com.jpexs.decompiler.flash.ecma.Undefined;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.TypeItem;
import com.jpexs.decompiler.graph.model.Callable;
import com.jpexs.decompiler.graph.model.LocalData;
import com.jpexs.helpers.Helper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular expression.
 *
 * @author JPEXS
 */
public class RegExpAvm2Item extends AVM2Item implements Callable {

    /**
     * Pattern
     */
    public String pattern;

    /**
     * Modifier
     */
    public String modifier;

    /**
     * Constructor.
     * @param pattern Pattern
     * @param modifier Modifier
     * @param instruction Instruction
     * @param lineStartIns Line start instruction
     */
    public RegExpAvm2Item(String pattern, String modifier, GraphSourceItem instruction, GraphSourceItem lineStartIns) {
        super(instruction, lineStartIns, PRECEDENCE_PRIMARY);
        this.pattern = pattern;
        this.modifier = modifier;
    }

    @Override
    public boolean isCompileTime() {
        return true;
    }

    /**
     * Escapes regular expression string.
     * @param s String
     * @return Escaped string
     */
    public static String escapeRegExpString(String s) {
        StringBuilder ret = new StringBuilder(s.length());
        boolean escape = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '/') {
                if (!escape) {
                    //not escaped / char, returning null
                    return null;
                }
                ret.append("/");
            } else if (c == '\\') {
                ret.append("\\");
                escape = true;
                continue;
            } else if (c == '\n') {
                ret.append("\\n");
            } else if (c == '\r') {
                ret.append("\\r");
            } else if (c == '\t') {
                ret.append("\\t");
            } else if (c == '\b') {
                ret.append("\\b");
            } else if (c == '\f') {
                ret.append("\\f");
            } else if (c < 32) {
                ret.append("\\x").append(Helper.byteToHex((byte) c));
            } else {
                ret.append(c);
            }
            escape = false;
        }

        return ret.toString();
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        if (Configuration.useRegExprLiteral.get()) {
            String escaped = escapeRegExpString(pattern);
            if (escaped != null) {
                writer.append("/");
                writer.append(escaped);
                writer.append("/");
                writer.append(modifier);
                return writer;
            }
        }

        writer.append("new RegExp(");
        writer.append("\"" + Helper.escapeActionScriptString(pattern) + "\"");
        if (!(modifier == null || modifier.isEmpty())) {
            writer.append(",");
            writer.append("\"" + modifier + "\"");
        }
        writer.append(")");
        return writer;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public GraphTargetItem returnType() {
        return new TypeItem("RegExp");
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        AVM2SourceGenerator g = (AVM2SourceGenerator) generator;
        ABC abc = g.abcIndex.getSelectedAbc();
        AVM2ConstantPool constants = abc.constants;
        boolean hasModifier = !(modifier == null || modifier.isEmpty());
        return toSourceMerge(localData, generator,
                ins(AVM2Instructions.GetLex, constants.getQnameId("RegExp", Namespace.KIND_PACKAGE, "", true)),
                new StringAVM2Item(null, null, pattern),
                hasModifier ? new StringAVM2Item(null, null, modifier) : null,
                ins(AVM2Instructions.Construct, hasModifier ? 2 : 1)
        );
    }

    @Override
    public Object call(String methodName, List<Object> args) {
        int flags = 0;
        for (char c : modifier.toCharArray()) {
            switch (c) {
                case 'g':
                    //global (??)
                    break;
                case 'i':
                    flags |= Pattern.CASE_INSENSITIVE;
                    break;
                case 's':
                    flags |= Pattern.DOTALL;
                    break;
                case 'm':
                    flags |= Pattern.MULTILINE;
                    break;
                case 'x':
                    flags |= Pattern.COMMENTS; //?
                    break;
                default:
                    //?
                    break;
            }
        }
        Pattern p = Pattern.compile(pattern, flags);
        switch (methodName) {
            case "exec":
                String estr = EcmaScript.toString(args.get(0));
                Matcher m = p.matcher(estr);
                if (m.find()) {
                    List<Object> avals = new ArrayList<>();
                    for (int i = 0; i <= m.groupCount(); i++) {
                        avals.add(m.group(i));
                    }
                    ArrayType a = new ArrayType(avals);
                    a.setAttribute("input", estr);
                    a.setAttribute("index", m.start());
                    return a;
                } else {
                    return Null.INSTANCE;
                }
            case "test":
                String tstr = EcmaScript.toString(args.get(0));
                return p.matcher(tstr).find(); //boolean
        }
        return Undefined.INSTANCE; //?
    }

    @Override
    public Object call(List<Object> args) {
        return call("exec", args);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.pattern);
        hash = 89 * hash + Objects.hashCode(this.modifier);
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
        final RegExpAvm2Item other = (RegExpAvm2Item) obj;
        if (!Objects.equals(this.pattern, other.pattern)) {
            return false;
        }
        if (!Objects.equals(this.modifier, other.modifier)) {
            return false;
        }
        return true;
    }

}
