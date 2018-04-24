package FEM;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {

        Pane root = new Pane();

        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            drawLines(gc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 537, 537, Color.WHITESMOKE);

        stage.setTitle("MES");
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
    //public static void main(String[] args) throws FileNotFoundException {
    private void drawLines(GraphicsContext gc) throws FileNotFoundException {
        //FEM.Grid grid(globalData.getnH(), globalData.getnB(), globalData.getH(),globalData.getB());
        GlobalData globalData = new GlobalData();
        Grid grid = new Grid(globalData.getnH(), globalData.getnB());
        grid.setGlD(globalData);
        grid.generate(globalData.getnH(), globalData.getnB(), globalData.getH(), globalData.getB());
        grid.printGrid();

        double[] x = new double[4];
        double[] y = new double[4];
        int size = globalData.getnH() * globalData.getnB(); //ilosc wezlow w siatce

        double[][] H = new double[size][size];
        double[] P = new double[size];

        double[][] dn_dx = new double[4][4];
        double[][] dn_dy = new double[4][4];
        double[][] tmp_H = new double[4][4];
        double[] tmp_P = new double[4];
        double t0 = 0.0, c_matrix_i = 0.0;
        double[] t1 = new double[size];
        double dt = globalData.getDt();

        //petla po czasie3
        for (double t = 0.0; t <globalData.getTproc(); t += dt) {
            double max=0.0, min= Double.MAX_VALUE;
            for (int m = 0; m < size; m++)
                for (int n = 0; n < size; n++)
                    H[m][n] = 0.0;
            for (int n = 0; n < size; n++)
                P[n] = 0.0;

            //petla po elementach
            for (int el = 0; el < grid.getNe(); el++) {
                for (int m = 0; m < 4; m++) {
                    for (int n = 0; n < 4; n++) {
                        tmp_H[m][n] = 0.0;
                    }
                    tmp_P[m] = 0.0;
                }

                for (int k = 0; k < 4; k++) {
                    x[k] = grid.getNE(el).getID(k).getX();
                    y[k] = grid.getNE(el).getID(k).getY();
                }

                LocalElement locEl = new LocalElement();
                //petla po punktach całkowania - intPoint
                for (int intPoint = 0; intPoint < 4; intPoint++) {
                    t0 = 0;
                    Jacobian jacobian = new Jacobian(locEl, intPoint, x, y);
                    for (int l = 0; l < 4; l++) {
                        dn_dx[intPoint][l] = jacobian.getDerivative_dndx(intPoint)[l];
                        dn_dy[intPoint][l] = jacobian.getDerivative_dndy(intPoint)[l];
                        t0 += grid.getNE(el).getID(l).getT() * locEl.getN(intPoint, l);
                    }
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            c_matrix_i = globalData.getC() * globalData.getRo() * jacobian.getLocEl().getN(intPoint, i) * jacobian.getLocEl().getN(intPoint, j) * jacobian.getDet();
                            tmp_H[i][j] += (dn_dx[intPoint][i] * dn_dx[intPoint][j] + dn_dy[intPoint][i] * dn_dy[intPoint][j]) * globalData.getK() * jacobian.getDet() + c_matrix_i / globalData.getDt();
                            tmp_P[i] += c_matrix_i / globalData.getDt() * t0;
                        }
                    }
                }
                double detSurface = 0.0;
                //petla po powierzchniach - warunki brzegowe
                for (int i = 0; i < 4; i++) {

                    if (grid.getNE(el).getIsExternalSurface(i)) { //jeśli powierzchnia spełnia warunki brzegowe (pow. zew)
                        switch (i) {
                            case 0:
                                detSurface = sqrt(pow(grid.getNE(el).getID(3).getX() - grid.getNE(el).getID(0).getX(), 2)
                                        + pow(grid.getNE(el).getID(3).getY() - grid.getNE(el).getID(0).getY(), 2)) / 2.0;
                                break;
                            case 1:
                                detSurface = sqrt(pow(grid.getNE(el).getID(1).getX() - grid.getNE(el).getID(0).getX(), 2)
                                        + pow(grid.getNE(el).getID(1).getY() - grid.getNE(el).getID(0).getY(), 2)) / 2.0;
                                break;
                            case 2:
                                detSurface = sqrt(pow(grid.getNE(el).getID(2).getX() - grid.getNE(el).getID(1).getX(), 2)
                                        + pow(grid.getNE(el).getID(2).getY() - grid.getNE(el).getID(1).getY(), 2)) / 2.0;
                                break;
                            case 3:
                                detSurface = sqrt(pow(grid.getNE(el).getID(3).getX() - grid.getNE(el).getID(2).getX(), 2)
                                        + pow(grid.getNE(el).getID(3).getY() - grid.getNE(el).getID(2).getY(), 2)) / 2.0;
                                break;
                        }

                        for (int pc = 0; pc < 2; pc++) {
                            for (int m = 0; m < 4; m++) {
                                for (int n = 0; n < 4; n++) {
                                    tmp_H[m][n] += globalData.getAlfa() * locEl.getNsurf(i, pc, m) * locEl.getNsurf(i, pc, n) * detSurface;
                                }
                                tmp_P[m] += globalData.getAlfa() * globalData.getTot() * locEl.getNsurf(i, pc, m) * detSurface;
                            }

                        }
                    }

                }
                System.out.print("");
                //wypisanie lokalnej macierzy H i lokalnego wektora P dla danego elementu
           /* System.out.println("Element: "+el);
            System.out.println("Macierz H: ");
            for(int m=0; m<4;m++){
                for(int n=0;n<4;n++){
                    System.out.print(tmp_H[m][n]+ " ");
                }
                System.out.println("");
            }
            System.out.println("Wektor P: ");
            for(int n=0;n<4;n++){
                System.out.print( tmp_P[n]+ " ");
            }
            System.out.println("");*/
                //agregacja do globalnej macierzy H i do globalnego wektora P
                for (int m = 0; m < 4; m++) {
                    for (int n = 0; n < 4; n++) {
                        H[grid.getNE(el).getgID(m)][grid.getNE(el).getgID(n)] += tmp_H[m][n];
                    }
                    P[grid.getNE(el).getgID(m)] += tmp_P[m];
                }
            } //koniec pętli po elementach

            //wypisanie globalnej macierzy H i wektora P
            /*System.out.println("MACIERZ H");
            for (int m = 0; m < size; m++) {
                for (int n = 0; n < size; n++) {
                    System.out.printf("%.6f\t", H[m][n]);
                }
                System.out.println("");
            }

            System.out.println("WEKTOR P");
            for (int m = 0; m < size; m++) {
                System.out.printf("%.6f\t", P[m]);
            }
            System.out.println("");*/

            t1 = GaussianElimination.lsolve(H,P);  //rozwiązanie ukłdu równać  [H]{t} = {P}
            //znalezienie min i max temperatury w danej iteracji
            for(int i=0;i<size;i++){
                grid.getND(i).setT(t1[i]);
                if(!grid.getND(i).isInside()) {
                    if (t1[i] < min) min = t1[i];
                    if (t1[i] > max) max = t1[i];
                }
            }
            int k=0;
            System.out.println("Temperatures: "+ t ); // temeratury w danej iteracji
            for (int m = 0; m < globalData.getnH(); m++) {
                for (int n = 0; n < globalData.getnB(); n++) {
                    System.out.printf("%.3f\t",grid.getND(k).getT());
                    k++;
                }
                System.out.println("");
            }
            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,537,537);


                System.out.print("Time: ");
                System.out.printf("%.3f", (t + dt));
                System.out.print(" Max: ");
                System.out.printf("%.3f", max);
                System.out.print(" Min: ");
                System.out.printf("%.3f\n", min);

            //naniesienie temperatu na canvas
            for(int i=0;i<size;i++){
                int x2 = (int) ((grid.getND(i).getX() * 500) * 3);
                int y2 = (int) ((grid.getND(i).getY() * 500) * 3);
                if(grid.getND(i).isInside()){
                    gc.setFill(Color.rgb(0, 0, 0));
                    gc.fillRect(x2, y2, 3, 3);
                }else {
                    double temp = (grid.getND(i).getT());
                    double blue = 255 - (255 * (temp / 300));
                    double red = 255 * (temp / 300);
                    gc.setFill(Color.rgb((int) red, 0, (int) blue));
                    //gc.setFill(Color.rgb( 200,  100, 0));

                    // bi.setRGB(x2,y2,java.awt.Color.BLUE.getRGB());
                    gc.fillRect(x2, y2, 3, 3);
                /*if((x2<340)&&(y2<354))*/
                    gc.fillRect(x2, y2, 5, 5);
                }
            }
        }
    }
}