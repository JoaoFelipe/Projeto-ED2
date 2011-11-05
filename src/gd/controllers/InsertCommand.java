package gd.controllers;

import gd.views.tabelaer.MainTable;
import javax.swing.JButton;

public class InsertCommand implements Command{

    private JButton cancel;
    private MainTable table;
    
    public InsertCommand(JButton cancel, MainTable table) {
        this.cancel = cancel;
        this.table = table;
    }

    @Override
    public Command execute() {
        cancel.setVisible(true);
        table.addRow(new Object[]{});
        return new InsertTupleCommand(cancel, table);
    }
    
}
