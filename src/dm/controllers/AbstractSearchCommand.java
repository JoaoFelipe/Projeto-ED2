package dm.controllers;

import dm.models.Entity;
import dm.models.Search;

abstract public class AbstractSearchCommand implements Command {

    private Search search;

    public AbstractSearchCommand(Search search) {
        this.search = search;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }
    
    public Entity getEntity() {
        return search.getHashFile().getEntity();
    }
    
    public abstract Command execute();
    
}
