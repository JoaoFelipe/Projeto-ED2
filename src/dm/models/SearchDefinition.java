package dm.models;

import dm.models.Attribute;

public class SearchDefinition {
    
    private Attribute attribute;
    private String operator;
    private Object value;


    public SearchDefinition(Attribute attr, String operator, Object value) {
        this.attribute = attr;
        this.operator = operator;
        this.value = attr.cast(value);
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attr) {
        this.attribute = attr;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = attribute.cast(value);
    }

    public boolean compare(Value searched){
        return attribute.compare(operator, searched, value);
    }

}
