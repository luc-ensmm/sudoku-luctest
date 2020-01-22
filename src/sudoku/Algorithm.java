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
import java.util.Random;
import static sudoku.TestGrille.stringToArray;

/**
 *
 * @author yannE
 */

/*
 Le but de cette classe est d'alléger la classe Grille. Elle contiendra
tous les algorithmes de résolution ou de génération particulière de grille
de sudoku

*/
public class Algorithm {
    
    /**
     * Un générateur de grille qui retourne une grille valide et complète 
     * de taille précisé
     * @param taille Pour l'instant 1 < taille < 5
     * @return une Grille complète et correcte
     */
    public static Grille randomSolutionGenerator(int taille){
       
        try{
            if (taille < 2){
                String errorMessage = "La taille de la grille ne peut pas être" +
                        " inférieur à 2";
                throw new IllegalArgumentException(errorMessage);
            }
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        
        ArrayList<Integer> sourceOfValues = new ArrayList<>();
        int tailleAuCarree = taille*taille;
        int i = 1;
        int j = 0;
        int k = 0;
        int valeur;
        while (i < taille + 1){
            while (j < taille){
                while (k < tailleAuCarree){
                    valeur = (i+j*taille + k)%tailleAuCarree;
                    if (valeur == 0){
                        valeur = tailleAuCarree;
                    }
                    sourceOfValues.add(valeur);
                    k++;
                }
                k = 0;
                j++;
            }
            j = 0;
            i++;
        }
        
        ArrayList<Case> ensembleCases = new ArrayList<>();
        for (Integer valeurCase: sourceOfValues){
            ensembleCases.add(new Case(taille,valeurCase, new ArrayList<>(),true));
        }
        
        Grille g = new Grille(taille,ensembleCases);
        Random ran = new Random();
        
        // au minimum taille échanges de ligne et colonne et maximum tailleAuCarree échanges
        int lineSwap = taille + ran.nextInt(tailleAuCarree-taille);
        int columnSwap = taille + ran.nextInt(tailleAuCarree-taille);
        
        for (i = 0; i < lineSwap; i++){
            int indexLine1 = ran.nextInt(tailleAuCarree);
            int indexLine2 = ran.nextInt(tailleAuCarree);
            ArrayList<Case> line1 = g.getLine(indexLine1);
            g.setLine(indexLine1, g.getLine(indexLine2));
            g.setLine(indexLine2, line1);
            
        }
        
        for (i = 0; i < columnSwap; i++){
            int indexColumn1 = ran.nextInt(tailleAuCarree);
            int indexColumn2 = ran.nextInt(tailleAuCarree);
            ArrayList<Case> column1 = g.getLine(indexColumn1);
            g.setLine(indexColumn1, g.getLine(indexColumn2));
            g.setLine(indexColumn2, column1);  
        }
        
        return g;
            
    
    }
    
    /**
     * Génère aléatoirement une grille de sudoku à partir d'une solution
     * @param solution
     * @param nbCasesReveles
     * @return une Grille incomplète
     */
    public static Grille randomGrilleGenerator(Grille solution, int nbCasesReveles){
        
        
        try {
            String errorMessage;
            if (nbCasesReveles > solution.getEnsembleCases().size()){
                errorMessage = "Le nombre de cases à révéler (" + nbCasesReveles 
                        + ") n'est pas compatibles avec la taille précisée (" + solution.getTaille() +
                        ")";
                throw new IllegalArgumentException(errorMessage);
            }
            else if (!solution.correcteEtPleine()){
                errorMessage = "La grille en paramètre n'est pas une grille solution";
                throw new IllegalArgumentException(errorMessage);
            }
            
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
 
        }
        
        for (int i  = 0; i < solution.getEnsembleCases().size(); i++){
              if (!solution.getEnsembleCases().get(i).estModifiable()){
                  solution.setModifiableCase(i, true);
              }
          }
        
        ArrayList<Integer> randomIndex = new ArrayList<>();
        int i = 0;
        int index;
        Random ran = new Random();
        while (i < nbCasesReveles) {
            index = ran.nextInt(solution.getEnsembleCases().size());
            if (!randomIndex.contains(index)) {
                randomIndex.add(index);
                i++;
            }

        }
        
        Grille g = solution.clone();
        g.videLesCandidats();
        for (i = 0; i < g.getEnsembleCases().size(); i++){
            if (randomIndex.contains(i)){
                g.setModifiableCase(i, false);
            }
            else{
                g.setValeurCase(i,0);
            }
        }
        
        return g;
        
        
    }
    
    /**
     * Génère une grille de sudoku à partir d'une solution
     * @param solution
     * @param indexCasesReveles Contient les indices des cases à révéler
     * @return une Grille incomplète
     */
    public static Grille grilleGenerator(Grille solution, List<Integer> indexCasesReveles){
        
        
        try {
            String errorMessage = "";
            if (indexCasesReveles.size() > solution.getEnsembleCases().size()){
                errorMessage = "Le nombre de cases à révéler (" + indexCasesReveles.size()
                        + ") n'est pas compatibles avec la taille précisée (" + solution.getTaille() +
                        ")";
                throw new IllegalArgumentException(errorMessage);
            }
            else if (!solution.correcteEtPleine()){
                errorMessage = "La grille en paramètre n'est pas une grille solution";
                throw new IllegalArgumentException(errorMessage);
            }
            
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        
        for (int i  = 0; i < solution.getEnsembleCases().size(); i++){
              if (!solution.getEnsembleCases().get(i).estModifiable()){
                  solution.setModifiableCase(i, true);
              }
          }
        
        Grille g = solution.clone();
        g.videLesCandidats();
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            if (indexCasesReveles.contains(i)){
                g.setModifiableCase(i, false);
            }
            else{
                g.setValeurCase(i,0);
            }
        }
        
        return g;
        
        
    }
  
        
    public static Grille genereGrille_Dessai(int i){
        
        Grille g = new Grille(3);
        if(i == 1){
                ArrayList<Case> listetest = new ArrayList<>();
                ArrayList<Integer> allCandidates = new ArrayList<>();
                for (int j= 1; j < 4; j++){
                    allCandidates.add(j);
                }
                
                listetest.add(new Case(2, 0, stringToArray("3 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("3 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("1 2 4", " "), true));
                listetest.add(new Case(2, 1, stringToArray("", " "), false));
                listetest.add(new Case(2, 2, stringToArray("", " "), false));
                listetest.add(new Case(2, 0, stringToArray("1 3 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("3 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("2", " "), true));
                listetest.add(new Case(2, 0, stringToArray("1 2 4", " "), true));
                listetest.add(new Case(2, 3, stringToArray("", " "), false));
                listetest.add(new Case(2, 1, stringToArray("", " "), false));
                listetest.add(new Case(2, 0, stringToArray("2 3 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("2 4", " "), true));
                listetest.add(new Case(2, 0, stringToArray("2 4", " "), true));
                
                /*
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 1, allCandidates, false));
                listetest.add(new Case(2, 2, allCandidates, false));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 3, allCandidates, false));
                listetest.add(new Case(2, 1, allCandidates, false));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                listetest.add(new Case(2, 0, allCandidates, true));
                */
                g = new Grille(2, listetest);
            }
        else if (i == 2){
            ArrayList<Case> listetest = new ArrayList<>();
            int tailleAuCarre = 3*3;
            ArrayList<Integer> allCandidates = new ArrayList<>();
            for (int j = 1; j <= tailleAuCarre; j++) {
                allCandidates.add(j);
            }
            for (int j = 0; j < tailleAuCarre * tailleAuCarre; j++) {
                //Collections.shuffle(allCandidates);
                listetest.add(new Case(3, (j+1)/tailleAuCarre, (ArrayList<Integer>) allCandidates.clone(), true));
            }
            
            g = new Grille(3,listetest);

        }
            
               
        return g;
       
    }
    
}
