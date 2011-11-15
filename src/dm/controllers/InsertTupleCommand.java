package dm.controllers;

import dm.models.Entity;
import dm.models.ERList;
import dm.models.HashFile;
import dm.models.Tuple;
import dm.views.MainWindow;
import dm.views.maintable.MainTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class InsertTupleCommand implements Command {

    private JButton cancel;
    private MainTable table;
    private JLabel dica;

    public InsertTupleCommand(JButton cancel, JLabel dica, MainTable table) {
        this.cancel = cancel;
        this.table = table;
        this.dica = dica;
    }

    @Override
    public Command execute() {
        Entity entity = (Entity) ERList.getSelected();

        List list = new ArrayList();
        for (int i = 0; i < table.getColumnCount(); i++) {
            Object object = table.getModel().getValueAt(table.getRowCount() - 1, i);
            if (object != null) {
                list.add(object);
            } else {
                JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível inserir o registro!", "Erro!", JOptionPane.ERROR_MESSAGE);
                return this;
            }
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
        dica.setText(""); 
        return new InsertCommand(cancel, dica, table);

    }
}
