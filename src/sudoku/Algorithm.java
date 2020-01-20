/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;
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
    
    // fonctionnelle mais à peaufiner s'il reste du temps
    public static Grille randomInitialization(int nbCasesReveles, int taille){
        
        int tailleAuCarree = taille * taille;
        
        try {
            if (nbCasesReveles > tailleAuCarree*tailleAuCarree){
                String errorMessage = "Le nombre de cases à révéler (" + nbCasesReveles 
                        + ") n'est pas compatibles avec la taille précisée (" + taille +
                        ")";
                throw new IllegalArgumentException(errorMessage);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
        
        Grille g = new Grille(taille);
        ArrayList<Integer> randomIndex = new ArrayList<>();
        int i = 0;
        int index;
        Random ran = new Random();
        while (i < nbCasesReveles) {
            index = ran.nextInt(tailleAuCarree * tailleAuCarree);
            if (!randomIndex.contains(index)) {
                randomIndex.add(index);
                i++;
            }

        }

        
        for (Integer j : randomIndex) {

             
            ArrayList<Integer> candidats = g.getCandidatCase(j);
            //System.out.println("Candidats de la case d'index " + j +": " + candidats);
            
            //System.out.println("\nCandidats de la case " + j + ": " + candidats);
            
            Collections.shuffle(candidats);
            
            int candidatAEnlever = candidats.get(0);
            g.setValeurCase(j,candidats.get(0));
            //System.out.println("Valeur de la case d'index " + j + ": " + candidatAEnlever);
            int ligne = j/tailleAuCarree;
            int colonne = j-ligne*tailleAuCarree;
            int bloc = (ligne/taille)*taille + colonne/taille;
            //ArrayList<Case> cLine = g.getLine(ligne);
            //ArrayList<Case> cColumn = g.getColumn(colonne);
            //ArrayList<Case> cBlock = g.getBlock(bloc);
            
            
            
            for(int k = ligne*tailleAuCarree; k < ligne*tailleAuCarree + tailleAuCarree; k++){
                if (g.getCandidatCase(k).contains(candidatAEnlever)){
                    g.removeCandidatCase(k, candidatAEnlever);
                }
            }
            
            
            
            for (int k = colonne; k < colonne + tailleAuCarree*tailleAuCarree; k+= tailleAuCarree) {
                if (g.getCandidatCase(k).contains(candidatAEnlever)){
                    g.removeCandidatCase(k, candidatAEnlever);
                }
            }
            
            
            int depart = (bloc/taille)*(tailleAuCarree*taille) + taille*(bloc-(bloc/taille)*taille);
            for (int k = depart; k < depart + taille * tailleAuCarree; k += tailleAuCarree) {
                for (int m = k; m < k + taille; m++) {
                    if (g.getCandidatCase(k).contains(candidatAEnlever)){
                    g.removeCandidatCase(k, candidatAEnlever);
                }
                }
            }
            

            //g.setLine(ligne, cLine);
            //g.setColumn(colonne, cColumn);
            //g.setBlock(bloc, cBlock);
            //g.setModifiableCase(j, false);
            

        }
        
        for (Integer j: randomIndex){
            g.setModifiableCase(j, false);
        }
        
        return g;
    
    }
  
    
        public static Grille randomInitialization2(int nbCasesReveles, int taille){
        
        int tailleAuCarree = taille * taille;
        
        try {
            if (nbCasesReveles > tailleAuCarree*tailleAuCarree){
                String errorMessage = "Le nombre de cases à révéler (" + nbCasesReveles 
                        + ") n'est pas compatibles avec la taille précisée (" + taille +
                        ")";
                throw new IllegalArgumentException(errorMessage);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        
        
        Grille g = new Grille(taille);
        ArrayList<Integer> randomIndex = new ArrayList<>();
        int i = 0;
        int index;
        Random ran = new Random();
        while (i < nbCasesReveles) {
            index = ran.nextInt(tailleAuCarree * tailleAuCarree);
            if (!randomIndex.contains(index)) {
                randomIndex.add(index);
                i++;
            }

        }

        
        ArrayList <Integer> randomIndex2 = new ArrayList<>();
        for (Integer j : randomIndex) {
                
                boolean singletonNuAEteAppliquer = false;
                for (int k = 0; k < tailleAuCarree * tailleAuCarree; k++) {
                    if (g.singletonNu(k)) {

                        singletonNuAEteAppliquer = true;
                        randomIndex2.add(k);

                        int ligne = k / tailleAuCarree;
                        int colonne = k - ligne * tailleAuCarree;
                        int bloc = (ligne / taille) * taille + colonne / taille;
                        ArrayList<Case> cLine = g.getLine(ligne);
                        ArrayList<Case> cColumn = g.getColumn(colonne);
                        ArrayList<Case> cBlock = g.getBlock(bloc);
                        int candidatAEnlever = g.getValeurCase(k);

                        for (Case c : cLine) {
                            c.removeCandidat(candidatAEnlever);
                        }

                        for (Case c : cColumn) {
                            c.removeCandidat(candidatAEnlever);
                        }

                        for (Case c : cBlock) {
                            c.removeCandidat(candidatAEnlever);
                        }

                        g.setLine(ligne, cLine);
                        g.setColumn(colonne, cColumn);
                        g.setBlock(bloc, cBlock);
                        break;
                    }
                
                }
                if (g.getValeurCase(j) == 0 && !singletonNuAEteAppliquer) {
                    ArrayList<Integer> candidats = g.getCandidatCase(j);

                    Collections.shuffle(candidats);

                    int candidatAEnlever = candidats.get(0);
                    g.setValeurCase(j, candidats.get(0));
                    //System.out.println("Valeur de la case d'index " + j + ": " + candidatAEnlever);
                    int ligne = j / tailleAuCarree;
                    int colonne = j - ligne * tailleAuCarree;
                    int bloc = (ligne / taille) * taille + colonne / taille;
                    ArrayList<Case> cLine = g.getLine(ligne);
                    ArrayList<Case> cColumn = g.getColumn(colonne);
                    ArrayList<Case> cBlock = g.getBlock(bloc);

                    for (Case c : cLine) {
                        c.removeCandidat(candidatAEnlever);
                    }

                    for (Case c : cColumn) {
                        c.removeCandidat(candidatAEnlever);
                    }

                    for (Case c : cBlock) {
                        c.removeCandidat(candidatAEnlever);
                    }

                    g.setLine(ligne, cLine);
                    g.setColumn(colonne, cColumn);
                    g.setBlock(bloc, cBlock);
                    randomIndex2.add(j);
                }
            }
        
        
        for (Integer j: randomIndex2){
            g.setModifiableCase(j, false);
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
                /*
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
                */
                
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
