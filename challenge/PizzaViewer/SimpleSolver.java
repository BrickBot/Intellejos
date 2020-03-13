import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class SimpleSolver implements Solver {   

	//assumes cities is non-null

	public PointP[] computeRoute(PointP[] cities) 
	{
		PointP[] route = new PointP[cities.length];
		route[0] = new PointP(cities[0]);   // starting point should not change
        
        
		PointP[] temproute = new PointP[cities.length];
		PointP[] puntover = new PointP[cities.length-4];
		float[] distance = new float[cities.length];

		
		for (int i=0; i<temproute.length; i++) 
		{
		temproute[i] = new PointP(cities[i]);
		}
        
		int xy = 0;		// Punt in de uiterste hoek
		int xyn = 0;		// nummer in de array
       
		int x = 0;		// Punt uiterst rechts
		int xn = 0;		// nummer in de array
        
		int y = 0;		// Punt uiterst beneden
		int yn = 0;		// nummer in de array
        
        
		// Hij kijkt welk punt in de uiterste hoek zit

		for (int i=1; i<temproute.length; i++) 
		{
			if ((temproute[i].x + temproute[i].y) > xy) 
			{
				xy = (temproute[i].x + temproute[i].y);
				xyn = i;
			} 
		}

		// Het resetten van het uiterste punt zodat hij deze overslaat 
		// als hij gaat zoeken naar x en y.  
      
		temproute[xyn].x = 0;
		temproute[xyn].y = 0;
             
		// Hij kijkt welk punt uiterst rechts ligt, uitgesloten van xy 

		for (int i=1; i<temproute.length; i++) 
		{
			if (temproute[i].x > x) 
			{
           		x = temproute[i].x;
				xn = i;
			} 
			else {}
		}
        
		temproute[xn].x = 0;
		temproute[xn].y = 0;
        
		// Hij kijkt welk punt uiterst onder ligt, uitgesloten van xy 
  
		int b =0;
		for (int i=1; i<cities.length; i++) 
		{
			if (temproute[i].y > y)
 			{
           		y = temproute[i].y;
				b = temproute[i].x;
				yn = i;
			} 

			if ((temproute[i].y == y) && (temproute[i].x > b)) 
			{
				y = temproute[i].y;
				b = temproute[i].x;
				yn = i;
			}
		}            
                 
                
		// Posten van de punten
			System.out.println("hoekpunt = "+ xyn);
		 	System.out.println("hoogste nx = "+ xn);
			System.out.println("hoogste ny = "+ yn);   
  
		if (((cities[xyn].x+cities[xyn].y)==(temproute[xn].x+temproute[xn].y)) 
			&& (cities[xyn].x>temproute[xn].x)) 
		{
			route[1] = new PointP(cities[xyn]);
			temproute[xyn].x = 0;
			temproute[xyn].y = 0;
			route[2] = new PointP(cities[xn]);
			temproute[xn].x = 0;
			temproute[xn].y = 0;
			route[3] = new PointP(cities[yn]);
			temproute[yn].x = 0;
			temproute[yn].y = 0; 
		} 
		else 
		{
			route[1] = new PointP(cities[xn]);
			temproute[xn].x = 0;
			temproute[xn].y = 0;
			route[2] = new PointP(cities[xyn]);
			temproute[xyn].x = 0;
			temproute[xyn].y = 0;
			route[3] = new PointP(cities[yn]);
			temproute[yn].x = 0;
			temproute[yn].y = 0; 
		}
	            
		// Maak lijst van alle punten die nog niet in de route zitten

		int m = 4;     
		int o = 0;

		for (int i=0; i<temproute.length; i++) 
		{
			if (temproute[i].x + temproute[i].y > 0) 
			{
				puntover[o] = new PointP(temproute[i]);
				route[m] = new PointP(temproute[i]);
		  
		  		o++;
		  		m = m+1;
                
			} 
			else {}
		}
	
		int lijnteller = 3;

		for (int i=0; i<puntover.length; i++) 
		{
			System.out.println("Overig punt " + puntover[i].x + " " 
								+ puntover[i].y);
		
			for (int p=0; p<(lijnteller+1); p++) 
			{
				if (p < lijnteller) 
				{
					distance[p] = (float)Line2D.ptSegDist(route[p].x,route[p].y,
						route[p+1].x,route[p+1].y,puntover[i].x,puntover[i].y);
					System.out.println("afstand tussen: " + puntover[i].x + " " 
						+ puntover[i].y + " en lijn " + route[p].x + " " + 
						route[p].y + "  " + route[p+1].x + " " + route[p+1].y + 
						" is " + distance[p]);
				} 
				else 
				{
					distance[p] = (float)Line2D.ptSegDist(route[p].x,route[p].y,
						0,0,puntover[i].x,puntover[i].y);	
					System.out.println("afstand tussen: " + puntover[i].x + " " 
						+ puntover[i].y + " en lijn " + route[p].x + " " + 
						route[p].y + "  " + 0 + " " + 0 + " is " + distance[p]);
				}
			}
		
		// bepalen van de dichtsbij zijnde lijn   
     		
			int d = 0; 
			for (int r=1; r<(lijnteller+1); r++) 
			{
				if(distance[d]>distance[r]) 
				{
				d=r;
				}  		  
			}
			System.out.println("kortste"+ distance[d] + 
				" kortste (lijnstuk - punt) is van stad " + d + "naar punt " 
				+ (d+1));
			System.arraycopy(route, d, route, (d+1), (lijnteller-d+1));
			route[d+1] = new PointP(puntover[i]);		
	
			lijnteller++;	
		}
    	return route;	
	}
    
    
    
	public String getAuthors() 
	{
		return "DH154-3";
	}
    
	public String getDescription() 
	{
		return "Make Up Anyone?";
	}

	public static void main(String[] args) 
	{
		Solver solver = new SimpleSolver();
		new PizzaViewer(solver);
	}
}


