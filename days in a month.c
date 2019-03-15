#include <stdio.h>
#include <stdlib.h>

int main()
{
    int mesec, godina;
    int brnaden=30;
    scanf("%d", &mesec);
    switch(mesec){
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            brnaden=31;
            break;
        case 2:
            scanf("%d", &godina);
            if(godina%4==0){
                brnaden=29;
            }
            else{
                brnaden=28;
            }
            break;
        default:
            break;
        }

    if(mesec>12 || mesec==0){
        printf("Nevaliden vlez\n");
    }
    else if(mesec<10){
        printf("Brojot na denovi za mesec 0%d e: %d", mesec, brnaden);
    }
    else{
        printf("Brojot na denovi za mesec %d e: %d", mesec, brnaden);
    }

    return 0;
}
