package gd.models.ER;

import gd.exceptions.ModelException;
import java.io.DataOutputStream;
import java.util.List;
import java.io.File;

public abstract class EntityRelationship {
    
    //Padrão...

    public static EntityRelationship createER(String type, List<String> defs) throws ModelException {
        if (type.equals("TABELA")) {
            return new Entity(defs);
        } else if (type.equals("REFERENCIA")) {
            Relation relation = new Relation(defs);
            relation.addToEntity();
            return relation;
        } else {
            return null;
        }
    }

    public abstract void save(DataOutputStream out) throws ModelException;

    public abstract String getName();

    public void delete(String prefix) {
        File file = new File(prefix + getName() + ".dat");
        file.delete();
    }
    
}
