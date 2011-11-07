package dm.models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharAttr extends Attribute{

    private String name;
    private boolean pk;
    private int size;

    final int STRSIZE = 2;

    public CharAttr(String name, boolean pk, int size) {
        this.name = name;
        this.pk = pk;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "char"+size;
    }

    @Override
    public boolean getPK() {
        return pk;
    }

    @Override
    public int getSize() {
        return size+STRSIZE;
    }

    @Override
    public Class getClassEx() {
        return String.class;
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
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.pk != other.pk) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

    @Override
    public int getHash(Value value) {
        String v = (String) value.getInfo();
        return v.hashCode();
    }

    @Override
    public Value read(RandomAccessFile in) throws IOException{
        return new Value<String>(this, in.readUTF().trim());
    }

    @Override
    public Value getDefault() {
        return new Value<String>(this, "");
    }

    @Override
    public void save(RandomAccessFile in, Value value) throws IOException {
        String temp = (String) value.getInfo();
        for (int i = temp.length(); i < this.getSize()-STRSIZE; i++) {
            temp += " ";
        }
        in.writeUTF(temp);
    }


    private static String toRegEx(String input) {
        input = input.replace("+", "\\+");
        input = input.replace("(", "\\(");
        input = input.replace(")", "\\)");
        input = input.replace(".", "\\.");
        input = input.replace("[", "\\[");
        input = input.replace("]", "\\]");
        input = input.replace("{", "\\{");
        input = input.replace("}", "\\}");
        input = input.replace(",", "\\,");
        input = input.replace("|", "\\|");
        input = input.replace("^", "\\^");
        input = input.replace("&", "\\&");
        input = input.replace("?", "\\?");
        input = input.replace("*", "\\*");
        input = input.replace("\\_", "\n");
        input = input.replace("_", ".");
        input = input.replace("\n", "\\_");
        input = input.replace("\\%", "\n"); 
        input = input.replace("%", ".*");
        input = input.replace("\n", "\\%");  
        return input;
    }

    public boolean likeSearch(Value value, String condition){
        condition = toRegEx(condition);
        Pattern p = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher((CharSequence) value.getInfo());
        return m.matches();
    }

    public boolean regExSearch(Value value, String condition){
        Pattern p = Pattern.compile(condition, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher((CharSequence) value.getInfo());
        return m.matches();
    }

    public boolean equalSearch(Value value, String condition){
        return ((String) value.getInfo()).compareTo(condition) == 0;
    }

    public boolean lowerThanSearch(Value value, String condition){
        return ((String) value.getInfo()).compareTo(condition) < 0;
    }

    public boolean lowerEqualSearch(Value value, String condition){
        return ((String) value.getInfo()).compareTo(condition) <= 0;
    }

    public boolean higherThanSearch(Value value, String condition){
        return ((String) value.getInfo()).compareTo(condition) > 0;
    }

    public boolean higherEqualSearch(Value value, String condition){
        return ((String) value.getInfo()).compareTo(condition) >= 0;
    }

    @Override
    public boolean compare(String operator, Value value, Object condition) {
        if (operator.equals("LIKE"))
            return likeSearch(value, (String)condition);
        else if(operator.equals("REGEX"))
            return regExSearch(value, (String)condition);
        else if(operator.equals("="))
            return equalSearch(value, (String)condition);
        else if(operator.equals("!="))
            return !equalSearch(value, (String)condition);
        else if(operator.equals(">"))
            return higherThanSearch(value, (String)condition);
        else if(operator.equals("<"))
            return lowerThanSearch(value, (String)condition);
        else if(operator.equals(">="))
            return higherEqualSearch(value, (String)condition);
        else if(operator.equals("<="))
            return lowerEqualSearch(value, (String)condition);
        else
            return true;
    }

    @Override
    public List<String> comparators() {
        return Arrays.asList("LIKE", "REGEX", "=", "!=", ">", "<", ">=", "<=");
    }

    @Override
    public Object cast(Object value) {
        return value + "";
    }

}
