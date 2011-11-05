package gd.models.atributos;

import gd.models.arquivo.Value;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class IntAttr extends Attribute{

    private String name;
    private boolean pk;

    public IntAttr(String name, boolean pk) {
        this.name = name;
        this.pk = pk;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getPK() {
        return pk;
    }

    @Override
    public String getType() {
        return "int";
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Class getClassEx() {
        return Integer.class;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntAttr other = (IntAttr) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.pk != other.pk) {
            return false;
        }
        return true;
    }

    @Override
    public int getHash(Value value) {
        Integer i = (Integer) (value.getInfo());
        return i.hashCode();
    }

    @Override
    public Value read(RandomAccessFile in) throws IOException{
        return new Value<Integer>(this, in.readInt());
    }

    @Override
    public Value getDefault() {
        return new Value<Integer>(this, new Integer(0));
    }

    @Override
    public void save(RandomAccessFile in, Value value) throws IOException {
        in.writeInt(((Integer) value.getInfo()).intValue());
    }

    public boolean equalSearch(Value value, Integer condition){
        return ((Integer) value.getInfo()).compareTo(condition) == 0;
    }

    public boolean lowerThanSearch(Value value, Integer condition){
        return ((Integer) value.getInfo()).compareTo(condition) < 0;
    }

    public boolean lowerEqualSearch(Value value, Integer condition){
        return ((Integer) value.getInfo()).compareTo(condition) <= 0;
    }

    public boolean higherThanSearch(Value value, Integer condition){
        return ((Integer) value.getInfo()).compareTo(condition) > 0;
    }

    public boolean higherEqualSearch(Value value, Integer condition){
        return ((Integer) value.getInfo()).compareTo(condition) >= 0;
    }

    @Override
    public boolean compare(String operator, Value value, Object condition) {
        if(operator.equals("="))
            return equalSearch(value, (Integer)condition);
        else if(operator.equals("!="))
            return !equalSearch(value, (Integer)condition);
        else if(operator.equals(">"))
            return higherThanSearch(value, (Integer)condition);
        else if(operator.equals("<"))
            return lowerThanSearch(value, (Integer)condition);
        else if(operator.equals(">="))
            return higherEqualSearch(value, (Integer)condition);
        else if(operator.equals("<="))
            return lowerEqualSearch(value, (Integer)condition);
        else
            return true;
    }

    @Override
    public List<String> comparators() {
        return Arrays.asList("=", "!=", ">", "<", ">=", "<=");
    }
    
    @Override
    public Object cast(Object value) {
        if (value instanceof Integer){
            return value;
        } else {
            return Integer.parseInt(value+"");
        }
    }
}
