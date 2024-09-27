#include <stdio.h>

struct SinPadding{
    char c; // 1 byte
    int i; // 4 bytes
    char c2; // 1 byte
};
struct ConPadding{
    char c; // 1 byte
    char c2; // 1 bytes
    int i; // 4 byte
};


int main(void){
    struct SinPadding a = {'a', 1, 'b'};
    struct ConPadding b = {'a', 'b', 1};
    printf("%lli %lli", sizeof(a), sizeof(b));
    return 0;
}