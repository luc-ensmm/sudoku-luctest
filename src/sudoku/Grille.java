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
        try {
            if (taille*taille*taille*taille != ensembleCases.size()) {
                throw new IllegalArgumentException("La taille précisée(" + taille 
                        + ") est incompatible avec la taille de l'ArrayList("
                        + ensembleCases.size() + ")");
            } else {
                this.taille = taille;
                this.ensembleCases = ensembleCases;

                this.estInitialiser = false;
                for (Case c : ensembleCases) {
                    if (c.getValeur() != 0) { // s'il y a au moins une case non vide
                        estInitialiser = true;
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        
    public static Grille randomInitialization(int nbCasesReveles, int taille){
        
        int tailleAuCarree = taille*taille;
        Grille g = new Grille(taille);
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
            
            Case c = g.ensembleCases.get(j); //peut être préciser si j représent la position de la case
            ArrayList<Integer> candidats = c.getCandidats();
            Collections.shuffle(candidats);
            int valeur_c = candidats.remove(0);
            c.setValeur(valeur_c);
            c.estModifiable(false);
            int ligne = j/tailleAuCarree;
            int colonne = j-ligne*tailleAuCarree;
            int bloc = (ligne/taille)*taille + colonne/taille;
            ArrayList<Case> cLine = g.getLine(ligne); 
            ArrayList<Case> cColumn = g.getColumn(colonne);
            ArrayList<Case> cBlock = g.getBlock(bloc);
            
            for (Case d : cLine){
                d.removeCandidat(valeur_c);
            }
            for (Case d : cColumn){
                d.removeCandidat(valeur_c);
            }
            for (Case d : cBlock){
                d.removeCandidat(valeur_c);
            
            }
            
            g.setLine(ligne, cLine);
            g.setColumn(colonne,cColumn);
            g.setBlock(bloc, cBlock); 
            g.ensembleCases.set(j, c);
            
            
        }
        
        return g;
      
    }
    
    public void singletonNuVersbose(int indexCase){
       
       Case c = ensembleCases.get(indexCase);
       if (c.estModifiable()){
            if (c.getCandidats().size() == 1){
                int valeur_c = c.getCandidats().get(0);
                c.setValeur(valeur_c);
                c.removeCandidat(valeur_c);
            }
            ensembleCases.set(indexCase, c);
       }
       else {
           System.out.println("La case " + indexCase + " n'est pas modifiable !");
       }
    
    }
    
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
        System.out.println("___________");
        for (int i = 0; i < taille; i++) {
            for (int k = i*t; k<(i+1)*t; k++){
                if ((k+1)%(taille*taille)==0){
                    System.out.println( this.ensembleCases.get(k).getValeur());
                }
                else {
                    System.out.print(this.ensembleCases.get(k).getValeur()+" |");
                }
            }
            System.out.println("___________");
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
     
    
    
    /*
    Pour les candidats proposées par le joueur, créer une grille fantome qui
    contiendra les propositions du joueuer
    
    */
    
    /*
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
    
    public static Grille resolutionAlgorithmique(Grille g){
        // Algorithmes permettant de simplifier les candidats des cases
        
        //Application du singleton caché à toutes les cases de la grille
        
        
        
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
                while (temp < g.ensembleCases.size()){
                    if (g.ensembleCases.get(temp).getCandidats().size() <
                           g.ensembleCases.get(index).getCandidats().size()){
                        index = temp;
                    }
                    if (g.ensembleCases.get(index).getCandidats().size() == 2){
                            temp+=g.ensembleCases.size();
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
                Grille g1 = new Grille(g.taille,g.ensembleCases);
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
        
        g.showGrille();
        System.out.println("\nEtat des cases après une étape");
        for(Case c: g.getEnsembleCases()){
            System.out.println(c);
        }
        for (int i = 0; i < g.getEnsembleCases().size(); i++){
            if (g.ensembleCases.get(i).estModifiable()){
                g.singletonCache(i);
            }
        }
        
        return g;
    
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
    
    // fonctionne seulement si tous les valeurs correctes des cases modifiables sont présentes
    // parmi les candidats !!!!!!!!!!!!!!!!!!!
    public static Grille resolutionHasardeuse(Grille g,int indexDepart){
        
        // assure que toutes les cases aient l'ensemble des candidats pour s'assurer du
        // bon fonctionnement de la méthode 
        if (indexDepart == 0){
            ArrayList<Integer> allCandidates = new ArrayList<>();
            for (int i = 0; i < g.getTaille() * g.getTaille(); i++) {
                allCandidates.add(i + 1);
            }
            for (int i = 0; i < g.getEnsembleCases().size(); i++) {
                g.setCandidatCase(i, allCandidates);
            }
        }
        
        if(!g.correcteEtPleine()){
            if (indexDepart < g.getEnsembleCases().size()){
                if (g.getEnsembleCases().get(indexDepart).estModifiable()) {
                    Grille g1 = new Grille(g.getTaille(),g.getEnsembleCases());
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
            }//System.out.println("candidats en trop colonne "+candidatsEnTrop);
            
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

    
}
    
      

