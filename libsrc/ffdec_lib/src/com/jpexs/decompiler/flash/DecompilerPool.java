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
package com.jpexs.decompiler.flash;

import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.ScriptPack;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.AbcIndexing;
import com.jpexs.decompiler.flash.abc.types.ConvertData;
import com.jpexs.decompiler.flash.abc.types.ScriptInfo;
import com.jpexs.decompiler.flash.action.ActionList;
import com.jpexs.decompiler.flash.cache.ScriptDecompiledListener;
import com.jpexs.decompiler.flash.configuration.Configuration;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.helpers.HighlightedText;
import com.jpexs.decompiler.flash.helpers.HighlightedTextWriter;
import com.jpexs.decompiler.flash.tags.base.ASMSource;
import com.jpexs.decompiler.flash.treeitems.Openable;
import com.jpexs.helpers.CancellableWorker;
import com.jpexs.helpers.ImmediateFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Decompiler thread pool.
 *
 * @author JPEXS
 */
public class DecompilerPool {

    /**
     * Executor
     */
    private final ThreadPoolExecutor executor;

    /**
     * Openable to futures map
     */
    private Map<Openable, List<Future<HighlightedText>>> openableToFutures = new WeakHashMap<>();

    /**
     * Constructs a new DecompilerPool.
     */
    public DecompilerPool() {
        int threadCount = Configuration.getParallelThreadCount();
        executor = new ThreadPoolExecutor(threadCount, threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * Submits a task.
     *
     * @param src Source
     * @param actions Actions
     * @param listener Listener
     * @return Future
     */
    public Future<HighlightedText> submitTask(ASMSource src, ActionList actions, ScriptDecompiledListener<HighlightedText> listener) {
        CancellableWorker w = CancellableWorker.getCurrent();
        Callable<HighlightedText> callable = new Callable<HighlightedText>() {
            @Override
            public HighlightedText call() throws Exception {
                if (w != null) {
                    CancellableWorker.assignThreadToWorker(Thread.currentThread(), w);
                }                

                if (listener != null) {
                    listener.onStart();
                }
                
                HighlightedTextWriter writer = new HighlightedTextWriter(Configuration.getCodeFormatting(), true);
                writer.startFunction("!script");
                src.getActionScriptSource(writer, actions);
                writer.endFunction();

                writer.finishHilights();

                HighlightedText result = new HighlightedText(writer);
                SWF swf = src.getSwf();
                if (swf != null) {
                    swf.as2Cache.put(src, result);
                }

                if (listener != null) {
                    listener.onComplete(result);
                }

                return result;
            }
        };

        return submit(callable);
    }

    /**
     * Submits a task.
     *
     * @param abcIndex ABC index
     * @param pack Script pack
     * @param listener Listener
     * @return Future
     */
    public Future<HighlightedText> submitTask(AbcIndexing abcIndex, ScriptPack pack, ScriptDecompiledListener<HighlightedText> listener) {
        CancellableWorker w = CancellableWorker.getCurrent();
        Callable<HighlightedText> callable = new Callable<HighlightedText>() {
            @Override
            public HighlightedText call() throws Exception {
                if (w != null) {
                    CancellableWorker.assignThreadToWorker(Thread.currentThread(), w);
                }
                if (listener != null) {
                    listener.onStart();
                }

                int scriptIndex = pack.scriptIndex;
                ScriptInfo script = null;
                if (scriptIndex > -1) {
                    script = pack.abc.script_info.get(scriptIndex);
                }
                boolean parallel = Configuration.parallelSpeedUp.get();
                HighlightedTextWriter writer = new HighlightedTextWriter(Configuration.getCodeFormatting(), true);
                pack.toSource(abcIndex, writer, script == null ? null : script.traits.traits, new ConvertData(), ScriptExportMode.AS, parallel, false, false);

                writer.finishHilights();
                HighlightedText result = new HighlightedText(writer);
                Openable openable = pack.getOpenable();
                SWF swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf();
                if (swf != null) {
                    swf.as3Cache.put(pack, result);
                }

                if (listener != null) {
                    listener.onComplete(result);
                }

                return result;
            }
        };

        return submit(callable);
    }

    /**
     * Submits a task.
     *
     * @param callable Callable
     * @return Future
     */
    private Future<HighlightedText> submit(Callable<HighlightedText> callable) {
        boolean parallel = Configuration.parallelSpeedUp.get();
        if (parallel) {
            Future<HighlightedText> f = executor.submit(callable);
            return f;
        } else {
            boolean cancelled = false;
            Throwable throwable = null;
            HighlightedText result = null;
            try {
                result = callable.call();
            } catch (InterruptedException ex) {
                cancelled = true;
            } catch (Exception ex) {
                throwable = ex;
            }

            return new ImmediateFuture<>(result, throwable, cancelled);
        }
    }

    /**
     * Gets statistics.
     *
     * @return Statistics
     */
    public String getStat() {
        return "core: " + executor.getCorePoolSize()
                + " size: " + executor.getPoolSize()
                + " largest: " + executor.getLargestPoolSize()
                + " max: " + executor.getMaximumPoolSize()
                + " active: " + executor.getActiveCount()
                + " count: " + executor.getTaskCount()
                + " completed: " + executor.getCompletedTaskCount();
    }

    /**
     * Decompiles ASM source.
     *
     * @param src ASM source
     * @param actions Actions
     * @return Highlighted text
     * @throws InterruptedException On interrupt
     */
    public HighlightedText decompile(ASMSource src, ActionList actions) throws InterruptedException {
        Future<HighlightedText> future = submitTask(src, actions, null);
        SWF swf = src.getSwf();
        if (!openableToFutures.containsKey(swf)) {
            openableToFutures.put(swf, new ArrayList<>());
        }
        openableToFutures.get(swf).add(future);
        
        CancellableWorker w = CancellableWorker.getCurrent();
                        
        if (w != null) {
            if (w.isCancelled()) {
                throw new InterruptedException();
            }
            w.addCancelListener(new Runnable() {
                @Override
                public void run() {
                    w.removeCancelListener(this);
                    future.cancel(true);
                }                
            });
        }
        
        try {
            return future.get();
        } catch (InterruptedException ex) {            
            future.cancel(true);
            throw ex;
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof InterruptedException) {
                throw (InterruptedException) ex.getCause();
            }
            Logger.getLogger(DecompilerPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            List<Future<HighlightedText>> futures = openableToFutures.get(swf);
            if (futures != null) {
                futures.remove(future);
            }
        }

        return null;
    }

    /**
     * Decompiles a script pack.
     *
     * @param abcIndex ABC indexing
     * @param pack Script pack
     * @return Highlighted text
     * @throws InterruptedException On interrupt
     */
    public HighlightedText decompile(AbcIndexing abcIndex, ScriptPack pack) throws InterruptedException {
        Future<HighlightedText> future = submitTask(abcIndex, pack, null);

        Openable openable = pack.getOpenable();
        if (!openableToFutures.containsKey(openable)) {
            openableToFutures.put(openable, new ArrayList<>());
        }
        openableToFutures.get(openable).add(future);              
        try {
            return future.get();
        } catch (InterruptedException ex) {
            future.cancel(true);
            throw ex;
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof InterruptedException) {
                throw (InterruptedException) ex.getCause();
            }
            Logger.getLogger(DecompilerPool.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            List<Future<HighlightedText>> futures = openableToFutures.get(openable);
            if (futures != null) {
                futures.remove(future);
            }
        }

        return null;
    }

    /**
     * Shuts down the pool.
     *
     * @throws InterruptedException On interrupt
     */
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);
    }

    /**
     * Destroys a SWF.
     *
     * @param swf SWF
     */
    public void destroySwf(SWF swf) {
        List<Future<HighlightedText>> futures = openableToFutures.get(swf);
        if (futures != null) {
            for (Future<HighlightedText> future : futures) {
                if (future != null) {
                    future.cancel(true);
                }
            }
        }
    }
}
