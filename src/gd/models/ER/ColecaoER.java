package gd.models.ER;

import gd.models.Filtro;
import gd.models.Processo;

public class ColecaoER {

    public static class processos {

        public static Processo<EntidadeRelacionamento, String> getNome(){
            return new Processo<EntidadeRelacionamento, String>(){
                public String processar(EntidadeRelacionamento er) {
                    return er.getNome();
                }
            };
        }

    }

    public static class filtros {

        public static Filtro<EntidadeRelacionamento> entidade() {
            return new Filtro<EntidadeRelacionamento>(){
                public boolean aplicar(EntidadeRelacionamento er) {
                    return (er instanceof Entidade);
                }
            };
        }
        
    }
    
}
