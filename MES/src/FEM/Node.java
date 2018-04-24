package FEM;

/**
 * Created by Wojtek on 13.12.2017.
 */
public class Node {
    double x,y;
    double t;
    boolean status;
    boolean inside;
    int ID[];
    public Node() {
        ID = new int[2];
        inside=false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public int getID(int i) {
        return ID[i];
    }

    public void setID(int i, int j) {
        ID[0]=i;
        ID[1]=j;
    }

    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }
}
