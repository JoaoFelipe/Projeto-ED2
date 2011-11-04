package gd.models.arquivo;

import gd.models.ER.Entidade;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arquivo {

    class RegistroIterador implements Iterator<Registro> {

        private int passo;
        private int endereco;
        private Valor valor;
        private int anterior;

        public RegistroIterador(Valor valor) {
            passo = 0;
            this.valor = valor;
        }

        public int hash() {
            if (passo == 0) {
                anterior = valor.getHash() % tamanho;
            }
            else {
                anterior = (anterior + passo) % tamanho;//(hash(valor, passo - 1) + passo) % tamanho();
            }
            return anterior;
        }

        public boolean hasNext() {
            return passo < tamanho;
        }

        public Registro next() {
            endereco = hash();
            passo += 1;
            try {
                return lerRegistro(endereco);
            } catch (IOException ex) {
                return null;
            }
        }

        public void endLoop() {
            passo = tamanho;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getEndereco() {
            return endereco;
        }
    }

    class RegistroIterable implements Iterable<Registro> {

        private RegistroIterador iterador = null;

        public RegistroIterable(Valor valor) {
            iterador = new RegistroIterador(valor);
        }

        public Iterator<Registro> iterator() {
            return iterador;
        }

        public int getEndereco() {
            return iterador.getEndereco();
        }

        public void endLoop() {
            iterador.endLoop();
        }
    }
    private Entidade entidade;
    private int tamanho;
    private RandomAccessFile arq;
    private int tamanhoRegistro;
    private String prefix = "";

    public Arquivo(Entidade entidade) {
        this.entidade = entidade;
        this.arq = null;
        this.tamanhoRegistro = entidade.getTamanho() + 4;
    }

    public void abrir(String prefix, int tamanho) throws IOException {
        this.setPrefix(prefix);
        File arquivo = new File(prefix + entidade.getNome() + ".dat");
        this.setArq(new RandomAccessFile(arquivo, "rw"));
        if (arq.length() == 0) {
            this.tamanho = tamanho;
            this.criar();
        }
        else {
            this.tamanho = (int) (arq.length() / tamanhoRegistro);
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

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void seek(int position) throws IOException {
        arq.seek(position * tamanhoRegistro);
    }

    public Registro lerRegistro() throws IOException {
        return new Registro(entidade, arq);
    }

    public Registro lerRegistro(int position) throws IOException {
        this.seek(position);
        return this.lerRegistro();
    }

    public void gravaRegistro(Registro registro) throws IOException {
        registro.grava(arq);
    }

    public void gravaRegistro(Registro registro, int position) throws IOException {
        this.seek(position);
        registro.grava(arq);
    }

    public Resultado busca(Valor valor) throws IOException {
        Resultado retorno = null;
        Resultado resultado = null;
        int removido = -1;

        RegistroIterable iterador = new RegistroIterable(valor);
        for (Registro registro : iterador) {
            int endereco = iterador.getEndereco();

            if (registro.isVazio()) {
                retorno = resultado != null ? resultado : new Resultado(removido == -1 ? endereco : removido, false);
                iterador.endLoop();
            }
            if (registro.getPk().equals(valor)) {
                if (resultado == null) {
                    resultado = new Resultado(endereco, !registro.isRemovido());
                }
                else if (!registro.isRemovido() && !resultado.isEncontrado()) {
                    resultado = new Resultado(endereco, true);
                }
            }
            if (registro.isRemovido() && removido == -1) {
                removido = endereco;
            }

        }
        if (retorno == null) {
            retorno = resultado != null ? resultado : new Resultado(removido, false);
        }
        return retorno;
    }

    public boolean insere(Registro registro) throws IOException {
        boolean retorno = true;
        Resultado resultado = busca(registro.getPk());

        Registro atual = this.lerRegistro(resultado.getPosicao());

        if (!resultado.isEncontrado() && !atual.isUsado() && existeReferencia(atual)) {
            this.gravaRegistro(registro, resultado.getPosicao());
            this.entidade.setNumeroRegistros(this.entidade.getNumeroRegistros() + 1);
        }
        else {
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
                Valor valor = atual.getValorByAttrName(relacionamento.getCampo());

                Arquivo temp = new Arquivo(relacionamento.getEntidadeReferenciada());
                temp.abrir(prefix);
                retorno = retorno & temp.busca(valor).isEncontrado();
                temp.fechar();
            }
        }
        return retorno;
    }

    public void contarRegistros() throws IOException {
        int contador = 0;
        arq.seek(0);
        while (arq.getFilePointer() < arq.length()) {
            if (this.lerRegistro().isUsado()) {
                contador++;
            }
        }
        entidade.setNumeroRegistros(contador);
    }

    public void criar() throws IOException {
        Registro branco = new Registro(entidade);
        for (int i = 0; i < tamanho; i++) {
            this.gravaRegistro(branco);
        }
    }

    public void reorganizar() throws IOException {
        int numero = entidade.getNumeroRegistros();

        File oldFile = new File(prefix + entidade.getNome() + ".dat");
        File tempFile = new File(prefix + "temp" + entidade.getNome() + ".dat");
        tempFile.delete();

        Arquivo temp = new Arquivo(entidade);
        temp.abrir(prefix + "temp", tamanho * 2);
        for (int i = 0; i < tamanho; i++) {
            Registro registro = this.lerRegistro(i);
            if (registro.isUsado()) {
                temp.insere(registro);
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

    public boolean modifica(Valor valor, int modo, List<Valor> mudancas) throws IOException {
        Resultado resultado = busca(valor);
        if (resultado.isEncontrado()) {
            for (Relacionamento relacionamento : entidade.getRelacionamentos()) {
                if (relacionamento.getEntidadeReferenciada() == entidade) {
                    Entidade referenciadora = relacionamento.getEntidade();
                    Arquivo temp = new Arquivo(referenciadora);
                    Atributo buscado = referenciadora.buscarAtributo(relacionamento.getCampo());
                    Consulta consulta = new Consulta(temp, null).busca(buscado, "=", resultado.getPosicao()).compila(prefix);
                    boolean campoRelacMudou = false;
                    for (Valor muda : mudancas) {
                        if (muda.getTipo().getNome().compareTo(relacionamento.getCampo()) == 0)
                            campoRelacMudou = true;
                    }
                    if (modo == 0 && !consulta.getCodigos().isEmpty() && campoRelacMudou) {
                        return false;
                    }
                    else if (modo == 1) {
                        temp.abrir(prefix);
                        for (Valor codigo : consulta.getCodigos()) {
                            List<Valor> novas_mudancas = new ArrayList<Valor>();
                            for (Valor mudanca : mudancas) {
                                if (mudanca.getTipo().getNome().compareTo(relacionamento.getCampoReferenciado()) != 0) {
                                    novas_mudancas.add(mudanca);
                                }
                                else {
                                    Atributo tipo = mudanca.getTipo();
                                    Atributo novo_tipo = Atributo.criarAtributo(relacionamento.getCampo(), tipo.getTipo(), false);
                                    novas_mudancas.add(new Valor(novo_tipo, mudanca.getInfo()));
                                }
                            }
                            temp.modifica(codigo, modo, novas_mudancas);
                        }
                        temp.fechar();
                    }
                }
            }
            Registro reg = this.lerRegistro(resultado.getPosicao());
            List<Valor> novos_val = reg.getValores();
            for (int i = 0; i < mudancas.size(); i++) {
                Valor muda = mudancas.get(i);
                for (int j = 0; j < novos_val.size(); j++) {
                    Valor val = novos_val.get(j);
                    if (muda.getTipo().equals(val.getTipo()))
                        novos_val.set(j, muda);
                }
            }
            reg.setValores(novos_val);
            this.gravaRegistro(reg, resultado.getPosicao());
            return true;
        }
        return false;
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
                    Consulta consulta = new Consulta(temp, null).busca(buscado, "=", resultado.getPosicao()).
                            compila(prefix);
                    if (modo == 0 && !consulta.getCodigos().isEmpty()) {
                        return false;
                    }
                    else if (modo == 1) {
                        temp.abrir(prefix);
                        for (Valor codigo : consulta.getCodigos()) {
                            temp.remove(codigo, modo);
                        }
                        temp.fechar();
                    }
                }
            }

            this.gravaRegistro(new Registro(entidade, 2), resultado.getPosicao());
            return true;
        }
        return false;
    }
}
