/*
 *  Copyright (C) 2010-2024 JPEXS, All rights reserved.
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
package com.jpexs.decompiler.flash.importers;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.ScriptPack;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.exporters.settings.ScriptExportSettings;
import com.jpexs.decompiler.flash.treeitems.Openable;
import com.jpexs.decompiler.graph.DottedChain;
import com.jpexs.helpers.CancellableWorker;
import com.jpexs.helpers.Helper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ActionScript 3 scripts importer.
 *
 * @author JPEXS
 */
public class AS3ScriptImporter {

    private static final Logger logger = Logger.getLogger(AS3ScriptImporter.class.getName());

    /**
     * Constructor.
     */
    public AS3ScriptImporter() {

    }

    /**
     * Imports scripts from a folder.
     * @param scriptReplacer Replacer for the scripts
     * @param scriptsFolder Folder with scripts
     * @param packs List of script packs
     * @param dependencies List of dependencies
     * @return Number of imported scripts
     * @throws InterruptedException On interrupt
     */
    public int importScripts(As3ScriptReplacerInterface scriptReplacer, String scriptsFolder, List<ScriptPack> packs, List<SWF> dependencies) throws InterruptedException {
        return importScripts(scriptReplacer, scriptsFolder, packs, null, dependencies);
    }
    
    /**
     * Imports scripts from a folder.
     * @param scriptReplacer Replacer for the scripts
     * @param scriptsFolder Folder with scripts
     * @param packs List of script packs
     * @param listener Listener for progress
     * @param dependencies List of dependencies
     * @return Number of imported scripts
     * @throws InterruptedException On interrupt
     */
    public int importScripts(As3ScriptReplacerInterface scriptReplacer, String scriptsFolder, List<ScriptPack> packs, ScriptImporterProgressListener listener, List<SWF> dependencies) throws InterruptedException {
        if (!scriptsFolder.endsWith(File.separator)) {
            scriptsFolder += File.separator;
        }
        
        ArrayList<File> newFiles = new ArrayList<>();
        
        ArrayList<File> allFiles = new ArrayList<>();
        File currentSearchingFolder = new File(scriptsFolder);
        ArrayList<File> unsearchedFolders = new ArrayList<>();
        int searchIterations = 0;
        // TODO: maxSearchIterations should probably be a setting.
        // TODO: raise this number higher than 200
        int maxSearchIterations = 200;
        for(searchIterations = 0; searchIterations < maxSearchIterations; searchIterations++)
        {
            for(File file : currentSearchingFolder.listFiles())
            {
                // The GUI logger display only uses the WARNING level
                logger.log(Level.WARNING, "\ncurrent file: %filepath%".replace("%filepath%", file.getAbsolutePath()) + 
                        "\ncurrentSearchingFolder: %i%".replace("%i%", currentSearchingFolder.getAbsolutePath()) +
                        "\nunsearchedFolders: %i%".replace("%i%", unsearchedFolders.toString()));
                if(file.isFile() && file.getAbsolutePath().endsWith(".as"))
                {
                    allFiles.add(file);
                }
                if(file.isDirectory())
                {
                    unsearchedFolders.add(file);
                }
            }
            if(unsearchedFolders.isEmpty())
            {
                break;
            }
            else
            {
                currentSearchingFolder = unsearchedFolders.remove(0);
            }
        }
        if(searchIterations >= maxSearchIterations)
        {
            logger.log(Level.SEVERE,
                    "Exhausted %i% iterations while trying to recursively search a directory for importing scripts.".replace("%i%", String.valueOf(searchIterations))
                    + "\nAny previously non-existent scripts not encountered yet will not be created and imported.");
        }
        
        
        for(int i = 0; i < allFiles.size(); i++)
        {
            File curFile = allFiles.get(i);
            String fileRelativePath = curFile.getAbsolutePath().substring(scriptsFolder.length());
            fileRelativePath = fileRelativePath.split("\\.as")[0];
            fileRelativePath = fileRelativePath.replace("\\/", ".");
            if(packs.get(0).abc.findScriptPacksByPath(fileRelativePath, packs.get(0).allABCs).isEmpty())
            {
                newFiles.add(curFile);
            }
        }
        // CHECK WHAT FILES WERE MARKED AS NEW ONLY FOR DEBUGGING
        String newFilesLogMessage = "";
        for(int i = 0; i < newFiles.size(); i++)
        {
            newFilesLogMessage += "%name%, ".replace("%name%", newFiles.get(i).getName());
        }
        logger.log(Level.WARNING, "\n\n===========================\n\n" + newFilesLogMessage);
        
        int importCount = 0;
        for (ScriptPack pack : packs) {
            if (CancellableWorker.isInterrupted()) {
                return importCount;
            }
            if (!pack.isSimple) {
                continue;
            }
            try {
                File file = pack.getExportFile(scriptsFolder, new ScriptExportSettings(ScriptExportMode.AS, false, false, false, false, true));
                if (file.exists()) {
                    // do we really need to Openable openable = pack.getOpenable(); and
                    // SWF swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf(); every loop?
                    Openable openable = pack.getOpenable();
                    SWF swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf();
                    swf.informListeners("importing_as", file.getAbsolutePath());
                    String fileName = file.getAbsolutePath();
                    String txt = Helper.readTextFile(fileName);

                    try {
                        pack.abc.replaceScriptPack(scriptReplacer, pack, txt, dependencies);
                    } catch (As3ScriptReplaceException asre) {
                        for (As3ScriptReplaceExceptionItem item : asre.getExceptionItems()) {
                            logger.log(Level.SEVERE, "%error% on line %line%, column %col%, file: %file%".replace("%error%", item.getMessage()).replace("%line%", Long.toString(item.getLine())).replace("%file%", fileName).replace("%col%", "" + item.getCol()));
                        }
                        if (listener != null) {
                            listener.scriptImportError();
                        }
                    } catch (InterruptedException ex) {
                        return importCount;
                    }

                    importCount++;
                    if (listener != null) {
                        listener.scriptImported();
                    }
                }
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        return importCount;
    }
}
