/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import sudoku.Grille;
import sudoku.Sudoku;

/**
 *
 * @author yannE
 */
    
public class GrilleTableModel extends DefaultTableModel{
    
    private Sudoku sudoku;
    
    public GrilleTableModel(Sudoku s){
        super(s.getGrille().getTaille()*s.getGrille().getTaille(),
                s.getGrille().getTaille()*s.getGrille().getTaille());
        this.sudoku = s;
        int tailleAuCarree = s.getGrille().getTaille()*s.getGrille().getTaille();
        for (int i = 0; i < tailleAuCarree; i++){
            for(int j = 0; j < tailleAuCarree; j++){
                this.setValueAt(s.getGrille().getCase(i+j*tailleAuCarree).getValeur(),i,j);
            }
        }

        this.addTableModelListener( new GrilleListener());
    }
    
    public Class<?> getColumnClass(int column){
        return Integer.class;
    }
    public Grille getSudokuGrille(){
        return sudoku.getGrille();
    }
    
}
