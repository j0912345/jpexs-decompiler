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
package com.jpexs.decompiler.flash.abc.avm2.deobfuscation;

import com.jpexs.decompiler.flash.BaseLocalData;
import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.AVM2LocalData;
import com.jpexs.decompiler.flash.abc.avm2.AVM2Code;
import com.jpexs.decompiler.flash.abc.avm2.instructions.AVM2Instruction;
import com.jpexs.decompiler.flash.abc.avm2.instructions.InstructionDefinition;
import com.jpexs.decompiler.flash.abc.avm2.instructions.debug.DebugIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.jumps.JumpIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.localregs.GetLocalTypeIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.localregs.KillIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.localregs.SetLocalTypeIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.other.ReturnValueIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.other.ReturnVoidIns;
import com.jpexs.decompiler.flash.abc.avm2.instructions.other.ThrowIns;
import com.jpexs.decompiler.flash.abc.avm2.model.NullAVM2Item;
import com.jpexs.decompiler.flash.abc.avm2.model.clauses.ExceptionAVM2Item;
import com.jpexs.decompiler.flash.abc.types.ABCException;
import com.jpexs.decompiler.flash.abc.types.MethodBody;
import com.jpexs.decompiler.flash.abc.types.traits.Trait;
import com.jpexs.decompiler.graph.Graph;
import com.jpexs.decompiler.graph.GraphPart;
import com.jpexs.decompiler.graph.GraphSource;
import com.jpexs.decompiler.graph.GraphSourceItem;
import com.jpexs.decompiler.graph.GraphTargetItem;
import com.jpexs.decompiler.graph.TranslateException;
import com.jpexs.decompiler.graph.TranslateStack;
import com.jpexs.helpers.CancellableWorker;
import com.jpexs.helpers.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AVM2 Deobfuscator removing single assigned local registers.
 * <p>
 * Example: var a = true; var b = false; ... if(a){ ...ok }else{ not executed }
 *
 * @author JPEXS
 */
public class AVM2DeobfuscatorRegistersOld extends AVM2DeobfuscatorSimpleOld {


    /**
     * Constructor.
     */
    public AVM2DeobfuscatorRegistersOld() {

    }

    /**
     * Gets all register ids.
     *
     * @param code AVM2 code
     * @return Set of register ids
     */
    private Set<Integer> getRegisters(AVM2Code code) {
        Set<Integer> regs = new HashSet<>();
        for (AVM2Instruction ins : code.code) {
            InstructionDefinition def = ins.definition;
            if (def instanceof SetLocalTypeIns) {
                int regId = ((SetLocalTypeIns) def).getRegisterId(ins);
                regs.add(regId);
            } else if (def instanceof GetLocalTypeIns) {
                int regId = ((GetLocalTypeIns) def).getRegisterId(ins);
                regs.add(regId);
            } else {
                for (int p = 0; p < ins.definition.operands.length; p++) {
                    int op = ins.definition.operands[p];
                    if (op == AVM2Code.DAT_LOCAL_REG_INDEX) {
                        int regId = ins.operands[p];
                        regs.add(regId);
                    }
                }
            }
        }

        return regs;
    }

    /**
     * Removes single assigned local registers.
     *
     * @param path Path
     * @param classIndex Class index
     * @param isStatic Is static
     * @param scriptIndex Script index
     * @param abc ABC
     * @param trait Trait
     * @param methodInfo Method info
     * @param body Method body
     * @throws InterruptedException On interrupt
     */
    @Override
    public void avm2CodeRemoveTraps(String path, int classIndex, boolean isStatic, int scriptIndex, ABC abc, Trait trait, int methodInfo, MethodBody body) throws InterruptedException {
        //System.err.println("regdeo:" + path);

        MethodBody originalBody = body;
        body.getCode().removeDeadCode(body);
        Set<Integer> ignoredRegs = new HashSet<>();

        int localReservedCount = body.getLocalReservedCount();
        for (int i = 0; i < localReservedCount; i++) {
            ignoredRegs.add(i);
        }

        int setReg = 0;
        List<Integer> listedRegs = new ArrayList<>();
        List<MethodBody> listedLastBodies = new ArrayList<>();
        Set<Integer> ignoredRegGets = new HashSet<>();
        Reference<AVM2Instruction> assignmentRef = new Reference<>(null);

        while (setReg > -1) {
            if (CancellableWorker.isInterrupted()) {
                throw new InterruptedException();
            }

            MethodBody bodybefore = body;
            body = bodybefore.clone();
            setReg = getFirstRegisterSetter(assignmentRef, classIndex, isStatic, scriptIndex, abc, body, ignoredRegs, ignoredRegGets);
            //System.err.println("setreg " + setReg + " ass:" + assignmentRef.getVal());
            if (setReg < 0) {
                break;
            }

            // if there is second assignment
            if (listedRegs.contains(setReg)) {
                //System.err.println("second assignment of loc" + setReg + ", ignoring");
                int lindex = listedRegs.indexOf(setReg);
                body = listedLastBodies.get(lindex); // switch to body before
                ignoredRegs.add(setReg); // this is not obfuscated
                for (int i = listedRegs.size() - 1; i >= lindex; i--) {
                    int r = listedRegs.get(i);
                    listedRegs.remove(i);
                    listedLastBodies.remove(i);
                    ignoredRegGets.remove(r);
                }
                continue;
            }

            AVM2Instruction assignment = assignmentRef.getVal();
            InstructionDefinition def = assignment.definition;
            if ((def instanceof SetLocalTypeIns) || (def instanceof GetLocalTypeIns /*First usage -> value undefined*/)) {
                super.removeObfuscationIfs(classIndex, isStatic, scriptIndex, abc, body, Arrays.asList(assignment));
            }

            if (def instanceof GetLocalTypeIns) {
                ignoredRegGets.add(setReg);
            }

            listedRegs.add(setReg);
            listedLastBodies.add(bodybefore);
        }

        body.getCode().removeDeadCode(body);

        originalBody.exceptions = body.exceptions;
        originalBody.setCode(body.getCode());
    }

