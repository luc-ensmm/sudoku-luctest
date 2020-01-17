/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

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
        s.playInGraphics();
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
        //s1.play();
        //Calendar cal = Calendar.getInstance();
        //System.out.println(cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s"); 
    }
    
    public static void test_jouerAvecUnCandidat() {
        Sudoku s1 = Sudoku.chargerGrille("nouvelle_grille");
        //s1.afficheSudoku();
        //s1.jouerUnCoupAvecCandidats();
        //s1.getGrille().candidatsEnTropColonne(2, 1);
        //System.out.println("column "+s1.getGrille().getColumn(2));
        //System.out.println("line "+s1.getGrille().getLine(2));
        //s1.getGrille().candidatsEnTropLigne(2, 1);
        //System.out.println("block "+s1.getGrille().getBlock(2));
        //s1.getGrille().candidatsEnTropBloc(2, 1);
        //s1.getGrille().candidatsEnTrop(2, 1);
        //s1.afficheSolution();
        //s1.help1();
        //s1.help2();
        //s1.help3(1,1);
        //s1.help4();
        //s1.enleverCandidat();
        //s1.playGameWithCandidat();
    }

    public static void test_jouerUnePartie() {
        Sudoku s1 = Sudoku.playCommande();
        //Joueur j = new Joueur("Nom",0);
        //Sudoku s = new Sudoku (j,30, 3);
        //s.afficheSudoku();
        /*Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.HOUR_OF_DAY)+"h "+cal.get(Calendar.MINUTE)+"m et "+cal.get(Calendar.SECOND)+"s"); 
        Calendar ca2 = Calendar.getInstance();
        System.out.println(ca2.get(Calendar.HOUR_OF_DAY)+"h "+ca2.get(Calendar.MINUTE)+"m et "+(ca2.get(Calendar.SECOND)+1)+"s"); 
        int h = ca2.get(Calendar.HOUR_OF_DAY)-cal.get(Calendar.HOUR_OF_DAY);
        int m = ca2.get(Calendar.MINUTE)-cal.get(Calendar.MINUTE);
        int s = (ca2.get(Calendar.SECOND)+65)-cal.get(Calendar.SECOND);
        System.out.println("h "+h+"m "+m+"s "+s);
        System.out.println("seconde "+(h*360+m*60+s)/30);*/
    }
}
