/*
 * Decompiled with CFR 0_114.
 */
package imageselection;

import imageselection.Entry;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.ImageObserver;
import javax.swing.JScrollPane;

public class Main
extends ComponentAdapter {
    Main() {
    }

    public void componentResized(ComponentEvent e) {
        int w = Math.min(Main.this.entryImage.getWidth(Main.this.entry) + 3, Main.this.getContentPane().getWidth());
        int h = Math.min(Main.this.entryImage.getHeight(Main.this.entry) + 3, Main.this.getContentPane().getHeight() - 50);
        if (h == Main.this.entryImage.getHeight(Main.this.entry) + 3) {
            Main.this.pictureScrollPane.setBounds((Main.this.getContentPane().getWidth() - w) / 2, (Main.this.getContentPane().getHeight() - h) / 2, w, h);
        } else {
            Main.this.pictureScrollPane.setBounds((Main.this.getContentPane().getWidth() - w) / 2, (Main.this.getContentPane().getHeight() - h) / 2, w, h + 25);
        }
        Main.this.pictureScrollPane.setViewportView(Main.this.entry);
    }
}