    /**
     * Gets first register with setter.
     *
     * @param assignment Assignment
     * @param classIndex Class index
     * @param isStatic Is static
     * @param scriptIndex Script index
     * @param abc ABC
     * @param body Method body
     * @param ignoredRegisters Ignored registers
     * @param ignoredGets Ignored gets
     * @return Register id
     * @throws InterruptedException On interrupt
     */
    private int getFirstRegisterSetter(Reference<AVM2Instruction> assignment, int classIndex, boolean isStatic, int scriptIndex, ABC abc, MethodBody body, Set<Integer> ignoredRegisters, Set<Integer> ignoredGets) throws InterruptedException {
        AVM2Code code = body.getCode();

        if (code.code.isEmpty()) {
            return -1;
        }

        Set<Integer> visited = new HashSet<>();
        return visitCode(assignment, visited, new TranslateStack("deo"), classIndex, isStatic, body, scriptIndex, abc, code, 0, code.code.size() - 1, ignoredRegisters, ignoredGets);
    }

    /**
     * Exception target ip pair.
     */
    private class ExceptionTargetIpPair {

        /**
         * Exception.
         */
        private final ABCException exception;
        /**
         * Target ip.
         */
        private final int targetIp;

        /**
         * Constructs a new instance of ExceptionTargetIpPair.
         *
         * @param exception Exception
         * @param targetIp Target ip
         */
        public ExceptionTargetIpPair(ABCException exception, int targetIp) {
            this.exception = exception;
            this.targetIp = targetIp;
        }

        /**
         * Gets exception.
         *
         * @return Exception
         */
        public ABCException getException() {
            return exception;
        }

        /**
         * Gets target ip.
         *
         * @return Target ip
         */
        public int getTargetIp() {
            return targetIp;
        }

    }

