#include <stdio.h>

struct Punto{
    int x, y;
};

void swapPoint(struct Punto* p){
    int temp = p->x;
    p->x = p->y;
    p->y = temp;
}

int main(void){
    struct Punto p1 = {1, 2};
    printf("El punto original es: (%i, %i)\n", p1.x, p1.y);
    swapPoint(&p1);
    printf("El punto cambiado es: (%i, %i)", p1.x, p1.y);
    return 0;
}