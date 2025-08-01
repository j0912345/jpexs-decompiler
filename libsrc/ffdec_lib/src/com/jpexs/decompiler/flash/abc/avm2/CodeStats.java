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
package com.jpexs.decompiler.flash.abc.avm2;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.DottedChain;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Code statistics.
 *
 * @author JPEXS
 */
public class CodeStats {

    /**
     * Maximum stack size
     */
    public int maxstack = 0;

    /**
     * Maximum scope size
     */
    public int maxscope = 0;

    /**
     * Number of local registers
     */
    public int maxlocal = 1;

    /**
     * Initial scope size
     */
    public int initscope = 0;

    /**
     * Has set_dxns instruction
     */
    public boolean has_set_dxns = false;

    /**
     * Has activation instruction
     */
    public boolean has_activation = false;

    /**
     * Instruction statistics
     */
    public InstructionStats[] instructionStats;

    /**
     * Converts statistics to string.
     *
     * @param writer Writer
     * @param abc ABC
     * @param fullyQualifiedNames Fully qualified names
     * @return Writer
     */
    public GraphTextWriter toString(GraphTextWriter writer, ABC abc, List<DottedChain> fullyQualifiedNames) {
        writer.appendNoHilight("Stats: maxstack=" + maxstack + ", maxscope=" + maxscope + ", maxlocal=" + maxlocal).newLine();
        int i = 0;
        int ms = 0;
        for (InstructionStats stats : instructionStats) {
            int deltastack = stats.ins.definition.getStackDelta(stats.ins, abc);
            if (stats.stackpos > ms) {
                ms = stats.stackpos;
            }
            writer.appendNoHilight(i + ":" + stats.stackpos + (deltastack >= 0 ? "+" + deltastack : deltastack) + "," + stats.scopepos + "    " + stats.ins.toString(writer, LocalData.create(new ArrayList<>(), null, abc, null, fullyQualifiedNames, new HashSet<>(), ScriptExportMode.AS, -1, new LinkedHashSet<>()))).newLine();
            i++;
        }
        return writer;
    }

    /**
     * Constructs code statistics.
     */
    public CodeStats() {
    }

    /**
     * Constructs code statistics.
     *
     * @param code AVM2 code
     */
    public CodeStats(AVM2Code code) {
        instructionStats = new InstructionStats[code.code.size()];
        for (int i = 0; i < code.code.size(); i++) {
            instructionStats[i] = new InstructionStats(code.code.get(i));
        }
    }
}
