/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;

/**
 *
 * @author smoukoka
 */
public class Coup {
    
    private int position;
    private int valeurApres;
    private int valeurAvant;
    private ArrayList<Integer> candidatsAvant;
    private ArrayList<Integer> candidatsApres;
    
    public Coup(int position,int valeurAvant, int valeurApres) {
        this.position = position;
        this.valeurApres = valeurApres;
        this.valeurAvant = valeurAvant;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public int getValeurApres() {
        return valeurApres;
    }

    public void setValeurApres(int valeurApres) {
        this.valeurApres = valeurApres;
    }

    public int getValeurAvant() {
        return valeurAvant;
    }

    public void setValeurAvant(int valeurAvant) {
        this.valeurAvant = valeurAvant;
    }
  
}
