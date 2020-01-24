/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import sudoku.Case;
import sudoku.Grille;
import sudoku.Sudoku;

/**
 *
 * @author yannE
 */
public class PanelGrille extends JPanelImage{
    
    private Sudoku sudoku;
    
    private int taille;
    private int tailleAuCarree;
    private NumericPad pad;
    private boolean aideActivated;
    
    public enum Draw{
        GRILLE,SOLUTION;
    }
    
    public PanelGrille(Sudoku s,int width, int height){
        super(width,height);
        this.sudoku = s;
        this.taille = s.getGrille().getTaille();
        aideActivated = false;
        this.tailleAuCarree = taille*taille;
        
        //pad = new NumericPad(this);
        //addMouseListener(pad);
    }
    
    public PanelGrille(Sudoku s){
        super(500,500);
        this.sudoku = s;
        this.taille = s.getGrille().getTaille();
        
        this.tailleAuCarree = taille*taille;
    }
    
    public void setNumericPad(NumericPad p){
        pad = p;
        addMouseListener(pad);
    }
    
    public int getTailleAuCarree(){
        return tailleAuCarree;
    }
    
    
    public void drawGrille(PanelGrille.Draw toDraw){
        
        Grille g = null;
        try {
            switch(toDraw){
                case GRILLE:
                    g = sudoku.getGrille();
                    break;
                
                case SOLUTION:
                    g = sudoku.getSolution();
                    g.videLesCandidats();
                    break;
               
                default:
                    throw new IllegalArgumentException("L'argument n'est pas dans PanelGrille.Draw");       
            }
            
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        
        int height = this.getHeight();
        int width = this.getWidth();
        
        setCouleur(Color.WHITE);
        this.getGraphics2D().fillRect(0,0, width, height);
        this.repaint();
        //Lignes de la grille
        
        setCouleur(Color.BLUE);
        resetLineThickness();
        for (int i = 1; i < tailleAuCarree; i++){
            if (i%taille != 0){
            this.getGraphics2D().drawLine(0, i*(height/tailleAuCarree), width, i*(height/tailleAuCarree)); //y
            this.getGraphics2D().drawLine(i*(width/tailleAuCarree),0,i*(width/tailleAuCarree),height); // x
            }
        }
        
        setCouleur(Color.BLACK);
        setLineThickness(3);
        for (int i = 0; i < taille; i++){
            this.getGraphics2D().drawLine(0, i*(height/taille), width, i*(height/taille));
            this.getGraphics2D().drawLine(i*(width/taille),0,i*(width/taille),height);
        }
        
        
        // Valeurs et candidats
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            drawValuesInSingleCell(g,i,Color.BLACK,Color.LIGHT_GRAY);
        } 
        
        this.repaint();
        
    }
    
    /**
     * Modifie la couleur avec laquelle les différents composants sont dessinés
     * @param couleur 
     */
    public void setCouleur(Color couleur) {
        this.getGraphics2D().setColor(couleur);
    }
    
    /**
     * Permet de dessiner une valeur d'une cellule différement des autres valeurs/candidats
     * @param g
     * @param indexCell
     * @param specialValue
     * @param colorSpecialValue
     * @param colorNonSpecialValue 
     */
    public void drawAideInSingleCell(Grille g,int indexCell, int specialValue, 
            Color colorSpecialValue, Color colorNonSpecialValue){
        
        int valeur = g.getValeurCase(indexCell);
        int ligne = indexCell / tailleAuCarree;
        int colonne = indexCell - ligne * tailleAuCarree;
        int height = this.getHeight();
        int width = this.getWidth();
        
        if (valeur != 0) {

            int fontSize = 2 * (height / tailleAuCarree) / 3;
            setFontSize(fontSize);
            if (valeur == specialValue){
                setCouleur(colorSpecialValue);
            }
            else{
                setCouleur(colorNonSpecialValue);
            }
            

            int x = colonne * (width / tailleAuCarree) + (width / tailleAuCarree) / 2 - 30 * fontSize / 100;
            int y = ligne * (height / tailleAuCarree) + (height / tailleAuCarree) / 2 + 30 * fontSize / 100;

            this.getGraphics2D().drawString(Integer.toString(valeur), x, y);
        } else {

            int lenghtCell = width / tailleAuCarree;
            int heightCell = height / tailleAuCarree;

            int fontSize = heightCell / taille;
            setFontSize(fontSize);
            

            int x = colonne * (width / tailleAuCarree) + 30 * (lenghtCell / taille) / 100;
            int y = ligne * (height / tailleAuCarree) + 90 * (heightCell / taille) / 100;

            int j = 0;
            for (Integer candidat : g.getCandidatCase(indexCell)) {
                if (candidat == specialValue) {
                    setCouleur(colorSpecialValue);
                } else {
                    setCouleur(colorNonSpecialValue);
                }
                this.getGraphics2D().drawString(Integer.toString(candidat),
                        x + (j % taille) * (lenghtCell / taille), y + (j / taille) * (heightCell / taille));
                j++;
            }

        }
        
        this.repaint();
        
    }
    
    /**
     * Dessine la valeur ou les candidats d'une case de façon uniforme
     * @param g
     * @param indexCell
     * @param colorValue
     * @param colorCandidates 
     */
    public void drawValuesInSingleCell(Grille g, int indexCell, Color colorValue, Color colorCandidates) {
        
        int valeur = g.getValeurCase(indexCell);
        int ligne = indexCell / tailleAuCarree;
        int colonne = indexCell - ligne * tailleAuCarree;
        int height = this.getHeight();
        int width = this.getWidth();
        
        if (valeur != 0) {

            int fontSize = 2 * (height / tailleAuCarree) / 3;
            setFontSize(fontSize);
            
            setCouleur(colorValue);

            int x = colonne * (width / tailleAuCarree) + (width / tailleAuCarree) / 2 - 30 * fontSize / 100;
            int y = ligne * (height / tailleAuCarree) + (height / tailleAuCarree) / 2 + 30 * fontSize / 100;

            this.getGraphics2D().drawString(Integer.toString(valeur), x, y);
        } else {

            int lenghtCell = width / tailleAuCarree;
            int heightCell = height / tailleAuCarree;

            int fontSize = heightCell / taille;
            setFontSize(fontSize);
            setCouleur(colorCandidates);

            int x = colonne * (width / tailleAuCarree) + 30 * (lenghtCell / taille) / 100;
            int y = ligne * (height / tailleAuCarree) + 90 * (heightCell / taille) / 100;

            int j = 0;
            for (Integer candidat : g.getCandidatCase(indexCell)) {
                this.getGraphics2D().drawString(Integer.toString(candidat),
                        x + (j % taille) * (lenghtCell / taille), y + (j / taille) * (heightCell / taille));
                j++;
            }

        }
        
        this.repaint();
    }
    
    /**
     * Existe car utilisée dans la classe NumericPad
     * @return l'objet Sudoku (!= copie du Sudoku, = référence à l'objet)
     */
    public Sudoku getSudoku(){
        return sudoku;
    }
    
    public void activateAide(boolean state){
        aideActivated = state;
        /*
        if (!state){
            drawGrille(Draw.GRILLE);
        }
        */
    }
    
    public boolean getAideActivated(){
        return aideActivated;
    }
    
    public void disposeNumericalPad(){
        pad.setVisible(false);
        pad.dispose();
    }
       
}

