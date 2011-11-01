package gd.views;

import gd.models.ER.Entidade;
import gd.views.base.ComboBoxEditor;
import gd.views.base.ComboBoxRenderer;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ConsultarTabela extends JTable {

    private DefaultTableModel modelo = null;
    private Entidade entidade = null;

    public ConsultarTabela(Entidade entidade) {
        super();
        modelo = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Atributo", "Condição", "Valor" }
        );
        this.entidade = entidade;

        this.setModel(modelo);
        List<String> lista = entidade.getAtributosBuscaveis();
        TableColumn col = this.getColumnModel().getColumn(0);
        col.setCellEditor(new ComboBoxEditor(lista));
        col.setCellRenderer(new ComboBoxRenderer(lista));

        lista = Arrays.asList("=", "!=", ">", ">=", "<", "<=", "LIKE", "REGEX");
        col = this.getColumnModel().getColumn(1);
        col.setCellEditor(new ComboBoxEditor(lista));
        col.setCellRenderer(new ComboBoxRenderer(lista));
    }

    public void removeSelectedRow(){
        int i = this.convertRowIndexToModel(this.getSelectedRow());
        if (i >= 0 && i < getModelo().getRowCount())
            getModelo().removeRow(i);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

}
