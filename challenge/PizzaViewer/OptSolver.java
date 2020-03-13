    /***********************************************
    *	                                           *
    *    OptSolver: Compute the shortest route     *
    *	 by optimising multiple random routes	   *
    *                                              *
    *  @author Joep Kalthoff & Jesper Schwachöfer  *
    *                                              *
    *	     Created on  March 9, 2005             *
    *                                              *
    ***********************************************/


public class OptSolver implements Solver {

	/*** Constructor ***/
	public OptSolver() {
    }

    public PointP[] computeRoute(PointP[] cities) {

		/*** Initialisation ***/
    	PointP[] route = new PointP[cities.length+1];		// Make space for the last point
    	PointP temp = new PointP(0,0); 						// Make a container for temporary points
        route[cities.length]= new PointP(cities[0]); 		// Define start and finish at same location
        float lengte = 1000000;								// Initial length to compare first computed distance with
        float d = 0;										// Make a container for computed distance
        int random;											// Make a container for random numbers
        int k,l;											// Make a container for reference to i
        java.util.Random r = new java.util.Random();		// Make a Random object



        /*** Fill route with points from cities ***/
        for (int i=0; i<cities.length; i++) {
			route[i] = cities[i];
		}

		/*** Iterate randomalisation and optimalisation process ***/
        for (int p=0; p<15000; p++) {

			/*** Create random route ***/
			for (int i=1; i<route.length-1; i++) {
				random = r.nextInt(i)+1;
				temp = route[random];
				route[random] = route[i];
				route[i] = temp;
			}

			/***  First optimalisation  ***
			  The algorithm checks
			  whether switching the two
			  adjacent points delivers
			  a shorter route
			 ******************************/
			for (int i=0; i<route.length-3; i++) {
				if (PointP.distance(route[i],route[i+1]) + PointP.distance(route[i+2],route[i+3]) >
				PointP.distance(route[i],route[i+2]) + PointP.distance(route[i+1],route[i+3])) {
					temp = route[i+1];
					route[i+1] = route[i+2];
					route[i+2] = temp;
				}
			}

			/*** Second optimalisation ***
			  The algorithm checks whether
			  switching one point with any
			  other point delivers a
			  shorter route, then mirrors
			  the order of the points in
			  between
			 *****************************/
			for (int i=0; i<route.length-1; i++) {
				for (int n=i+2; n<route.length-2; n++) {
					if (PointP.distance(route[i],route[i+1]) + PointP.distance(route[n],route[n+1]) >
					PointP.distance(route[i],route[n]) + PointP.distance(route[i+1],route[n+1])) {
						k=i+1;
						l=n;
						while (k<l) {
							temp = route[k];
							route[k] = route[l];
							route[l] = temp;
							k++;
							l=l-1;
						}
					}
				}
			}

			/*** Compute total distance ***/
			d=0;
			temp = new PointP(0,0);
			for (int i=0; i<route.length; i++) {
				d += PointP.distance(temp, route[i]);
				temp = route[i];
			}

			/*** Compare saved distance with computed one ***/
			if (d<lengte) {
				lengte=d;
				for (int i=1; i<cities.length; i++) {
					cities[i] = route[i];
				}
			}

		}

	    return cities;
    }

    public String getAuthors() {
        return "Joep Kalthoff & Jesper Schwachöfer";
    }

    public String getDescription() {
        return "OptSolver: Compute the shortest route by optimising multiple random routes.\nFirst Optimalisation: The algorithm checks whether switching the two adjacent points delivers a shorter route.\nSecond Optimalisation: The algorithm checks whether switching one point with any other point delivers a shorter route,\nthen mirrors the order of the points in between.";
    }

    public static void main(String[] args) {
        Solver solver = new OptSolver();
        new PizzaViewer(solver);
    }
}

