public class PizzaDeliveryRobot2 implements Solver {

    /** Creates a new instance of SimpleSolver */
    public PizzaDeliveryRobot2() {}

    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities)
    {
		PointP memory;
        float distance; 																//distance between two points

        int closest=0; 																	//closest point to the point the 'robot' is at the moment


        for (int q=0; q<cities.length; q++)
        {
            float min_distance=999999999;														//set shortest to max first
            memory=cities[q];
            cities[q]=cities[closest];
            cities[closest] = memory;
            for(int z=q+1; z<cities.length; z++)										//counter for the distance to the different points.
            {
                distance=PointP.distance(cities[q], cities[z]);
                if(distance<=min_distance)													//search for closest point
                {
                    min_distance=distance;
                    closest=z;
                }
            }
        }
        return cities;
    }

    public String getAuthors() {
        return "Jens Gijbels and Werner Bastianen";
    }

    public String getDescription() {
        return "Finds per point the nearest point";
    }

    public static void main(String[] args) {
        Solver solver = new PizzaDeliveryRobot2();
        new PizzaViewer(solver);
    }
}
