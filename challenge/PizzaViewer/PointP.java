/*
 * PointP.java
 *
 * Created on February 12, 2005, 4:22 PM
 *
 * To replace the class java.awt.Point in Lejos that does not provide it
 * To keep things small, not all methods are implemented
 * addes distance function
 */



/**
 *
 * @author kees
 */
public class PointP {
    public int x = 0;
    public int y = 0;
    
    public PointP() {
    }
 
    public PointP(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public PointP(PointP p) {
        this(p.x, p.y);
    }
    
    public PointP(java.awt.Point p) {
        this(p.x, p.y);
    }
    
    public static float distance(PointP p, PointP q) {
        return distance(p.x, p.y, q.x, q.y);
    }
    
    public static float distance(int px, int py, int qx, int qy) {
        return (float)Math.sqrt((qx-px)*(qx-px) + (qy-py)*(qy-py));
    }
    
    public boolean equals(Object o) {
        if (! (o  instanceof PointP)) { return false; }
        PointP p = (PointP) o;
        return this.x == p.x && this.y == p.y;
    }
}
