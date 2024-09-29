//
// NOMBRE: Iván Bezare Pino
//
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Task.h"

struct Task* Task_new(unsigned int id, const char name[], unsigned int quantum){
  if(strlen(name) > MAX_NAME_LEN){
    perror("Too long");
    exit(1);
  }

  struct Task* newTask = (struct Task*)malloc(sizeof(struct Task));
  if (newTask == NULL){
    perror("No memory");
    exit(1);
  }

  newTask->id = id;
  newTask->quantum = quantum;
  strcpy(newTask->name, name);

  return newTask;
}

void Task_free(struct Task** p_p_task){
  if (p_p_task == NULL){
    perror("Null parameter");
    exit(1);
  }

  if (*p_p_task != NULL){
    free(*p_p_task);
    *p_p_task = NULL;
  }
}

struct Task* Task_copyOf(const struct Task* p_task){
  struct Task* newTask = (struct Task*)malloc(sizeof(struct Task));
  if (newTask == NULL){
    perror("No memory");
    exit(1);
  }

  newTask->id = p_task->id;
  newTask->quantum = p_task->quantum;
  strcpy(newTask->name, p_task->name);
  
  return newTask;
}

void Task_print(const struct Task* p_task){
  if (p_task != NULL){
    printf("ID: %i, QUANTUM: %i, NAME: %s\n", p_task->id, p_task->quantum, p_task->name);
  }else{
    printf("Empty task");
  }
}

