package gd.models.atributos;

import gd.models.arquivo.Valor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

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
        Integer i = (Integer) (valor.getInfo());
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

    public boolean buscaEqual(Valor valor, Integer condicao){
        return ((Integer) valor.getInfo()).compareTo(condicao) == 0;
    }

    public boolean buscaLT(Valor valor, Integer condicao){
        return ((Integer) valor.getInfo()).compareTo(condicao) < 0;
    }

    public boolean buscaLE(Valor valor, Integer condicao){
        return ((Integer) valor.getInfo()).compareTo(condicao) <= 0;
    }

    public boolean buscaGT(Valor valor, Integer condicao){
        return ((Integer) valor.getInfo()).compareTo(condicao) > 0;
    }

    public boolean buscaGE(Valor valor, Integer condicao){
        return ((Integer) valor.getInfo()).compareTo(condicao) >= 0;
    }

    @Override
    public boolean compara(String operador, Valor valor, Object condicao) {
        if(operador.equals("="))
            return buscaEqual(valor, (Integer)condicao);
        else if(operador.equals("!="))
            return !buscaEqual(valor, (Integer)condicao);
        else if(operador.equals(">"))
            return buscaGT(valor, (Integer)condicao);
        else if(operador.equals("<"))
            return buscaLT(valor, (Integer)condicao);
        else if(operador.equals(">="))
            return buscaGE(valor, (Integer)condicao);
        else if(operador.equals("<="))
            return buscaLE(valor, (Integer)condicao);
        else
            return true;
    }

    @Override
    public List<String> comparadores() {
        return Arrays.asList("=", "!=", ">", "<", ">=", "<=");
    }
    
    @Override
    public Object cast(Object valor) {
        if (valor instanceof Integer){
            return valor;
        } else {
            return Integer.parseInt(valor+"");
        }
    }
}
