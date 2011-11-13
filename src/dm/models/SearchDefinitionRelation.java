package dm.models;

import dm.exceptions.ModelException;
import dm.models.Attribute;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchDefinitionRelation implements SearchDefinitionInterface {
    
    private Attribute attribute;
    private String operator;
    private Object value;

    private Entity entity;
    private Entity referrer;
    private String searched; 
    
        
    public SearchDefinitionRelation(Entity entity, String referrer, String operator, Object value) {
        try {
            ERList erlist = ERList.getInstance();
            this.entity = entity;
            this.referrer = (Entity) erlist.find(referrer);
            for (Relation relation : entity.getRelation()) {
                if (relation.getEntity() == this.referrer) {
                    searched = relation.getField();
                }
            }
            
        } catch (ModelException ex) {
            Logger.getLogger(SearchDefinitionRelation.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.attribute = Attribute.createAttribute("count", "int", false);
        this.operator = operator;
        this.value = this.attribute.cast(value);
    }

    public boolean compare(Value searched){
        return attribute.compare(operator, searched, value);
    }
    
    public boolean verifyCondition(Tuple tuple, String prefix) {
        try {
            HashFile hashFile = new HashFile(referrer);
            Search search = new Search(hashFile, null).search(searched, "=", tuple.getPK().getInfo()).compile(prefix);
            return this.compare(new Value(attribute, search.getPKs().size()));
        } catch (IOException ex) {
            Logger.getLogger(SearchDefinitionRelation.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
