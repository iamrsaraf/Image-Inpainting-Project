/*
 * Decompiled with CFR 0_114.
 */
package imageselection;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Help
extends JFrame {
    private JScrollPane jScrollPane1;
    private JTextPane jTextPane1;

    public Help() {
        this.initComponents();
        this.setTitle("Image Inpainting Help");
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jTextPane1 = new JTextPane();
        this.setDefaultCloseOperation(2);
        this.jTextPane1.setEditable(false);
        this.jTextPane1.setText("Image inpainting Copyright (C) 2010-201, Sapan Diwakar and Pulkit Goyal\nThis program comes with ABSOLUTELY NO WARRANTY.\nThis is free software, and you are welcome to redistribute it under certain conditions.\n\nInpainting is easy. Just follow these steps to guide you through the inpainting process.  \n1. Open an image: Go to File. Click on Open. Alternatively click on the toolbar icon for opening an image. You can also press Ctrl^O to open an image.\n2. Select the region to be inpainted/removed from your image. Just click on the border points of the region that you want to select and watch as the points are automatically interpolated to enclose a bounding polygon once you reach back to the start point. For better results, try not to leave any portion of the image that you want to remove and select curved regions (Avoid pointed edges.). Select as many regions as you want. No need for the regions to be spatially connected. \n3. Start Inpainting by selecting Inpaint -> Inpaint or Inpaint -> Fast Inpaint or choose the toolbar icon.\n4. You can watch as the selected region is removed from the image.\n5. While inpainting you could also stop the process in between by clicking on the pause inpaint toolbar icon.\n6. When inpainting is completed, you would see a message box to confirm the same. \n7. Save or Save As your Image by selecting the appropriate option from the menu bar or the tool box. \n8. Enjoy Inpainting.  ");
        this.jScrollPane1.setViewportView(this.jTextPane1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 466, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 322, 32767));
        this.pack();
    }
}
