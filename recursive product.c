#include <stdio.h>
#include <stdlib.h>

float odnosProizvodCifri(int n)
{
    int proizvod=1, temp;
    temp=n;
    while(temp){
        proizvod=(temp%10)*proizvod;
        temp=temp/10;
    }
    float odnos;
    odnos=(float)proizvod/n;
    return odnos;
}
int main()
{
    int a;
    scanf("%d", &a);
    printf("%.2f", odnosProizvodCifri(a));
    return 0;
}
