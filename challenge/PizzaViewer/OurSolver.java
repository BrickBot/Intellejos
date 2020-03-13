/*
 * SimpleSolver.java
 *
 * Created on February 8, 2005, 12:55 PM
 */


/**
 *
 * @author kees
 */
public class OurSolver implements Solver {

    /** Creates a new instance of SimpleSolver */
    public OurSolver() {
    }

    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        //route[0] = new PointP(cities[0]);   // starting point should not change
        for (int i=0; i<route.length; i++) {
            route[i] = new PointP(cities[i]);
        }
        //index-array van route[]
        int[] index = new int[5];
        index[0] = 0;

        PointP a;
        int closestP=0;


        // zoekt punt met kleinste y-coördinaat
        for (int i = 2; i < route.length; i++) {
            int miny = 1;
            if (route[miny].y > route[i].y) {
                miny = i;
            }
            a = route[1];
            route[1] = route[miny];
            route[miny] = a;
            index[1] = 1;


        }

        // zoekt punt met grootste x-coördinaat
        for (int i = 3; i < route.length; i++) {
            int maxx = 2;
            if (route[maxx].x < route[i].x) {
                maxx = i;
            }
            a = route[2];
            route[2] = route[maxx];
            route[maxx] = a;
            index[2] = 2;
        }

        // zoekt punt met grootste y-coördinaat
        for (int i = 4; i < route.length; i++) {
            int maxy = 3;
            if (route[maxy].y < route[i].y) {
                maxy = i;
            }
            a = route[3];
            route[3] = route[maxy];
            route[maxy] = a;
            index[3] = 3;
        }

        // zoekt punt met kleinste x-coördinaat
        for (int i = 5; i < route.length; i++) {
            int minx = 4;
            if (route[minx].x > route[i].x) {
                minx = i;
            }
            a = route[4];
            route[4] = route[minx];
            route[minx] = a;
            index[4] = 4;
        }

        //
        if (route.length > 5) {

                print(route);
                System.out.println();
                print2(index);

                // rekent kortste afstand uit van extra punten (route[5] - route[route.length-1]) tot vaste punten (route[0] - route[4])
                for (int i=5; i < route.length; i++) {
                    float shortestDist = 200;
                    for (int j=0; j < 5; j++) {

                        float dist = PointP.distance(route[i], route[index[j]]);
                        //System.out.println(dist);

                        if (dist < shortestDist) {
                            shortestDist = dist;
                            closestP = j; //b = dichtsbijzijnde "vaste" punt tot route[i]
                        }
                    }


                    //definiëren punten vóór dichstbijzijnde punt (voorB) en achter dichstbijzijnde punt (achterB)
                    int voorClosestP = closestP-1;
                    int achterClosestP = closestP+1;
                    if (voorClosestP == -1) {
                        voorClosestP = 4;
                    }
                    if (achterClosestP == 5) {
                        achterClosestP = 0;
                    }

                    System.out.println();
                    System.out.println("Afstand van route[" + i + "] tot dichtsbijzijnde vaste punt: "+ shortestDist);

                    //bereken afstand van punt voorB en punt achterB tot extra punt (route[i])
                    float dist1 = PointP.distance(route[i], route[index[voorClosestP]]);
                    float dist2 = PointP.distance(route[i], route[index[achterClosestP]]);

                    //kiezen gunstigste 2e punt (om extra punt route[i] aan te koppelen)
                    if (dist1 < dist2) {
                        if (closestP != 0) {
                        a = route[i];                                     //in array route
                        for (int k = i; k > index[closestP]; k--) {
                            route[k] = route[k-1];
                            }
                            route[index[closestP]] = a;

                        for (int m = 4; m > voorClosestP; m--) {          //in array index
                            index[m] = index[m]+1;
                            }

                        System.out.println("Afstand van route[" + i + "] tot eerste vaste punt voor vaste punt " + closestP + ": " + dist1);
                        System.out.println();
                        }
                    }
                    else {
                        if (closestP != 4) {
                        a = route[i];                                    //in array route
                        for (int k = i; k > index[achterClosestP]; k--) {
                            route[k] = route[k-1];
                            }
                            route[index[achterClosestP]] = a;

                        for (int m = 4; m > closestP; m--) {             //in array index
                            index[m] = index[m]+1;
                            }

                        System.out.println("Afstand van route[" + i + "] tot eerste vaste punt na vaste punt " + closestP + ": " + dist2);
                        System.out.println();
                        }
                    }

                    print(route);
                    System.out.println();
                    print2(index);

                }

        }



        return route;
    }

    public void print(PointP[] r) {
        for(int i=0; i<r.length; i++) {
            System.out.println("i: "+i+"("+r[i].x+","+r[i].y+")");
        }
    }

    public void print2(int[] s) {
        for(int i=0; i<s.length; i++) {
            System.out.println("index["+i+"]: "+s[i]);
        }
    }

    public String getAuthors() {
        return "Wieke Oudhuis & Jente de Pee";
    }

    public String getDescription() {
        return "OurSolver: 5 fixed points";
    }

    public static void main(String[] args) {
        Solver solver = new OurSolver();
        new PizzaViewer(solver);
    }
}
