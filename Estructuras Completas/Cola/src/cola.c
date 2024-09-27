#include "cola.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Person * createPerson(char *name, int age){
    Person* p = (Person*)malloc(sizeof(Person));
    p->age = age;
    strcpy(p->name, name);  
    return p;
}

void createQueue(Queue *ptrqueue){
    *ptrqueue = NULL;
}

bool isEmpty(Queue queue){
    bool res;
    if(queue == NULL){
        res = true;
    }else{
        res = false;
    }
    return res;
}

int size(Queue queue){
    int count;
    if (isEmpty(queue)){
        count = 0;
    }else{
        Queue aux = (Queue)malloc(sizeof(Node));
        aux = queue;
        do{
            count++;
            queue = queue->next;
        }while(queue != aux);
    }
    return count;
}

void enqueue(Queue *ptrqueue, struct Person * person){
    Queue new = (Queue)malloc(sizeof(Node));
    new->person = person;

    if (isEmpty(*ptrqueue)){
        new->next = new;
        *ptrqueue = new;
    }else{
        new->next = (*ptrqueue)->next;
        (*ptrqueue)->next = new;
        *ptrqueue = new;
    }
}

void dequeue(Queue *ptrqueue){
    if (isEmpty(*ptrqueue)){
        return;
    }else{
        if (size(*ptrqueue) == 1){
            free(*ptrqueue);
            *ptrqueue = NULL;
        }else{
            Queue aux = (Queue)malloc(sizeof(Node));
            aux = (*ptrqueue)->next->next;
            free((*ptrqueue)->next);
            (*ptrqueue)->next = aux;
        }
    }
}

struct Person * first(Queue queue){
    return queue->next->person;
}

void clear(Queue *ptrqueue){
    while(*ptrqueue != NULL){
        dequeue(ptrqueue);
    }
}

void display(Queue queue){
    printf("\n");
    queue = queue->next;
    Queue aux = (Queue)malloc(sizeof(Node));
    aux = queue;
    do{
        printf("Name: %s // Age: %i\n", queue->person->name, queue->person->age);
        queue = queue->next;
    }while(queue != aux);
}

int main(void){
    Person* p1 = createPerson("Pepe", 20);
    Person* p2 = createPerson("Manolo", 15);
    Person* p3 = createPerson("Yeyo", 36);

    Queue queue = (Queue)malloc(sizeof(Node));
    createQueue(&queue);

    enqueue(&queue, p1);
    enqueue(&queue, p2);
    enqueue(&queue, p3);

    display(queue);

    dequeue(&queue);
    dequeue(&queue);
    display(queue);

    return 0;
}