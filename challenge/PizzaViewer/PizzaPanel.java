/*
 * PizzaPanel.java
 *
 * Created on February 7, 2005, 10:28 AM
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author kees
 */
public class PizzaPanel extends JPanel implements MouseListener {
    // settings of the grid
    final static int ROWS = 6;
    final static int COLS = 10;
    final static int tapeWidth = 10;
    final static int numberOffset = 24;
    final static int citySize = 2*tapeWidth;
    int cw = 50;   //cell width    
    final static Color columnColor = Color.red;    
    final static Color rowColor = Color.black;
    final static Color cityColor = Color.blue;
    final static Color routeNumberColor = Color.white;
    
    Vector cities; // list of Cities to be visited, including starting point (should be (0,0))
    PointP[] route; // optimized rearrangement of cities
       
        /** Creates a new instance of PizzaView */
    public PizzaPanel() {
        this.addMouseListener( this );
        //intitialize route and cities
        this.reset();
        this.cities.add(new PointP(0,0));
    }

    
    public void reset() {
        this.cities = new Vector();
        this.route  = new PointP[0];
    }
    
    // converts grid coordinates to pixel coordinates of center of node
    PointP grid2screen(int x, int y) {
        return new PointP( numberOffset + x*cw + tapeWidth/2,
                          numberOffset + y*cw + tapeWidth/2);
    }
    
    PointP grid2screen(PointP p) { return grid2screen(p.x, p.y); }
    
    // converts pixel coordinates to grid coordinates
    // returns the closest node
    PointP screen2grid(PointP s) {
        PointP p = new PointP( (s.x - numberOffset - tapeWidth/2 + cw/2)/cw, 
                             (s.y - numberOffset - tapeWidth/2 + cw/2)/cw);
        p.x = Math.max( 0, Math.min(p.x, COLS-1));
        p.y = Math.max( 0, Math.min(p.y, ROWS-1));
        return p;
    }
         
    void drawGrid(Graphics g) {
        for (int x=0; x<COLS; x++){
            g.setColor(Color.black);
            g.drawString(Integer.toString(x), numberOffset+tapeWidth/2-4+x*cw, numberOffset-8);
            g.setColor(columnColor);
            g.fillRect(numberOffset+x*cw, numberOffset, tapeWidth, (ROWS-1)*cw+tapeWidth);
        }
        for (int y=0; y<ROWS; y++){
            g.setColor(Color.black);
            g.drawString(Integer.toString(y), numberOffset/2-8, numberOffset+y*cw+tapeWidth);
            g.setColor(rowColor);
            g.fillRect(numberOffset, numberOffset+y*cw, (COLS-1)*cw+tapeWidth, tapeWidth);
        }
    }
    
     void drawStart(Graphics g) {
        // p should be (0,0), but we try to be general
        PointP p = (PointP)cities.firstElement();
        int sizex = 34; 
        int sizey = 16;
        g.setColor(cityColor);
        PointP upperleft = new PointP(grid2screen(p.x, p.y).x - sizex/2, grid2screen(p.x, p.y).y - sizey/2);
        PointP center = new PointP(grid2screen(p.x, p.y));
        g.fillOval(upperleft.x, upperleft.y, sizex, sizey);
        g.setColor(Color.white);
        g.drawString("start", upperleft.x+1, upperleft.y+sizey-3);
    }
 
    void drawCities(Graphics g) {
        if (this.cities.size()<1) return; // not or wrongly set
        Iterator i=cities.iterator();
        i.next(); // skip first element; will be drawn later
        for (; i.hasNext(); ) {
            PointP c = grid2screen((PointP)(i.next()));
            g.setColor(cityColor);
            int size = this.citySize;
            g.fillOval(c.x-size/2, c.y-size/2, size, size);
        }
    }
    
    void drawRoute(Graphics g) {
        if (this.route.length < 1) return; // route is not or wrongly set
        PointP prev = grid2screen(route[route.length-1]); // make a cycle
        PointP here = null;
        for (int i=0; i<route.length; i++){
            here = grid2screen(route[i]);
            g.setColor(cityColor);
            g.drawLine(prev.x, prev.y, here.x, here.y);
            prev = here;
        }
        g.setColor(routeNumberColor);
        for (int i=1; i<route.length; i++) {
            here = grid2screen(route[i]);
            g.drawString(Integer.toString(i), here.x-(i<10?4:8), here.y+4); 
        }
    }
       
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        this.drawGrid(g);
        this.drawCities(g);
        this.drawRoute(g);
        this.drawStart(g);
    }
       
    void toggleCity(PointP node) {
        boolean found = false;
        Iterator i = cities.iterator();
        for (; i.hasNext() && !found; ) {
            found = node.equals(i.next());
        }
        if (found) {
            i.remove();
        } else {
            cities.add(node);
        }
    }
    
    public PointP[] getCities() {
        PointP[] citiesArray = new PointP[cities.size()];
        int j = 0;
        for (Iterator i = this.cities.iterator(); i.hasNext(); j++) {
            citiesArray[j] = (PointP) i.next();
        }
        return citiesArray;
    }
    
    void addCity(PointP node) {
        boolean found = false;
        Iterator i = cities.iterator();
        for (; i.hasNext() && !found; ) {
            found = node.equals(i.next());
        }
        if (! found) {
            cities.add(node);
        }
    }
        
    public void readCities(File file) throws IOException {
        this.reset();
        StreamTokenizer st = new StreamTokenizer( new FileReader(file) );
        int token = st.nextToken();
        int[] p = new int[2]; // pair of coordinates
        int i = 0;
        boolean outliers = false;
                
        while (token != StreamTokenizer.TT_EOF) {
            if (token == StreamTokenizer.TT_NUMBER) {
                p[i] = (int)st.nval;
                i++;
            }
            if (i==2) { // we have a new pair
                if ( 0<=p[0] && p[0]<COLS && 0<=p[1] && p[1]<ROWS )
                    this.addCity( new PointP(p[0], p[1]));
                else 
                    outliers = true;
                i = 0;
            }
            token = st.nextToken();
        }
        String message = "";
        if (outliers) message += "Found points outside grid; ignored\n";
        if (this.cities.size()==0) {
            message += "Found no coordinate pairs in file\n";
            this.cities.add(new PointP(0,0));
        } else if (! ((PointP)this.cities.firstElement()).equals(new PointP(0,0)))
            message += "City list should start with 0 0\n";
        if (! message.equals("")) {
            JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
        }
        this.repaint();
    }
    
    public void writeCities(File file) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(file));
        for (Iterator i = this.cities.iterator(); i.hasNext(); ) {
            PointP p = (PointP)i.next();
            pw.println(p.x +" "+ p.y);
        }
        pw.close();
    }
    
    public void setRoute(PointP[] route) {
        if (route == null || route.length<1) {
            JOptionPane.showMessageDialog(this, "Route error", "Error in setRoute: no route", JOptionPane.ERROR_MESSAGE);          
            return;
        }
        this.route = new PointP[route.length];
        for (int i=0; i<route.length; i++) {
            this.route[i] = new PointP(route[i]);
        }
        this.repaint();
        
    }
    
    public void mouseReleased(MouseEvent e) {
        PointP clickloc = new PointP(e.getPoint());
        PointP node = this.screen2grid(clickloc);
        PointP nodeloc = this.grid2screen(node);
        if (PointP.distance(clickloc, nodeloc) < 0.6*citySize) {
            this.toggleCity(node);
            this.repaint();
        }
    }
    
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited (MouseEvent e) { }
           
}
