/* * S1.java *//** * * @author Joost Bastings, Robin Tollenaer  * Created: February 2005 */public class S1 implements Solver {    /** Creates a new instance of S1 */    public S1() { }    	// consolewindow, we dont need it anymore	// public ConsoleWindow myConsoleWindow = new ConsoleWindow();	public S1helper s1helper = new S1helper();      //assumes cities is non-null    public PointP[] computeRoute(PointP[] cities) {			PointP[] route = new PointP[cities.length];			//Start shortest route algorithm by joost bastings, robin tollenaer						// this moves "cities" to a float array, floatcities		// we do not want PointP objects, so coordinates are stored as floats		// a table then looks like this:		//  0		1		//0	CITY1.X CITY1.Y		//1	CITY2.X CITY2.Y		//     etc.		// the order is the order of insertion (by pizzaviewer or the argument in main())				float[][] floatcities = new float[cities.length][2];		for (int r=0; r<cities.length; r++) {            floatcities[r][0] = (float) cities[r].x;			floatcities[r][1] = (float) cities[r].y;        }				float[] finalroute = s1helper.calcRoute(floatcities);				// convert float[] to PointP[] to make PizzaViewer very happy		route[0] = new PointP(cities[0]);		for (int i=1; i<route.length; i++) {            route[i] = new PointP(cities[(int)finalroute[i-1]]);        }        return route;    }	    public String getAuthors() {        return "Joost Bastings and Robin Tollenaer";    }        public String getDescription() {        return "With a bit of luck this solver returns the shortest route using a genetic algorithm.";    }    public static void main(String[] args) {        S1 solver = new S1();        new PizzaViewer(solver);    }}