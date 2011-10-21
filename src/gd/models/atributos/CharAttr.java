package gd.models.atributos;

import gd.models.arquivo.Valor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CharAttr extends Atributo{

    private String nome;
    private boolean pk;
    private int tamanho;

    final int TAMANHOSTR = 2;

    public CharAttr(String nome, boolean pk, int tamanho) {
        this.nome = nome;
        this.pk = pk;
        this.tamanho = tamanho;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getTipo() {
        return "char"+tamanho;
    }

    @Override
    public boolean getPK() {
        return pk;
    }

    @Override
    public int getTamanho() {
        return tamanho+TAMANHOSTR;
    }

    @Override
    public Class getClasse() {
        return String.class;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CharAttr other = (CharAttr) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.pk != other.pk) {
            return false;
        }
        if (this.tamanho != other.tamanho) {
            return false;
        }
        return true;
    }

    @Override
    public int getHash(Valor valor) {
        String v = (String) valor.getInfo();
        return v.hashCode();
    }

    @Override
    public Valor ler(RandomAccessFile in) throws IOException{
        return new Valor<String>(this, in.readUTF());
    }

    @Override
    public Valor getDefault() {
        return new Valor<String>(this, "");
    }

    @Override
    public void grava(RandomAccessFile in, Valor valor) throws IOException {
        String temp = (String) valor.getInfo();
        for (int i = temp.length(); i < this.getTamanho()-TAMANHOSTR; i++) {
            temp += " ";
        }
        in.writeUTF(temp);
    }



 

}