    /**
     * Visits code.
     *
     * @param assignment Assignment
     * @param visited Visited
     * @param stack Stack
     * @param classIndex Class index
     * @param isStatic Is static
     * @param body Method body
     * @param scriptIndex Script index
     * @param abc ABC
     * @param code AVM2 code
     * @param idx Index
     * @param endIdx End index
     * @param ignored Ignored
     * @param ignoredGets Ignored gets
     * @return Register id
     * @throws InterruptedException On interrupt
     */
    private int visitCode(Reference<AVM2Instruction> assignment, Set<Integer> visited, TranslateStack stack, int classIndex, boolean isStatic, MethodBody body, int scriptIndex, ABC abc, AVM2Code code, int idx, int endIdx, Set<Integer> ignored, Set<Integer> ignoredGets) throws InterruptedException {

        Map<Integer, List<ExceptionTargetIpPair>> exceptionStartToTargets = new HashMap<>();
        for (ABCException ex : body.exceptions) {
            int startIp = code.adr2pos(ex.start, true);
            int targetIp = code.adr2pos(ex.target);
            if (!exceptionStartToTargets.containsKey(startIp)) {
                exceptionStartToTargets.put(startIp, new ArrayList<>());
            }
            exceptionStartToTargets.get(startIp).add(new ExceptionTargetIpPair(ex, targetIp));
        }

        List<GraphTargetItem> output = new ArrayList<>();
        AVM2LocalData localData = newLocalData(scriptIndex, abc, abc.constants, body, isStatic, classIndex);
        initLocalRegs(localData, body.getLocalReservedCount(), body.max_regs);
        localData.localRegs.put(0, new NullAVM2Item(null, null)); // this

        List<Integer> toVisit = new ArrayList<>();
        toVisit.add(idx);
        List<TranslateStack> toVisitStacks = new ArrayList<>();
        toVisitStacks.add(stack);
        outer:
        while (!toVisit.isEmpty()) {
            if (CancellableWorker.isInterrupted()) {
                throw new InterruptedException();
            }

            idx = toVisit.remove(0);
            stack = toVisitStacks.remove(0);

            while (true) {
                if (idx > endIdx) {
                    break;
                }
                if (visited.contains(idx)) {
                    break;
                }
                visited.add(idx);

                if (exceptionStartToTargets.containsKey(idx)) {
                    for (ExceptionTargetIpPair pair : exceptionStartToTargets.get(idx)) {
                        toVisit.add(pair.targetIp);
                        TranslateStack targetStack = (TranslateStack) stack.clone();
                        targetStack.push(new ExceptionAVM2Item(pair.exception));
                        toVisitStacks.add(targetStack);
                    }
                }

                AVM2Instruction ins = code.code.get(idx);
                InstructionDefinition def = ins.definition;
                //System.err.println("" + idx + ": " + ins + " stack:" + stack.size());

                // do not throw EmptyStackException, much faster
                int requiredStackSize = ins.getStackPopCount(localData);
                if (stack.size() < requiredStackSize) {
                    continue outer;
                }

                ins.translate(localData, stack, output, 0, "");
                if (def instanceof SetLocalTypeIns) {
                    int regId = ((SetLocalTypeIns) def).getRegisterId(ins);
                    if (!ignored.contains(regId)) {
                        assignment.setVal(ins);
                        return regId;
                    }
                } else if (def instanceof GetLocalTypeIns) {
                    int regId = ((GetLocalTypeIns) def).getRegisterId(ins);
                    if (!ignored.contains(regId) && !ignoredGets.contains(regId)) {
                        assignment.setVal(ins);
                        return regId;
                    }
                } else if (!(def instanceof KillIns) && !(def instanceof DebugIns)) {
                    //can be inclocal, declocal, hasnext...
                    for (int p = 0; p < ins.definition.operands.length; p++) {
                        int op = ins.definition.operands[p];
                        if (op == AVM2Code.DAT_LOCAL_REG_INDEX) {
                            int regId = ins.operands[p];
                            if (!ignored.contains(regId)) {
                                assignment.setVal(ins);
                                return regId;
                            }
                        }
                    }
                }

                idx++;

                if (ins.definition instanceof JumpIns) {

                    long address = ins.getTargetAddress();
                    idx = code.adr2pos(address);
                    if (idx == -1) {
                        throw new TranslateException("Jump target not found: " + address);
                    }
                }

                if (ins.isBranch()) {
                    List<Integer> branches = ins.getBranches(new GraphSource() {

                        @Override
                        public int size() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public GraphSourceItem get(int pos) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public boolean isEmpty() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public void translatePart(List<GraphTargetItem> output, Graph graph, GraphPart part, BaseLocalData localData, TranslateStack stack, int start, int end, int staticOperation, String path) throws InterruptedException {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public int adr2pos(long adr) {
                            return code.adr2pos(adr);
                        }

                        @Override
                        public int adr2pos(long adr, boolean nearest) {
                            return code.adr2pos(adr, nearest);
                        }

                        @Override
                        public long pos2adr(int pos) {
                            return code.pos2adr(pos);
                        }

                        @Override
                        public Set<Long> getImportantAddresses() {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }

                        @Override
                        public String insToString(int pos) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    idx = branches.get(0);
                    for (int n = 1; n < branches.size(); n++) {
                        //visitCode(visited, (TranslateStack) stack.clone(), classIndex, isStatic, body, scriptIndex, abc, code, branches.get(n), endIdx, result);
                        int nidx = branches.get(n);
                        if (visited.contains(nidx)) {
                            continue;
                        }
                        toVisit.add(nidx);
                        toVisitStacks.add((TranslateStack) stack.clone());
                    }
                }
                /*if (ins.definition instanceof IfTypeIns) {
                 long address = ins.offset + ins.getBytes().length + ins.operands[0];
                 int newIdx = code.adr2pos(address);
                 if (newIdx == -1) {
                 throw new TranslateException("If target not found: " + address);
                 }
                 visitCode(visited, (TranslateStack) stack.clone(), classIndex, isStatic, body, scriptIndex, abc, code, newIdx, endIdx, result);
                 }*/

                if (ins.definition instanceof ReturnValueIns) {
                    break;
                }

                if (ins.definition instanceof ThrowIns) {
                    break;
                }

                if (ins.definition instanceof ReturnVoidIns) {
                    break;
                }
            }
        }
        return -1;
    }
}
