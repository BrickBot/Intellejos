public class SimpleSolver2 implements Solver {

    public PointP[] computeRoute(PointP[] cities) {
           PointP[] volgorde = new PointP[cities.length];      //array waarin de coordinaten in de juiste volgorde komen te staan

           volgorde[0] = cities[0];        //vastleggen punt 1
           volgorde[1] = cities[1];        //vastleggen punt 2

        if (cities.length == 2){
            volgorde[0] = cities[0];
            volgorde[1] = cities[1];
            return volgorde;

        }//if

        else volgorde[2] = cities[2];      //vastleggen punt 3

        float distance2;            //vergelijkingsvariabele
        float buurpunt1;            //variabele voor afstandsberekening buurpunt - 1
        float buurpunt2;            //variabele voor afstandsberekening buurpunt + 1
        int d;                      //variabele om alle plaatsen van de volgorde array te doorlopen
        int buurcoord1;             //variabele voor bepaling coordinaat dat voor het nieuwe coordinaat ligt
        int buurcoord2;             //variabele voor bepaling coordinaat dat na het nieuwe coordinaat ligt
        int e;                      //vergelijkingsvariabele om de plaats te bepalen in de volgorde array

        for (int c=3; c<cities.length; c++){    //for loop die de gegeven plaatsen doorloopt
             distance2 = 1000;                  //instellen variabele op oneindig
             int kortsteafstandcoord = 100;                      //variabele die het coordinaat dat bij de kleinste afstand hoort onthoud
             float distance1 = 0;               //variabele voor de kleinste afstand

             for (d=0; d<c; d++){               //for loop berekening afstand tot alle voorgaande punten
             distance1 = PointP.distance(cities[c],volgorde[d]);

                 if (distance1 < distance2 || distance1 == distance2){        //if loop om de kleinste afstand te bepalen
                     distance2 = distance1;
                     kortsteafstandcoord = d;                        //onthouden coordinaat dat bij kleinste afstand hoort
                 }//if
             }//for

             buurcoord1 = kortsteafstandcoord - 1;                      //instellen coordinaat dat voor het coordinaat met de kleinste afstand ligt, buurpunt - 1
             buurcoord2 = kortsteafstandcoord + 1;                      //instellen coordinaat dat na het coordinaat met de kleinste afstand ligt, buurpunt + 1

                 if (kortsteafstandcoord==c-1){                      //if loop die langste 0 maakt omdat je anders buiten de array komt
                     buurcoord2 = 0;
                 }//if

                 if (kortsteafstandcoord==0){
                     buurcoord1 = c-1;
                 }//if

             buurpunt1 = (PointP.distance(cities[c],volgorde[buurcoord1]));       //berekening afstand tot buurpunt - 1
             buurpunt2 = (PointP.distance(cities[c],volgorde[buurcoord2]));      //berekening afstand tot buurpunt + 1

                 if (buurpunt1 < buurpunt2 || buurpunt1 == buurpunt2){

                         for (e=c; e>kortsteafstandcoord; e--){
                              volgorde[e] = volgorde[e-1];

                          }//for

                     volgorde[kortsteafstandcoord] = cities[c];

                 }//if

                 if (buurpunt2 < buurpunt1 && kortsteafstandcoord==c-1){
                     volgorde[kortsteafstandcoord+1] = cities[c];

                 }//if

                 if (buurpunt2 < buurpunt1){

                         for (e=c; e>kortsteafstandcoord + 1; e--){
                              volgorde[e] = volgorde[e-1];

                         }//for

                     volgorde[kortsteafstandcoord+1] = cities[c];

                 }//if


        }//for

    return volgorde;

    }
    public String getAuthors() {
        return "Stijn & Tom - DM156 microgroep 3";
    }

    public String getDescription() {
        return "ONS algorithme";
    }
}
