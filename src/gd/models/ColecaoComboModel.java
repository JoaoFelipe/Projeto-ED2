/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models;

import java.util.Collection;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Joao
 */
public class ColecaoComboModel {
    public static <T, G> DefaultComboBoxModel filtrarEProcessar(Collection<T> target, Filtro<T> filtro, Processo<T, G> processo, DefaultComboBoxModel model) {
        model.removeAllElements();
        int i = 0;
        for (T element : target) {
            if (filtro.aplicar(element)) {
                model.insertElementAt(processo.processar(element), i);
                i++;
            }
        }
        return model;
    }

    public static <T> DefaultComboBoxModel filtrar(Collection<T> target, Filtro<T> filtro, DefaultComboBoxModel model) {
        Processo processo = new Processo<T,T>(){
            public T processar(T type) {
                return type;
            }
        };
        return filtrarEProcessar(target, filtro, processo, model);
    }

    public static <T, G> DefaultComboBoxModel processar(Collection<T> target, Processo<T, G> processo, DefaultComboBoxModel model) {
        Filtro filtro = new Filtro<T>() {
            public boolean aplicar(T type) {
                return true;
            }
        };
        return filtrarEProcessar(target, filtro, processo, model);
    }

    public static <T> DefaultComboBoxModel converter(Collection<T> target, DefaultComboBoxModel model) {
        Processo processo = new Processo<T,T>(){
            public T processar(T type) {
                return type;
            }
        };
        Filtro filtro = new Filtro<T>() {
            public boolean aplicar(T type) {
                return true;
            }
        };
        return filtrarEProcessar(target, filtro, processo, model);
    }
}
