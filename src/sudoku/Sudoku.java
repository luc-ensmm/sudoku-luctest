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
    
    public Sudoku(Joueur j, Grille g, Pile listeCoup, Grille solution){
        this.j = j;
        this.g = g;
        this.listeCoup = listeCoup;
        this.solution = solution;
    }
    
    public Sudoku(){
        Joueur j = new Joueur("Nom",0);
        Grille g = new Grille(0);
        Pile listeCoup = new Pile();
        Grille solution = new Grille(0);
    }
    
    public Grille getGrille() {
        return g;
    }
    
    public Grille getSolution(){
        return solution;
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
        Sudoku s = new Sudoku();
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
            s = new Sudoku(joueurCourant,grilleCourante,listeDesCoups,laSolution);
        }catch (IOException e){
            e.printStackTrace();
        } return s;
    }
    
    public void afficheSudoku(){
        this.g.afficheGrille();
    }
    
    public void afficheSolution(){
        this.solution.afficheGrille();
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
        if(this.getGrille().getEnsembleCases().get(position_case).estModifiable() == true){
            while (mot.equals("oui") && lesCandidats.size()<taille*taille) {
                System.out.print("Entrer la valeur d'un candidat de la case (1 à " + taille*taille +"):");
                unCandidat = Clavier.Clavier.getInt();
                if(!lesCandidats.contains(unCandidat) && unCandidat<=taille*taille && !this.g.getEnsembleCases().get(position_case).getCandidats().contains(unCandidat)){
                    lesCandidats.add(unCandidat);
                    
                    System.out.println("Voulez vous ajouter d'autres candidats ? (oui ou non)");
                    mot = Clavier.Clavier.getString();
                    
                } else{
                    System.out.println("le candidat proposé fait déjà parti des candidats de la case ou sa valeur n'est pas valide");
                }
            } 
        this.g.getEnsembleCases().get(position_case).addCandidat2(lesCandidats);
        System.out.println("candidats de la case "+this.g.getEnsembleCases().get(position_case).getCandidats());
        this.getGrille().candidatsEnTrop(ligne, colonne);
        //this.supprimerCandidatsEnTrop(ligne, colonne); //méthode qu'il convient d'utiliser sur une grille de taille 3 minimum sinon pas d'intérêt
        this.g.getEnsembleCases().get(position_case).resteUnCandidat();
        
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
        
        } else {
            System.out.println("la case sélectionnée n'est pas modifiable");
        }
    }
    
    public void playGameWithCandidat(){
        g.afficheGrille();
        while(!g.pleine()){
            this.jouerUnCoupAvecCandidats();
            g.afficheGrille();
        }
        
        if (g.equals(solution)){
            System.out.println("Vous avez trouvé la bonne solution !");
        } 
        else {
            System.out.println("Faux !");
        }
               
    }
    
    public void supprimerCandidatsEnTrop(int lineCase, int columnCase){
        if(!this.getGrille().candidatsEnTrop(lineCase, columnCase).isEmpty());
            this.enleverCandidat();
    }
     
    public void help1(){ //permet de savoir si la solution d'une case spécifique se trouve parmi les candidats de cette case
        int j = 0;
        boolean candidatBon = false;
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
            Case caseCourante = this.getGrille().getEnsembleCases().get(positionCase);
            while(j<caseCourante.getCandidats().size() && candidatBon == false){
                if (caseCourante.getCandidats().get(j) == this.getSolution().getValeurCase(positionCase) || caseCourante.getValeur() == this.getSolution().getValeurCase(positionCase)){
                    System.out.println("la solution de la case se trouve parmi les candidats proposés ");
                    candidatBon = true;
                } else {
                    System.out.println("La solution de la case ne se trouve pas parmi les candidats proposés");
                }j++;
            }
        
    }
    
    public void help2(){ //si la case ne contient pas la bonne valeur dans ces candidats ou dans sa propre valeur elle est écrite en rouge
        int taille = this.getGrille().getTaille();
        int ligne;
        int colonne;
        for(int i = 0; i<this.getGrille().getEnsembleCases().size(); i++){
            Case caseCourante = this.getGrille().getEnsembleCases().get(i);
            if(!caseCourante.getCandidats().contains(this.getSolution().getValeurCase(i)) && caseCourante.getValeur() != this.getSolution().getValeurCase(i)){
                String candidatsFaux = "\033[31m";
                candidatsFaux+= Integer.toString(caseCourante.getValeur());
                ligne = i/(taille*taille);
                colonne = i-ligne*(taille*taille);
                System.out.println("Aucun des candidats de la case ligne "+ligne+" colonne "+colonne+" ne convient. Valeur de la case en question "+candidatsFaux);
            }  
        }
    }
        
    
    public void help3(){ //supprime aléatoirement des candidats faux
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
    }
 
}
