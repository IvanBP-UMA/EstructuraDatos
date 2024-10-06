#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include "lista_enlazada_doble.h"

void print_error(const char* message){
  fprintf(stderr, message);
}

struct Node* findSong(struct PlayList *myPlayList ,const char* songName){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return NULL;
  }

  struct Node* aux = myPlayList->head;
  while (aux != NULL && strcmp(aux->data, songName) != 0){
    aux = aux->next;
  }
  return aux;
}

void PlayList_create(struct PlayList *myPlayList){
  if (myPlayList != NULL){
    myPlayList->currentsong = NULL;
    myPlayList->head = NULL;
  }
}

struct Node * createNode(const char* value){
  struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
  if (newNode == NULL){
    printf("No memory to allocate");
    return NULL;
  }
  if (strlen(value) > MAX_SONG_LENGTH){
    printf("Song name is too long");
    return NULL;
  }

  strcpy(newNode->data, value);
  newNode->prev = NULL;
  newNode->next = NULL;
  return newNode;
}

int PlayList_insertAtFront(struct PlayList *myPlayList, const char* value){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return -1;
  }
  struct Node* newNode = createNode(value);
  if (newNode == NULL){
    print_error("Couldnt create node");
    return -1;
  }

  newNode->next = myPlayList->head;
  myPlayList->head = newNode;
  if (myPlayList->currentsong == NULL){
    myPlayList->currentsong = newNode;
  }else{
    myPlayList->head->next->prev = myPlayList->head;
  }
  return 0;
}

int PlayList_insertInOrder(struct PlayList *myPlayList, const char* valor){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return -1;
  }

  if (myPlayList->head == NULL){
    PlayList_insertAtFront(myPlayList, valor);
  }else{
    struct Node* auxHead = myPlayList->head;
    PlayList_order(myPlayList);
    while(strcmp(auxHead->data, valor) < 0 && auxHead->next != NULL){
      auxHead = auxHead->next;
    }
    if (strcmp(auxHead->data, valor) < 0){
      PlayList_insertAtEnd(myPlayList, valor);
    }else{
      PlayList_insertBefore(myPlayList, auxHead->data, valor);
    }
  }
  return 0;
}

int PlayList_insertAtEnd(struct PlayList *myPlayList, const char* value){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return -1;
  }
  struct Node* newNode = createNode(value);
  if (newNode == NULL){
    print_error("Couldnt create node");
    return -1;
  }

  if (myPlayList->head != NULL){
    struct Node* aux = myPlayList->head;
    while (aux->next != NULL){
      aux = aux->next;
    }
    aux->next = newNode;
    newNode->prev = aux;
  }else{
    myPlayList->head = newNode;
    newNode->prev = NULL;
  }

  if (myPlayList->currentsong == NULL){
    myPlayList->currentsong = newNode;
  }
  return 0;
}

int PlayList_insertAfter(struct PlayList *myPlayList, const char *cancion, const char* value){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return -1;
  }
  struct Node* newNode = createNode(value);
  if (newNode == NULL){
    print_error("Couldnt create node");
    return -1;
  }
  struct Node* beforeSong = findSong(myPlayList, cancion);
  if (beforeSong == NULL){
    print_error("Couldnt find song");
    return -1;
  }

  struct Node* aux = beforeSong->next;
  beforeSong->next = newNode;
  newNode->next = aux;
  newNode->prev = beforeSong;
  if(aux != NULL){
    aux->prev = newNode;
  }
  return 0;
}

int PlayList_insertBefore(struct PlayList *myPlayList, const char *cancion, const char* value){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return -1;
  }
  struct Node* newNode = createNode(value);
  if (newNode == NULL){
    print_error("Couldnt create node");
    return -1;
  }
  struct Node* afterSong = findSong(myPlayList, cancion);
  if (afterSong == NULL){
    print_error("Couldnt find song");
    return -1;
  }

  struct Node* aux = afterSong->prev;
  afterSong->prev = newNode;
  newNode->next = afterSong;
  newNode->prev = aux;
  if (aux != NULL){
    aux->next = newNode;
  }else{
    myPlayList->head = newNode;
  }
  return 0;
}

