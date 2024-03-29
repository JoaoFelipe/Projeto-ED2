package dm.controllers;

import dm.models.HashFile;
import dm.models.Search;
import dm.views.MainWindow;
import dm.views.maintable.MainTable;
import java.io.IOException;
import javax.swing.JOptionPane;

public class RemoveCommand extends AbstractSearchCommand {

    private MainTable table;

    public RemoveCommand(Search search, MainTable table) {
        super(search);
        this.table = table;
    }

    @Override
    public Command execute() {
        try {
            HashFile hashFile = getSearch().getHashFile();
            
            hashFile.open();
            for (int i = 0; i < getSearch().getPKs().size(); i++) {
                hashFile.remove(getSearch().getPKs().get(i));
            }
            hashFile.close();        
            TableController.tupleToTableModel(new Search(hashFile, null).compile(), table.getModel());
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
        return this;
    }
}
