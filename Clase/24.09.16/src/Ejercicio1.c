#include <stdio.h>

int main(void){
    int* ptr;
    int x = 8;
    ptr = &x;
    printf("Dir: %p // Value: %i", ptr, *ptr);
    return 0;
}