package dm.models;

import dm.exceptions.ModelException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import dm.exceptions.NonUniqueException;
import dm.exceptions.NotFoundException;
import dm.models.CollectionUtil;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;

public class Entity extends EntityRelationship {

    private String name;
    private List<Attribute> attributes = null;
    private List<Relation> relations = null;
    private int numberOfTuples = 0;

    public Entity(List<String> definition) throws ModelException {
        this.name = definition.get(0);
        this.attributes = new ArrayList<Attribute>();
        this.relations = new ArrayList<Relation>();
        
        for (int i = 1; i < definition.size(); i++) {
            String def = definition.get(i);
            Attribute attr = Attribute.createAttribute(def);
            attributes.add(attr);
        }

        this.validate();
    }

    public Entity(String name, List<Attribute> attrs) throws ModelException {
        this.name = name;
        this.attributes = attrs;
        this.relations = new ArrayList<Relation>();
        this.validate();
    }

    public void validate() throws ModelException { 
        if (name == null || name.isEmpty()) {
            throw new NotFoundException("O nome não pode estar em branco");
        }
        name = name.trim();
        if (name == null || name.isEmpty()) {
            throw new NotFoundException("O nome não pode estar em branco");
        }
        name = name.toLowerCase(Locale.ENGLISH);
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        name = Normalizer.normalize(name, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        
        int count = 0;
        Set<String> set = new HashSet<String>();
        for (Attribute attr : attributes) {
            if (!set.add(attr.getName())){
                throw new NonUniqueException("Atributo não é único");
            }
            if (attr.isPK()) {
                count++;
            }
        }
        if (count == 0) {
            throw new NotFoundException("Nenhuma chave primária encontrada");
        }
        if (count > 1) {
            throw new NonUniqueException("Mais de uma chave primária encontrada");
        }
    }



    @Override
    public void save(DataOutputStream out) throws ModelException {
        try {
            out.writeUTF("TABELA");
            out.writeUTF(name);
            for (Attribute attr : attributes) {
                out.writeUTF(attr.getRepr());
            }
        } catch (IOException e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.attributes == null) ? (other.attributes != null) : (!this.attributes.equals(other.attributes))) {
            return false;
        }

        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Relation> getRelation(){
        return relations;
    }

    public List<Attribute> getAttributes(){
        return attributes;
    }

    public List<String> getSearchableAttributes(){
        List<String> resp = new ArrayList<String>();
        resp.addAll(CollectionUtil.process(attributes, AttributeCollection.processes.getName()));
        for (Relation relation : relations) {
            if (relation.getEntity() == this){
//                resp.add(relacionamento.getEntidadeReferenciada().getName()+"-"+relacionamento.getCampoReferenciado());
            } else {
                resp.add("#"+relation.getEntity().getName());
            }
        }
        return resp;
    }

    public int getSize(){
        int resp = 0;
        for (Attribute attr : attributes) {
            resp += attr.getSize();
        }
        return resp;
    }

    public Attribute getPk(){
        for (Attribute attr : attributes) {
            if (attr.isPK()) {
                return attr;
            }
        }
        return null;
    }
    
    public Attribute getAttributeByName(String name){
        for (Attribute attr : attributes) {
            if (attr.getName().equals(name)) {
                return attr;
            }
        }
        return null;
    }


    public int getNumberOfTuples() {
        return numberOfTuples;
    }

    public void setNumberOfTuples(int numberOfTuples) {
        this.numberOfTuples = numberOfTuples;
    }

    public Relation getRelationFK(String attribute) {
        for (Relation relation : getRelation()) {
            if (relation.getField().equals(attribute)){
                 return relation;    
            }
        }
        return null;
    }
    
}
