#include <stdio.h>

struct Punto
{
    int x, y;
};


int main(void){
    int numPuntos = 3;
    struct Punto p1 = {1, 2};
    struct Punto p2 = {3, 4};
    struct Punto p3 = {5, 6};

    struct Punto puntos[] = {p1, p2, p3};
    struct Punto centroide;

    for (int i = 0; i < numPuntos; i++){
        centroide.x += puntos[i].x;
        centroide.y += puntos[i].y;
    }

    centroide.x /= 3;
    centroide.y /= 3;

    printf("Centroide: (%i, %i)", centroide.x, centroide.y);

    return 0;
}