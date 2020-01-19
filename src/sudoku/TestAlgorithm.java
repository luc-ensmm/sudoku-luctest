/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author yannE
 */
public class TestAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Grille g = Algorithm.genereGrille_Dessai(1);
        Grille g1 = Algorithm.genereGrille_Dessai(2);
        
        System.out.println("Une grille de taille 2:");
        g.showGrille();
        System.out.println("\n\nUne grille de taille 3:");
        g1.showGrille();
    }
    
}
