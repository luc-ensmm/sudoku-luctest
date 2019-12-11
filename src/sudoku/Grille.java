/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author smoukoka
 */

/*
JavaDoc à faire absolument !
*/
public class Grille {
    
    private ArrayList<Case> ensembleCases;
    private int taille;
    private boolean estInitialiser; // peut-être supprimable plus tard

    public Grille(int taille, ArrayList<Case> ensembleCases) {
       // il faut vérifier que le nombre d'objets dans les ArrayList et cohérent avec la taille 
       // précisé !
        this.taille = taille;
        this.ensembleCases = ensembleCases;
        this.estInitialiser = false;
        for (Case c: ensembleCases){
            if (c.getValeur() != 0){
                estInitialiser = true;
            }
        }
        
    }
    

    
    public Grille(int taille){
        this.taille = taille;
        ensembleCases = new ArrayList<>();
        int tailleAuCarre = taille*taille;
        for (int i = 0; i < tailleAuCarre*tailleAuCarre; i++){
            ensembleCases.add(new Case(taille,0,true));
        }
        this.estInitialiser = false;
        
    }
        
    public void randomInitialization(int nbCasesReveles){
        
        int tailleAuCarree = taille*taille;
        ArrayList<Integer> allCandidates = new ArrayList<>();
        for (int i = 0; i < tailleAuCarree; i++){
            allCandidates.add(i+1);
        }
        // il faut s'assurer que toutes les cases soient modifiables
        for (Case c: ensembleCases){
            c.estModifiable(true);
            c.addCandidat(allCandidates);
        }
        
        ArrayList<Integer> randomIndex = new ArrayList<>();
        int i = 0;
        int index;
        Random ran = new Random();
        while (i < nbCasesReveles){
            index = ran.nextInt(tailleAuCarree*tailleAuCarree);
            if (!randomIndex.contains(index)){
                randomIndex.add(index);
                i++;
            }
             
        }
        
        for (Integer j: randomIndex){
            
            Case c = ensembleCases.get(j);
            ArrayList<Integer> candidats = c.getCandidats();
            Collections.shuffle(candidats);
            int valeur_c = candidats.remove(0);
            c.setValeur(valeur_c);
            c.estModifiable(false);
            int ligne = j/tailleAuCarree;
            int colonne = j-ligne*tailleAuCarree;
            int bloc = (ligne/taille)*taille + colonne/taille;
            ArrayList<Case> cLine = this.getLine(ligne); 
            ArrayList<Case> cColumn = this.getColumn(colonne);
            ArrayList<Case> cBlock = this.getBlock(bloc);
            
            for (Case d : cLine){
                d.removeCandidat(valeur_c);
            }
            for (Case d : cColumn){
                d.removeCandidat(valeur_c);
            }
            for (Case d : cBlock){
                d.removeCandidat(valeur_c);
            
            }
            
            this.setLine(ligne, cLine);
            this.setColumn(colonne,cColumn);
            this.setBlock(bloc, cBlock); 
            ensembleCases.set(j, c);
            
            
        }
        
        
    }
    
    /* 
    La solution, si non fourni à la création de la grille, sera générée à partir des 
    algorithmes de résolution de sudoku.
    
    */

    public ArrayList<Case> getEnsembleCases() {
        return ensembleCases;
    }

    public void setEnsembleCases(ArrayList<Case> ensembleCases) {
        this.ensembleCases = ensembleCases;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }


    public ArrayList<Case> getLine(int i) {
        
        ArrayList<Case> line = new ArrayList<>();
        for(int k = i*taille*taille; k < i*taille*taille+taille*taille; k++) {
            line.add(ensembleCases.get(k));
        }
        
        return line;
    }
    
    
    public ArrayList<Case> getColumn(int j) {
        
        ArrayList<Case> column = new ArrayList<>();
        int tailleAuCarree = taille*taille; // introduit pour plus de clarté dans le code
        for(int k = j; k < j + tailleAuCarree*tailleAuCarree; k = k + tailleAuCarree) {
            column.add(ensembleCases.get(k));
        }
        
        return column;
    }
    
    
    public void setLine(int i, ArrayList<Case> line){ 
        int tailleAuCarre = taille*taille;
        if (line.size() != tailleAuCarre){
            System.out.println("La ligne mise en argument n'a pas le bon nombre d'élément:\nNombre d'élément de la ligne: " +
                    line.size() + "\nTaille au carrée de la grille: " + tailleAuCarre);
        }
        else {
            for(int k = 0; k < tailleAuCarre; k++) {
                ensembleCases.set(i*tailleAuCarre + k,line.get(k));
            }
        }
    }
    
    public void setColumn(int j, ArrayList<Case> column){ 
        int tailleAuCarree = taille*taille; // introduit pour plus de clarté dans le code
        if (column.size() != tailleAuCarree){
            System.out.println("La ligne mise en argument n'a pas le bon nombre d'élément:\nNombre d'élément de la ligne: " +
                    column.size() + "\nTaille au carrée de la grille: " + tailleAuCarree);
        }
        else {
             
            for(int k = 0; k < tailleAuCarree; k++) {
                ensembleCases.set(j + k*tailleAuCarree, column.get(k));
            }
        }
    }
    
