#include <stdio.h>
#include <string.h>

int main(void){
    char str1[5] ="hola";
    char str2[5] = "BDGG";
    char str3[5];

    strcpy(str3, str1);
    strcat(str1, str2);
    if (strcmp(str1, str3) != 0){
        printf("Las cadenas %s y %s son diferentes", str1, str3);
    }

    return 0;
}