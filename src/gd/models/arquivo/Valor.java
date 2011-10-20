/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

/**
 *
 * @author Joao
 */
public class Valor <T> {
    private Atributo tipo;
    private T info;

    public Valor(Atributo tipo, T info) {
        this.tipo = tipo;
        this.info = info;
    }

    public Atributo getTipo() {
        return tipo;
    }

    public T getInfo() {
        return info;
    }

    public int getHash(){
        return getTipo().getHash(this);
    }

    public void grava(RandomAccessFile out) throws IOException{
        tipo.grava(out, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Valor<T> other = (Valor<T>) obj;
        if (this.tipo == null || !this.tipo.equals(other.tipo)) {
            return false;
        }
        if (this.info == null || !this.info.equals(other.info)) {
            return false;
        }
        return true;
    }






}
