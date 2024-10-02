/**
 * @file persona.h
 * @brief Header file for managing Persona structures and related functions.
 *
 * This file contains the definition of the Persona structure and declarations
 * for functions to create, display, and free Persona instances.
 */

#ifndef PERSONA_H
#define PERSONA_H


 /**
    * @struct Persona
    * @brief Structure to store personal information.
    *
    * This structure holds personal information including an ID, name, surname,
    * and email address.
    *
    * @var Persona::id
    * Member 'id' contains the unique identifier for the person.
    * @var Persona::nombre
    * Member 'nombre' contains the first name of the person.
    * @var Persona::apellido
    * Member 'apellido' contains the surname of the person.
    * @var Persona::email
    * Member 'email' contains the email address of the person.
    */


struct Persona{
    int id;
    char nombre[50];
    char apellido[50];
    char email[50];
};


 /**
    * @brief Creates a new Persona instance.
    *
    * This function allocates memory for a new Persona structure and initializes
    * it with the provided id, name, surname, and email.
    *
    * @param id The unique identifier for the person.
    * @param nombre The first name of the person.
    * @param apellido The surname of the person.
    * @param email The email address of the person.
    * @return A pointer to the newly created Persona structure.
    */
struct Persona * crearPersona(int id, char *nombre, char *apellido, char *email);

 /**
    * @brief Displays the information of a Persona.
    *
    * This function prints the details of the given Persona structure to the
    * standard output.
    *
    * @param persona A pointer to the Persona structure to be displayed.
    */
void mostrarPersona(struct Persona *persona);

/**
 * @brief Frees the memory allocated for a Persona structure.
 *
 * This function takes a pointer to a pointer to a Persona structure and
 * deallocates the memory associated with it. After calling this function,
 * the pointer to the Persona structure will be set to NULL.
 *
 * @param persona A double pointer to the Persona structure to be freed.
 */
void liberarPersona(struct Persona **persona);


    

#endif // HASH_H
