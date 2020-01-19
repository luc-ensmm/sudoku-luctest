/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface_Graphique;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.lang.String;
/**
 *
 * @author yannE
 */
public class GrilleListener implements TableModelListener {
    
    /**
     *  Récupère les coordonnées de la cellule modifiée et met à jour la valeur
     * de la case associé
     * @param e 
     */
    @Override
    public void tableChanged(TableModelEvent e){
        
        try { 
            GrilleTableModel table = (GrilleTableModel)e.getSource();
            int row = e.getFirstRow();
            int column = e.getColumn();
            int valeur = (int)table.getValueAt(row,column);
        
            int tailleAuCarree = table.getSudokuGrille().getTaille()*table.getSudokuGrille().getTaille();
            table.getSudokuGrille().setValeurCase(row*tailleAuCarree+column, valeur);
            System.out.println("\nGrille après modification");
            table.getSudokuGrille().showGrille();
            
        } catch (ClassCastException f){
            System.out.println(f.getMessage());
        }
         
    }
    
    public int toInteger(Object obj){
        
        String sobj = obj.toString();
        System.out.println("Yeees");
        return Integer.parseInt(sobj); 
        
    }
}
