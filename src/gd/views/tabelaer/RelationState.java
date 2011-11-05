package gd.views.tabelaer;

import gd.models.ER.EntityRelationship;
import gd.models.ER.Relation;
import java.util.Arrays;

public class RelationState implements TableState {

    private Relation relation = null;
    private MainTable table = null;

    public RelationState(MainTable table, EntityRelationship relation) {
        this.table = table;
        this.relation = (Relation) relation;
    }

    public void show() {
        getTable().setModel(Arrays.asList("Tabela", "Atributo", "Tabela Referenciada", "Código"), null, false);
        getTable().getModel().addRow(getRelation().getRow());
        getTable().getPanel().setVisible(false);
    }


    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public MainTable getTable() {
        return table;
    }

    public void setTable(MainTable table) {
        this.table = table;
    }

}
