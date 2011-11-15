package dm.controllers;

import dm.DataManager;
import dm.views.MainWindow;
import dm.views.maintable.MainTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.jdesktop.application.ResourceMap;

public class InsertCommand implements Command{

    private JButton cancel;
    private MainTable table;
    private JLabel dica;
    
    public InsertCommand(JButton cancel, JLabel dica, MainTable table) {
        this.cancel = cancel;
        this.table = table;
        this.dica = dica;
    }

    @Override
    public Command execute() {
        cancel.setVisible(true);
        dica.setText("Dica: Para encontrar um codigo de um relacionamento clique com o botão direito na coluna da FK"); 
        table.addRow(new Object[]{});
        return new InsertTupleCommand(cancel, dica, table);
    }
    
}
