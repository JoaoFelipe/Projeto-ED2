/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criartabela;

import gd.controllers.MensagemErroCommand;
import gd.models.atributos.Atributo;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class AtributosTabela extends JTable {

    DefaultTableModel modelo = null;

    public AtributosTabela() {
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

    public void inserirAtributo(Atributo atributo) {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(atributo.getNome())){
                (new MensagemErroCommand()).execute("Já existe um atributo com este nome!");
                return;
            }
        }
        modelo.insertRow(modelo.getRowCount(), new Object[]{atributo.getNome(), atributo.getTipo(), atributo.getPK()});
    }

    public void removerAtributo() {
        modelo.removeRow(this.convertRowIndexToModel(this.getSelectedRow()));
    }
}
