#include <stdio.h>
#include <stdlib.h>

void kvadrat(int m){
    int i, j;
    for(i=0; i<m; i++){
        for(j=0; j<m; j++){
            printf("*");
        }
        printf("\n");
    }
}
int main()
{
    int n, i;
    scanf("%d", &n);
    for(i=0; i<=n; i++){
        kvadrat(i);
    }
    return 0;
}
