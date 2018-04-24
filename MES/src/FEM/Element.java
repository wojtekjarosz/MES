package FEM;

/**
 * Created by Wojtek on 13.12.2017.
 */
public class Element {
    public Node[] ID;
    public double[] walls;
    boolean[] isExternalSurface;
    public int[] gID; //global ID

    public Element() {
        ID = new Node[4];
        walls = new double[4];
        isExternalSurface = new boolean[4];
        gID = new int[4];
    }

    void setID(Node a, Node b, Node c, Node d){
        ID[0]=a; ID[1]=b; ID[2]=c; ID[3]=d;
    }

    Node getID(int i){
        return ID[i];
    }

    public void setWalls(double a, double b, double c, double d){
        walls[0]=a; walls[1]=b; walls[2]=c; walls[3]=d;
    }


    public boolean getIsExternalSurface(int i) {
        return isExternalSurface[i];
    }

    public void setIsExternalSurface(int i, boolean b) {
        isExternalSurface[i] = b;
    }

    public int getgID(int i) {
        return gID[i];
    }

    public void setgID(int a, int b, int c, int d){
        gID[0]=a; gID[1]=b; gID[2]=c; gID[3]=d;
    }
}
