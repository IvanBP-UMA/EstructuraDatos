//
// NOMBRE: Iván Bezares Pino
//

#include <stdlib.h>
#include <stdio.h>
#include "Scheduler.h"
#include "Task.h"

struct Node* Scheduler_new(void){
  return NULL;
}

size_t Scheduler_size(const struct Node* p_last){
  int size = 0;
  if (p_last != NULL){
    struct Node* aux = p_last;
    do{
      size++;
      p_last = p_last->p_next;
    }while(p_last != aux);
  }
  return size;
}

void Scheduler_clear(struct Node** p_p_last){
  if (p_p_last == NULL){
    perror("NULL pointer");
    exit(1);
  }
  if(*p_p_last != NULL){
    struct Node* originAux = *p_p_last;
    struct Node* aux;
    do{
      Task_free(&((*p_p_last)->task));
      aux = (*p_p_last)->p_next;
      free(*p_p_last);
      *p_p_last = aux;
    }while(*p_p_last != originAux);
    *p_p_last = NULL;
  }
}

struct Task* Scheduler_first(const struct Node* p_last){
  return p_last->p_next->task;
}

void Scheduler_enqueue(struct Node** p_p_last,const struct Task* p_task){
  if(p_p_last == NULL){
    perror("Null parameter");
    exit(1);
  }

  struct Node* newNode= (struct Node*)malloc(sizeof(struct Node));
  if (newNode == NULL){
    perror("No memory");
    exit(1);
  }

  newNode->task = p_task;
  if(*p_p_last == NULL){
    newNode->p_next = newNode;
  }else{
    newNode->p_next = (*p_p_last)->p_next;
    (*p_p_last)->p_next = newNode;
  }
  *p_p_last = newNode;
}

void Scheduler_dequeue(struct Node** p_p_last){
  if(p_p_last == NULL){
    perror("Null parameter");
    exit(1);
  }

  if(*p_p_last != NULL){
    struct Node* aux = (*p_p_last)->p_next;
    Task_free(&(aux->task));
    (*p_p_last)->p_next = aux->p_next;
    free(aux);
    if (*p_p_last == aux){
      *p_p_last = NULL;
    }
  }
}

void Scheduler_step(struct Node** p_p_last){
  if(p_p_last == NULL){
    perror("Null parameter");
    exit(1);
  }
  if(*p_p_last != NULL && Scheduler_size(*p_p_last) != 1){
    struct Task* firstTask = Task_copyOf(Scheduler_first(*p_p_last));
    Scheduler_dequeue(p_p_last);
    Scheduler_enqueue(p_p_last, firstTask);
  }
}

void Scheduler_print(const struct Node* p_last){
  if (p_last != NULL){
    struct Node* aux = p_last;
    do{
      p_last = p_last->p_next;
      Task_print(p_last->task);
    }while(p_last != aux);
  }else{
    printf("Empty scheduler\n");
  }
  printf("\n");
}

