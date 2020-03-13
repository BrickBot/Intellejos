// Autors Jeroen Verhoeven & Martijn ten Bhömer
// 

public class FinalSolver implements Solver {
int teller = 1;
PointP[] route;
PointP[] not_visited;
PointP[] cities_temp;
boolean[] visited;
int pizzas;

//distance where the first calculated distance is going to be compared to
float distance = 1000;

// the route through the pizzas_calculate is going to be calculated
int pizzas_calculate = 9;

public FinalSolver() {
	}
	
public boolean anyRight(int x) {
	boolean condition = false;
	int i = 1;
	while(!condition && (i < cities_temp.length))
	{
		condition = condition || (!visited[i] && x < cities_temp[i].x);
		i++;
	}
	return condition;
}

public boolean anyDown (int y) {
	boolean condition = false;
	int i = 1;
	while(!condition && (i < cities_temp.length))
	{
		condition = condition || (!visited[i] && y < cities_temp[i].y);
		i++;
	}
	return condition;
}

public boolean anyUp (int y) {
	boolean condition = false;
	int i = 1;
	while(!condition && (i < cities_temp.length))
	{
		condition = condition || (!visited[i] && cities_temp[i].y < y);
		i++;
	}
	return condition;
}

public boolean anyLeft (int x) {
	boolean condition = false;
	int i = 1;
	while(!condition && (i < cities_temp.length))
	{
		condition = condition || (!visited[i] && cities_temp[i].x < x);
		i++;
	}
	return condition;
}

public PointP[] checkPizza(int x, int y) {    
    for (int i = 1; i < cities_temp.length; i++) {
        if (x == cities_temp[i].x && y == cities_temp[i].y && !visited[i] && pizzas > pizzas_calculate) 
        {
            route[teller] = cities_temp[i];
         	visited[i] = true;
         	pizzas = pizzas - 1;
            teller++;
        }
    }
	return cities_temp;
}

	
 //	swap the characters at indices i and j
 public static void swap(int[] numbers, int i, int j) {
	  int c;
	  c = numbers[i];
	  numbers[i] = numbers[j]; 
	  numbers[j] = c;
  }

// print permutation of the numbers in the array
public void permutate(int[] numbers, int n) {
		
	 // array to copy the points to in the good order everytime
	 PointP [] cities_combinations = new PointP[numbers.length];
		
	 // float that contains the renewed calculated distance
	 float temp_distance = 0;
		 
	  // new permutation is in the array
	  if (n == 1) {
	  	      
			  // copy points in good order in temp_cities
			  for(int k = 0; k < numbers.length; k++) {
				 cities_combinations[k] = not_visited[numbers[k]];
				 }
				 	
			  // calculate distance from last point, to point 0
			  temp_distance = PointP.distance(cities_combinations[numbers.length-1], cities_temp[0]); 
				 
			  // calculate the other points
			  for(int l = 0; l < numbers.length-1; l++) {
				 temp_distance = temp_distance + PointP.distance(cities_combinations[l], cities_combinations[l+1]);
				 }
			  // check if the calculated distance was smaller than the previous distance
			  if(temp_distance<distance) {
				 // copy the temp_distance to distance for further comparing
				 distance = temp_distance;
					
			 // copy the temporary array to the final array
		     int o = 0;
		     for(int m = cities_temp.length-numbers.length; m < cities_temp.length; m++) {				
					 route[m] = new PointP(cities_combinations[o]);
					 o++;
					 }
			  }
	  }
				 
	  for (int i = 0; i < n; i++) {
		  swap(numbers, i, n-1);
		  permutate(numbers, n-1);
		  swap(numbers, i, n-1);
	  }
  }

public PointP[] computeRoute(PointP[] cities) {
    int Robot_x = 0;
    int Robot_y = 0;
    pizzas = cities.length-1;
	route = new PointP[cities.length];
	cities_temp = cities;
	visited = new boolean[cities.length];
	visited[0] = true;
	
//	if pizzas is smaller than the pizzas to calculate, make pizzas_calulate smaller
	 if(pizzas < pizzas_calculate) {
		 pizzas_calculate = pizzas;
	 }
	 
	for(int i = 0; i < visited.length; i++)
		visited[i] = false;
	
    while (pizzas > pizzas_calculate) {
       		while (anyRight(Robot_x)) {
            	Robot_x = Robot_x + 1;
            	checkPizza (Robot_x, Robot_y);
       		}
  
        	while (anyDown(Robot_y)) {
            	Robot_y = Robot_y + 1;
            	checkPizza (Robot_x, Robot_y);
			}
			
        	while (anyLeft(Robot_x)){
           	 	Robot_x = Robot_x - 1;
            	checkPizza (Robot_x, Robot_y);
        	}
  
			while (anyUp(Robot_y)) {
            	Robot_y = Robot_y - 1;
            	checkPizza (Robot_x, Robot_y);
			}
    }
		
    // Make the array with the points that aren't visited
	not_visited = new PointP[pizzas_calculate];
	int k = 0;
		for (int j = 1; j < cities_temp.length; j++) {
			if(!visited[j]) {
				not_visited[k] = cities_temp[j];
				k++;
				}
			}
   
    // Make an array and put the point 1 till N in array
		  int[] numbers = new int[pizzas_calculate];
		  for (int i = 0; i < numbers.length; i++) {
			  numbers[i] = i;
			  }
       
    // Cal funcion that makes the permutations    
	permutate(numbers, pizzas_calculate);
	
	route[0] = cities[0];
		
	return route;
    }
    
	public String getAuthors() {
		return "Jeroen & Martijn";
	}
    
	public String getDescription() {
		return "Algorithm for solving the route the pizzas have to be delivered";
	}

	public static void main(String[] args) {
		FinalSolver fs = new FinalSolver();
		new PizzaViewer(fs);
	}
}