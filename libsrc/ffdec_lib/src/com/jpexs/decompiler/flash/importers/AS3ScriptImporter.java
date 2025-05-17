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

import com.jpexs.decompiler.flash.IdentifiersDeobfuscation;
import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.abc.ABC;
import com.jpexs.decompiler.flash.abc.ScriptPack;
import com.jpexs.decompiler.flash.abc.avm2.parser.AVM2ParseException;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.AbcIndexing;
import com.jpexs.decompiler.flash.abc.avm2.parser.script.ActionScript3Parser;
import com.jpexs.decompiler.flash.exporters.modes.ScriptExportMode;
import com.jpexs.decompiler.flash.exporters.settings.ScriptExportSettings;
import com.jpexs.decompiler.flash.gui.tagtree.TagTreeContextMenu;
import com.jpexs.decompiler.flash.tags.ABCContainerTag;
import com.jpexs.decompiler.flash.tags.Tag;
import com.jpexs.decompiler.flash.treeitems.Openable;
import com.jpexs.decompiler.graph.CompilationException;
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
     * @param NewScriptABCContainer ABCContainerTag for new scripts to be put into. Ignores new scripts if null.
     * @return Number of imported scripts
     * @throws InterruptedException On interrupt
     */
    public int importScripts(As3ScriptReplacerInterface scriptReplacer, String scriptsFolder, List<ScriptPack> packs, List<SWF> dependencies, ABCContainerTag NewScriptABCContainer) throws InterruptedException {
        return importScripts(scriptReplacer, scriptsFolder, packs, null, dependencies, NewScriptABCContainer);
    }
    
    /**
     * Imports scripts from a folder.
     * @param scriptReplacer Replacer for the scripts
     * @param scriptsFolder Folder with scripts
     * @param packs List of script packs
     * @param listener Listener for progress
     * @param dependencies List of dependencies
     * @param NewScriptABCContainer ABCContainerTag for new scripts to be put into. Ignores new scripts if null.
     * @return Number of imported scripts
     * @throws InterruptedException On interrupt
     */
    public int importScripts(As3ScriptReplacerInterface scriptReplacer, String scriptsFolder, List<ScriptPack> packs, ScriptImporterProgressListener listener, List<SWF> dependencies, ABCContainerTag NewScriptABCContainer) throws InterruptedException {
        if (!scriptsFolder.endsWith(File.separator)) {
            scriptsFolder += File.separator;
        }
        
        Openable openable = packs.get(0).getOpenable();
        SWF swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf();
        
        if(NewScriptABCContainer != null){
            ArrayList<File> allFiles = recursivelySearchDirForScripts(scriptsFolder);
            ArrayList<String> newFileDotPaths = new ArrayList<>();
            
            if(!allFiles.isEmpty())
            {
                // the below functions are called because TagTreeContextMenu.addAs3ClassActionPerformed() does it.
                // these are in an if to avoid setting the same values every loop in addBlankScriptWithName().
                swf.clearAllCache();
                ((Tag) NewScriptABCContainer).setModified(true);
                swf.setModified(true);
            }

            for(int i = 0; i < allFiles.size(); i++)
            {
                File curFile = allFiles.get(i);
                // TODO: check how symlinks are handled.
                if(!curFile.getAbsolutePath().contains(scriptsFolder))
                {
                    logger.log(Level.WARNING, "Found %file% while recursively searching for new scripts, ".replace("%file%", curFile.getAbsolutePath())
                            + "which doesn't exist inside %folder%, the folder being imported.".replace("%folder%", scriptsFolder));
                    continue;
                }
                String fileRelativePath = curFile.getAbsolutePath().substring(scriptsFolder.length());
                fileRelativePath = fileRelativePath.substring(0, fileRelativePath.lastIndexOf("."));
                fileRelativePath = fileRelativePath.replace("/", ".").replace("\\", ".");
                // apparently paths with slashes also are detected correctly for this function?
                if(packs.get(0).abc.findScriptPacksByPath(fileRelativePath, packs.get(0).allABCs).isEmpty())
                {
                    // add a blank script for `curFile`
                    addBlankScriptWithName(fileRelativePath, NewScriptABCContainer, swf);
                    newFileDotPaths.add(fileRelativePath);
                }
            }
            
            // CHECK WHAT FILES WERE MARKED AS NEW ONLY FOR DEBUGGING
            String newFilesLogMessage = "";
            for(int i = 0; i < newFileDotPaths.size(); i++)
            {
                newFilesLogMessage += "%name%, ".replace("%name%", newFileDotPaths.get(i));
            }
            logger.log(Level.WARNING, "\n\n===========================\n\n" + newFilesLogMessage);
        }
        
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
                    // do we really need to openable = pack.getOpenable(); and
                    // swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf(); every loop?
                    openable = pack.getOpenable();
                    swf = (openable instanceof SWF) ? (SWF) openable : ((ABC) openable).getSwf();
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
    
    private ArrayList<File> recursivelySearchDirForScripts(String scriptsFolder)
    {
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
            // TODO: set this back to a warning after i remove the debug warnings
            logger.log(Level.SEVERE,
                    "Exhausted %i% iterations while trying to recursively search for new scripts.".replace("%i%", String.valueOf(searchIterations))
                    + "\nAny previously non-existent scripts not encountered yet will not be created and imported.");
        }
        return allFiles;
    }
    
    private void addBlankScriptWithName(String scriptDotPath, ABCContainerTag doAbc, SWF swf)
    {
        String pkg = scriptDotPath.contains(".") ? scriptDotPath.substring(0, scriptDotPath.lastIndexOf(".")) : "";
        String classSimpleName = scriptDotPath.contains(".") ? scriptDotPath.substring(scriptDotPath.lastIndexOf(".") + 1) : scriptDotPath;
        String fileName = scriptDotPath.replace(".", "/");
        String[] pkgParts = new String[0];
        if (!pkg.isEmpty()) {
            if (pkg.contains(".")) {
                pkgParts = pkg.split("\\.");
            } else {
                pkgParts = new String[]{pkg};
            }
        }
        try {
            AbcIndexing abcIndex = swf.getAbcIndex();
            abcIndex.selectAbc(doAbc.getABC());
            ActionScript3Parser parser = new ActionScript3Parser(abcIndex);

            DottedChain dc = new DottedChain(pkgParts);
            String script = "package " + dc.toPrintableString(true) + " {"
                    + "public class " + IdentifiersDeobfuscation.printIdentifier(true, classSimpleName) + " {"
                    + " }"
                    + "}";
            parser.addScript(script, fileName, 0, 0, swf.getDocumentClass(), doAbc.getABC());
        } catch (IOException | InterruptedException | AVM2ParseException | CompilationException ex) {
            Logger.getLogger(TagTreeContextMenu.class.getName()).log(Level.SEVERE, "Error during script compilation", ex);
        }
    }
}
