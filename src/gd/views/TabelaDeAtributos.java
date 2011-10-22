package gd.views;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabelaDeAtributos extends JTable {

    private DefaultTableModel modelo = null;

    public TabelaDeAtributos() {
        super();
        modelo = new javax.swing.table.DefaultTableModel (
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

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

}
