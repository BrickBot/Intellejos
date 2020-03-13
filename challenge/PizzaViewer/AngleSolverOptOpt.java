/********************************************************************************************************
* 		This version is  CopyRight Protected.
Made by: Argo of The Crey's & his fellow American.
Produced and distributed by Mark alias God!!!!!!
*
*																										*
*																										*
*																										*
********************************************************************************************************/



import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.awt.geom.*;

public class AngleSolverOptOpt implements Solver {

    public AngleSolverOptOpt() {
    }

    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]);
		PointP[] order = new PointP[cities.length];
		order[0] = new PointP(cities[0]);

		PointP oFive = new PointP(0,5);

		boolean[] busyCities = new boolean[cities.length];
		for (int i=0; i<cities.length; i++) {
			busyCities[i] = false;
		}
		busyCities[0] = true;

		float biggestAngle = -1;
		float smallestRoute = 100;
		float smallestDistance = 100;
		int tempCity = 0;
		int tempRoute = 0;
		int citiesDone = 0;
		int g = 0;

		for (int i = 1; i < cities.length; i++) {
			float result = computeAngle(PointP.distance(order[0], oFive), PointP.distance(order[0], cities[i]), PointP.distance(oFive, cities[i]));

			if (result > biggestAngle) {
				biggestAngle = result;
				tempCity = i;
			}
		}

		order[1] = new PointP(cities[tempCity]);
		busyCities[tempCity] = true;

		for (int j = 2; j < cities.length; j++) {
			biggestAngle = g;
			//System.out.println("j:" + j);
			for (int i = 1; i < cities.length; i++) {
				if (busyCities[i] == false) {
					float result = computeAngle(PointP.distance(order[j-1], order[j-2]), PointP.distance(order[j-1], cities[i]), PointP.distance(order[j-2], cities[i]));
					//System.out.println(result0);

					if (result >= biggestAngle) {
						biggestAngle = result;
						tempCity = i;
					}
				}
			}
			order[j] = new PointP(cities[tempCity]);
			busyCities[tempCity] = true;
		}

		for (int i=0; i<cities.length; i++) {
			busyCities[i] = false;
		}

		biggestAngle = 0;
		smallestRoute = 100;
		smallestDistance = 100;
		tempCity = 0;
		tempRoute = 0;
		citiesDone = 0;
		g = 0;

		for (int i = 1; i < order.length; i++) {
					float result = computeAngle(PointP.distance(route[0], oFive), PointP.distance(route[0], order[i]), PointP.distance(oFive, order[i]));

					if (result > biggestAngle) {
						biggestAngle = result;
						tempCity = i;
					}
		}

		route[1] = new PointP(order[tempCity]);
		busyCities[tempCity] = true;

		for (int j = 2; j < order.length; j++) {
			biggestAngle = g;
			//System.out.println("j:" + j);
			for (int i = 1; i < order.length; i++) {
				if (busyCities[i] == false) {
					float result = computeAngle(PointP.distance(route[j-1], route[j-2]), PointP.distance(route[j-1], order[i]), PointP.distance(route[j-2], order[i]));
					float result0 = computeAngle(PointP.distance(route[j-1], route[j-2]), PointP.distance(route[j-1], order[0]), PointP.distance(route[j-2], order[0]));
					//System.out.println(result0);


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
				for (int k = j; k < order.length; k++) {
					route[k] = order[0];
					//System.out.println("k:" + k);
					//busyCities[k] = true;
					//System.out.println("Voor de routestad: " + j + "geld: " + busyCities[k]);
					g = 40;
				}
			} else {
				route[j] = new PointP(order[tempCity]);
				busyCities[tempCity] = true;
				//System.out.println("Voor de routestad: " + j + "geld: " + busyCities[tempCity]);
			}
		}

		for (int i = 1; i < order.length; i++) {
			smallestDistance = 100;
			if (busyCities[i] == false) {
				//System.out.println("Voor de routestad: " + i + "geld: " + busyCities[i]);
				for (int j = 0; j < order.length - 1; j++) {

					float distance = (float)Line2D.ptSegDist(route[j].x, route[j].y, route[j+1].x, route[j+1].y, order[i].x, order[i].y);
					//System.out.println("Afstand naar lijn route[" + j + "] - route[" + (j+1) + "] geld: " + distance);
					if (distance < smallestDistance) {
						smallestDistance = distance;
						tempRoute = j;
						busyCities[i] = true;
					}
				}
				route = swapRoute(route, order, tempRoute +1);
				route[tempRoute + 1] = order[i];
			}
		}
		for (int i = 0; i < route.length - 3; i++){
			if ((PointP.distance(route[i], route[i+1])+PointP.distance(route[i+2],route[i+3]))>(PointP.distance(route[i+2], route[i])+PointP.distance(route[i+1], route[i+3]))){
				PointP temp = route[i+1];
				route[i+1] = route[i+2];
				route[i+2] = temp;
			}
		}

        return route;
    }

    float computeAngle(float a, float b, float c) {
		float result;
		result = (float)Math.acos(((a*a)+(b*b)-(c*c))/(2*a*b));

		if ((result >= 0) && (result <= 3.15)){
			return result;
		} else {
			return -1;
		}
	}

	PointP[] swapRoute(PointP[] route, PointP[] order, int citiesDone) {
		for (int i = 1; i < order.length - citiesDone; i++) {
			route[order.length - i] = route[order.length - 1 - i];
			//System.out.println("Voor de routestad: " + j + "geld: " + busyCities[tempCity]);
		}
		return route;
	}
    public String getAuthors() {
        return "Mark Brand & Argo van Crey";
    }

    public String getDescription() {
        return "Hij kan niet Korter!!!!!";
    }

    public static void main(String[] args) {
        Solver solver = new AngleSolverOptOpt();
        new PizzaViewer(solver);
    }
}
