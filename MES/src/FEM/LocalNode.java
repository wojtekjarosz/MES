package FEM;

/**
 * Created by Wojtek on 30.12.2017.
 */
public class LocalNode {  // lokalne współrzędne punktu całkowania
    double ksi;
    double eta;

    public LocalNode(double ksi, double eta) {
        this.ksi = ksi;
        this.eta = eta;
    }

    public double getKsi() {
        return ksi;
    }

    public double getEta() {
        return eta;
    }
}
