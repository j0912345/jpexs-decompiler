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
package com.jpexs.decompiler.flash.gui.generictageditors;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.types.annotations.DottedIdentifier;
import com.jpexs.helpers.Helper;
import com.jpexs.helpers.ReflectionTools;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Field;
import java.util.Objects;
import javax.swing.JTextArea;

/**
 * @author JPEXS
 */
public class StringEditor extends JTextArea implements GenericTagEditor {

    private final Object obj;

    private final Field field;

    private final int index;

    private final Class<?> type;

    private String fieldName;

    private boolean multiline;
    private final SWF swf;

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension ret = super.getPreferredSize();
        ret.width = 300;
        return ret;
    }

    @Override
    public BaselineResizeBehavior getBaselineResizeBehavior() {
        return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
    }

    @Override
    public int getBaseline(int width, int height) {
        return 0;
    }

    public StringEditor(String fieldName, Object obj, Field field, int index, Class<?> type, boolean multiline, SWF swf) {
        setLineWrap(true);
        this.obj = obj;
        this.field = field;
        this.index = index;
        this.type = type;
        this.fieldName = fieldName;
        this.multiline = multiline;
        this.swf = swf;
        if (multiline) {
            Dimension d = new Dimension(500, 200);
            setPreferredSize(d);
            setSize(d);
        }
        reset();
    }

    @Override
    public void reset() {
        try {
            String newValue = (String) ReflectionTools.getValue(obj, field, index);
            DottedIdentifier di = field.getAnnotation(DottedIdentifier.class);
            if (di != null) {
                if (di.exportName()) {
                    newValue = Helper.escapeExportname(swf, newValue, false);
                } else {
                    newValue = Helper.escapePCodeString(newValue);
                    //DottedChain.parseNoSuffix(newValue).toPrintableString(new LinkedHashSet<>(), swf, di.as3());
                }
            }
            setText(newValue);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // ignore
        }
    }

    @Override
    public boolean save() {
        try {
            String oldValue = (String) ReflectionTools.getValue(obj, field, index);
            String newValue = getText();
            DottedIdentifier di = field.getAnnotation(DottedIdentifier.class);
            if (di != null) {
                if (di.exportName()) {
                    newValue = Helper.unescapeExportname(swf, newValue);
                } else {
                    newValue = Helper.unescapePCodeString(newValue);
                    //DottedChain.parsePrintable(newValue).toRawString();
                }
            }

            if (Objects.equals(oldValue, newValue)) {
                return false;
            }
            ReflectionTools.setValue(obj, field, index, newValue);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // ignore
        }
        return true;
    }

    @Override
    public void addChangeListener(final ChangeListener l) {
        final GenericTagEditor t = this;
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent e) {
                l.change(t);
            }

        });
    }

    @Override
    public Object getChangedValue() {
        return getText();
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public String getReadOnlyValue() {
        return Helper.escapeHTML(getChangedValue().toString());
    }

    @Override
    public void added() {

    }

    @Override
    public void validateValue() {
    }

    @Override
    public Object getObject() {
        return obj;
    }

    @Override
    public void setValueNormalizer(ValueNormalizer normalizer) {

    }
}
