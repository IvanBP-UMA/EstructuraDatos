#include "stack_a.h"
#include <stdio.h>
#include <stdlib.h>

void stack_a_init(struct Stack_a * ptrStack_a, unsigned capacity){
    ptrStack_a->top = (Node*)malloc(sizeof(Node)*capacity);
    ptrStack_a->capacity = capacity;
    ptrStack_a->size = 0;
    return;
}

void stack_a_push(struct Stack_a *  ptrStack_a, struct Node element){
    if(ptrStack_a->size == ptrStack_a->capacity){
        ptrStack_a->capacity = ptrStack_a->capacity * 2;
        ptrStack_a->top = realloc(ptrStack_a->top, sizeof(Node) * ptrStack_a->capacity);
    }
    ptrStack_a->top[ptrStack_a->size] = element;
    ptrStack_a->size++;
    return;
}

struct Node stack_a_top(struct Stack_a *  ptrStack_a){
    return ptrStack_a->top[ptrStack_a->size-1];
}

bool stack_a_pop(struct Stack_a *  ptrStack_a){
    if (ptrStack_a->size == 0){
        return false;
    }
    ptrStack_a->size--;
    return true;
}

bool stack_a_isEmpty(struct Stack_a *  ptrStack_a){
    return ptrStack_a->size == 0;
}

int stack_a_size(struct Stack_a *  ptrStack_a){
    return ptrStack_a->size;
}

void stack_a_clear(struct Stack_a *  ptrStack_a){
    ptrStack_a->size = 0;
    return;
}

void stack_a_delete(struct Stack_a *  ptrStack_a){
    free(ptrStack_a->top);
    free(ptrStack_a);
    return;
}

void stack_a_show(struct Stack_a *  ptrStack_a){
    for (int i = ptrStack_a->size - 1; i>=0; i--){
        printf("Color: %i // Capacidad: %f // Material: %i\n", ptrStack_a->top[i].color, ptrStack_a->top[i].capacidad, ptrStack_a->top[i].material);
    }
}