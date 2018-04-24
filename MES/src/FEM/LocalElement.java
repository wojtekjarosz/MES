package FEM;

import static java.lang.Math.sqrt;

/**
 * Created by Wojtek on 30.12.2017.
 */
public class LocalElement {
    LocalNode[] ID; // punkty całkowania [4]
    LocalNode[][] SURFACE; //powierzchnia [4][2] - 4 powierzchni po 2 punkty całkowania

    double derivative_dndksi[][]; //pochodne funkcji kształtu po ksie [4][4]
    double derivative_dndeta[][]; //...i po eta [4][4]
    double N[][]; //funkcje kształtu - [4][4] 4 punkty całkowania po 4 funkcje kształtu
    double Nsurf[][][]; // funkcje ksztaltu dla powierzchni [4][2][4] [pow][punkt_cal][funkcja_ksztaltu]

    public LocalElement() {
        ID = new LocalNode[4];
        ID[0] = new LocalNode(-1.0/sqrt(3.0),-1.0/sqrt(3.0));
        ID[1] = new LocalNode(-1.0/sqrt(3.0),1.0/sqrt(3.0));
        ID[2] = new LocalNode(1.0/sqrt(3.0),-1.0/sqrt(3.0));
        ID[3] = new LocalNode(1.0/sqrt(3.0),1.0/sqrt(3.0));

        //4 powierzchnie w elemencie ( po 2 punkty całkowania dla każdej powierzchni)
        SURFACE = new LocalNode[4][2];
        SURFACE[0][0] = new LocalNode(-1.0, 1.0 / sqrt(3.0));
        SURFACE[0][1] = new LocalNode(-1.0, -1.0 / sqrt(3.0));
        SURFACE[1][0] = new LocalNode(-1.0 / sqrt(3.0), -1.0);
        SURFACE[1][1] = new LocalNode(1.0 / sqrt(3.0), -1.0);
        SURFACE[2][0] = new LocalNode(1.0, -1.0 / sqrt(3.0));
        SURFACE[2][1] = new LocalNode(1.0, 1.0 / sqrt(3.0));
        SURFACE[3][0] = new LocalNode(1.0 / sqrt(3.0), 1.0);
        SURFACE[3][1] = new LocalNode(-1.0 / sqrt(3.0), 1.0);

        derivative_dndksi = new double[4][4];
        derivative_dndeta = new double[4][4];
        N = new double[4][4];
        Nsurf = new double[4][2][4];

        for (int i = 0; i < 4; i++) {
            //funkcje kształtu
            N[i][0] = N1(ID[i].getKsi(), ID[i].getEta());
            N[i][1] = N2(ID[i].getKsi(), ID[i].getEta());
            N[i][2] = N3(ID[i].getKsi(), ID[i].getEta());
            N[i][3] = N4(ID[i].getKsi(), ID[i].getEta());
            //pochodne funkcji kształtu po ksi
            derivative_dndksi[i][0] = dN1_dKsi(ID[i].getEta());
            derivative_dndksi[i][1] = dN2_dKsi(ID[i].getEta());
            derivative_dndksi[i][2] = dN3_dKsi(ID[i].getEta());
            derivative_dndksi[i][3] = dN4_dKsi(ID[i].getEta());
            //...i po eta
            derivative_dndeta[i][0] = dN1_dEta(ID[i].getKsi());
            derivative_dndeta[i][1] = dN2_dEta(ID[i].getKsi());
            derivative_dndeta[i][2] = dN3_dEta(ID[i].getKsi());
            derivative_dndeta[i][3] = dN4_dEta(ID[i].getKsi());

        }

        //macierze funkcji ksztaltu dla powierzchni
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<2;j++){
                Nsurf[i][j][0] = N1(SURFACE[i][j].getKsi(), SURFACE[i][j].getEta());
                Nsurf[i][j][1] = N2(SURFACE[i][j].getKsi(), SURFACE[i][j].getEta());
                Nsurf[i][j][2] = N3(SURFACE[i][j].getKsi(), SURFACE[i][j].getEta());
                Nsurf[i][j][3] = N4(SURFACE[i][j].getKsi(), SURFACE[i][j].getEta());
            }
        }

    }
    //getters
    public double getDerivative_dndksi(int x, int y) {
        return derivative_dndksi[x][y];
    }
    double getDerivative_dndeta(int x, int y) {
        return derivative_dndeta[x][y];
    }
    public double getN(int i, int j){
        return N[i][j];
    }
    double getNsurf(int i, int j, int k){
        return Nsurf[i][j][k];
    }
    //funkcje kształtu
    double N1(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 - eta);
    }
    double N2(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 - eta);
    }
    double N3(double ksi, double eta) {
        return 0.25 * (1 + ksi) * (1 + eta);
    }
    double N4(double ksi, double eta) {
        return 0.25 * (1 - ksi) * (1 + eta);
    }
    //pochodne funkcji kształtu po ksi
    double dN1_dKsi(double eta) {
        return -0.25 * (1 - eta);
    }
    double dN2_dKsi(double eta) {
        return 0.25 * (1 - eta);
    }
    double dN3_dKsi(double eta) {
        return 0.25 * (1 + eta);
    }
    double dN4_dKsi(double eta) {
        return -0.25 * (1 + eta);
    }
    //...i po eta
    double dN1_dEta(double ksi) {
        return -0.25 * (1 - ksi);
    }
    double dN2_dEta(double ksi) { return -0.25 * (1 + ksi);}
    double dN3_dEta(double ksi) {
        return 0.25 * (1 + ksi);
    }
    double dN4_dEta(double ksi) {
        return 0.25 * (1 - ksi);
    }
}

