package gd.controllers;

import gd.views.tabelaer.TabelaPrincipal;
import javax.swing.JButton;

public class InserirCommand extends Command{

    JButton cancelar;
    TabelaPrincipal tabela;
    
    public InserirCommand(JButton cancelar, TabelaPrincipal tabela) {
        this.cancelar = cancelar;
        this.tabela = tabela;
    }

    @Override
    public Command execute(Object... arg) {
        cancelar.setVisible(true);
        tabela.addRow(new Object[]{});
        return new InserirRegistroCommand(cancelar, tabela);
    }
    
}
