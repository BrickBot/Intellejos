public class RemcoVersion2 implements Solver {
    
    /** Creates a new instance of SimpleSolver */
    public RemcoVersion2() {
    }
    
    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP help;
        float distance; //variable for the distance between two point
        for (int d=0; d<cities.length; d++){
            System.out.print("x: "+cities[d].x+"  y: "+cities[d].y);
        }
        
        int shortestNumber=0; //variable that stores the number of the shortest point
        //take the first point in the array and calculate all the different (free) cities, adds the shortest and then goes to the next city
        for (int a=0; a<cities.length; a++){//counter for the distance from the first point.
            float shortest=999999; //variable for the shortest route between two points (set to 50, because the first one is always shorter this way)
            help=cities[a];
            cities[a]=cities[shortestNumber];
            cities[shortestNumber]=help;
            for(int b=a+1; b<cities.length; b++){//counter for the distance to the different points.
                distance=PointP.distance(cities[a], cities[b]);//calculates the distance to the different points.
                if(distance<=shortest){//if the distance to the next point is shorter the the shortest (as set before) the new distance is set to the new value
                    shortest=distance;
                    shortestNumber=b;
                }
            }
        }
        for (int d=0; d<cities.length; d++){
            System.out.println("x: "+cities[d].x+"  y: "+cities[d].y);
        }
        return cities;
    }
    
    public String getAuthors() {
        return "Remco Magielse";
    }
    
    public String getDescription() {
        return "Nearest Neighbour: Searches for the nearest point and makes that the next point in the route [version 2]";
    }

    public static void main(String[] args) {
        Solver solver = new RemcoVersion2();
        new PizzaViewer(solver);
    }
}
