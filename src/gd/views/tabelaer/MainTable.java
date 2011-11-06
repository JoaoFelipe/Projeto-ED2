package gd.views.tabelaer;

import gd.models.ER.Entity;
import gd.models.ER.EntityRelationship;
import gd.views.base.tableModel;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainTable extends JTable {

    //Padrão Singleton
    //Padrão State: o comportamento da tabela depende do seu estado, ou seja, 
    //depende se está selecionado uma entidade, um relacionamento ou se nada está 
    //selecionado
    
    private tableModel model = null;
    private static MainTable instance = null;
    private JPanel panel = null;

    private TableState state = null;

    public MainTable(JPanel panel) {
        super();
        this.panel = panel;
        state = new EmptyState(this);
        state.show();
    }

    public static MainTable getInstance(){
        return instance;
    }
    
    public static MainTable instantiate(JPanel painel){
        if (getInstance() == null){
            instance = new MainTable(painel);
        }
        return getInstance();
    }

    @Override
    public tableModel getModel() {
        return model;
    }
    
    public void setModel(List<String> param, List<Class> classes, boolean editable){
        model = new tableModel(param.toArray(), classes, editable);
        super.setModel(model);
    }

    public void setState(EntityRelationship er) {
        if (er == null){
            setState(new EmptyState(this));
        } else if (er instanceof Entity) {
            setState(new EntityState(this, er));
        } else {
            setState(new RelationState(this, er));
        }
        getState().show();
    }


    public JPanel getPanel() {
        return panel;
    }
    
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void addRow(Object[] rowData) {
        this.getModel().addRow(rowData);
        this.setEditingRow(this.getRowCount()-1);
    }
    
    public void removeRow() {
        this.getModel().removeRow(this.getRowCount()-1);
        this.getModel().setEditable(false);
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

}
