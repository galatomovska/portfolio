#include <stdio.h>
#include <stdlib.h>
#define MAX_LEN 50

typedef struct proizvod {
    char ime[50];
    float cena;
    int kolicina;
} proizvod ;

int main()
{
    int n, i;
    char name[MAX_LEN];
    float price, suma=0;
    int quantity;
    struct proizvod p;
    scanf("%d", &n);
    for(i=0; i<n; i++){
        scanf("%s", name);
        scanf("%f", &price);
        scanf("%d", &quantity);
        strcpy(p.ime, name);
        p.cena=price;
        p.kolicina=quantity;
        float vkupnoCena=quantity*price;
        suma+=vkupnoCena;
        printf("%d. %s\t%.2f x %d = %.2f\n", i+1, p.ime, p.cena, p.kolicina, vkupnoCena);
    }
    printf("Total: %.2f", suma);
    return 0;
}
