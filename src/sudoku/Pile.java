/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayDeque;

/**
 *
 * @author smoukoka
 */
public class Pile {
    
    private ArrayDeque<Coup> pile;
    
    public Pile(){
        pile = new ArrayDeque<>();
        
    }
    public void push(Coup c){
        this.pile.addLast(c);
    }
    
    public Coup pop (){
        return this.pile.removeLast();
    }
    
    public Coup peek (){
        return this.pile.getLast();
        
    }
    
    public boolean empty() {
        return this.pile.isEmpty();
    }
    
    public int size() {
        return this.pile.size();
    }
    
    public String toString() {
        return "Pile = "+ pile;
    }

}
    
