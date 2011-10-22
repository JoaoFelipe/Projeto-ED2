package gd.models.atributos;

import gd.models.arquivo.Valor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return new Valor<String>(this, in.readUTF().trim());
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


    private static String passaParaExpressaoRegular(String input) {
        input = input.replace("+", "\\+");
        input = input.replace("(", "\\(");
        input = input.replace(")", "\\)");
        input = input.replace(".", "\\.");
        input = input.replace("[", "\\[");
        input = input.replace("]", "\\]");
        input = input.replace("{", "\\{");
        input = input.replace("}", "\\}");
        input = input.replace(",", "\\,");
        input = input.replace("|", "\\|");
        input = input.replace("^", "\\^");
        input = input.replace("&", "\\&");
        input = input.replace("?", "\\?");
        input = input.replace("*", "\\*");
        input = input.replace("\\_", "\n");
        input = input.replace("_", ".");
        input = input.replace("\n", "\\_");
        input = input.replace("\\%", "\n"); 
        input = input.replace("%", ".*");
        input = input.replace("\n", "\\%");  
//        input = input.replace("\\", "\\\\"); //"te\\\\%ste" - 'te\\%ste'
        return input;
    }

    public boolean buscaLike(Valor valor, String condicao){
        condicao = passaParaExpressaoRegular(condicao);
        Pattern p = Pattern.compile(condicao, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher((CharSequence) valor.getInfo());
        return m.matches();
    }

    public boolean buscaRegex(Valor valor, String condicao){
        Pattern p = Pattern.compile(condicao, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher((CharSequence) valor.getInfo());
        return m.matches();
    }

    public boolean buscaEqual(Valor valor, String condicao){
        return ((String) valor.getInfo()).compareTo(condicao) == 0;
    }

    public boolean buscaLT(Valor valor, String condicao){
        return ((String) valor.getInfo()).compareTo(condicao) < 0;
    }

    public boolean buscaLE(Valor valor, String condicao){
        return ((String) valor.getInfo()).compareTo(condicao) <= 0;
    }

    public boolean buscaGT(Valor valor, String condicao){
        return ((String) valor.getInfo()).compareTo(condicao) > 0;
    }

    public boolean buscaGE(Valor valor, String condicao){
        return ((String) valor.getInfo()).compareTo(condicao) >= 0;
    }

    @Override
    public boolean compara(String operador, Valor valor, Object condicao) {
        if (operador.equals("LIKE"))
            return buscaLike(valor, (String)condicao);
        else if(operador.equals("REGEX"))
            return buscaRegex(valor, (String)condicao);
        else if(operador.equals("="))
            return buscaEqual(valor, (String)condicao);
        else if(operador.equals("!="))
            return !buscaEqual(valor, (String)condicao);
        else if(operador.equals(">"))
            return buscaGT(valor, (String)condicao);
        else if(operador.equals("<"))
            return buscaLT(valor, (String)condicao);
        else if(operador.equals(">="))
            return buscaGE(valor, (String)condicao);
        else if(operador.equals("<="))
            return buscaLE(valor, (String)condicao);
        else
            return true;
    }

    @Override
    public List<String> comparadores() {
        return Arrays.asList("LIKE", "REGEX", "=", "!=", ">", "<", ">=", "<=");
    }

}
