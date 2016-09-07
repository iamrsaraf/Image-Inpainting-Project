/*
 * Decompiled with CFR 0_114.
 */
package imageselection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JPanel;

public class Entry
extends JPanel {
    protected Image entryImage;
    protected Graphics entryGraphics;
    protected int lastX = -1;
    protected int lastY = -1;
    protected int polySides = 0;
    private Vector PolygonCoordinatesX;
    private Vector PolygonCoordinatesY;
    private Image img;
    public Stack SavedImages;
    public Stack RedoImages;
    private int firstX = -1;
    private int firstY = -1;
    private int currX = -1;
    private int currY = -1;
    public Boolean isDisabled = false;
    public int maxX = -1;
    public int maxY = -1;
    public int minX = -1;
    public int minY = -1;
    Boolean pressed;

    Entry(Image img) {
        this.entryImage = img;
        this.enableEvents(49);
        this.pressed = false;
        this.SavedImages = new Stack();
        this.RedoImages = new Stack();
        this.PolygonCoordinatesX = new Vector();
        this.PolygonCoordinatesY = new Vector();
    }

    public void showImage(Image img) {
        this.entryImage = img;
        this.entryGraphics = this.entryImage.getGraphics();
        this.repaint();
    }

    public Image getImage() {
        return this.entryImage;
    }

    protected void initImage() {
        this.img = this.entryImage;
        this.entryGraphics = this.entryImage.getGraphics();
        Image tmg = this.createImage(this.entryImage.getWidth(this), this.entryImage.getHeight(this));
        Graphics tg = tmg.getGraphics();
        tg.drawImage(this.entryImage, 0, 0, null);
        this.SavedImages.push(tmg);
        this.repaint();
    }

    public void paint(Graphics g) {
        if (this.entryImage == null) {
            this.initImage();
        }
        g.drawImage(this.entryImage, 0, 0, this);
        if (this.firstX != -1) {
            g.setColor(Color.red);
            g.drawRect(this.firstX - 5, this.firstY - 5, 10, 10);
            g.drawRect(this.firstX - 4, this.firstY - 4, 8, 8);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.green);
        if (this.PolygonCoordinatesX == null || this.PolygonCoordinatesY == null) {
            return;
        }
        g.setColor(Color.black);
        for (int i = 1; i < this.PolygonCoordinatesX.size(); ++i) {
            g.drawRect((Integer)this.PolygonCoordinatesX.get(i) - 5, (Integer)this.PolygonCoordinatesY.get(i) - 5, 10, 10);
            g.drawRect((Integer)this.PolygonCoordinatesX.get(i) - 4, (Integer)this.PolygonCoordinatesY.get(i) - 4, 8, 8);
        }
        g.setColor(Color.green);
        if (this.lastX != -1) {
            g.drawLine(this.lastX, this.lastY, this.currX, this.currY);
        }
    }

    protected void processMouseEvent(MouseEvent e) {
        if (!this.isDisabled.booleanValue()) {
            if (e.getID() == 501 && !this.pressed.booleanValue()) {
                this.lastX = e.getX();
                this.lastY = e.getY();
                this.firstX = this.lastX;
                this.firstY = this.lastY;
                this.pressed = true;
                this.PolygonCoordinatesX.add(this.lastX);
                this.PolygonCoordinatesY.add(this.lastY);
                this.repaint();
            } else if (e.getID() == 501 && this.pressed.booleanValue()) {
                this.entryGraphics.setColor(Color.green);
                this.entryGraphics.drawLine(this.lastX, this.lastY, e.getX(), e.getY());
                this.getGraphics().drawImage(this.entryImage, 0, 0, this);
                this.repaint();
                this.lastX = e.getX();
                this.lastY = e.getY();
                this.maxY = -1;
                this.maxX = -1;
                this.minX = this.lastX;
                this.minY = this.lastY;
                this.PolygonCoordinatesX.add(this.lastX);
                this.PolygonCoordinatesY.add(this.lastY);
                if (Math.abs(this.lastX - (Integer)this.PolygonCoordinatesX.get(0)) < 10 && Math.abs(this.lastY - (Integer)this.PolygonCoordinatesY.get(0)) < 10) {
                    int[] PolyX = new int[this.PolygonCoordinatesX.size()];
                    int[] PolyY = new int[this.PolygonCoordinatesY.size()];
                    for (int i = 0; i < this.PolygonCoordinatesX.size(); ++i) {
                        PolyX[i] = (Integer)this.PolygonCoordinatesX.get(i);
                        PolyY[i] = (Integer)this.PolygonCoordinatesY.get(i);
                        if (this.minX > PolyX[i]) {
                            this.minX = PolyX[i];
                        }
                        if (this.minY > PolyY[i]) {
                            this.minY = PolyY[i];
                        }
                        if (this.maxX < PolyX[i]) {
                            this.maxX = PolyX[i];
                        }
                        if (this.maxY < PolyY[i]) {
                            this.maxY = PolyY[i];
                        }
                        System.out.println(this.PolygonCoordinatesX.get(i) + ", " + this.PolygonCoordinatesY.get(i));
                    }
                    this.polySides = this.PolygonCoordinatesX.size();
                    this.entryGraphics.fillPolygon(PolyX, PolyY, this.polySides);
                    this.PolygonCoordinatesX.clear();
                    this.PolygonCoordinatesY.clear();
                    this.currY = -1;
                    this.currX = -1;
                    this.lastY = -1;
                    this.lastX = -1;
                    this.firstY = -1;
                    this.firstX = -1;
                    this.pressed = false;
                    Image tmg = this.createImage(this.entryImage.getWidth(this), this.entryImage.getHeight(this));
                    Graphics tg = tmg.getGraphics();
                    tg.drawImage(this.entryImage, 0, 0, null);
                    this.SavedImages.push(tmg);
                    this.RedoImages.clear();
                    this.repaint();
                }
            } else {
                return;
            }
        }
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        if (!this.isDisabled.booleanValue()) {
            if (e.getID() != 503) {
                return;
            }
            this.currX = e.getX();
            this.currY = e.getY();
            this.repaint();
        }
    }

    void setDisabled() {
        this.isDisabled = true;
    }

    void setEnabled() {
        this.isDisabled = false;
    }

    public Boolean getPressed() {
        return this.pressed;
    }

    void entryReset() {
        this.PolygonCoordinatesX.clear();
        this.PolygonCoordinatesY.clear();
        this.currY = -1;
        this.currX = -1;
        this.lastY = -1;
        this.lastX = -1;
        this.firstY = -1;
        this.firstX = -1;
        this.pressed = false;
    }

    Object entryImage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
