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

import com.jpexs.decompiler.graph.Block;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetDialect;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.Loop;
import java.util.Arrays;
import java.util.List;

/**
 * Universal loop.
 *
 * @author JPEXS
 */
public class UniversalLoopItem extends WhileItem implements Block {

    /**
     * Constructor.
     *
     * @param dialect Dialect
     * @param src Source
     * @param lineStartIns Line start instruction
     * @param loop Loop
     * @param commands Commands
     */
    public UniversalLoopItem(GraphTargetDialect dialect, GraphSourceItem src, GraphSourceItem lineStartIns, Loop loop, List<GraphTargetItem> commands) {
        super(dialect, src, lineStartIns, loop, Arrays.asList(new TrueItem(dialect, src, lineStartIns)), commands);
    }
}
