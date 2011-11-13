package dm.models;

import dm.models.Entity;
import dm.models.Attribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search {
    
    //Padrão Builder foi utilizado nesta classe, para que possam ser acumuladas as
    //condições sob as quais será realizada uma busca, e estas serão compiladas no fim

    private HashFile file = null;
    private List<SearchDefinitionInterface> searches = null;
    private List<Value> pks = null;

    public Search(HashFile file, List<Value> pks) {
        this.file = file;
        this.pks = pks;
        this.searches = new ArrayList<SearchDefinitionInterface>();
    }
    
    public Search(Entity entity, List<Value> pks) {
        this.file = new HashFile(entity);
        this.pks = pks;
        this.searches = new ArrayList<SearchDefinitionInterface>();
    }

    public Search(Entity entity, List<Value> pks, int mode) {
        this.file = new HashFile(entity);
        this.file.setStrategy(mode);
        this.pks = pks;
        this.searches = new ArrayList<SearchDefinitionInterface>();
    }

    public Search search(Attribute attr, String operator, Object value) {
        if (attr.isPK() && operator.equals("=")){
            if (pks == null) {
                pks = new ArrayList<Value>();
                pks.add(new Value(attr, value));
            } else {
                pks.retainAll(Arrays.asList(new Value(attr, value)));
            }
        } else {
            searches.add(new SearchDefinition(attr, operator, value));
        }
        return this;
    }
    
    public Search search(String strAttr, String operator, Object value) {
        if (strAttr.startsWith("#")){
            searches.add(new SearchDefinitionRelation(file.getEntity(), strAttr.substring(1), operator, value));
            return this;
        } else {
            Attribute attr = file.getEntity().getAttributeByName(strAttr);
            return search(attr, operator, value);
        }
        
    }

    public Search compile() throws IOException{
        return this.compile("");
    }

    public Search compile(String prefix) throws IOException{
        file.open(prefix);
        if (getPKs() == null){
            searchWhenNull(prefix);
        } else {
            search(prefix);
        }
        file.close();
        searches.clear();
        return this;
    }

    private void searchWhenNull(String prefix) throws IOException {
        pks = new ArrayList<Value>();
        for (int i = 0; i < file.getSize(); i++) {
            file.seek(i);
            Tuple tuple = file.readTuple();
            if (tuple.isUsed() && verifyCondition(tuple, prefix)) {
                getPKs().add(tuple.getPK());
            }
        }
    }

    private void search(String prefix) throws IOException {
        List<Value> tempPKs = new ArrayList<Value>();
        for (Value value : getPKs()) {
            Result result = file.find(value);
            if (result.isFound()) {
                file.seek(result.getPosition());
                Tuple tuple = file.readTuple();
                if (tuple.isUsed() && verifyCondition(tuple, prefix)) {
                    tempPKs.add(tuple.getPK());
                }
            }
        }
        pks = tempPKs;
    }

    private boolean verifyCondition(Tuple tuple, String prefix) {
        boolean condition = true;
        for (SearchDefinitionInterface search : searches) {
            condition = condition && search.verifyCondition(tuple, prefix);
        }
        return condition;
    }

    public List<Value> getPKs() {
        return pks;
    }
    
    public HashFile getHashFile() {
        return this.file;
    }

}
