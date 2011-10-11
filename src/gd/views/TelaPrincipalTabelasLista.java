/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.Command;
import gd.controllers.tabela.AbreConsultarRegistrosCommand;
import gd.controllers.tabela.AbrirInserirRegistroCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.SelecionarTabelaCommand;
import gd.exceptions.ModelException;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;
import gd.models.ER.ListaER;
import gd.views.base.Lista;
import gd.views.base.ModeloLista;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Joao
 */
public class TelaPrincipalTabelasLista extends Lista {

    public static TelaPrincipalTabelasLista instancia = null;

    Command command = null;
    ModeloLista modelo = null;

    private TelaPrincipalTabelasLista() {
        super();
        modelo = new ModeloLista();
        this.setModel(modelo);
        command = new SelecionarTabelaCommand();
        this.atualizar();
    }

    public static TelaPrincipalTabelasLista getInstancia(){
        if (instancia == null){
            instancia = new TelaPrincipalTabelasLista();
        }
        return instancia;
    }

    public void atualizar(){
        try {
            modelo.clear();
            List<String> lista = ListaER.getInstancia().getNomes();
            for (int i = 0; i < lista.size(); i++) {
                modelo.add(i, lista.get(i));
            }
        } catch (ModelException ex) {
            ex.execute();
        }


    }

    public void valueChanged(ListSelectionEvent e) {
        command.execute(this);
    }

}
