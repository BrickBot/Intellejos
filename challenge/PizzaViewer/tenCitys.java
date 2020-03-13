/*       			       	*
 *  Rik's en Wouter's java 	*
 *  April 2005           	*
 *  Rik Speijcken			*
 *	Wouter Widdershoven		*
 *                       	*/


public class tenCitys implements Solver {
    public tenCitys() {
    }

    public PointP[] computeRoute(PointP[] cities) {

        PointP[] upper_cities = new PointP[cities.length];
        PointP[] lower_cities = new PointP[cities.length];
        int ucc = 0; int lcc = 0;


        for (int i = 0; i < cities.length; i++)
        {
			if (((10 - cities[i].x) + (cities[i].y * 2)) < 11)
				upper_cities[ucc++] = cities[i];
			else
				lower_cities[lcc++] = cities[i];
		}

        PointP[] tmp = new PointP[ucc];
        for (int i = 0; i < ucc; i++) tmp[i] = upper_cities[i];
        upper_cities = sorteren(tmp);
        tmp = new PointP[lcc];
        for (int i = 0; i < lcc; i++) tmp[i] = lower_cities[i];
        lower_cities = sorteren(tmp);
        for (int i = 0; i < lower_cities.length;i++) tmp[i] = lower_cities[(lower_cities.length - 1) - i];
        lower_cities = tmp;

        PointP[] ret = new PointP[ucc + lcc];
        for (int i = 0; i < ucc; i++) ret[i] = upper_cities[i];
        for (int i = 0; i < lcc; i++) ret[ucc + i] = lower_cities[i];

        return ret;
    }

PointP[] sorteren(PointP[] pts)
{
	PointP[] retrn = new PointP[pts.length];											// the new array
	for (int i = 0; i < pts.length; i++) retrn[i] = pts[i];
	int counter = 0;																	// the change counter
	PointP startpos = new PointP(0,0);													// the distance marker
	PointP t;																			// temporary variable to help with swapping two points

	debugArray(retrn,"begin sorteren ...");
	do
	{
		counter = 0;
		for (int i = 0; i < (retrn.length - 1);i++)										// loop through the array
		{
			// if the distance to the current pos is larger than the distance to the next pos
			if (PointP.distance(startpos,retrn[i]) > PointP.distance(startpos,retrn[i+1]))
			{																// swap the two points in the array
				t = retrn[i];
				retrn[i] = retrn[i+1];
				retrn[i+1] = t;
				counter++;
			}
		}
		debugArray(retrn,"sorteerslag (" + counter + ") ");
	} while (counter > 0);
	return retrn;
}


    public String getAuthors() {
        return " Rik Speijcken and Wouter Widdershoven";
    }

    public String getDescription() {
        return " w.p.a.widdershoven@student.tue.nl \n r.g.w.p.speijcken@student.tue.nl \n ";
    }

    public static void main(String[] args) {
        Solver solver = new tenCitys();
        new PizzaViewer(solver);
    }

    public void debugArray(PointP[] arr, String msg)
    {
		String s = msg + ": ";
		for (int i = 0; i < arr.length; i++)
			s += "(" + arr[i].x + "," + arr[i].y + ") ";
		System.out.println(s);
	}
}
