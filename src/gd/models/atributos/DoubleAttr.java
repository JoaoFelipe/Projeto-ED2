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
public class DoubleAttr extends Atributo{


    private String nome;
    private boolean pk;

    public DoubleAttr(String nome, boolean pk) {
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
        return "double";
    }

    @Override
    public int getTamanho() {
        return 8;
    }

    @Override
    public Class getClasse() {
        return Double.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleAttr other = (DoubleAttr) obj;
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
        Double d = (Double) valor.getInfo();
        return d.hashCode();
    }

    @Override
    public Valor ler(RandomAccessFile in) throws IOException{
        return new Valor<Double>(this, in.readDouble());
    }

    @Override
    public Valor getDefault() {
        return new Valor<Double>(this, new Double(0));
    }

    @Override
    public void grava(RandomAccessFile in, Valor valor) throws IOException {
        in.writeDouble(((Double) valor.getInfo()).doubleValue());
    }

}
