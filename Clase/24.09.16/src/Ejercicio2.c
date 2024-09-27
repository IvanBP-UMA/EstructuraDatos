#include <stdio.h>

int main(void){
    int* ptrInt;
    char* ptrChar;
    float* ptrFloat;

    int x = 8;
    char y = 'a';
    float z = 9.8;

    ptrInt = &x;
    ptrChar = &y;
    ptrFloat = &z;

    printf("Size of:\nInt pointer -> %lli\nChar pointer -> %lli\nFloat pointer -> %lli", sizeof(ptrInt), sizeof(ptrChar), sizeof(ptrFloat));

    return 0;
}