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
package com.jpexs.decompiler.flash.abc.usages.multinames;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.types.ConvertData;
import com.jpexs.decompiler.flash.abc.types.traits.TraitMethodGetterSetter;
import com.jpexs.decompiler.flash.abc.types.traits.Traits;
import com.jpexs.decompiler.flash.configuration.Configuration;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.helpers.HighlightedTextWriter;
import com.jpexs.decompiler.flash.helpers.NulWriter;
import com.jpexs.decompiler.graph.DottedChain;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Method multiname usage.
 *
 * @author JPEXS
 */
public abstract class MethodMultinameUsage extends TraitMultinameUsage {

    private boolean isInitializer;

    /**
     * Constructor.
     *
     * @param abc ABC
     * @param multinameIndex Multiname index
     * @param scriptIndex Script index
     * @param classIndex Class index
     * @param traitIndex Trait index
     * @param traitsType Traits type
     * @param isInitializer Is initializer
     * @param traits Traits
     * @param parentTraitIndex Parent trait index
     */
    public MethodMultinameUsage(ABC abc, int multinameIndex, int scriptIndex, int classIndex, int traitIndex, int traitsType, boolean isInitializer, Traits traits, int parentTraitIndex) {
        super(abc, multinameIndex, scriptIndex, classIndex, traitIndex, traitsType, traits, parentTraitIndex);
        this.isInitializer = isInitializer;
    }

    /**
     * Is initializer.
     *
     * @return Is initializer
     */
    public boolean isInitializer() {
        return isInitializer;
    }

    @Override
    public String toString() {
        NulWriter nulWriter = new NulWriter();
        ConvertData convertData = new ConvertData();
        if (!isInitializer) {
            if (parentTraitIndex > -1) {
                if (traitsType == TRAITS_TYPE_CLASS) {
                    ((TraitMethodGetterSetter) abc.class_info.get(classIndex).static_traits.traits.get(parentTraitIndex)).convertHeader(new LinkedHashSet<>(), -1, null, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, nulWriter, new ArrayList<>(), false);
                } else {
                    ((TraitMethodGetterSetter) abc.instance_info.get(classIndex).instance_traits.traits.get(parentTraitIndex)).convertHeader(new LinkedHashSet<>(), -1, null, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, nulWriter, new ArrayList<>(), false);
                }
            }
            ((TraitMethodGetterSetter) traits.traits.get(traitIndex)).convertHeader(new LinkedHashSet<>(), -1, null, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, nulWriter, new ArrayList<>(), false);
        }

        HighlightedTextWriter writer = new HighlightedTextWriter(Configuration.getCodeFormatting(), false);
        writer.appendNoHilight(super.toString());
        writer.appendNoHilight(" ");
        if (isInitializer) {
            switch (traitsType) {
                case TRAITS_TYPE_CLASS:
                    writer.appendNoHilight("class initializer");
                    break;
                case TRAITS_TYPE_INSTANCE:
                    writer.appendNoHilight("instance initializer");
                    break;
                case TRAITS_TYPE_SCRIPT:
                    writer.appendNoHilight("script initializer");
                    break;
                default:
                    break;
            }
        } else {
            boolean insideInterface = false;
            if (classIndex > -1) {
                insideInterface = abc.instance_info.get(classIndex).isInterface();
            }
            if (parentTraitIndex > -1) {
                if (traitsType == TRAITS_TYPE_CLASS) {
                    ((TraitMethodGetterSetter) abc.class_info.get(classIndex).static_traits.traits.get(parentTraitIndex)).toStringHeader(new LinkedHashSet<>(), -1, null, DottedChain.EMPTY /*??*/, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, writer, new ArrayList<>(), false, insideInterface);
                } else {
                    ((TraitMethodGetterSetter) abc.instance_info.get(classIndex).instance_traits.traits.get(parentTraitIndex)).toStringHeader(new LinkedHashSet<>(), -1, null, DottedChain.EMPTY /*??*/, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, writer, new ArrayList<>(), false, insideInterface);
                }
                writer.appendNoHilight(" ");
            }
            ((TraitMethodGetterSetter) traits.traits.get(traitIndex)).toStringHeader(new LinkedHashSet<>(), -1, null, DottedChain.EMPTY /*??*/, convertData, "", abc, traitsType == TRAITS_TYPE_CLASS, ScriptExportMode.AS, -1/*FIXME*/, classIndex, writer, new ArrayList<>(), false, insideInterface);
        }
        writer.finishHilights();
        return writer.toString().trim();
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 61 * hash + (this.isInitializer ? 1 : 0);
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
        if (!super.equals(obj)) {
            return false;
        }
        final MethodMultinameUsage other = (MethodMultinameUsage) obj;
        if (this.isInitializer != other.isInitializer) {
            return false;
        }
        return true;
    }

    /**
     * Is static.
     *
     * @return Is static
     */
    public boolean isStatic() {
        return traitsType == TRAITS_TYPE_CLASS;
    }
}
