package gd.models;

public interface Processo<T, G> {
    
    G processar(T type);
    
}
