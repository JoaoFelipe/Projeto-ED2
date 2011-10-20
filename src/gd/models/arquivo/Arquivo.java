/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

import gd.models.ER.Entidade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Joao
 */
public class Arquivo {

    Entidade entidade;
    private int tamanho;
    RandomAccessFile arq;

    int tamanhoRegistro;

    public int hash(Valor valor, int passo){
        if (passo == 0){
            return valor.getHash() % getTamanho();
        } else {
            return (hash(valor, passo - 1) + passo) % getTamanho();
        }
    }

    public Arquivo(Entidade entidade) {
        this.entidade = entidade;
        this.arq = null;
        this.tamanhoRegistro = entidade.getTamanho() + 4;
    }

    public void abrir(String prefix) throws IOException{
        this.arq = new RandomAccessFile(new File(prefix+entidade.getNome()+".dat"), "rw");
        if (arq.length() == 0){
            this.tamanho = 8;
        } else {
            this.tamanho = (int) (arq.length() / tamanhoRegistro);
        }
    }

    public void abrir() throws IOException{
        this.abrir("");
    }

    public void fechar() throws IOException{
        this.arq.close();
    }

    public Resultado busca(Valor valor) throws IOException{
        Resultado retorno = null;
        int endereco;
        int passo = 0;
        int removido = -1;
        endereco = hash(valor, passo);
        while (retorno == null) {
            arq.seek(endereco*tamanhoRegistro);
            Registro r = new Registro(entidade, arq);
            if (r.isVazio()) {
                retorno = new Resultado(removido == -1 ? endereco : removido, false);
            } else if (r.getPk().equals(valor)) {
                retorno = new Resultado(endereco, true);
            } else if (passo < tamanho) {
                if (r.isRemovido() && removido == -1) {
                    removido = endereco;
                }
                passo += 1;
                endereco = hash(valor, passo);
            } else {
                retorno = new Resultado(-1, false);
            }
        }

        return retorno;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }


}
