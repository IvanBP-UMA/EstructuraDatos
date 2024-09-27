#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct Node{
    int data;
    struct Node* next;
}Node;

bool insertHead(Node** head, int data){
    Node* newHead = (Node*)malloc(sizeof(Node));
    if (newHead == NULL){
        printf("Cant allocate memory");
        return false;
    }
    
    newHead->data = data;
    newHead->next = *head;
    *head = newHead;
    return true;
}

bool insertTail(Node** head, int data){
    Node* newTail = (Node*)malloc(sizeof(Node));
    if (newTail == NULL){
        printf("Cant allocate memory");
        return false;
    }
    newTail->data = data;
    newTail->next = NULL;
    
    while(*head != NULL){
        head = &(*head)->next;
    }
    *head = newTail;
    return true;
}

bool deleteHead(Node** head){
    Node* aux = (Node*)malloc(sizeof(Node));
    if (aux == NULL){
        printf("Error: Cannot allocate memory");
        return false;
    } 
    aux = *head;
    *head = (*head)->next;
    free(aux);
    return true;
}

bool delete(Node** head, int data){
    while((*head) != NULL && (*head)->data != data){
        head = &(*head)->next;
    }
    if ((*head == NULL)){
        printf("Value not found");
        return false;
    }

    Node* afterValue = (*head)->next;
    free(*head);
    *head = afterValue;
    return true;
}

void destroyList(Node** head){
    if (*head == NULL){
        return;
    }else{
        destroyList(&(*head)->next);
        free(*head);
        *head = NULL;
    }
}

void printList(Node** head){
    while(*head != NULL){
        printf("%i ", (*head)->data);
        head = &(*head)->next;
    }
    printf("\n");
}

int main(void){
    Node* head = NULL;
    insertHead(&head, 5);
    insertHead(&head, 4);
    insertHead(&head, 6);
    insertTail(&head, 7);
    insertTail(&head, 8);
    printList(&head);
    destroyList(&head);
    return 0;
}

