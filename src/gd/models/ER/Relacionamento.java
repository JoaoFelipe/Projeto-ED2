/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.ER;

import gd.exceptions.ModelException;
import gd.models.ER.ListaER;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;

/**
 *
 * @author Joao
 */
public class Relacionamento extends EntidadeRelacionamento{

    private Entidade entidade;
    private String campo;

    private Entidade entidadeReferenciada;
    private String campoReferenciado;

    public Relacionamento(EntidadeRelacionamento entidade, String campo, EntidadeRelacionamento entidadeReferenciada, String campoReferenciado) throws ModelException {
        this.entidade = (Entidade) entidade;
        this.campo = campo;
        this.entidadeReferenciada = (Entidade) entidadeReferenciada;
        this.campoReferenciado = campoReferenciado;
        
        this.validar();
    }

 
    public Relacionamento(List<String> defs) throws ModelException{
        this(ListaER.getInstancia().buscar(defs.get(0)), defs.get(1), ListaER.getInstancia().buscar(defs.get(2)), defs.get(3));
        if (defs.size() != 4) {
            throw new NotFoundException("Elementos não encontrados");
        }
        this.validar();
    }

    public void validar() throws ModelException {// throws NotFoundException{
        if (entidade == null) {
            throw new NotFoundException("Entidade não encontrada");
        }
        if (getEntidadeReferenciada() == null) {
            throw new NotFoundException("Entidade Referenciada não encontrada");
        }
        if (entidade.buscarAtributo(getCampo()) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade");
        }
        if (getEntidadeReferenciada().buscarAtributo(campoReferenciado) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade Referenciada");
        }
  

    }

    @Override
    public void grava(DataOutputStream saida) throws ModelException {
        try {
            saida.writeUTF("REFERENCIA");
            saida.writeUTF(entidade.getNome());
            saida.writeUTF(getCampo());
            saida.writeUTF(getEntidadeReferenciada().getNome());
            saida.writeUTF(campoReferenciado);
        } catch (IOException ex) {
            throw new ModelException(ex);
        }
    }

    public Object[] getRow(){
        return new Object[]{
           entidade.getNome(),
           getCampo(), getEntidadeReferenciada().getNome(),
           campoReferenciado
        };
    }

    @Override
    public String getNome() {
        return "R: "+entidade.getNome()+" "+getCampo();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relacionamento other = (Relacionamento) obj;
        if (this.entidade != other.entidade && (this.entidade == null || !this.entidade.equals(other.entidade))) {
            return false;
        }
        if ((this.getCampo() == null) ? (other.getCampo() != null) : !this.campo.equals(other.campo)) {
            return false;
        }
        if (this.getEntidadeReferenciada() != other.getEntidadeReferenciada() && (this.getEntidadeReferenciada() == null || !this.entidadeReferenciada.equals(other.entidadeReferenciada))) {
            return false;
        }
        if ((this.campoReferenciado == null) ? (other.campoReferenciado != null) : !this.campoReferenciado.equals(other.campoReferenciado)) {
            return false;
        }
        return true;
    }

    public void adicionarNaEntidade(){
        entidade.getRelacionamentos().add(this);
        getEntidadeReferenciada().getRelacionamentos().add(this);
    }

    public void deletar(){}

  
    public Entidade getEntidade(){
        return entidade;
    }

    public Entidade getEntidadeReferenciada(){
        return entidadeReferenciada;
    }

    public String getCampoReferenciado() {
        return campoReferenciado;
    }

    /**
     * @return the campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @param campo the campo to set
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @param entidadeReferenciada the entidadeReferenciada to set
     */
    public void setEntidadeReferenciada(Entidade entidadeReferenciada) {
        this.entidadeReferenciada = entidadeReferenciada;
    }

    


   




}
