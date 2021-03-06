/*
 * PizzaViewer.java
 *
 * Created on February 7, 2005, 3:51 PM
 */



import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

/**
 *
 * @author  kees
 */
public class PizzaViewer extends javax.swing.JFrame {
    
    Solver solver;
    
    // returns travelling distance, following straight lines
    // start en end in (0,0)
    public float calculateEuclideanDistance(PointP[] route) {
        float d = 0;
        PointP previous = new PointP(0,0);
        for (int i=0; i<route.length; i++) {
           d += PointP.distance(previous.x, previous.y, route[i].x, route[i].y);
           previous = route[i];
        }
        // get back to start
        d += PointP.distance(previous.x, previous.y, 0, 0);
        return d;
    }
    
    // returns travelling distance, following grid lines
    // start en end in (0,0)
    public float calculateGridDistance(PointP[] route) {
        float d = 0;
        PointP prev = route[route.length-1]; //cycle!
        PointP here = null;
        for (int i=0; i<route.length; i++) {
           here = route[i];
           d += Math.abs(prev.x - here.x) + Math.abs(prev.y - here.y);
           prev = here;
        }
        // get back to start
        //d += Math.abs(previous.x - 0) + Math.abs(previous.y - 0);
        return d;
    }
    
    public PizzaViewer() {
        initComponents();
        this.setVisible(true);
    }
    
    /** Creates new form PizzaViewer */
    public PizzaViewer(Solver solver) {
        this();
        this.setSolver(solver);
    }
    
    void setSolver(Solver solver) {
        this.solver = solver;
        this.solverField.setText( solver.getClass().getName() );
        this.authorText.setText( solver.getAuthors() );
        this.descriptionText.setText( solver.getDescription() );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        pizzaPanel1 = new PizzaPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        authorLabel = new javax.swing.JLabel();
        authorText = new javax.swing.JTextArea();
        descriptionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        solverField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizza Delivery Viewer");
        pizzaPanel1.setMinimumSize(new java.awt.Dimension(400, 300));
        pizzaPanel1.setPreferredSize(new java.awt.Dimension(500, 350));
        getContentPane().add(pizzaPanel1, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jButton2.setText("Load Cities");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCities(evt);
            }
        });

        jPanel4.add(jButton2);

        jButton3.setText("Save Cities");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCities(evt);
            }
        });

        jPanel4.add(jButton3);

        jPanel1.add(jPanel4);

        jButton1.setText("Compute Route");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jPanel2.setLayout(new java.awt.GridLayout(2, 3));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Euclidean distance");
        jPanel2.add(jLabel1);

        jTextField1.setEditable(false);
        jTextField1.setText("0");
        jPanel2.add(jTextField1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Grid distance");
        jPanel2.add(jLabel2);

        jTextField2.setEditable(false);
        jTextField2.setText("0");
        jPanel2.add(jTextField2);

        jPanel1.add(jPanel2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(127, 100));
        jPanel3.setPreferredSize(new java.awt.Dimension(756, 100));
        authorLabel.setText("Authors:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel3.add(authorLabel, gridBagConstraints);

        authorText.setBackground(new java.awt.Color(255, 204, 204));
        authorText.setEditable(false);
        authorText.setPreferredSize(new java.awt.Dimension(300, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel3.add(authorText, gridBagConstraints);

        descriptionLabel.setText("Description:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 14, 2);
        jPanel3.add(descriptionLabel, gridBagConstraints);

        descriptionText.setEditable(false);
        descriptionText.setPreferredSize(new java.awt.Dimension(0, 32));
        jScrollPane1.setViewportView(descriptionText);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jLabel3.setText("Solver class: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        jPanel3.add(jLabel3, gridBagConstraints);

        solverField.setText("<type class name here and press Enter>");
        solverField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSolver(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel3.add(solverField, gridBagConstraints);

        getContentPane().add(jPanel3, java.awt.BorderLayout.NORTH);

        pack();
    }//GEN-END:initComponents

    private void loadCities(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCities
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) return;
        java.io.File file = fileChooser.getSelectedFile();
        try { pizzaPanel1.readCities(file); }
        catch(java.io.IOException e) {
            String name = file.getName();
            JOptionPane.showMessageDialog(this, "Error reading from file '"+name +"'", "file error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loadCities

    private void saveCities(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCities
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) return;
        java.io.File file = fileChooser.getSelectedFile();
        try {pizzaPanel1.writeCities(file);}
        catch(java.io.IOException e) {
            String name = file.getName();
            JOptionPane.showMessageDialog(this, "Error writing to file '"+name +"'", "file error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveCities

    private void updateSolver(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateSolver
        String className = solverField.getText();
        try {
            //Class solverClass = Class.forName(this.getClass().getPackage().getName() +"."+ className);
            Class solverClass = Class.forName(className);
            this.setSolver( (Solver)solverClass.newInstance() );
        } catch(ClassNotFoundException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(this,
                    "Solver class "+className+" not found. Hints:\n " +
                    "- Is the file '"+className+".class' in the same directory as PizzaViewer etc.?\n" + 
                    "- Did you spell the name correct? (Don't add '.class')\n" +
                    "- Did you compile the code?\n", 
                    "Class not found", JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
            System.err.println(e);
        }
    }//GEN-LAST:event_updateSolver

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.solver == null) {
            JOptionPane.showMessageDialog(this, "Solver class has not been set", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        PointP[] cities = pizzaPanel1.getCities();
        PointP[] route = solver.computeRoute(cities);
        pizzaPanel1.setRoute(route);
        float dE = this.calculateEuclideanDistance(route);
        float dG = this.calculateGridDistance(route);
        jTextField1.setText((int)(dE*100)+"");  // +"" turns int into String      
        jTextField2.setText((int)(dG*100)+"");
    }//GEN-LAST:event_jButton1ActionPerformed
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PizzaViewer().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel authorLabel;
    private javax.swing.JTextArea authorText;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private PizzaPanel pizzaPanel1;
    private javax.swing.JTextField solverField;
    // End of variables declaration//GEN-END:variables
    
}
