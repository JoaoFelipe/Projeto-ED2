package gd.controllers;

import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Registro;
import gd.views.tabelaer.TabelaPrincipal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class InserirRegistroCommand extends Command{

    JButton cancelar;
    TabelaPrincipal tabela;
    
    public InserirRegistroCommand(JButton cancelar, TabelaPrincipal tabela) {
        this.cancelar = cancelar;
        this.tabela = tabela;
    }

    @Override
    public Command execute(Object... arg) {
        Entidade entidade = (Entidade)ListaER.getSelecionado();
        
        
        List lista = new ArrayList();
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            lista.add(tabela.getModel().getValueAt(tabela.getRowCount()-1, i));
        }
        Registro registro = new Registro(entidade, lista);
        Arquivo arquivo = new Arquivo(entidade);
        try {
            arquivo.abrir();
            arquivo.insere(registro);
            arquivo.fechar();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao abrir arquivo");
        }
        
        cancelar.setVisible(false);
        return new InserirCommand(cancelar, tabela);
        
    }
    
}

