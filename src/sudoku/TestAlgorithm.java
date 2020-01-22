/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    
}
