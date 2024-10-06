# Versión mínima de CMake
cmake_minimum_required(VERSION 3.10)

# Nombre del proyecto
project(Practica1 C)


# Variable para el nombre del archivo (sin la extensión .c)
set(M_FILE src/main)
set(L_FILE src/EEDD/lista_enlazada_doble)

# Añadir directorios de inclusión para los archivos de cabecera
include_directories(${CMAKE_SOURCE_DIR}/src/EEDD)

set(CMAKE_C_STANDARD 17)
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_C_EXTENSIONS OFF)

set(CMAKE_BUILD_TYPE Debug)

# Añadir flags de compilación
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")

# Asegurarse de que el compilador no realice optimizaciones
set(CMAKE_C_FLAGS_DEBUG "${CMAKE_C_FLAGS_DEBUG} -Wall -Wextra")


# Crear el ejecutable usando la variable SOURCE_FILE
add_executable(${PROJECT_NAME} ${M_FILE}.c ${L_FILE}.c)


