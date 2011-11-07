package dm.views;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AttributeTable extends JTable {

    public AttributeTable() {
        super();
        DefaultTableModel model = new javax.swing.table.DefaultTableModel (
            new Object [][] {

            },
            new String [] {
                "Nome", "Tipo", "Chave Primária"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        this.setModel(model);
    }


}
