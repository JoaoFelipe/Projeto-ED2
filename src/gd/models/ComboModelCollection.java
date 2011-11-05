package gd.models;

import java.util.Collection;
import javax.swing.DefaultComboBoxModel;

public class ComboModelCollection {
    
    public static <T, G> DefaultComboBoxModel filterAndProcess(Collection<T> target, Filter<T> filter, Process<T, G> process, DefaultComboBoxModel model) {
        model.removeAllElements();
        int i = 0;
        for (T element : target) {
            if (filter.apply(element)) {
                model.insertElementAt(process.apply(element), i);
                i++;
            }
        }
        return model;
    }

    public static <T> DefaultComboBoxModel filter(Collection<T> target, Filter<T> filter, DefaultComboBoxModel model) {
        Process process = new Process<T,T>(){
            public T apply(T type) {
                return type;
            }
        };
        return filterAndProcess(target, filter, process, model);
    }

    public static <T, G> DefaultComboBoxModel process(Collection<T> target, Process<T, G> process, DefaultComboBoxModel model) {
        Filter filtro = new Filter<T>() {
            public boolean apply(T type) {
                return true;
            }
        };
        return filterAndProcess(target, filtro, process, model);
    }

    public static <T> DefaultComboBoxModel convert(Collection<T> target, DefaultComboBoxModel model) {
        Process process = new Process<T,T>(){
            public T apply(T type) {
                return type;
            }
        };
        Filter filter = new Filter<T>() {
            public boolean apply(T type) {
                return true;
            }
        };
        return filterAndProcess(target, filter, process, model);
    }
    
}
