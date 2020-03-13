public class EasySolver implements Solver {
    
    private PointP[] tempRoute, route;

    public EasySolver() {
    }

    public PointP[] computeRoute(PointP[] cities) {
        tempRoute = new PointP[cities.length+1];
        
        for (int i = 0; i < cities.length; i++) {
            tempRoute[i] = new PointP(cities[i]);
        }
        
        tempRoute[tempRoute.length-1] = new PointP(0,0);
        route = new PointP[tempRoute.length-1];
        
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////       Calculate first point       //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
        
        for (int i = 1; i < (cities.length); i++) {
            float comparingProportion = (float) tempRoute[i].y / (float) tempRoute[i].x;
            float originalProportion  = (float) tempRoute[1].y / (float) tempRoute[1].x;
            float comparingX = (float) tempRoute[i].x;
            float originalX  = (float) tempRoute[1].x;

            if (comparingProportion == originalProportion) {
                if (comparingX < originalX) {
                    swap(1, i);
                }
            }

            if (comparingProportion < originalProportion) {
                swap(1, i);
            }
        }
        
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////       Calculate Convex Hull       //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

        int z;       
       
        for (z = 2; z < (tempRoute.length) ; z++) {
                                 
            float oldDistance   = PointP.distance(tempRoute[z-2], tempRoute[z-1]); 
            float newDistance   = PointP.distance(tempRoute[z-1], tempRoute[z]); 
            float skewDistance  = PointP.distance(tempRoute[z-2], tempRoute[z]); 
            float originalAngle = (((oldDistance * oldDistance) + (newDistance * newDistance)) - (skewDistance * skewDistance)) / (2 * oldDistance * newDistance);
            
            for (int m = z; m < (tempRoute.length); m++) {
                
                float comparingSkewDistance = (float) PointP.distance(tempRoute[z-2], tempRoute[m]); // afstand route[0] - route[m]
                float comparingNewDistance  = (float) PointP.distance(tempRoute[z-1], tempRoute[m]); // afstand route[1] - route[m]
                float comparingAngle = (((oldDistance * oldDistance) + (comparingNewDistance * comparingNewDistance)) - (comparingSkewDistance * comparingSkewDistance)) / (2 * oldDistance * comparingNewDistance);
    
                if (comparingAngle < originalAngle) {
                    swap(m, z);
                    originalAngle = comparingAngle;
                }             
            }

            if (tempRoute[z].x == 0 && tempRoute[z].y == 0) {
                break;
            }
        }    
      
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////   Calculate the inner points      //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                
        
        for (z = z + 1; z < tempRoute.length; z++) {
                   
            int f = 1;      
             
            float originalPath            = (float) PointP.distance(tempRoute[0], tempRoute[1]);
            float newPathFirst            = (float) PointP.distance(tempRoute[0], tempRoute[z]);
            float newPathSecond           = (float) PointP.distance(tempRoute[1], tempRoute[z]);
            float lengthDetour            = (float) (newPathFirst + newPathSecond) - originalPath;
                 
            for ( int b = 1; b < (z - 1); b++) {            
                
                float comparingOriginalPath   = (float) PointP.distance(tempRoute[b], tempRoute[b+1]);
                float comparingNewPathFirst   = (float) PointP.distance(tempRoute[b], tempRoute[z]);
                float comparingNewPathSecond  = (float) PointP.distance(tempRoute[b+1], tempRoute[z]);
                float comparingLengthDetour   = (float) (comparingNewPathFirst + comparingNewPathSecond) - comparingOriginalPath;
           
                if (comparingLengthDetour < lengthDetour) {
                    lengthDetour = comparingLengthDetour; 
                    f = b + 1;                                                      
                }                                  
            }
            insert(z, f);
        }
        
        for (int i = 0; i < tempRoute.length-1; i++) {
            route[i] = new PointP(tempRoute[i]);
        }
    
        return tempRoute;
    }

    public String getAuthors() {
        return " David van Kerhof & Elgin Gülpinar";
    }

    private void swap(int j, int k) {
        PointP hulp;
        hulp = tempRoute[j];
        tempRoute[j] = tempRoute[k];
        tempRoute[k] = hulp;
    }
    
    private void insert(int source, int target) {
        PointP hulp;
        hulp = tempRoute[source];
        for (int p = source; p > target; p--) {
            swap(p, p-1);
        }
        
        tempRoute[target] = hulp;
    }


    public String getDescription() {
        return "EasySolver: The best solver since water";
    }

    public static void main(String[] args) {
        Solver solver = new EasySolver();
        new PizzaViewer(solver);
    }
}