/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

import gd.models.atributos.Atributo;

public class Busca {
    
    private Atributo atributo;
    private String operador;
    private Object valor;


    public Busca(Atributo atributo, String operador, Object valor) {
        this.atributo = atributo;
        this.operador = operador;
        this.valor = valor;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public void setAtributo(Atributo atributo) {
        this.atributo = atributo;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean compara(Valor buscado){
        return atributo.compara(operador, buscado, valor);
    }

}
