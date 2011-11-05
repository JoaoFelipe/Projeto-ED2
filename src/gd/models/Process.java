package gd.models;

public interface Process<T, G> {
    
    G apply(T type);
    
}
