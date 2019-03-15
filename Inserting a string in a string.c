#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char promena(char *niza1, char *niza2, int pozicija)
{
    strcat(niza2, niza1+pozicija);
    niza1[pozicija]='\0';
    strcat(niza1, niza2);
    return niza1;
}
int main()
{
    int n;
    char n1[100];
    char n2[100];
    gets(n1);
    gets(n2);
    scanf("%d", &n);
    promena(n1, n2, n);
    puts(n1);
    //printf("Hello world!\n");
    return 0;
}