//Erik Kremers & Kevin Verleijsdonk

public class Navigation implements Solver {

    float p = (PizzaPanel.COLS)/2;									//p is the borderline between zone A & B, and between zone D & C; cities on this line belong to B/C
    float q = (PizzaPanel.ROWS)/2;									//q is the borderline between zone A & D, and between zone B & C; cities on this line belong to D/C

    public Navigation() {											//Creates a new instance of Navigation
    }

    //Assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {

		PointP[] vakA = new PointP[cities.length];					//Arrays are given a length of cities.length
		PointP[] vakB = new PointP[cities.length];
		PointP[] vakC = new PointP[cities.length];
		PointP[] vakD = new PointP[cities.length];

		int A = 0;													//A is the no. of cities in zone A, etc.
		int B = 0;
		int C = 0;
		int D = 0;

        PointP[] route = new PointP[cities.length];					//Creates array for route
        route[0] = new PointP(cities[0]);							//Starting point is [0,0]

        for (int i=1; i<route.length; i++){							//Each city (i) is checked and put in array of correct zone

        	if (cities[i].x < p && cities[i].y < q){				//x and y are checked to see if city is in zone A
        		vakA[A] = new PointP(cities[i]);					//If so, city is put in vakA
        		A = A + 1;
        	}

        	if (cities[i].x >= p && cities[i].y < q){				//...
        		vakB[B] = new PointP(cities[i]);
        		B = B + 1;
        	}

        	if (cities[i].x >= p && cities[i].y >= q){				//...
        		vakC[C] = new PointP(cities[i]);
        		C = C + 1;
        	}

        	if (cities[i].x < p && cities[i].y >= q){				//...
        		vakD[D] = new PointP(cities[i]);
        		D = D + 1;
        	}
        }

		int z = 1;													//z is the number of cities already placed in route
		int done = 0;
        //Zone A
        for (int x=0; x<=PizzaPanel.COLS; x++) {					//Cities are placed in route according to increasing x-values

			int dubbel = 0;											//dubbel is the variable that stores the number of cities that have the same x coordinate and need to be put in a certain order.
			PointP[] dubbelarray = new PointP[A];					//the array that stores all cities that have the same x-coordinates and need to be put in a certain order.
        	for (int i=0; i<A; i++){							

        		if (vakA[i].x == x){								//if the current city (i) that is being checked has the same x-coordinate as the x-value that is currently been 'done',
        			dubbelarray[dubbel]=vakA[i];					//it is placed in dubbelarray, and the number of cities in dubbelarray is increased by 1
        			dubbel = dubbel + 1;							//the number of cities with the same x coordinate
        		}
			}
			int[] gehad = new int[dubbel];
			for (int n = 0; n<dubbel; n++){
				float length = 99999;
				float newlength = 99999;
				for (int i=0; i<dubbel; i++) {
					if (gehad[i]!=1){					
						newlength = PointP.distance(route[z-1], dubbelarray[i]);	//The distance between the last city in the route and the city that is being checked is calculated
						}
					if (newlength < length){						//If this city is closer than the previous choice
						route[z] = dubbelarray[i];					//It is placed in the route
						length = newlength;							//New distance is stored
						done = i;
					}
				}
			gehad[done]=1;
			z = z + 1;												//Number of cities in the route increased by 1.
			}
		}

