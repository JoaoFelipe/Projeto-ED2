package dm.models.ER;

import dm.exceptions.ModelException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import dm.exceptions.NotFoundException;

public class Relation extends EntityRelationship {

    private Entity entity;
    private String field;
    private Entity referencedEntity;
    private String referencedField;

    public Relation(EntityRelationship entity, String field, EntityRelationship referencedEntity, String referencedField) throws ModelException {
        this.entity = (Entity) entity;
        this.field = field;
        this.referencedEntity = (Entity) referencedEntity;
        this.referencedField = referencedField;
        this.validate();
    }
 
    public Relation(List<String> defs) throws ModelException{
        this(ERList.getInstance().find(defs.get(0)), defs.get(1), ERList.getInstance().find(defs.get(2)), defs.get(3));
        if (defs.size() != 4) {
            throw new NotFoundException("Elementos não encontrados");
        }
        this.validate();
    }

    public void validate() throws ModelException {
        if (entity == null) {
            throw new NotFoundException("Entidade não encontrada");
        }
        if (getReferencedEntity() == null) {
            throw new NotFoundException("Entidade Referenciada não encontrada");
        }
        if (entity.findAttribute(getField()) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade");
        }
        if (getReferencedEntity().findAttribute(referencedField) == null) {
            throw new NotFoundException("Atributo não encontrado na Entidade Referenciada");
        }
    }

    @Override
    public void save(DataOutputStream out) throws ModelException {
        try {
            out.writeUTF("REFERENCIA");
            out.writeUTF(entity.getName());
            out.writeUTF(getField());
            out.writeUTF(getReferencedEntity().getName());
            out.writeUTF(referencedField);
        } catch (IOException ex) {
            throw new ModelException(ex);
        }
    }

    public Object[] getRow(){
        return new Object[]{
           entity.getName(),
           getField(), getReferencedEntity().getName(),
           referencedField
        };
    }

    @Override
    public String getName() {
        return "R: "+entity.getName()+" "+getField();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relation other = (Relation) obj;
        if (this.entity != other.entity && (this.entity == null || !this.entity.equals(other.entity))) {
            return false;
        }
        if ((this.getField() == null) ? (other.getField() != null) : !this.field.equals(other.field)) {
            return false;
        }
        if (this.getReferencedEntity() != other.getReferencedEntity() && (this.getReferencedEntity() == null || !this.referencedEntity.equals(other.referencedEntity))) {
            return false;
        }
        if ((this.referencedField == null) ? (other.referencedField != null) : !this.referencedField.equals(other.referencedField)) {
            return false;
        }
        return true;
    }

    public void addToEntity(){
        entity.getRelation().add(this);
        getReferencedEntity().getRelation().add(this);
    }

    public void delete(){}

  
    public Entity getEntity(){
        return entity;
    }

    public Entity getReferencedEntity(){
        return referencedEntity;
    }

    public String getReferencedField() {
        return referencedField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setReferencedEntity(Entity referencedEntity) {
        this.referencedEntity = referencedEntity;
    }

    


   




}
