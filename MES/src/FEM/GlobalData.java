package FEM;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Wojtek on 13.12.2017.
 */
public class GlobalData {

    double H,B; //wymiary siatki
    int nH,nB; //liczba węzłów
    double K, C, ro, tp, tot, alfa, tproc, dt;

    public GlobalData() throws FileNotFoundException {
        Scanner input = new Scanner(new File("dane.txt"));
        input.hasNextDouble();
        this.H = input.nextDouble();
        input.findInLine("H");
        this.B = input.nextDouble();
        input.findInLine("B");
        this.nH = input.nextInt();
        input.findInLine("nH");
        this.nB = input.nextInt();
        input.findInLine("nB");
        this.K = input.nextDouble();
        input.findInLine("K");
        this.C = input.nextDouble();
        input.findInLine("C");
        this.ro = input.nextDouble();
        input.findInLine("ro");
        this.tp = input.nextDouble();
        input.findInLine("tp");
        this.tot = input.nextDouble();
        input.findInLine("tot");
        this.alfa = input.nextDouble();
        input.findInLine("alfa");
        this.tproc = input.nextDouble();
        input.findInLine("tproc");
        this.dt = input.nextDouble();
        input.findInLine("dt");
        double asr = K/(C*ro);
        dt= Math.pow(B/nB,2)/(0.5*asr);
    }

    public double getH() { return H; }

    public void setH(double h) {
        H = h;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnB() {
        return nB;
    }

    public void setnB(int nB) {
        this.nB = nB;
    }

    public double getK() {
        return K;
    }

    public double getC() {
        return C;
    }

    public double getRo() {
        return ro;
    }

    public double getTp() {
        return tp;
    }

    public double getTot() {
        return tot;
    }

    public double getAlfa() {
        return alfa;
    }

    public double getTproc() {
        return tproc;
    }

    public double getDt() {
        return dt;
    }
}
