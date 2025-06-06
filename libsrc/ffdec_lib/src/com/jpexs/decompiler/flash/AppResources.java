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

import java.util.ResourceBundle;

/**
 * Application resources
 *
 * @author JPEXS
 */
public class AppResources {

    /**
     * Resource bundle
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("com.jpexs.decompiler.flash.locales.AppResources");

    /**
     * Constructor.
     */
    public AppResources() {
        
    }    
    
    /**
     * Translates a key
     *
     * @param key The key
     * @return The translated key
     */
    public static String translate(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Translates a key from a bundle
     *
     * @param bundle The bundle
     * @param key The key
     * @return The translated key
     */
    public static String translate(String bundle, String key) {
        ResourceBundle b = ResourceBundle.getBundle(bundle);
        return b.getString(key);
    }
}
