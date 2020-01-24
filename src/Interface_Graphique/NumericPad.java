/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import sudoku.Algorithm;
import sudoku.Coup;
import sudoku.Grille;
import sudoku.Joueur;
import sudoku.Sudoku;

/**
 *
 * @author yannE
 */

/**
 * Fenêtre représentant le "pad numérique"
 * @author yannE
 */
public class NumericPad extends javax.swing.JFrame implements MouseListener{

    /**
     * Creates new form FrameChoice
     */
    
    public NumericPad(PanelGrille pg){
        
        jPanel1 = new javax.swing.JPanel();
        this.parentPanel = pg;
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 51));
        jPanel1.setLayout(new java.awt.GridLayout(pg.getSudoku().getGrille().getTaille(),
                        pg.getSudoku().getGrille().getTaille()));
       
        
        for (int i = 1; i <= pg.getTailleAuCarree(); i++){
            JButton button = new javax.swing.JButton(""+(i));
            //button.setOpaque(false); // ça n'as pas l'air de marché
            button.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonActionPerformed(e);
                }
            });
            jPanel1.add(button);
            }
        
        
        
        setResizable(false);
        this.currentIndex = -1;
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        setVisible(false);
        
        this.setLocationRelativeTo(parentPanel);
        //this.setOpacity(0.1f); // ça n'as pas l'air de marché
        pack();
      
          
    }
    
    
    public void buttonActionPerformed(java.awt.event.ActionEvent evt){
        
        
        
        int valeur = Integer.parseInt(evt.getActionCommand()); 
                                                                  
        Grille g = parentPanel.getSudoku().getGrille();
        int valeurDeLaCaseAvant = g.getValeurCase(currentIndex);
        // Case sans valeur, ni candidats
        
        if (g.getValeurCase(currentIndex) == 0 && g.getCandidatCase(currentIndex).isEmpty()){
            g.setValeurCase(currentIndex, valeur);
        }
        // Case sans valeur, avec des candidats
        else if (g.getValeurCase(currentIndex) == 0 && !g.getCandidatCase(currentIndex).isEmpty()){
            if(!g.getCandidatCase(currentIndex).contains(valeur)){
                g.addCandidatCase(currentIndex,valeur);
            }
            else{
                g.removeCandidatCase(currentIndex,valeur);
                if (g.getCandidatCase(currentIndex).size() == 1){
                    int valeurCase = g.getCandidatCase(currentIndex).get(0);
                    g.setValeurCase(currentIndex,valeurCase);
                    g.removeCandidatCase(currentIndex,valeurCase);
            }
            }
            
        }
        else { // Case avec valeur
            
            g.setValeurCase(currentIndex, 0);
            if (valeurDeLaCaseAvant != valeur && valeurDeLaCaseAvant != 0) {
                g.addCandidatCase(currentIndex, valeurDeLaCaseAvant);
                parentPanel.drawGrille(PanelGrille.Draw.GRILLE);
                g.addCandidatCase(currentIndex,valeur);
            }
        }
        
        
        parentPanel.drawGrille(PanelGrille.Draw.GRILLE);
        // ON RAJOUTE L'EVOLUTION DES COUPS ICI
        parentPanel.getSudoku().addCoup(new Coup(currentIndex,valeurDeLaCaseAvant,g.getValeurCase(currentIndex)));
        // IMPORTANT, VERIFIE SI LA GRILLE EST PLEINE
        parentPanel.getSudoku().getGrille().correcteEtPleine();
        
        
        
        
    }
    
    public void setCurrentIndex(int index){
        currentIndex = index;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        int x = e.getX(); 
        int y = e.getY();
    
        int lenghtCell = parentPanel.getWidth()/parentPanel.getTailleAuCarree();
        int heightCell = parentPanel.getHeight()/parentPanel.getTailleAuCarree();
        int column = x/lenghtCell;
        int line = y/heightCell;
        int indexCase = line*parentPanel.getTailleAuCarree()+ column;
        if (parentPanel.getSudoku().getGrille().getCase(indexCase).estModifiable()){
            if (parentPanel.getAideActivated()){
                parentPanel.drawAideInSingleCell(parentPanel.getSudoku().getGrille(),indexCase,
                        parentPanel.getSudoku().getSolution().getValeurCase(indexCase),
                        Color.GREEN,Color.RED);
            }
            else {
                /*
                int X;
                if (x < getWidth()/2){
                    X = -lenghtCell;
                }
                else{
                    X = getWidth() + lenghtCell;
                }
                */
                
                this.setLocation((int)parentPanel.getLocationOnScreen().getX() + x,
                        (int)parentPanel.getLocationOnScreen().getY() + y);
                
                setCurrentIndex(indexCase);
                setVisible(true);
            }

        } else{
            setVisible(false);
        }




    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(NumericPad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NumericPad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NumericPad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NumericPad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Grille solution = Algorithm.randomSolutionGenerator(2);
                Grille g = Algorithm.randomGrilleGenerator(solution, 8);
                PanelGrille pg = new PanelGrille(new Sudoku(new Joueur("Unknown",0),g,solution));
                new NumericPad(pg).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    // More variables
    
    private PanelGrille parentPanel;
    private int currentIndex;

}
