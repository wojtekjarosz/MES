package Integration;
import FEM.*;
import java.io.FileNotFoundException;

/**
 * Created by Wojtek on 10.01.2018.
 */
public class Integration {
    double []weights;
    double []points;

    public Integration(double[] weights, double[] points) {
        this.weights = weights;
        this.points = points;
    }

    double function(double x, double y){
        return ((2*x*x*y*y) + (6*x) + 5);
    }

    double function2(double x){
        return (3*x*x + 2*x + 5);
    }

    double countInt2D(double a1, double b1, double a2, double b2){
        double[] x;
        double[] y;
        double local_x = 0;
        double local_y = 0;
        x = new double[]{a1, b1, b1, a1};
        y = new double[]{a2, a2, b2, b2};
        LocalElement locEl = new LocalElement();
        double sum=0.0;

        for( int k=0; k<4; k++) {
            local_x = 0;
            local_y  =0;
            Jacobian jacobian = new Jacobian(locEl,k,x,y);
            for (int j = 0; j < 4; j++) {
                local_x +=jacobian.getLocEl().getN(k,j)*x[j];
                local_y +=jacobian.getLocEl().getN(k,j)*y[j];
            }
                sum += (function(local_x, local_y) *jacobian.getDet());
        }
        return sum;
    }

    double countInt1D(int a, int b){
        double sum = 0.0;
        double det = (b-a)/2;
        for(int i=0; i<points.length; i++){
            double r = ((1-points[i])*a/2) + ((1+points[i])*b/2);
            sum+=(function2(r)*weights[i]*det);
        }
        return sum;
    }
}
