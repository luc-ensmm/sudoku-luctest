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
public class PanelGrille extends JPanelImage implements MouseListener{
    
    private Sudoku sudoku;
    private int taille;
    private int tailleAuCarree;
    private FrameChoice fenetreChoix;
    
    public PanelGrille(Sudoku s,int width, int height){
        super(width,height);
        this.sudoku = s;
        this.taille = s.getGrille().getTaille();
        this.tailleAuCarree = taille*taille;
        fenetreChoix = new FrameChoice(this);
        fenetreChoix.setVisible(false);
        this.addMouseListener(this);
    }
    
    public PanelGrille(Sudoku s){
        super();
        this.sudoku = s;
    }
    
    public int getTailleAuCarree(){
        return tailleAuCarree;
    }
    
    public void drawGrille(){
        int height = this.getHeight();
        int width = this.getWidth();
        
        setCouleur(Color.WHITE);
        this.getGraphics2D().fillRect(0,0, width, height);
        this.repaint();
        //Lignes de la grille
        
        setCouleur(Color.BLUE);
       
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
        
        
        Grille g = this.getGrille();
        
        // Valeurs et candidats
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            
            int valeur = g.getValeurCase(i);
            int ligne = i / tailleAuCarree;
            int colonne = i - ligne * tailleAuCarree;
            
            if (valeur != 0) {
                
                int fontSize = 2*(height/tailleAuCarree)/3;
                setFontSize(fontSize);
                setCouleur(Color.BLACK);
                
                int x = colonne*(width/tailleAuCarree) + (width/tailleAuCarree)/2 - 30*fontSize/100; 
                int y = ligne*(height/tailleAuCarree) + (height/tailleAuCarree)/2 + 30*fontSize/100; 
                
                
                this.getGraphics2D().drawString(Integer.toString(valeur), x, y);
            }
            else {
                
                int lenghtCell = width/tailleAuCarree;
                int heightCell = height/tailleAuCarree;
                
                int fontSize = heightCell/taille;
                setFontSize(fontSize);
                setCouleur(Color.GRAY);
                
                int x = colonne*(width/tailleAuCarree) + 30*(lenghtCell/taille)/100;
                int y = ligne*(height/tailleAuCarree) + 90*(heightCell/taille)/100;
                
                int j = 0;
                for (Integer candidat: g.getCandidatCase(i)){
                    this.getGraphics2D().drawString(Integer.toString(candidat),
                            x + (j%taille)*(lenghtCell/taille)  , y + (j/taille)*(heightCell/taille));
                    j++;
                }
                
            }
        } 
        
        
        for (Case c: g.getEnsembleCases()){
            System.out.println(c);
        }
        
        this.repaint();
        
    }
    
    public void setCouleur(Color couleur) {
        this.getGraphics2D().setColor(couleur);
    }
    
    public Grille getGrille(){
        return sudoku.getGrille();
    }
    
    @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int lenghtCell = getWidth()/tailleAuCarree;
            int heightCell = getHeight()/tailleAuCarree;
            int column = x/lenghtCell;
            int line = y/heightCell;
            int indexCase = line*tailleAuCarree + column;
            if (sudoku.getGrille().getCase(indexCase).estModifiable()){
                //int valeurCase = sudoku.getGrille().getValeurCase(indexCase);
                //System.out.println("Index:" + indexCase + " , Valeur:" + valeurCase);
                fenetreChoix.setLocation(x, y);
                fenetreChoix.setCurrentIndex(indexCase);
                fenetreChoix.setVisible(true);
            }
            
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
          
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
        

        
    }

