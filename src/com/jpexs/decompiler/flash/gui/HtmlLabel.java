/*
 *  Copyright (C) 2016-2025 JPEXS
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
package com.jpexs.decompiler.flash.gui;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;

public class HtmlLabel extends JEditorPane {

    private final JLabel label = new JLabel();
    private String rawText;

    public HtmlLabel() {
        this("");
    }

    public HtmlLabel(String text) {
        super("text/html", "");
        setText(text);
        setEditable(false);
        setFocusable(false);
        setOpaque(false);
        addHyperlinkListener((HyperlinkEvent hle) -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                View.navigateUrl(hle.getURL().toString());
            }
        });
    }

    @Override
    public void setText(String t) {
        String modText = t;
        if (!t.equals("")) {
            Color fgColor = label.getForeground();

            modText = "<body style=\"font-size:" + label.getFont().getSize() + "pt; font-family: " + label.getFont().getFamily() + "; color:rgb(" + fgColor.getRed() + "," + fgColor.getGreen() + "," + fgColor.getBlue() + ");\">" + t + "</body>";
        }
        this.rawText = t;
        super.setText(modText);

        Color bgColor = UIManager.getColor("Panel.background");
        int light = (bgColor.getRed() + bgColor.getGreen() + bgColor.getBlue()) / 3;
        boolean nightMode = light <= 128;

        Color linkColor = Color.blue;
        if (nightMode) {
            linkColor = new Color(0x88, 0x88, 0xff);
        }

        String aRule = "a {color: " + String.format("#%02x%02x%02x", linkColor.getRed(), linkColor.getGreen(), linkColor.getBlue()) + "}";

        ((HTMLDocument) getDocument()).getStyleSheet().addRule(aRule);
    }

    @Override
    public String getText() {
        return rawText;
    }
}
