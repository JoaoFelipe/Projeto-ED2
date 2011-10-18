/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.tabelaer;

import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Relacionamento;
import gd.models.atributos.ColecaoAtributo;
import gd.views.base.ModeloTabela;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class TelaPrincipalEntidadeTabela extends JTable {

    DefaultTableModel modelo = null;
    public static TelaPrincipalEntidadeTabela instancia = null;
    JPanel painel = null;

    EstadoTabela estado = null;

    public TelaPrincipalEntidadeTabela(JPanel painel) {
        super();
        this.painel = painel;
        estado = new EstadoVazio(this);
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

    public void setEstado(EntidadeRelacionamento er) {
        if (er == null){
            estado = new EstadoVazio(this);
        } else if (er instanceof Entidade) {
            estado = new EstadoEntidade(this, er);
        } else {
            estado = new EstadoRelacionamento(this, er);
        }
        estado.show();
    }


    public EntidadeRelacionamento getEr() {
        return estado.getEr();
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JPanel getPainel() {
        return painel;
    }

  


}
