package org.uma.ed.dataestructure.stack;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class AbstractStack <T>{
    protected abstract Iterable<T> elements(); //Abstracto
    public abstract int size(); //Abstracto

    private boolean equals(Iterable<T> it1, Iterable<?> it2){ // recomendación para facilitar el equals
        var iter1 = it1.iterator();
        var iter2 = it2.iterator();

        while (iter1.hasNext() && iter2.hasNext() && Objects.equals(iter1.next(), iter2.next())){

        }
        return !(iter1.hasNext() && iter2.hasNext());
    }
    //Comprueba si son el mismo, o si es instanca de Abstract class, tiene el mismo tamaño y son iguales
    public boolean equals(Object obj) {
        return this == obj || obj instanceof AbstractStack<?> stack1 && size() == stack1.size() && equals(this.elements(), stack1.elements());
    }

        //Itera sobre los elementos
    public int hashCode(){
        int hc = 1;
        for (var el: elements()){
            hc += 7*hc+ el.hashCode();
        }
        return hc;
    }

    //Usa el StringJoiner y el this.getClass().getSimpleName() para poner el nombre de la clase primero
    public String toString(){
        String nombreClase = this.getClass().getSimpleName();
        StringJoiner sj = new StringJoiner("| ", nombreClase+ " (", ")");
        for (var el: elements()){
            sj.add(el.toString());
        }
        return sj.toString();
    }
}
