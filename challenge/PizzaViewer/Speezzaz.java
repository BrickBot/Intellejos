//Algoritme van Tom van Bergen en Paula Kassenaar, DM155


import java.util.Vector;
import java.awt.geom.*;
import java.lang.Object.*;
import java.util.Arrays;



public class Speezzaz implements Solver{

    public PointP[] cities;                         //array met originele lijst van cities
    public Vector destinations;                     //vector met bestemmingen erin
    public Vector outerGrid;                        //vector met verstgelegen punten
    public Vector outerDestinations;                //vector met verstgelegen bestemmingen



    public String getAuthors() {
        return "Tom van Bergen en Paula Kassenaar, DM155 microteam 1. ";
    }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    public String getDescription() {
        return "Speezzaz!!! Step 1: Make a list of all the points on the lines defined by the highest coordinates.\n Step 2: Define which cities fit in that list and draw lines between them. \n Step 3: Draw lines from the left-over cities to the closest allready drawn line. \n Step 4: Return the start point of each line to make a list of cities in the correct order. ";
    }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    public void OuterDestinations(){                                    //bepaalt welke bestemmingen op de buitenste rand liggen
        int COLS=0;
        int ROWS=0;


	    for (int i = 0; i < destinations.size(); i++) {                     //bepaalt de waarde van COLS
	        PointP maxPoint = (PointP) destinations.get(i);
	        if (maxPoint.x > COLS) {
	            COLS = maxPoint.x;
	        }
	    }

	    for (int i = 0; i < destinations.size(); i++) {                     //bepaalt de waarde van ROWS
	        PointP maxPoint = (PointP) destinations.get(i);
	        if (maxPoint.y > ROWS) {
	            ROWS = maxPoint.y;
	        }
	    }


        for (int i = 0; i < COLS; i++) {                                //bepaalt bovenste rij punten van OuterGrid
	       PointP outerPoint = new PointP();
	       outerGrid.add(outerPoint);
	       outerPoint.x = i;
	       outerPoint.y = 0;
        }

	for (int i = 1; i < ROWS; i++) {                               //bepaalt rechtse rij punten van outerGrid
	       PointP outerPoint = new PointP();
	       outerGrid.add(outerPoint);
	       outerPoint.x = COLS -1;
	       outerPoint.y = i;
        }

	for (int i = COLS-2; i > 0; i--) {                             //bepaalt onderste rij punten van outerGrid
	       PointP outerPoint = new PointP();
	       outerGrid.add(outerPoint);
	       outerPoint.x = i;
	       outerPoint.y = ROWS -1;
        }

	for (int i = ROWS-1; i < 0; i--) {                             //bepaalt linkse rij punten van outerGrid
	       PointP outerPoint = new PointP();
	       outerGrid.add(outerPoint);
               outerPoint.x = 0;
	       outerPoint.y = i;
	}

        for(int i=0; i<outerGrid.size(); i++){
            for(int j=0; i<destinations.size();){
                if (((PointP)outerGrid.get(i)).equals((PointP)destinations.get(j))){
                    outerDestinations.add(destinations.get(j));
                    destinations.remove(j);
                    break;
                }
                else{
                    j++;
                    break;
                }
            }
        }

        PointP endPoint = new PointP(0,0);
        outerDestinations.add(endPoint);
        outerDestinations.trimToSize();
    }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    public Vector basicLines;                              			 //basislijnen

    public void BasicLines(){                                           //verbindt de punten op de buitenste rand

    outerDestinations.trimToSize();

        for (int i = 0; i < outerDestinations.size()-1; i++) {

            PointP p = (PointP) outerDestinations.get(i);
            PointP pp = (PointP) outerDestinations.get(i+1);

            Line2D.Float line = new Line2D.Float();
            line.setLine(p.x, p.y, pp.x, pp.y);                         //maakt lijn aan tussen huidige en volgende outerDestination
            basicLines.add(line);
        }
    }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/
    public int shortestPointIndex;                                                  //index van het dichtsbijzijnde punt
    public int shortestLineIndex;                                                   //index van de bijbehorende lijn

