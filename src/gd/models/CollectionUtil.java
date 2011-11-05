package gd.models;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtil {

    public static <T, G> Collection<G> filterAndProcess(Collection<T> target, Filter<T> filter, Process<T, G> process) {
        Collection<G> result = new ArrayList<G>();
        for (T element : target) {
            if (filter.apply(element)) {
                result.add(process.apply(element));
            }
        }
        return result;
    }

    public static <T> Collection<T> filter(Collection<T> target, Filter<T> filter) {
        return filterAndProcess(target, filter, new Process<T,T>(){
            public T apply(T type) {
                return type;
            }
        });
    }

    public static <T, G> Collection<G> process(Collection<T> target, Process<T, G> processing) {
        Filter filter = new Filter<T>() {
            public boolean apply(T type) {
                return true;
            }
        };
        return filterAndProcess(target, filter, processing);
    }

}
