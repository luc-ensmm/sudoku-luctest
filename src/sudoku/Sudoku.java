/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Interface_Graphique.Menu_Principal;
import java.util.Calendar;

/**
 *
 * @author smoukoka
 */
public class Sudoku {
    
    private Joueur j;
    private Grille g;
    private Pile listeCoup;
    private Grille solution;

    public Sudoku(Joueur j, Grille g, Grille solution) {
        this.j = j;
        this.g = g;
        this.solution = solution;
        this.listeCoup = new Pile();
    }
    
    /*
    public Sudoku(Joueur j, Grille g){
        this.j = j;
        this.g  = g;
        this.solution = Grille.resolutionHasardeuse(g, 0);
        this.listeCoup = new Pile();
    }
    */
    
    public Sudoku(Joueur j, Grille g, Grille solution, Pile listeCoup){
        this.j = j;
        this.g = g;
        this.listeCoup = listeCoup;
        this.solution = solution;
    }
    
    /*public Sudoku(Joueur j, int nbCasesRevelees, int taille){
        this.j = j;
        Grille g = Grille.randomInitialization(nbCasesRevelees, taille);
        Pile listeCoup = new Pile();
        Grille solution = Grille.resolutionHasardeuse(g, 0);
    }*/
    
    /**
     * Retourne la grille par référence
     * @return La grille de l'objet et non un clone
     */
    public Grille getGrille() {
        return g;
    }
    
    public Grille getSolution(){
        return solution;
    }
    
    public void setJoueurName(String name){
        j.setNom(name);
    }
    
    public void setJoueurScore(int score){
        j.setScore(score);
    }
    
    public int getJoueurScore(){
        return j.getScore();
    }
    
    public void jouerUnCoup(){
        
        int taille = g.getTaille();
        int ligne = -1;
        int colonne = -1;
        System.out.println("Veuillez entrer la ligne et la colonne de votre case:");
        while (ligne < 1 || ligne > taille*taille) {
            
            System.out.print("Ligne(1 à " + taille*taille + "):");
            ligne = Clavier.Clavier.getInt();
        }
        ligne-=1;
        
        while(colonne < 1 || colonne > taille*taille) {
        
            System.out.print("Colonne(1 à " + taille*taille + "):");
            colonne = Clavier.Clavier.getInt();
        
        }
        colonne-=1;
        
        int position_case = taille*taille*ligne + colonne;
        ArrayList<Case> cases = g.getEnsembleCases();
        int valeur = -1;
        int valeurInitiale = cases.get(position_case).getValeur();
        if (cases.get(position_case).estModifiable()){
            
            while (valeur < 1 || valeur > taille*taille) {
                System.out.print("Entrer la valeur de la case (1 à " + taille*taille +"):");
                valeur = Clavier.Clavier.getInt();
                if (valeur < 1 || valeur > taille*taille){
                    System.out.println("Valeur hors de la plage d'utilisation !");
                }
                else {
                    int valeurAvant = cases.get(position_case).getValeur();
                    cases.get(position_case).setValeur(valeur);
                    g.setEnsembleCases(cases);
                    listeCoup.push(new Coup(position_case, valeurAvant , valeur));
                    //System.out.println("ligne "+ligne);
                    //System.out.println("colonne "+colonne);
                    //g.valeursPlausible(ligne, colonne);
                    if(g.valeursPlausible(ligne, colonne) == false){
                        System.out.println("la valeur proposée n'est pas possible veuillez changer la valeur");
                    } 
    
                }
            }
        
        }
        else {
            System.out.println("La case choisie n'est pas modifiable");
        }
       
    
    }
    
    
    public void play(){
        g.afficheGrille();
        while(!g.pleine()){
            this.jouerUnCoup();
            g.afficheGrille();
        }
        
        if (g.equals(solution)){
            System.out.println("Vous avez trouvé la bonne solution !");
        }
        else {
            System.out.println("Faux !");
        }
               
    }
    
    
    
