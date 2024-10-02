#include <stdio.h>
#include <stdlib.h>
#include "hash.h"

struct Hash * crearHashTable(int capacidad){
    struct Hash* newHash = (struct Hash*)malloc(sizeof(struct Hash));
    if (newHash == NULL){
        printf("No memory");
    }
    newHash->capacidad = capacidad;
    newHash->tamano = 0;
    newHash->listaPersonas = (struct Node*)malloc(sizeof(struct Node)*capacidad);
    if(newHash->listaPersonas == NULL){
        printf("No memory");
    }
    for (int i = 0; i<capacidad; i++){
        newHash->listaPersonas[i] = NULL;
    }
    return newHash;
}

int hashFunc(int id, int capacidad){
    return id % capacidad;
}

void insertar(struct Hash *hash, struct Persona *persona){
    int personHash = hashFunc(persona->id, hash->capacidad);
    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
    if (newNode == NULL){
        printf("No memory");
    }

    newNode->persona = persona;
    newNode->next = NULL;

    if (hash->listaPersonas[personHash] != NULL){
        struct Node* aux = hash->listaPersonas[personHash];
        while (aux->next != NULL){
            aux = aux->next;
        }
        aux->next = newNode;
    }else{
        hash->listaPersonas[personHash] = newNode;
    }
}

struct Persona * buscar(struct Hash *hash, int id){
    struct Persona* res = NULL;
    int personHash = hashFunc(id, hash->capacidad);

    struct Node* aux = hash->listaPersonas[personHash];
    while (aux != NULL && res == NULL){
        if (aux->persona->id == id){res = aux->persona;}
        aux = aux->next;
    }    
    return res;
}

void eliminar(struct Hash *hash, int id){
    int personHash = hashFunc(id, hash->capacidad);

    struct Node** aux = hash->listaPersonas[personHash];
    while ((*aux)->persona->id != id){
        aux = &(*aux)->next;
    }
    free(*aux); 
}