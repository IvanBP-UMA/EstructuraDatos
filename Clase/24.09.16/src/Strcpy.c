#include <stdio.h>

void strcpy(char* des, char* src);

int main(void){
    char str1[5] = "hola";
    char str2[5] = "BDGG";
    printf("Primera cadena -> %s\nSegunda cadena -> %s\n\n", str1, str2);

    strcpy(str1, str2);
    printf("Primera cadena -> %s\nSegunda cadena -> %s", str1, str2);

    return 0;
}

void strcpy(char* des, char* src){
    while(*src != '\0'){
        *des = *src;
        des++;
        src++;
    }
}