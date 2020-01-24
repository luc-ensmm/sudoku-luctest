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

/**
 Le but de cette classe est d'alléger la classe Grille. Elle contiendra
tous les algorithmes de résolution ou de génération particulière de grille
de sudoku

*/
public class Algorithm {
    
    
    
    
    
    /**
     * Un générateur de grille qui retourne une grille valide et complète 
     * de taille précisé
     * @param taille 
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
            throw new RuntimeException(e);
        }
        
        // Création d'une solution triviale
        
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
        
        // Mélange de ligne et de colonne afin d'avoir une nouvelle solution de façon aléatoire
        Random ran = new Random();
        
        // au minimum tailleAuCaree échanges de ligne/colonne et maximum 2*tailleAuCarree échanges
        int lineSwap = tailleAuCarree + ran.nextInt(tailleAuCarree);
        int columnSwap = tailleAuCarree + ran.nextInt(tailleAuCarree);
        
        int indexLine1;
        int indexLine2;
        for (i = 0; i < lineSwap; i++){
            // échanges entre lignes appartenant à la même ligne de bloc
            indexLine1 = ran.nextInt(tailleAuCarree);
            indexLine2 = (indexLine1/taille)*taille + ran.nextInt(taille);
            if (indexLine1 != indexLine2){
                ArrayList<Case> line1 = g.getLine(indexLine1);
                g.setLine(indexLine1, g.getLine(indexLine2));
                g.setLine(indexLine2, line1);
            }
            
        }
        
        
        int indexColumn1;
        int indexColumn2;
        for (i = 0; i < columnSwap; i++){
            // échanges entre colonnes appartenant à la même colonne de bloc
            indexColumn1 = ran.nextInt(tailleAuCarree);
            indexColumn2 = (indexColumn1/taille)*taille + ran.nextInt(taille);
            if (indexColumn1 != indexColumn2){
                ArrayList<Case> column1 = g.getLine(indexColumn1);
                g.setLine(indexColumn1, g.getLine(indexColumn2));
                g.setLine(indexColumn2, column1);  
            }
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
    
     public static void paireNue(ArrayList<Case> groupeEtudie){
        ArrayList<Case> caseAvec2Candidats = new ArrayList<Case>();
        ArrayList<Case> caseAvecPaireNue = new ArrayList<Case>();
        ArrayList<Integer> candidatsCommuns = new ArrayList<Integer>();
        ArrayList<Integer> position = new ArrayList<Integer>(); // position des cases ayant 2 candidats
        ArrayList<Integer> position2 = new ArrayList<Integer>(); //position des cases dont il ne faudra pas supprimer les candidats
        System.out.println("groupe etudie "+groupeEtudie);
        for (int i = 0; i<groupeEtudie.size(); i++){
            if(groupeEtudie.get(i).estModifiable() ==true && groupeEtudie.get(i).getCandidats().size() == 2){
                caseAvec2Candidats.add(groupeEtudie.get(i)); //liste des cases qui ont 2 candidats  
                position.add(i);
                System.out.println("caseAvec2Candidats "+caseAvec2Candidats);
                System.out.println("position "+position);
            }
        } 
        int k = 0;
        while(k<caseAvec2Candidats.size() && candidatsCommuns.size()<=2){
            for(int j = k+1; j<caseAvec2Candidats.size(); j++){
                if(caseAvec2Candidats.get(k).getCandidats().equals(caseAvec2Candidats.get(j).getCandidats())){
                    candidatsCommuns = caseAvec2Candidats.get(j).getCandidats(); //liste des candidats qu'il faudra supprimer dans le bloc
                    System.out.println("k "+k);
                    System.out.println("j "+j);
                    System.out.println("case k "+caseAvec2Candidats.get(k));
                    System.out.println("case j "+caseAvec2Candidats.get(j));
                    System.out.println("position 1er case "+groupeEtudie.indexOf(caseAvec2Candidats.get(k)));
                    System.out.println("position 2eme case "+groupeEtudie.indexOf(caseAvec2Candidats.get(j)));
                    position2.add(position.get(k));
                    position2.add(position.get(j));
                    //position.add(groupeEtudie.indexOf(caseAvec2Candidats.get(k)));
                    //position.add(groupeEtudie.indexOf(caseAvec2Candidats.get(j))); 
                    System.out.println("c "+candidatsCommuns);
                    System.out.println("position2 "+position2);
                }
                
            } k++;
        }
        System.out.println("position2.get(0) "+position2.get(0));
        System.out.println("position2.get(1) "+position2.get(1));
        System.out.println("candidatsCommuns.get(0) "+candidatsCommuns.get(0));
        System.out.println("candidatsCommuns.get(1) "+candidatsCommuns.get(1));
        for (int i = 0; i<groupeEtudie.size(); i++){
            //System.out.println("i for "+i);
            if(i!=position2.get(0) && i!=position2.get(1)){ //on enlève les candidats pour toutes les autres cases
                //System.out.println("i if "+i);
                if(groupeEtudie.get(i).getCandidats().contains(candidatsCommuns.get(0)) || groupeEtudie.get(i).getCandidats().contains(candidatsCommuns.get(1))){
                    System.out.println("i if contains "+i);
                    System.out.println("groupeEtudie.get(i).getCandidats() avant remove "+groupeEtudie.get(i).getCandidats());
                    groupeEtudie.get(i).removeCandidat(candidatsCommuns.get(0));
                    groupeEtudie.get(i).removeCandidat(candidatsCommuns.get(1));
                    System.out.println("groupeEtudie.get(i).getCandidats() apres remove "+groupeEtudie.get(i).getCandidats());
                }
            }
        } System.out.println("groupe etudie "+groupeEtudie);
        
    }
    
}
