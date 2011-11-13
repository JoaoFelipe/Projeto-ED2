package dm.models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

public class DoubleAttr extends Attribute{

    private String name;
    private boolean pk;

    public DoubleAttr(String name, boolean pk) {
        this.name = name;
        this.pk = pk;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPK() {
        return pk;
    }

    @Override
    public String getType() {
        return "double";
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public Class getClassEx() {
        return Double.class;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleAttr other = (DoubleAttr) obj;
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
        Double d = (Double) value.getInfo();
        return d.hashCode();
    }

    @Override
    public Value read(RandomAccessFile in) throws IOException{
        return new Value<Double>(this, in.readDouble());
    }

    @Override
    public Value getDefault() {
        return new Value<Double>(this, new Double(0));
    }

    @Override
    public void save(RandomAccessFile in, Value value) throws IOException {
        in.writeDouble(((Double) value.getInfo()).doubleValue());
    }

    public boolean equalSearch(Value value, Double condition){
        return ((Double) value.getInfo()).compareTo(condition) == 0;
    }

    public boolean lowerThanSearch(Value value, Double condition){
        return ((Double) value.getInfo()).compareTo(condition) < 0;
    }

    public boolean lowerEqualSearch(Value value, Double condition){
        return ((Double) value.getInfo()).compareTo(condition) <= 0;
    }

    public boolean higherThanSearch(Value value, Double condition){
        return ((Double) value.getInfo()).compareTo(condition) > 0;
    }

    public boolean higherEqualSearch(Value value, Double condition){
        return ((Double) value.getInfo()).compareTo(condition) >= 0;
    }

    @Override
    public boolean compare(String operator, Value value, Object condition) {
        if(operator.equals("="))
            return equalSearch(value, (Double)condition);
        else if(operator.equals("!="))
            return !equalSearch(value, (Double)condition);
        else if(operator.equals(">"))
            return higherThanSearch(value, (Double)condition);
        else if(operator.equals("<"))
            return lowerThanSearch(value, (Double)condition);
        else if(operator.equals(">="))
            return higherEqualSearch(value, (Double)condition);
        else if(operator.equals("<="))
            return lowerEqualSearch(value, (Double)condition);
        else
            return true;
    }

    @Override
    public List<String> comparators() {
        return Arrays.asList("=", "!=", ">", "<", ">=", "<=");
    }
    
    @Override
    public Object cast(Object value) {
        if (value instanceof Double){
            return value;
        } else {
            return Double.parseDouble(value+"");
        }
    }
}
