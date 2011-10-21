package gd.views;

import gd.models.atributos.Atributo;
import gd.views.base.ComboBoxEditavel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class ComboDeAtributos extends ComboBoxEditavel {

    private DefaultComboBoxModel modelo = null;

    public ComboDeAtributos() {
        super();
        modelo = new DefaultComboBoxModel();
        this.setModel(modelo);
        listarAtributos();
    }

    public void listarAtributos() {
        getModelo().removeAllElements();
        List<String> temp = Atributo.todasOpcoes();
        for (int i = 0; i < temp.size(); i++) {
            getModelo().insertElementAt(temp.get(i), i);
        }
    }

    public DefaultComboBoxModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultComboBoxModel modelo) {
        this.modelo = modelo;
    }

}
