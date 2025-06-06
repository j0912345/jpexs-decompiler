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
package com.jpexs.decompiler.flash.easygui;

import com.jpexs.decompiler.flash.timeline.Timeline;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * @author JPEXS
 */
public class TimelineDepthPanel extends JPanel {

    private int maxDepth;

    public static final int PADDING = 5;

    public static final float FONT_SIZE = 10.0f;

    private int scrollOffset = 0;

    public static final Color BORDER_COLOR = Color.lightGray;

    public static final Color FONT_COLOR = Color.black;

    public TimelineDepthPanel() {
        setTimeline(null);
    }
    
    public void setTimeline(Timeline timeline) {
        maxDepth = timeline == null ? 0 : timeline.getMaxDepth();
        String maxDepthStr = Integer.toString(maxDepth);
        setFont(getFont().deriveFont(FONT_SIZE));
        int yofs = TimelineBodyPanel.FRAME_HEIGHT - (scrollOffset % TimelineBodyPanel.FRAME_HEIGHT);
        int height = yofs + maxDepth * TimelineBodyPanel.FRAME_HEIGHT + 1;
        int maxDepthW = getFontMetrics(getFont()).stringWidth(maxDepthStr);
        Dimension dim = new Dimension(maxDepthW + 2 * PADDING, height);
        setSize(dim);
        setPreferredSize(dim);
        setMinimumSize(new Dimension(maxDepthW + 2 * PADDING, 0));
        revalidate();
    }

    public void scroll(int offset) {
        this.scrollOffset = offset;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Rectangle clip = g.getClipBounds();
        int yofs = TimelineBodyPanel.FRAME_HEIGHT - (scrollOffset % TimelineBodyPanel.FRAME_HEIGHT);
        int start_d = (scrollOffset + clip.y) / TimelineBodyPanel.FRAME_HEIGHT;
        int end_d = (scrollOffset + clip.y + clip.height) / TimelineBodyPanel.FRAME_HEIGHT;
        int d_count = end_d - start_d;
        g.setColor(TimelineBodyPanel.getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int d = 0; d < d_count; d++) {
            g.setColor(BORDER_COLOR);
            g.drawLine(0, yofs + d * TimelineBodyPanel.FRAME_HEIGHT + 1, getWidth(), yofs + d * TimelineBodyPanel.FRAME_HEIGHT + 1);
            int curr_d = start_d + d;
            g.setColor(FONT_COLOR);
            g.drawString(start_d + d == 0 ? "a" : Integer.toString(curr_d), PADDING, yofs + d * TimelineBodyPanel.FRAME_HEIGHT - PADDING);
        }
    }
}
