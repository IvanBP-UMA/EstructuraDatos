#include "canales.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct T_Canal* buscarCanalByName(struct T_Canal **canales, char *nombreCanal){
    if (canales == NULL){
        printf("ERROR: NULL ARG");
        exit(1);
    }
    if (strlen(nombreCanal) > MAX_NOMBRE){
        exit(1);
    }

    struct T_Canal* res = NULL;
    while(*canales != NULL && strcmp(nombreCanal, (*canales)->nombre) != 0){
        *canales = (*canales)->sig;
    }
    if (*canales != NULL){
        res = *canales;
    }
    return res;
}

/**
 * @brief Crea un nuevo canal.
 *
 * Esta función se encarga de inicializar la lista de canales.
 *
 * @param canales Doble puntero a una estructura T_Canal donde se almacenarán los canales en un futuro.
 */
void crear(struct T_Canal **canales){
    if (canales != NULL){
        (*canales) = NULL;
    }
}

/**
 * @brief Destruye la lista de canales.
 *
 * Esta función libera la memoria asignada para cada canal, dejando el @p canales apuntando a NULL.
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 */
void destruir(struct T_Canal **canales){
    struct T_Canal* aux;
    while (*canales != NULL){
        aux = (*canales)->sig;
        free(*canales);
        *canales = aux;
    }
}

/**
 * @brief Muestra la información de los canales.
 *
 * Esta función toma un puntero a una estructura T_Canal y muestra la información
 * contenida en los canales.
 *
 * @param canales Puntero a una estructura T_Canal que contiene la información de los canales.
 */
void mostrar(struct T_Canal *canales){
    while (canales != NULL){
        printf("Nombre: %s, Posicion: %i, Tipo: %i\n", canales->nombre, canales->pos, canales->tipo);
        canales = canales->sig;
    }
    printf("\n");
}

/**
 * @brief Inserta un nuevo canal en la lista de canales.
 *
 * Esta función inserta un nuevo canal en la posición especificada dentro de la lista de canales. Si en la lista de canales ya existe un canal en dicha
posicion, el nuevo se insertar en la posición indicada y el canal antiguo (y los consecutivos en posiciones mayores) se desplazaran una posición.
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 * @param nombreCanal Nombre del canal a insertar.
 * @param posCanal Posición en la que se debe insertar el nuevo canal.
 * @param tipoCanal Tipo del canal a insertar.
 */
void insertar(struct T_Canal **canales, char *nombreCanal, int posCanal, enum Tipo tipoCanal){
    if (canales == NULL){
        printf("ERROR: NULL ARG");
        exit(1);
    }
    if (strlen(nombreCanal) > MAX_NOMBRE){
        exit(1);
    }

    struct T_Canal* newCanal = (struct T_Canal*)malloc(sizeof(struct T_Canal));
    if (newCanal == NULL){
        printf("no memory");
        exit(1);
    }

    strcpy(newCanal->nombre, nombreCanal);
    newCanal->pos = posCanal;
    newCanal->tipo = tipoCanal;

    while (*canales != NULL && (*canales)->pos<posCanal){
        canales = &(*canales)->sig;
    }
    newCanal->sig = (*canales);
    *canales = newCanal;
    canales = &(*canales)->sig;
    while (*canales != NULL){
        (*canales)->pos++;
        canales = &(*canales)->sig;
    }
}


/**
 * @brief Elimina un canal de la lista de canales.
 *
 * Esta función busca un canal por su nombre en la lista de canales proporcionada
 * y lo elimina si es encontrado.
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 * @param nombreCanal Nombre del canal a eliminar.
 * @return int Retorna 0 si el canal fue eliminado exitosamente, o un valor negativo si ocurrió un error.
 */
int eliminar(struct T_Canal **canales, char *nombreCanal){
    if (canales == NULL){
        printf("ERROR: NULL ARG");
        return -1;
    }
    if (strlen(nombreCanal) > MAX_NOMBRE){
        return -1;
    }


    while(*canales != NULL && strcmp(nombreCanal, (*canales)->nombre) != 0){
        canales = &(*canales)->sig;
    }
    if (*canales != NULL){
        struct T_Canal* aux = (*canales)->sig;
        free(*canales);
        *canales = aux;
    }
    return 0;
}

/**
 * @brief Extrae un canal de radio de la lista de canales de origen.
 *
 * @param origen Dirección de memoria dónde está el puntero a la cabeza de la lista.
 * @return Dirección de memoria de la lista de solo canales de radio extraída. Debes mover, no pedir nueva memoria para maxima nota.
 */
struct T_Canal *extraerRadio(struct T_Canal **origen){
    struct T_Canal* radios = (struct T_Canal*)malloc(sizeof(struct T_Canal));
    if (radios == NULL){
        exit(1);
    }
    crear(&radios);

    while(*origen != NULL){
        if ((*origen)->tipo == 1){
            insertar(&radios, (*origen)->nombre, (*origen)->pos, 1);
            eliminar(origen, (*origen)->nombre);
        }else{
            origen = &(*origen)->sig;
        }
    }

    return radios;
}
