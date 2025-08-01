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
package com.jpexs.decompiler.flash.tags.base;

import com.jpexs.decompiler.flash.DisassemblyListener;
import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.ActionList;
import com.jpexs.decompiler.flash.action.ActionTreeOperation;
import com.jpexs.decompiler.flash.action.ConstantPoolTooBigException;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.flash.tags.DefineButtonTag;
import com.jpexs.decompiler.flash.tags.Tag;
import com.jpexs.decompiler.flash.treeitems.Openable;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.helpers.ByteArrayRange;
import com.jpexs.helpers.Helper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Button action.
 *
 * @author JPEXS
 */
public class ButtonAction implements ASMSource {

    List<DisassemblyListener> listeners = new ArrayList<>();

    private String scriptName = "-";

    private String exportedScriptName = "-";

    private final DefineButtonTag buttonTag;

    /**
     * Constructor.
     *
     * @param buttonTag Button tag
     */
    public ButtonAction(DefineButtonTag buttonTag) {
        this.buttonTag = buttonTag;
    }

    @Override
    public GraphTextWriter getASMSource(ScriptExportMode exportMode, GraphTextWriter writer, ActionList actions) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToString(listeners, 0, actions, getSwf().version, exportMode, writer);
    }

    @Override
    public GraphTextWriter getActionScriptSource(GraphTextWriter writer, ActionList actions) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToSource(new HashMap<>(), this, actions, getScriptName(), writer, buttonTag.getCharset());
    }

    @Override
    public GraphTextWriter getActionScriptSource(GraphTextWriter writer, ActionList actions, List<ActionTreeOperation> treeOperations) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToSource(new HashMap<>(), this, actions, getScriptName(), writer, buttonTag.getCharset(), treeOperations);
    }

    /**
     * Whether or not this object contains ASM source
     *
     * @return True when contains
     */
    @Override
    public boolean containsSource() {
        return true;
    }

    /**
     * Returns actions associated with this object
     *
     * @return List of actions
     * @throws InterruptedException On interrupt
     */
    @Override
    public ActionList getActions() throws InterruptedException {
        return SWF.getCachedActionList(this, listeners);
    }

    @Override
    public void setActions(List<Action> actions) {
        buttonTag.setActions(actions);
    }

    @Override
    public void setModified() {
        buttonTag.setModified();
    }

    @Override
    public ByteArrayRange getActionBytes() {
        return buttonTag.getActionBytes();
    }

    @Override
    public void setActionBytes(byte[] actionBytes) {
        buttonTag.setActionBytes(actionBytes);
        SWF.uncache(this);
    }

    @Override
    public void setConstantPools(List<List<String>> constantPools) throws ConstantPoolTooBigException {
        Action.setConstantPools(this, constantPools, false);
    }

    @Override
    public GraphTextWriter getActionBytesAsHex(GraphTextWriter writer) {
        return Helper.byteArrayToHexWithHeader(writer, buttonTag.getActionBytes().getRangeData());
    }

    @Override
    public void addDisassemblyListener(DisassemblyListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDisassemblyListener(DisassemblyListener listener) {
        listeners.remove(listener);
    }

    @Override
    public GraphTextWriter getActionSourcePrefix(GraphTextWriter writer) {
        return writer;
    }

    @Override
    public GraphTextWriter getActionSourceSuffix(GraphTextWriter writer) {
        return writer;
    }

    @Override
    public int getPrefixLineCount() {
        return 0;
    }

    @Override
    public String removePrefixAndSuffix(String source) {
        return source;
    }

    @Override
    public Tag getSourceTag() {
        return buttonTag;
    }

    @Override
    public void setSourceTag(Tag t) {
    }

    @Override
    public String getScriptName() {
        return scriptName;
    }

    @Override
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    public String getExportFileName() {
        return buttonTag.getExportFileName();
    }

    @Override
    public Openable getOpenable() {
        return buttonTag.getOpenable();
    }

    @Override
    public boolean isModified() {
        return buttonTag.isModified();
    }

    @Override
    public String toString() {
        return buttonTag.toString();
    }

    @Override
    public Tag getTag() {
        return buttonTag;
    }

    @Override
    public SWF getSwf() {
        return (SWF) getOpenable();
    }

    @Override
    public List<GraphTargetItem> getActionsToTree() {
        try {
            return Action.actionsToTree(new LinkedHashSet<>(), false, new HashMap<>(), false, false, getActions(), buttonTag.getSwf().version, 0, getScriptName(), buttonTag.getSwf().getCharset());
        } catch (InterruptedException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public String getExportedScriptName() {
        return exportedScriptName;
    }

    @Override
    public void setExportedScriptName(String scriptName) {
        this.exportedScriptName = scriptName;
    }
}
