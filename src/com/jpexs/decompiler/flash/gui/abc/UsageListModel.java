/*
 *  Copyright (C) 2010-2025 JPEXS
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.gui.abc;

import com.jpexs.decompiler.flash.abc.usages.multinames.MultinameUsage;
import javax.swing.DefaultListModel;

/**
 * @author JPEXS
 */
public class UsageListModel extends DefaultListModel<Object> {

    @Override
    public Object get(int index) {
        return ((MultinameUsage) super.get(index)).toString();
    }

    @Override
    public Object getElementAt(int index) {
        return ((MultinameUsage) super.getElementAt(index)).toString();
    }

    public MultinameUsage getUsage(int index) {
        return ((MultinameUsage) super.getElementAt(index));
    }
}