        //Zone B
        for (int y=0; y<=PizzaPanel.ROWS; y++) {					//Cities are placed in route according to increasing y-values
			
			int dubbel = 0;	
        	PointP[] dubbelarray = new PointP[B];					//the array that stores all cities that have the same x-coordinates and need to be put in a certain order.
        	for (int i=0; i<B; i++){							

        		if (vakB[i].y == y){								//if the current city (i) that is being checked has the same x-coordinate as the x-value that is currently been 'done',
        			dubbelarray[dubbel]=vakB[i];					//it is placed in dubbelarray, and the number of cities in dubbelarray is increased by 1
        			dubbel = dubbel + 1;							//the number of cities with the same x coordinate
        		}
			}
			int[] gehad = new int[dubbel];
			for (int n = 0; n<dubbel; n++){
				float length = 99999;
				float newlength = 99999;
				for (int i=0; i<dubbel; i++) {
					if (gehad[i]!=1){					
						newlength = PointP.distance(route[z-1], dubbelarray[i]);	//The distance between the last city in the route and the city that is being checked is calculated
						}
					if (newlength < length){						//If this city is closer than the previous choice
						route[z] = dubbelarray[i];					//It is placed in the route
						length = newlength;							//New distance is stored
						done = i;
					}
				}
			gehad[done]=1;
			z = z + 1;												//Number of cities in the route increased by 1.
			}
        }

        //Zone C
        for (int x=PizzaPanel.COLS; x>=0; x=x-1) {					//Cities are placed in route according to decreasing x-values

			int dubbel = 0;	
        	PointP[] dubbelarray = new PointP[C];					//the array that stores all cities that have the same x-coordinates and need to be put in a certain order.
        	for (int i=0; i<C; i++){							

        		if (vakC[i].x == x){								//if the current city (i) that is being checked has the same x-coordinate as the x-value that is currently been 'done',
        			dubbelarray[dubbel]=vakC[i];					//it is placed in dubbelarray, and the number of cities in dubbelarray is increased by 1
        			dubbel = dubbel + 1;							//the number of cities with the same x coordinate
        		}
			}
			int[] gehad = new int[dubbel];
			for (int n = 0; n<dubbel; n++){
				float length = 99999;
				float newlength = 99999;
				for (int i=0; i<dubbel; i++) {
					if (gehad[i]!=1){					
						newlength = PointP.distance(route[z-1], dubbelarray[i]);	//The distance between the last city in the route and the city that is being checked is calculated
						}
					if (newlength < length){						//If this city is closer than the previous choice
						route[z] = dubbelarray[i];					//It is placed in the route
						length = newlength;							//New distance is stored
						done = i;
					}
				}
			gehad[done]=1;
			z = z + 1;												//Number of cities in the route increased by 1.
			}
        }

		//Zone D
        for (int y=PizzaPanel.ROWS; y>=0; y=y-1) {				    //Cities are placed in route according to decreasing y-values

        	int dubbel = 0;	
        	PointP[] dubbelarray = new PointP[D];					//the array that stores all cities that have the same x-coordinates and need to be put in a certain order.
        	for (int i=0; i<D; i++){							

        		if (vakD[i].y == y){								//if the current city (i) that is being checked has the same x-coordinate as the x-value that is currently been 'done',
        			dubbelarray[dubbel]=vakD[i];					//it is placed in dubbelarray, and the number of cities in dubbelarray is increased by 1
        			dubbel = dubbel + 1;							//the number of cities with the same x coordinate
        		}
			}
			int[] gehad = new int[dubbel];
			for (int n = 0; n<dubbel; n++){
				float length = 99999;
				float newlength = 99999;
				for (int i=0; i<dubbel; i++) {
					if (gehad[i]!=1){					
						newlength = PointP.distance(route[z-1], dubbelarray[i]);	//The distance between the last city in the route and the city that is being checked is calculated
						}
					if (newlength < length){						//If this city is closer than the previous choice
						route[z] = dubbelarray[i];					//It is placed in the route
						length = newlength;							//New distance is stored
						done = i;
					}
				}
			gehad[done]=1;
			z = z + 1;												//Number of cities in the route increased by 1.
			}
        }

        return route;
    }

    public String getAuthors() {
        return "Erik Kremers & Kevin Verleijsdonk -_-";
    }

    public String getDescription() {
        return "Quatro Divisione: gives cities after they are divided in four zones.";
    }

    public static void main(String[] args) {
        Solver navigation = new Navigation();
        new PizzaViewer(navigation);
    }
}
