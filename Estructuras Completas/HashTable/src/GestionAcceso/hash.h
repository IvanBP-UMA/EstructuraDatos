/**
 * @file hash.h
 * @brief Header file for hash table operations.
 * 
 * This file contains the definitions and function declarations for managing
 * a hash table that stores `Persona` structures. The hash table supports
 * operations such as insertion, search, deletion, and display of entries.
 * 
 */
 
#ifndef HASH_H
#define HASH_H
#include "GestionAcceso/persona.h"

struct Node{
    struct Persona *persona;
    struct Node *next;
};

struct Hash{
    int capacidad;
    int tamano;
    struct Node **listaPersonas;
};

    /**
     * @brief Creates a new hash table with the specified capacity.
     * 
     * This function allocates memory for a hash table and initializes it
     * with the given capacity. The hash table can be used to store and 
     * retrieve key-value pairs efficiently.
     * 
     * @param capacidad The capacity of the hash table, i.e., the number of 
     *                  buckets it will contain.
     * @return A pointer to the newly created hash table.
     */
    struct Hash * crearHashTable(int capacidad);

    /**
     * @brief Computes the hash value for a given ID.
     * 
     * This function takes an integer ID and computes its hash value based on the provided capacity.
     * The hash value is used to determine the index in a hash table where the ID should be stored.
     * 
     * @param id The integer ID to be hashed.
     * @param capacidad The capacity of the hash table.
     * @return The computed hash value for the given ID.
     */
    int hashFunc(int id, int capacidad);

    /**
     * @brief Inserts a Persona into the given Hash table.
     * 
     * This function takes a pointer to a Hash table and a pointer to a Persona
     * and inserts the Persona into the Hash table.
     * 
     * @param hash Pointer to the Hash table where the Persona will be inserted.
     * @param persona Pointer to the Persona to be inserted into the Hash table.
     */
    void insertar(struct Hash *hash, struct Persona *persona);

    /**
     * @brief Searches for a person in the hash table by their ID.
     * 
     * This function takes a pointer to a hash table and an ID, and searches
     * for a person with the given ID in the hash table. If the person is found,
     * a pointer to the corresponding `Persona` structure is returned. If the
     * person is not found, the function returns `nullptr`.
     * 
     * @param hash A pointer to the hash table.
     * @param id The ID of the person to search for.
     * @return A pointer to the `Persona` structure if found, otherwise `nullptr`.
     */
    struct Persona * buscar(struct Hash *hash, int id);

    /**
     * @brief Elimina una entrada del hash dado su identificador.
     * 
     * @param hash Puntero a la estructura Hash de la cual se eliminará la entrada.
     * @param id Identificador de la entrada que se desea eliminar.
     */
    void eliminar(struct Hash *hash, int id);

    /**
     * @brief Displays the contents of the given hash table.
     * 
     * This function takes a pointer to a Hash structure and prints its contents
     * to the standard output. The exact format of the output depends on the 
     * implementation details of the Hash structure.
     * 
     * @param hash A pointer to the Hash structure to be displayed.
     */
    void mostrar(const struct Hash *hash);


    /**
     * @brief Frees the memory allocated for the hash table.
     * 
     * This function releases all the resources associated with the given hash table,
     * including any dynamically allocated memory for the table itself and its contents.
     * 
     * @param hash A pointer to the reference to the Hash structure to be freed.
     */
    void liberarHash(struct Hash **hash);
     



#endif // HASH_H
