/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.tabelaer;

import gd.models.ER.EntidadeRelacionamento;
import java.util.Arrays;

/**
 *
 * @author Joao
 */
public class EstadoVazio implements EstadoTabela {

    TelaPrincipalEntidadeTabela tabela = null;

    public EstadoVazio(TelaPrincipalEntidadeTabela tabela) {
        this.tabela = tabela;
    }

    public void show() {
        tabela.setModel(Arrays.asList("Selecione uma tabela"), null, false);
        tabela.getPainel().setVisible(false);
    }

    public EntidadeRelacionamento getEr() {
        return null;
    }

    
}
