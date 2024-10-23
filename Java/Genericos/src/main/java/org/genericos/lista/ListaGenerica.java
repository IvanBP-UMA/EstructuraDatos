package org.genericos.lista;

import java.util.Arrays;
import java.util.StringJoiner;

public class ListaGenerica<T>{
    private T[] lista;
    private int size;
    private static final int CAPACIDAD_INICIAL = 16;

    public ListaGenerica(int capacidad){
        if (capacidad<=0){throw new IllegalArgumentException("La capacidad tiene que ser positiva");}
        lista = (T[]) new Object[CAPACIDAD_INICIAL];
        size = 0;
    }
    public static <T> ListaGenerica<T> empty(){
        return new ListaGenerica<>(CAPACIDAD_INICIAL);
    }

    @SafeVarargs
    public static ListaGenerica of(Integer... elements){
        ListaEnteros newLista = new ListaEnteros(elements.length);
        for (Integer el: elements){
            newLista.add(el, newLista.size);
        }
        return newLista;
    }

    public void add(T newI, int pos){
        if (pos>this.size){throw new ArrayIndexOutOfBoundsException("Not valid pos");}
        if (this.size == lista.length){
            lista = Arrays.copyOf(lista, size*2);
        }
        for (int i = size; i>pos; i--){
            lista[i] = lista[i-1];
        }
        lista[pos] = newI;
    }

    public String toString(){
        StringJoiner sj = new StringJoiner(", ","Lista Enteros (",")");
        for (int i = 0; i <size ; i++) {
            sj.add(lista[i].toString());
        }
        return sj.toString();
    }
}
