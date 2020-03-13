import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.awt.geom.*;

public class Hoekalg implements Solver {

    public Hoekalg() {
    }

    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]);
            	PointP oFive = new PointP(0,5);

		boolean[] busyCities = new boolean[cities.length];
			for (int i=0; i<cities.length; i++) {
				busyCities[i] = false;
		}
		busyCities[0] = true;

		float biggestAngle = 0;
		float smallestRoute = 100;
		float smallestDistance = 100;
		int tempCity = 0;
		int tempRoute = 0;
		int citiesDone = 0;
		int endRond = 0;


		for (int i = 1; i < cities.length; i++) {
			float result = giveAngle(PointP.distance(route[0], oFive), PointP.distance(route[0], cities[i]), PointP.distance(oFive, cities[i]));

			if (result > biggestAngle) {
				biggestAngle = result;
				tempCity = i;
			}
		}

		route[1] = new PointP(cities[tempCity]);
		busyCities[tempCity] = true;

		for (int j = 2; j < cities.length; j++) {
			biggestAngle = 0;
			for (int i = 1; i < cities.length; i++) {
				if (busyCities[i] == false) {
					float result = giveAngle(PointP.distance(route[j-1], route[j-2]), PointP.distance(route[j-1], cities[i]), PointP.distance(route[j-2], cities[i]));
					float result0 = giveAngle(PointP.distance(route[j-1], route[j-2]), PointP.distance(route[j-1], cities[0]), PointP.distance(route[j-2], cities[0]));



					if (result0 > biggestAngle) {
						biggestAngle = result0;
						tempCity = 0;
					}
					if (result0 == -1) {
						biggestAngle = 200;
						tempCity = 0;
					}
					if (result >= biggestAngle) {
							biggestAngle = result;
							tempCity = i;
					}
				}
			}
			if (tempCity == 0) {
				endRond = j-1;
				j=cities.length;
			}
            else {
				route[j] = new PointP(cities[tempCity]);
				busyCities[tempCity] = true;
			}
		}


		for (int i = 1; i < cities.length; i++) {
			smallestDistance=100;
			if (busyCities[i] == false){
				for (int k = 0; k < endRond; k++){
					float distance = (float)Line2D.ptSegDist(route[k].x, route[k].y, route[k+1].x, route[k+1].y, cities[i].x, cities[i].y);
					if (distance<smallestDistance){
						smallestDistance = distance;
						tempCity = k;

					}
					System.out.println("afstand tussen "+cities[i].x+","+cities[i].y+" en lijnstuk "+route[k].x+","+route[k].y+" - "+route[k+1].x+","+route[k+1].y+" is "+distance+" smallest: "+smallestDistance);
				}
				System.out.println("plakken na "+route[tempCity].x+","+route[tempCity].y);
				for (int j = endRond; j> tempCity; j--){
				  	route[j+1]=route[j];
				}
  				route [tempCity+1] = cities[i];

				busyCities[i] = true;
				endRond++;
			}
		}

		System.out.println("the end...");

		return route;
    }
    float giveAngle(float a, float b, float c) {
	float result;
	result = (float)Math.acos(((a*a)+(b*b)-(c*c))/(2*a*b));

	if ((result >= 0) && (result <= 3.15)){
        	return result;
	}
        else {
	   return -1;
	}
    }


    public String getAuthors() {
        return "Marcel Schneijdenberg & Pakwing Man";
    }

    public String getDescription() {
        return "Be Fast Be Simple";
    }

    public static void main(String[] args) {
        Solver solver = new Hoekalg();
        new PizzaViewer(solver);
    }
}