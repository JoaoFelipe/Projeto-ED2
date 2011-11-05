package gd.models.atributos;

import gd.models.Process;

public class AttributeCollection {

    public static class processes {

        public static Process<Attribute, String> getName(){
            return new Process<Attribute, String>(){
                public String apply(Attribute a) {
                    return a.getName();
                }
            };
        }

        public static Process<Attribute, Class> getClassEx(){
            return new Process<Attribute, Class>(){
                public Class apply(Attribute a) {
                    return a.getClassEx();
                }
            };
        }

    }
    
}
