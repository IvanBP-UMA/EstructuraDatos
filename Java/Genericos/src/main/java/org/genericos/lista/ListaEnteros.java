package org.genericos.lista;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.StringJoiner;

public class ListaEnteros {
    private Integer[] lista;
    private int size;
    private static final int CAPACIDAD_INICIAL = 16;
    public ListaEnteros(int capacidad){
        if (capacidad<=0){throw new IllegalArgumentException("La capacidad tiene que ser positiva");}
        lista = new Integer[capacidad];
        size = 0;
    }
    public static ListaEnteros empty(){
        return new ListaEnteros(CAPACIDAD_INICIAL);
    }

    @SafeVarargs
    public static ListaEnteros of(Integer... elements){
        ListaEnteros newLista = new ListaEnteros(elements.length);
        for (Integer el: elements){
            newLista.add(el, newLista.size);
        }
        return newLista;
    }

    public void add(Integer newI, int pos){
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
