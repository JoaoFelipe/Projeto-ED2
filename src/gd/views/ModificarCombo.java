package gd.views;

import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.atributos.ColecaoAtributo;
import gd.views.base.ComboBoxNaoEditavel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class ModificarCombo extends ComboBoxNaoEditavel {

    private DefaultComboBoxModel modelo = null;

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

    public DefaultComboBoxModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultComboBoxModel modelo) {
        this.modelo = modelo;
    }

}
