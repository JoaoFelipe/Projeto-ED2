package dm.models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Attribute{
    
    //Padrão Factory Method: A criação de abributos em suas respectivas classes 
    //antes que fossem definidas, foi delegada para o createAttribute

    public static Attribute createAttribute(String name, String type, Boolean pk){
        name = name.toLowerCase(Locale.ENGLISH);
        name = Normalizer.normalize(name, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        name = name.trim();
        if (type.contains("char")){
            Pattern p = Pattern.compile("char(.+)");
            Matcher m = p.matcher(type);
            if (m.matches()){
                int size = Integer.parseInt(m.group(1));
                return new CharAttr(name, pk, size);
            } else {
                return new CharAttr(name, pk, 1);
            }
        } else if (type.equals("int")){
            return new IntAttr(name, pk);
        } else if (type.equals("double")){
            return new DoubleAttr(name, pk);
        }
        return null;
    }

    public static Attribute createAttribute(String text){
        Pattern p = Pattern.compile("(\\*)?(.+):(.+)");
        Matcher m = p.matcher(text);
        if (m.matches()){
            String name = m.group(2);
            name = name.toLowerCase(Locale.ENGLISH);
            name = Normalizer.normalize(name, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            name = name.trim();
            
            if (!name.isEmpty()) {
                boolean pk = (m.group(1) != null);
                if (m.group(3).equals("int")){
                    return new IntAttr(name, pk);
                } else if (m.group(3).equals("double")){
                    return new DoubleAttr(name, pk);
                } else if (m.group(3).contains("char")) {
                    p = Pattern.compile("char(.+)");
                    m = p.matcher(m.group(3));
                    if (m.matches()){
                        int size = Integer.parseInt(m.group(1));
                        return new CharAttr(name, pk, size);
                    } else {
                        return new CharAttr(name, pk, 1);
                    }
                }
            }
               
        }
        return null;
    }

    public static List<String> allOptions() {
        return Arrays.asList(
            "int",
            "double",
            "char1",
            "char10",
            "char30",
            "char50",
            "char100"
        );
    }

    public String getRepr() {
        return (isPK() ? "*": "")+getName()+":"+getType();
    }

    public abstract String getName();
    public abstract String getType();
    public abstract boolean isPK();
    public abstract int getSize();
    public abstract Class getClassEx();
    public abstract int getHash(Value value);
    public abstract Value getDefault();
    public abstract Value read(RandomAccessFile in) throws IOException;
    public abstract void save(RandomAccessFile out, Value value) throws IOException;

    public abstract boolean compare(String operator, Value value, Object condition);
    public abstract List<String> comparators();
    public abstract Object cast(Object value);

}
