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
package com.jpexs.decompiler.flash.tags;

import com.jpexs.decompiler.flash.DisassemblyListener;
import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.SWFInputStream;
import com.jpexs.decompiler.flash.SWFOutputStream;
import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.ActionList;
import com.jpexs.decompiler.flash.action.ActionTreeOperation;
import com.jpexs.decompiler.flash.action.ConstantPoolTooBigException;
import com.jpexs.decompiler.flash.dumpview.DumpInfoSpecialType;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.flash.tags.base.ASMSource;
import com.jpexs.decompiler.flash.types.annotations.HideInRawEdit;
import com.jpexs.decompiler.flash.types.annotations.Internal;
import com.jpexs.decompiler.flash.types.annotations.SWFVersion;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.helpers.ByteArrayRange;
import com.jpexs.helpers.Helper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * DoAction tag - Instructs Flash Player to perform a list of actions when the
 * current frame is complete.
 *
 * @author JPEXS
 */
@SWFVersion(from = 1)
public class DoActionTag extends Tag implements ASMSource {

    public static final int ID = 12;

    public static final String NAME = "DoAction";

    /**
     * List of actions to perform
     */
    @HideInRawEdit
    public ByteArrayRange actionBytes;

    @Internal
    private String scriptName = "-";

    @Internal
    private String exportedScriptName = "-";

    @Override
    public String getScriptName() {
        return scriptName;
    }

    /**
     * Constructor
     *
     * @param swf SWF
     */
    public DoActionTag(SWF swf) {
        super(swf, ID, NAME, null);
        actionBytes = ByteArrayRange.EMPTY;
    }

    /**
     * Constructor
     *
     * @param swf SWF
     * @param data Data
     */
    public DoActionTag(SWF swf, ByteArrayRange data) {
        super(swf, ID, NAME, data);
        actionBytes = ByteArrayRange.EMPTY;
    }

    /**
     * Constructor
     *
     * @param sis SWF input stream
     * @param data Data
     * @throws IOException On I/O error
     */
    public DoActionTag(SWFInputStream sis, ByteArrayRange data) throws IOException {
        super(sis.getSwf(), ID, NAME, data);
        readData(sis, data, 0, false, false, false);
    }

    @Override
    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    public final void readData(SWFInputStream sis, ByteArrayRange data, int level, boolean parallel, boolean skipUnusualTags, boolean lazy) throws IOException {
        actionBytes = sis.readByteRangeEx(sis.available(), "actionBytes", DumpInfoSpecialType.ACTION_BYTES, sis.getPos());
    }

    /**
     * Gets data bytes
     *
     * @param sos SWF output stream
     * @throws IOException On I/O error
     */
    @Override
    public void getData(SWFOutputStream sos) throws IOException {
        sos.write(getActionBytes());
    }

    /**
     * Converts actions to ASM source
     *
     * @param exportMode PCode or hex?
     * @param writer Writer
     * @param actions Actions
     * @return ASM source
     * @throws InterruptedException On interrupt
     */
    @Override
    public GraphTextWriter getASMSource(ScriptExportMode exportMode, GraphTextWriter writer, ActionList actions) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToString(listeners, 0, actions, swf.version, exportMode, writer);
    }

    @Override
    public GraphTextWriter getActionScriptSource(GraphTextWriter writer, ActionList actions) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToSource(new HashMap<>(), this, actions, getScriptName(), writer, getCharset());
    }

    @Override
    public GraphTextWriter getActionScriptSource(GraphTextWriter writer, ActionList actions, List<ActionTreeOperation> treeOperations) throws InterruptedException {
        if (actions == null) {
            actions = getActions();
        }

        return Action.actionsToSource(new HashMap<>(), this, actions, getScriptName(), writer, getCharset(), treeOperations);
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

    @Override
    public ActionList getActions() throws InterruptedException {
        return SWF.getCachedActionList(this, listeners);
    }

    @Override
    public void setActions(List<Action> actions) {
        actionBytes = Action.actionsToByteArrayRange(actions, true, swf.version);
    }

    @Override
    public ByteArrayRange getActionBytes() {
        return actionBytes;
    }

    @Override
    public void setActionBytes(byte[] actionBytes) {
        this.actionBytes = new ByteArrayRange(actionBytes);
        SWF.uncache(this);
    }

    @Override
    public void setConstantPools(List<List<String>> constantPools) throws ConstantPoolTooBigException {
        Action.setConstantPools(this, constantPools, false);
    }

    @Override
    public void setModified() {
        setModified(true);
    }

    @Override
    public GraphTextWriter getActionBytesAsHex(GraphTextWriter writer) {
        return Helper.byteArrayToHexWithHeader(writer, actionBytes.getRangeData());
    }

    List<DisassemblyListener> listeners = new ArrayList<>();

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
        return this;
    }

    @Override
    public void setSourceTag(Tag t) {
        //nothing
    }

    @Override
    public Tag getTag() {
        return null; //?
    }

    @Override
    public List<GraphTargetItem> getActionsToTree() {
        try {
            return Action.actionsToTree(new LinkedHashSet<>(), false, new HashMap<>(), false, false, getActions(), swf.version, 0, getScriptName(), swf.getCharset());
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
