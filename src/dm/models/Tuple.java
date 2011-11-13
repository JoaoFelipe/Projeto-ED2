package dm.models;

import dm.models.Entity;
import dm.models.Attribute;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Tuple {

    private Entity entity;
    private List<Value> values;
    private int state; // 0: não usado; 1: usado; 2:apagado
    private Value pk;

    public Tuple(Entity entity, int state, List attrValues) {
        this.entity = entity;
        this.state = state;
        this.values = new ArrayList<Value>();
        List<Attribute> attrs = entity.getAttributes();
        for (int i= 0; i < attrs.size(); i++) {
            Attribute attr = attrs.get(i);
            Value value = new Value(attr, attrValues.get(i));
            if (attr.isPK()){
                pk = value;
            }
            this.values.add(value);
        }
    }

    public Tuple(Entity entity, List attrValues) {
        this(entity, 1, attrValues);
    }

    public Tuple(Entity entity, int state) {
        this.state = state;
        this.entity = entity;
        this.values = new ArrayList<Value>();

        for (Attribute attr : entity.getAttributes()) {
            Value value = attr.getDefault();
            if (attr.isPK()){
                pk = value;
            }
            this.values.add(value);
        }
    }

    public Tuple(Entity entity) {
        this(entity, 0);
    }

    public Tuple(Entity entity, RandomAccessFile in) throws IOException {
        this.entity = entity;
        this.values = new ArrayList<Value>();
        this.state = in.readInt();
        for (Attribute attr : entity.getAttributes()) {
            Value value = attr.read(in);
            if (attr.isPK()){
                pk = value;
            }
            this.values.add(value);
        }
    }

    public void save(RandomAccessFile out) throws IOException {
        out.writeInt(getState());
        for (Value value : getValues()) {
            value.save(out);
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
        final Tuple other = (Tuple) obj;
        if (this.getEntity() == null || !this.entity.equals(other.entity)) {
            return false;
        }
        if (this.getValues() == null || !this.values.equals(other.values)) {
            return false;
        }
        if (this.getState() != other.getState()) {
            return false;
        }
        return true;
    }

    public boolean isEmpty(){
        return (getState() == 0);
    }

    public boolean isUsed(){
        return (getState() == 1);
    }

    public boolean isRemoved(){
        return (getState() == 2);
    }

    public Value getPK(){
        return this.pk;
    }
    
    public void setPK(Value pk) {
        this.pk = pk;
    }


    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    
    public Value getValueByAttributeName(String name) {
        for (Value value : this.getValues()) {
            if (value.getType().getName().equals(name)) {
                return value;
            }

        }
        return null;
    }
    
     public Object[] getRow(){
         Object[] row = new Object[this.getValues().size()]; 
         for (int i = 0; i < this.getValues().size(); i++) {
             row[i] = this.getValues().get(i).getInfo();
             
         }
         return row;
    }
     
     
}
