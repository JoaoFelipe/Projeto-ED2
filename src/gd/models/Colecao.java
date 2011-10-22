package gd.models;

import java.util.ArrayList;
import java.util.Collection;

public class Colecao {

    public static <T, G> Collection<G> filtrarEProcessar(Collection<T> target, Filtro<T> filtro, Processo<T, G> processo) {
        Collection<G> result = new ArrayList<G>();
        for (T element : target) {
            if (filtro.aplicar(element)) {
                result.add(processo.processar(element));
            }
        }
        return result;
    }

    public static <T> Collection<T> filtrar(Collection<T> target, Filtro<T> filtro) {
        return filtrarEProcessar(target, filtro, new Processo<T,T>(){
            public T processar(T type) {
                return type;
            }
        });
    }

    public static <T, G> Collection<G> processar(Collection<T> target, Processo<T, G> processo) {
        Filtro filtro = new Filtro<T>() {
            public boolean aplicar(T type) {
                return true;
            }
        };
        return filtrarEProcessar(target, filtro, processo);
    }

}
