package dm.models;

import dm.models.Attribute;

public interface SearchDefinitionInterface {
    
    public boolean verifyCondition(Tuple tuple, String prefix);

}
