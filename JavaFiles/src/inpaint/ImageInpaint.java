/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  imageselection.Main
 */
package inpaint;

import imageselection.Main;
import inpaint.GradientCalculator;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ImageInpaint {
    BufferedImage origImg;
    Image fillImg;
    BufferedImage img;
    WritableRaster raster;
    int iw;
    int ih;
    int[] pixels;
    int[][] pixelmap;
    int[][] fillPixelmap;
    int[][] sourceRegion;
    int[][] initialSourceRegion;
    double[][] fillRegion;
    double[][] gradientX;
    double[][] gradientY;
    double[][] confidence;
    double[][] data;
    GradientCalculator gc;
    double omega = 0.7;
    double Alpha = 0.2;
    double Beta = 0.8;
    int maxX;
    int maxY;
    int minX;
    int minY;
    int continuousCol = 0;
    int continuousRow = 0;
    public Main owner;
    public Boolean halt = false;
    public Boolean completed = false;
    final int diamX = 50;
    final int diamY = 30;
    private int pixelPosX;
    private int pixelPosY;
    int w = 3;
    double[][] con = new double[][]{{1.0, 1.0, 1.0}, {1.0, -8.0, 1.0}, {1.0, 1.0, 1.0}};

    public ImageInpaint(Main owner) {
        this.owner = owner;
    }

    public void init(BufferedImage a_origImg, BufferedImage a_fillImg, Boolean quickInpaint) {
        int j;
        int i;
        int g;
        int b;
        this.halt = false;
        try {
            this.origImg = a_origImg;
            this.img = a_origImg;
            this.fillImg = a_fillImg;
            this.raster = this.origImg.getRaster();
            this.iw = this.img.getWidth(null);
            this.ih = this.img.getHeight(null);
            this.pixels = new int[this.iw * this.ih];
            PixelGrabber pg = new PixelGrabber(this.img, 0, 0, this.iw, this.ih, this.pixels, 0, this.iw);
            pg.grabPixels();
            this.pixelmap = new int[this.ih][this.iw];
            for (i = 0; i < this.ih; ++i) {
                for (j = 0; j < this.iw; ++j) {
                    this.pixelmap[i][j] = this.pixels[i * this.iw + j];
                }
            }
            PixelGrabber fillPg = new PixelGrabber(this.fillImg, 0, 0, this.iw, this.ih, this.pixels, 0, this.iw);
            fillPg.grabPixels();
            this.fillPixelmap = new int[this.ih][this.iw];
            for (i = 0; i < this.ih; ++i) {
                for (j = 0; j < this.iw; ++j) {
                    this.fillPixelmap[i][j] = this.pixels[i * this.iw + j];
                }
            }
        }
        catch (InterruptedException e1) {
            JOptionPane.showMessageDialog((Component)this.owner, "Error " + e1, "Error!!", 0);
            System.out.println("Error " + e1);
        }
        this.gc = new GradientCalculator();
        this.gc.calculateGradientFromImage(this.pixelmap, this.ih, this.iw);
        this.gradientX = this.gc.gradientX;
        this.gradientY = this.gc.gradientY;
        this.initialize_confidence_term();
        this.initialize_data_term();
        this.fillRegion = new double[this.ih][this.iw];
        this.sourceRegion = new int[this.ih][this.iw];
        this.initialSourceRegion = new int[this.ih][this.iw];
        this.minX = this.iw;
        this.minY = this.ih;
        this.maxY = 0;
        this.maxX = 0;
        this.continuousCol = 0;
        this.continuousRow = 0;
        for (i = 0; i < this.ih; ++i) {
            int countrow = 0;
            for (j = 0; j < this.iw; ++j) {
                int pixel = this.fillPixelmap[i][j];
                int r = 255 & pixel >> 16;
                g = 255 & pixel >> 8;
                b = 255 & pixel;
                if (r == 0 && g == 255 && b == 0) {
                    ++countrow;
                    this.fillRegion[i][j] = 1.0;
                    this.sourceRegion[i][j] = 0;
                    this.initialSourceRegion[i][j] = 0;
                    if (j < this.minX) {
                        this.minX = j;
                    }
                    if (i < this.minY) {
                        this.minY = i;
                    }
                    if (j > this.maxX) {
                        this.maxX = j;
                    }
                    if (i <= this.maxY) continue;
                    this.maxY = i;
                    continue;
                }
                if (countrow > this.continuousRow) {
                    this.continuousRow = countrow;
                }
                countrow = 0;
                this.fillRegion[i][j] = 0.0;
                this.sourceRegion[i][j] = 1;
                this.initialSourceRegion[i][j] = 1;
            }
        }
        for (i = 0; i < this.iw; ++i) {
            int countcol = 0;
            for (j = 0; j < this.ih; ++j) {
                int pixel = this.fillPixelmap[j][i];
                int r = 255 & pixel >> 16;
                g = 255 & pixel >> 8;
                b = 255 & pixel;
                if (r == 0 && g == 255 && b == 0) {
                    ++countcol;
                    continue;
                }
                if (countcol > this.continuousCol) {
                    this.continuousCol = countcol;
                }
                countcol = 0;
            }
        }
        Boolean flag = true;
        double[][] temp = new double[this.ih][this.iw];
        double[][] sourceGradX = new double[this.ih][this.iw];
        double[][] sourceGradY = new double[this.ih][this.iw];
        Vector<Integer> dR = new Vector<Integer>();
        Vector<Double> Nx = new Vector<Double>();
        Vector<Double> Ny = new Vector<Double>();
        while (flag.booleanValue()) {
            temp = this.conv2(this.fillRegion, this.con);
            this.gc.calculateGradient(this.sourceRegion, this.ih, this.iw);
            sourceGradX = this.gc.gradientX;
            sourceGradY = this.gc.gradientY;
            dR.clear();
            Nx.clear();
            Ny.clear();
            for (i = 0; i < temp[0].length; ++i) {
                for (j = 0; j < temp.length; ++j) {
                    if (temp[j][i] <= 0.0) continue;
                    dR.add(i * temp.length + j);
                    Nx.add(sourceGradX[j][i]);
                    Ny.add(sourceGradY[j][i]);
                }
            }
            double[][] N = this.normr(Nx, Ny);
            Vector<Integer> q = new Vector<Integer>();
            double count = 0.0;
            for (i = 0; i < dR.size(); ++i) {
                int[][] Hp = this.getpatch(this.pixelmap, (Integer)dR.get(i));
                for (j = 0; j < Hp.length; ++j) {
                    for (int k = 0; k < Hp[0].length; ++k) {
                        int row = Hp[j][k] % this.ih;
                        int col = Hp[j][k] / this.ih;
                        if (this.fillRegion[row][col] != 0.0) continue;
                        count += this.confidence[row][col];
                        q.add(Hp[j][k]);
                    }
                }
                int col = (Integer)dR.get(i) / this.ih;
                int row = (Integer)dR.get(i) % this.ih;
                this.confidence[row][col] = count / (double)(Hp.length * Hp[0].length);
                count = 0.0;
            }
            double maxPriority = 0.0;
            int maxPriorityIndex = -1;
            for (i = 0; i < dR.size(); ++i) {
                int col = (Integer)dR.get(i) / this.ih;
                int row = (Integer)dR.get(i) % this.ih;
                this.data[row][col] = Math.abs(this.gradientX[row][col] * N[i][0] + this.gradientY[row][col] * N[i][1]) + 0.001;
                double Rcp = (1.0 - this.omega) * this.confidence[row][col] + this.omega;
                double tempPriority = this.Alpha * Rcp + this.Beta * this.data[row][col];
                if (tempPriority < maxPriority) continue;
                maxPriority = tempPriority;
                maxPriorityIndex = i;
            }
            if (maxPriorityIndex == -1) break;
            int[][] Hp = this.getpatch(this.pixelmap, (Integer)dR.get(maxPriorityIndex));
            double[][] toFill = new double[Hp.length][Hp[0].length];
            double[][] toFillTrans = new double[Hp[0].length][Hp.length];
            for (i = 0; i < Hp.length; ++i) {
                for (j = 0; j < Hp[0].length; ++j) {
                    int col = Hp[i][j] / this.ih;
                    int row = Hp[i][j] % this.ih;
                    toFill[i][j] = this.fillRegion[row][col];
                    toFillTrans[j][i] = this.fillRegion[row][col];
                }
            }
            this.pixelPosX = (Integer)dR.get(maxPriorityIndex) / this.ih;
            this.pixelPosY = (Integer)dR.get(maxPriorityIndex) % this.ih;
            int[] best = this.bestExemplar(Hp, toFillTrans, this.initialSourceRegion, quickInpaint);
            int nRows = best[3] - best[2] + 1;
            int nCols = best[1] - best[0] + 1;
            int[][] X = new int[nRows][nCols];
            int[][] Y = new int[nRows][nCols];
            int[][] Hq = new int[nRows][nCols];
            for (i = 0; i < nRows; ++i) {
                for (j = 0; j < nCols; ++j) {
                    X[i][j] = best[0] + j;
                    Y[i][j] = best[2] + i;
                    Hq[i][j] = X[i][j] + Y[i][j] * this.ih;
                }
            }
            int p = (Integer)dR.get(maxPriorityIndex);
            for (i = 0; i < toFill.length; ++i) {
                for (j = 0; j < toFill[0].length; ++j) {
                    if (toFill[i][j] == 0.0) continue;
                    toFill[i][j] = 1.0;
                    int col = Hp[i][j] / this.ih;
                    int row = Hp[i][j] % this.ih;
                    int col1 = Hq[i][j] / this.ih;
                    int row1 = Hq[i][j] % this.ih;
                    this.fillRegion[row][col] = 0.0;
                    this.sourceRegion[row][col] = 1;
                    this.confidence[row][col] = this.confidence[p % this.ih][p / this.ih];
                    this.gradientX[row][col] = this.gradientX[row1][col1];
                    this.gradientY[row][col] = this.gradientY[row1][col1];
                    this.pixelmap[row][col] = this.pixelmap[row1][col1];
                    int[] color = new int[]{255 & this.pixelmap[row1][col1] >> 16, 255 & this.pixelmap[row1][col1] >> 8, 255 & this.pixelmap[row1][col1]};
                    this.raster.setPixel(col, row, color);
                }
            }
            if (this.halt.booleanValue()) break;
            this.owner.updateStats(this.origImg);
            Thread.yield();
            flag = false;
            for (i = 0; i < this.fillRegion.length; ++i) {
                for (j = 0; j < this.fillRegion[0].length; ++j) {
                    if (this.fillRegion[i][j] != 1.0) continue;
                    flag = true;
                    break;
                }
                if (flag.booleanValue()) break;
            }
            if (!this.halt.booleanValue()) continue;
            break;
        }
        this.gc = null;
        this.completed = this.halt == false ? Boolean.valueOf(true) : Boolean.valueOf(false);
        this.owner.updateStats(this.origImg);
        Thread.yield();
    }

    void initialize_confidence_term() {
        this.confidence = new double[this.ih][this.iw];
        for (int i = 0; i < this.ih; ++i) {
            for (int j = 0; j < this.iw; ++j) {
                int p = this.pixelmap[i][j];
                int r = 255 & p >> 16;
                int g = 255 & p >> 8;
                int b = 255 & p;
                this.confidence[i][j] = r == 0 && g == 255 && b == 0 ? 0.0 : 1.0;
            }
        }
    }

    void initialize_data_term() {
        this.data = new double[this.ih][this.iw];
        for (int i = 0; i < this.ih; ++i) {
            for (int j = 0; j < this.iw; ++j) {
                this.data[i][j] = -0.1;
            }
        }
    }

    double[][] conv2(double[][] a, double[][] b) {
        int ra = a.length;
        int ca = a[0].length;
        int rb = b.length;
        int cb = b[0].length;
        double[][] c = new double[ra + rb - 1][ca + cb - 1];
        for (int i = 0; i < rb; ++i) {
            for (int j = 0; j < cb; ++j) {
                int r1 = i;
                int r2 = r1 + ra - 1;
                int c1 = j;
                int c2 = c1 + ca - 1;
                for (int k = r1; k < r2; ++k) {
                    for (int l = c1; l < c2; ++l) {
                        c[k][l] = c[k][l] + b[i][j] * a[k - r1 + 1][l - c1 + 1];
                    }
                }
            }
        }
        double[][] out = new double[ra][ca];
        int r1 = rb / 2;
        int r2 = r1 + ra - 1;
        int c1 = cb / 2;
        int c2 = c1 + ca - 1;
        for (int i2 = r1; i2 < r2; ++i2) {
            for (int j = c1; j < c2; ++j) {
                out[i2 - r1 + 1][j - c1 + 1] = c[i2][j];
            }
        }
        return out;
    }

    double[][] normr(Vector X, Vector Y) {
        double[][] normalized = new double[X.size()][2];
        for (int i = 0; i < X.size(); ++i) {
            double temp3;
            double temp1 = (Double)X.get(i);
            double temp2 = (Double)Y.get(i);
            if ((temp1 *= temp1) + (temp2 *= temp2) == 0.0) {
                temp3 = 0.0;
            } else {
                temp3 = 1.0 / (temp1 + temp2);
                temp3 = Math.sqrt(temp3);
            }
            normalized[i][0] = temp3 * (Double)X.get(i);
            normalized[i][1] = temp3 * (Double)Y.get(i);
        }
        return normalized;
    }

    int[][] getpatch(int[][] pixelmap, int p) {
        int y = --p / this.ih;
        int x = (p %= this.ih) + 1;
        int temp1 = Math.max(x - this.w, 0);
        int temp2 = Math.min(x + this.w, this.ih - 1);
        int temp3 = Math.max(y - this.w, 0);
        int temp4 = Math.min(y + this.w, this.iw - 1);
        int[][] N = new int[temp4 - temp3 + 1][temp2 - temp1 + 1];
        for (int i = 0; i < temp4 - temp3 + 1; ++i) {
            for (int j = 0; j < temp2 - temp1 + 1; ++j) {
                N[i][j] = temp1 + j + (temp3 + i) * this.ih;
            }
        }
        return N;
    }

    int[] bestExemplar(int[][] Hp, double[][] toFill, int[][] sourceRegion, Boolean quickInpaint) {
        int mm;
        int endY;
        int startX;
        int endX;
        int startY;
        int nn;
        int[][] Ip = new int[toFill.length][toFill[0].length];
        for (int i = 0; i < toFill[0].length; ++i) {
            for (int j = 0; j < toFill.length; ++j) {
                int col = Hp[i][j] / this.ih;
                int row = Hp[i][j] % this.ih;
                Ip[j][i] = this.pixelmap[row][col];
            }
        }
        int[][] rIp = new int[Ip.length][Ip[0].length];
        int[][] gIp = new int[Ip.length][Ip[0].length];
        int[][] bIp = new int[Ip.length][Ip[0].length];
        for (int i2 = 0; i2 < Ip.length; ++i2) {
            for (int j = 0; j < Ip[0].length; ++j) {
                rIp[i2][j] = 255 & Ip[i2][j] >> 16;
                gIp[i2][j] = 255 & Ip[i2][j] >> 8;
                bIp[i2][j] = 255 & Ip[i2][j];
            }
        }
        int m = Ip.length;
        int n = Ip[0].length;
        if (quickInpaint.booleanValue()) {
            startX = Math.max(0, this.pixelPosX - n / 2 - this.continuousRow - 25);
            startY = Math.max(0, this.pixelPosY - m / 2 - this.continuousCol - 15);
            endX = Math.min(this.pixelmap[0].length - 1, this.pixelPosX + n / 2 + this.continuousRow + 25);
            endY = Math.min(this.pixelmap.length - 1, this.pixelPosY + m / 2 + this.continuousCol + 15);
            mm = endY - startY + 1;
            nn = endX - startX + 1;
        } else {
            mm = this.pixelmap.length;
            nn = this.pixelmap[0].length;
            startX = 0;
            startY = 0;
            endY = 0;
            endX = 0;
        }
        double patchErr = 0.0;
        double err = 0.0;
        double bestErr = 1.0E20;
        double bestPatchErr1 = 1.0E20;
        int[] best = new int[]{0, 0, 0, 0};
        Boolean skipPatchFlag = false;
        int N = startX + nn - n + 1;
        int M = startY + mm - m + 1;
        for (int j = startX; j < N; ++j) {
            int J = j + n - 1;
            for (int i3 = startY; i3 < M; ++i3) {
                int rImage;
                int ii2;
                int ii;
                int gImage;
                int bImage;
                double patchErr1;
                int I = i3 + m - 1;
                skipPatchFlag = false;
                double meanR = 0.0;
                double meanG = 0.0;
                double meanB = 0.0;
                int jj = j;
                int jj2 = 0;
                while (jj <= J) {
                    ii = i3;
                    ii2 = 0;
                    while (ii <= I) {
                        if (sourceRegion[ii][jj] != 1) {
                            skipPatchFlag = true;
                            break;
                        }
                        if (toFill[ii2][jj2] == 0.0) {
                            int rImage2 = 255 & this.pixelmap[ii][jj] >> 16;
                            int gImage2 = 255 & this.pixelmap[ii][jj] >> 8;
                            int bImage2 = 255 & this.pixelmap[ii][jj];
                            err = rImage2 - rIp[ii2][jj2];
                            patchErr += err * err;
                            err = gImage2 - gIp[ii2][jj2];
                            patchErr += err * err;
                            err = bImage2 - bIp[ii2][jj2];
                            patchErr += err * err;
                            meanR += (double)rImage2;
                            meanG += (double)gImage2;
                            meanB += (double)bImage2;
                        }
                        ++ii;
                        ++ii2;
                    }
                    if (skipPatchFlag.booleanValue()) break;
                    ++jj;
                    ++jj2;
                }
                if (!skipPatchFlag.booleanValue() && patchErr < bestErr) {
                    bestErr = patchErr;
                    best[0] = i3;
                    best[1] = I;
                    best[2] = j;
                    best[3] = J;
                    patchErr1 = 0.0;
                    jj = j;
                    jj2 = 0;
                    while (jj <= J) {
                        ii = i3;
                        ii2 = 0;
                        while (ii <= I) {
                            if (toFill[ii2][jj2] == 1.0) {
                                rImage = 255 & this.pixelmap[ii][jj] >> 16;
                                gImage = 255 & this.pixelmap[ii][jj] >> 8;
                                bImage = 255 & this.pixelmap[ii][jj];
                                err = (double)rImage - meanR;
                                patchErr1 += err * err;
                                err = (double)gImage - meanG;
                                patchErr1 += err * err;
                                err = (double)bImage - meanB;
                                patchErr1 += err * err;
                            }
                            ++ii;
                            ++ii2;
                        }
                        ++jj;
                        ++jj2;
                    }
                    bestPatchErr1 = patchErr1;
                } else if (!skipPatchFlag.booleanValue() && patchErr == bestErr) {
                    patchErr1 = 0.0;
                    jj = j;
                    jj2 = 0;
                    while (jj <= J) {
                        ii = i3;
                        ii2 = 0;
                        while (ii <= I) {
                            if (toFill[ii2][jj2] == 1.0) {
                                rImage = 255 & this.pixelmap[ii][jj] >> 16;
                                gImage = 255 & this.pixelmap[ii][jj] >> 8;
                                bImage = 255 & this.pixelmap[ii][jj];
                                err = (double)rImage - meanR;
                                patchErr1 += err * err;
                                err = (double)gImage - meanG;
                                patchErr1 += err * err;
                                err = (double)bImage - meanB;
                                patchErr1 += err * err;
                            }
                            ++ii;
                            ++ii2;
                        }
                        ++jj;
                        ++jj2;
                    }
                    if (bestPatchErr1 > patchErr1) {
                        best[0] = i3;
                        best[1] = I;
                        best[2] = j;
                        best[3] = J;
                        bestPatchErr1 = patchErr1;
                    }
                }
                patchErr = 0.0;
            }
        }
        if (best[0] == 0 && best[1] == 0 && best[2] == 0 && best[3] == 0) {
            return this.bestExemplar(Hp, toFill, sourceRegion, false);
        }
        return best;
    }
}
