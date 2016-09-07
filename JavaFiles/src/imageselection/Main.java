
package imageselection;

import imageselection.Entry;
import imageselection.Help;
import inpaint.ImageInpaint;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Main
extends JFrame
implements Runnable {
    public Entry entry;
    protected Image entryImage;
    JScrollPane pictureScrollPane;
    File outputFile;
    String fileExtension;
    ImageInpaint Inpainter;
    Thread inpaintThread = null;
    private Boolean fastInpaint = false;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton6;
    private JButton jButton7;
    private JFrame jFrame5;
    private JFrame jFrame6;
    private JFrame jFrame7;
    private JToolBar jToolBar1;

    Main() {
        this.Inpainter = new ImageInpaint(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedImage i = ImageIO.read(this.getClass().getResource("/imageselection/Images/logo.png"));
            this.setIconImage(i);
        }
        catch (IOException ex) {
            Logger.getLogger(Entry.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setDefaultCloseOperation(3);
        this.initComponents();
        try {
            this.entryImage = ImageIO.read(this.getClass().getResource("/imageselection/Images/defaultImage.png"));
        }
        catch (IOException ex) {
            Logger.getLogger(Entry.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setTitle("Image Inpainting");
        this.getContentPane().setLayout(null);
        this.setSize(500, 500);
        this.entry = new Entry(this.entryImage);
        this.entry.setPreferredSize(new Dimension(this.entryImage.getWidth(this), this.entryImage.getHeight(this)));
        this.getContentPane().add(this.entry);
        this.entry.initImage();
        this.pictureScrollPane = new JScrollPane();
        this.getContentPane().add(this.pictureScrollPane);
        int w = Math.min(this.entryImage.getWidth(this) + 3, this.getContentPane().getWidth());
        int h = Math.min(this.entryImage.getHeight(this) + 3, this.getContentPane().getHeight() - 50);
        if (h == this.entryImage.getHeight(this) + 3) {
            this.pictureScrollPane.setBounds((this.getContentPane().getWidth() - w) / 2, (this.getContentPane().getHeight() - h) / 2, w, h);
        } else {
            this.pictureScrollPane.setBounds((this.getContentPane().getWidth() - w) / 2, (this.getContentPane().getHeight() - h) / 2, w, h + 25);
        }
        this.pictureScrollPane.setAlignmentY(0.5f);
        this.pictureScrollPane.setVerticalScrollBarPolicy(20);
        this.pictureScrollPane.setHorizontalScrollBarPolicy(30);
        this.pictureScrollPane.setViewportView(this.entry);
        this.addComponentListener(new ComponentAdapter(){

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
        });
    }

    public static void main(String[] args) {
        new Main().show();
    }

    private void initComponents() {
        JFrame jFrame1 = new JFrame();
        JFrame jFrame2 = new JFrame();
        JFrame jFrame3 = new JFrame();
        JFrame jFrame4 = new JFrame();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenu jMenu2 = new JMenu();
        JMenu jMenu3 = new JMenu();
        JMenu jMenu4 = new JMenu();
        JMenuItem jMenuItem1 = new JMenuItem();
        JMenuItem jMenuItem2 = new JMenuItem();
        JMenuItem jMenuItem3 = new JMenuItem();
        JMenuItem jMenuItem4 = new JMenuItem();
        JMenuItem jMenuItem5 = new JMenuItem();
        JMenuItem jMenuItem6 = new JMenuItem();
        JMenuItem jMenuItem7 = new JMenuItem();
        JMenuItem jMenuItem8 = new JMenuItem();
        JMenuItem jMenuItem9 = new JMenuItem();
        GroupLayout jFrame1Layout = new GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(jFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame1Layout.setVerticalGroup(jFrame1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        GroupLayout jFrame2Layout = new GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(jFrame2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame2Layout.setVerticalGroup(jFrame2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        GroupLayout jFrame3Layout = new GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(jFrame3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame3Layout.setVerticalGroup(jFrame3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        GroupLayout jFrame4Layout = new GroupLayout(jFrame3.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(jFrame4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame4Layout.setVerticalGroup(jFrame4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        this.setDefaultCloseOperation(3);
        jMenu1.setText("File");
        jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        jMenuItem1.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/open.png")));
        jMenuItem1.setText("Open Image");
        jMenu1.add(jMenuItem1);
        jMenuItem1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        jMenuItem2.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/save.png")));
        jMenuItem2.setText("Save");
        jMenu1.add(jMenuItem2);
        jMenuItem2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuItem7.setAccelerator(KeyStroke.getKeyStroke(83, 10));
        jMenuItem7.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/saveAs.png")));
        jMenuItem7.setText("Save As");
        jMenu1.add(jMenuItem7);
        jMenuItem7.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem7ActionPerformed(evt);
            }
        });
        jMenuItem3.setAccelerator(KeyStroke.getKeyStroke(115, 8));
        jMenuItem3.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/exit.png")));
        jMenuItem3.setText("Exit");
        jMenu1.add(jMenuItem3);
        jMenuItem3.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem3ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);
        jMenu2.setText("Edit");
        jMenuItem4.setAccelerator(KeyStroke.getKeyStroke(90, 2));
        jMenuItem4.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/undo.png")));
        jMenuItem4.setText("Undo");
        jMenu2.add(jMenuItem4);
        jMenuItem4.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuItem5.setAccelerator(KeyStroke.getKeyStroke(89, 2));
        jMenuItem5.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/redo.png")));
        jMenuItem5.setText("Redo");
        jMenu2.add(jMenuItem5);
        jMenuItem5.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem5ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);
        jMenu3.setText("Inpaint");
        jMenuItem8.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        jMenuItem8.setText("Run");
        jMenu3.add(jMenuItem8);
        jMenuItem8.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem8ActionPerformed(evt);
            }
        });
        jMenuItem9.setAccelerator(KeyStroke.getKeyStroke(70, 2));
        jMenuItem9.setText("Fast Inpaint");
        jMenu3.add(jMenuItem9);
        jMenuItem9.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.setText("Help");
        jMenuItem6.setAccelerator(KeyStroke.getKeyStroke(112, 0));
        jMenuItem6.setText("Help");
        jMenu4.add(jMenuItem6);
        jMenuItem6.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem6ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(jMenu4);
        this.setJMenuBar(jMenuBar1);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 279, 32767));
        this.jFrame5 = new JFrame();
        this.jFrame6 = new JFrame();
        this.jFrame7 = new JFrame();
        this.jToolBar1 = new JToolBar();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton6 = new JButton();
        this.jButton7 = new JButton();
        GroupLayout jFrame5Layout = new GroupLayout(this.jFrame5.getContentPane());
        this.jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(jFrame5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame5Layout.setVerticalGroup(jFrame5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        GroupLayout jFrame6Layout = new GroupLayout(this.jFrame6.getContentPane());
        this.jFrame6.getContentPane().setLayout(jFrame6Layout);
        jFrame6Layout.setHorizontalGroup(jFrame6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame6Layout.setVerticalGroup(jFrame6Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        GroupLayout jFrame7Layout = new GroupLayout(this.jFrame7.getContentPane());
        this.jFrame7.getContentPane().setLayout(jFrame7Layout);
        jFrame7Layout.setHorizontalGroup(jFrame7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 400, 32767));
        jFrame7Layout.setVerticalGroup(jFrame7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 300, 32767));
        this.setDefaultCloseOperation(3);
        this.jToolBar1.setRollover(true);
        this.jButton1.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/open.png")));
        this.jButton1.setFocusable(false);
        this.jButton1.setHorizontalTextPosition(0);
        this.jButton1.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton1);
        this.jButton1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem1ActionPerformed(evt);
            }
        });
        this.jButton2.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/save.png")));
        this.jButton2.setFocusable(false);
        this.jButton2.setHorizontalTextPosition(0);
        this.jButton2.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton2);
        this.jButton2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem2ActionPerformed(evt);
            }
        });
        this.jButton3.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/undo.png")));
        this.jButton3.setFocusable(false);
        this.jButton3.setHorizontalTextPosition(0);
        this.jButton3.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton3);
        this.jButton3.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem4ActionPerformed(evt);
            }
        });
        this.jButton4.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/redo.png")));
        this.jButton4.setFocusable(false);
        this.jButton4.setHorizontalTextPosition(0);
        this.jButton4.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton4);
        this.jButton4.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem5ActionPerformed(evt);
            }
        });
        this.jButton6.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/play-button.png")));
        this.jButton6.setFocusable(false);
        this.jButton6.setHorizontalTextPosition(0);
        this.jButton6.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton6);
        this.jButton6.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.jMenuItem9ActionPerformed(evt);
            }
        });
        this.jButton7.setIcon(new ImageIcon(this.getClass().getResource("/imageselection/Images/pause-button.png")));
        this.jButton7.setFocusable(false);
        this.jButton7.setHorizontalTextPosition(0);
        this.jButton7.setVerticalTextPosition(3);
        this.jToolBar1.add(this.jButton7);
        this.jButton7.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt) {
                Main.this.PauseActionPerformed(evt);
            }
        });
        GroupLayout layout1 = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jToolBar1, -1, 400, 32767));
        layout1.setVerticalGroup(layout1.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout1.createSequentialGroup().addComponent(this.jToolBar1, -2, 25, -2).addContainerGap(275, 32767)));
        this.pack();
    }

    private void jMenuItem1ActionPerformed(ActionEvent evt) {
        if (!this.entry.isDisabled.booleanValue()) {
            JFileChooser _fileChooser = new JFileChooser();
            int retval = _fileChooser.showOpenDialog(this);
            String[] okFileExtensions = new String[]{"jpg", "png", "gif", "bmp", "jpeg"};
            if (retval == 0) {
                try {
                    File file = _fileChooser.getSelectedFile();
                    Boolean flag = false;
                    for (String extension : okFileExtensions) {
                        if (!file.getName().toLowerCase().endsWith(extension)) continue;
                        this.outputFile = file;
                        this.fileExtension = extension;
                        flag = true;
                    }
                    if (!flag.booleanValue()) {
                        JOptionPane.showMessageDialog(this, "Please choose a jpg, jpeg, png, bmp or gif file only.", "Error", 0);
                        return;
                    }
                    this.entry.SavedImages.clear();
                    this.entry.RedoImages.clear();
                    BufferedImage selectedImage = ImageIO.read(file);
                    Image tmg = this.createImage(selectedImage.getWidth(this), selectedImage.getHeight(this));
                    Graphics tg = tmg.getGraphics();
                    tg.drawImage(selectedImage, 0, 0, null);
                    this.entry.SavedImages.push(selectedImage);
                    this.entryImage = tmg;
                    this.entry.showImage(this.entryImage);
                    this.entry.setPreferredSize(new Dimension(this.entryImage.getWidth(this), this.entryImage.getHeight(this)));
                    int w = Math.min(this.entryImage.getWidth(this) + 3, this.getContentPane().getWidth());
                    int h = Math.min(this.entryImage.getHeight(this) + 3, this.getContentPane().getHeight());
                    this.pictureScrollPane.setBounds((this.getContentPane().getWidth() - w) / 2, (this.getContentPane().getHeight() - h) / 2, w, h);
                    this.pictureScrollPane.setViewportView(this.entry);
                }
                catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void jMenuItem4ActionPerformed(ActionEvent evt) {
        Boolean flag = false;
        if (!this.entry.isDisabled.booleanValue() && this.entry.SavedImages.size() > 1) {
            if (this.entry.getPressed().booleanValue()) {
                this.entry.entryReset();
                flag = true;
            }
            this.entry.RedoImages.push(this.entry.SavedImages.pop());
            Image tmg = this.createImage(((Image)this.entry.SavedImages.peek()).getWidth(this), ((Image)this.entry.SavedImages.peek()).getHeight(this));
            Graphics tg = tmg.getGraphics();
            tg.drawImage((Image)this.entry.SavedImages.peek(), 0, 0, null);
            this.entry.showImage(tmg);
            this.entry.setPreferredSize(new Dimension(this.entryImage.getWidth(this), this.entryImage.getHeight(this)));
            int w = Math.min(this.entryImage.getWidth(this) + 3, this.getContentPane().getWidth());
            int h = Math.min(this.entryImage.getHeight(this) + 3, this.getContentPane().getHeight());
            this.pictureScrollPane.setBounds((this.getContentPane().getWidth() - w) / 2, (this.getContentPane().getHeight() - h) / 2, w, h);
            this.pictureScrollPane.setViewportView(this.entry);
            if (flag.booleanValue()) {
                this.jMenuItem5ActionPerformed(evt);
            }
        }
    }

    private void jMenuItem5ActionPerformed(ActionEvent evt) {
        if (!this.entry.isDisabled.booleanValue() && this.entry.RedoImages.size() > 0) {
            Image tmg = this.createImage(((Image)this.entry.RedoImages.peek()).getWidth(this), ((Image)this.entry.RedoImages.peek()).getHeight(this));
            Graphics tg = tmg.getGraphics();
            tg.drawImage((Image)this.entry.RedoImages.peek(), 0, 0, null);
            this.entry.showImage(tmg);
            this.entry.SavedImages.push(this.entry.RedoImages.pop());
            this.entry.setPreferredSize(new Dimension(this.entryImage.getWidth(this), this.entryImage.getHeight(this)));
            int w = Math.min(this.entryImage.getWidth(this) + 3, this.getContentPane().getWidth());
            int h = Math.min(this.entryImage.getHeight(this) + 3, this.getContentPane().getHeight());
            this.pictureScrollPane.setBounds((this.getContentPane().getWidth() - w) / 2, (this.getContentPane().getHeight() - h) / 2, w, h);
            this.pictureScrollPane.setViewportView(this.entry);
        }
    }

    private void jMenuItem6ActionPerformed(ActionEvent evt) {
        new Help().show();
    }

    private void jMenuItem2ActionPerformed(ActionEvent evt) {
        if (this.outputFile == null) {
            System.err.println("Error!! No file to save");
            return;
        }
        try {
            BufferedImage bi = (BufferedImage)this.entry.getImage();
            ImageIO.write((RenderedImage)bi, this.fileExtension, this.outputFile);
        }
        catch (IOException e) {
            System.err.println("Error!! File not saved");
        }
    }

    private void jMenuItem7ActionPerformed(ActionEvent evt) {
        JFileChooser _fileChooser = new JFileChooser();
        int retval = _fileChooser.showSaveDialog(this);
        String[] okFileExtensions = new String[]{"jpg", "png", "gif", "bmp", "jpeg"};
        if (retval == 0) {
            File file = _fileChooser.getSelectedFile();
            Boolean flag = false;
            for (String extension : okFileExtensions) {
                if (!file.getName().toLowerCase().endsWith(extension)) continue;
                if (this.outputFile == null) {
                    System.err.println("Error!! No file to save");
                    return;
                }
                try {
                    this.outputFile = file;
                    this.fileExtension = extension;
                    BufferedImage bi = (BufferedImage)this.entry.getImage();
                    ImageIO.write((RenderedImage)bi, this.fileExtension, this.outputFile);
                    System.out.println("Saved");
                }
                catch (IOException e) {
                    System.err.println("Error!! File not saved");
                }
                flag = true;
            }
            if (!flag.booleanValue()) {
                JOptionPane.showMessageDialog(this, "Please choose a jpg, jpeg, png, bmp or gif file only.", "Error", 0);
                return;
            }
        }
    }

    private void jMenuItem8ActionPerformed(ActionEvent evt) {
        this.Inpainter.halt = false;
        this.Inpainter.completed = false;
        if (this.inpaintThread == null) {
            this.entry.setDisabled();
            this.inpaintThread = new Thread(this);
            this.inpaintThread.start();
        }
    }

    private void jMenuItem9ActionPerformed(ActionEvent evt) {
        this.fastInpaint = true;
        this.Inpainter.halt = false;
        this.Inpainter.completed = false;
        if (this.inpaintThread == null) {
            this.entry.setDisabled();
            this.inpaintThread = new Thread(this);
            this.inpaintThread.start();
        }
    }

    private void PauseActionPerformed(ActionEvent evt) {
        if (this.inpaintThread != null) {
            this.Inpainter.halt = true;
        }
    }

    private void jMenuItem3ActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    public void run() {
        this.Inpainter.init((BufferedImage)this.entry.getImage(), (BufferedImage)this.entry.getImage(), this.fastInpaint);
    }

    public void updateStats(BufferedImage toShow) {
        UpdateStats stats = new UpdateStats();
        stats.toShow = toShow;
        try {
            SwingUtilities.invokeAndWait(stats);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Training", 0);
        }
        if (this.Inpainter.completed.booleanValue()) {
            JOptionPane.showMessageDialog(this, "                      Inpainting is completed.", "Inpainting", -1);
        }
        if (this.Inpainter.completed.booleanValue() || this.Inpainter.halt.booleanValue()) {
            System.out.println("Inpainting completed or halted");
            this.inpaintThread = null;
            Image tmg = this.createImage(toShow.getWidth(this), toShow.getHeight(this));
            Graphics tg = tmg.getGraphics();
            tg.drawImage(toShow, 0, 0, null);
            this.entry.SavedImages.push(tmg);
            this.entry.setEnabled();
            this.entry.RedoImages.clear();
            this.fastInpaint = false;
        }
    }

    public class UpdateStats
    implements Runnable {
        BufferedImage toShow;

        public void run() {
            Main.this.entry.showImage(this.toShow);
        }
    }

    class SymAction
    implements ActionListener {
        SymAction() {
        }

        public void actionPerformed(ActionEvent event) {
            Object object = event.getSource();
        }
    }

}
