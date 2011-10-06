/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.views.criartabela.*;
import gd.controllers.MensagemErroCommand;
import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import gd.models.atributos.ColecaoAtributo;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class EntidadeTabela extends JTable {

    DefaultTableModel modelo = null;
    public static EntidadeTabela instancia = null;
    EntidadeRelacionamento er = null;
    JPanel painel = null;

    private EntidadeTabela(JPanel painel) {
        super();
        this.painel = painel;
        this.setModel(Arrays.asList("Escolha uma Tabela"));
        this.setEnabled(false);
        this.painel.setVisible(false);
    }

    public static EntidadeTabela getInstancia(){
        return instancia;
    }

    public static EntidadeTabela instanciar(JPanel painel){
        if (instancia == null){
            instancia = new EntidadeTabela(painel);
        }
        return instancia;
    }

    public void setModel(List<String> param){
        modelo =  new DefaultTableModel(new Object[][]{}, param.toArray());
        super.setModel(modelo);
    }

    public void setEntidadeRelacionamento(EntidadeRelacionamento er) {
        this.er = er;
    }

    public JPanel getPainel() {
        return painel;
    }

    public EntidadeRelacionamento getEr() {
        return er;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

}
