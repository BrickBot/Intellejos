//punt object

class Punt {
    double x = 0;
    double y = 0;
    double hoek = 0;
    double afstand = 0;
    double centrumX = 0;
    double centrumY = 0;
    double preAngle = 0;
    
   
    public Punt(double centrumX, double centrumY, double nX, double nY, double preAngle){
        x = nX;
        y = nY;
        hoek = hoekMethod(centrumX, centrumY, x, y);
        if (hoek < preAngle){
            hoek += 360;
        }
        afstand = afstandMethod(centrumX, centrumY, x, y);
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getHoek(){
        return hoek;
    }
    
    public void setHoek(int nieuwHoek){
        hoek = nieuwHoek;
    }
        
    public double getAfstand(){
        return afstand;
    }
    
    //the method for calcualting the distance from the point to the center
    public static double afstandMethod(double centrumX, double centrumY, double X, double Y){
        return Math.sqrt(((X - centrumX)*(X - centrumX)) + ((Y - centrumY)*(Y - centrumY)));
    }
    
    //the method for calculating the angle fromt the center to the point
    public static double hoekMethod(double input1x, double input1y, double x, double y){
        
        double input2x = x;
        double input2y = y;
        double a = 0;
        double output = 0;
        
        //calculate the angle in a 360 degree radius
        if (((input2x - input1x) >= 0) && ((input2y - input1y) >=0)){
            a = (input2x - input1x) / (input2y - input1y);
            output = Math.toDegrees(Math.atan(a)); 
        }
    
        else if (((input2x - input1x) >= 0) && ((input2y - input1y) < 0)){
            a = -(input2y - input1y) / (input2x - input1x);
            output = Math.toDegrees(Math.atan(a)) + 90; 
        }
    
        else if (((input2x - input1x) < 0) && ((input2y - input1y) < 0)){
            a = (input2x - input1x) / (input2y - input1y);
            output = Math.toDegrees(Math.atan(a)) + 180; 
        }
    
        else {
            a = -(input2y - input1y) / (input2x - input1x);
            output = Math.toDegrees(Math.atan(a)) + 270; 
        }
        
        return output;
    }        
}
