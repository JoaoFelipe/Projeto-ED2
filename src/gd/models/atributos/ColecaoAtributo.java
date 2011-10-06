/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.atributos;

import gd.models.Processo;

/**
 *
 * @author Joao
 */
public class ColecaoAtributo {

    public static class processos {

        public static Processo<Atributo, String> getNome(){
            return new Processo<Atributo, String>(){
                public String processar(Atributo a) {
                    return a.getNome();
                }
            };
        }

    }
}
