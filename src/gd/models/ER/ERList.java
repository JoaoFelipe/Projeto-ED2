package gd.models.ER;

import gd.exceptions.ModelException;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gd.exceptions.NonUniqueException;
import gd.models.CollectionUtil;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class ERList {

    //No campo abaixo, foi usado o padrão Singleton
    private static ERList instance = null;
    
    private String fileName = "metadados.dat";
    private String prefix = "";
    private List<EntityRelationship> list = null;
    
    private static EntityRelationship selected = null;

    private ERList() throws ModelException {
    }

    private ERList(String name, String prefix) throws ModelException {
        this.fileName = name;
        this.prefix = prefix;
    }

    public EntityRelationship find(String name) throws ModelException {
        for (EntityRelationship er : getList()) {
            if (er.getName().equals(name)) {
                return er;
            }
        }
        return null;
    }

    public void removeByName(String name) throws ModelException {
        EntityRelationship e = find(name);
        remove(e);
    }

    public void remove(EntityRelationship e) {
        if (e instanceof Entity) {
            for (Relation relation : ((Entity) e).getRelation()) {
                this.remove(relation);
            }
        }
        e.delete(prefix);
        list.remove(e);
    }

    public void add(EntityRelationship e) throws ModelException {
        if (find(e.getName()) != null) {
            throw new NonUniqueException("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!");
        }
        list.add(e);
    }

    private void readFile() throws ModelException {
        try {
            list = new ArrayList<EntityRelationship>();
            DataInputStream file = null;
            String type = "";
            List<String> attrs = new ArrayList<String>();
            try {
                file = new DataInputStream(new FileInputStream(fileName));

                while (true) {
                    String text = file.readUTF();
                    if (text.equals("TABELA") || text.equals("REFERENCIA")) {
                        if (!type.equals("")) {
                            add(EntityRelationship.createER(type, attrs));
                        }
                        attrs.clear();
                        type = text;
                    } else {
                        attrs.add(text);
                    }
                }
            } catch (EOFException e) {
                add(EntityRelationship.createER(type, attrs));
            } catch (FileNotFoundException e) {
            } finally {
                if (file != null) {
                    file.close();
                }
            }
        } catch (IOException ex) {
            throw new ModelException(ex);
        }
    }

    public static ERList getInstance() throws ModelException {
        if (instance == null) {
            instance = new ERList();
            instance.readFile();
        }
        return instance;
    }

    public List<EntityRelationship> getList() throws ModelException {
        return list;
    }

    public List<String> getNames() {
        return (List<String>) CollectionUtil.process(list, ERCollection.processes.getName());
    }

    public List<String> getTableNames() {
        return (List<String>) CollectionUtil.filterAndProcess(list, ERCollection.filtros.entity(), ERCollection.processes.getName());
    }

    public void save() throws ModelException {
        try {
            DataOutputStream file = null;
            try {
                file = new DataOutputStream(new FileOutputStream(fileName));
                for (EntityRelationship er : list) {
                    er.save(file);
                }
            } catch (FileNotFoundException e) {
            } finally {
                if (file != null) {
                    file.close();
                }
            }
        } catch (IOException e) {
            throw new ModelException();
        }
    }

    
    public static EntityRelationship getSelected() {
        return selected;
    }

    public static void setSelected(EntityRelationship selected) {
        ERList.selected = selected;
    }

    
    
    
    // Os métodos abaixo foram criados para facilitar os testes. Não usar na aplicação
    public static void apagarInstancia() {
        instance = null;
    }
    
    public static ERList instanciarTeste(String nomeArquivo, String prefix) throws ModelException {
        if (instance == null) {
            instance = new ERList(nomeArquivo, prefix);
            instance.readFile();

        }
        return instance;
    }
    
}
