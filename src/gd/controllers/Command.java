package gd.controllers;

public interface Command {
    
    //Interface usada para o padrão Command, que foi usado para que seja possível
    //alterar funcionalidades de certas componentes do projeto em tempo de execução
    
    public Command execute();
    
}
