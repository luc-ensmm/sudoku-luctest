/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;
import sudoku.Algorithm;
import sudoku.Coup;
import sudoku.Grille;
import sudoku.Joueur;
import sudoku.Sudoku;
import sudoku.GrilleListener;



/**
 * Interface du jeu
 * @author yannE
 */
public class GrilleGraphique extends javax.swing.JFrame implements GrilleListener {
    
    /**
     * Creates new form Grille
     */
    
    
    public GrilleGraphique(Sudoku s){
        initComponents(s);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getHeight()/2);
        panelGrille.drawGrille(PanelGrille.Draw.GRILLE);
        setTitle("Sudoku by Luc and Steeven");
           
    }
    
                           
    private void initComponents(Sudoku s) {
        
        sudoku = s;
        sudoku.getGrille().addListener(this);
        
        int widthPanel = 579;
        int heightPanel = 468;
        if (s.getGrille().getTaille() == 4){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenHeight = screenSize.height;
            int screenWidth = screenSize.width;
            widthPanel = 3*(screenWidth/4);
            heightPanel = 3*(screenHeight/4);
        }
        panelGrille = new PanelGrille(s,widthPanel,heightPanel);
        panelGrille.setNumericPad(new NumericPad(panelGrille));
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        coup_precedent = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        aide = new javax.swing.JToggleButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        menu_principal = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        solutionButton = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        quitter = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        scoreLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setBackground(new java.awt.Color(0, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        coup_precedent.setText("Coup précedent");
        jPanel2.add(coup_precedent);
        jPanel2.add(filler4);

        aide.setText("Aide");
        jPanel2.add(aide);
        jPanel2.add(filler2);

        menu_principal.setText("Menu principal");
        jPanel2.add(menu_principal);
        jPanel2.add(filler6);

        solutionButton.setText("Solution");
        jPanel2.add(solutionButton);
        jPanel2.add(filler3);

        quitter.setText("Quitter");
        jPanel2.add(quitter);
        jPanel2.add(filler5);

        scoreLabel.setText("Score:");
        jPanel2.add(scoreLabel);
        jPanel2.add(filler1);

        jPanel1.add(jPanel2);

        panelGrille.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelGrilleLayout = new javax.swing.GroupLayout(panelGrille);
        panelGrille.setLayout(panelGrilleLayout);
        panelGrilleLayout.setHorizontalGroup(
            panelGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, widthPanel, Short.MAX_VALUE)
        );
        panelGrilleLayout.setVerticalGroup(
            panelGrilleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, heightPanel, Short.MAX_VALUE)
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
        
        
        // Actions des boutons
        
        quitter.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt){
                
                quitterActionPerformed();
            }
        });
        
        menu_principal.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipalActionPerformed();
            }
        });
        
        solutionButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 solutionButtonActionPerformed();
            }
        });
        
        coup_precedent.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coup_precedentActionPerformed();
            }
        });
        
        aide.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                aideActionPerformed(e);
            }
        });
        
        
        

        pack();
    }                      

    @Override
    /**
     * Méthode désactivant les boutons aide, coup précedent et solution ainsi que
     * le pad numérique à la fin d'une partie de jeu
     */
    public void laGrilleEstRemplieEtCorrecte(){
        
            JOptionPane.showMessageDialog(this,"Vous avez réussi, félicitations !");
            coup_precedent.setEnabled(false);
            aide.setEnabled(false);
            solutionButton.setEnabled(false);
            panelGrille.disposeNumericalPad();
        
    }
    
    /**
     * Action du bouton quitter
     */
    public void quitterActionPerformed(){
        // J'assume que le joueur n'as pas donner son nom 
        
        String nomJoueur = (String) JOptionPane.showInputDialog(
                        this,"Votre nom:",
                        "Voulez-vous sauvegardez ?",
                        JOptionPane.WARNING_MESSAGE
                        );
        if ((nomJoueur != null) && nomJoueur.length() > 0) {
            String nom_sauvegarde = "database/saves/" + nomJoueur; 
            sudoku.setJoueurName(nom_sauvegarde);
            sudoku.saveGame();
            
        }
        
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    
    /**
     * Action du bouton Menu Principal
     */
    public void menuPrincipalActionPerformed(){
        String nomJoueur = (String) JOptionPane.showInputDialog(
                        this,"Votre nom:",
                        "Voulez-vous sauvegardez ?",
                        JOptionPane.WARNING_MESSAGE
                        );
        if ((nomJoueur != null) && nomJoueur.length() > 0) { 
            sudoku.setJoueurName(nomJoueur);
            sudoku.saveGame();
            
        }
        
        new Menu_Principal().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }
    
    /**
     * Action du bouton Solution
     */
    public void solutionButtonActionPerformed(){
        int response = JOptionPane.showConfirmDialog(
                this,
                "Voir la solution met fin à la partie et annule votre score\n"+
                        "Voulez-vous quand même voir la solution ?",
                "Attention !",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (response == 0){
            panelGrille.drawGrille(PanelGrille.Draw.SOLUTION);
            sudoku.setJoueurScore(0);
            aide.setEnabled(false);
            coup_precedent.setEnabled(false);
            solutionButton.setEnabled(false);
        }
        
    }
    
    public void coup_precedentActionPerformed(){
        
        try {
            Coup dernierCoup = sudoku.getListeCoup().pop();
            sudoku.getGrille().setValeurCase(dernierCoup.getPosition(), dernierCoup.getValeurAvant());
            // on vide les candidats par défault
            sudoku.getGrille().setCandidatCase(dernierCoup.getPosition(), new ArrayList<>());
            panelGrille.drawGrille(PanelGrille.Draw.GRILLE);

        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(this,
                    "Vous n'avez pas joué autant de coups !",
                    "Liste de coup vide !",
                    JOptionPane.ERROR_MESSAGE);

        }
        
    }
    
    private boolean aideButtonUsedAtLeastOnce = false;
    
    public void aideActionPerformed(ItemEvent e){
        
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED){
            if (!aideButtonUsedAtLeastOnce){
                aideButtonUsedAtLeastOnce = true;
                JOptionPane.showMessageDialog(this,
                        "Séléctionnez une case et la bonne valeur de la case\n"+
                                "s'affichera si elle est parmi les candidats de la case.\n"+
                                "Chaque utilisation retire -3 au score. Recliquer sur le\n"
                                + "bouton pour désactiver l'aide.", "Fonctionnement de l'aide",
                                JOptionPane.PLAIN_MESSAGE);
                
            }
            
            panelGrille.activateAide(true);
            
        }
        else if (state == ItemEvent.DESELECTED){
            panelGrille.activateAide(false);
        }
        
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
            java.util.logging.Logger.getLogger(GrilleGraphique.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphique.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphique.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrilleGraphique.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Grille solution = Algorithm.randomSolutionGenerator(2);
                Grille g = Algorithm.randomGrilleGenerator(solution, 8);
                new GrilleGraphique(new Sudoku(new Joueur("Inconnu"), g, solution)).setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JToggleButton aide;
    private javax.swing.JButton coup_precedent;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JButton solutionButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private PanelGrille panelGrille;
    private javax.swing.JButton menu_principal;
    private javax.swing.JButton quitter;
    private javax.swing.JLabel scoreLabel;
    // End of variables declaration                   
    // Other variables declaration
    private Sudoku sudoku;
}
