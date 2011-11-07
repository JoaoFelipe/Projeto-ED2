package dm.models;

import dm.models.Entity;
import dm.models.Relation;
import dm.models.Attribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restrict extends ConsistencyStrategy{
    

    public Restrict(HashFile hashFile) {
        super(hashFile);
    }

    @Override
    public boolean verifyAndApplyModify(HashFile temp, Search search, Relation relation, List<Value> changes) throws IOException {
        return search.getPKs().isEmpty();
    }

    @Override
    public boolean verifyAndApplyRemove(HashFile temp, Search search, Relation relation) throws IOException {
        return search.getPKs().isEmpty();
    }
    
}
