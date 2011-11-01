package gd.controllers;

import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Consulta;
import gd.views.tabelaer.TabelaPrincipal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import sun.org.mozilla.javascript.internal.ast.ForInLoop;

public class RemoverCommand extends ConsultaCommand {

    TabelaPrincipal tabela;

    public RemoverCommand(Consulta consulta, TabelaPrincipal tabela) {
        super(consulta);
        this.tabela = tabela;
    }

    @Override
    public Command execute(Object... arg) {
        try {
            Arquivo arquivo = consulta.getArquivo();
            
            arquivo.abrir();
            for (int i = 0; i < consulta.getCodigos().size(); i++) {
                arquivo.remove(consulta.getCodigos().get(i), 0);
            }
            arquivo.fechar();        
            TabelasController.registrosToTabelaModel(new Consulta(arquivo, null).compila(), tabela.getModelo());
        
        } catch (IOException ex) {
            Logger.getLogger(RemoverCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}
