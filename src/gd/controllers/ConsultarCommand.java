package gd.controllers;

import gd.models.arquivo.Consulta;
import gd.views.tabelaer.TabelaPrincipal;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultarCommand extends ConsultaCommand{

    TabelaPrincipal tabela;
    
    public ConsultarCommand(Consulta consulta, TabelaPrincipal tabela) {
        super(consulta);
        this.tabela = tabela;
    }

    @Override
    public Command execute(Object... arg) {
        try {
            TabelasController.registrosToTabelaModel(consulta, tabela.getModelo());
        } catch (IOException ex) {
            Logger.getLogger(ConsultarCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    
}
