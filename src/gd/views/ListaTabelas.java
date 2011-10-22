package gd.views;

import gd.views.tabelaer.TabelaPrincipal;
import gd.exceptions.ModelException;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.views.base.Lista;
import gd.views.base.ModeloLista;
import java.util.List;
import javax.swing.event.ListSelectionEvent;

public class ListaTabelas extends Lista {

    public ListaTabelas() {
        super();
        this.setModel(new ModeloLista());
        this.atualizar();
    }

    public void atualizar(){
        try {
            ModeloLista modelo = (ModeloLista) this.getModel();
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
