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

import com.jpexs.decompiler.flash.tags.DoActionTag;
import com.jpexs.decompiler.flash.tags.DoInitActionTag;
import com.jpexs.decompiler.flash.tags.Tag;

/**
 *
 * @author JPEXS
 */
public class ActionScript2TestBase extends ActionScriptTestBase {

    protected SWF swf;

    protected DoActionTag getFirstActionTag() {
        for (Tag t : swf.getTags()) {
            if (t instanceof DoActionTag) {
                return (DoActionTag) t;
            }
        }
        return null;
    }

    protected DoInitActionTag getFirstInitActionTag() {
        for (Tag t : swf.getTags()) {
            if (t instanceof DoInitActionTag) {
                return (DoInitActionTag) t;
            }
        }
        return null;
    }
}