    public void saveGame (){
        try{
            FileWriter fichier = new FileWriter("partie_"+ j.getNom() +".txt");
            fichier.write("#nom du joueur: "+j.getNom()+"\n");
            fichier.write("#score: "+String.valueOf(j.getScore())+"\n");
            fichier.write("#taille: "+String.valueOf(g.getTaille())+"\n");
            fichier.write("#grille du joueur: \n");
            fichier.write("%;");
            for (int i = 0; i<g.getEnsembleCases().size(); i++){
                fichier.write(g.getEnsembleCases().get(i).getValeur()+";");
            }
            fichier.write("\n"+"#solution de la partie: \n$;");
            for(int i = 0; i<solution.getEnsembleCases().size(); i++){
                fichier.write(solution.getEnsembleCases().get(i).getValeur()+";");
            }
            
            String position;
            String valeurCoup;
            if (!listeCoup.empty()){
                fichier.write("\n#liste des coups joués:\n#valeur avant     valeurapres    position de la case\n");
                int tailleListe = listeCoup.size();
                for (int i=0; i<tailleListe; i++){ 
                    Coup coupCourant = listeCoup.pop();
                    fichier.write(coupCourant.getPosition()+"  "+coupCourant.getValeurAvant()+"  "+coupCourant.getValeurApres()+"\n");
                }
            }
            fichier.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static Sudoku chargerGrille (String nomDuFichier){
        int scoreCourant = 0;
        String name = "";
        Case nouvelleCase;
        Joueur joueurNouveau;
        int taille = 0;
        ArrayList<Coup> stockCoup = new ArrayList<Coup>();
        ArrayList<Case> ensembleDesCases = new ArrayList<Case>();
        Pile listeDesCoups = new Pile();
        ArrayList<Case> ensembleSolutionCase = new ArrayList<Case>();
        
        //lecture du fichier
        try{
            BufferedReader fichier = new BufferedReader (new FileReader(nomDuFichier+".txt"));//rajouter condition si txt déjà dans nom du fichier
            while (fichier.ready()){
                String ligne;
                ligne = fichier.readLine();
                if (ligne.startsWith("#")){
                    if (ligne.contains("taille")){
                    taille = Integer.parseInt(ligne.substring(9, 10));
                    //System.out.println(this.g.getTaille());
                    }
                    if(ligne.contains("nom du joueur")){
                        name = ligne.substring(16);
                    }
                    if(ligne.contains("score")){
                        scoreCourant = Integer.parseInt(ligne.substring(8));
                    }
                //on cherche à présent à reconstituer la grille de jeu   
                } else if (ligne.startsWith("%")){
                    String champs[] = ligne.split(";");
                    int tailleAuCarre = taille*taille;
                    //System.out.println(tailleCarre*tailleCarre);
                    for (int i = 1; i<tailleAuCarre*tailleAuCarre+1; i++){
                        //System.out.print(champs[i]+" ");
                        int valeurCase = Integer.parseInt(champs[i]);
                        ArrayList<Integer> candidats = new ArrayList<Integer>();
                        if(valeurCase!=0){
                            nouvelleCase = new Case(taille, valeurCase, candidats,false);
                            
                        } else {
                            nouvelleCase = new Case(taille, valeurCase, candidats,true);
                            
                        }ensembleDesCases.add(nouvelleCase);
                    }
                //on reconstruit la grille de solution
                } else if(ligne.startsWith("$")) {
                    String champs[] = ligne.split(";");
                    int tailleAuCarre = taille*taille;
                    for (int i = 1; i<tailleAuCarre*tailleAuCarre+1; i++){
                        //System.out.print(champs[i]+" ");
                        int valeurCase = Integer.parseInt(champs[i]);
                        ArrayList<Integer> candidats = new ArrayList<Integer>();
                        nouvelleCase = new Case(taille, valeurCase, candidats, false);
                        ensembleSolutionCase.add(nouvelleCase);
                    }
                } else {
                    
                    String position = ligne.substring(0,2).replace(" ","");
                    String valeurAvant = ligne.substring(3,5).replace(" ","");
                    String valeurApres = ligne.substring(6).replace(" ","");
                    int positionInt = Integer.parseInt(position);
                    int valeurAvantInt = Integer.parseInt(valeurAvant);
                    int valeurApresInt = Integer.parseInt(valeurApres);
                    Coup unCoup = new Coup(positionInt, valeurAvantInt, valeurApresInt);
                    stockCoup.add(unCoup);
                    ensembleDesCases.get(positionInt).estModifiable(true);
                }
                
            }if (stockCoup.size()>1){
                    for(int i = stockCoup.size()-1; i>=0; i--){
                        listeDesCoups.push(stockCoup.get(i));   
                    }
                }
            Joueur joueurCourant = new Joueur(name,scoreCourant); 
            Grille grilleCourante = new Grille(taille,ensembleDesCases);
            Grille laSolution = new Grille(taille,ensembleSolutionCase);
            
        }catch (IOException e){
            e.printStackTrace();
        } 
        
        return new Sudoku(new Joueur(name,scoreCourant),new Grille(taille,ensembleDesCases),
                new Grille(taille,ensembleSolutionCase),listeDesCoups);
        
    }
    
    public void afficheSudoku(){
        this.g.afficheGrille();
    }
    
    public void afficheSolution(){
        this.solution.afficheGrille();
    }
    

    public void addCoup(Coup coup){
        listeCoup.push(coup);
    }
    
    public Pile getListeCoup(){
        return listeCoup;
    }
    
    public void revenirEnArriere(int nbCoupsJoues){
        System.out.println(listeCoup.size());
        if (nbCoupsJoues > listeCoup.size()){
            System.out.println("Vous n'avez pas joué autant de coups");
        } else {
            while(nbCoupsJoues>0){
            Coup coupCourant = listeCoup.pop();
            Case caseConcerne = g.getEnsembleCases().get(coupCourant.getPosition());
            caseConcerne.setValeur(coupCourant.getValeurAvant());
            g.getEnsembleCases().set(coupCourant.getPosition(), caseConcerne);
            System.out.println("coupCourant.getPosition() "+coupCourant.getPosition());
            System.out.println("nbCoupsJoues "+nbCoupsJoues);
            nbCoupsJoues--;
            
            }
        } this.afficheSudoku();
    }
        
       
 public void jouerUnCoupAvecCandidats(){
        
        int taille = g.getTaille();
        int ligne = -1;
        int colonne = -1;
        int nbHelp1 = 0;
        int nbHelp2 = 0;
        int nbHelp3 = 0;
        int nbHelp4 = 0;
            System.out.println("Veuillez entrer la ligne et la colonne de votre case:");
            while (ligne < 1 || ligne > taille*taille) {
            
                System.out.print("Ligne(1 à " + taille*taille + "):");
                ligne = Clavier.Clavier.getInt();
            }
        ligne-=1;
            while(colonne < 1 || colonne > taille*taille) {
        
                System.out.print("Colonne(1 à " + taille*taille + "):");
                colonne = Clavier.Clavier.getInt();
        
            }
        colonne-=1;
        String mot = "oui";
        ArrayList<Integer> lesCandidats = new ArrayList<Integer>();
        int unCandidat;
        int position_case = taille*taille*ligne + colonne;
        int valeurAvant = this.getGrille().getValeurCase(position_case);
        if(this.getGrille().getEnsembleCases().get(position_case).estModifiable() == true){
            System.out.println("Fonctionnalitées disponibles:"
                 + "\nVous pouvez taper directement les valeurs de vos candidats ou bien "
                 + "\nvous pouvez taper h qui vous proposera les différentes aides disponibles qui sont:"
                    + "\n  a)Aide 1 vous indique si la solution d'une case se trouve parmi les candidats que vous avez proposés (taper a)\"" 
                    + "\n  b)Aide 2 vous indique toutes les cases dont aucun des candidats n'est solution taper b)"
                    + "\n  c)Aide 3 vous indique les candidats à enlever dans la case sélectionnée car des cases non modifiables possèdent déjà cette valeur "
                    + "soit dans la ligne,soit dans la colonne, soit dans le bloc (taper c)\"" 
                    + "\n  d)Aide 4 supprime un candidats faux dans chaque cases modifiables (taper d)");
            while (mot.equals("oui") && lesCandidats.size()<taille*taille) {
                System.out.print("Entrer la valeur d'un candidat de la case (1 à " + taille*taille +") ou taper h:");
                String reponse = Clavier.Clavier.getString();
                if(this.estInt(reponse) == true){
                    unCandidat = Integer.parseInt(reponse);
                    if(!lesCandidats.contains(unCandidat) && unCandidat<=taille*taille && unCandidat>0 && !this.g.getEnsembleCases().get(position_case).getCandidats().contains(unCandidat)){
                        lesCandidats.add(unCandidat);
                    
                        System.out.println("Voulez vous ajouter d'autres candidats ? (oui ou non)");
                        mot = Clavier.Clavier.getString();
                    
                    } else{
                        System.out.println("le candidat proposé fait déjà parti des candidats de la case ou sa valeur n'est pas valide");
                    } 
                } else {
                    if (reponse.equals("h")) {
                        System.out.println("Quelle type d'aide souhaitez vous ?");
                        String aide = Clavier.Clavier.getString();
                        if(aide.equals("a")){
                            this.help1(ligne, colonne);
                            nbHelp1 += 1;
                            System.out.println("nbhelp1 "+nbHelp1);
                        } else if (aide.equals("b")){
                            this.help2();
                            nbHelp2 += 1;
                            System.out.println("nbhelp2 "+nbHelp2);
                        } else if (aide.equals("c")){
                            this.help3(ligne, colonne);
                            nbHelp3 += 1;
                            System.out.println("nbhelp3 "+nbHelp3);
                        } else {
                            this.help4();
                            nbHelp4 += 1;
                            System.out.println("nbhelp4 "+nbHelp4);
                        } 
                          System.out.println("Voulez vous ajouter d'autres candidats ? (oui ou non)");
                          mot = Clavier.Clavier.getString();
                    } else {
                    System.out.println("Nous n'avons pas compris votre réponse");
                    }
                }
            }this.calculScore(nbHelp1, nbHelp2, nbHelp3, nbHelp4);
            this.g.getEnsembleCases().get(position_case).addCandidat(lesCandidats);
            System.out.println("candidats de la case "+this.g.getEnsembleCases().get(position_case).getCandidats());
            //this.getGrille().candidatsEnTrop(ligne, colonne);
            //this.supprimerCandidatsEnTrop(ligne, colonne); //méthode qu'il convient d'utiliser sur une grille de taille 3 minimum sinon pas d'intérêt
            this.g.getEnsembleCases().get(position_case).resteUnCandidat(); 
            int valeurApres = this.getGrille().getValeurCase(position_case);
            if(valeurApres != valeurAvant){
                listeCoup.push(new Coup(position_case, valeurAvant , valeurApres));
            }
        } else {
            System.out.println("la case sélectionnée n'est pas modifiable");
        }
    }
    public void enleverCandidat(){
        int taille = g.getTaille();
        int ligne = -1;
        int colonne = -1;
        int indiceCandidatEnlever;
            System.out.println("enlever candidat(s)");
            System.out.println("Veuillez entrer la ligne et la colonne de votre case:");
            while (ligne < 1 || ligne > taille*taille) {
            
                System.out.print("Ligne(1 à " + taille*taille + "):");
                ligne = Clavier.Clavier.getInt();
            }
        ligne-=1;
            while(colonne < 1 || colonne > taille*taille) {
        
                System.out.print("Colonne(1 à " + taille*taille + "):");
                colonne = Clavier.Clavier.getInt();
        
            }
        colonne-=1;
        String mot = "oui";
        ArrayList<Integer> lesCandidats = new ArrayList<Integer>();
        int unCandidat;
        int position_case = taille*taille*ligne + colonne;
        int valeurAvant = this.getGrille().getValeurCase(position_case);
        if(this.getGrille().getEnsembleCases().get(position_case).estModifiable() == true){
            while (mot.equals("oui") && lesCandidats.size()<taille*taille) {
                System.out.print("Entrer la valeur d'un candidat de la case (1 à " + taille*taille +"):");
                unCandidat = Clavier.Clavier.getInt();
                if(unCandidat<=taille*taille && this.g.getEnsembleCases().get(position_case).getCandidats().contains(unCandidat)){
                    indiceCandidatEnlever = this.g.getEnsembleCases().get(position_case).getCandidats().indexOf(unCandidat);
                    this.g.getEnsembleCases().get(position_case).getCandidats().remove(indiceCandidatEnlever);
                    
                    System.out.println("Voulez vous enlever d'autres candidats ? (oui ou non)");
                    mot = Clavier.Clavier.getString();
                    
                } else{
                    System.out.println("le candidat proposé ne fait pas parti des candidats de la case ou sa valeur n'est pas valide");
                }
            }
        System.out.println("candidats de la case "+this.g.getEnsembleCases().get(position_case).getCandidats());
        this.g.getEnsembleCases().get(position_case).resteUnCandidat();
        int valeurApres = this.getGrille().getValeurCase(position_case);
            if(valeurApres != valeurAvant){
                listeCoup.push(new Coup(position_case, valeurAvant , valeurApres));
            }
        } else {
            System.out.println("la case sélectionnée n'est pas modifiable");
        }
    }
    
    public boolean playGameWithCandidat(){
        g.afficheGrille();
        boolean resultat;
        String reponseDuJoueur = "";
        while(!g.pleine()&& reponseDuJoueur != "stop"){
            System.out.println("Que souhaitez vous faire ?"
                    + "\n--> Ajouter des candidats (taper adj)"
                    + "\n--> Enlever des candidats (taper del)"
                    + "\n--> Sauvagarder la partie (taper s)"
                    + "\n--> Revenir en arrière sur les coups joués (taper r)"
                    + "\n--> Visualiser les candidats d'une case (taper c)"
                    + "\n--> Obtenir votre score (taper sc)"
                    + "\n--> Stopper la partie (taper stop)");
            reponseDuJoueur = Clavier.Clavier.getString();
            if (reponseDuJoueur.equals("adj")){
                this.jouerUnCoupAvecCandidats();
            } else if (reponseDuJoueur.equals("del")) {
                this.enleverCandidat();
            } else if (reponseDuJoueur.equals("s")){
                this.saveGame();
                reponseDuJoueur = "stop";
            } else if (reponseDuJoueur.equals("r")){
                System.out.println("Veuillez indiquer sur combien de coups vous souhaitez revenir en arrière :");
                int nbCoups = Clavier.Clavier.getInt();
                this.revenirEnArriere(nbCoups);
            } else if (reponseDuJoueur.equals("c")){
                int taille = this.getGrille().getTaille();
                int ligne = -1;
                int colonne = -1;
                System.out.println("Veuillez entrer la ligne et la colonne de votre case:");
                while (ligne < 1 || ligne > taille*taille) {
            
                    System.out.print("Ligne(1 à " + taille*taille + "):");
                    ligne = Clavier.Clavier.getInt();
                }
                ligne-=1;
                while(colonne < 1 || colonne > taille*taille) {
        
                    System.out.print("Colonne(1 à " + taille*taille + "):");
                    colonne = Clavier.Clavier.getInt();
        
                }
                colonne-=1;
                int positionCase = taille*taille*ligne + colonne;
                System.out.println(this.getGrille().getCandidatCase(positionCase));
            }
            else if (reponseDuJoueur.equals("sc")){
                System.out.println(this.j.getScore());
            } else if(reponseDuJoueur.equals("stop")){
                reponseDuJoueur = "stop";
            } else {
                System.out.println("Nous n'avons pas compris votre demande");
            }
            g.afficheGrille();
        }
        
        if (g.equals(solution)){
            resultat = true;
            System.out.println("Vous avez trouvé la bonne solution !");
            System.out.println("Votre score est de "+this.j.getScore());
        } 
        else {
            resultat = false;
            System.out.println("Faux !");
        }return resultat;
               
    }
    
    public void supprimerCandidatsEnTrop(int lineCase, int columnCase){
        if(!this.getGrille().candidatsEnTrop(lineCase, columnCase).isEmpty());
            this.enleverCandidat();
    }
     
    public void help1(int lineCase, int columnCase){ //permet de savoir si la solution d'une case spécifique se trouve parmi les candidats de cette case
        int j = 0;
        boolean candidatBon = false;
        int taille = this.getGrille().getTaille();
        int positionCase = taille*taille*lineCase + columnCase;
            Case caseCourante = this.getGrille().getEnsembleCases().get(positionCase);
            while(j<caseCourante.getCandidats().size() && candidatBon == false){
                if (caseCourante.getCandidats().get(j) == this.getSolution().getValeurCase(positionCase) || caseCourante.getValeur() == this.getSolution().getValeurCase(positionCase)){
                    candidatBon = true;
                } j++;
            } if (candidatBon == true){
                System.out.println("la solution de la case se trouve parmi les candidats proposés ");
            } else {
                System.out.println("La solution de la case ne se trouve pas parmi les candidats proposés");
            }
        
    }
    
    public void help2(){ //si la case ne contient pas la bonne valeur dans ces candidats ou dans sa propre valeur elle est écrite en rouge
        int taille = this.getGrille().getTaille();
        int ligne;
        int colonne;
        for(int i = 0; i<this.getGrille().getEnsembleCases().size(); i++){
            Case caseCourante = this.getGrille().getEnsembleCases().get(i);
            if(!caseCourante.getCandidats().contains(this.getSolution().getValeurCase(i)) && caseCourante.getValeur() != this.getSolution().getValeurCase(i)){
                String candidatsFaux = "";
                candidatsFaux+= Integer.toString(caseCourante.getValeur());
                ligne = i/(taille*taille);
                colonne = i-ligne*(taille*taille);
                System.out.println("\033[31mAucun des candidats de la case ligne "+(ligne+1)+" colonne "+(colonne+1)+" ne convient. Valeur de la case en question "+candidatsFaux);
            }  
        }
    }
    
public void help3(int lineCase, int columnCase ){
    ArrayList<Integer> candidatsAEnlever = this.getGrille().candidatsEnTrop(lineCase, columnCase);
    int taille = this.getGrille().getTaille();
    int positionCase = taille*taille*lineCase + columnCase;
    for (int i = 0; i<candidatsAEnlever.size(); i++){
        this.getGrille().getCandidatCase(positionCase).remove(candidatsAEnlever.get(i));
    }
    System.out.println("candidat de la case après aide "+this.getGrille().getCandidatCase(positionCase));
}    
    
    public void help4(){ //supprime un candidat faux
        //int nbCandidatsSupprime;
        int indiceCandidatSupprime;
        int valeurBonne = 0;
        for(int i = 0; i<this.getGrille().getEnsembleCases().size(); i++){
            Case caseCourante = this.getGrille().getEnsembleCases().get(i);
            if (this.getGrille().getEnsembleCases().get(i).estModifiable() == true && !caseCourante.getCandidats().isEmpty()){
                if (caseCourante.getCandidats().contains(this.getSolution().getValeurCase(i))){
                    valeurBonne = caseCourante.getCandidats().remove(caseCourante.getCandidats().indexOf(this.getSolution().getValeurCase(i)));
                    //System.out.println("valeur bonne "+valeurBonne);
                    System.out.println("candidat - valeur "+caseCourante.getCandidats());
                }
                //Random ran1 = new Random();
                //nbCandidatsSupprime = ran1.nextInt(caseCourante.getCandidats().size()+1);
                System.out.println("size "+caseCourante.getCandidats().size());
                //System.out.println("nbCandidatsSupprime "+nbCandidatsSupprime);
                //for(int j = 0; j<nbCandidatsSupprime; j++){
                    Random ran2 = new Random();
                    indiceCandidatSupprime = ran2.nextInt(caseCourante.getCandidats().size());
                    System.out.println("indiceCandidatSupprime "+indiceCandidatSupprime);
                    caseCourante.getCandidats().remove(indiceCandidatSupprime);
                //} 
                if (valeurBonne != 0){
                    caseCourante.getCandidats().add(valeurBonne);
                    valeurBonne = 0;
                    Collections.sort(caseCourante.getCandidats());
                    System.out.println("caseCourante.getCandidats()"+caseCourante.getCandidats());
                }
            }
        }
    } 
    
    /*public void help5(){ //supprime aléatoirement des candidats faux
        int nbCandidatsSupprime;
        int indiceCandidatSupprime;
        int valeurBonne = 0;
        for(int i = 0; i<this.getGrille().getEnsembleCases().size(); i++){
            Case caseCourante = this.getGrille().getEnsembleCases().get(i);
            if (this.getGrille().getEnsembleCases().get(i).estModifiable() == true && !caseCourante.getCandidats().isEmpty()){
                if (caseCourante.getCandidats().contains(this.getSolution().getValeurCase(i))){
                    valeurBonne = caseCourante.getCandidats().remove(caseCourante.getCandidats().indexOf(this.getSolution().getValeurCase(i)));
                    //System.out.println("valeur bonne "+valeurBonne);
                    System.out.println("candidat - valeur "+caseCourante.getCandidats());
                }
                Random ran1 = new Random();
                nbCandidatsSupprime = ran1.nextInt(caseCourante.getCandidats().size()+1);
                System.out.println("size "+caseCourante.getCandidats().size());
                System.out.println("nbCandidatsSupprime "+nbCandidatsSupprime);
                for(int j = 0; j<nbCandidatsSupprime; j++){
                    Random ran2 = new Random();
                    indiceCandidatSupprime = ran2.nextInt(caseCourante.getCandidats().size());
                    System.out.println("indiceCandidatSupprime "+indiceCandidatSupprime);
                    caseCourante.getCandidats().remove(indiceCandidatSupprime);
                } 
                if (valeurBonne != 0){
                    caseCourante.getCandidats().add(valeurBonne);
                    valeurBonne = 0;
                    Collections.sort(caseCourante.getCandidats());
                    System.out.println("caseCourante.getCandidats()"+caseCourante.getCandidats());
                }
            }
        }
    }*/
    
    public static Sudoku playCommande(){
        String mot;
        String name;
        Joueur joueur;
        Sudoku s;
        int taille;
        int nbCasesRevelees;
        String niveau = "";
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s");
        System.out.println("Veuillez entrer votre nom de joueur");
        name = Clavier.Clavier.getString();
        name.toLowerCase();
        System.out.println("Voulez vous jouer une nouvelle grille ou charger une ancienne grille ? (nouvelle grille ou ancienne grille)");
        mot = Clavier.Clavier.getString();
        mot.toLowerCase();
        System.out.println("mot "+mot);
        if (mot.equals("nouvelle grille")){
            joueur = new Joueur(name);
            System.out.println("Quelle taille de grille souhaitez vous ? (2, 3, 4...");
            taille = Clavier.Clavier.getInt();
            System.out.println("niveau de difficulté: facile, moyen, difficile");
            niveau = Clavier.Clavier.getString();
            nbCasesRevelees = Grille.niveauGrille(niveau, taille);
            Grille laSolution = Algorithm.randomSolutionGenerator(taille);
            Grille laGrille = Algorithm.randomGrilleGenerator(laSolution,nbCasesRevelees);
            
            laGrille.videLesCandidats();
            //laSolution.afficheGrille();
            Pile p = new Pile();
            s = new Sudoku(joueur, laGrille, laSolution, p);
            
        } else {
            String nomDeLaPartie = "partie_"+ name;
            System.out.println("nom de la partie "+nomDeLaPartie);
            s = Sudoku.chargerGrille(nomDeLaPartie);
        } 
        System.out.print("A vous de jouer !\n");
        s.playGameWithCandidat();
        s.scoreFinale(niveau, cal);
        return s; 
    }
    
    public boolean estInt(String chaine){
        boolean valeur = true;
        try{
            int entier = Integer.parseInt(chaine);
            
        } catch(Exception e){
            valeur = false;
        }
        return valeur;
    } 
    public int scoreFinale(String niveauDeDifficulte, Calendar cal){
        Calendar ca2 = Calendar.getInstance();
        int duree;
        int ecart;
        int nbPoint;
        int h = ca2.get(Calendar.HOUR_OF_DAY)-cal.get(Calendar.HOUR_OF_DAY);
        int m = ca2.get(Calendar.MINUTE)-cal.get(Calendar.MINUTE);
        int s = ca2.get(Calendar.SECOND)-cal.get(Calendar.SECOND);
        //System.out.println("h "+h+"m "+m+"s "+s);
        duree = (h*360+m*60+s);
        if(niveauDeDifficulte.equals("moyen")){
            this.j.setScore(5);
            ecart = (1500-duree)/30;
            nbPoint = -5*ecart;
        } else if (niveauDeDifficulte.equals("difficile")){
            this.j.setScore(10);
            ecart = (2400-duree)/30;
            nbPoint = -5*ecart;
            
        } else {
            ecart = (600-duree)/30;
            nbPoint = -5*ecart;
            this.j.setScore(nbPoint);
        }
        //System.out.println("ecart "+ecart);
        //System.out.println("nb point "+nbPoint);
        return this.j.getScore();
    }
    
    public int calculScore(int nbHelp1, int nbHelp2, int nbHelp3, int nbHelp4){
        int pointParNbHelp1 = -5*nbHelp1;
        int pointParNbHelp2 = -10*nbHelp2;
        int pointParNbHelp3 = -15*nbHelp3;
        int pointParNbHelp4 = -20*nbHelp4;
        this.j.setScore(pointParNbHelp1 + pointParNbHelp2 + pointParNbHelp3 + pointParNbHelp4);
        return pointParNbHelp1 + pointParNbHelp2 + pointParNbHelp3 + pointParNbHelp4;
    }
}
