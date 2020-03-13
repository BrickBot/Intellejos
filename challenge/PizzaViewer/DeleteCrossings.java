// PIZZA DELIVERY  --> PIZZA VIEWER VERSION
// Stijn Coppieters & Arno van den Tillaart

public class DeleteCrossings implements Solver {

  public PointP[] computeRoute(PointP[] cities) {
  PointP[] route = new PointP[cities.length]; // Makes the output array
  
  float distance=0;
  float optimum=999999;
  float[][] afstanden = new float[cities.length][cities.length]; // Makes distance matrix
  afstandtabel(cities, afstanden);

  int[] volgorde = new int[cities.length]; // Changing array of points in array of ints so you can read the distance matrix
  for(int w=0; w<volgorde.length; w++){ // Filling the order array from 0 to n
  volgorde[w]=w;
  }

  for (int h = 0; h < cities.length*10; h++) {
    randomvolgorde(volgorde);

    int j = 2;
    for(int k=0; k<volgorde.length; k++){
      for(int i=0; i<volgorde.length; i++){
         for(j=i+2; j<volgorde.length; j++){
          draaien(afstanden, volgorde, i, j);
        }
    
      }
    }

    // berekenkiesroute(distance,volgorde,cities,route,optimum,afstanden);

    for(int c=0; c<cities.length-1; c++){ // Filling the output array
      distance=distance+afstanden[volgorde[c]][volgorde[c+1]];
    }
    distance=distance+afstanden[0][volgorde[cities.length-1]];
    
   
    if(distance<optimum){
      optimum=distance;

      for(int t=0; t<cities.length; t++){
        route[t]=cities[volgorde[t]];  
      }
    }

    distance=0;    
      }
      return route;
  }

  public void afstandtabel(PointP [] cities, float [][] afstanden){  // Fills a matrix with the distances between all the points
    float afstand;
    for (int j=0; j<cities.length; j++) {
      for (int k=0; k<cities.length; k++) {
        afstand=PointP.distance(cities[j],cities[k]);
        afstanden[j][k]=afstand;
        afstanden[k][j]=afstand;
      }
    }
  }
        
  public void randomvolgorde(int[] volgorde){  // Makes a new random order to start with
    for (int g = 1; g < volgorde.length; g++) {
      int r = (int) (Math.random() * (g+1));  // Makes an integer between 0 and i
      if(r!=0){
        int wissel = volgorde[r];
        volgorde[r] = volgorde[g];
        volgorde[g] = wissel;
        }
    }
  }
     
  public void draaien(float[][] afstanden, int[] volgorde, int p1, int p2)  // Seeks if there is a crossing in the route between certain points
  {
    int x = volgorde[p1];
    int y = (p1 == volgorde.length-1 ? 0 : volgorde[p1+1]);  // Setting to zero if it is at the end
    int a = volgorde[p2];
    int b = (p2 == volgorde.length-1 ? 0 : volgorde[p2+1]);
 
    if(afstanden[x][y] + afstanden[a][b] > afstanden[x][a] + afstanden[y][b]){
      omkeren(volgorde, p1+1, p2);  // Begin position: X-Y, A-B Changing direction of Y-A
    } 
  }

  public void omkeren(int volgorde[], int start, int einde)  // Changing the route by swapping the first and last point and the direction of the route between
  {
    int length = einde - start;
    if(length < 0){length += volgorde.length;}
    length = length / 2 + 1;
    for(int l = 0; l < length; l++){
      int temp = volgorde[start];  // Changing direction by swapping the first and last and going to the center
      volgorde[start] = volgorde[einde];
      volgorde[einde] = temp;
      if(++start >= volgorde.length){start = 0;}
      if(--einde < 0){einde = volgorde.length-1;}
    }
  }

  public void berekenkiesroute(float distance, int [] volgorde, PointP[] cities, PointP[] route, float optimum, float[][] afstanden){
      
  }            
                        
  public String getAuthors() {
    return "Stijn Coppieters & Arno van den Tillaart";
  }
    
  public String getDescription() {
    return "Creates order in chaos by deleting all crossings.\nSwaps two points and the route between them.";
  }

  public static void main(String[] args) {
    Solver solver = new DeleteCrossings();
    new PizzaViewer(solver);
  }
}
