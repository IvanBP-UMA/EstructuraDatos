#include <stdio.h>

void sumar(int* x, int y);

int main(void){
    int x = 2;
    int y = 3;

    sumar(&x, y);
    printf("El resultado de la suma es: %i", x);
    return 0;
}

void sumar(int* x, int y){
    *x = *x+y;
}