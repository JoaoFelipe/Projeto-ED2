package gd.controllers;

import gd.exceptions.ModelException;
import gd.models.ColecaoComboModel;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import gd.models.atributos.ColecaoAtributo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableModel;

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

    public static void criarTabela(TableModel modelo, String nome) throws ModelException {
        List<Atributo> lista = new ArrayList<Atributo>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            lista.add(Atributo.criarAtributo(
                (String) modelo.getValueAt(i, 0),
                (String) modelo.getValueAt(i, 1),
                (Boolean) modelo.getValueAt(i, 2)
            ));
        }
        ListaER inst = ListaER.getInstancia();
        inst.add(new Entidade(nome, lista));
        inst.grava();
    }

    public static void criarReferencia(String tabNome, String attr, String refNome, String cod) throws ModelException {
        ListaER inst = ListaER.getInstancia();
        Entidade tab = (Entidade) inst.buscar(tabNome);
        Entidade ref = (Entidade) inst.buscar(refNome);

        inst.add(new Relacionamento(tab, attr, ref, cod));
        inst.grava();
    }

    public static void entidadesToComboBoxModel(DefaultComboBoxModel modelo){
        try {
            ListaER inst = ListaER.getInstancia();
            ColecaoComboModel.converter(
                inst.getNomesTabelas(),
                modelo
            );
        } catch (ModelException ex) {
            ex.execute();
        }
    }

    public static void atributosToComboBoxModel(String nomeEntidade, DefaultComboBoxModel modelo) {
        try {
            ListaER inst = ListaER.getInstancia();
            Entidade entidade = (Entidade) inst.buscar(nomeEntidade);
            ColecaoComboModel.processar(
                entidade.getAtributos(),
                ColecaoAtributo.processos.getNome(),
                modelo
            );
        } catch (ModelException ex) {
            ex.execute();
        }
    }
    
}
