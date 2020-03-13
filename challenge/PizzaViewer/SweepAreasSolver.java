import java.math.*;
import javax.swing.*;
import java.lang.Math.*;
import java.lang.*;

    
public class SweepAreasSolver implements Solver {
    
    
    /** Creates a new instance of SweepAreasSolver*/
    public SweepAreasSolver() {
    }
    
    //assumes cities is non-null
    public PointP[] computeRoute(PointP[] cities) {
       // PointP(int x, int y){
                     
        PointP[] route = new PointP[cities.length];
        //coordinates[][] = new coordinates
        route[0] = new PointP(cities[0]);   // starting point should not change
        for (int i=1; i<route.length; i++) {
        route[i] = new PointP(cities[i]);}
        
        int areas[] = new int[cities.length];
        boolean visited[] = new boolean[cities.length];
        int numbOfCitInArea[] = new int[6];//number of cities in area
        int xMin = 100;// 9/5 is the maximum x/y in this grid and 0 is the minimum
        int xMax = 0;
        int yMin = 100;
        int yMax = 0;
        float xMiddle, yMiddle;//the middle point of  "our" grid
        int currentCity=0;
        int currentX = 0;// starting point 0,0
        int currentY = 0;
        int currentArea = 1;
        int closestI= 100;
        float closestDis;//closest distance
        float distanceD;
        int k=1;
        boolean foundCl = false; //found closest
        int nextI=0;
        int xDis=0;
        int yDis=0;
        float dis=0;
        int xDisMin=10000;
        int yDisMin=10000;
        float disMin=10000;
        boolean foundCityInArea=false;
        int citiesLeft=cities.length-1;
        int currentRoute=0;
    
        
                
        
        //initializing areas & visited  
        for (int i = 0; i<cities.length; i++){
            areas[i]=0;
            visited[i]=false;
        }
        
        //initializing  number of cities in area
        for (int i = 0; i<6; i++){
            numbOfCitInArea[i]= 0;
        }
        
        //finding minimum and maximum x and y  
        for (int i=0; i < cities.length; i++){
            if(cities[i].x >= xMax){
            xMax = cities[i].x;}
            if(cities[i].x <= xMin){
            xMin = cities[i].x;}
            if(cities[i].y >= yMax){
            yMax = cities[i].y;} 
            if(cities[i].y <= yMin){
            yMin = cities[i].y;}                  
        }
        
        //find middle point       
        xMiddle= (xMax+xMin) /2.0F;
        yMiddle= (yMax+yMin) /2.0F;
        
        //find middle of 1st area
        double xMidArea1= xMiddle/2.0;
        double yMidArea1= yMiddle/2.0;
        
        //put points into areas, area 1=NWa,2=NWb 3=NE, 4=SE, 5=SW
        for (int i=1; i < cities.length; i++){
            if ( (cities[i].x <= xMiddle)&&(cities[i].y <= yMiddle) ){
                //subdivide NW in subareas
                if ( cities[i].x < xMidArea1 && cities[i].y > yMidArea1){
                areas[i]=5;
                numbOfCitInArea[5]++;} 
                else{
                areas[i]=1;
                numbOfCitInArea[1]++;} 
            }
            if ( (cities[i].x > xMiddle)&&(cities[i].y < yMiddle) ){
            areas[i]= 2;
            numbOfCitInArea[2]++;} 
            if ( (cities[i].x >= xMiddle)&&(cities[i].y >= yMiddle) ){
            areas[i]= 3;
            numbOfCitInArea[3]++;} 
            if ( (cities[i].x < xMiddle)&&(cities[i].y > yMiddle) ){
            areas[i]= 4;
            numbOfCitInArea[4]++;}    
        }
                     
         
        //just testing: calculating distance of city 1 from starting point
        double distanceD1 = (Math.sqrt( Math.pow( Math.abs(currentX-cities[1].x), 2) + Math.pow( Math.abs(currentY-cities[1].y), 2))); 
        float distanceF1 = (float)distanceD1;
        
        //initializing closestDis
        closestDis = 10000000;
                      
           
        
        //find the closest point, look at 1st area,if no point there, then at 2nd,then 3rd and so on
        while (k<6 && foundCl==false){  
           for (int i = 1; i<cities.length; i++){
               if (areas[i]==k){
               System.out.println( "case number "+k );
               distanceD = (float)Math.sqrt( Math.pow( Math.abs(currentX-cities[i].x), 2F) + Math.pow( Math.abs(currentY-cities[i].y), 2F));
               System.out.println("city "+ i+ " is at a distance of: "+ distanceD); 
                  if ( distanceD <= closestDis){
                  closestDis= distanceD;
                  closestI=i;
                  currentArea=k;
                  currentCity=i;
                  foundCl = true;
                  }
               }
           }
           k=k+1;
        }
        
        //go to closest city first
        currentRoute=currentRoute+1;
       route[currentRoute]=cities[closestI];
       visited[currentCity]=true;
       citiesLeft=citiesLeft-1;
       
       numbOfCitInArea[currentArea]=numbOfCitInArea[currentArea]-1;
       
       
       //find next point on x axis in 1st and 2nd area
        
        if (citiesLeft !=0){ 
        for (int j=1; j<3; j++){           
              while (numbOfCitInArea[j]>0){ 
                 for (int i=1; i<cities.length; i++){
                   //look at points further on x axis
                   if (visited[i]==false && areas[i]==j && cities[i].x >= cities[currentCity].x){
                    foundCityInArea=true;
                    xDis= Math.abs( cities[i].x-cities[currentCity].x);
                    yDis=cities[i].y;
                        if( xDis<xDisMin){
                        yDisMin=cities[i].y;
                        xDisMin=xDis;
                        nextI=i;                                                                                                                             
                        }
                        else if (xDis==xDisMin && yDis<yDisMin ){
                            yDisMin=cities[i].y;
                            xDisMin=xDis;
                            nextI=i;                        
                       }
                   }        
                 }                                 
                
                                
                xDisMin=1000;
                yDisMin=1000;
                numbOfCitInArea[j]=numbOfCitInArea[j]-1;
                citiesLeft=citiesLeft-1;                   
                System.out.println("next point in "+j+" st/nd area to visit is city "+nextI);
                currentRoute=currentRoute+1;
                route[currentRoute]=cities[nextI];
                currentCity=nextI;
                visited[currentCity]=true;
                 
                              
              }
         }
                        
        System.out.println("cities left in area 1 & 2 are 0 ");
        currentArea=currentArea+1;
        }       
        nextI=0;
        foundCityInArea=false;
        xDisMin=10000;
        yDisMin=10000;
        
        
        //see if there is any point in 3rd area that is further in the x axis than current location
        for(int i=1;i<cities.length;i++){
               if ( areas[i]==3 && visited[i]==false && cities[i].x >= cities[currentCity].x){
                   foundCityInArea=true;
               }
        }
        
        //go from NE to SE with our rule
        
        if (numbOfCitInArea[3]>0){
          while (foundCityInArea){
           for(int i=1;i<cities.length;i++){
               if ( areas[i]==3 && visited[i]==false && cities[i].x >= cities[currentCity].x){
               yDis=Math.abs(cities[i].y-cities[currentCity].y);
               xDis=cities[i].x;
               foundCityInArea=true;
                  if (yDis<=yDisMin){
                  yDisMin=yDis;
                  xDisMin=xDis;
                  nextI=i;
                  } 
                  else if (yDis==yDisMin && xDis<xDisMin){
                  yDisMin=yDis;
                  xDisMin=xDis;
                  nextI=i;
                  }                   
              }
             
          }
                yDisMin=1000;
                numbOfCitInArea[3]=numbOfCitInArea[3]-1;
                citiesLeft=citiesLeft-1;                   
                System.out.println("next point in 3rd area to visit is city "+nextI);
                //go to
                currentRoute=currentRoute+1;
                route[currentRoute]=cities[nextI];
                currentCity=nextI;
                visited[currentCity]=true;
                foundCityInArea=false;
                for(int i=1;i<cities.length;i++){
                   if ( areas[i]==3 && visited[i]==false && cities[i].x >= cities[currentCity].x){
                      foundCityInArea=true;}
                  }
          }
       }
        yDisMin=10000;
        xDisMin=10000;
        
        // areas 3 and 4
        if(citiesLeft>0){
           for (int j=3; j<5; j++){           
              while (numbOfCitInArea[j]>0){ 
                 for (int i=1; i<cities.length; i++){
                   if (visited[i]==false && areas[i]==j && cities[i].x <= cities[currentCity].x){
                    foundCityInArea=true;
                    xDis= Math.abs( cities[i].x-cities[currentCity].x);
                    yDis= cities[i].y;
                       if( xDis<xDisMin){
                        yDisMin=yDis;
                        xDisMin=xDis;
                        nextI=i;                                  
                       }
                       else if (xDis==xDisMin && yDis>yDisMin){
                        yDisMin=yDis;
                        xDisMin=xDis;
                        nextI=i; 
                       }  
                   }                                    
                }
                                
                xDisMin=1000;
                numbOfCitInArea[j]=numbOfCitInArea[j]-1;
                citiesLeft=citiesLeft-1;                   
                System.out.println("next point in "+j+" st/nd area to visit is city "+nextI);
                //go to
                currentRoute=currentRoute+1;
                route[currentRoute]=cities[nextI];
                currentCity=nextI;
                visited[currentCity]=true; 
                                
              }
         }
        //printing to test data               
        System.out.println("cities left in area 3 & 4 are 0 ");
        currentArea=currentArea+1;
        } 
        
        xDisMin=1000;
        yDisMin=1000;
        
        //area 5
        if(citiesLeft>0){
            while (numbOfCitInArea[5]>0){ 
                 for (int i=1; i<cities.length; i++){
                   if (visited[i]==false && areas[i]==5){
                    foundCityInArea=true;
                    dis =(float) Math.sqrt( Math.pow( Math.abs(currentX-cities[i].x), 2) + Math.pow( Math.abs(currentY-cities[i].y), 2));
                       if( dis<=disMin){
                        disMin=dis;
                        nextI=i;                                  
                       }
                   }                                    
                }
                                
                disMin=1000;
                numbOfCitInArea[5]=numbOfCitInArea[5]-1;
                citiesLeft=citiesLeft-1;                   
                System.out.println("next point in 5th area to visit is city "+nextI);
                //go to
                currentRoute=currentRoute+1;
                route[currentRoute]=cities[nextI];
                currentCity=nextI;
                visited[currentCity]=true; 
                                
              }
         }
         
                        
        System.out.println("cities left in area 5 are 0 ");
         
            
       //testing data- just printing
       System.out.println("Number of cities(incl.starting point): "+ cities.length);
       System.out.println("min x: "+xMin+" max x: "+xMax+" min y: "+yMin+ " max y: "+yMax);
       System.out.println("middle point ("+ xMiddle+ " , "+ yMiddle+")");
       System.out.println("middle of 1st area ("+ xMidArea1+ " , "+ yMidArea1+")");
       for (int i=0; i < cities.length; i++){
          System.out.println("City "+i+" with coordinates ("+cities[i].x+","+cities[i].y+") belongs to area: "+ areas[i]);
         }  
       System.out.println("city 1 is at a distance of: " + distanceF1);
       System.out.println( "the closest city to starting point is city "+ closestI );
           
       return route;
    }
    
    
    
   
        
    public String getAuthors() {
        return "Juli Mata & Philomena Athanasiadou";
    }
    
    public String getDescription() {
        return "SweepAreasSolver: Devides Grid into 4 areas based on the cities with the highest and lowest x & y.\n Then visits areas clockwise.";
    }

    public static void main(String[] args) {
        Solver swslv = new SweepAreasSolver();
        new PizzaViewer(swslv);
    }
}
