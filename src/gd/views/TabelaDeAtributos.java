/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.JanelaController;
import gd.models.atributos.Atributo;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class TabelaDeAtributos extends JTable {

    DefaultTableModel modelo = null;

    public TabelaDeAtributos() {
        super();
        modelo = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Tipo", "Chave Primária"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        this.setModel(modelo);

    }

}
