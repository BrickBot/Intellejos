public class Solution implements Solver {
    
    /** Creates a new instance of SimpleSolver */
    public Solution() {
    }
    
    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        for (int i=0; i<route.length; i++) {
            route[i] = new PointP(cities[i]);
        }
        
        for(int i=1; i<route.length; i++) { //op plek i moet het volgende punt komen
            for (int j = i; j < route.length; j++) {
                float afstand=PointP.distance(route[i-1], route[i]);    
                float afstand2=PointP.distance(route[i-1], route[j]);
                if(afstand2<afstand) {
                    PointP tmp=route[j];
                    route[j]=route[i];
                    route[i]=tmp;
                }
            }
        }
        return route;
    
    }
    public String getAuthors() {
        return "Erik Strijbos & Martijn van der Wijst";
    }
    
    public String getDescription() {
        return "Solution: Search for the nearest city.";
    }

    public static void main(String[] args) {
        Solver solver = new Solution();
        new PizzaViewer(solver);
    }
}