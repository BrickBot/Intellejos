import java.awt.geom.*;


public class EigenSolver implements Solver {
     public EigenSolver() {
    }


    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]); // starting point should not change

        int xgrid = PizzaPanel.COLS - 1;
        int ygrid = PizzaPanel.ROWS - 1;
        // bepaling van de hoogte en breedte van het grid
        int aantalgevuld = 0;
        //is de hoeveelheid die al in de array route staan
        int indexinroute = 0;
        //is in de array route de plaats waar het dichtstbijzijnde punt moet
        double dichtstbijzijndeafstand = 100;
        //is de waarde van de kortste afstand van een punt tot een lijn of hoek
        int dichtstbijzijndepunt = 0;
        //is de indexwaarde in array cities van het punt dat het dichts bij een lijn of hoek ligt

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------


        //als er maar 2 steden of minder ingevoerd worden. Dan is de route array namelijk dezelfde als de korts mogelijke afstand
        if (cities.length <= 3) {

            for (int i = 1; i < cities.length; i++) {
                route[i] = cities[i];
            }

        } else {


            //zoek het punt dat op de bovenste lijn ligt. En als er daar geen ligt, daar een onder zoeken.
            dichtstbijzijndepunt = 1;
            for (int i = 1; i < cities.length; i++) {
                if (cities[i] != null) {
                    if ((cities[i].y <= cities[dichtstbijzijndepunt].y) && (cities[i].x >= cities[dichtstbijzijndepunt].x)) {
                        dichtstbijzijndepunt = i;
                    System.out.println ("dbp:" + dichtstbijzijndepunt);
                    }
                }
             }

            route[1] = new PointP(cities[dichtstbijzijndepunt]);
            System.out.println("route[1]" +route[1]);
            aantalgevuld = aantalgevuld + 1;
            cities[dichtstbijzijndepunt] = null; //verwijder de dichtstbijzijndepunt uit de array cities zodat die niet opnieuw gebruikt wordt
            dichtstbijzijndepunt = 0;
            dichtstbijzijndeafstand = 100;
            System.out.println("dichtsbijzijndepunt 1 =" +dichtstbijzijndepunt);
            System.out.println("dichtsbijzijndeafstand 1 = " + dichtstbijzijndeafstand);


             //zoek het punt dat op de meeste rechtste lijn ligt. En als er daar geen ligt, daar een links van zoeken.

                for (int i = 1; i < cities.length; i++) {
                if (cities[i] != null) {
                    if ((cities[i].x >= cities[dichtstbijzijndepunt].x) && (cities[i].y >= cities[dichtstbijzijndepunt].y)) {
                        dichtstbijzijndepunt = i;
                        System.out.println ("dbp:" + dichtstbijzijndepunt);
                    }
                }
            }

            route[2] = new PointP(cities[dichtstbijzijndepunt]);
            aantalgevuld = aantalgevuld + 1;
            cities[dichtstbijzijndepunt] = null;
            for(dichtstbijzijndepunt = 1; cities[dichtstbijzijndepunt]==null; dichtstbijzijndepunt++); //zoek het eerste punt (vanaf 1) dat NIET null is
            dichtstbijzijndeafstand = 100;
            System.out.println("dichtsbijzijndepunt 2 =" +dichtstbijzijndepunt);
            System.out.println("dichtsbijzijndeafstand 2 = " + dichtstbijzijndeafstand);


            //zoek het punt dat op de onderste lijn ligt. En als er daar geen ligt, daar een boven zoeken.

            for (int i = 1; i < cities.length; i++) {
                if (cities[i] != null) {
                    if ((cities[i].y > cities[dichtstbijzijndepunt].y) && (cities[i].x <= cities[dichtstbijzijndepunt].x)) {
                        dichtstbijzijndepunt = i;
                        System.out.println ("dbp:" + dichtstbijzijndepunt);
                        }
                }
            }

            route[3] = new PointP(cities[dichtstbijzijndepunt]);
            aantalgevuld = aantalgevuld + 1;
            cities[dichtstbijzijndepunt] = null;
            dichtstbijzijndepunt = 0;
            dichtstbijzijndeafstand = 1000;

            System.out.println("coordinaat 3 is " + route[3].x + "," + route[3].y);
            System.out.println("coordinaat 2 is " + route[2].x + "," + route[2].y);
            System.out.println("coordinaat 3 is " + route[1].x + "," + route[1].y);




            // nu zijn er drie basislijnen.

//-----------------------------------------------------------------------------------------------------------------------------------------------

            for (int t = 1; t < cities.length; t++) {
                //vergelijk elk punt in cities waar nog een waarde in staat met alle lijnen die er al zijn
                dichtstbijzijndeafstand = 100;

                for (int i = 1; i < cities.length; i++) {
                    if (cities[i] != null) {
                        for (int a = 0; a < (route.length); a++) {
                            //voor de lijn van het laatste punt in de array tot 0,0
                            if (a == aantalgevuld) {
                                if (Line2D.ptSegDist(route[a].x, route[a].y, 0,
                                            0, cities[i].x, cities[i].y) <= dichtstbijzijndeafstand) {
                                    dichtstbijzijndeafstand = Line2D.ptSegDist(route[a].x,
                                            route[a].y, 0, 0, cities[i].x,
                                            cities[i].y);
                                    dichtstbijzijndepunt = i;
                                    indexinroute = aantalgevuld + 1;
                                }
                            }
                               else if (route[a] != null) {
                                //voor alle andere lijnen tussen punten die na elkaar komen in de array route
                                if (route[a + 1] != null) {
                                    if (cities[i] != null) {
                                        if (Line2D.ptSegDist(route[a].x,
                                                    route[a].y, route[a + 1].x,
                                                    route[a + 1].y,
                                                    cities[i].x, cities[i].y) <= dichtstbijzijndeafstand) {
                                            dichtstbijzijndeafstand = Line2D.ptSegDist(route[a].x,
                                                    route[a].y, route[a + 1].x,
                                                    route[a + 1].y,
                                                    cities[i].x, cities[i].y);
                                            dichtstbijzijndepunt = i;
                                            indexinroute = a + 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //zet het punt dat het dichtst bij een lijn ligt op de eerste vrije plaats in array route en verwijder hem uit cities
                boolean stop = false; //zodat alleen de eerste null waarde wordt vervangen

                for (int a = 1; a < route.length; a++) {
                    if (stop == false) {
                        if (route[a] == null) {
                            route[a] = new PointP(cities[dichtstbijzijndepunt]);
                            aantalgevuld = aantalgevuld + 1;
                            cities[dichtstbijzijndepunt] = null;
                            stop = true;

                            //de swap dinges
                            for (int i = aantalgevuld - 1; i >= indexinroute;
                                    i--) {
                                PointP z = new PointP();

                                z = route[i];
                                route[i] = route[i + 1];
                                route[i + 1] = z;
                            }
                        }
                    }
                }
            }
        }
                            /*for (int i = aantalgevuld - 2; i >= indexinroute; i--) {
                                PointP z = route[i];
                                route[i] = route[i + 1];
                                route[i + 1] = z;
                            }
                            System.out.println("werkt nog 5");
                    //    }
                  //  }
                //}
            }
        }
        System.out.println("dichtsbijzijndepunt 5 =" +dichtstbijzijndepunt);
        System.out.println("dichtsbijzijndeafstand 5 = " + dichtstbijzijndeafstand);
        //System.out.println("-------");*/

        return route;
}
    public String getAuthors() {
        return "Misha Croes en Jesse Kirschner";
    }

    public String getDescription() {
        return "Finds fastest route by first searching for the most outer lines, and then connect the other cities to the closest already excisting line.";
    }

    public static void main(String[] args) {
        Solver solver = new EigenSolver();
        new PizzaViewer(solver);
    }
}
