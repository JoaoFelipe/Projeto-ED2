/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.atributos.Atributo;
import gd.models.atributos.ColecaoAtributo;
import gd.views.base.ComboBoxEditavel;
import gd.views.base.ComboBoxNaoEditavel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Joao
 */
public class ModificarCombo extends ComboBoxNaoEditavel {

    DefaultComboBoxModel modelo = null;

    public ModificarCombo(Entidade entidade) {
        super();
        modelo = new DefaultComboBoxModel();
        this.setModel(modelo);
        modelo.removeAllElements();
        List<String> temp = (List<String>) Colecao.processar(entidade.getAtributos(), ColecaoAtributo.processos.getNome());
        for (int i = 0; i < temp.size(); i++) {
            modelo.insertElementAt(temp.get(i), i);
        }
    }


}
