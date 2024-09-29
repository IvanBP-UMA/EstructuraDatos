#include <stdio.h>
#include <stdlib.h>
#include "stack_ll.h"

void stack_ll_init(struct Stack_ll **ptrStack){
    (*ptrStack)->size = 0;
    (*ptrStack)->top = NULL;
}

void stack_ll_push(struct Stack_ll *ptrStack, struct Node element){
    struct Node* aux = ptrStack->top;
    ptrStack->top = &element;
    ptrStack->top->next = aux;
    ptrStack->size++;
}

struct Node stack_ll_top(struct Stack_ll * ptrStack){
    return *(ptrStack->top);
}

bool stack_ll_pop(struct Stack_ll * ptrStack){
    bool res = false;
    if (!stack_ll_isEmpty(ptrStack)){
        struct Node* aux = ptrStack->top;
        ptrStack->top = ptrStack->top->next;
        free(aux);
        ptrStack->size--;
        res = true;
    }
    return res;
}

bool stack_ll_isEmpty(struct Stack_ll * ptrStack){
    return (ptrStack->size == 0);
}

int stack_ll_size(struct Stack_ll * ptrStack){
    return ptrStack->size;
}

void stack_ll_clear(struct Stack_ll * ptrStack){
    while (stack_ll_pop(ptrStack))
    {
        /*Do nothing, pop will delete everything*/
    }
}

void stack_ll_show(struct Stack_ll * ptrStack){
    struct Stack_ll* ptrStackAux = ptrStack;
    for (int i = 0; i<ptrStack->size; i++){
         printf("Color: %i // Capacidad: %f // Material: %i\n", ptrStackAux->top->color, ptrStackAux->top->capacidad, ptrStackAux->top->material);
         ptrStackAux->top = ptrStackAux->top->next;
    }
}


