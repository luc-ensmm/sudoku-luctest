/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author yannE
 */
public class TestCase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int taille = 3;
        Case c1 = new Case(taille,2,true);
        System.out.println(c1.getCandidats());
        Case c2 = new Case(taille,0,true);
        System.out.println(c2.getCandidats());
        
    }
    
}
