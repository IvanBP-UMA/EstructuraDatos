#include "canalesl/canales.h"
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>

void test_crear(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    assert(canales == NULL);
    destruir(&canales);
}

void test_destruir(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    canales = (struct T_Canal *)malloc(sizeof(struct T_Canal));
    canales->sig = (struct T_Canal *)malloc(sizeof(struct T_Canal));
    canales->pos = 0;
    canales->tipo = TV;
    strcpy(canales->nombre, "La Primera");

    strcpy(canales->sig->nombre, "Onda tres");
    canales->sig->pos = 1;
    canales->sig->tipo = RADIO;
    canales->sig->sig = NULL;

    destruir(&canales);
    assert(canales == NULL);
}

void test_mostrar(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    canales = (struct T_Canal *)malloc(sizeof(struct T_Canal));
    canales->sig = (struct T_Canal *)malloc(sizeof(struct T_Canal));
    canales->pos = 0;
    canales->tipo = TV;
    strcpy(canales->nombre, "La Primera");

    strcpy(canales->sig->nombre, "Onda tres");
    canales->sig->pos = 1;
    canales->sig->tipo = RADIO;
    canales->sig->sig = NULL;
    mostrar(canales); // No hay una manera directa de probar la salida, pero no debe haber errores.
    destruir(&canales);
}

void test_insertar(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Canal 1", 0, TV);
    insertar(&canales, "Radio 2", 1, RADIO);
    mostrar(canales); // Verificar visualmente que los canales se insertaron correctamente.
    destruir(&canales);
}

void test_insertar_desplazo(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Canal 1", 0, TV);
    insertar(&canales, "Radio 2", 1, RADIO);
    insertar(&canales, "Canal 0", 0, TV);
    mostrar(canales); // Verificar visualmente que los canales se insertaron correctamente.
    destruir(&canales);
}

void test_eliminar(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Canal 1 Eliminar", 0, TV);
    insertar(&canales, "Radio 2", 1, RADIO);
    int resultado = eliminar(&canales, "Canal 1 Eliminar");
    assert(resultado == 0);
    mostrar(canales); // Verificar visualmente que el canal se eliminó correctamente.
    destruir(&canales);
}

void test_extraerRadio(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Canal 1", 0, TV);
    insertar(&canales, "Radio 2", 1, RADIO);
    struct T_Canal *radios = extraerRadio(&canales);
    printf("Radios:\n");
    mostrar(radios);  // Verificar visualmente que solo los canales de radio fueron extraídos.
    printf("Canales:\n");
    mostrar(canales); // Verificar visualmente que los canales de TV permanecen.
    destruir(&radios);
    destruir(&canales);
}

void test_extraerRadio_cabeza(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Radio 1", 0, RADIO);
    insertar(&canales, "Radio 2", 1, RADIO);
    insertar(&canales, "Canal 1", 2, TV);
    insertar(&canales, "Canal 2", 3, TV);
    struct T_Canal *radios = extraerRadio(&canales);
    printf("Radios:\n");
    mostrar(radios);  // Verificar visualmente que solo los canales de radio fueron extraídos.
    printf("Canales:\n");
    mostrar(canales); // Verificar visualmente que los canales de TV permanecen.
    destruir(&radios);
    destruir(&canales);
}

void test_extraerRadio_intercalados(void)
{
    struct T_Canal *canales = NULL;
    crear(&canales);
    insertar(&canales, "Radio 1", 0, RADIO);
    insertar(&canales, "Canal 1", 1, TV);
    insertar(&canales, "Radio 2", 2, RADIO);
    insertar(&canales, "Canal 2", 4, TV);
    struct T_Canal *radios = extraerRadio(&canales);
    printf("Radios:\n");
    mostrar(radios);  // Verificar visualmente que solo los canales de radio fueron extraídos.
    printf("Canales:\n");
    mostrar(canales); // Verificar visualmente que los canales de TV permanecen.
    destruir(&radios);
    destruir(&canales);
}

void test_combinarCanales(void)
{
    struct T_Canal *destino = NULL;
    struct T_Canal *origen = NULL;
    crear(&destino);
    crear(&origen);
    insertar(&destino, "Canal 1", 0, TV);
    insertar(&origen, "Canal 2", 0, RADIO);
    //combinarCanales(&destino, &origen);
    mostrar(destino);       // Verificar visualmente que los canales se combinaron correctamente.
    assert(origen == NULL); // Verificar que la lista origen está vacía.
    destruir(&destino);
}

int main(void)
{
    test_crear();
    test_destruir();
    test_mostrar();
    test_insertar();
    test_insertar_desplazo();
    test_eliminar();
    test_extraerRadio();
    test_extraerRadio_cabeza();
    test_extraerRadio_intercalados();

    printf("Todos los tests pasaron exitosamente.\n");
    return 0;
}
