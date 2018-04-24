package Integration;

import java.io.FileNotFoundException;

/**
 * Created by Wojtek on 10.01.2018.
 */
public class CountIntegration {

    public static void main(String[] args) throws FileNotFoundException {
        int size = 3;
        double []points = new double[3];
        double []weights = new double[3];

        weights[0] = 0.5555;
        weights[1] = 0.8889;
        weights[2] = 0.5555;

        points[0] = -0.774;
        points[1] = 0.0;
        points[2] = 0.774;
        Integration integration3p = new Integration(weights, points);
        System.out.println("Wynik 1D: "+ integration3p.countInt1D(2,10)+ ", dla 3 punktów");

        //dodać zakres
        //System.out.println("Wynik 2D: " +integration1D.countInt2D(int a1, int b1, int b1, int b2)+ ", dla 3 punktów");

	double []points2 = new double[2];
	double []weights2 = new double[2];

        weights2[0] = 1;
        weights2[1] = 1;

        points2[0] = -0.577;
        points2[1] = 0.577;
        Integration integration2p = new Integration(weights2, points2);
        System.out.println("Wynik 1D: " + integration2p.countInt1D(2, 10)+ ", dla 2 punktow");
        //dodac zakres
        System.out.println("Wynik 2D: " + integration2p.countInt2D(2, 4,3,5)+ ", dla 2 punktow");


    }
}
