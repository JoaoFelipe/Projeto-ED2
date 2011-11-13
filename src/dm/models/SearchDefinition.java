package dm.models;

import dm.models.Attribute;

public class SearchDefinition implements SearchDefinitionInterface {
    
    private Attribute attribute;
    private String operator;
    private Object value;


    public SearchDefinition(Attribute attr, String operator, Object value) {
        this.attribute = attr;
        this.operator = operator;
        this.value = attr.cast(value);
    }

    public boolean compare(Value searched){
        return attribute.compare(operator, searched, value);
    }
    
    public boolean verifyCondition(Tuple tuple, String prefix) {
        boolean condition = true;
        for (Value value : tuple.getValues()) {
            if (value.getType() == attribute){
                condition = condition && this.compare(value);
            }
        }
        return condition;
    }

}
