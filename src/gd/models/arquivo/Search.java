package gd.models.arquivo;

import gd.models.ER.Entity;
import gd.models.atributos.Attribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search {
    
    //Padrão Builder foi utilizado nesta classe, para que possam ser acumuladas as
    //condições sob as quais será realizada uma busca, e estas serão compiladas no fim

    private HashFile file = null;
    private List<SearchDefinition> searches = null;
    private List<Value> pks = null;

    public Search(HashFile file, List<Value> pks) {
        this.file = file;
        this.pks = pks;
        this.searches = new ArrayList<SearchDefinition>();
    }
    
    public Search(Entity entity, List<Value> pks) {
        this.file = new HashFile(entity);
        this.pks = pks;
        this.searches = new ArrayList<SearchDefinition>();
    }


    public Search search(Attribute attr, String operator, Object value) {
        if (attr.getPK() && operator.equals("=")){
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

    public Search compile() throws IOException{
        return this.compile("");
    }

    public Search compile(String prefix) throws IOException{
        file.open(prefix);
        if (getPKs() == null){
            searchWhenNull();
        } else {
            search();
        }
        file.close();
        searches.clear();
        return this;
    }

    private void searchWhenNull() throws IOException {
        pks = new ArrayList<Value>();
        for (int i = 0; i < file.getSize(); i++) {
            file.seek(i);
            Tuple tuple = file.readTuple();
            if (tuple.isUsed() && verifyCondition(tuple)) {
                getPKs().add(tuple.getPK());
            }
        }
    }

    private void search() throws IOException {
        List<Value> tempPKs = new ArrayList<Value>();
        for (Value value : getPKs()) {
            Result result = file.find(value);
            if (result.isFound()) {
                file.seek(result.getPosition());
                Tuple tuple = file.readTuple();
                if (tuple.isUsed() && verifyCondition(tuple)) {
                    tempPKs.add(tuple.getPK());
                }
            }
        }
        pks = tempPKs;
    }

    private boolean verifyCondition(Tuple tuple) {
        boolean condition = true;
        for (Value value : tuple.getValues()) {
            for (SearchDefinition search : searches) {
                if (value.getType() == search.getAttribute()){
                    condition = condition && search.compare(value);
                }
            }
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
