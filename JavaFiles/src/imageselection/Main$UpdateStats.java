/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  imageselection.Entry
 *  imageselection.Main
 */
package imageselection;

import imageselection.Entry;
import imageselection.Main;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Main.UpdateStats
implements Runnable {
    BufferedImage toShow;

    public void run() {
        Main.this.entry.showImage((Image)this.toShow);
    }
}
