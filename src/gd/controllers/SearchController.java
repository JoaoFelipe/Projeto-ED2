/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.controllers;

import gd.models.ER.Entity;
import gd.models.arquivo.ConsistencyStrategy;
import gd.models.arquivo.HashFile;
import gd.models.arquivo.Search;
import gd.models.arquivo.Value;
import gd.views.MainWindow;
import gd.views.SearchTable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SearchController {

    private static void searchByTable(Search search, SearchTable searchTable) throws IOException {
        Entity entity = search.getHashFile().getEntity();
        for (int i = 0; i < searchTable.getModel().getRowCount(); i++) {
            search.search(entity.findAttribute((String) searchTable.getValueAt(i, 0)), (String) searchTable.getValueAt(i, 1), searchTable.getValueAt(i, 2));
        }
        search.compile();
    }
    
    public static void performSearch(AbstractSearchCommand command, SearchTable searchTable, JDialog dialog) {
        try {
            searchByTable(command.getSearch(), searchTable);
            command.execute();
            WindowController.closeDialog(dialog);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void performChange(Search search, String attributeName, String attributeValue, SearchTable searchTable, DefaultTableModel model, JDialog dialog) {
        try {
            searchByTable(search, searchTable);
            
            HashFile hashFile = search.getHashFile();
            Entity entity = hashFile.getEntity();

            List<Value> mudancas = Arrays.asList(new Value(entity.findAttribute(attributeName), attributeValue));
            hashFile.setStrategy(ConsistencyStrategy.RESTRICT);
            hashFile.open();
            for (Value valor : search.getPKs()) {
                hashFile.modify(valor, mudancas);
            }
            hashFile.close();
            
            TableController.tupleToTableModel(new Search(hashFile, null).compile(), model);
            WindowController.closeDialog(dialog);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }
}