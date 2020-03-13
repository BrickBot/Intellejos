/*
 * corkscrewConvention.java
 *
 * Created on March 2, 2005,
 */


/*
 *
 * @author DT155
 */
 import javax.swing.*;
 import java.awt.geom.*;

 
public class corkscrewConvection2 implements Solver {

    /** Creates a new instance of corkscrewConvection */
    public corkscrewConvection2() { 
    }
    
    boolean[] citiesVisited;
    PointP[] route;
    PointP[] FCities;
    
    private void PrintRoute(int maxindex)
    {
    	String str = "(0, 0)";
    	for(int i = 1; i <= maxindex; i++)
    	{
    		str += " - (" + route[i].x + ", " + route[i].y + ")";
    	}
    	System.out.println(str);
   	}
    
    private void swap(int j, int swapPunt)
    {
		PointP temp;
	  	temp = route[j];
	   	route[j] = route[swapPunt];
	   	route[swapPunt] = temp;
    }

    
    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
    	        FCities = cities; //waar is dit voor?
		route = new PointP[cities.length];
		route[0] = new PointP(cities[0]);   // starting point should not change
		
		int j = 1;
                int swapPunt = -1; // Sergio: Initializeer. Weten jullie zeker dat swapPunt altijd een waarde heeft 
        					// wanneer hij gebruikt wordt?
        
		citiesVisited = new boolean[cities.length]; //om te onthouden welke steden al in de route zijn opgenomen
		for (int a=0; a<cities.length; a++)
		{
			citiesVisited[a] = false;
			route[a] = cities[a];
		}    
		citiesVisited[0] = true;

		for (int b=1; b<cities.length; b++)
		{
			float grootsteHoek = -1.0f; 
			int grootsteIndex = -1;
			float hoek;
			for (int p = 1; p<route.length; p++)
			{
				if(!citiesVisited[p])
				{
					hoek = (float)Math.atan2(cities[p].x, cities[p].y);
					if(hoek >= grootsteHoek && !citiesVisited[p] && cities[p].x >= route[j-1].x) 
					{
						if(hoek == grootsteHoek)
						{
							if(cities[0].distance(route[j-1], cities[p]) < cities[0].distance(route[j-1], cities[grootsteIndex]))
							{
								grootsteIndex = p; 
								grootsteHoek = hoek; 
							}
						}
						else
						{
							grootsteIndex = p; 
							grootsteHoek = hoek; 
						}
					}
				}    
			}
			if (0 <= grootsteIndex)
			{
				route[j] = cities[grootsteIndex];
				citiesVisited[grootsteIndex] = true;
				j++;
			}
		}
		

		for (int b=1; b<cities.length; b++)
		{
			float grootsteHoek = -1.0f; // Groter dan pi
			int grootsteIndex = -1;
			float hoek;
			for (int p = 1; p<route.length; p++)
			{
				if(!citiesVisited[p])
				{
					hoek = (float)Math.atan2(cities[p].x, cities[p].y); 
				        if (hoek >= grootsteHoek && cities[p].y >= route[j-1].y) 
					{
						if(hoek == grootsteHoek)
						{
							if(cities[0].distance(route[j-1], cities[p]) < cities[0].distance(route[j-1], cities[grootsteIndex]))
							{
								grootsteIndex = p; 
								grootsteHoek = hoek;
							}
						}
						else
						{
							grootsteIndex = p; 
							grootsteHoek = hoek; 
						}
					}
				}
			}
			if (0 <= grootsteIndex)	
			{
				route[j] = cities[grootsteIndex];
				citiesVisited[grootsteIndex] = true;
				j++;
			}
		}
		
		for (int b=1; b<cities.length; b++)
		{
			float grootsteHoek = -1.0f; 
			int grootsteIndex = -1; 
			float hoek;
			float hoekVorige;
			for (int p = 1; p<route.length; p++)
			{
				if(!citiesVisited[p])
				{                           
					hoek = (float)Math.atan2(cities[p].x, cities[p].y);
					hoekVorige = (float)Math.atan2(route[j-1].x, route[j-1].y);                                 
					if (hoek >= grootsteHoek && !citiesVisited[p] && cities[p].x <= route[j-1].x && hoek <= hoekVorige) 
					{
						if(hoek == grootsteHoek)
						{
							if(cities[0].distance(route[j-1], cities[p]) < cities[0].distance(route[j-1], cities[grootsteIndex]))
							{
								grootsteIndex = p; 
								grootsteHoek = hoek; 
							}
						}
						else
						{
							grootsteIndex = p; 
							grootsteHoek = hoek;
 						}
					}
				}
			}
			if (0 <= grootsteIndex)
			{
				route[j] = cities[grootsteIndex];
				citiesVisited[grootsteIndex] = true;
				j++;
			}
		}
		                
		for (int b=1; b<cities.length; b++)
		{
			float kleinsteAfstand = 1000.0f;
			for(int p = 1; p < j; p++) 
			{
				if (!citiesVisited[b])
				{
					float afstand = (float)Line2D.ptSegDist (route[p].x, route[p].y, 
															 route[(p+1) % j].x, route[(p+1) % j].y, 
															 cities[b].x, cities[b].y);
					if (afstand < kleinsteAfstand)
					{
						kleinsteAfstand = afstand;
						swapPunt = p + 1;
					}
				}
			}    
			if (!citiesVisited[b])
			{
				route[j] = cities[b];
				citiesVisited[b] = true;
				PrintRoute(j);
				for (int n = j; n > swapPunt; n--)
				{
					swap(n, n - 1);
				}
				j++;
			}                    
		}
		return route;
	}

    public String getAuthors() {
        return "Koen van Boerdonk & Gerrit-Willem Vos";
    }

    public String getDescription() {
        return "-";
    }

    public static void main(String[] args) {
        Solver solver = new corkscrewConvection2();
        new PizzaViewer(solver);
    }
}
                                             
                    
