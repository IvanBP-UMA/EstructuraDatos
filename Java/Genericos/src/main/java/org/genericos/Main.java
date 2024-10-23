package org.genericos;
import org.genericos.claseschorras.Pelicula;
import org.genericos.lista.*;


public class Main {
    public static void main(String[] args) {
        ListaEnteros le = ListaEnteros.of(1,4,3,5,7,4);
        System.out.println(le.toString());
        le.add(8,5);
        System.out.println(le.toString());

        /*ListaPeliculas lp = ListaPeliculas.of(new Pelicula("Lo que el viento se llevo",1970,2),new Pelicula("Alien",1884,4));
        System.out.println(lp.toString());
        lp.add(new Pelicula("Gamma", 2034, 123),0);
        System.out.println(lp.toString());*/

    }
}