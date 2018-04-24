package FEM;

/**
 * Created by Wojtek on 30.12.2017.
 */
public class Jacobian {

    double J[][];
    double det;
    int intPoint;
    LocalElement loc_el;

    public Jacobian( LocalElement loc_el, int intPoint, double []x, double []y) {
        J = new double[2][2];
        this.intPoint = intPoint;
        this.loc_el = loc_el;
        //macierz Jakobiego
        J[0][0] = loc_el.getDerivative_dndksi(intPoint,0) * x[0] + loc_el.getDerivative_dndksi(intPoint,1) * x[1]
                + loc_el.getDerivative_dndksi(intPoint,2) * x[2] + loc_el.getDerivative_dndksi(intPoint,3) * x[3];
        J[0][1] = loc_el.getDerivative_dndksi(intPoint,0) * y[0] + loc_el.getDerivative_dndksi(intPoint,1) * y[1]
                + loc_el.getDerivative_dndksi(intPoint,2) * y[2] + loc_el.getDerivative_dndksi(intPoint,3) * y[3];
        J[1][0] = loc_el.getDerivative_dndeta(intPoint,0) * x[0] + loc_el.getDerivative_dndeta(intPoint,1) * x[1]
                + loc_el.getDerivative_dndeta(intPoint,2) * x[2] + loc_el.getDerivative_dndeta(intPoint,3) * x[3];
        J[1][1] = loc_el.getDerivative_dndeta(intPoint,0) * y[0] + loc_el.getDerivative_dndeta(intPoint,1) * y[1]
                + loc_el.getDerivative_dndeta(intPoint,2) * y[2] + loc_el.getDerivative_dndeta(intPoint,3) * y[3];
        det = J[0][0] * J[1][1] - J[0][1] * J[1][0]; //wyznacznik
    }
    //wydrukowanie macierzy Jakobiego dla punktu całkowania
    void printJacobian() {
        System.out.println("Jakobian punktu calkowania: " + intPoint);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(J[i][j] + " \t");
            }
            System.out.println("");
        }
        System.out.println("Det: " + det + "\n");
    }

    double[] getDerivative_dndx(int intPoint){
        double[] dndx = new double[4];
        for(int i=0;i<4;i++){
            dndx[i]= ((J[1][1]*loc_el.getDerivative_dndksi(intPoint,i))-(J[0][1]*loc_el.getDerivative_dndeta(intPoint,i)))/det;
        }
        return dndx;
    }
    double[] getDerivative_dndy(int intPoint){
        double[] dndy = new double[4];
        for(int i=0;i<4;i++){
            dndy[i]= ((J[0][0]*loc_el.getDerivative_dndeta(intPoint,i))-(J[1][0]*loc_el.getDerivative_dndksi(intPoint,i)))/det;
        }
        return dndy;
    }

    public double getDet(){
        return det;
    }
    public LocalElement getLocEl(){
        return loc_el;
    }


}
