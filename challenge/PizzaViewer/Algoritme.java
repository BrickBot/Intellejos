/*
 * Algoritme.java
 *
 * Created on March 22, 2005, 12:55 PM
 */


/**
 *
 * @author Remco Mooren
 */
public class Algoritme implements Solver {
    /** Creates a new instance of SimpleSolver */
    public Algoritme() {
    }

    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
		PointP[] route = new PointP[cities.length];
		double[] angle = new double[cities.length];

		double anglemid;
        double xmid=0, ymid=0;

        for(int i=0; i<route.length; ++i) {
			route[i]=new PointP(cities[i]);
		}
        for (int i=1; i<route.length; i++) {
        	xmid+=cities[i].x;
        	ymid+=cities[i].y;
        }

        xmid=xmid/(cities.length-1);
        ymid=ymid/(cities.length-1);

        System.out.println("-----------------------------------\nThe mean coordinates are;\nxmid: "+xmid+" ymid: "+ymid+"\n");
        anglemid = Math.atan(ymid/xmid) * (180/Math.PI);

        for (int i=1; i<route.length; i++) {
			if (cities[i].x == xmid) {
				if (cities[i].y > ymid) {
					angle[i] = anglemid + 90;
				}
				else {
					angle[i] = anglemid + 270;
				}
			}
			else if (cities[i].y == ymid) {
				if (cities[i].x > xmid) {
					angle[i] = anglemid + 180;
				}
				else {
					angle[i] = anglemid;
				}
			}
			else if (cities[i].x < xmid) {
				if( cities[i].y> ymid) {
					angle[i] = (Math.atan((cities[i].y-ymid)/(xmid-cities[i].x)) * (180/(Math.PI))) + anglemid;
				}

				else {
					angle[i] = (Math.atan((ymid-cities[i].y)/(xmid-cities[i].x)) * (180/(Math.PI)));
					if (angle[i] > anglemid) {
						angle[i] = (360 - angle[i]) + anglemid;
					}
					else {
						angle[i]= anglemid - angle[i];
					}
				}
			}

			else if ( cities[i].x > xmid) {
				if (cities[i].y > ymid) {
					angle[i] = (Math.atan((cities[i].x-xmid)/(cities[i].y-ymid)) * (180/(Math.PI))) + anglemid + 90;
				}
				else {
					angle[i] = (Math.atan((ymid-cities[i].y)/(cities[i].x-xmid)) * (180/(Math.PI))) + anglemid + 180;
				}
			}
			System.out.println("The angle of point ("+cities[i].x+", "+cities[i].y+") is "+angle[i]+" Degrees");
		}

		for(int i=1; i<route.length; ++i) {

			for(int j=i+1; j<route.length; ++j) {

				if (angle[i]>angle[j]){
					swap(route, i, j);
					swap(angle, i, j);
				}
			}
		}
        return route;
	}

	public void swap(PointP[] p, int i, int j) {
		PointP tmpP=p[i];
		p[i]=p[j];
		p[j]=tmpP;
	}

	public void swap(double[] a, int i, int j) {
		double tmpA=a[i];
		a[i]=a[j];
		a[j]=tmpA;
	}

    public String getAuthors() {
        return "Remco Mooren";
    }

    public String getDescription() {
        return "Algorithm: Does nothing realy exiting yet:P, or does it?";
    }

    public static void main(String[] args) {
        Solver solver = new Algoritme();
        new PizzaViewer(solver);
    }
}
