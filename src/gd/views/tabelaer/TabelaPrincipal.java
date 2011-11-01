package gd.views.tabelaer;

import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.views.base.ModeloTabela;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabelaPrincipal extends JTable {

    private ModeloTabela modelo = null;
    private static TabelaPrincipal instancia = null;
    private JPanel painel = null;

    private EstadoTabela estado = null;

    public TabelaPrincipal(JPanel painel) {
        super();
        this.painel = painel;
        estado = new EstadoVazio(this);
        estado.show();
    }

    public static TabelaPrincipal getInstancia(){
        return instancia;
    }
    
    public static void setInstancia(TabelaPrincipal aInstancia) {
        instancia = aInstancia;
    }

    public static TabelaPrincipal instanciar(JPanel painel){
        if (getInstancia() == null){
            setInstancia(new TabelaPrincipal(painel));
        }
        return getInstancia();
    }
    
    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(List<String> param, List<Class> classes, boolean editavel){
        this.modelo = new ModeloTabela(param.toArray(), classes, editavel);
        super.setModel(getModelo());
    }

    public void setEstado(EntidadeRelacionamento er) {
        if (er == null){
            setEstado(new EstadoVazio(this));
        } else if (er instanceof Entidade) {
            setEstado(new EstadoEntidade(this, er));
        } else {
            setEstado(new EstadoRelacionamento(this, er));
        }
        getEstado().show();
    }


    public JPanel getPainel() {
        return painel;
    }
    
    public void setPainel(JPanel painel) {
        this.painel = painel;
    }

    public void addRow(Object[] rowData) {
        this.getModelo().addRow(rowData);
        this.setEditingRow(this.getRowCount()-1);
    }
    
    public void removeRow() {
        this.getModelo().removeRow(this.getRowCount()-1);
        modelo.setEditavel(false);
    }

    public EstadoTabela getEstado() {
        return estado;
    }

    public void setEstado(EstadoTabela estado) {
        this.estado = estado;
    }

}
