/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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

        int compteur = 0;
        for (Integer j : randomIndex) {

             
            ArrayList<Integer> candidats = g.getCandidatCase(j);
            //System.out.println("Candidats de la case d'index " + j +": " + candidats);
            
            System.out.println("\nCandidats de la case " + j + ": " + candidats);
            
            Collections.shuffle(candidats);
            
            int candidatAEnlever = candidats.get(0);
            g.setValeurCase(j,candidats.get(0));
            System.out.println("Valeur de la case d'index " + j + ": " + candidatAEnlever);
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
            g.setModifiableCase(j, false);
            

        }

        return g;
    
    }
    
    
    
}
