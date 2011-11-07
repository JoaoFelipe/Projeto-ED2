package dm.models;

public interface Filter<T> {
    
    boolean apply(T type);
    
}
