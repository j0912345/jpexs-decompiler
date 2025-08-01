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
package com.jpexs.decompiler.flash.action.model.clauses;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.SourceGeneratorLocalData;
import com.jpexs.decompiler.flash.action.Action;
import com.jpexs.decompiler.flash.action.model.ActionItem;
import com.jpexs.decompiler.flash.action.model.DirectValueActionItem;
import com.jpexs.decompiler.flash.action.parser.script.ActionSourceGenerator;
import com.jpexs.decompiler.flash.action.swf4.ActionIf;
import com.jpexs.decompiler.flash.action.swf4.ActionJump;
import com.jpexs.decompiler.flash.action.swf4.ActionPop;
import com.jpexs.decompiler.flash.action.swf4.ActionPush;
import com.jpexs.decompiler.flash.action.swf4.RegisterNumber;
import com.jpexs.decompiler.flash.action.swf5.ActionDefineLocal;
import com.jpexs.decompiler.flash.action.swf5.ActionEquals2;
import com.jpexs.decompiler.flash.action.swf5.ActionPushDuplicate;
import com.jpexs.decompiler.flash.action.swf5.ActionStackSwap;
import com.jpexs.decompiler.flash.action.swf7.ActionCastOp;
import com.jpexs.decompiler.flash.action.swf7.ActionThrow;
import com.jpexs.decompiler.flash.action.swf7.ActionTry;
import com.jpexs.decompiler.flash.ecma.Null;
import com.jpexs.decompiler.flash.helpers.GraphTextWriter;
import com.jpexs.decompiler.graph.Block;
import com.jpexs.decompiler.graph.CompilationException;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.GraphTargetVisitorInterface;
import com.jpexs.decompiler.graph.SourceGenerator;
import com.jpexs.decompiler.graph.model.BreakItem;
import com.jpexs.decompiler.graph.model.ContinueItem;
import com.jpexs.decompiler.graph.model.LocalData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Try statement.
 *
 * @author JPEXS
 */
public class TryActionItem extends ActionItem implements Block {

    /**
     * Try commands
     */
    public List<GraphTargetItem> tryCommands;

    /**
     * Catch exception names
     */
    public List<GraphTargetItem> catchExceptionNames;

    /**
     * Catch exception types
     */
    public List<GraphTargetItem> catchExceptionTypes;

    /**
     * Catch commands
     */
    public List<List<GraphTargetItem>> catchCommands;

    /**
     * Finally commands
     */
    public List<GraphTargetItem> finallyCommands;

    @Override
    public List<List<GraphTargetItem>> getSubs() {
        List<List<GraphTargetItem>> ret = new ArrayList<>();
        if (tryCommands != null) {
            ret.add(tryCommands);
        }
        ret.addAll(catchCommands);
        if (finallyCommands != null) {
            ret.add(finallyCommands);
        }
        return ret;
    }

    @Override
    public void visit(GraphTargetVisitorInterface visitor) {
        if (tryCommands != null) {
            visitor.visitAll(tryCommands);
        }
        for (List<GraphTargetItem> cc : catchCommands) {
            visitor.visitAll(cc);
        }
        if (finallyCommands != null) {
            visitor.visitAll(finallyCommands);
        }
    }

    @Override
    public void visitNoBlock(GraphTargetVisitorInterface visitor) {

    }

    /**
     * Constructor.
     *
     * @param tryCommands Try commands
     * @param catchExceptionNames Catch exception names
     * @param catchExceptionTypes Catch exception types
     * @param catchCommands Catch commands
     * @param finallyCommands Finally commands
     */
    public TryActionItem(List<GraphTargetItem> tryCommands, List<GraphTargetItem> catchExceptionNames, List<GraphTargetItem> catchExceptionTypes, List<List<GraphTargetItem>> catchCommands, List<GraphTargetItem> finallyCommands) {
        super(null, null, NOPRECEDENCE);
        this.tryCommands = tryCommands;
        this.catchExceptionNames = catchExceptionNames;
        this.catchExceptionTypes = catchExceptionTypes;
        this.catchCommands = catchCommands;
        this.finallyCommands = finallyCommands;
    }

