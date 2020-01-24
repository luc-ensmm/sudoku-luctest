/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import Interface_Graphique.Menu_Principal;

/**
 *
 * @author yannE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println("Mode de jeu: 1)Console 2)Graphique");
        int choix = Clavier.Clavier.getInt();
        if (choix == 1){
            Sudoku.playCommande();
        }
        else if(choix == 2){
            Menu_Principal.main(args);
        }
    }
    
}
