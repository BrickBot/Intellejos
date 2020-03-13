/*
 * Solver.java
 *
 * Created on February 8, 2005, 12:44 PM
 */



/**
 *
 * @author kees
 */
public interface Solver {
    public PointP[] computeRoute(PointP[] cities);
    public String getDescription();
    public String getAuthors();
}
