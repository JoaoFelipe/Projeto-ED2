package gd.models.ER;

import gd.exceptions.ModelException;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gd.exceptions.NonUniqueException;
import gd.models.Colecao;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class ListaER {

    private static ListaER instancia = null;
    private String nomeArquivo = "metadados.dat";
    private String prefix = "";
    private List<EntidadeRelacionamento> lista = null;
    
    private static EntidadeRelacionamento selecionado = null;

    private ListaER() throws ModelException {
    }

    private ListaER(String nome, String prefix) throws ModelException {
        this.nomeArquivo = nome;
        this.prefix = prefix;
    }

    public EntidadeRelacionamento buscar(String nome) throws ModelException {
        for (EntidadeRelacionamento er : getLista()) {
            if (er.getNome().equals(nome)) {
                return er;
            }
        }
        return null;
    }

    public void removePorNome(String nome) throws ModelException {
        EntidadeRelacionamento e = buscar(nome);
        remove(e);
    }

    public void remove(EntidadeRelacionamento e) {
        if (e instanceof Entidade) {
            for (Relacionamento relacionamento : ((Entidade) e).getRelacionamentos()) {
                this.remove(relacionamento);
            }
        }
        e.deletar(prefix);
        lista.remove(e);
    }

    public void add(EntidadeRelacionamento e) throws ModelException {
        if (buscar(e.getNome()) != null) {
            throw new NonUniqueException("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!");
        }
        lista.add(e);
    }

    private void lerArquivo() throws ModelException {
        try {
            lista = new ArrayList<EntidadeRelacionamento>();
            DataInputStream arquivo = null;
            String tipo = "";
            List<String> attrs = new ArrayList<String>();
            try {
                arquivo = new DataInputStream(new FileInputStream(nomeArquivo));

                while (true) {
                    String texto = arquivo.readUTF();
                    if (texto.equals("TABELA") || texto.equals("REFERENCIA")) {
                        if (!tipo.equals("")) {
                            add(EntidadeRelacionamento.criarER(tipo, attrs));
                        }
                        attrs.clear();
                        tipo = texto;
                    } else {
                        attrs.add(texto);
                    }
                }
            } catch (EOFException e) {
                add(EntidadeRelacionamento.criarER(tipo, attrs));
            } catch (FileNotFoundException e) {
            } finally {
                if (arquivo != null) {
                    arquivo.close();
                }
            }
        } catch (IOException ex) {
            throw new ModelException(ex);
        }
    }

    public static ListaER getInstancia() throws ModelException {
        if (instancia == null) {
            instancia = new ListaER();
            instancia.lerArquivo();
        }
        return instancia;
    }

    public List<EntidadeRelacionamento> getLista() throws ModelException {
        return lista;
    }

    public List<String> getNomes() {
        return (List<String>) Colecao.processar(lista, ColecaoER.processos.getNome());
    }

    public List<String> getNomesTabelas() {
        return (List<String>) Colecao.filtrarEProcessar(lista, ColecaoER.filtros.entidade(), ColecaoER.processos.getNome());
    }

    public void grava() throws ModelException {
        try {
            DataOutputStream arq = null;
            try {
                arq = new DataOutputStream(new FileOutputStream(nomeArquivo));
                for (EntidadeRelacionamento er : lista) {
                    er.grava(arq);
                }
            } catch (FileNotFoundException e) {
            } finally {
                if (arq != null) {
                    arq.close();
                }
            }
        } catch (IOException e) {
            throw new ModelException();
        }
    }

    
    public static EntidadeRelacionamento getSelecionado() {
        return selecionado;
    }

    public static void setSelecionado(EntidadeRelacionamento selecionado) {
        ListaER.selecionado = selecionado;
    }

    
    
    
    // Os métodos abaixo foram criados para facilitar os testes. Não usar na aplicação
    public static void apagarInstancia() {
        instancia = null;
    }
    
    public static ListaER instanciarTeste(String nomeArquivo, String prefix) throws ModelException {
        if (instancia == null) {
            instancia = new ListaER(nomeArquivo, prefix);
            instancia.lerArquivo();

        }
        return instancia;
    }
    
}
