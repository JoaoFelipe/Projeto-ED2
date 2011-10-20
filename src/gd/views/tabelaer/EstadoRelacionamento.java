/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.tabelaer;

import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Relacionamento;
import java.util.Arrays;

/**
 *
 * @author Joao
 */
public class EstadoRelacionamento implements EstadoTabela {

    Relacionamento relacionamento = null;
    TabelaPrincipal tabela = null;


    public EstadoRelacionamento(TabelaPrincipal tabela, EntidadeRelacionamento relacionamento) {
        this.tabela = tabela;
        this.relacionamento = (Relacionamento) relacionamento;
    }

    public void show() {
        tabela.setModel(Arrays.asList("Tabela", "Atributo", "Tabela Referenciada", "Código"), null, false);
        tabela.getModelo().addRow(relacionamento.getRow());
        tabela.getPainel().setVisible(false);
    }

    public EntidadeRelacionamento getEr() {
        return relacionamento;
    }



}
