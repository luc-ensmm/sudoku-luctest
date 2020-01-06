/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author smoukoka
 */
public class Case {
    
    final private int taille; // à remplacer par tailleGrille
    private int valeur; // si valeur = 0 alors la case est considérée vide
    private ArrayList<Integer> candidats;
    private boolean estModifiable; // défini si le joueur peut modifier ou non la case 

    public Case(int taille, int valeur, ArrayList<Integer> candidats, boolean estModifiable) {
        this.taille = taille;
        this.valeur = valeur;
        this.candidats = candidats;
        this.estModifiable = estModifiable;
    }
   
    
    public Case(int taille, int valeur, boolean estModifiable){
        this.valeur = valeur;
        this.taille = taille;
        this.candidats = new ArrayList<Integer>();
        this.estModifiable = estModifiable;
    }
    
    public Case(int taille){ // utiliser pour initialiser aléatoirement la valeur d'une case non vide 
        int v = (int)(Math.random() * (taille*taille+1));
        ArrayList<Integer> c = new ArrayList<>();
        for (int i = 1; i <= taille*taille; i++){
            if (i != v) {
                c.add(i);
            }
        }
        
        this.valeur = v;
        this.taille = taille;
        this.candidats = c;
        this.estModifiable = true;
         
    }
    
    public int getTaille() {
        return taille;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        if (estModifiable) {
        this.valeur = valeur;
        }
        else{
            System.out.println("La case n'est pas modifiable. Modification de valeur impossible");
        }
    }

    public ArrayList<Integer> getCandidats() {
        return candidats;
    }

    
    public boolean removeCandidat(Integer candidat){ // utilise la méthode remove(Object o)
    
            return candidats.remove(candidat);
            // retourne true ou false selon que le candidat soit présent dans candidats
    }
    
    public void addCandidat(Integer candidat) {
        if(estModifiable){
            if(!candidats.contains(candidat)){
                candidats.add(candidat);
            }
        }
        else{
            System.out.println("La case n'est pas modifiable. Modification des candidats impossible");
        }
    }
    
    public void addCandidat(ArrayList<Integer> autreCandidats){  
        for (Integer candidat: autreCandidats){
            this.addCandidat(candidat);
        }
    }
    
    public void addCandidat2(ArrayList<Integer> autreCandidats){ // celle ci fonctionne
        for(int i = 0; i<autreCandidats.size(); i++){
            Integer candidat = autreCandidats.get(i);
            this.addCandidat(candidat);
        }
    }
    
    public void resteUnCandidat(){
        if (this.candidats.size() == 1){
            this.setValeur(candidats.get(0));
        }
    }
    public boolean estModifiable() {
        return estModifiable;
    }

    public void estModifiable(boolean estModifiable) {
        this.estModifiable = estModifiable;
    }

    
    
    @Override
    public String toString() {
        return "Case{" + "valeur=" + valeur + ", candidats=" + candidats + ", estModifiable=" + estModifiable + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) { // comparaison basée sur la valeur de la case
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Case other = (Case) obj;
        if (this.valeur != other.valeur) {
            return false;
        }
     
        return true;
    }
    
    @Override
    public Case clone(){
        return new Case(taille,valeur,candidats,estModifiable);
    }
    
    public int candidatDejaPresent (Case other) { 
        Integer candidatEnTrop = 0;
        if(other.estModifiable() == false){
        //while (i<other.candidats.size() && egale == false){
            for (int i = 0; i<this.getCandidats().size(); i++){
                if (this.getCandidats().get(i) == other.getValeur()) {
                    //System.out.print("candidat deja présent "+this.getCandidats().get(i));
                    candidatEnTrop = this.getCandidats().get(i);
                }   
            }//System.out.println("candidats en trop "+candidatEnTrop);
        }return candidatEnTrop;    
    }
    //on compare les candidats d'une case proposés par un joueur à une autre case non modifiable donc sa valeur est fixe
    //ainsi il ne peut y avoir des candidats de même valeur car cette case non modifiable provient de la grille de départ
    
}
