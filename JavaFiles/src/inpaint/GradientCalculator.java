/*
 * Decompiled with CFR 0_114.
 */
package inpaint;

import java.util.Vector;

public class GradientCalculator {
    public double[][] gradientX;
    public double[][] gradientY;
    int r;
    int g;
    int b;

    public void calculateGradientFromImage(int[][] pixelmap, int ih, int iw) {
        int g2;
        int r2;
        int j;
        int bn1;
        int g1;
        double t3;
        int b2;
        int b1;
        int r1;
        int j2;
        int gn1;
        int b22;
        int r12;
        int i;
        int rn;
        int bn;
        int gn;
        int b12;
        int rn1;
        double t2;
        int g22;
        int r22;
        int g12;
        int i2;
        this.gradientX = new double[ih][iw];
        this.gradientY = new double[ih][iw];
        int[] row = new int[ih];
        int[] column = new int[iw];
        for (i2 = 0; i2 < ih; ++i2) {
            row[i2] = i2 + 1;
        }
        for (i2 = 0; i2 < iw; ++i2) {
            column[i2] = i2 + 1;
        }
        if (ih > 1) {
            for (j = 0; j < iw; ++j) {
                this.extractRGB(pixelmap, 1, j);
                r22 = this.r;
                g22 = this.g;
                b2 = this.b;
                this.extractRGB(pixelmap, 0, j);
                r12 = this.r;
                g12 = this.g;
                b1 = this.b;
                double t1 = (double)(r22 - r12) / (double)(row[1] - row[0]);
                t2 = (double)(g22 - g12) / (double)(row[1] - row[0]);
                t3 = (double)(b2 - b1) / (double)(row[1] - row[0]);
                this.gradientX[0][j] = (- t1 + t2 + t3) / 765.0;
                this.extractRGB(pixelmap, ih - 1, j);
                rn = this.r;
                gn = this.g;
                bn = this.b;
                this.extractRGB(pixelmap, ih - 2, j);
                rn1 = this.r;
                gn1 = this.g;
                bn1 = this.b;
                t1 = (double)(rn - rn1) / (double)(row[ih - 1] - row[ih - 2]);
                t2 = (double)(gn - gn1) / (double)(row[ih - 1] - row[ih - 2]);
                t3 = (double)(bn - bn1) / (double)(row[ih - 1] - row[ih - 2]);
                this.gradientX[ih - 1][j] = (- t1 + t2 + t3) / 765.0;
            }
        }
        if (ih > 2) {
            for (i = 1; i < ih - 1; ++i) {
                for (j2 = 0; j2 < iw; ++j2) {
                    this.extractRGB(pixelmap, i + 1, j2);
                    r2 = this.r;
                    g2 = this.g;
                    b22 = this.b;
                    this.extractRGB(pixelmap, i - 1, j2);
                    r1 = this.r;
                    g1 = this.g;
                    b12 = this.b;
                    double t1 = (double)(r2 - r1) / (double)(row[i + 1] - row[i - 1]);
                    t2 = (double)(g2 - g1) / (double)(row[i + 1] - row[i - 1]);
                    t3 = (double)(b22 - b12) / (double)(row[i + 1] - row[i - 1]);
                    this.gradientX[i][j2] = (- t1 + t2 + t3) / 765.0;
                }
            }
        }
        if (iw > 1) {
            for (j = 0; j < ih; ++j) {
                this.extractRGB(pixelmap, j, 1);
                r22 = this.r;
                g22 = this.g;
                b2 = this.b;
                this.extractRGB(pixelmap, j, 0);
                r12 = this.r;
                g12 = this.g;
                b1 = this.b;
                double t1 = (double)(r22 - r12) / (double)(column[1] - column[0]);
                t2 = (double)(g22 - g12) / (double)(column[1] - column[0]);
                t3 = (double)(b2 - b1) / (double)(column[1] - column[0]);
                this.gradientY[j][0] = (t1 + t2 + t3) / 765.0;
                this.extractRGB(pixelmap, j, iw - 1);
                rn = this.r;
                gn = this.g;
                bn = this.b;
                this.extractRGB(pixelmap, j, iw - 2);
                rn1 = this.r;
                gn1 = this.g;
                bn1 = this.b;
                t1 = (double)(rn - rn1) / (double)(column[iw - 1] - column[iw - 2]);
                t2 = (double)(gn - gn1) / (double)(column[iw - 1] - column[iw - 2]);
                t3 = (double)(bn - bn1) / (double)(column[iw - 1] - column[iw - 2]);
                this.gradientY[j][iw - 1] = (t1 + t2 + t3) / 765.0;
            }
        }
        if (iw > 2) {
            for (i = 0; i < ih; ++i) {
                for (j2 = 1; j2 < iw - 1; ++j2) {
                    this.extractRGB(pixelmap, i, j2 + 1);
                    r2 = this.r;
                    g2 = this.g;
                    b22 = this.b;
                    this.extractRGB(pixelmap, i, j2 - 1);
                    r1 = this.r;
                    g1 = this.g;
                    b12 = this.b;
                    double t1 = (double)(r2 - r1) / (double)(column[j2 + 1] - column[j2 - 1]);
                    t2 = (double)(g2 - g1) / (double)(column[j2 + 1] - column[j2 - 1]);
                    t3 = (double)(b22 - b12) / (double)(column[j2 + 1] - column[j2 - 1]);
                    this.gradientY[i][j2] = (t1 + t2 + t3) / 765.0;
                }
            }
        }
    }

    public void calculateGradient(int[][] a, int ih, int iw) {
        int j;
        int i;
        int j2;
        this.gradientX = new double[ih][iw];
        this.gradientY = new double[ih][iw];
        Vector h = new Vector();
        int[] row = new int[ih];
        int[] column = new int[iw];
        for (i = 0; i < ih; ++i) {
            row[i] = i + 1;
        }
        for (i = 0; i < iw; ++i) {
            column[i] = i + 1;
        }
        if (ih > 1) {
            for (j2 = 0; j2 < iw; ++j2) {
                this.gradientY[0][j2] = (double)(a[1][j2] - a[0][j2]) / (double)(row[1] - row[0]);
                this.gradientY[ih - 1][j2] = (double)(a[ih - 1][j2] - a[ih - 2][j2]) / (double)(row[ih - 1] - row[ih - 2]);
            }
        }
        if (ih > 2) {
            for (i = 1; i < ih - 1; ++i) {
                for (j = 0; j < iw; ++j) {
                    this.gradientY[i][j] = (double)(a[i + 1][j] - a[i - 1][j]) / (double)(row[i + 1] - row[i - 1]);
                }
            }
        }
        if (iw > 1) {
            for (j2 = 0; j2 < ih; ++j2) {
                this.gradientX[j2][0] = (double)(a[j2][1] - a[j2][0]) / (double)(column[1] - column[0]);
                this.gradientX[j2][iw - 1] = (double)(a[j2][iw - 1] - a[j2][iw - 2]) / (double)(column[iw - 1] - column[iw - 2]);
            }
        }
        if (iw > 2) {
            for (i = 0; i < ih; ++i) {
                for (j = 1; j < iw - 1; ++j) {
                    this.gradientX[i][j] = (double)(a[i][j + 1] - a[i][j - 1]) / (double)(column[j + 1] - column[j - 1]);
                }
            }
        }
    }

    void extractRGB(int[][] img, int i, int j) {
        this.r = 255 & img[i][j] >> 16;
        this.g = 255 & img[i][j] >> 8;
        this.b = 255 & img[i][j];
    }
}
