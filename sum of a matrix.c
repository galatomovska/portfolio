#include <stdio.h>
#include <stdlib.h>

int main()
{
    int red, kolona;
    int mat[10][10],i,j, zbir=0;
    scanf("%d %d", &red, &kolona);

    for(i=0; i<red; i++){
        for(j=0; j<kolona; j++){
            scanf("%d",&mat[i][j]);
            zbir=zbir+mat[i][j];
        }
    }

    printf("%d", zbir);
    //printf("Hello world!\n");
    return 0;
}
