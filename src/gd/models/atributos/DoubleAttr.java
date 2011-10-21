/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.atributos;

import gd.models.arquivo.Valor;
import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

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

    public boolean buscaEqual(Valor valor, Double condicao){
        return ((Double) valor.getInfo()).compareTo(condicao) == 0;
    }

    public boolean buscaLT(Valor valor, Double condicao){
        return ((Double) valor.getInfo()).compareTo(condicao) < 0;
    }

    public boolean buscaLE(Valor valor, Double condicao){
        return ((Double) valor.getInfo()).compareTo(condicao) <= 0;
    }

    public boolean buscaGT(Valor valor, Double condicao){
        return ((Double) valor.getInfo()).compareTo(condicao) > 0;
    }

    public boolean buscaGE(Valor valor, Double condicao){
        return ((Double) valor.getInfo()).compareTo(condicao) >= 0;
    }

    @Override
    public boolean compara(String operador, Valor valor, Object condicao) {
        if(operador.equals("="))
            return buscaEqual(valor, (Double)condicao);
        else if(operador.equals("!="))
            return !buscaEqual(valor, (Double)condicao);
        else if(operador.equals(">"))
            return buscaGT(valor, (Double)condicao);
        else if(operador.equals("<"))
            return buscaLT(valor, (Double)condicao);
        else if(operador.equals(">="))
            return buscaGE(valor, (Double)condicao);
        else if(operador.equals("<="))
            return buscaLE(valor, (Double)condicao);
        else
            return true;
    }

    @Override
    public List<String> comparadores() {
        return Arrays.asList("=", "!=", ">", "<", ">=", "<=");
    }
}
