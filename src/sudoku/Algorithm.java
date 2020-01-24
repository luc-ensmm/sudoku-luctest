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
  
        
    /**
     * Technique de résolution si dans un groupe 2 cases possèdent les 2 mêmes candidats
     * @param groupeEtudie 
     */
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
    
    public static void test_paireNue(){
        ArrayList<Case> testList = new ArrayList<>();
        int taille = 2;
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        
        ArrayList<Case> solution = new ArrayList<>();
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        
        Grille uneGrille = new Grille (taille, testList);
        uneGrille.afficheGrille();
    }
    
     
        /**
     * Résout la grille par force brute
     * @param g
     * @param indexDepart
     * @return une Grille pleine et correcte
     */
    public static Grille resolutionHasardeuse(Grille g,int indexDepart){
        
        // assure que toutes les cases aient l'ensemble des candidats pour s'assurer du
        // bon fonctionnement de la méthode 
        if (indexDepart == 0){
            ArrayList<Integer> allCandidates = new ArrayList<>();
            for (int i = 0; i < g.getTaille() * g.getTaille(); i++) {
                allCandidates.add(i + 1);
            }
            for (int i = 0; i < g.getEnsembleCases().size(); i++) {
                if (g.getCase(i).estModifiable()){
                    g.setCandidatCase(i, allCandidates);
                }
            }
        }
        
        if(!g.correcteEtPleine()){
            if (indexDepart < g.getEnsembleCases().size()){
                if (g.getCase(indexDepart).estModifiable()) {
                    Grille g1 = g.clone();
                    /*
                    La ligne ci-dessus est très importante car elle permet de sauvegarder l'état
                    de la grille avant modification et donc assure que l'on puisse backtrack
                    lors de la récursion
                     */
                    ArrayList<Integer> candidats = g1.getCandidatCase(indexDepart);
                    Collections.shuffle(candidats); // aléa
                    for(int candidat : candidats){
                        g1.setValeurCase(indexDepart, candidat);
                        g1 = resolutionHasardeuse(g1,indexDepart+1);
                     
                        if(g1.correcteEtPleine()){
                            g = g1; // on valide les modifications si g1 est bonne
                            break;
                        }
                    } 
                }
                else{
                    g = resolutionHasardeuse(g,indexDepart+1);   
                }
            }
        }
        
        return g;
        
    }
    
    /*
    Principe de fonctionnement de resolutionAlgoritmique 
    1) utiliser tous les algo(sauf singleton nu) sur toutes les cases modifiables de la grille
    pour simplifier les candidats de chaque case
    2) appliquer le singleton nu sur toute les cases modifiables
    3) si le singleton nu a réussi sur au moins 1 case, on boucle les étapes 1 et 2 en 
    enclenchant une récursion.
    Si la grille est totalement rempli (aucune valeur non nulle), on vérifie que la grille
    est correcte. Si la grille n'est pas correcte, on revient à l'étape de récursion précédente
    et au lieu d'appliquer le singleton nu, on choisit une case parmi celle qui contiennent le
    moins de candidat(au minimum 2 candidats donc singleton nu exclu) et on choisit la valeur
    de cette case de façon aléatoire parmi ces candidats puis on boucle par récursion
    4) Une fois la grille complète est correcte, la retourner (sans appel récursif)
    
    */
    
    /**
     * ATTENTION: NON TERMINER !!!
     * Résout la grille en utilisant des algorithmes de résolution simplifiant les candidats
     * Permet de générer une solution plus rapidement que la résolution
     * hasardeuse
     * @param g
     * @return une Grille dont toutes les valeurs sont non nulles et correctes
     */ 
    public static Grille resolutionAlgorithmique(Grille g){
        
        //Application du singleton caché à toutes les cases de la grille
        
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            g.singletonCache(i);
        }
        
        /*
        // Application de paire nue à toutes les lignes
        
        for(int i = 0; i < g.getTaille()*g.getTaille(); i++){
            ArrayList<Case> line = g.getLine(i);
            Algorithm.paireNue(line);
            g.setLine(i, line);
        }
        
        // Application de paire nue à toutes les colonnes
        
        for(int i = 0; i < g.getTaille()*g.getTaille(); i++){
            ArrayList<Case> colonne = g.getColumn(i);
            Algorithm.paireNue(colonne);
            g.setColumn(i, colonne);
        }
        
        // Application de paire nue à tous les blocs
        
        for(int i = 0; i < g.getTaille()*g.getTaille(); i++){
            ArrayList<Case> bloc = g.getBlock(i);
            Algorithm.paireNue(bloc);
            g.setBlock(i, bloc);
        }
        */
        
        
        // Singleton nu 
        int utilisationSingletonNu = 0;
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            if (g.singletonNu(i)){
                utilisationSingletonNu++;
            }
        }
        
        /*
        Inutile de faire le reste si la grille est déjà correctement rempli.
        On peut directement la renvoyer
        */
        System.out.println("Utilisation du singleton nu = " + utilisationSingletonNu);
        if(!g.correcteEtPleine()){
            
            if(utilisationSingletonNu == 0){
                /*
                Si les candidats de chaque case ne peuvent pas être réduit
                à 1 seul candidat par case (qui devient alors la valeur de
                la case par l'application du singleton nu) alors on choisis
                la première case que l'on trouve parmi celle ayant le moins
                de candidats et la valeur de cette case devient l'un de ses
                candidats (autrement dit on fait 1 test) et on recommence
                la méthode sur toute la grille par récursion
                
                */
                int index = 0;
                int temp = 0;
                while (temp < g.getEnsembleCases().size()){
                    if (g.getEnsembleCases().get(temp).getCandidats().size() <
                           g.getEnsembleCases().get(index).getCandidats().size()){
                        index = temp;
                    }
                    if (g.getEnsembleCases().get(index).getCandidats().size() == 2){
                            temp+=g.getEnsembleCases().size();
                        }
                    else{
                        temp++;
                    }
                    
                }
                
                
                /*
                Création d'une copie de g pour sauvegarder l'état de g en cas
                de "remonter de branche" lors d'une récursion. C'est en effet 
                nécessaire ici car on teste chaque valeur possible de la case c
                (parmi ses candidats) sans garanti que la modification appliqué
                est la bonne, contrairement à l'utilisation des algorithmes 
                ci-dessus qui dans le pire des cas ne faussent pas le remplissage
                de la grille (sous réserve que ces algorithmes soient fonctionnels)
                */
                Grille g1 = new Grille(g.getTaille(),g.getEnsembleCases());
                for (int candidat: g1.getCandidatCase(index)){
                    g1.setValeurCase(index,candidat);
                    g1.removeCandidatCase(index,candidat);
                    // RECURSION
                    g1 = resolutionAlgorithmique(g1);
                    if (g1.correcteEtPleine()){
                        g = g1;
                        break;
                        /*
                        Si g1 n'est pas correctement remplie alors on retourne
                        à l'étape précédente (on "remonte la branche de la récursion")
                        */
                    }
                }
               
            }
        }
        
        return g;
    
    }
    
    
    
}
