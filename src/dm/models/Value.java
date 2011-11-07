package dm.models;

import dm.models.Attribute;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Value <T> {
    
    private Attribute type;
    private T info;

    public Value(Attribute type, Object info) {
        this.type = type;
        this.info = (T) type.cast(info);
    }

    public Attribute getType() {
        return type;
    }

    public T getInfo() {
        return info;
    }

    public int getHash(){
        return getType().getHash(this);
    }

    public void save(RandomAccessFile out) throws IOException{
        getType().save(out, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Value<T> other = (Value<T>) obj;
        if (this.info == null || !this.info.equals(other.info)) {
            return false;
        }
        return true;
    }

    public void setType(Attribute type) {
        this.type = type;
    }
    
    public void setInfo(Object info) {
        this.info = (T) type.cast(info);
    }

}