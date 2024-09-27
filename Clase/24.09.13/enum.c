#include <stdio.h>

enum Mes {
    ENERO, FEBRERO, MARZO, ABRIL, MAYOR, JUNIO, JULIO, AGOSTO, SEPTIEMBRE, OCTUBRE, NOVIEMBRE, DICEMBRE
};

const char* meses[] = {
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
};

int main(void){
    enum Mes mes = MARZO;
    printf("El mes seleccionado es %s", meses[mes]);
    return 0;
}