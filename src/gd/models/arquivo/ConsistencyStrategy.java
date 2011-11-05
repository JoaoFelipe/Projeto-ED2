package gd.models.arquivo;

import java.io.IOException;
import java.util.List;

public interface ConsistencyStrategy {
    
    final static int RESTRICT = 0;
    final static int CASCADE = 1;
    
    public boolean modify(Value value, List<Value> changes) throws IOException;
    public boolean remove(Value value) throws IOException;
    
}
