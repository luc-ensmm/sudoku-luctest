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
import java.lang.IllegalArgumentException;
import java.util.List;
/**
 *
 * @author smoukoka
 */

/**
 * Classe représentant la grille contenant les cases du sudoku
 * @author yannE
 */
public class Grille {
    
    private ArrayList<Case> ensembleCases;
    private int taille;
    private List<GrilleListener> listeners = new ArrayList<>();
    
    /**
     * Constructeur général
     * @param taille
     * @param ensembleCases ArrayList contenant les cases de la grille
     */
    public Grille(int taille, ArrayList<Case> ensembleCases) {
        
        // il faut vérifier que le nombre d'objets dans les ArrayList et cohérent avec la taille précisée !
        try {
            if (taille*taille*taille*taille != ensembleCases.size()) {
                throw new IllegalArgumentException("La taille précisée(" + taille 
                        + ") est incompatible avec la taille de l'ArrayList("
                        + ensembleCases.size() + ")");
            } else {
                this.taille = taille;
                this.ensembleCases = ensembleCases;

            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }
    
    /**
     * Constructeur qui génère une grille "vide" (valeur des cases = 0)
     * à la taille spécifiée
     * @param taille 
     */
    public Grille(int taille){
        
        this.taille = taille;
        ensembleCases = new ArrayList<>();
        int tailleAuCarre = taille*taille;
        ArrayList<Integer> allCandidates = new ArrayList<>();
        
        for (int i = 1; i <=tailleAuCarre; i++){
            allCandidates.add(i);
        }
        
        for (int i = 0; i < tailleAuCarre*tailleAuCarre; i++){
            ensembleCases.add(new Case(taille,0,(ArrayList<Integer>)allCandidates.clone(),true));
        }
        
    }
    
    public void addListener(GrilleListener listener){
        listeners.add(listener);
    }
    
    
    /**
     * Applique la technique du singleton nu à la case d'indice précisé
     * @param indexCase Index de la case sur laquelle on applique la méthode
     * @return Un boolean qui indique si la case n'a plus qu'un seul candidat après application de la technique
     */
    public boolean singletonNu (int indexCase){
       
       boolean aEteAppliquer = false;
       Case c = ensembleCases.get(indexCase);
       if (c.estModifiable()){
            if (c.getCandidats().size() == 1){
                int valeur_c = c.getCandidats().get(0);
                c.setValeur(valeur_c);
                c.removeCandidat(valeur_c);
                ensembleCases.set(indexCase, c);
                aEteAppliquer = true;
            }
       }
     
       return aEteAppliquer;
       
    }
    
    public ArrayList<Case> getEnsembleCases() {
        return (ArrayList<Case>)ensembleCases.clone();
    }

    public void setEnsembleCases(ArrayList<Case> ensembleCases) {
        this.ensembleCases = ensembleCases;
    }

    public int getTaille() {
        return taille;
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
    
    /**
     * Remplace la ligne i par celle en argument
     * Attention, modifie aussi les cases dites non modifiables
     * @param i
     * @param line 
     */
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
    
    /**
     * 
     * @param b Numéro du bloc  
     * @return 
     */
    public ArrayList<Case> getBlock(int b){ 
    
        ArrayList<Case> block = new ArrayList<>();
        int tailleAuCarree = taille*taille; // introduit pour plus de clarté dans le code
        int depart = (b/taille)*(tailleAuCarree*taille) + taille*(b-(b/taille)*taille); 
        for (int k = depart; k < depart + taille*tailleAuCarree; k+=tailleAuCarree){
            for (int m = k; m < k + taille; m++){
                block.add(ensembleCases.get(m));
            }
        }
        
      
        return block;
    }

    public void setBlock(int b, ArrayList<Case> block){ 
        int tailleAuCarree = taille*taille; 
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
    
    /**
     * Affiche la grille (valeurs des cases) dans la console 
     * sans afficher les lignes formant les cases
     */
    public void showGrille(){
        for (int i = 0; i < (int) Math.pow(taille, 4); i++){
            if (i%(taille*taille) == 0) {
                System.out.println("");
            }
            System.out.print(" " + ensembleCases.get(i).getValeur() + " ");
            
        }
    }
    

    /**
     * Affiche la grille (valeurs des cases) dans la console
     * avec les lignes formant les cases
     */
    public void afficheGrille(){
        
        int tailleAuCarree = taille*taille;
        int t = taille*tailleAuCarree;
        for (int j = 0; j<tailleAuCarree+1; j++){
            System.out.print("__");
        }
        System.out.print("\n");
        for (int i = 0; i < taille; i++) {
            for (int k = i*t; k<(i+1)*t; k++){
                if ((k+1)%(taille*taille)==0){
                    System.out.println( this.ensembleCases.get(k).getValeur());
                } else if ((k+1)%taille == 0){
                    System.out.print(this.ensembleCases.get(k).getValeur()+" ||");
                }
                else {
                    System.out.print(this.ensembleCases.get(k).getValeur()+" ");
                }
            } for (int j = 0; j<tailleAuCarree+1; j++){
                System.out.print("__");
              }
              System.out.print("\n");
        }
    
    }
    
    /**
     * Indique si la grille est remplie càd que les valeurs de toutes
     * les cases sont non nulles.
     * @return 
     */
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
    /**
     * Egalité basée sur les attributs taille et ensembleCases
     * sachant que l'égalité entre cases se fait uniquement 
     * par rapport à la valeur des cases
     */
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
    
    
    /**
     * Applique la technique du singleton caché à une case
     * @param indexCase 
     */
    public void singletonCache(int indexCase){
        /*
        1) On récupère les candidats de la case
        2) Pour chaque candidat, on vérifie dans son voisinage(ligne,colonne,bloc) si le candidat
            est présent dans les candidats d'une autre case. Si durant la recherche on trouve le candidat alors on stoppe
            la recherche et on refait 2) pour le candidat suivant
        3) Si étape 2) passée pour le candidat(courant) alors la valeur de la case devient la valeur du candidat et l'algo se termine
        */
        
        try {

            if (ensembleCases.get(indexCase).estModifiable()) {
                int tailleAuCarree = taille * taille;

                int i = indexCase / tailleAuCarree;
                ArrayList<Case> ligne = this.getLine(i);
                int j = indexCase - i * tailleAuCarree;
                ArrayList<Case> colonne = this.getColumn(j);
                int b = (i / taille) * taille + j / taille;
                ArrayList<Case> bloc = this.getBlock(b);
                //System.out.println("\nCandidats de la case: " + ensembleCases.get(indexCase).getCandidats());
                for (Integer candidat : ensembleCases.get(indexCase).getCandidats()) {
                    //System.out.println("Candidat courant: " + candidat);
                    if (singletonCacheOneCandidate(indexCase, candidat, ligne, colonne, bloc)) {
                        break;
                    }
                }

            }
            else{
                throw new IllegalArgumentException("La case d'index" + indexCase +
                        "est non modifiable");
            }
        
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Applique la technique du singleton caché à une case pour un candidat
     * Cette méthode est exclusivement utilisé dans la méthode singletonCache
     * @param indexCase
     * @param candidat
     * @param ligne
     *        La ligne doit contenir la case précisée par indexCase pour 
     *        le bon fonctionnement de la méthode
     * @param colonne
     *        La colonne doit contenir la case précisée par indexCase pour 
     *        le bon fonctionnement de la méthode
     * @param bloc
     *        La bloc doit contenir la case précisée par indexCase pour 
     *        le bon fonctionnement de la méthode
     * 
     * @return 
     */
    private boolean singletonCacheOneCandidate(int indexCase, int candidat,
        ArrayList<Case> ligne, ArrayList<Case> colonne, ArrayList<Case> bloc){
        /*
        
        1) On récupère les candidats de la case
        2) Pour chaque candidat, on vérifie dans son voisinage(ligne,colonne,bloc) si le candidat
            est présent dans les candidats d'une autre case. Si durant la recherche on trouve le candidat alors on stoppe
            la recherche et on refait 2) pour le candidat suivant
        3) Si étape 2) passée pour le candidat(courant) alors la valeur de la case devient la valeur du candidat et l'algo se termine
        */
        
        int valeur_c = 0; // par souci d'initialisation
        int compteurDuCandidat = 0;
        boolean finDeLalgo = false;
           
        for (Case d : ligne){
            if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                compteurDuCandidat++;
            }
        }
   
        // Si compteurDuCandidat > 1 alors il y a au moins 2 cases dans la ligne contenant le candidat
        // dans leur liste de candidat, donc on passe au candidat suivant.
        //Plus besoin de vérifier le reste du voisinage si compteurDuCandidat  = 1 cependant
        if (compteurDuCandidat == 1){
            valeur_c = candidat;
            finDeLalgo = true;  
        }
        else{
            compteurDuCandidat = 0;
        }

        // même chose mais avec la colonne
        if (!finDeLalgo){
            for (Case d: colonne){
                if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                    compteurDuCandidat++;
                }
            }
            
            if (compteurDuCandidat == 1){
            valeur_c = candidat;
            finDeLalgo = true;
            
            }
            else{
                compteurDuCandidat = 0;
            }
        
        }

        // même chose mais avec le bloc
        if (!finDeLalgo){
            for (Case d: bloc){
                if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                    compteurDuCandidat++;
                }
            }
            if (compteurDuCandidat == 1){
            valeur_c = candidat;
            }
        }

        if (compteurDuCandidat == 1){
            Case c = ensembleCases.get(indexCase);
            c.setValeur(valeur_c);
            ensembleCases.set(indexCase, c);
        }
        return finDeLalgo;
        
    }
    
    private boolean singletonCacheOneCandidateVerbose(int indexCase, int candidat,
        ArrayList<Case> ligne, ArrayList<Case> colonne, ArrayList<Case> bloc){
        /*
        
        1) On récupère les candidats de la case
        2) Pour chaque candidat, on vérifie dans son voisinage(ligne,colonne,bloc) si le candidat
            est présent dans les candidats d'une autre case. Si durant la recherche on trouve le candidat alors on stoppe
            la recherche et on refait 2) pour le candidat suivant
        3) Si étape 2) passée pour le candidat(courant) alors la valeur de la case devient la valeur du candidat et l'algo se termine
        */
        
        int valeur_c = 0; // par souci d'initialisation
        int compteurDuCandidat = 0;
        boolean finDeLalgo = false;
        
        System.out.println("Ligne:");    
        for (Case d : ligne){
            System.out.println("Valeur:" + d.getValeur() + "; Candidats:" + d.getCandidats());
            if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                compteurDuCandidat++;
            }
        }
        System.out.print("\n");
        // Si compteurDuCandidat > 1 alors il y a au moins 2 cases dans la ligne contenant le candidat
        // dans leur liste de candidat, donc on passe au candidat suivant.
        //Plus besoin de vérifier le reste du voisinage si compteurDuCandidat  = 1 cependant
        if (compteurDuCandidat == 1){
            valeur_c = candidat;
            finDeLalgo = true;
            System.out.println("Le candidat " + candidat + " va devenir de la valeur de la case (l)");
            
        }
        else{
            System.out.println("Ligne checked, pas fin de l'algo. Compteur = " + compteurDuCandidat);
            compteurDuCandidat = 0;
        }

        // même chose mais avec la colonne
        if (!finDeLalgo){
            System.out.println("Colonne:");
            for (Case d: colonne){
                System.out.println("Valeur:" + d.getValeur() + "; Candidats:" + d.getCandidats());
                if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                    compteurDuCandidat++;
                }
            }
            System.out.print("\n");
            if (compteurDuCandidat == 1){
            valeur_c = candidat;
            finDeLalgo = true;
            System.out.println("Le candidat " + candidat + " va devenir de la valeur de la case (c)");
            }
            else{
                System.out.println("Colonne checked, pas fin de l'algo. Compteur = " + compteurDuCandidat);
                compteurDuCandidat = 0;
            }
        
        }

        // même chose mais avec le bloc
        if (!finDeLalgo){
            System.out.println("Bloc:");
            for (Case d: bloc){
                System.out.println("Valeur:" + d.getValeur() + "; Candidats:" + d.getCandidats());
                if((d.getCandidats().contains(candidat) && d.getValeur() == 0)
                        || d.getValeur() == candidat){
                    compteurDuCandidat++;
                }
            }
            System.out.print("\n");
            if (compteurDuCandidat == 1){
            valeur_c = candidat;
            System.out.println("Le candidat " + candidat + " va devenir de la valeur de la case (b)");
            }
            else {
                System.out.println("Block checked. Compteur = " + compteurDuCandidat);
            }
        
        }

        
        
        if (compteurDuCandidat == 1){
            Case c = ensembleCases.get(indexCase);
            System.out.println("Modification de la valeur de la case. Valeur de la case " + 
                    indexCase + " avant la modif: " + ensembleCases.get(indexCase).getValeur());
            c.setValeur(valeur_c);
            ensembleCases.set(indexCase, c);
            System.out.println("Valeur de la case " + indexCase + " màj :" + 
                    ensembleCases.get(indexCase).getValeur());
        }
        
        return finDeLalgo;
        
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
     
   
    
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
    
    */
    public void setCase(int index, Case c){
        ensembleCases.set(index, c);
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
    
    */
    public Case getCase(int index){
        return ensembleCases.get(index);
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
   
    */
    public void setValeurCase(int index, int valeur){
        Case c = ensembleCases.get(index);
        c.setValeur(valeur);
        ensembleCases.set(index,c);
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
    
    */
    public int getValeurCase(int index){
        return ensembleCases.get(index).getValeur();
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
   
    */
    public void setCandidatCase(int index, ArrayList<Integer> candidats){
        
        Case c = ensembleCases.get(index);
        ArrayList<Integer> candidatsAvant = c.getCandidats();
        for(int cand: candidatsAvant){
            if(!candidats.contains(cand)){
                c.removeCandidat(cand);
            }
        }
        c.addCandidat(candidats);
        ensembleCases.set(index, c);
       
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
   
    */
    public void removeCandidatCase(int index, int candidat){
        Case c = ensembleCases.get(index);
        c.removeCandidat(candidat);
        ensembleCases.set(index, c);
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
    
    */
    
    /**
     * Rajoute 1 candidat aux candidats de la case index
     * Pour rajouter plusieurs candidats simultanement, utiliser
     * la méthode @see Grille#setCandidatCase(int,ArrayList)
     * @param index
     * @param candidat 
     */
    public void addCandidatCase(int index, int candidat){
        Case c = ensembleCases.get(index);
        c.addCandidat(candidat);
        ensembleCases.set(index, c);
    }
    
    /* Introduit pour alléger l'écriture dans certaines méthodes.
   .
    */
    public ArrayList<Integer> getCandidatCase(int index){
        return ensembleCases.get(index).getCandidats();
    }
    
    public void setModifiableCase(int index, boolean estModifiable){
        Case c = ensembleCases.get(index);
        c.estModifiable(estModifiable);
        ensembleCases.set(index, c);
    }
    

    
   
    public boolean correcteEtPleine() { // fonctionne
        boolean estCorrecte = true;
        if (this.pleine()) {
            ArrayList<Case> unit = new ArrayList<>();
            for (int i = 1; i < taille * taille + 1; i++) {
                unit.add(new Case(taille, i, true));
            }

            int index = 0;
            
            while (estCorrecte && index < taille*taille) {
                if (!this.getLine(index).containsAll(unit) || !this.getColumn(index).containsAll(unit)) {
                    estCorrecte = false;
                }
                index++;
            }
        }
        else{
            estCorrecte = false;
        }
        
        if(estCorrecte){
            for(GrilleListener listener: listeners){
                listener.laGrilleEstRemplieEtCorrecte();
            }
        }
        
        return estCorrecte;
    }
    
    public boolean valeursPlausible(int lineCase, int columnCase){
        
        boolean valeurBonne = this.valeurPlausibleColonne(lineCase, columnCase);
        if(this.valeurPlausibleColonne(lineCase, columnCase) == true){
            valeurBonne = this.valeurPlausibleColonne(lineCase, columnCase);
            if (this.valeurPlausibleLigne(lineCase, columnCase) == true){
                valeurBonne = this.valeurPlausibleLigne(lineCase, columnCase);
                if(this.valeurPlausibleBloc(lineCase, columnCase) == true){
                    valeurBonne = this.valeurPlausibleBloc(lineCase, columnCase);
                }
            }
        }
         return valeurBonne;
    }
     
    public boolean valeurPlausibleColonne (int lineCase, int columnCase){
        boolean valeurBonneDansColonne = true;
        ArrayList<Case> column = this.getColumn(columnCase);
        int i = 0;
        while(i<column.size() && valeurBonneDansColonne == true){
            if(i!=(lineCase)){
                valeurBonneDansColonne = !column.get(lineCase).equals(column.get(i));
                //System.out.println(i);
            } i++;
        }System.out.println("valeurBonneDansColonne "+valeurBonneDansColonne);
        return valeurBonneDansColonne;
    }
    
    public boolean valeurPlausibleLigne(int lineCase, int columnCase){
        boolean valeurBonneDansLigne = true;
        ArrayList<Case> line = this.getLine(lineCase);
        int i = 0;
        while(i<line.size() && valeurBonneDansLigne == true){
            if(i!=(columnCase)){
                valeurBonneDansLigne = !line.get(columnCase).equals(line.get(i));
                System.out.println(i);
            }i++;
        }System.out.println("valeurBonneDansLigne "+valeurBonneDansLigne);
        return valeurBonneDansLigne;
    }
    
    public boolean valeurPlausibleBloc(int lineCase, int columnCase){
        boolean valeurBonneDansBloc = true;
        int bloc = ((lineCase)/taille)*taille + (columnCase)/taille;
        System.out.println("bloc "+bloc);
        ArrayList<Case> block = this.getBlock(bloc);
        int lignePremiereCaseBloc = ((lineCase)/taille)*taille;
        System.out.println("lignePremiereCaseBloc "+lignePremiereCaseBloc);
        
        int colonnePremiereCaseBloc = ((columnCase)/taille)*taille;
        System.out.println("colonnePremiereCaseBloc "+colonnePremiereCaseBloc);
        
        int positionCaseDansBloc = ((lineCase)-lignePremiereCaseBloc)*taille + (columnCase)-colonnePremiereCaseBloc;
        System.out.println("positionCaseDansBloc "+positionCaseDansBloc);
        int i = 0;
        while(i<block.size() && valeurBonneDansBloc == true){
            if(i!=positionCaseDansBloc){
                valeurBonneDansBloc = !block.get(positionCaseDansBloc).equals(block.get(i));
                System.out.println(i);
            }
            i++;
        }System.out.println("valeurBonneDansBloc "+valeurBonneDansBloc);
        return valeurBonneDansBloc;
    }
    
    public ArrayList<Integer> candidatsEnTropColonne (int lineCase, int columnCase){
        ArrayList<Integer> candidatsEnTrop = new ArrayList<Integer>();
        ArrayList<Case> column = this.getColumn(columnCase);
        int i = 0;
            while(i<column.size()){
                if(i!=(lineCase) && column.get(lineCase).candidatDejaPresent(column.get(i)) != 0 ){
                    candidatsEnTrop.add(column.get(lineCase).candidatDejaPresent(column.get(i)));
                } i++;
            }
            
        return candidatsEnTrop;
    }

    public ArrayList<Integer> candidatsEnTropLigne (int lineCase, int columnCase){
        ArrayList<Integer> candidatsEnTrop = new ArrayList<Integer>();
        int position_case = taille*taille*lineCase + columnCase;
        boolean valeurBonneDansLigne = true;
        ArrayList<Case> line = this.getLine(lineCase);
        int i = 0;
        while(i<line.size()){
            if(i!=(columnCase) && line.get(columnCase).candidatDejaPresent(line.get(i)) != 0){
                candidatsEnTrop.add(line.get(columnCase).candidatDejaPresent(line.get(i)));
            }i++;
        }//System.out.println("candidats en trop ligne "+candidatsEnTrop);
        return candidatsEnTrop;
    }
    
    public ArrayList<Integer> candidatsEnTropBloc (int lineCase, int columnCase){
        ArrayList<Integer> candidatsEnTrop = new ArrayList<Integer>();
        int bloc = ((lineCase)/taille)*taille + (columnCase)/taille;
        //System.out.println("bloc "+bloc);
        ArrayList<Case> block = this.getBlock(bloc);
        int lignePremiereCaseBloc = ((lineCase)/taille)*taille;
        //System.out.println("lignePremiereCaseBloc "+lignePremiereCaseBloc);
        
        int colonnePremiereCaseBloc = ((columnCase)/taille)*taille;
        //System.out.println("colonnePremiereCaseBloc "+colonnePremiereCaseBloc);
        
        int positionCaseDansBloc = ((lineCase)-lignePremiereCaseBloc)*taille + (columnCase)-colonnePremiereCaseBloc;
        //System.out.println("positionCaseDansBloc "+positionCaseDansBloc);
        int i = 0;
        while(i<block.size()){
            if(i!=positionCaseDansBloc && block.get(positionCaseDansBloc).candidatDejaPresent(block.get(i)) != 0){
                candidatsEnTrop.add(block.get(positionCaseDansBloc).candidatDejaPresent(block.get(i)));  
            }
            i++;
        }//System.out.println("candidats en trop bloc "+candidatsEnTrop);
        return candidatsEnTrop;
    }
    /**
     * indique parmi les candidats d'une case lesquels ne sont pas possibles
     * car d'autres cases non modifiables contiennent déjà ces valeurs 
     * soit sur la ligne, soit sur la colonne, soit dans le bloc (généralisation
     * de la méthode candidatDejaPresent de la clase Case
     * @param lineCase
     * @param columnCase
     * @return 
     */
    public ArrayList<Integer> candidatsEnTrop(int lineCase, int columnCase){
        ArrayList<Integer> candidatsAEnlever1 = this.candidatsEnTropColonne(lineCase, columnCase);
        ArrayList<Integer> candidatsAEnlever2 = this.candidatsEnTropLigne(lineCase, columnCase);
        ArrayList<Integer> candidatsAEnlever3 = this.candidatsEnTropBloc(lineCase, columnCase);
        for (int i = 0; i<candidatsAEnlever2.size(); i++){
            if(!candidatsAEnlever1.contains(candidatsAEnlever2.get(i))){
                candidatsAEnlever1.add(candidatsAEnlever2.get(i));
            }
        }
        for (int j = 0; j<candidatsAEnlever3.size(); j++){
            if(!candidatsAEnlever1.contains(candidatsAEnlever3.get(j))){
                candidatsAEnlever1.add(candidatsAEnlever3.get(j));
            }
        }System.out.println("candidats a enlever (candidats non valide) "+candidatsAEnlever1);
        return candidatsAEnlever1;
    }
    
    /**
     * Retourne un clone parfait de this, complètement indépendant de this
     * @return 
     */
    public Grille clone(){
        
        ArrayList<Case> cloneEnsembleCases = new ArrayList<>();
        for (Case c: ensembleCases){
            cloneEnsembleCases.add(c.clone());
        }
        return new Grille(taille, cloneEnsembleCases);
    }
    public static String niveauGrille(int nbCasesRevelees, int taille){
        int tailleAuCarre = taille*taille;
        double totalCases = tailleAuCarre*tailleAuCarre;
        double proportionCasesRevelees = (double)nbCasesRevelees/totalCases;
        String niveau = "";
        if (taille == 2){
            niveau = "facile";
        } else{
            if(proportionCasesRevelees>0.20 && proportionCasesRevelees<=0.28){
                niveau = "difficile";
            } else if (proportionCasesRevelees>28 && proportionCasesRevelees<=0.36){
                niveau = "moyen";
            } else if (proportionCasesRevelees>0.36){
                niveau = "facile";
            }
        } return niveau; 
             
    }
    
    public static  int niveauGrille2(String niveauDifficulte, int taille){ //a supprimer
        int nbCasesRevelees;
        niveauDifficulte.toLowerCase();
        int taillePuissance4 = taille*taille*taille*taille;
        double nbMinimum = (17.0/81.0)*taillePuissance4;
        System.out.println("nb minimum "+nbMinimum);
        Math.floor(nbMinimum);
        System.out.println("math floor "+nbMinimum);
        double facile = (34.0/81.0)*taillePuissance4;
        System.out.println("facile "+facile);
        Math.floor(facile);
         System.out.println("math floor facile "+facile);
        double moyen = (30.0/81.0)*taillePuissance4;
        System.out.println("moyen "+moyen);
        Math.floor(moyen);
        System.out.println("math floor moyen "+moyen);
        double difficile = (28.0/81.0)*taillePuissance4;
        System.out.println("difficile "+difficile);
        Math.floor(difficile);
         System.out.println("math floor difficile "+difficile);
        if(niveauDifficulte == "facile"){
            Random ran1 = new Random();
            nbCasesRevelees = ran1.nextInt((int)facile-(int)nbMinimum+1);
            nbCasesRevelees +=(int)nbMinimum;
            System.out.println("nbCasesRevelees "+nbCasesRevelees);
        } else if (niveauDifficulte == "moyen"){
            Random ran1 = new Random();
            nbCasesRevelees = ran1.nextInt((int)moyen-(int)nbMinimum+1);
            nbCasesRevelees +=(int)nbMinimum;
            System.out.println("nbCasesRevelees "+nbCasesRevelees);
        } else {
            Random ran1 = new Random();
            nbCasesRevelees = ran1.nextInt((int)difficile-(int)nbMinimum+1);
            nbCasesRevelees +=(int)nbMinimum;
            System.out.println("nbCasesRevelees "+nbCasesRevelees);
        } return nbCasesRevelees;
    }
    
    public void videLesCandidats() {

        for (int i = 0; i < this.ensembleCases.size(); i++) {
            if (!this.getCase(i).getCandidats().isEmpty()) {
                this.getCase(i).getCandidats().clear();
            }
        }
    }
    
    public void candidatBloque(int block){
        int tailleAuCarree = taille*taille;
        ArrayList<Case> blocCourant = this.getBlock(block);
        ArrayList<Case> ligneCourante = this.getLine((block/taille)*taille);
        System.out.println("numero de la ligne "+(block/taille)*taille);
        //System.out.println("numero de la colonne "+taille*(block-(block/taille)*taille));
        //int departCase = (block/taille)*(tailleAuCarree*taille) + taille*(block-(block/taille)*taille);
        //System.out.println("numero de la case "+departCase);
        //ArrayList<Integer> candidatsCommuns; 
        ArrayList<Integer> candidatsEtudies = new ArrayList<Integer>();
        Case caseCourante;
        int depart = 0;
        //for (int i = 0; i<taille; i++){
            for (int j = 0; j<taille; j++){
                System.out.println("j "+j);
                caseCourante = blocCourant.get(depart);
                System.out.println("caseCourante "+caseCourante);
                //ligneCourante.remove(caseCourante);
                //System.out.println("bloc avant "+blocCourant);
                blocCourant.remove(caseCourante);
                //System.out.println("bloc apres "+blocCourant);
                //System.out.println("ligneCourante avant "+ligneCourante);
                ligneCourante.remove(caseCourante);
                //System.out.println("ligneCourante apres "+ligneCourante);
                for(int k = 0; k<caseCourante.getCandidats().size(); k++){
                    if (caseCourante.estModifiable() == true && !candidatsEtudies.contains(caseCourante.getCandidats().get(k))){
                        candidatsEtudies.add(caseCourante.getCandidats().get(k)); //on récupère les candidats de la ligne du bloc
                    }
                }  
            } System.out.println("candidatsEtudies "+candidatsEtudies);
              System.out.println("blocCourant.size() "+blocCourant.size());
            
            
            for (int m = 0; m<blocCourant.size(); m++){
                int n = 0;
                while(n<candidatsEtudies.size()){
                        if(blocCourant.get(m).estModifiable() == true && blocCourant.get(m).getCandidats().contains(candidatsEtudies.get(n))){
                            System.out.println("candidatsEtudies.get(n) "+candidatsEtudies.get(n));
                            System.out.println("blocCourant.get(m).getCandidats() "+blocCourant.get(m).getCandidats());
                            //candidatsEtudies.remove(candidatsEtudies.get(n));
                            //estDedans = true;
                        } else {
                            n++;
                        }
                  System.out.println("n "+n); 
                } 
            } System.out.println("candidatsEtudies - candidats en trop "+candidatsEtudies);
            /*for(int n = 0; n<candidatsEtudies.size(); n++){
                for(int b = 0; b<ligneCourante.size(); b++){
                    if(ligneCourante.get(b).estModifiable() == true && ligneCourante.get(b).getCandidats().contains(candidatsEtudies.get(n))){
                        ligneCourante.get(b).getCandidats().remove(candidatsEtudies.get(n));
                        System.out.println("ligneCourante candidats apres remove "+ligneCourante.get(b).getCandidats());
                    }
                }
            }
            depart+=taille;*/    
        //}
    }
    
   
    
    
    
    
}
    
      

