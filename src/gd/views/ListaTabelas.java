/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.views.tabelaer.TabelaPrincipal;
import gd.controllers.Command;
import gd.controllers.tabelas.SelecionarTabelaCommand;
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
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Joao
 */
public class ListaTabelas extends Lista {


    Command command = null;
    ModeloLista modelo = null;

    public ListaTabelas() {
        super();
        modelo = new ModeloLista();
        this.setModel(modelo);
        command = new SelecionarTabelaCommand();
        this.atualizar();
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

  

    public String getSelectedName(){
        int indice = this.getSelectedIndex();
        if (indice > -1) {
            return (String) this.getModel().getElementAt(indice);
        }
        return null;

    }
    
    
    public void valueChanged(ListSelectionEvent e) {
         try {
            EntidadeRelacionamento er = null;
            int i = this.getSelectedIndex();
            if (ListaER.getInstancia().getLista().size() > i && i > -1){
                er = ListaER.getInstancia().getLista().get(i);
            }
            TabelaPrincipal tabela = TabelaPrincipal.getInstancia();
            tabela.setEstado(er);
        } catch (ModelException ex) {
            ex.execute();
        }
    }



}