    @Override
    public GraphTextWriter appendTo(GraphTextWriter writer, LocalData localData) throws InterruptedException {
        writer.append("try");
        appendBlock(null, writer, localData, tryCommands);
        for (int e = 0; e < catchExceptionNames.size(); e++) {
            writer.newLine();
            writer.append("catch");
            if (writer.getFormatting().spaceBeforeParenthesesCatchParentheses) {
                writer.append(" ");
            }
            writer.append("(");
            catchExceptionNames.get(e).toStringNoQuotes(writer, localData);
            if (catchExceptionTypes.get(e) != null) {
                writer.append(":");
                catchExceptionTypes.get(e).toStringNoQuotes(writer, localData);
            }
            writer.append(")");
            List<GraphTargetItem> commands = catchCommands.get(e);
            appendBlock(null, writer, localData, commands);
        }
        if (catchExceptionNames.isEmpty() || finallyCommands.size() > 0) {
            writer.newLine();
            writer.append("finally");
            appendBlock(null, writer, localData, finallyCommands);
        }
        return writer;
    }

    @Override
    public List<ContinueItem> getContinues() {
        List<ContinueItem> ret = new ArrayList<>();
        for (GraphTargetItem ti : tryCommands) {
            if (ti instanceof ContinueItem) {
                ret.add((ContinueItem) ti);
            }
            if (ti instanceof Block) {
                ret.addAll(((Block) ti).getContinues());
            }
        }
        if (finallyCommands != null) {
            for (GraphTargetItem ti : finallyCommands) {
                if (ti instanceof ContinueItem) {
                    ret.add((ContinueItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getContinues());
                }
            }
        }
        for (List<GraphTargetItem> commands : catchCommands) {
            for (GraphTargetItem ti : commands) {
                if (ti instanceof ContinueItem) {
                    ret.add((ContinueItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getContinues());
                }
            }
        }
        return ret;
    }
   
    @Override
    public List<BreakItem> getBreaks() {
        List<BreakItem> ret = new ArrayList<>();
        for (GraphTargetItem ti : tryCommands) {
            if (ti instanceof ContinueItem) {
                ret.add((BreakItem) ti);
            }
            if (ti instanceof Block) {
                ret.addAll(((Block) ti).getBreaks());
            }
        }
        if (finallyCommands != null) {
            for (GraphTargetItem ti : finallyCommands) {
                if (ti instanceof BreakItem) {
                    ret.add((BreakItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getBreaks());
                }
            }
        }
        for (List<GraphTargetItem> commands : catchCommands) {
            for (GraphTargetItem ti : commands) {
                if (ti instanceof ContinueItem) {
                    ret.add((BreakItem) ti);
                }
                if (ti instanceof Block) {
                    ret.addAll(((Block) ti).getBreaks());
                }
            }
        }
        return ret;
    }

    @Override
    public boolean needsSemicolon() {
        return false;
    }

    @Override
    public List<GraphSourceItem> toSource(SourceGeneratorLocalData localData, SourceGenerator generator) throws CompilationException {
        List<GraphSourceItem> ret = new ArrayList<>();
        ActionSourceGenerator asGenerator = (ActionSourceGenerator) generator;
        String charset = asGenerator.getCharset();
        List<Action> tryCommandsA = asGenerator.toActionList(asGenerator.generate(localData, tryCommands));
        List<Action> finallyCommandsA = finallyCommands == null ? null : asGenerator.toActionList(asGenerator.generate(localData, finallyCommands));
        List<Action> catchCommandsA = null;
        String catchName = null;
        int catchSize = 0;
        int catchRegister = 0;
        boolean catchInRegisterFlag = false;
        if (!catchCommands.isEmpty()) {

            List<GraphSourceItem> fullCatchBody = new ArrayList<>();

            if (catchExceptionNames.size() == 1 && catchExceptionTypes.get(0) == null) { //catch everything without any type
                GraphTargetItem ename = catchExceptionNames.get(0);
                if (ename instanceof DirectValueActionItem) {
                    catchName = ((DirectValueActionItem) ename).getAsString();
                } else {
                    Logger.getLogger(TryActionItem.class.getName()).log(Level.SEVERE, "Invalid catchName, string expected");
                }
                catchInRegisterFlag = false;
                fullCatchBody = GraphTargetItem.toSourceMerge(localData, generator, catchCommands.get(0));
            } else {
                catchInRegisterFlag = true;
                catchRegister = asGenerator.getTempRegister(localData);
                boolean allCatched = false;
                for (int i = catchExceptionNames.size() - 1; i >= 0; i--) {
                    GraphTargetItem etype = catchExceptionTypes.get(i);
                    if (etype == null) {
                        allCatched = true;
                        break;
                    }
                }

                if (!allCatched) {
                    fullCatchBody.addAll(0, GraphTargetItem.toSourceMerge(localData, generator,
                            new ActionPush(new RegisterNumber(catchRegister), charset),
                            new ActionThrow()
                    ));
                }

                for (int i = catchExceptionNames.size() - 1; i >= 0; i--) {
                    GraphTargetItem ename = catchExceptionNames.get(i);
                    GraphTargetItem etype = catchExceptionTypes.get(i);
                    List<GraphTargetItem> ebody = catchCommands.get(i);
                    if (etype == null) {
                        fullCatchBody.addAll(0, GraphTargetItem.toSourceMerge(localData, generator,
                                new DirectValueActionItem(new RegisterNumber(catchRegister)),
                                ename,
                                new ActionStackSwap(charset),
                                new ActionDefineLocal(),
                                ebody
                        ));
                    } else {
                        List<GraphSourceItem> ifBody = GraphTargetItem.toSourceMerge(localData, generator,
                                ename,
                                new ActionStackSwap(charset),
                                new ActionDefineLocal(),
                                ebody);
                        fullCatchBody.add(0, new ActionPop());
                        int toFinishSize = Action.actionsToBytes(asGenerator.toActionList(fullCatchBody), false, SWF.DEFAULT_VERSION).length;
                        ActionJump finishJump = new ActionJump(toFinishSize, charset);
                        ifBody.add(finishJump);
                        List<Action> ifBodyA = asGenerator.toActionList(ifBody);
                        int ifBodySize = Action.actionsToBytes(ifBodyA, false, SWF.DEFAULT_VERSION).length;
                        fullCatchBody.addAll(0, ifBody);
                        fullCatchBody.addAll(0,
                                GraphTargetItem.toSourceMerge(localData, generator,
                                        etype,
                                        new ActionPush(new RegisterNumber(catchRegister), charset),
                                        new ActionCastOp(),
                                        new ActionPushDuplicate(charset),
                                        new ActionPush(Null.INSTANCE, charset),
                                        new ActionEquals2(),
                                        new ActionIf(ifBodySize, charset)
                                ));

                    }
                }
                asGenerator.releaseTempRegister(localData, catchRegister);
            }
            catchCommandsA = asGenerator.toActionList(fullCatchBody);
            catchSize = Action.actionsToBytes(catchCommandsA, false, SWF.DEFAULT_VERSION).length;
            tryCommandsA.add(new ActionJump(catchSize, charset));
        }
        int finallySize = 0;
        if (finallyCommandsA != null) {
            finallySize = Action.actionsToBytes(finallyCommandsA, false, SWF.DEFAULT_VERSION).length;
        }
        int trySize = Action.actionsToBytes(tryCommandsA, false, SWF.DEFAULT_VERSION).length;
        ret.add(new ActionTry(catchInRegisterFlag, finallyCommands != null, !catchCommands.isEmpty(), catchName, catchRegister, trySize, catchSize, finallySize, SWF.DEFAULT_VERSION, charset));
        ret.addAll(tryCommandsA);
        if (catchCommandsA != null) {
            ret.addAll(catchCommandsA);
        }
        if (finallyCommandsA != null) {
            ret.addAll(finallyCommandsA);
        }
        return ret;
    }

    @Override
    public boolean hasReturnValue() {
        return false;
    }
}
