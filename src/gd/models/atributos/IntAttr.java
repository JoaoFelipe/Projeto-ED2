/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.atributos;

import gd.models.arquivo.Valor;
import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Joao
 */
public class IntAttr extends Atributo{


    private String nome;
    private boolean pk;

    public IntAttr(String nome, boolean pk) {
        this.nome = nome;
        this.pk = pk;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public boolean getPK() {
        return pk;
    }

    @Override
    public String getTipo() {
        return "int";
    }

    @Override
    public int getTamanho() {
        return 4;
    }

    @Override
    public Class getClasse() {
        return Integer.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntAttr other = (IntAttr) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.pk != other.pk) {
            return false;
        }
        return true;
    }

    @Override
    public int getHash(Valor valor) {
        Integer i = (Integer) valor.getInfo();
        return i.hashCode();
    }

    @Override
    public Valor ler(RandomAccessFile in) throws IOException{
        return new Valor<Integer>(this, in.readInt());
    }

    @Override
    public Valor getDefault() {
        return new Valor<Integer>(this, new Integer(0));
    }

    @Override
    public void grava(RandomAccessFile in, Valor valor) throws IOException {
        in.writeInt(((Integer) valor.getInfo()).intValue());
    }

}
