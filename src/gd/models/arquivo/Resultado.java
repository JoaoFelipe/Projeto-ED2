/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

/**
 *
 * @author Joao
 */
public class Resultado {

    
    private int posicao;
    private boolean encontrado;

    public Resultado(int posicao, boolean encontrado) {
        this.posicao = posicao;
        this.encontrado = encontrado;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public void setEncontrado(boolean encontrado) {
        this.encontrado = encontrado;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resultado other = (Resultado) obj;
        if (this.posicao != other.posicao) {
            return false;
        }
        if (this.encontrado != other.encontrado) {
            return false;
        }
        return true;
    }



}
