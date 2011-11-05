package gd.models.arquivo;

import gd.models.ER.Entity;
import gd.models.ER.Relation;
import gd.models.atributos.Attribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cascade extends ConsistencyStrategy{
    

    public Cascade(HashFile hashFile) {
        super(hashFile);
    }

    @Override
    public boolean verifyAndApplyModify(HashFile temp, Search search, Relation relation, List<Value> changes) throws IOException {
        temp.setStrategy(ConsistencyStrategy.CASCADE);
        temp.open(hashFile.getPrefix());
        for (Value pk : search.getPKs()) {
            List<Value> newChanges = new ArrayList<Value>();
            for (Value change : changes) {
                if (change.getType().getName().compareTo(relation.getReferencedField()) != 0) {
                    newChanges.add(change);
                }
                else {
                    Attribute type = change.getType();
                    Attribute newType = Attribute.createAttribute(relation.getField(), type.getType(), false);
                    newChanges.add(new Value(newType, change.getInfo()));
                }
            }
            temp.modify(pk, newChanges);
        }
        temp.close();
        return true;
    }

    @Override
    public boolean verifyAndApplyRemove(HashFile temp, Search search, Relation relation) throws IOException {
        temp.setStrategy(ConsistencyStrategy.CASCADE);
        temp.open(hashFile.getPrefix());
        for (Value pk : search.getPKs()) {
            temp.remove(pk);
        }
        temp.close();
        return true;
    }
    
}
