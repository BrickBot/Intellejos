//package HAL8999;

/*
 * SimpleSolver.java
 *
 * Created on February 8, 2005, 12:55 PM
 */
import java.util.*;

/**
 *
 * @author ks
 */
public class HAL8999 implements Solver {
    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
        for (int i=0;i<cities.length;i++) {
            System.out.println(cities[i].x+"  "+cities[i].y);
        }


        ArrayList Route = new ArrayList();
        ArrayList newRoute=new ArrayList();
        int status=0;

        // Instances naar array waardes aan Arraylist toevoegen
        newRoute.add(new PointP(cities[0]));
        for(int i=1; i<cities.length; ++i) {
            Route.add(cities[i]);
        }


        // Vervolgens door blijven lopen tot alle punten gedaan zijn
        while (Route.size()>0) {
            PointP temppoint=null;

            ///////////////////////////
            // Stap 1: Naar rechts onder zoeken
            if (status == 0) {

                for (int i = 0; i < Route.size(); i++) {
                        // Ligt hij rechtsonder vorige punt in nieuwe route?
                        if (((PointP) (newRoute.get(newRoute.size() - 1))).y <=
                                   ((PointP) Route.get(i)).y &&
                                   ((PointP) (newRoute.get(newRoute.size() - 1))).x <=
                                   ((PointP) Route.get(i)).x) {
                                   // Nog geen gevonden linksonder, dan is het altijd goed
                                   if (temppoint==null) {
                                       temppoint = (PointP)Route.get(i);
                                   }
                                   // Anders kijken of y kleiner is
                                   else if (((PointP) Route.get(i)).y < temppoint.y) {
                                       temppoint = (PointP) Route.get(i);
                                   // Zijn Y waardes gelijk dan kijken of x waarde kleiner is
                                   }
                                   else if (((PointP) Route.get(i)).y == temppoint.y &&
                                          temppoint.x > ((PointP) Route.get(i)).x) {
                                       temppoint = (PointP) Route.get(i);
                                   }

                               }
                }
                // Geen punt rechtsonder gevonden dan naar volgende status
            }

            ///////////////////////////
            // Stap 2: Naar links onder zoeken
            else if (status == 1) {

                for (int i = 0; i < Route.size(); i++) {
                        // Ligt hij rechtsonder vorige punt in nieuwe route?
                        if (((PointP) (newRoute.get(newRoute.size() - 1))).y <
                                   ((PointP) Route.get(i)).y &&
                                   ((PointP) (newRoute.get(newRoute.size() - 1))).x >=
                                   ((PointP) Route.get(i)).x) {
                                   // Nog geen gevonden linksonder, dan is het altijd goed
                                   if (temppoint==null) {
                                       temppoint = (PointP)Route.get(i);
                                   }
                                   // Anders kijken of y kleiner is
                                   else if (((PointP) Route.get(i)).y < temppoint.y) {
                                       temppoint = (PointP) Route.get(i);
                                   // Zijn Y waardes gelijk dan kijken of x waarde kleiner is
                                   }
                                   else if (((PointP) Route.get(i)).y == temppoint.y &&
                                          temppoint.x < ((PointP) Route.get(i)).x) {
                                       temppoint = (PointP) Route.get(i);
                                   }
                        }
                }

            }

            ///////////////////////////
            // Stap 3: Naar Links boven zoeken
            else if (status == 2) {

                for (int i = 0; i < Route.size(); i++) {
                    // Ligt hij links boven vorige punt in nieuwe route?
                    if (((PointP) (newRoute.get(newRoute.size() - 1))).x >=
                        ((PointP) Route.get(i)).x) {
                        // Nog geen gevonden linksonder, dan is het altijd goed
                        if (temppoint == null) {
                            temppoint = (PointP) Route.get(i);
                        }
                        // Anders kijken of y kleiner is
                        else if (((PointP) Route.get(i)).y > temppoint.y) {
                            temppoint = (PointP) Route.get(i);
                            // Zijn Y waardes gelijk dan kijken of x waarde kleiner is
                        } else if (((PointP) Route.get(i)).y == temppoint.y &&
                                   temppoint.x < ((PointP) Route.get(i)).x) {
                            temppoint = (PointP) Route.get(i);
                        }
                    }
                }
            }
    ///////////////////////////
    // Stap 4: Overige doen
    else if (status == 3) {

        for (int i = 0; i < Route.size(); i++) {
                // Nog geen punt aangegeven dan altijd goed
               if (temppoint==null) {
                   temppoint = (PointP)Route.get(i);
               }
               // Afstand tussen vorige punt en laatste selectie groter dan tussen dit unt en vorige punt
               else if (temppoint.distance((PointP) Route.get(i),(PointP) newRoute.get(newRoute.size() - 1))<temppoint.distance(temppoint,(PointP) newRoute.get(newRoute.size() - 1))) {
                   temppoint = (PointP) Route.get(i);
               }
       }
   }


     if (temppoint == null) {
         status ++;
     }
     // Wel punt gevonden wat aan de eisen voldoet, dan toevoegen aan de nieuwe route
     else {
         System.out.println("status:"+Integer.toString(status)+" x: "+Integer.toString(temppoint.x)+" y: "+Integer.toString(temppoint.y));

         newRoute.add(temppoint);
         Route.remove(temppoint);
     }

}


        PointP[] temppoints=new PointP[newRoute.size()];
        newRoute.toArray(temppoints);

        return temppoints;

 }

    public String getAuthors() {
        return "Emiel Claessen & Niek Otten";
    }

    public String getDescription() {
        return "HAL8999: All your pizza are belong to us.";
    }

    public static void main(String[] args) {
        Solver solver = new HAL8999();
        new PizzaViewer(solver);
    }
}
