#define TAM 5
#include <stdio.h>

int main(void){
    int arr[TAM];
    int* puntero = NULL;
    puntero = arr;

    for (int i = 0; i<TAM; i++){
        *puntero = i+1;
        puntero++;
    }
    return 0;
}