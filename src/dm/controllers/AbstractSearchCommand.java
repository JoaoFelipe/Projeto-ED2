package dm.controllers;

import dm.models.file.Search;

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
    
    public abstract Command execute();
    
}
