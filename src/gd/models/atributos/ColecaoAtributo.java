package gd.models.atributos;

import gd.models.Processo;

public class ColecaoAtributo {

    public static class processos {

        public static Processo<Atributo, String> getNome(){
            return new Processo<Atributo, String>(){
                public String processar(Atributo a) {
                    return a.getNome();
                }
            };
        }

        public static Processo<Atributo, Class> getClasse(){
            return new Processo<Atributo, Class>(){
                public Class processar(Atributo a) {
                    return a.getClasse();
                }
            };
        }

    }
    
}
