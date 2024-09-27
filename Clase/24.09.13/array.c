#include <stdio.h>

int main(void){
    int longitud;
    printf("Introduce la longitud del array: ");
    scanf("%i", &longitud);

    int array[longitud];
    int contador = 0;
    int suma = 0;

    while (contador<longitud){
        printf("Introduce un numero: ");
        scanf("%i", &array[contador]);
        suma += array[contador];
        contador++;
    }

    printf("La media de los elementos del array es: %2d", suma/longitud);
    return 0;
}