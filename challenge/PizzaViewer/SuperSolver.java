// Bertjuh && Henk, 23-03, second try, SuperSolver

import java.lang.Math;

public  class  SuperSolver implements Solver {
    // Creates an instance of SuperSolver
    
    public SuperSolver() {
    }
    
    private void printRoute(PointP[] route)
    {
        //System.out.println("Printing the route");
        for(int i = 0; i < route.length; i++)
        {
            if(route[i] != null) {
                //System.out.println("Punt " + i + " = (" + route[i].x + ", " + route[i].y + ")");
            } 
            else {
                //System.out.println("Punt " + i + " = null!!");
            }
        }
    }
    
    // Assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        boolean[] visited = new boolean[cities.length];        
        for(int i = 0; i < cities.length; i++)
        {
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
    
    double tanPosPos = 0.0;  // Represent the tangenses of cities in different quadrants
    double tanPosNeg = 0.0;
    double tanNegNeg = 0.0;
    double tanNegPos = 0.0;
    double[] PosPos = new double[cities.length];  // Array that stores the tangenses per quadrant
    double[] PosNeg = new double[cities.length];
    double[] PosZero = new double[cities.length];
    double[] NegPos = new double[cities.length];
    double[] NegNeg = new double[cities.length];
    double[] NegZero = new double[cities.length];
    double[] ZeroPos = new double[cities.length];
    double[] ZeroNeg = new double[cities.length];
 
    int posPosCount = 0;  // 
    int posNegCount = 0;
    int posZeroCount = 0;
    int negPosCount = 0;
    int negNegCount = 0;
    int negZeroCount = 0;
    int zeroPosCount = 0;
    int zeroNegCount = 0;
    int zeroZeroCount = 0;
    
 //   int minIndex = -1;
    double minTan = 200.0;
    double minDeltaX = 20.0;
    double minDeltaY = 20.0;
    
    
    
        for (mainLoopIndex = 0; mainLoopIndex < (cities.length-1); mainLoopIndex++) {
            for (citiesIndex = cities.length-1; citiesIndex > 0; citiesIndex--) {
                if(!visited[citiesIndex])
                {
                    deltaY = (cities[citiesIndex].y)-(route[routeIndex].y);
                    deltaX = (cities[citiesIndex].x)-(route[routeIndex].x);
                  //  System.out.println("deltaY = " + deltaY);  
                  //  System.out.println("deltaX = " + deltaX);
                  //System.out.println("deltaY van punt " + citiesIndex + " t.o.v. punt " + routeIndex + " = " + cities[citiesIndex].y + " - " + route[routeIndex].y + " = " + deltaY);
                  //System.out.println("deltaX van punt " + citiesIndex + " t.o.v. punt " + routeIndex + " = " + cities[citiesIndex].x + " - " + route[routeIndex].x + " = " + deltaX);
                    
                    // Calculates the tangenses of all the cities, and stores them per quadrant in arrays in the same place where they are stored in cities[] 
                    if (deltaY > 0) {
                        if (deltaX > 0) {   // deltaY and delta X are both positive
                            tanPosPos = (double)(cities[citiesIndex].y-route[routeIndex].y) / (double)(cities[citiesIndex].x-route[routeIndex].x);
                            // System.out.println("Delta Y > 0 & Delta X > 0: tanPosPos = " + tanPosPos);
                            PosPos[citiesIndex] = tanPosPos;
                            posPosCount++;
                        }
                        else {            // delta Y is positive, delta X is negative or zero
                            if (deltaX < 0) {    // delta Y is positive, delta X is negative
                                tanPosNeg = (double)(route[routeIndex].x-cities[citiesIndex].x) / (double)(cities[citiesIndex].y-route[routeIndex].y);
                                // System.out.println("Delta Y > 0 & Delta X < 0: tanPosNeg = " + tanPosNeg);                        
                                PosNeg[citiesIndex] = tanPosNeg;
                                posNegCount++;
                            }
                            else {      // delta Y is positive, delta X is zero
                                // System.out.println("Delta Y > 0 & Delta X = 0");                        
                                PosZero[citiesIndex] = deltaY;
                                posZeroCount++;    
                                
                            }                           
                        }
                    }
                    else if (deltaY < 0) {  // delta Y is negative
                            if (deltaX < 0) {     // delta Y is negative, delta X is negative
                                tanNegNeg = (double)(route[routeIndex].y-cities[citiesIndex].y) / (double)(route[routeIndex].x-cities[citiesIndex].x);
                                // System.out.println("Delta Y < 0 & Delta X < 0: tanNegNeg = " + tanNegNeg);
                                NegNeg[citiesIndex] = tanNegNeg;
                                negNegCount++;
                            }
                            else if (deltaX > 0) {      // delta Y is negative, delta X is positive
                                    tanNegPos = (double)(route[routeIndex].y-cities[citiesIndex].y) / (double)(cities[citiesIndex].x-route[routeIndex].x);
                                    // System.out.println("Delta Y < 0 & Delta X > 0: tanNegPos = " + tanNegPos);
                                    NegPos[citiesIndex] = tanNegPos;
                                    negPosCount++;
                            }
                            else {   // delta Y is negative, delta X is zero
                                // System.out.println("Delta Y < 0 & Delta X = 0");                        
                                NegZero[citiesIndex] = deltaY;
                                negZeroCount++; 
                            }
                     }
                     else { //    deltaY is zero
                        if (deltaX < 0) {     // delta Y is zero, delta X is negative
                                // System.out.println("Delta Y = 0 & Delta X < 0");                        
                                ZeroNeg[citiesIndex] = deltaX;
                                zeroNegCount++;
                            }
                            else if (deltaX > 0) {      // delta Y is zero, delta X is positive
                                // System.out.println("Delta Y = 0 & Delta X > 0");                        
                                ZeroPos[citiesIndex] = deltaX;
                                zeroPosCount++;
                            }
                            else {   // delta Y is zero, delta X is zero
                                // System.out.println("Delta Y = 0 & Delta X = 0 This is the current point");                        
                            }
                    }        
                 }
            }
                if (zeroPosCount > 0) {     // deltaY = 0, deltaX > 0
                    // int storedIndex = -1;
                    for (int minIndex = 1; minIndex < ZeroPos.length; minIndex++) {
                        if (ZeroPos[minIndex] != 0) {
                            if (ZeroPos[minIndex] < minDeltaX) {
                                minDeltaX = ZeroPos[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }        
                                // System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                                //System.out.println(minIndex + "e keer doorlopen");
                                //System.out.println(minDeltaX + " is de kleinste Delta X");
                                //System.out.println("(" + cities[minIndex].x + "," + cities[minIndex].y + ") wordt het volgende punt in route[]");
                            }
                        }
                   }
               }
                    
                else if (posPosCount > 0) {
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < PosPos.length; minIndex++) {
                        if (PosPos[minIndex] != 0) {
                            if (PosPos[minIndex] < minTan) {
                                minTan = PosPos[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }        
                                // System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                    }
                }
                
                else if (posZeroCount > 0) {     // deltaY > 0, deltaX = 0
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < PosZero.length; minIndex++) {
                        if (PosZero[minIndex] != 0) {
                            if (PosZero[minIndex] < minDeltaY) {
                                minDeltaY = PosZero[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }        
                                //System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                   }
               }
            
                else if (posNegCount > 0) {
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < PosNeg.length; minIndex++) {
                        if (PosNeg[minIndex] != 0) {
                            if (PosNeg[minIndex] < minTan) {
                                minTan = PosNeg[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                    if (route[routeIndex + 1] == null) {                                    
                                        route[routeIndex + 1] = cities[minIndex];
                                        visited[minIndex] = true;
                                        //storedIndex = minIndex; 
                                    }    
                                    else {
                                        visited[minIndex] = false;
                                        route[routeIndex + 1] = cities[minIndex];
                                        visited[minIndex] = true;
                                        //storedIndex = minIndex;
                                    }
                                // System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                    }
                }
                
                else if (zeroNegCount > 0) {     // deltaY = 0, deltaX < 0
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < ZeroNeg.length; minIndex++) {
                        if (ZeroNeg[minIndex] != 0) {
                            if (ZeroNeg[minIndex] > -minDeltaX) {
                                minDeltaX = ZeroNeg[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }        
                //                System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                   }
               }
                
                else if (negNegCount > 0) {
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < NegNeg.length; minIndex++) {
                        if (NegNeg[minIndex] != 0) {
                            if (NegNeg[minIndex] < minTan) {
                                minTan = NegNeg[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                    if (route[routeIndex + 1] == null) {                                    
                                        route[routeIndex+1] = cities[minIndex];
                                        visited[minIndex] = true;
                                        //storedIndex = minIndex;
                                    }    
                                    else {
                                        visited[minIndex] = false;
                                        route[routeIndex+1] = cities[minIndex];
                                        visited[minIndex] = true;
                                        //storedIndex = minIndex;
                                    }
                  //              System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                    }
                }
                
                else if (negZeroCount > 0) {     // deltaY < 0, deltaX = 0
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < NegZero.length; minIndex++) {
                        if (NegZero[minIndex] != 0) {
                            if (NegZero[minIndex] > -minDeltaY) {
                                minDeltaY = NegZero[minIndex];
                                if (route[routeIndex + 1] == null) {
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex + 1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }        
                                // System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }
                        }
                   }
               }
                
                else if (negPosCount > 0) {
                    //int storedIndex = -1;
                    for (int minIndex = 1; minIndex < NegPos.length; minIndex++) {
                        if (NegPos[minIndex] != 0) {
                            if (NegPos[minIndex] < minTan) {
                                minTan = NegPos[minIndex];
                                route[routeIndex+1] = cities[minIndex];
                                 if (route[routeIndex + 1] == null) {                                    
                                    route[routeIndex+1] = cities[minIndex];
                                    visited[minIndex] = true;
                                        //storedIndex = minIndex;
                                    }    
                                else {
                                    visited[minIndex] = false;
                                    route[routeIndex+1] = cities[minIndex];
                                    visited[minIndex] = true;
                                    //storedIndex = minIndex;
                                }
                               // System.out.println("Punt " + routeIndex + " = (" + route[routeIndex].x + ", " + route[routeIndex].y + ")");
                            }   
                        }
                    }
                }         
                                  
                                    
                routeIndex++;           
            
                
                for (int i=0; i<cities.length; i++) {
                    PosPos[i] = 0;
                    PosNeg[i] = 0;
                    PosZero[i] = 0;
                    NegPos[i] = 0;
                    NegNeg[i] = 0;
                    NegZero[i] = 0;
                    ZeroPos[i] = 0;
                    ZeroNeg[i] = 0;
                }
                    
                posPosCount = 0;
                posNegCount = 0;
                posZeroCount = 0;
                negPosCount = 0;
                negNegCount = 0;
                negZeroCount = 0;
                zeroPosCount = 0;
                zeroNegCount = 0;
                zeroZeroCount = 0;
                minTan = 200.0;
                minDeltaX = 20;
                minDeltaY = 20;
                
          //  minIndex = -1;
            }
         printRoute(route);
        return route;
    }
    
    public String getAuthors() {
        return "Bertjuh && Henk";
    }
    
    public String getDescription() {
        return "SuperSolver: The Ultimate Experience";
    }
    
    public static void main(String[] args) {
        Solver solver = new SuperSolver();
        new PizzaViewer(solver);
    }
}
