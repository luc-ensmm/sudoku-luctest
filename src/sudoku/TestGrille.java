/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author smoukoka
 */
public class TestGrille {

    /**
     * @param args the command line arguments
     */
    
   
    public static void main(String[] args) {
        // TODO code application logic here
        test_set(); 
   
    }
    
    
    public static void test_singletonCache(){
        
    }
    // fonctionnelle
    public static void test_set(){
        int taille = 3;
        int tailleAuCarre = taille*taille; 
        ArrayList<Case> testList = new ArrayList<Case>();
        
        for (int i = 0; i < (int) Math.pow(taille, 4) ; i++) {
            testList.add(new Case(taille,1,new ArrayList<Integer>(),true));
    
        }
        Grille g = new Grille(taille,testList);
        System.out.println("Image de la grille de travail\n");
        g.afficheGrille();
        
        ArrayList<Case> source = new ArrayList<>(); 
        for (int i = 0; i < tailleAuCarre; i++){
            source.add(new Case(taille,i+1,false));
        }
        
        g.setLine((taille*2)/taille, source);
        
        System.out.println("Grille après modification de la " + ((taille*2)/taille+1) + "ème ligne");
        g.afficheGrille();
        
        g.setColumn(taille, source);
        
        System.out.println("Grille après modification de la " + (taille+1) + "ème colonne");
        g.afficheGrille();
        
        g.setBlock(0, source);
        System.out.println("Grille après modification du 1er bloc");
        g.afficheGrille();
        
        /*
        Il est important de noter que pour l'instant on autorise les méthodes
        setLine, setColumn et setBlock à modifier des cases mêmes si elles sont 
        notées comme non modifiables. Seul les méthodes de la classe Case sont 
        affectés par la valeur du boolean estModifiable de la classe Case.
        En fonction des avancées du projet, il sera peut-être nécessaire que
        les méthodes set de la classe Grille ne soient pas en mesure de modifier
        une case quand son boolean estModifiable est false.
        
        */
        
        
    }
    

    public static void test_initialisationAleatoire(){
    
        int taille = 3;
        Grille g = new Grille(taille);
        System.out.println("Grille avec que des 0 (pas initialisé)");
        g.showGrille();
        int nbCasesImmuables = 17;
        g.randomInitialization(nbCasesImmuables);
        System.out.println("\n\nGrille après initialisation aléatoire:");
        g.showGrille();
        
    }
    
    
    public static void test_get(){
         
       
        int taille = 3;
        int tailleAuCarre = taille*taille; 
        ArrayList<Case> testList = new ArrayList<Case>();
        System.out.println("Image de la grille de travail\n");
        for (int i = 0; i < (int) Math.pow(taille, 4) ; i++) {
            testList.add(new Case(taille,i,new ArrayList<Integer>(),true));
            if (i%(taille*taille) == 0) {
                System.out.println("");
            }
            if (i < 10){
               System.out.print(" " + i + " "); 
            }
            else {
                System.out.print(i + " ");
            }
        }
        Grille g = new Grille(taille,testList);
        
        System.out.println("\n\nTest de la méthode getColumn:");
        for (int i = 0; i < tailleAuCarre; i++){
            ArrayList<Case> colonne = g.getColumn(i);
            System.out.println("Colonne #" + i + ":");
            String s = "";
            for (Case c: colonne){
                s+= " " + c.getValeur();
            }
            s+= "\n";
            System.out.println(s);
        }
        
        System.out.println("\n\nTest de la méthode getBlock:");
        for (int i = 0; i < tailleAuCarre; i++){
            ArrayList<Case> block = g.getBlock(i);
            System.out.println("Block #" + i + ":");
            String s = "";
            for (Case c: block){
                s+= " " + c.getValeur();
            }
            s+= "\n";
            System.out.println(s);
        }
    }
    
    
    
    public static void test_dAlgorithme(){
        // Test de l'algorithme utilisé dans le getColumn
        ArrayList<Integer> testList = new ArrayList<Integer>();
        int taille = 3; // taille d'un bloc (= nombre de ligne et de colonne d'un bloc)
        int j = 8; // numéro de la colonne à sélectionner
        int l = 0; // numéro du block à sélectionner
        int tailleAuCarre = taille*taille; // introduit pour plus de clarté
        
        
        for (int i = 0; i < (int) Math.pow(taille, 4) ; i++) {
            testList.add(i);
        }
        
        System.out.println("Tableau 2D de test de méthode:");
        
        for (int i = 0; i < (int) Math.pow(taille, 4); i++){
            if (i%(taille*taille) == 0) {
                System.out.println("");
            }
            if (i < 10){
               System.out.print(" " + testList.get(i) + " "); 
            }
            else {
                System.out.print(testList.get(i) + " ");
            }

        }
        
        
        
        System.out.println("\n\nAffichage de la " + j + "ème colonne:");
        
        
        for(int k = j; k <  j + tailleAuCarre*tailleAuCarre; k = k+ tailleAuCarre) {
            System.out.println(testList.get(k));
        }
        
        System.out.println("\nAffichage du " + l + "ème block:");
        for (int k = l*tailleAuCarre; k < l*tailleAuCarre + taille*tailleAuCarre; k+= tailleAuCarre){
            for (int m = k; m < k + taille; m++){
                System.out.println(testList.get(m));
            }
        }
        
        
        
        System.out.println("\n\nTest de la boucle dans la méthode initialisation() qui recherche le bloc auxquel\n"
                + "appartient la case i");
        
        for (int i = 1; i < tailleAuCarre; i+=2) {
           for (int k = 0; k < tailleAuCarre; k+=3){
               int n = tailleAuCarre*i + k;
               int bloc = (n/taille)*taille + k/taille;
               System.out.println("La case #" + n + " appartient au bloc " + bloc);
           }
        }
        
        
    }
}
