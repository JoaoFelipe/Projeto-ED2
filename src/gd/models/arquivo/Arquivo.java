package gd.models.arquivo;

import gd.models.ER.Entidade;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Arquivo {

    private Entidade entidade;
    private int tamanho;
    private RandomAccessFile arq;
    private int tamanhoRegistro;
    private String prefix = "";

    public int hash(Valor valor, int passo) {
        if (passo == 0) {
            return valor.getHash() % getTamanho();
        } else {
            return (hash(valor, passo - 1) + passo) % getTamanho();
        }
    }

    public void seek(int position) throws IOException {
        arq.seek(position * tamanhoRegistro);
    }

    public Registro lerRegistro() throws IOException{
        return new Registro(entidade, arq);
    }

    public Arquivo(Entidade entidade) {
        this.entidade = entidade;
        this.arq = null;
        this.tamanhoRegistro = entidade.getTamanho() + 4;
    }

    public void abrir(String prefix, int tamanho) throws IOException {
        this.setPrefix(prefix);
        File arquivo = new File(prefix + getEntidade().getNome() + ".dat");
        this.setArq(new RandomAccessFile(arquivo, "rw"));
        if (getArq().length() == 0) {
            this.tamanho = tamanho;
            this.criar();
        } else {
            this.tamanho = (int) (getArq().length() / getTamanhoRegistro());
        }
        this.contarRegistros();
    }

    public void abrir(String prefix) throws IOException {
        this.abrir(prefix, 8);
    }

    public void abrir() throws IOException {
        this.abrir("");
    }

    public void fechar() throws IOException {
        this.getArq().close();
    }

    public Resultado busca(Valor valor) throws IOException {
        Resultado retorno = null;
        int endereco;
        int passo = 0;
        int removido = -1;
        Resultado resultado = null;
        endereco = hash(valor, passo);
        while (retorno == null) {
            getArq().seek(endereco * getTamanhoRegistro());
            Registro r = new Registro(getEntidade(), getArq());
            if (r.isVazio()) {
                retorno = resultado != null ? resultado : new Resultado(removido == -1 ? endereco : removido, false);
            }
            if (r.getPk().equals(valor)) {
                if (resultado == null) {
                    resultado = new Resultado(endereco, !r.isRemovido());
                } else if (!r.isRemovido() && !resultado.isEncontrado()) {
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

        Resultado resultado = busca(registro.getPk());

        arq.seek(resultado.getPosicao() * tamanhoRegistro);
        Registro atual = new Registro(entidade, arq);

        if (!resultado.isEncontrado() && !atual.isUsado() && existeReferencia(atual)) {

            getArq().seek(resultado.getPosicao() * getTamanhoRegistro());
            registro.grava(getArq());
            getEntidade().setNumeroRegistros(getEntidade().getNumeroRegistros() + 1);
        } else {
            retorno = false;
        }

        if ((getEntidade().getNumeroRegistros()) * 3 > tamanho * 2) {
            reorganizar();
        }


        return retorno;
    }

    public boolean existeReferencia(Registro atual) throws IOException {
        boolean retorno = true;
        for (Relacionamento relacionamento : getEntidade().getRelacionamentos()) {
            if (relacionamento.getEntidade() == getEntidade()) {
                for (Valor valor : atual.getValores()) {
                    if (valor.getTipo().getNome().equals(relacionamento.getCampo())) {
                        Arquivo temp = new Arquivo(relacionamento.getEntidadeReferenciada());
                        temp.abrir(getPrefix());
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
        getArq().seek(0);
        while (getArq().getFilePointer() < getArq().length()) {
            Registro r = new Registro(getEntidade(), getArq());
            if (r.isUsado()) {
                contador++;
            }
        }
        getEntidade().setNumeroRegistros(contador);
    }

    public void criar() throws IOException {
        Registro r = new Registro(getEntidade());
        for (int i = 0; i < tamanho; i++) {
            r.grava(getArq());
        }
    }

    public void reorganizar() throws IOException {
        int numero = getEntidade().getNumeroRegistros();

        File oldFile = new File(getPrefix() + getEntidade().getNome() + ".dat");
        File tempFile = new File(getPrefix() + "temp" + getEntidade().getNome() + ".dat");
        tempFile.delete();

        Arquivo temp = new Arquivo(getEntidade());
        temp.abrir(getPrefix() + "temp", tamanho*2);
        for (int i = 0; i < tamanho; i++) {
            getArq().seek(i * getTamanhoRegistro());
            Registro r = new Registro(getEntidade(), getArq());
            if (r.isUsado()) {
                temp.insere(r);
            }
        }
        temp.fechar();
        this.fechar();

        oldFile.delete();
        tempFile.renameTo(oldFile);
        this.setArq(new RandomAccessFile(oldFile, "rw"));

        this.setTamanho(tamanho * 2);
        getEntidade().setNumeroRegistros(numero);
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public RandomAccessFile getArq() {
        return arq;
    }

    public void setArq(RandomAccessFile arq) {
        this.arq = arq;
    }

    public int getTamanhoRegistro() {
        return tamanhoRegistro;
    }

    public void setTamanhoRegistro(int tamanhoRegistro) {
        this.tamanhoRegistro = tamanhoRegistro;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean remove(Valor valor, int modo) throws IOException {
        // modo == 0: restrict
        // modo == 1: cascade

        Resultado resultado = busca(valor);
        if (resultado.isEncontrado()) {

            for (Relacionamento relacionamento : entidade.getRelacionamentos()) {
                if (relacionamento.getEntidadeReferenciada() == entidade) {
                    Entidade referenciadora = relacionamento.getEntidade();
                    Arquivo temp = new Arquivo(referenciadora);
                    Atributo buscado = referenciadora.buscarAtributo(relacionamento.getCampo());
                    Consulta consulta = new Consulta(temp, null).
                            busca(buscado, "=", resultado.getPosicao()).
                            compila(prefix);
                    if (modo == 0 && !consulta.getCodigos().isEmpty()) {
                        return false;
                    } else if (modo == 1) {
                        temp.abrir(prefix);
                        for (Valor codigo : consulta.getCodigos()) {
                            temp.remove(codigo, modo);
                        }
                        temp.fechar();
                    }
                }
            }

            arq.seek(resultado.getPosicao() * tamanhoRegistro);
            new Registro(entidade, 2).grava(arq);
            return true;
        }
        return false;
    }
}
