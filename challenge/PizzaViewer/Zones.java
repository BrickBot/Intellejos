/*
 * SimpleSolver.java
 *
 * Created on February 8, 2005, 12:55 PM
 */


/**
 *
 * @author kees
 */
public class Zones implements Solver {

    /** Creates a new instance of Zones */
    public Zones() {
    }

    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]);   // starting point should not change

        //Define variables
        int nearest = (cities[cities.length-1].x + cities[cities.length-1].y); //shortest distance at the moment
        int nearindex = (cities.length-1);
        int nearx = cities[cities.length-1].x;
        int neary = cities[cities.length-1].y;
        int index = 2;


        int iMax = 0;
        float maxX = (cities[0].x);
        float maxY = (cities[0].y);

        int Een = 0;
        int Twee = 0;
        int Drie = 0;
        int Vier = 0;

        float divideX;
        float divideY;


        //Find the nearest point to (0,0)
        for (int t = 1; t < cities.length; t++) {
            if ((cities[t].x + cities[t].y) < nearest ) {
                nearx = cities[t].x;
                neary = cities[t].y;
                nearest = (cities[t].x + cities[t].y); //set new coördinate of shortest point
                nearindex = t;
            }
            else {
                if (nearest == (cities[t].x + cities[t].y)) {
                    if (cities[t].y > neary) {
                        nearx = cities[t].x;
                        neary = cities[t].y;
                        nearest = (cities[t].x + cities[t].y); //set new coördinate of shortest point
                        nearindex = t;
                    }
                }
            } //end: else
        } //end: for (int t=1; t<cities.length; t++) {
        route[1] = cities[nearindex];
        System.out.println("The nearest point to (0,0): (" + cities[nearindex].x + ", " + cities[nearindex].y + ")") ;


        //Find the farrest point
        for (int i = 1; i < cities.length; i++) {
            if (maxX < (cities[i].x)){
                maxX = (cities[i].x);
                iMax = i;
            }
        }
        for (int j = 1; j < cities.length; j++) {
            if (maxY < (cities[j].y)){
                maxY = (cities[j].y);
            }
        }

       divideX = (maxX / 2);
       divideY = (maxY / 2);

        for (int k = 1; k < cities.length; k++) {

               if (cities[k].x <= divideX && cities[k].y <= divideY){
                    Een++;
               }

               else if (cities[k].x < divideX && cities[k].y > divideY) {
                        Twee++;
               }

               else if (cities[k].x >= divideX && cities[k].y >= divideY) {
                        Drie++;
               }

               else if (cities[k].x > divideX && cities[k].y < divideY) {
                        Vier++;
               }

       } // end: for (int k=1; k<route.length; k++)

        System.out.println("The imaginary grid is " + (maxX) + " by " + (maxY));                             // Prints the size of the imaginary grid
        System.out.println("In kwart een liggen " + (Een) + " punten");
        System.out.println("In kwart twee liggen " + (Twee) + " punten");
        System.out.println("In kwart drie liggen " + (Drie) + " punten");
        System.out.println("In kwart vier liggen " + (Vier) + " punten");


        //Set coördinates to every zone
        int c = 0;


        PointP[] kwartEen = new PointP[Een];
        for (int p = 1; p < cities.length; p++){
           if (cities[p].x <= divideX && cities[p].y <= divideY){
               kwartEen[c] = cities[p];
               System.out.println("The coordinates of the points in quarter one is/are ( " + (kwartEen[c].x) + ", " + (kwartEen[c].y) + " )");
               c++;
           }
        } //end: for (int p = 0; p < route.length; p++){
        if (Een > 0) {
            for (int v = 0; v < kwartEen.length; v++) {
                if (kwartEen[v] != route[1]) {
                    route[index] = kwartEen[v];
                    index++;
                }
            }
        }

        if (Twee > 0) {
            PointP[] kwartTwee = new PointP[Twee];
            c = 0;
            for (int q = 1; q < cities.length; q++){
                if (cities[q].x < divideX && cities[q].y > divideY) {
                    kwartTwee[c] = cities[q];
                    System.out.println("The coordinates of the points in quarter two is/are ( " + (kwartTwee[c].x) + ", " + (kwartTwee[c].y) + " )");
                    c++;
                }
            } //end: for (int q = 0; q < route.length; q++){
            for (int v = 0; v < kwartTwee.length; v++) {
                if (kwartTwee[v] != route[1]) {
                    route[index] = kwartTwee[v];
                    index++;
                }
            }
        }

        if (Drie > 0) {
            PointP[] kwartDrie = new PointP[Drie];
            c = 0;
            for (int r = 1; r < cities.length; r++){
                if (cities[r].x >= divideX && cities[r].y >= divideY && (cities[r].x != (int)divideX || cities[r].y != (int)divideY) ) {
                kwartDrie[c] = cities[r];
                System.out.println("The coordinates of the points in quarter three is/are ( " + (kwartDrie[c].x) + ", " + (kwartDrie[c].y) + " )");
                c++;
                }
            } //end: for (int r = 1; r < route.length; r++){
            for (int v = 0; v < kwartDrie.length; v++) {
                if (kwartDrie[v] != route[1]) {
                    route[index] = kwartDrie[v];
                    index++;
               }
            }
        }

        if (Vier > 0) {
            PointP[] kwartVier = new PointP[Vier];
            c = 0;
            for (int s = 1; s < cities.length; s++){
                if (cities[s].x > divideX && cities[s].y < divideY) {
                    kwartVier[c] = cities[s];
                    System.out.println("The coordinates of the points in quarter four is/are ( " + (kwartVier[c].x) + ", " + (kwartVier[c].y) + " )");
                    c++;
                }
            }  //end: for (int s = 1; s < route.length; s++){
            for (int v=0; v<kwartVier.length; v++) {
                if (kwartVier[v] != route[1]) {
                    route[index] = kwartVier[v];
                    index++;
                }
            }
        }


    for (int u = 0; u < route.length; u++) {
        System.out.println("Route[" + u + "]: (" + route[u].x + ", " + route[u].y + ")");
    }

    return route;
    }

    public String getAuthors() {
        return "Hansen Ling";
    }

    public String getDescription() {
        return "Zones: Divides the grid in 4 zones.";
    }

    public static void main(String[] args) {
        Solver solver = new Zones();
        new PizzaViewer(solver);
    }
}
