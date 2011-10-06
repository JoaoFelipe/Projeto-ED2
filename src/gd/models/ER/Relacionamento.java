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
    private String campoAlternativo;

    public Relacionamento(EntidadeRelacionamento entidade, String campo, EntidadeRelacionamento entidadeReferenciada, String campoReferenciado, String campoAlternativo) throws ModelException {
        this.entidade = (Entidade) entidade;
        this.campo = campo;
        this.entidadeReferenciada = (Entidade) entidadeReferenciada;
        this.campoReferenciado = campoReferenciado;
        this.campoAlternativo = campoAlternativo;
        
        this.validar();
    }

    public Relacionamento(EntidadeRelacionamento entidade, String campo, EntidadeRelacionamento entidadeReferenciada, String campoReferenciado) throws ModelException {
        this(entidade, campo, entidadeReferenciada, campoReferenciado, campoReferenciado);
    }

    public Relacionamento(List<String> defs) throws ModelException{
        this(ListaER.getInstancia().buscar(defs.get(0)), defs.get(1), ListaER.getInstancia().buscar(defs.get(2)), defs.get(3));
        if (defs.size() == 5) {
            this.campoAlternativo = defs.get(4);
        } else if (defs.size() != 4) {
            throw new NotFoundException("Elementos não encontrados");
        }
        this.validar();
    }

    public void validar() throws ModelException {// throws NotFoundException{
        if (entidade == null) {
            throw new NotFoundException("Entidade não encontrada");
        }
        if (entidadeReferenciada == null) {
            throw new NotFoundException("Entidade Referenciada não encontrada");
        }
        if (entidade.buscarAtributo(campo) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade");
        }
        if (entidadeReferenciada.buscarAtributo(campoReferenciado) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade Referenciada");
        }
        if (entidadeReferenciada.buscarAtributo(campoAlternativo) == null) {
            throw new NotFoundException("Atributo alterenativo não encontrado na Entidade Referenciada");
        }

    }

    @Override
    public void grava(DataOutputStream saida) throws ModelException {
        try {
            saida.writeUTF("REFERENCIA");
            saida.writeUTF(entidade.getNome());
            saida.writeUTF(campo);
            saida.writeUTF(entidadeReferenciada.getNome());
            saida.writeUTF(campoReferenciado);
            saida.writeUTF(campoAlternativo);
        } catch (IOException ex) {
            throw new ModelException(ex);
        }
    }

    public Object[] getRow(){
        return new Object[]{
           entidade.getNome(),
           campo,
           entidadeReferenciada.getNome(),
           campoReferenciado,
           campoAlternativo
        };
    }

    @Override
    public String getNome() {
        return "R: "+entidade.getNome()+" "+campo;
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
        if ((this.campo == null) ? (other.campo != null) : !this.campo.equals(other.campo)) {
            return false;
        }
        if (this.entidadeReferenciada != other.entidadeReferenciada && (this.entidadeReferenciada == null || !this.entidadeReferenciada.equals(other.entidadeReferenciada))) {
            return false;
        }
        if ((this.campoReferenciado == null) ? (other.campoReferenciado != null) : !this.campoReferenciado.equals(other.campoReferenciado)) {
            return false;
        }
        if ((this.campoAlternativo == null) ? (other.campoAlternativo != null) : !this.campoAlternativo.equals(other.campoAlternativo)) {
            return false;
        }
        return true;
    }

    public void adicionarNaEntidade(){
        entidade.getRelacionamentos().add(this);
    }

    public void deletar(){}

  
    


   




}
