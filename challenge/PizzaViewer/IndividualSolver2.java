//Wat hij eigenlijk zou moeten doen: eerst alle combinaties / mogelijke routes uitrekenen en dan de kortste doorlopen.

// S. Hendricks s030459 // DW150 // IndividualSolver2

import java.lang.Math;

public  class  IndividualSolver2 implements Solver {
    //Creates an instance of IndividualSolver2

    public IndividualSolver2() {
    }

    // Assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        boolean[] visited = new boolean[cities.length];
            for(int i = 0; i < cities.length; i++) {
            route[i] = null; // Starting Point should not change
            visited[i] = false;
            }
        visited[0] = true;
        route[0] = new PointP(cities[0]); // Starting Point should not change

    //all the variables I use
    int mainLoopIndex = 0; //Index of the main loop
    int citiesIndex = 0;   //Index of the cities so you know where you are (array)
    int routeIndex = 0;    //Index of the route so you know where you are (array)
    int deltaY = 0;        //Difference between all following points and the current point (Y axis)
    int deltaX = 0;        //Difference between all following points and the current point (X axis)

    double CalcDist = 0.0;  //Array with all calculated distances
    double[] Dist = new double[cities.length]; //Array with all distances

    int DistCount = 0;  //Keeps track of how many distances are stored per array

 	int a=0; //Keeps track of the point with the lowest index

    double minimalDistance = 40.0; //start with a high distance so the first calculated distance overwrites it

	/*
	System.out.println("test: "+Math.sqrt((Math.abs(-1))^2 + (Math.abs(-1))^2));
	System.out.println("test: "+Math.pow(3,2));
	*/

        for (mainLoopIndex = 0; mainLoopIndex < (cities.length-1); mainLoopIndex++) {
            for (citiesIndex = cities.length-1; citiesIndex > 0; citiesIndex--) { //sets all the indexes in the right order and puts them in the right array
                if(!visited[citiesIndex])										  //and puts them in the order as described in cities
                {
                    deltaY = (cities[citiesIndex].y)-(route[routeIndex].y); //System.out.println("deltaY"+deltaY);
                    deltaX = (cities[citiesIndex].x)-(route[routeIndex].x); //System.out.println("deltaX"+deltaX);

                    // Calculates the distances of all the cities and stores them in array CalcDist with the same indexes as stored in cities[]

                            CalcDist = Math.sqrt(Math.pow(Math.abs(deltaX),2) + Math.pow(Math.abs(deltaY),2)); //deltaY & deltaX, rule of Pythagoras
                            //System.out.println("1 "+CalcDist);
                            Dist[citiesIndex] = CalcDist;
                            DistCount++;
            				}

                      for (int minimalIndex = 1; minimalIndex < Dist.length; minimalIndex++) { //checks distances and picks the shortest
                        if (Dist[minimalIndex] != 0) {
                            if (Dist[minimalIndex] < minimalDistance) {
                                minimalDistance = Dist[minimalIndex];
                                a=minimalIndex;
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minimalIndex];
                                    //visited[minimalIndex] = true; (student assistent put this in but seems not to work)
                                    }
                                else {
                                    route[routeIndex + 1] = cities[minimalIndex];
                                    //visited[minimalIndex] = true; (student assistent put this in but seems not to work)
                                }

                            }
                            //System.out.println("minimalTan"+minimalDistance+" "+PosPos[minimalIndex]);
                        }
                    }

				}

				visited[a]=true; //each pizzapoint should be visited just once

				//Everything gets reseted for the next city
				a=0;
                routeIndex++;


                for (int i=0; i<cities.length; i++) {
                    Dist[i] = 0;
                }

                DistCount = 0;
                minimalDistance = 40;

        }

    return route;
    }

    public String getAuthors() { //Author of this algorithm
        return "S. Hendricks // s030459";
    }

    public String getDescription() { //Discription of the algorithm
        return "IndividualSolver2, starts at 0,0 and calculates which point is the shortest nearby using the rule of Pythagoras. Then visits that point and does the calculation again, but visited points excluded.";
    }

    public static void main(String[] args) {
        Solver solver = new IndividualSolver2();
        new PizzaViewer(solver);
    }
}