    public ArrayList<Case> getBlock(int b){ //b = numero du block
    
        ArrayList<Case> block = new ArrayList<>();
        int tailleAuCarree = taille*taille; // introduit pour plus de clarté dans le code
        int depart = (b/taille)*(tailleAuCarree*taille) + taille*(b-(b/taille)*taille); //le premier terme donne la ligne et le deuxième terme donne la colonne
        for (int k = depart; k < depart + taille*tailleAuCarree; k+=tailleAuCarree){
            for (int m = k; m < k + taille; m++){
                block.add(ensembleCases.get(m));
            }
        }
        
      
        return block;
    }
    
    
    public void setBlock(int b, ArrayList<Case> block){ 
        int tailleAuCarree = taille*taille; // introduit pour plus de clarté dans le code
        if (block.size() != tailleAuCarree){
            System.out.println("La ligne mise en argument n'a pas le bon nombre d'élément:\nNombre d'élément de la ligne: " +
                    block.size() + "\nTaille au carrée de la grille: " + tailleAuCarree);
        }
        else {
            int depart = (b/taille)*(tailleAuCarree*taille) + taille*(b-(b/taille)*taille);
            int index_block = 0;
            for (int k = depart; k < depart + taille*tailleAuCarree; k+=tailleAuCarree){
                for (int m = k; m < k + taille; m++){
                    ensembleCases.set(m, block.get(index_block));
                    index_block++;
                }
            }
        }
    }
    
    public void showGrille(){
        for (int i = 0; i < (int) Math.pow(taille, 4); i++){
            if (i%(taille*taille) == 0) {
                System.out.println("");
            }
            System.out.print(" " + ensembleCases.get(i).getValeur() + " ");
            
        }
    }
    
  
    public void afficheGrille(){
        
        int tailleAuCarree = taille*taille;
        int t = taille*tailleAuCarree;
        
        for (int i = 0; i < taille; i++) {
            for (int k = i*t; k<(i+1)*t; k++){
                if ((k+1)%(taille*taille)==0){
                    System.out.println( this.ensembleCases.get(k).getValeur());
                }
                else {
                    System.out.print(this.ensembleCases.get(k).getValeur()+" |");
                }
            }
            System.out.println("-------------------------");
        }
    
    }
    
    public boolean pleine(){
        int i = 0;
        boolean estPleine = true;
        while (i < ensembleCases.size() && estPleine){
            if (ensembleCases.get(i).getValeur() == 0){
                estPleine = false;
            }
            else {
                i++;
            }
        }
        
        return estPleine;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grille other = (Grille) obj;
        if (this.taille != other.taille) {
            return false;
        }
        if (!Objects.equals(this.ensembleCases, other.ensembleCases)) {
            return false;
        }
        return true;
    }
    
    
    
    public void singletonCache(int indexCase){
        /*
        1) On récupère les candidats de la case
        2) Pour chaque candidat, on vérifie dans son voisinage(ligne,colonne,bloc) si le candidat
            est présent dans les candidats d'une autre case. Si durant la recherche on trouve le candidat alors on stoppe
            la recherche et on refait 2) pour le candidat suivant
        3) Si étape 2) passée pour le candidat(courant) alors la valeur de la case devient la valeur du candidat et l'algo se termine
        */
        int tailleAuCarree = taille*taille;
        
        int i = indexCase/tailleAuCarree;
        ArrayList<Case> ligne = this.getLine(i);
        int j = indexCase-i*tailleAuCarree;
        ArrayList<Case> colonne = this.getColumn(j);
        int b = (i/taille)*taille + j/taille;
        ArrayList<Case> bloc = this.getBlock(b);
        
        Case c = ensembleCases.get(indexCase);
        int valeur_c = 0; // par souci d'initialisation
        int compteurDuCandidat = 0;
        
        for (Integer candidat: c.getCandidats()){
            
            for (Case d : ligne){
                if(d.getCandidats().contains(candidat)){
                    compteurDuCandidat++;
                }
            }
            
            // si compteurDuCandidat > 1 alors il y a au moins 2 cases dans la ligne contenant le candidat
            // dans leur liste de candidat, donc on sort de la boucle, plus besoin de vérifier le reste du voisinage
            if (compteurDuCandidat > 1){
                break;
            }
            else{
                valeur_c = candidat;
                compteurDuCandidat = 0;
            }
            
            // même chose mais avec la colonne
            for (Case d: colonne){
                if(d.getCandidats().contains(candidat)){
                    compteurDuCandidat++;
                }
            }
            if (compteurDuCandidat > 1){
                break;
            }
            else{
                valeur_c = candidat;
                compteurDuCandidat = 0;
            }
            
            // même chose mais avec le bloc
            for (Case d: bloc){
                if(d.getCandidats().contains(candidat)){
                    compteurDuCandidat++;
                }
            }
            if (compteurDuCandidat > 1){
                break;
            }
            else{
                valeur_c = candidat;
                compteurDuCandidat = 0;
            }
            
        }
        
        if (compteurDuCandidat < 2){
            c.setValeur(valeur_c);
            ensembleCases.set(indexCase, c);
        }
        
        
    }
    
     public boolean candidatsPlausible(int lineCase, int columnCase){
        boolean candidatBon = true;
        int bloc = (lineCase/taille)*taille + columnCase/taille;
        ArrayList<Case> block = this.getBlock(bloc);
        ArrayList<Case> column = this.getColumn(columnCase);
        ArrayList<Case> line = this.getLine(lineCase);
        int position_case = taille*taille*lineCase + columnCase;
        for (int i = 0; i<column.size(); i++){
            if(!column.get(position_case).equals(column.get(i)) || !line.get(position_case).equals(line.get(i)) 
                    || block.get(position_case).equals(block.get(i))){
                candidatBon = false;
            }
        } return candidatBon;
    }
}
    
      

