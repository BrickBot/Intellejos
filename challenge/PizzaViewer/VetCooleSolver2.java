//VetCooleSolver = aanpassing van SimpleSolver van Kees Huizing
//Moet ons algoritme laten werken in dat vage pizzaviewer programma
//WERKT & verbeterde versie, sneller & minder variables
public class VetCooleSolver2 implements Solver {
    
    public PointP[] computeRoute(PointP[] cities) {
        PointP[] route = new PointP[cities.length];
        route[0] = new PointP(cities[0]); // starting point should not change
        int scanpointx;
        byte richting = 1;
        byte arraypos;
        byte foundpos = 0;
        for (scanpointx = -1; scanpointx >= -1;) { //de totale loop
            scanpointx = scanpointx + richting; //telkens 1 punt opschuiven bij het nalopen van de het grid
            if (scanpointx < 10){
                for (arraypos = 0; arraypos < route.length; arraypos++) { //het nalopen van alle punten in de gegeven array
                    if (richting == 1 && cities[arraypos].x == scanpointx && cities[arraypos].y < 3) { //1e helft van het programma
                        route[foundpos] = cities[arraypos];
                        if (foundpos > 1 && route[foundpos-1].x == cities[arraypos].x && Math.abs(route[foundpos-2].y - route[foundpos].y) < Math.abs(route[foundpos-2].y - route[foundpos-1].y)) {
                            route[foundpos] = route[foundpos-1];
                            route[foundpos-1] = cities[arraypos];
                        }
                        foundpos++;
                    }
                    if (richting == -1 && cities[arraypos].x == scanpointx && cities[arraypos].y > 2) { //2e helft van het programma
                        route[foundpos] = cities[arraypos];
                        if (foundpos > 1 && route[foundpos-1].x == cities[arraypos].x && Math.abs(route[foundpos-2].y - route[foundpos].y) < Math.abs(route[foundpos-2].y - route[foundpos-1].y)) {
                            route[foundpos] = route[foundpos-1];
                            route[foundpos-1] = cities[arraypos];
                        }
                        foundpos++;
                    }
                }
            }
            if (scanpointx == 10) richting = -1; //als hij halverwege is en moet omdraaien
            if (scanpointx == 0 && richting == -1) break; //als hij klaar is.
        }
        return route;
    }
    public String getAuthors() {
        return "Joran Damsteegt & Danny Damen";
    }
    public String getDescription() {
        return "VetCooleSolver2: Punten vinden 3-3 methode, geoptimaliseerd";
    }
    public static void main(String[] args) {
        Solver solver = new VetCooleSolver2();
        new PizzaViewer(solver);
    }
}
