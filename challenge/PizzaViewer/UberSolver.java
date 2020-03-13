//--------------------------------------------------------------------------
// INFO
//--------------------------------------------------------------------------
// UberSolver.java
//
// Finished on March 9, 2005, 11:00 AM
//--------------------------------------------------------------------------

//--------------------------------------------------------------------------
// AUTHORS
//--------------------------------------------------------------------------
// Puma (s041544)
// Drunk Birdie (s040954)
//--------------------------------------------------------------------------

import java.awt.geom.*;

public class UberSolver implements Solver {
    public UberSolver() {
    }

    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]); 	// starting point should not change
        int xgrid = cities[1].x; 	// index in cities with the highest x
        int ygrid = cities[1].y; 	// index in cities with the highest y

        //change xgrid and ygrid to highest values
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].x > xgrid){
                xgrid = cities[i].x;
            }
		}
        for (int i = 1; i < cities.length; i++) {
            if (cities[i].y > ygrid){
                ygrid = cities[i].y;
            }
        }

        int filled = 0; 	// number of place already assigned in array route, so this is the indexnumber of the place that comes before the first null in route
        int indexinroute = 0; 	// the index in array route where the closest point should go to
        double closestdist = 100; 	// the shortest distance from a point to a line or corner
        int closestpoint = 0; 	// the indexnumber of the point that is closest to a line or corner
        System.out.println("Look for the closest route from the starting point (0,0) and back with in between " +
                   		   (cities.length - 1) + " random points on a grid of " +
            			   (xgrid+1) + " by " + (ygrid+1) + ".");

        // for when there are less than 3 points given
        if (cities.length <= 3) {
            System.out.println("There are too few points, nothing will be changed.");

            for (int i = 1; i < cities.length; i++) {
                route[i] = cities[i];
            }

        } else {
        // look for the point that is closest the the lower left corner and place it on indexnumber 1 in the array route

            for (int i = 1; i < cities.length; i++) {
            	if (cities[i] != null) {
                	if ((PointP.distance(0, ygrid, cities[i].x, cities[i].y)) < closestdist) {
                        closestdist = (PointP.distance(0, ygrid, cities[i].x, cities[i].y));
                        closestpoint = i;
                    }
                }
            }

            route[1] = new PointP(cities[closestpoint]);
            filled = filled + 1;
            cities[closestpoint] = null; 	// removes the closest point from the array citties so it won't be used again
            closestpoint = 0;
            closestdist = 100;

            // look for the point that is closest the the lower right corner and place it on indexnumber 2 in the array route
            for (int i = 1; i < cities.length; i++) {
                if (cities[i] != null) {
                    if ((PointP.distance(xgrid, ygrid, cities[i].x, cities[i].y)) < closestdist) {
                        closestdist = (PointP.distance(xgrid, ygrid, cities[i].x, cities[i].y));
                        closestpoint = i;
                    }
                }
            }

            route[2] = new PointP(cities[closestpoint]);
            filled = filled + 1;
            cities[closestpoint] = null; 	// removes the closest point from the array citties so it won't be used again
            closestpoint = 0;
            closestdist = 100;

            // look for the point that is closest the the upper right corner and place it on indexnumber 3 in the array route
            for (int i = 1; i < cities.length; i++) {
                if (cities[i] != null) {
                    if ((PointP.distance(xgrid, 0, cities[i].x, cities[i].y)) < closestdist) {
                        closestdist = (PointP.distance(xgrid, 0, cities[i].x, cities[i].y));
                        closestpoint = i;
                    }
                }
            }

            route[3] = new PointP(cities[closestpoint]);
            filled = filled + 1;
            cities[closestpoint] = null;
            closestpoint = 0;
            closestdist = 100;

            System.out.println("- De 'outercities' (lower left, lower right and upper right) are (" +
                			   route[1].x + "," + route[1].y + ") , (" + route[2].x + "," +
                			   route[2].y + ") and (" + route[3].x + "," + route[3].y +
                			   "). 3 places in the array are filled.");

            for (int t = 1; t < cities.length; t++) {
                // compares each value that is left in the array cities with all the lines that are present at this time

                closestdist = 100;

                for (int i = 1; i < cities.length; i++) {
                    if (cities[i] != null) {
                        for (int a = 0; a < (route.length); a++) {
                            // for the line from the last point in the array to (0,0)
                            if (a == filled) {
                                if (Line2D.ptSegDist(route[a].x, route[a].y, 0, 0, cities[i].x, cities[i].y) <= closestdist) {
                                    closestdist = Line2D.ptSegDist(route[a].x, route[a].y, 0, 0, cities[i].x, cities[i].y);
                                    closestpoint = i;
                                    indexinroute = filled + 1;
                                }
                            } else if (route[a] != null) {
                             	// for all other lines between the points that follow up each other in the array route
                                if (route[a + 1] != null) {
                                    if (cities[i] != null) {
	                                    if (Line2D.ptSegDist(route[a].x, route[a].y, route[a + 1].x, route[a + 1].y, cities[i].x, cities[i].y) < closestdist) {
                                            closestdist = Line2D.ptSegDist(route[a].x, route[a].y, route[a + 1].x, route[a + 1].y, cities[i].x, cities[i].y);
                                            closestpoint = i;
                                            indexinroute = a + 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // place the point closest to a line on the first free spot in the array route and remove the same point from the array cities
                boolean stop = false; // so that only the first null value in the array is replaced

                for (int a = 1; a < route.length; a++) {
                    if (stop == false) {
                        if (route[a] == null) {
                            route[a] = new PointP(cities[closestpoint]);
                            filled = filled + 1;
                            cities[closestpoint] = null;
                            stop = true;

                            System.out.println("- Closest to a line is point (" + route[a].x + "," + route[a].y + "), place it on index " +
                                			   indexinroute + " in route. There are " + filled + " indexplaces filled in the array.");

                            // swapping the last filled index until it is at the desire indexnumber
                            for (int i = filled - 1; i >= indexinroute; i--) {
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


		// This checks that when you swap two points if the route gets shorter, if so it will stay this way, if not it will stay as it was
		for (int i = 1; i <= (route.length-2); i++) {
			if (i == (route.length-2)) {
				if (PointP.distance(route[i-1], route[i+1]) + PointP.distance(route[i], route[0]) <
					PointP.distance(route[i-1], route[i]) + PointP.distance(route[i+1], route[0])) {

					PointP z = new PointP();

					z = route[i];
					route[i] = route[i + 1];
                  	route[i + 1] = z;

                  	System.out.println("- Swap point " + i + " and " + (i+1));
				}
			} else {
					if (PointP.distance(route[i-1], route[i+1]) + PointP.distance(route[i], route[i+2]) <
						PointP.distance(route[i-1], route[i]) + PointP.distance(route[i+1], route[i+2])) {

						PointP z = new PointP();

						z = route[i];
						route[i] = route[i + 1];
                		route[i + 1] = z;

                		System.out.println("- Swap point " + i + " and " + (i+1));
					}
			  }
		 }


        System.out.println("-------");

        return route;
    }

    public String getAuthors() {
        return "- DT152 - Microteam 1 - Auke Buma (s041544) and Dirk de Bruin (s040954) -";
    }

    public String getDescription() {
        return "- Calculates outer cities. Calculates the distance to each line for each remaining point.\n- The point with the shortest distance is assigned to that specific line.\n- This is repeated until all points are assigned to a line.";
    }

    public static void main(String[] args) {
        Solver solver = new UberSolver();
        new PizzaViewer(solver);
    }
}
