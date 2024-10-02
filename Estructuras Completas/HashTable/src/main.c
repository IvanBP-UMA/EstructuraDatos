#include "GestionAcceso/hash.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#define HASH_SIZE 7
int main(void)
{
    struct Hash *tablaHash = crearHashTable(HASH_SIZE);
    struct Persona *persona1 = crearPersona(1, "John", "Doe", "jonny@uma.es");
    struct Persona *persona2 = crearPersona(2, "Jane", "Smith", "janes@uma.es");
    struct Persona *persona3 = crearPersona(3, "Alice", "Johnson", "aljoh@uma.es");
    struct Persona *persona4 = crearPersona(4, "Bob", "Brown", "boby@uma.es");
    struct Persona *persona5 = crearPersona(5, "Charlie", "Davis", "chard@uma.es");
    struct Persona *persona6 = crearPersona(6, "Diana", "Evans", "diev@uma.es");
    struct Persona *persona7 = crearPersona(7, "Frank", "Green", "frankg@uma.es");
    struct Persona *persona8 = crearPersona(8, "Henry", "Irvine", "hentyi@uma.es");

    struct Persona *persona13 = crearPersona(13, "Nina", "Moore", "nimo@uma.es");
    struct Persona *persona14 = crearPersona(14, "Oscar", "Perez", "frankg@uma.es");
    struct Persona *persona21 = crearPersona(21, "Grace", "Hall", "graceh@uma.es");



    printf("Hash persona 1 %i", hashFunc(persona1->id, tablaHash->capacidad));
    insertar(tablaHash, persona1);
    printf("Hash persona 2 %i", hashFunc(persona2->id, tablaHash->capacidad));
    insertar(tablaHash, persona2);
    printf("Hash persona 3 %i", hashFunc(persona3->id, tablaHash->capacidad));
    insertar(tablaHash, persona3);
    printf("Hash persona 4 %i", hashFunc(persona4->id, tablaHash->capacidad));
    insertar(tablaHash, persona4);
    printf("Hash persona 5 %i", hashFunc(persona5->id, tablaHash->capacidad));
    insertar(tablaHash, persona5);
    printf("Hash persona 6 %i", hashFunc(persona6->id, tablaHash->capacidad));
    insertar(tablaHash, persona6);
    printf("Hash persona 7 %i", hashFunc(persona7->id, tablaHash->capacidad));
    insertar(tablaHash, persona7);

    printf("Hash persona 8 %i", hashFunc(persona8->id, tablaHash->capacidad));
    insertar(tablaHash, persona8);


    mostrar(tablaHash);

    //Empezamos los duplicados de hash

    printf("Hash persona 13 %i", hashFunc(persona13->id, tablaHash->capacidad));
    insertar(tablaHash, persona13);
    
    printf("Hash persona 14 %i", hashFunc(persona14->id, tablaHash->capacidad));
    insertar(tablaHash, persona14);

    printf("Hash persona 21 %i", hashFunc(persona21->id, tablaHash->capacidad));
    insertar(tablaHash, persona21);



    mostrar(tablaHash);

    //BUsamos una persona que no existe

    struct Persona *buscada = buscar(tablaHash, 22);
    assert(buscada == NULL);

    //Buscamos una persona que si existe
    buscada = buscar(tablaHash, 21);
    printf("Persona encontrada: \n");
    mostrarPersona(buscada);

    //Eliminamos una persona que está al final de una lista enlazada
    eliminar(tablaHash, 21);
    mostrar(tablaHash);

    //Eliminamos una persona que está al principio de una lista enlazada
    eliminar(tablaHash, 8);
    mostrar(tablaHash);

    //Eliminamos una persona que está sola en la lista enlazada
    eliminar(tablaHash, 1);
    mostrar(tablaHash);

    //Eliminamos la tabla hash al completo  
    liberarHash(&tablaHash);
    mostrar(tablaHash);
    return 0;
}