void PlayList_deleteFromFront(struct PlayList *myPlayList){
  if (myPlayList != NULL && myPlayList->head != NULL){
    if (myPlayList->head->next == NULL){
      free(myPlayList->head);
      myPlayList->head = NULL;
      myPlayList->currentsong = NULL;
    }else{
      struct Node* aux = myPlayList->head->next;
      if (myPlayList->head == myPlayList->currentsong){
        myPlayList->currentsong = myPlayList->head->next;
      }
      free(myPlayList->head);
      myPlayList->head = aux;
      myPlayList->head->prev = NULL;
    }
  }
}

int PlayList_deleteSong(struct PlayList *myPlayList, const char *value){
  if (myPlayList == NULL){
    print_error("Null parameter");
    return 0;
  }
  struct Node* song = findSong(myPlayList, value);
  if (song == NULL){
    print_error("Couldnt find song");
    return 0;
  }

  bool isCurrentSong = false;
  if (myPlayList->currentsong == song){
    isCurrentSong = true;
  }

  if(song->prev == NULL && song->next == NULL){
    free(song);
    myPlayList->currentsong = NULL;
    myPlayList->head = NULL;
  }else if(song->prev == NULL){
    song->next->prev = NULL;
    if (isCurrentSong){myPlayList->currentsong = song->next;}
    free(song);
  }else if(song->next == NULL){
    song->prev->next = NULL;
    if (isCurrentSong){myPlayList->currentsong = song->prev;}
    free(song);
  }else{
    song->prev->next = song->next;
    song->next->prev = song->prev;
    if (isCurrentSong){myPlayList->currentsong = song->next;}
    free(song);
  }
  return 1;
}

void PlayList_print(struct PlayList myPlayList){
  if (myPlayList.head != NULL){
    while (myPlayList.head != NULL){
      if (myPlayList.head == myPlayList.currentsong){
        printf("<CURRENT SONG> ");
      }
      printf("SongName: %s\n", myPlayList.head->data);
      myPlayList.head = myPlayList.head->next;
    }
  }else{printf("Empty playlist\n");}
  printf("\n");
}

void PlayList_order(struct PlayList *myPlayList){
  if (myPlayList != NULL && myPlayList->head != NULL){

    char originalCurrentSong[MAX_SONG_LENGTH];
    strcpy(&originalCurrentSong, myPlayList->currentsong->data);

    struct Node* auxHead = myPlayList->head;
    struct Node* last = NULL;
    bool swap = false;
    char auxSwapMem[MAX_SONG_LENGTH];
    char* auxSwapData = auxSwapMem;
    do{
      swap = false;
      auxHead = myPlayList->head;
      while (auxHead->next != last){
        if (strcmp(auxHead->data, auxHead->next->data) > 0){
          strcpy(auxSwapData, auxHead->data);
          strcpy(auxHead->data, auxHead->next->data);
          strcpy(auxHead->next->data, auxSwapData);
          swap = true;
        }
        auxHead = auxHead->next;
      }
      last = auxHead;
    }while(swap);

    struct Node* aux = findSong(myPlayList, &originalCurrentSong);
    myPlayList->currentsong = aux;
  }
}

void PlayList_deleteAll (struct PlayList *myPlayList){
  if (myPlayList != NULL && myPlayList->head != NULL){
    while(myPlayList->head->next != NULL){
      myPlayList->head = myPlayList->head->next;
    }
    struct Node* aux;
    while(myPlayList->head != NULL){
      aux = myPlayList->head->prev;
      free(myPlayList->head);
      myPlayList->head = aux;
    }
    myPlayList->currentsong = NULL;
  }
}

char *PlayList_currentSong (struct PlayList myPlayList){
  char* res;
  if (myPlayList.currentsong == NULL){
    res = "NO CURRENT SONG";
  }else{
    res = myPlayList.currentsong->data;
  }return res;
}

void PlayList_forwardSong (struct PlayList *myPlayList){
  if (myPlayList != NULL && myPlayList->head != NULL && myPlayList->currentsong->next != NULL){
    myPlayList->currentsong = myPlayList->currentsong->next;
  }
}

void PlayList_backwardSong (struct PlayList *myPlayList){
  if (myPlayList != NULL && myPlayList->head != NULL && myPlayList->currentsong->prev != NULL){
    myPlayList->currentsong = myPlayList->currentsong->prev;
  }
}
