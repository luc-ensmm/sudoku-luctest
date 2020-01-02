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
public class TestSudoku {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        test_sudokuJouable2();
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
        //s1.jouerUnCoup();
        //s1.getGrille().valeursPlausible(2, 2);
        //s1.getGrille().valeurPlausibleColonne(2, 1);
        //s1.getGrille().valeurPlausibleLigne(2, 1);
        //s1.getGrille().valeurPlausibleBloc(2, 1);
        //s1.revenirEnArriere(1);
        //s1.jouerUnCoup();
        //Calendar cal = Calendar.getInstance();
        //System.out.println(cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s"); 
    }
    
    public static void test_jouerAvecUnCandidat(){
        Sudoku s1 = Sudoku.chargerGrille("nouvelle_grille");
        s1.afficheSudoku();
        s1.jouerUnCoupAvecCandidats();
        s1.getGrille().candidatsEnTropColonne(3, 2);
    }
}
