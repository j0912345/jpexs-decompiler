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

import java.io.IOException;
import java.util.Properties;

/**
 * Application information
 *
 * @author JPEXS
 */
public class ApplicationInfo {

    /**
     * Application name
     */
    public static final String APPLICATION_NAME = "JPEXS Free Flash Decompiler";

    /**
     * CLI application name
     */
    public static final String CLI_APPLICATION_NAME = "@|blue JPEXS|@ @|green Free|@ @|red Flash Decompiler|@";

    /**
     * Short application name
     */
    public static final String SHORT_APPLICATION_NAME = "FFDec";

    /**
     * Vendor
     */
    public static final String VENDOR = "JPEXS";

    /**
     * Library version
     */
    public static String libraryVersion = "";

    /**
     * Version
     */
    public static String version = "";

    /**
     * Revision
     */
    public static String revision = "";

    /**
     * Version major
     */
    public static int version_major = 4;

    /**
     * Version minor
     */
    public static int version_minor = 0;

    /**
     * Version release
     */
    public static int version_release = 0;

    /**
     * Version build
     */
    public static int version_build = 0;

    /**
     * Nightly
     */
    public static boolean nightly = false;

    /**
     * CLI application version name
     */
    public static String cliApplicationVerName;

    /**
     * Application version name
     */
    public static String applicationVerName;

    /**
     * Short application version name
     */
    public static String shortApplicationVerName;

    /**
     * Git hub project
     */
    public static final String GIT_HUB_PROJECT = "jindrapetrik/jpexs-decompiler";

    /**
     * Project page
     */
    public static final String PROJECT_PAGE = "https://github.com/" + GIT_HUB_PROJECT;
    
    /**
     * Project Wiki
     */
    public static final String WIKI_PAGE = PROJECT_PAGE + "/wiki";

    /**
     * URL for checking new updates
     */
    public static final String GITHUB_RELEASES_LATEST_URL = "https://api.github.com/repos/" + GIT_HUB_PROJECT + "/releases/latest";

    /**
     * URL for checking new updates
     */
    public static final String GITHUB_RELEASES_URL = "https://api.github.com/repos/" + GIT_HUB_PROJECT + "/releases";

    /**
     * URL for doing update
     */
    public static String UPDATE_URL = PROJECT_PAGE;

    static {
        loadProperties();
        loadLibraryVersion();
    }

    /**
     * Constructor.
     */
    public ApplicationInfo() {
        
    }
    
    /**
     * Loads library version
     */
    private static void loadLibraryVersion() {
        Properties prop = new Properties();
        try {
            prop.load(SWF.class.getResourceAsStream("/project.properties"));
            String version = prop.getProperty("version");
            int version_build = Integer.parseInt(prop.getProperty("version.build"));
            boolean nightly = prop.getProperty("nightly").equals("true");
            if (nightly) {
                version = version + " nightly build " + version_build;
            }

            libraryVersion = version;
        } catch (IOException | NullPointerException | NumberFormatException ex) {
            // ignore
            libraryVersion = "unknown";
        }
    }

    /**
     * Loads properties.
     */
    private static void loadProperties() {
        Properties prop = new Properties();
        try {
            prop.load(ApplicationInfo.class.getResourceAsStream("/project.properties"));
            version = prop.getProperty("version");
            revision = prop.getProperty("build");
            version_major = Integer.parseInt(prop.getProperty("version.major"));
            version_minor = Integer.parseInt(prop.getProperty("version.minor"));
            version_release = Integer.parseInt(prop.getProperty("version.release"));
            version_build = Integer.parseInt(prop.getProperty("version.build"));
            nightly = prop.getProperty("nightly").equals("true");
            if (nightly) {
                version = version + " nightly build " + version_build;
            }
        } catch (IOException | NullPointerException | NumberFormatException ex) {
            // ignore
            version = "unknown";
        }
        cliApplicationVerName = CLI_APPLICATION_NAME + " v." + version;
        applicationVerName = APPLICATION_NAME + " v." + version;
        shortApplicationVerName = SHORT_APPLICATION_NAME + " v." + version;
    }
}