    public void ClosestPoint(){                                         //vindt het punt met de kortste afstand tot 1 van de basislijnen
	    basicLines.trimToSize();
	    destinations.trimToSize();
	    int NUMBER_OF_DISTANCES = basicLines.size()*destinations.size();
	    double[] distances = new double[NUMBER_OF_DISTANCES];
	    double shortestDistance= Integer.MAX_VALUE;
	    int k=0;

        if (destinations.isEmpty()) {                                   //als er geen bestemmingen meer zijn (alles op outergrid) dan door naar methode ArrayPoints
            return;
            }

        else {
            for (int j = 0; j < destinations.size(); j++) {
                for (int i = 0; i < basicLines.size(); i++) {

	                PointP p = (PointP) destinations.get(j);
	                Line2D.Float l = (Line2D.Float) basicLines.get(i);
	                distances[k] = l.ptSegDist(p.x, p.y);

                    if (distances[k] < shortestDistance){
                            shortestDistance = distances[k];
                            shortestPointIndex = j;
                            shortestLineIndex = i;
                    }
	                k++;
                }
            }
        RedirectLines();
        }
    }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    public void RedirectLines(){
        float x1, y1, x2, y2;

        PointP shortestPoint = (PointP) destinations.get(shortestPointIndex);
        Line2D.Float shortestLine = (Line2D.Float) basicLines.get(shortestLineIndex);

        x1 = (float) shortestLine.getP1().getX();
        y1 = (float) shortestLine.getP1().getY();
        x2 = shortestPoint.x;
        y2 = shortestPoint.y;
        Line2D.Float line1 = new Line2D.Float(x1, y1, x2, y2 );     //maakt een lijn tussen het beginpunt van de dichtstbijzijnde lijn en de
                                                                    //dichtsbijzijnde bestemming
        x1 = shortestPoint.x;
        y1 = shortestPoint.y;
        x2 = (float) shortestLine.getP2().getX();
        y2 = (float) shortestLine.getP2().getY();
        Line2D.Float line2 = new Line2D.Float(x1, y1, x2, y2 );     //maakt een lijn tussen de dichtstbijzijnde bestemming en het eindpunt
                                                                    //van de dichtsbijzijnde lijn
        Point2D.Double nullPoint = new Point2D.Double(0,0);

        if (line1.getP1().equals(nullPoint)) {
            basicLines.add(0,line1);
        }
        else {
            basicLines.addElement(line1);
        }

        if (line2.getP1().equals(nullPoint)) {
            basicLines.add(0,line2);
        }
        else {
            basicLines.addElement(line2);
        }

        destinations.remove(shortestPoint);         //haalt de bestemming uit destinations zodat de loop in ClosestPoint eindig wordt                                                                        

        basicLines.remove(shortestLine);
        basicLines.trimToSize();
        ClosestPoint();                                                 //looped terug naar ClosestPoint
   }

/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    public PointP[] ArrayPoints() {                                     //maakt array in de goede volgorde van de bestemmingen


    	PointP[] route = new PointP[this.cities.length + 1];
    	PointP startPoint = new PointP(0,0);
    	route[0] = startPoint;

    	if (basicLines.isEmpty()) {
    		return route;
    	}
    	else{
    		int routeIndex = 1;
	    	PointP nextPoint = getOtherPointOnThisLine(startPoint);
	    	while (!nextPoint.equals(startPoint)){
	    		route[routeIndex] = nextPoint;
	    		nextPoint = getOtherPointOnThisLine(nextPoint);
	    		routeIndex++;
	    	}
	    	route[routeIndex] = startPoint;                       //voeg het startpunt aan het eind nog een keer toe.
	    	return route;
    	}
    }

    PointP getOtherPointOnThisLine( PointP point){
    	/** Een hulp-procedure voor ArrayPoints().
    	 * Vanuit een point, zoekt deze procedure de lijn (in basicLines) die punt "point" als het ene eindpunt heeft, en
    	 * retourneert vervolgens het andere eindpunt van die lijn.
    	 */

    	PointP otherPoint = new PointP();
    	Line2D.Float line;

    	boolean found = false;
    	int i = 0;

    	while (!found){
    		line = (Line2D.Float) basicLines.get(i);
    		if ((line.getP1().getX() == point.x)  && (line.getP1().getY() == point.y)) {

    			otherPoint.x = (int) line.getP2().getX();
    			otherPoint.y = (int) line.getP2().getY();
    			basicLines.remove(i);
    			found = true;
    		}
    		if ((line.getP2().getX() == point.x)  && (line.getP2().getY() == point.y)) {

    			otherPoint.x = (int) line.getP1().getX();
    			otherPoint.y = (int) line.getP1().getY();
    			basicLines.remove(i);
    			found = true;
    		}
    		i++;
    	}
    	return otherPoint;
    }
/*************************************************************************************************************************************************************
**************************************************************************************************************************************************************/

    private static void print(PointP[] test)
    {
        System.out.println("Printing cities");
        for(int i=0; i < test.length; i++)
        System.out.println("Punt " + i + " = (" + test[i].x + ", " + test[i].y + ")");
    }




    public PointP[] computeRoute(PointP[] cities) {           //maakt array van steden
        this.cities = cities;
    	destinations = new Vector(cities.length);
    	basicLines = new Vector();
        outerGrid = new Vector();
        outerDestinations = new Vector();

    	basicLines.clear();
        destinations.clear();
        outerGrid.clear();
        outerDestinations.clear();
        

    	for (int i = 0; i < cities.length; i++){              //voegt cities 1 voor 1 toe in destinations
    		destinations.add(cities[i]);
    	}

    	OuterDestinations();                                 //start methode OuterDestinations
    	BasicLines();                                        //start methode BasicLines
    	ClosestPoint();                                      //start methode ClosestPoint

    	return ArrayPoints();
    }

    public static void main(String[] args) {
       Solver solver = new Speezzaz();
       new PizzaViewer(solver);
   }

}
