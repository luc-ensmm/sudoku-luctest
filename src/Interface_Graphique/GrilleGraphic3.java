/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import sudoku.Algorithm;
import sudoku.Joueur;
import sudoku.Sudoku;

/**
 *
 * @author yannE
 */
public class GrilleGraphic3 extends javax.swing.JFrame {
    
    /**
     * Creates new form Grille
     */
    
    public GrilleGraphic3() {
        sudoku = new Sudoku(new Joueur("Inconnu"),Algorithm.genereGrille_Dessai(1),Algorithm.genereGrille_Dessai(1));
        initComponents();
        panelGrille.drawGrille(PanelGrille.Draw.GRILLE);
        //this.setSize(new Dimension(frameWidth,frameHeight));
        //System.out.println("A dessiner");
       
    }
    
    public GrilleGraphic3(Sudoku s){
        sudoku = s;
        initComponents();
        panelGrille.drawGrille(PanelGrille.Draw.GRILLE);
           
    }
    
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ajouterGrille = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        menu_principal = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
       
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
       
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        panelGrille = new PanelGrille(sudoku,579,468);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setBackground(new java.awt.Color(0, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        ajouterGrille.setText("Ajouter la grille");
        
        jPanel2.add(ajouterGrille);
        jPanel2.add(filler4);

        menu_principal.setText("Menu principal");
        jPanel2.add(menu_principal);
        jPanel2.add(filler6);

   

        jPanel1.add(jPanel2);

        panelGrille.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelGrilleLayout = new javax.swing.GroupLayout(panelGrille);
        panelGrille.setLayout(panelGrilleLayout);
        panelGrilleLayout.setHorizontalGroup(
            panelGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );
        panelGrilleLayout.setVerticalGroup(
            panelGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 468, Short.MAX_VALUE)
        );

        jPanel1.add(panelGrille);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>
    
    private void ajouterGrilleActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        /* l'idée c'est d'utiliser la méthode de sauvegarde saveGame de sudoku
        et d'utiliser le nom du joueur comme code. Comme il sera possible
        d'enchainer les sauvegardes de grilles, il faudra donc dans cette
        méthode recréer un objet sudoku ayant la même grille que celui qui
        est affiché et un ayant un joueur avec un nom de code qui n'a pas 
        encore été utilisé.
        */
    } 
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphic3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphic3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphic3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphic3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrilleGraphic3().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify                     
  
    private javax.swing.JButton ajouterGrille;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private PanelGrille panelGrille;
    private javax.swing.JButton menu_principal;

   
    // End of variables declaration                   
    // Other variables declaration
    private Sudoku sudoku;
}
