package gd.controllers;

import gd.models.arquivo.Consulta;

abstract public class ConsultaCommand extends Command {

    Consulta consulta;

    public ConsultaCommand(Consulta consulta) {
        this.consulta = consulta;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    
    public abstract Command execute(Object... arg);
    
}
