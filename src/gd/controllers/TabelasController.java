/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.exceptions.ModelException;
import gd.models.ER.ListaER;
import gd.views.ListaTabelas;

/**
 *
 * @author Joao
 */
public class TabelasController {

    public static void excluirTabela(String tabela) {
        if (tabela != null) {
            try {
                ListaER inst = ListaER.getInstancia();
                inst.removePorNome(tabela);
                inst.grava();
            } catch (ModelException ex) {
                ex.execute();
            }
        }
    }

}
