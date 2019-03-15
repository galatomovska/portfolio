#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int brojIstiZnaci(char niza1[], char niza2[])
{
    int i, j, znaci=0;
    for(i=0; i<strlen(niza1); i++){
            for(j=0;j<strlen(niza2); j++){
                if(i==j&&niza1[i]==niza2[j]){
                    niza1[i]='*';
                    niza2[j]='*';
                    znaci++;
                }
            }

    }
    return znaci;
}
int main()
{
    char n1[100];
    char n2[100];
    gets(n1);
    gets(n2);
    printf("%d\n", brojIstiZnaci(n1, n2));
    puts(n1);
    puts(n2);
    //printf("Hello world!\n");
    return 0;
}