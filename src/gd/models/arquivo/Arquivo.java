/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.models.arquivo;

import gd.models.ER.Entidade;
import gd.models.ER.Relacionamento;
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
    String prefix = "";

    public int hash(Valor valor, int passo) {
        if (passo == 0) {
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

    public void abrir(String prefix) throws IOException {
        this.prefix = prefix;
        File arquivo = new File(prefix + entidade.getNome() + ".dat");
        this.arq = new RandomAccessFile(arquivo, "rw");
        if (arq.length() == 0) {
            this.tamanho = 8;
        } else {
            this.tamanho = (int) (arq.length() / tamanhoRegistro);
        }
        this.contarRegistros();
    }

    public void abrir() throws IOException {
        this.abrir("");
    }

    public void fechar() throws IOException {
        this.arq.close();
    }

    public Resultado busca(Valor valor) throws IOException {
        Resultado retorno = null;
        int endereco;
        int passo = 0;
        int removido = -1;
        Resultado resultado = null;
        endereco = hash(valor, passo);
        while (retorno == null) {
            arq.seek(endereco * tamanhoRegistro);
            Registro r = new Registro(entidade, arq);
            if (r.isVazio()) {
                retorno = resultado != null ? resultado : new Resultado(removido == -1 ? endereco : removido, false);
            }
            if (r.getPk().equals(valor)) {
                if (resultado == null) {
                    resultado = new Resultado(endereco, !r.isRemovido());
                } else if (!r.isRemovido() && !resultado.isEncontrado()){
                    resultado = new Resultado(endereco, true);
                }
            }
            if (r.isRemovido() && removido == -1) {
                removido = endereco;
            }
            passo += 1;
            endereco = hash(valor, passo);
            if (passo >= tamanho) {
                retorno = resultado != null ? resultado : new Resultado(removido, false);
//                retorno = resultado != null? resultado : new Resultado(-1, false);
            }
        }

        return retorno;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean insere(Registro registro) throws IOException {

        boolean retorno = true;

        Resultado resultado = busca(registro.pk);

        arq.seek(resultado.getPosicao() * tamanhoRegistro);

        Registro atual = new Registro(entidade, arq);

        if (!resultado.isEncontrado() && !atual.isUsado() && existeReferencia(atual)) {

            arq.seek(resultado.getPosicao() * tamanhoRegistro);
            registro.grava(arq);
            entidade.setNumeroRegistros(entidade.getNumeroRegistros() + 1);
        } else {
            retorno = false;
        }

        if ((entidade.getNumeroRegistros()) * 3 > tamanho * 2) {
            reorganizar();
        }


        return retorno;
    }

    public boolean existeReferencia(Registro atual) throws IOException {
        boolean retorno = true;
        for (Relacionamento relacionamento : entidade.getRelacionamentos()) {
            if (relacionamento.getEntidade() == entidade) {
                for (Valor valor : atual.valores) {
                    if (valor.getTipo().getNome().equals(relacionamento.getCampo())) {
                        Arquivo temp = new Arquivo(relacionamento.getEntidadeReferenciada());
                        temp.abrir(prefix);
                        retorno = retorno & temp.busca(valor).isEncontrado();
                        temp.fechar();

                    }

                }
            }
        }
        return retorno;

    }

    public void contarRegistros() throws IOException {
        int contador = 0;
        arq.seek(0);
        while (arq.getFilePointer() < arq.length()) {
            Registro r = new Registro(entidade, arq);
            if (r.isUsado()) {
                contador++;
            }
        }
        entidade.setNumeroRegistros(contador);
    }

    public void criar() throws IOException {
        Registro r = new Registro(entidade);
        for (int i = 0; i < tamanho; i++) {
            r.grava(arq);
        }
    }

    public void reorganizar() throws IOException {
        int numero = entidade.getNumeroRegistros();

        File oldFile = new File(prefix + entidade.getNome() + ".dat");
        File tempFile = new File(prefix + "temp" + entidade.getNome() + ".dat");
        tempFile.delete();

        Arquivo temp = new Arquivo(entidade);
        temp.abrir(prefix + "temp");
        temp.setTamanho(tamanho * 2);
        temp.criar();
        for (int i = 0; i < tamanho; i++) {
            arq.seek(i * tamanhoRegistro);
            Registro r = new Registro(entidade, arq);
            if (r.isUsado()) {
                temp.insere(r);
            }
        }
        temp.fechar();
        this.fechar();

        oldFile.delete();
        tempFile.renameTo(oldFile);
        this.arq = new RandomAccessFile(oldFile, "rw");

        this.setTamanho(tamanho * 2);
        entidade.setNumeroRegistros(numero);
    }
}
