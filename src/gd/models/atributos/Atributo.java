package gd.models.atributos;

import gd.models.arquivo.Valor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Atributo{

    public static Atributo criarAtributo(String nome, String tipo, Boolean pk){
        if (tipo.contains("char")){
            Pattern p = Pattern.compile("char(.+)");
            Matcher m = p.matcher(tipo);
            if (m.matches()){
                int tamanho = Integer.parseInt(m.group(1));
                return new CharAttr(nome, pk, tamanho);
            } else {
                return new CharAttr(nome, pk, 1);
            }
        } else if (tipo.equals("int")){
            return new IntAttr(nome, pk);
        } else if (tipo.equals("double")){
            return new DoubleAttr(nome, pk);
        }
        return null;
    }

    public static Atributo criarAtributo(String texto){
        Pattern p = Pattern.compile("(\\*)?(.+):(.+)");
        Matcher m = p.matcher(texto);
        if (m.matches()){
            String nome = m.group(2);
            boolean pk = (m.group(1) != null);
            if (m.group(3).equals("int")){
                return new IntAttr(nome, pk);
            } else if (m.group(3).equals("double")){
                return new DoubleAttr(nome, pk);
            } else if (m.group(3).contains("char")) {
                p = Pattern.compile("char(.+)");
                m = p.matcher(m.group(3));
                if (m.matches()){
                    int tamanho = Integer.parseInt(m.group(1));
                    return new CharAttr(nome, pk, tamanho);
                } else {
                    return new CharAttr(nome, pk, 1);
                }
            }
        }
        return null;
    }

    public static List<String> todasOpcoes() {
        return Arrays.asList(
            "int",
            "double",
            "char1",
            "char10",
            "char30",
            "char50",
            "char100"
        );
    }

    public String getRepr() {
        return (getPK() ? "*": "")+getNome()+":"+getTipo();
    }

    public abstract String getNome();
    public abstract String getTipo();
    public abstract boolean getPK();
    public abstract int getTamanho();
    public abstract Class getClasse();
    public abstract int getHash(Valor valor);
    public abstract Valor getDefault();
    public abstract Valor ler(RandomAccessFile in) throws IOException;
    public abstract void grava(RandomAccessFile out, Valor valor) throws IOException;

    public abstract boolean compara(String operador, Valor valor, Object condicao);
    public abstract List<String> comparadores();

}
