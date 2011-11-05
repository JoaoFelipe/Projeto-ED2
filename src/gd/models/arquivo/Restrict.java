package gd.models.arquivo;

import gd.models.ER.Entity;
import gd.models.ER.Relation;
import gd.models.atributos.Attribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restrict implements ConsistencyStrategy{
    
    HashFile hashFile = null;

    public Restrict(HashFile hashFile) {
        this.hashFile = hashFile;
    }
    
    public boolean modify(Value value, List<Value> changes) throws IOException {
        Result result = hashFile.find(value);
        if (result.isFound()) {
            for (Relation relation : hashFile.getEntity().getRelation()) {
                if (relation.getReferencedEntity() == hashFile.getEntity()) {
                    Entity referrer = relation.getEntity();
                    HashFile temp = new HashFile(referrer);
                    Attribute searched = referrer.findAttribute(relation.getField());
                    Search search = new Search(temp, null).search(searched, "=", result.getPosition()).compile(hashFile.getPrefix());

                    if (!search.getPKs().isEmpty()) {
                        return false;
                    }
                   
                }
            }
            Tuple tuple = hashFile.readTuple(result.getPosition());
            List<Value> newValues = tuple.getValues();
            for (int i = 0; i < changes.size(); i++) {
                Value change = changes.get(i);
                for (int j = 0; j < newValues.size(); j++) {
                    Value val = newValues.get(j);
                    if (change.getType().equals(val.getType()))
                        newValues.set(j, change);
                }
            }
            tuple.setValues(newValues);
            hashFile.saveTuple(tuple, result.getPosition());
            return true;
        }
        return false;
    }

    public boolean remove(Value value) throws IOException{
        Result result = hashFile.find(value);
        if (result.isFound()) {

            for (Relation relation : hashFile.getEntity().getRelation()) {
                if (relation.getReferencedEntity() == hashFile.getEntity()) {
                    Entity referrer = relation.getEntity();
                    HashFile temp = new HashFile(referrer);
                    Attribute searched = referrer.findAttribute(relation.getField());
                    Search search = new Search(temp, null).search(searched, "=", result.getPosition()).
                            compile(hashFile.getPrefix());
                    if (!search.getPKs().isEmpty()) {
                        return false;
                    }
                    
                }
            }

            hashFile.saveTuple(new Tuple(hashFile.getEntity(), 2), result.getPosition());
            return true;
        }
        return false;
        
    }
    
}
