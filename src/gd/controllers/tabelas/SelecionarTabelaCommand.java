/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.controllers.tabelas;

import gd.controllers.Command;
import gd.exceptions.ModelException;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;
import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.models.ER.Relacionamento;
import gd.models.atributos.ColecaoAtributo;
import gd.views.base.Lista;
import gd.views.base.ModeloLista;
import gd.views.tabelaer.TabelaPrincipal;
import gd.views.ListaTabelas;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class SelecionarTabelaCommand extends Command {

    static boolean using = false; // só permitir executar 1 vez

    public SelecionarTabelaCommand() {
        super();
    }

    @Override
    public void execute(Object... arg) {
       

    }

   
}
