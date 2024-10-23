/*
NOMBRE: Iván Bezares Pino
*/

#include <stdlib.h>
#include <stdio.h>
#include "lista_enlazada_doble.h"

int main(void) {

/*
Lista de funcionalidades a probar.
- Crear una playList vacía
- Añadir una canción al principio de la playList
- Añadir una canción al final de la playList
- Añadir una canción en una PlayList ordenada
- Mostrar la playList
- Ordenar alfabéticamente la PlayList
- Eliminar Todas las canciones de la playList
- Eliminar una canción de la playList
- Eliminar la primera canción de la playList
- Saber cuál es la canción actual
- Pasar a la siguiente canción
- Pasar a la canción anterior

*/

    struct PlayList* playlist = (struct PlayList*)malloc(sizeof(struct PlayList));
    char* song1 = "A";
    char* song2 = "B";
    char* song3 = "C";
    char* song4 = "D";
    char* song5 = "E";
    char* song6 = "F";
    char* song7 = "G";

    PlayList_create(playlist);
    PlayList_insertAtFront(playlist, song1);
    PlayList_insertAtFront(playlist, song2);
    PlayList_insertAtEnd(playlist, song3);
    PlayList_print(*playlist);

    PlayList_insertAfter(playlist, song1, song4);
    PlayList_insertBefore(playlist, song1, song5);
    PlayList_print(*playlist);

    PlayList_insertBefore(playlist, song2, song6);
    PlayList_insertAfter(playlist, song3, song7);
    PlayList_print(*playlist);

    PlayList_deleteFromFront(playlist);
    PlayList_print(*playlist);

    PlayList_deleteSong(playlist, song1);
    PlayList_print(*playlist);

    PlayList_backwardSong(playlist);
    PlayList_order(playlist);
    PlayList_print(*playlist);

    PlayList_forwardSong(playlist);
    PlayList_forwardSong(playlist);
    PlayList_forwardSong(playlist);
    PlayList_print(*playlist);

    PlayList_insertInOrder(playlist, song1);
    PlayList_insertInOrder(playlist, song6);
    PlayList_print(*playlist);

    PlayList_deleteAll(playlist);
    PlayList_print(*playlist);

    PlayList_insertAtEnd(playlist, song1);
    PlayList_print(*playlist);
    printf("Expected exit codes:\nDelete song not in the list--> Exp: 0  Result: %i\n", PlayList_deleteSong(playlist, song2));
    printf("Insert before song not in the list--> Exp: -1  Result: %i\n", PlayList_insertBefore(playlist, song2, song3));
    printf("Insert after song not in the list--> Exp: -1  Result: %i\n\n", PlayList_insertBefore(playlist, song2, song3));

    PlayList_insertAtFront(playlist, song2);
    PlayList_backwardSong(playlist);
    PlayList_print(*playlist);

    return 0;
}