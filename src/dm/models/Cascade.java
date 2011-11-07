package dm.models;

import dm.models.Relation;
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
        temp.open(getHashFile().getPrefix());
        for (Value pk : search.getPKs()) {
            List<Value> newChanges = new ArrayList<Value>();
            for (Value change : changes) {
                if (change.getType().getName().compareTo(relation.getReferencedField()) != 0) {
                    newChanges.add(change);
                }
                else {
                    
//                    Attribute type = change.getType();
//                    Attribute newType = Attribute.createAttribute(relation.getField(), type.getType(), false);
                    
                    newChanges.add(new Value(relation.getEntity().getAttributeByName(relation.getField()), change.getInfo()));
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
        temp.open(getHashFile().getPrefix());
        for (Value pk : search.getPKs()) {
            temp.remove(pk);
        }
        temp.close();
        return true;
    }
    
}
