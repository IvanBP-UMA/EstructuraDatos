#include <stdio.h>
#include <stdlib.h>

typedef struct Node *ptrNode; // Alias para un puntero a la estructura
typedef struct Node{
    int data;
    ptrNode next;
} Node; // Alias para la estructura

void iterar(ptrNode head){
    while(head != NULL){
        printf("Data: %i\n", head->data);
        head = head->next;
    }
}

void insertar(ptrNode* ptrHead, int data){
    ptrNode newHead = (ptrNode)malloc(sizeof(struct Node));
    if (newHead == NULL){
        printf("Cant allocate memory");
        return;
    }

    newHead->data = data;

    if (*ptrHead == NULL){
        (*ptrHead) = newHead;
        newHead->next = NULL;
    }else{
        newHead->next = *ptrHead;
        *ptrHead = newHead;
    }
}

int main(void){
    // Declara una solo variable de tipo ptrNode llamada head.
    ptrNode head;
    // Pide memoria para tres nodos y enlaza cada una de ellas para tener el valor 3->7->9->NULL.
    head = (ptrNode)malloc(sizeof(struct Node));
    head->data = 3;
    //(*head).data = 3

    head->next = (ptrNode)malloc(sizeof(struct Node));
    head->next->data = 7;
    head->next->next = (ptrNode)malloc(sizeof(struct Node));
    head->next->next->data = 9;
    head->next->next->next = NULL;

    iterar(head);

    return 0;
}