/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static sudoku.TestGrille.stringToArray;

/**
 *
 * @author yannE
 */
public class TestAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       test_generators();
       test_solutionHasardeuse();
       test_solutionAlgorithmique();
    }
    
    public static void test_generators(){
        
        
        Grille solution = Algorithm.randomSolutionGenerator(3);
        System.out.println("Une grille solution:\n");
        //solution.setValeurCase(0, 0);
        solution.showGrille();
        Grille g1 = Algorithm.randomGrilleGenerator(solution, 17);
        Grille g2 = Algorithm.randomGrilleGenerator(solution, 30);
        System.out.println("\n\nUne grille générée aléatoirement à partir de la solution ci-desssus");
        g1.showGrille();
        System.out.println("\n\nUne autre grille générée aléatoirement à partir de la solution ci-dessus");
        g2.showGrille();
        List<Integer> aReveles = Arrays.asList(0,1,6,9,15);
        Grille g3 = Algorithm.grilleGenerator(solution, aReveles);
        System.out.println("\n\nGrille générée en révélant les cases d'indices:");
        aReveles.stream().forEach(n->System.out.print(n + " "));
        System.out.print("\n");
        g3.showGrille();
        
       
    }
    
     
    public static void test_solutionHasardeuse(){
        
        
        ArrayList<Case> listetest = new ArrayList<>();
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("1 2 4"," "),true));
        listetest.add(new Case(2,1,stringToArray(""," "),false));
        listetest.add(new Case(2,2,stringToArray(""," "),false));
        listetest.add(new Case(2,0,stringToArray("1 3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("4"," "),true));
        listetest.add(new Case(2,0,stringToArray("4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2"," "),true));
        listetest.add(new Case(2,0,stringToArray("1 2 4"," "),true));
        listetest.add(new Case(2,3,stringToArray(""," "),false));
        listetest.add(new Case(2,1,stringToArray(""," "),false));
        listetest.add(new Case(2,0,stringToArray("2 3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2 4"," "),true));
        
        
        Grille g = new Grille(2,listetest); 
        System.out.println("Grille initiale avant la résolution\n");
        g.showGrille();
        System.out.println("\n");
        Grille solution = Algorithm.resolutionHasardeuse(g, 0);
        System.out.println("Grille initiale après la résolution\n");
        g.showGrille();
        System.out.println("\n\nGrille solution\n");
        solution.showGrille();
        
        
    }
    
    public static void test_solutionAlgorithmique(){
        
        
        ArrayList<Case> listetest = new ArrayList<>();
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("1 2 4"," "),true));
        listetest.add(new Case(2,1,stringToArray(""," "),false));
        listetest.add(new Case(2,2,stringToArray(""," "),false));
        listetest.add(new Case(2,0,stringToArray("1 3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("4"," "),true));
        listetest.add(new Case(2,0,stringToArray("4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2"," "),true));
        listetest.add(new Case(2,0,stringToArray("1 2 4"," "),true));
        listetest.add(new Case(2,3,stringToArray(""," "),false));
        listetest.add(new Case(2,1,stringToArray(""," "),false));
        listetest.add(new Case(2,0,stringToArray("2 3 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2 4"," "),true));
        listetest.add(new Case(2,0,stringToArray("2 4"," "),true));
        
        
        Grille g = new Grille(2,listetest); 
        //g.showGrille();
        System.out.println("\n");
        g = Algorithm.resolutionAlgorithmique(g);
        System.out.println("\nGrille après résolution");
        g.showGrille();
        /*
        System.out.println("\nEtat des cases après la résolution");
        for(Case c: g.getEnsembleCases()){
            System.out.println(c);
        }
        
        */
        
        //System.out.println("\nGrille après l'application de la résolution hasardeuse");
        
        
        /*
        System.out.println();
        for(Case c: g.getEnsembleCases()){
            System.out.println(c.estModifiable());
        }
        */
        
    }
    
}
