package FEM;

import java.io.FileNotFoundException;

/**
 * Created by Wojtek on 13.12.2017.
 */
public class Grid {

    int nh; //liczba węzłów
    int ne; //liczba elementów
    Node[] ND; //dynamiczna tablica węzłów
    Element[] NE; //dynamiczna tablica elementow
    GlobalData glD;
    Grid(int nH, int nB) throws FileNotFoundException {
        //glD = new FEM.GlobalData();
        nh = nH*nB;
        ne = (nH - 1)*(nB - 1);
        ND = new Node[nh];
        NE = new Element[ne];
        for(int i=0;i<ND.length;i++)
            ND[i]=new Node();
        for(int i=0;i<NE.length;i++)
            NE[i]=new Element();
        //generate(glD.getnH(),glD.getnB(), glD.getH(), glD.getB());
    }
    //funkcja generująca siatkę
    void generate(int nH, int nB, double H, double B){
        int k=0;
        double elH = H/(nH-1); //wysokość pojedynczego elementu
        double elB = B/(nB-1); //szerokość pojedynczego elementu
        int wall_width=10;
        for(int i=0; i<nB;i++){
            for(int j=0; j<nH; j++){
                ND[k].setX(i * elB);
                ND[k].setY(j * elH);
                ND[k].setID(i, j);
                if(i>=wall_width && j>=wall_width && i<= nB-wall_width-1 && j<=nH-wall_width-1) {
                    ND[k].setT(glD.getTot());
                    ND[k].setInside(true);
                    if(i==wall_width || j==wall_width || i== nB-wall_width-1 || j==nH-wall_width-1){
                        ND[k].setStatus(true);
                        ND[k].setT(glD.getTp());
                    }else{
                        ND[k].setStatus(false);
                    }
                }else{

                    ND[k].setT(glD.getTp());
                    ND[k].setStatus(false);
                    /*if ((i == 0 || i == (nB - 1)) || (j == 0 || j == (nH - 1))) {
                        ND[k].setStatus(true);
                    } else {
                        ND[k].setStatus(false);
                    }*/

                }
                k++;
            }
        }
        k=0;int l=0;
        for(int i=0;i<(nh-nH-1);i++){
            if(i!=(l+nH-1) ){
                NE[k].setID(ND[i],ND[i+nH],ND[i+nH+1],ND[i+1]);
                k++;
            } else l+=nH;
        }
        //isExternalSurface = true, jeśli powierznia jest zewnętrzna
        for(int i=0 ; i<ne;i++){
            if(NE[i].getID(0).getStatus()==true && NE[i].getID(1).getStatus()==true) NE[i].setIsExternalSurface(1,true); else NE[i].setIsExternalSurface(1,false);
            if(NE[i].getID(1).getStatus()==true && NE[i].getID(2).getStatus()==true) NE[i].setIsExternalSurface(2,true); else NE[i].setIsExternalSurface(2,false);
            if(NE[i].getID(2).getStatus()==true && NE[i].getID(3).getStatus()==true) NE[i].setIsExternalSurface(3,true); else NE[i].setIsExternalSurface(3,false);
            if(NE[i].getID(3).getStatus()==true && NE[i].getID(0).getStatus()==true) NE[i].setIsExternalSurface(0,true); else NE[i].setIsExternalSurface(0,false);


        }
        for(int i=0;i<(ne);i++){
            NE[i].setWalls(elH,elB,elB,elH);
        }
        k=0;
        //ustawienie globalnych współrzędnych węzłów
        for(int m=0; m<(nB-1);m++){
            for(int n=0; n<(nH-1); n++) {
                NE[k].setgID(nH*m+n,nH*(m+1)+n, nH*(m+1)+(n+1),nH*m+(n+1) );
                k++;
            }
        }

    }
    public void printGrid(){
        int k=0;
	    for(int i=0; i<glD.getnB();i++){
		    for(int j=0; j<glD.getnH(); j++){
                System.out.print("(");
                System.out.printf("%.3f",ND[k].getX());
                System.out.print(";");
                System.out.printf("%.3f",ND[k].getY());
                System.out.print(") ");
				k++;
			}
            System.out.println("");
        }

       /* for(int i=0;i<ne;i++){
            System.out.println("El "+i+": ");
            for(int j=0;j<4;j++){
                System.out.print(NE[i].getID(j).getX() +","+NE[i].getID(j).getY() + " ");
            }
            System.out.println("");
        }*/

    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public int getNe() {
        return ne;
    }

    public void setNe(int ne) {
        this.ne = ne;
    }

    public Node getND(int i) {
        return ND[i];
    }

    public void setND(Node[] ND) {
        this.ND = ND;
    }

    public Element getNE(int i) {
        return NE[i];
    }

    public void setNE(Element[] NE) {
        this.NE = NE;
    }

    public void setGlD(GlobalData glD) {
        this.glD = glD;
    }
}
