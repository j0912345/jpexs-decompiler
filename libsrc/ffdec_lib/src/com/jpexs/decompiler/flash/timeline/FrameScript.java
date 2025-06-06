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
package com.jpexs.decompiler.flash.timeline;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.tags.DoActionTag;
import com.jpexs.decompiler.flash.tags.base.Exportable;
import com.jpexs.decompiler.flash.treeitems.Openable;
import com.jpexs.decompiler.flash.treeitems.TreeItem;

/**
 * A frame that contains script
 *
 * @author JPEXS
 */
public class FrameScript implements TreeItem, Exportable {

    /**
     * SWF.
     */
    private final SWF swf;

    /**
     * Frame.
     */
    private final Frame frame;

    /**
     * Constructs FrameScript.
     *
     * @param swf SWF
     * @param frame Frame
     */
    public FrameScript(SWF swf, Frame frame) {
        this.swf = swf;
        this.frame = frame;
    }

    /**
     * Gets Frame.
     *
     * @return Frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Gets openable.
     *
     * @return Openable
     */
    @Override
    public Openable getOpenable() {
        return swf;
    }

    @Override
    public String toString() {
        if (getSingleDoActionTag() != null) {
            return frame.toString() + " - " + "DoAction";
        }
        return frame.toString();
    }
    
    public DoActionTag getSingleDoActionTag() {
        if (!frame.actionContainers.isEmpty()) {
            return null;
        }
        if (frame.actions.size() != 1) {
            return null;
        }
        
        return frame.actions.get(0);                
    }

    /**
     * Gets export filename.
     *
     * @return Export filename
     */
    @Override
    public String getExportFileName() {
        return frame.getExportFileName();
    }

    /**
     * Gets modified flag.
     *
     * @return Modified flag
     */
    @Override
    public boolean isModified() {
        return frame.isModified();
    }
}
