package gd.models.arquivo;

import gd.models.ER.Entity;
import gd.models.ER.Relation;
import gd.models.atributos.Attribute;
import java.io.IOException;
import java.util.List;

public abstract class ConsistencyStrategy {
    
    // Padrão Strategy, usado para definir estratégias de execução diferentes
    //para cascade e restrict
    
    // Padrão Template Method, os metodos modify e remove usam os metodos 
    //verifyAndApplyModify e verifyAndApplyRemove que são definidos de forma 
    //diferente nas classes cascade e restrict que herdam desta

    public final static int RESTRICT = 0;
    public static int CASCADE = 1;
    
    HashFile hashFile = null;

    public ConsistencyStrategy(HashFile hashFile) {
        this.hashFile = hashFile; 
    }
    
    
    public abstract boolean verifyAndApplyModify(HashFile temp, Search search, Relation relation, List<Value> changes) throws IOException;
    public abstract boolean verifyAndApplyRemove(HashFile temp, Search search, Relation relation) throws IOException;
    
    public boolean modify(Value value, List<Value> changes) throws IOException {
        
        Result result = hashFile.find(value);
        if (result.isFound()) {
            for (Relation relation : hashFile.getEntity().getRelation()) {
                if (relation.getReferencedEntity() == hashFile.getEntity()) {
                    Entity referrer = relation.getEntity();
                    HashFile temp = new HashFile(referrer);
                    Attribute searched = referrer.findAttribute(relation.getField());
                    Search search = new Search(temp, null).search(searched, "=", result.getPosition()).compile(hashFile.getPrefix());

                    if (!verifyAndApplyModify(temp, search, relation, changes)){
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
                    if (change.getType().equals(val.getType())) {
                        val.setInfo(change.getInfo());
                        
//                        newValues.set(j, change);
                    }
                }
            }
            tuple.setValues(newValues);
            hashFile.saveTuple(new Tuple(hashFile.getEntity(), 2), result.getPosition());
            hashFile.getEntity().setNumberOfTuples(hashFile.getEntity().getNumberOfTuples() -1);
            return hashFile.insert(tuple, true);
//            hashFile.saveTuple(tuple, result.getPosition());
        }
        return false;
        
    }
    public boolean remove(Value value) throws IOException {
        Result result = hashFile.find(value);
        if (result.isFound()) {

            for (Relation relation : hashFile.getEntity().getRelation()) {
                if (relation.getReferencedEntity() == hashFile.getEntity()) {
                    Entity referrer = relation.getEntity();
                    HashFile temp = new HashFile(referrer);
                    Attribute searched = referrer.findAttribute(relation.getField());
                    Search search = new Search(temp, null).search(searched, "=", result.getPosition()).
                            compile(hashFile.getPrefix());
                    
                    if (!verifyAndApplyRemove(temp, search, relation)){
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
