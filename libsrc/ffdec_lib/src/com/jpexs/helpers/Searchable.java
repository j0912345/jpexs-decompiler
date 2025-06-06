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
package com.jpexs.helpers;

import java.io.InputStream;
import java.util.Map;

/**
 * Interface for searchable objects.
 *
 * @author JPEXS
 */
public interface Searchable {

    /**
     * Searches for byte sequences
     *
     * @param data Data
     * @return Map Position=>Input stream
     */
    public Map<Long, InputStream> search(byte[]... data);

    /**
     * Searches for byte sequences with progress listener
     *
     * @param progListener Listener
     * @param data Data
     * @return Map Position=>Input stream
     */
    public Map<Long, InputStream> search(ProgressListener progListener, byte[]... data);
}
