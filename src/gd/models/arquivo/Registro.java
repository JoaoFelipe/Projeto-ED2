/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

import gd.models.ER.Entidade;
import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class Registro {

    Entidade entidade;
    List<Valor> valores;
    int estado; // 0: não usado; 1: usado; 2:apagado
    Valor pk;


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
        out.writeInt(estado);
        for (Valor valor : valores) {
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
        if (this.entidade == null || !this.entidade.equals(other.entidade)) {
            return false;
        }
        if (this.valores == null || !this.valores.equals(other.valores)) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        return true;
    }

    public boolean isVazio(){
        return (estado == 0);
    }

    public boolean isRemovido(){
        return (estado == 2);
    }

    public Valor getPk(){
        return this.pk;
    }


}
