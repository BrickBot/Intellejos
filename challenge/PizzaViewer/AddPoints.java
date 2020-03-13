/**
* Algorithm that tries to find the optimal route through a list of cities. It only does a small number of calculations, so the optimal route is not guaranteed.
* However, due to this small number of calculations it is possible to calculate a rather large number of cities.
*
* @authors Frank Lemaire and Rob Tieben
*/

//************************************************* START OF ALGORITHM CLASS ************************************

class AddPoints implements Solver {
	//Section: init standard variables/array
	float[][] distances; //the array to save the distances that are calculated in
	int aCity; //the number of cities
	int[] order; //the array to save the order of the cities in
	//------------------------------------------------------------

	//Method getAuthors
	//input:
	//output: String - the name(s) of the Authors
	public String getAuthors() {
		return "Frank Lemaire and Rob Tieben - DW148";
	}
	//------------------------------------------------------------

	//Method getDescription
	//input:
	//output: String - a short description of the Algorithm
	public String getDescription() {
		return "This algorithm tries to calculate the optimal route, by adding cities to the route.\nIf you have the optimal route, and you remove 1 city, you get the new optimal route\nThis algorithm tries to do the opposite: add a city at the best place";
	}
	//------------------------------------------------------------

	//Method computeRoute
	//input: PointP[] - an array of PointP
	//output: PointP[] - an array of PointP in a more optimal order
	public PointP[] computeRoute(PointP[] addresses) {
		aCity = addresses.length; //set aCity to the number of cities
		PointP[] route = new PointP[addresses.length]; // create an PointP[] array to save the PointPs in
		order = new int[addresses.length]; //create the array to save the order of cities in

		calcDistances(addresses); //fill the distances matrix

		int[] startRoute = {0}; //create an array with the startRoute in it
		searchBest(1, startRoute); //search the optimal route

		PointP[] addresses2 = new PointP[addresses.length]; //create an array with PointPs in it: to return the most-optimal found order
		for(int i=0;i<addresses.length;i++) {
			addresses2[i] = addresses[order[i]]; //fill the return array with cities, with use of the order array
		}

		return addresses2; //return

	}
	//-----------------------------------------------------------

	//Method searchBest
	//input: integer, int[] array - input the amount of cities to start calcing with, and the cities to start calcing with
	//output:
	void searchBest(int n, int[] route) {
		int[] route2 = new int[route.length+1]; //create an array to save the order in
		if(n == aCity) { //all cities have been calculated
			for (int a=0;a<order.length;a++) {
				order[a] = route[a]; //so fill the order array with the order calculated
			}
		} else {
			float[] newDistances = new float[n]; //create the array so save the new distances in
			float curDis = calcTotal(route); //calculate and save the total length of the current route
			for(int j=0; j<route.length; j++) { //foreach position in the route
				//calc new routelength
				float newDis;
				if (j+1==route.length) { //if it is the last position, calculate including the distance back to 0,0
					newDis = curDis + distances[route[j]][n] + distances[n][0] - distances[route[j]][0]; //calculate the distance back to 0,0
				} else { //else, just calculate the position to the next city
					newDis = curDis + distances[route[j]][n] + distances[n][route[j+1]] - distances[route[j]][route[j+1]]; //calculate the distance to the next point
				}
				newDistances[j] = newDis; //save the "inserted" distance in the array
			}

			//find the shortest route in the array
			int shortestDis=0;
			float shortestLength = Float.MAX_VALUE;
			for(int m=0;m<newDistances.length;m++) {
				if(newDistances[m]<shortestLength) { //go through the array with new distances, and find the shortest. Save this shortest distance.
					shortestLength=newDistances[m];
					shortestDis=m;
				}
			}

			//compute the new best route
			for (int k=0; k<route.length; k++) { //for each city
				if(k<shortestDis) {
					route2[k] = route[k]; //if it is before the place to insert the new city: leave it
				} else if(k==shortestDis) {
					route2[k] = route[k]; //if it is at the place to insert the new city: insert the new city
					route2[k+1] = n;
				} else {
					route2[k+1] = route[k];//if it is after the place to insert the new city: insert the old city, but one place to the end
				}
			}

			//go on for the shortest route
			searchBest(n+1, route2);

		}
	}
	//------------------------------------------------------------


	//Method calcTotal
	//input: int[] - the order of cities
	//output: float - the length of the route with the inputted order of cities
	float calcTotal(int [] route) {
		float r = 0;
		for(int i=0; i<route.length; i++) { //for each city in the order array, calculate the distance to the next city
			if (i+1==route.length) {
				r = r + distances[route[i]][0]; //if it is the last city, calculate the distance to 0,0
			} else {
				r = r + distances[route[i]][route[i+1]];//otherwise, calculate the distance to 0,0
			}
		}
		return r;//return the total value
	}
	//-----------------------------------------------------------

	//Method calcDistances
	//input: PointP[] - the list of cities
	//output:
	void calcDistances(PointP[] addresses) {
		distances = new float[addresses.length][addresses.length]; //create the array to save the distances in

		for(int m=0; m<addresses.length; m++) { //foreach city, calculate the distance to all the other cities
			for(int n=0; n<addresses.length; n++) {
				if (m==n) {
					distances[m][n] = Float.MAX_VALUE; //the distance from city a to city a = not to be calculated
				} else {
					 distances[m][n] = (float)PointP.distance(addresses[m], addresses[n]); //otherwise calc the distance
				}
			}
		}
	}
	//------------------------------------------------------------

	//Method main
	//input: String[] - no use here
	//output:
	public static void main(String[] args) {
		AddPoints ap = new AddPoints(); //create the object
		new PizzaViewer(ap); //start the PizzaViewer
	}
}
//**************************************************END OF ALGORITHM CLASS ************************************
