package gd.controllers;

import gd.models.arquivo.Search;
import gd.views.MainWindow;
import gd.views.tabelaer.MainTable;
import java.io.IOException;
import javax.swing.JOptionPane;

public class SearchCommand extends AbstractSearchCommand{

    private MainTable table;
    
    public SearchCommand(Search search, MainTable table) {
        super(search);
        this.table = table;
    }

    @Override
    public Command execute() {
        try {
            TableController.tupleToTableModel(getSearch(), table.getModel());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
        return this;
    }
    
    
}
