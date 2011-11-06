package gd.controllers;

import gd.models.ER.Entity;
import gd.models.ER.ERList;
import gd.models.arquivo.HashFile;
import gd.models.arquivo.Tuple;
import gd.views.MainWindow;
import gd.views.tabelaer.MainTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class InsertTupleCommand implements Command {

    private JButton cancel;
    private MainTable table;

    public InsertTupleCommand(JButton cancel, MainTable table) {
        this.cancel = cancel;
        this.table = table;
    }

    @Override
    public Command execute() {
        Entity entity = (Entity) ERList.getSelected();

        List list = new ArrayList();
        for (int i = 0; i < table.getColumnCount(); i++) {
            list.add(table.getModel().getValueAt(table.getRowCount() - 1, i));
        }
        Tuple tuple = new Tuple(entity, list);
        HashFile hashFile = new HashFile(entity);
        try {
            hashFile.open();
            if (!hashFile.insert(tuple)) {
                JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível inserir o registro!", "Erro!", JOptionPane.ERROR_MESSAGE);
                hashFile.close();
                return this;
            }
            hashFile.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }

        cancel.setVisible(false);
        return new InsertCommand(cancel, table);

    }
}
