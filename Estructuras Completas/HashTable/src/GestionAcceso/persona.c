#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "persona.h"

struct Persona * crearPersona(int id, char *nombre, char *apellido, char *email){
    struct Persona* newPersona = (struct Persona*)malloc(sizeof(struct Persona));
    if (newPersona == NULL){
        printf("No memory");
    }

    newPersona->id = id;
    strcpy(newPersona->nombre, nombre);
    strcpy(newPersona->apellido, apellido);
    strcpy(newPersona->email, email);
    return newPersona;
}

void mostrarPersona(struct Persona *persona){
    printf("ID: %i; Nombre: %s; Apellido: %s; Email: %s\n", persona->id, persona->nombre, persona->apellido, persona->email);
}

void liberarPersona(struct Persona **persona){
    if (*persona != NULL){
        free(*persona);
        *persona = NULL;
    }
}