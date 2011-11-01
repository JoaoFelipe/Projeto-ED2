package gd.models.arquivo;

import gd.models.atributos.Atributo;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class Valor <T> {
    
    private Atributo tipo;
    private T info;

    public Valor(Atributo tipo, Object info) {
        this.tipo = tipo;
        this.info = (T) tipo.cast(info);
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
        getTipo().grava(out, this);
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
        if (this.info == null || !this.info.equals(other.info)) {
            return false;
        }
        return true;
    }

    public void setTipo(Atributo tipo) {
        this.tipo = tipo;
    }

}