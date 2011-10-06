/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.atributos;

import gd.models.atributos.Atributo;

/**
 *
 * @author Joao
 */
public class CharAttr extends Atributo{


    private String nome;
    private boolean pk;
    private int tamanho;

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
        return tamanho;
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

 

}
