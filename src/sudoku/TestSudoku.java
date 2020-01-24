/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author smoukoka
 */
public class TestSudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test_jouerUnePartie();
    }
    
    
    public static void test_sudokuJouable(){
        ArrayList<Case> testList = new ArrayList<>();
        int taille = 2;
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        
        ArrayList<Case> solution = new ArrayList<>();
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        
        Grille uneGrille = new Grille (taille, testList);
        Grille grilleSolution = new Grille(taille,solution);
        Joueur j = new Joueur("Nom",0);
        Sudoku s = new Sudoku(j,uneGrille,grilleSolution);
        s.play();
    }
    
    public static void test_sudokuJouable2(){
        ArrayList<Case> testList = new ArrayList<>();
        int taille = 2;
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        
        Grille laGrille = new Grille(taille,testList);
        Grille laSolution = Grille.resolutionHasardeuse(laGrille, 0);
        Joueur j = new Joueur("No name");
        Sudoku s = new Sudoku(j,laGrille,laSolution);
        s.play();
        
    }
    
    public static void testSudokuGraphique(){
            ArrayList<Case> testList = new ArrayList<>();
        int taille = 2;
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        
        Grille laGrille = new Grille(taille,testList);
        Grille laSolution = Grille.resolutionHasardeuse(laGrille, 0);
        Joueur j = new Joueur("No name");
        Sudoku s = new Sudoku(j,laGrille,laSolution);
       
    }
    
    public static void test_sudokuSauvegarde(){
        ArrayList<Case> testList = new ArrayList<>();
        int taille = 2;
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,1,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,2,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,3,new ArrayList<Integer>(),false));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,0,new ArrayList<Integer>(),true));
        testList.add(new Case(taille,4,new ArrayList<Integer>(),false));
        
        ArrayList<Case> solution = new ArrayList<>();
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,3,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,2,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,1,new ArrayList<Integer>(),true));
        solution.add(new Case(taille,4,new ArrayList<Integer>(),true));
        
        Grille uneGrille = new Grille (taille, testList);
        Grille grilleSolution = new Grille(taille,solution);
        Joueur j = new Joueur("Nom",0);
        Sudoku s = new Sudoku(j,uneGrille,grilleSolution);
        //s.play();
        //s.saveGame();
        Sudoku s1 = Sudoku.chargerGrille("nouvelle_grille");
        s1.afficheSudoku();
        s1.jouerUnCoup();
        //s1.getGrille().valeursPlausible(2, 2);
        //s1.getGrille().valeurPlausibleColonne(2, 1);
        //s1.getGrille().valeurPlausibleLigne(2, 1);
        //s1.getGrille().valeurPlausibleBloc(2, 1);
        s1.revenirEnArriere(1);
        //s1.jouerUnCoup();
        //s1.play();
    }
    
    public static void test_jouerAvecUnCandidat() {
        Sudoku s1 = Sudoku.chargerGrille("nouvelle_grille");
        s1.afficheSudoku();
        s1.jouerUnCoupAvecCandidats();
        //s1.getGrille().candidatsEnTropColonne(2, 1);
        //s1.getGrille().candidatsEnTropLigne(2, 1);
        //s1.getGrille().candidatsEnTropBloc(2, 1);
        //s1.getGrille().candidatsEnTrop(2, 1);
        //s1.afficheSolution();
        //s1.help1();
        //s1.help2();
        //s1.help3(1,1);
        //s1.help4();
        //s1.help5();
        //s1.afficheSudoku();
        //s1.enleverCandidat();
    }

    public static void test_jouerUnePartie() {
        Sudoku s1 = Sudoku.playCommande();
        //Joueur j = new Joueur("Nom",0);
        //Sudoku s = new Sudoku (j,30, 3);
        s1.afficheSudoku();
    }
    
    public static void test_resolutionAlgorithmique(){
        Sudoku s1 = Sudoku.chargerGrille("partie_Luc");
        s1.afficheSudoku();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        /*s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();
        s1.jouerUnCoupAvecCandidats();*/
        //ArrayList<Case> c = s1.getGrille().getBlock(3);
        //System.out.append("c "+c);
        //s1.getGrille().paireNue(s1.getGrille().getBlock(2));
    }
}
