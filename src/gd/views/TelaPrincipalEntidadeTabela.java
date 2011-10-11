/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.MensagemErroCommand;
import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import gd.models.atributos.ColecaoAtributo;
import gd.views.base.ModeloTabela;
import java.awt.Component;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Joao
 */
public class TelaPrincipalEntidadeTabela extends JTable {

    DefaultTableModel modelo = null;
    public static TelaPrincipalEntidadeTabela instancia = null;
    EntidadeRelacionamento er = null;
    JPanel painel = null;

    private TelaPrincipalEntidadeTabela(JPanel painel) {
        super();
        this.painel = painel;
        this.setModel(Arrays.asList("Escolha uma Tabela"), null, false);
       // this.cellEditor =

        this.painel.setVisible(false);
    }

    public static TelaPrincipalEntidadeTabela getInstancia(){
        return instancia;
    }

    public static TelaPrincipalEntidadeTabela instanciar(JPanel painel){
        if (instancia == null){
            instancia = new TelaPrincipalEntidadeTabela(painel);
        }
        return instancia;
    }

    public void setModel(List<String> param, List<Class> classes, boolean editavel){
        modelo =  new ModeloTabela(param.toArray(), classes, editavel);
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
