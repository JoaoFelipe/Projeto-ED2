/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.ER;

import gd.exceptions.ModelException;
import gd.models.atributos.Atributo;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;
import gd.models.Colecao;
import gd.models.atributos.ColecaoAtributo;

/**
 *
 * @author Joao
 */
public class Entidade extends EntidadeRelacionamento{


    private String nome;
    private List<Atributo> atributos = null;
    private List<Relacionamento> relacionamentos = null;
    private int numeroRegistros = 0;

    public Entidade(List<String> definicao) throws ModelException {
        this.nome = definicao.get(0);
        this.atributos = new ArrayList<Atributo>();
        this.relacionamentos = new ArrayList<Relacionamento>();
        
        for (int i = 1; i < definicao.size(); i++) {
            String def = definicao.get(i);
            Atributo attr = Atributo.criarAtributo(def);
            atributos.add(attr);
        }

        this.verificar();
    }

    public Entidade(String nome, List<Atributo> atributos) throws ModelException {
        this.nome = nome;
        this.atributos = atributos;
        this.relacionamentos = new ArrayList<Relacionamento>();
        this.verificar();
    }

    public void verificar() throws ModelException {
        if (nome == null || nome.equals("")) {
            throw new NotFoundException("O nome não pode estar em branco");
        }

        int count = 0;
        Set<String> set = new HashSet<String>();

        for (Atributo atributo : atributos) {
            if (!set.add(atributo.getNome())){
                throw new NonUniqueException("Atributo não é único");
            }
            if (atributo.getPK()){
                count++;
            }
        }
        if (count == 0) {
            throw new NotFoundException("Nenhuma chave primária encontrada");
        }
        if (count > 1) {
            throw new NonUniqueException("Mais de uma chave primária encontrada");
        }
    }

    public Atributo buscarAtributo(String nome){
        for (Atributo atributo : atributos) {
            if (atributo.getNome().equals(nome)){
                return atributo;
            }
        }
        return null;
    }

    @Override
    public void grava(DataOutputStream saida) throws ModelException{
        try {
            saida.writeUTF("TABELA");
            saida.writeUTF(nome);
            for (Atributo atributo : atributos) {
                saida.writeUTF(atributo.getRepr());
            }
        } catch (IOException e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entidade other = (Entidade) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.atributos == null) ? (other.atributos != null) : (!this.atributos.equals(other.atributos))) {
            return false;
        }

        return true;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public List<Relacionamento> getRelacionamentos(){
        return relacionamentos;
    }

    public List<Atributo> getAtributos(){
        return atributos;
    }

    public List<String> getAtributosBuscaveis(){
        List<String> retorno = new ArrayList<String>();
        retorno.addAll(Colecao.processar(atributos, ColecaoAtributo.processos.getNome()));
        for (Relacionamento relacionamento : relacionamentos) {
            if (relacionamento.getEntidade() == this){
                retorno.add(relacionamento.getEntidadeReferenciada().getNome()+"-"+relacionamento.getCampoReferenciado());
            } else {
                retorno.add("#"+relacionamento.getEntidade().getNome());
            }
        }

        return retorno;
    }

    public int getTamanho(){
        int retorno = 0;
        for (Atributo atributo : atributos) {
            retorno += atributo.getTamanho();
        }
        return retorno;
    }

    public Atributo getPk(){
        for (Atributo atributo : atributos) {
            if (atributo.getPK())
                return atributo;
        }
        return null;
    }

    /**
     * @return the numeroRegistros
     */
    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * @param numeroRegistros the numeroRegistros to set
     */
    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }


}
