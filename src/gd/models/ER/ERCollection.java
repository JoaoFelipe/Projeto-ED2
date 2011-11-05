package gd.models.ER;

import gd.models.Filter;
import gd.models.Process;

public class ERCollection {

    public static class processes {

        public static Process<EntityRelationship, String> getName(){
            return new Process<EntityRelationship, String>(){
                public String apply(EntityRelationship er) {
                    return er.getName();
                }
            };
        }

    }

    public static class filtros {

        public static Filter<EntityRelationship> entity() {
            return new Filter<EntityRelationship>(){
                public boolean apply(EntityRelationship er) {
                    return (er instanceof Entity);
                }
            };
        }
        
    }
    
}
