#include <stdio.h>
#include <string.h>
#define NEDELI 4
#define DENOVI 5

// ovde strukturata RabotnaNedela
struct RabotnaNedela{
    int casovi[5];
    int brnedela;
};

typedef struct RabotnaNedela RN;

// ovde strukturata Rabotnik
struct Rabotnik{
    char ime[50];
    struct RabotnaNedela nedeli[4];
};

typedef struct Rabotnik R;

int sumaCasovi(RN *nedela){
    int suma = 0;
    int i;
    for(i = 0; i < 5; i++){
        suma += nedela->casovi[i];
    }
    return suma;
}

int maxNedela(R *r){
    int i, temp;
    int brnedela, max;
    max = sumaCasovi(&r->nedeli[0]);
    for(i = 1; i < 4; i++){
        temp = sumaCasovi(&r->nedeli[i]);
        if(temp > max){
            max = temp;
            brnedela = i + 1;
        }
    }
    return brnedela;
}


void table(R *r, int n){
    printf("Rab\t1\t2\t3\t4\tVkupno\n");
    int i;
    int eden, dva, tri, cetiri, vkupno = 0;
    for(i = 0; i < n; i++){
        eden = sumaCasovi(&r[i].nedeli[0]);
        dva = sumaCasovi(&r[i].nedeli[1]);
        tri = sumaCasovi(&r[i].nedeli[2]);
        cetiri = sumaCasovi(&r[i].nedeli[3]);
        vkupno = eden + dva + tri + cetiri;
        printf("%s\t%d\t%d\t%d\t%d\t%d\n", r[i].ime, eden, dva, tri, cetiri, vkupno);
    }

}

int main() {
    int n;
    scanf("%d", &n);
    R rabotnici[n];
    int i;
    for (i = 0; i < n; ++i) {
        scanf("%s", rabotnici[i].ime);
        int j;
        for (j = 0; j < NEDELI; ++j) {
            int k;
            for (k = 0; k < DENOVI; ++k) {
                scanf("%d", &rabotnici[i].nedeli[j].casovi[k]);
            }

        }
    }
    printf("TABLE\n");
    table(rabotnici, n);
    printf("MAX NEDELA NA RABOTNIK: %s\n", rabotnici[n / 2].ime);
    printf("%d\n", maxNedela(&rabotnici[n / 2]));
    return 0;
}
