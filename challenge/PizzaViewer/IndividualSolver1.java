// S. Hendricks s030459 //uses tangens function for each following point to calculate fastest route

import java.lang.Math;

public  class  IndividualSolver1 implements Solver {
    // Creates an instance of SuperSolver

    public IndividualSolver1() {
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

    // Integers, doubles, floats, arrays, etc.
    int mainLoopIndex = 0;
    int citiesIndex = 0;
    int routeIndex = 0;
    int deltaY = 0;
    int deltaX = 0;

    double tanPP = 0.0;  // Represent the tangenses of cities in different quadrants
    double tanPN = 0.0;
    double tanNN = 0.0;
    double tanNP = 0.0;
    double[] PP = new double[cities.length];  // Array that stores the tangenses per quadrant
    double[] PN = new double[cities.length];
    double[] PZ = new double[cities.length];
    double[] NP = new double[cities.length];
    double[] NN = new double[cities.length];
    double[] NZ = new double[cities.length];
    double[] ZP = new double[cities.length];
    double[] ZN = new double[cities.length];

    int pPCount = 0;  // Keeps track of how many tangenses are stored per array
    int pNCount = 0;
    int pZCount = 0;
    int nPCount = 0;
    int nNCount = 0;
    int nZCount = 0;
    int zPCount = 0;
    int zNCount = 0;
    int zZCount = 0;

    double minTan = 200.0;  // Represents the smallest value of respectively tangens, delta X and delta Y
    double minDeltaX = 20.0;
    double minDeltaY = 20.0;

        for (mainLoopIndex = 0; mainLoopIndex < (cities.length-1); mainLoopIndex++) {
            for (citiesIndex = cities.length-1; citiesIndex > 0; citiesIndex--) {
                if(!visited[citiesIndex])
                {
                    deltaY = (cities[citiesIndex].y)-(route[routeIndex].y);
                    deltaX = (cities[citiesIndex].x)-(route[routeIndex].x);

                    if (deltaY > 0) {
                        if (deltaX > 0) {   // deltaY and delta X are both positive
                            tanPP = (double)(cities[citiesIndex].y-route[routeIndex].y) / (double)(cities[citiesIndex].x-route[routeIndex].x);
                            PP[citiesIndex] = tanPP;
                            pPCount++;
                        }
                        else {            // delta Y is positive, delta X is negative or zero
                            if (deltaX < 0) {    // delta Y is positive, delta X is negative
                                tanPN = (double)(route[routeIndex].x-cities[citiesIndex].x) / (double)(cities[citiesIndex].y-route[routeIndex].y);
                                PN[citiesIndex] = tanPN;
                                pNCount++;
                            }
                            else {      // delta Y is positive, delta X is zero
                                PZ[citiesIndex] = deltaY;
                                pZCount++;

                            }
                        }
                    }
                    else if (deltaY < 0) {  // delta Y is negative
                            if (deltaX < 0) {     // delta Y is negative, delta X is negative
                                tanNN = (double)(route[routeIndex].y-cities[citiesIndex].y) / (double)(route[routeIndex].x-cities[citiesIndex].x);
                                NN[citiesIndex] = tanNN;
                                nNCount++;
                            }
                            else if (deltaX > 0) {      // delta Y is negative, delta X is positive
                                    tanNP = (double)(route[routeIndex].y-cities[citiesIndex].y) / (double)(cities[citiesIndex].x-route[routeIndex].x);
                                    NP[citiesIndex] = tanNP;
                                    nPCount++;
                            }
                            else {   // delta Y is negative, delta X is zero
                                NZ[citiesIndex] = deltaY;
                                nZCount++;
                            }
                     }
                     else { //    deltaY is zero
                        if (deltaX < 0) {     // delta Y is zero, delta X is negative
                                ZN[citiesIndex] = deltaX;
                                zNCount++;
                            }
                            else if (deltaX > 0) {      // delta Y is zero, delta X is positive
                                ZP[citiesIndex] = deltaX;
                                zPCount++;
                            }
                            else {   // delta Y is zero, delta X is zero
                            }
                    }
                 }
            }
                if (zPCount > 0) {     // deltaY = 0, deltaX > 0
                    for (int minIndex = 1; minIndex < ZP.length; minIndex++) {
                        if (ZP[minIndex] != 0) {
                            if (ZP[minIndex] < minDeltaX) {
                                minDeltaX = ZP[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                   }
               }

                else if (pPCount > 0) {
                    for (int minIndex = 1; minIndex < PP.length; minIndex++) {
                        if (PP[minIndex] != 0) {
                            if (PP[minIndex] < minTan) {
                                minTan = PP[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                    }
                }

                else if (pZCount > 0) {     // deltaY > 0, deltaX = 0
                    for (int minIndex = 1; minIndex < PZ.length; minIndex++) {
                        if (PZ[minIndex] != 0) {
                            if (PZ[minIndex] < minDeltaY) {
                                minDeltaY = PZ[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                   }
               }

                else if (pNCount > 0) {
                    for (int minIndex = 1; minIndex < PN.length; minIndex++) {
                        if (PN[minIndex] != 0) {
                            if (PN[minIndex] < minTan) {
                                minTan = PN[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                    if (route[routeIndex + 1] == null) {
                                        route[routeIndex + 1] = cities[minIndex];
                                        visited[minIndex] = true;
                                    }
                                    else {
                                        visited[minIndex] = false;
                                        route[routeIndex + 1] = cities[minIndex];
                                        visited[minIndex] = true;
                                    }
                            }
                        }
                    }
                }

                else if (zNCount > 0) {     // deltaY = 0, deltaX < 0
                    for (int minIndex = 1; minIndex < ZN.length; minIndex++) {
                        if (ZN[minIndex] != 0) {
                            if (ZN[minIndex] > -minDeltaX) {
                                minDeltaX = ZN[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                   }
               }

                else if (nNCount > 0) {
                    for (int minIndex = 1; minIndex < NN.length; minIndex++) {
                        if (NN[minIndex] != 0) {
                            if (NN[minIndex] < minTan) {
                                minTan = NN[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                    if (route[routeIndex + 1] == null) {
                                        route[routeIndex+1] = cities[minIndex];
                                        visited[minIndex] = true;
                                    }
                                    else {
                                        visited[minIndex] = false;
                                        route[routeIndex+1] = cities[minIndex];
                                        visited[minIndex] = true;
                                    }
                            }
                        }
                    }
                }

                else if (nZCount > 0) {     // deltaY < 0, deltaX = 0
                    for (int minIndex = 1; minIndex < NZ.length; minIndex++) {
                        if (NZ[minIndex] != 0) {
                            if (NZ[minIndex] > -minDeltaY) {
                                minDeltaY = NZ[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                   }
               }

                else if (nPCount > 0) {
                    for (int minIndex = 1; minIndex < NP.length; minIndex++) {
                        if (NP[minIndex] != 0) {
                            if (NP[minIndex] < minTan) {
                                minTan = NP[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                 if (route[routeIndex + 1] == null) {
                                    route[routeIndex+1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    }
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex+1] = cities[minIndex];
                                    visited[minIndex] = true;
                                }
                            }
                        }
                    }
                }

                routeIndex++;

                for (int i=0; i<cities.length; i++) {
                    PP[i] = 0;
                    PN[i] = 0;
                    PZ[i] = 0;
                    NP[i] = 0;
                    NN[i] = 0;
                    NZ[i] = 0;
                    ZP[i] = 0;
                    ZN[i] = 0;
                }

                pPCount = 0;
                pNCount = 0;
                pZCount = 0;
                nPCount = 0;
                nNCount = 0;
                nZCount = 0;
                zPCount = 0;
                zNCount = 0;
                zZCount = 0;
                minTan = 200.0;
                minDeltaX = 20;
                minDeltaY = 20;

        }

//        printRoute(route);
        return route;
    }

    public String getAuthors() {
        return "S. Hendricks";
    }

    public String getDescription() {
        return "IndividualSolver1, tangens";
    }

    public static void main(String[] args) {
        Solver solver = new IndividualSolver1();
        new PizzaViewer(solver);
    }
}

// S. Hendricks s030459