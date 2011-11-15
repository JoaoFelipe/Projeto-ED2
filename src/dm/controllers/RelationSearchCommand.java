package dm.controllers;

import dm.models.HashFile;
import dm.models.Search;
import dm.views.MainWindow;
import dm.views.maintable.MainTable;
import java.io.IOException;
import javax.swing.JOptionPane;

public class RelationSearchCommand extends AbstractSearchCommand {

    private MainTable table;
    private int column;
        
    public RelationSearchCommand(Search search, int col, MainTable table) {
        super(search);
        this.table = table;
        this.column = col;
    }

    @Override
    public Command execute() {
        if (getSearch().getPKs().size() > 0) {
            this.table.getModel().setValueAt(getSearch().getPKs().get(0).getInfo(), this.table.getModel().getRowCount()-1, column);

        } else {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Nenhum registro foi encontrado!");
        }
        return this;
    }
}
