package gd.models.arquivo;

import gd.models.ER.Entidade;
import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Registro {

    private Entidade entidade;
    private List<Valor> valores;
    private int estado; // 0: não usado; 1: usado; 2:apagado
    private Valor pk;

    public Registro(Entidade entidade, int estado, List valoresAtributos) {
        this.entidade = entidade;
        this.estado = estado;
        this.valores = new ArrayList<Valor>();
        List<Atributo> atributos = entidade.getAtributos();
        for (int i= 0; i < atributos.size(); i++) {
            Atributo atributo = atributos.get(i);
            Valor valor = new Valor(atributo, valoresAtributos.get(i));
            if (atributo.getPK()){
                pk = valor;
            }
            this.valores.add(valor);
        }
    }

    public Registro(Entidade entidade, List valoresAtributos) {
        this(entidade, 1, valoresAtributos);
    }

    public Registro(Entidade entidade, int estado) {
        this.estado = estado;
        this.entidade = entidade;
        this.valores = new ArrayList<Valor>();

        for (Atributo atributo : entidade.getAtributos()) {
            Valor valor = atributo.getDefault();
            if (atributo.getPK()){
                pk = valor;
            }
            this.valores.add(valor);
        }
    }

    public Registro(Entidade entidade) {
        this(entidade, 0);
    }

    public Registro(Entidade entidade, RandomAccessFile in) throws IOException {
        this.entidade = entidade;
        this.valores = new ArrayList<Valor>();
        this.estado = in.readInt();
        for (Atributo atributo : entidade.getAtributos()) {
            Valor valor = atributo.ler(in);
            if (atributo.getPK()){
                pk = valor;
            }
            this.valores.add(valor);
        }
    }

    public void grava(RandomAccessFile out) throws IOException {
        out.writeInt(getEstado());
        for (Valor valor : getValores()) {
            valor.grava(out);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Registro other = (Registro) obj;
        if (this.getEntidade() == null || !this.entidade.equals(other.entidade)) {
            return false;
        }
        if (this.getValores() == null || !this.valores.equals(other.valores)) {
            return false;
        }
        if (this.getEstado() != other.getEstado()) {
            return false;
        }
        return true;
    }

    public boolean isVazio(){
        return (getEstado() == 0);
    }

    public boolean isUsado(){
        return (getEstado() == 1);
    }

    public boolean isRemovido(){
        return (getEstado() == 2);
    }

    public Valor getPk(){
        return this.pk;
    }
    
    public void setPk(Valor pk) {
        this.pk = pk;
    }


    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public List<Valor> getValores() {
        return valores;
    }

    public void setValores(List<Valor> valores) {
        this.setValores(valores);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public Valor getValorByAttrName(String nome) {
        for (Valor valor : this.getValores()) {
            if (valor.getTipo().getNome().equals(nome)) {
                return valor;
            }

        }
        return null;
    }
    
     public Object[] getRow(){
         Object[] row = new Object[this.getValores().size()]; 
         for (int i = 0; i < this.getValores().size(); i++) {
             row[i] = this.getValores().get(i).getInfo();
             
         }
         return row;
    }
}
