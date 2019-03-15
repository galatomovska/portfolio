#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef struct SkiLift{
    char imeLift[15];
    int maxKorisnici;
    int pusten;
}Skilift;

typedef struct SkiCentar{
    char imeCentar[20];
    char drzava[20];
    struct SkiLift niza[20];
    int brojSkiliftovi;
}SkiCentar;

//kaoacitet mi vrakja vrednost za eden ski centar
int kapacitet(SkiCentar skic){
    int i, temp = 0;
    for(i = 0; i < skic.brojSkiliftovi; i++){
            if(skic.niza[i].pusten == 1){
                temp += skic.niza[i].maxKorisnici;
            }
    }
    return temp;
}

void najgolemKapacitet(SkiCentar *skic, int n){
    int i, temp, maxKapacitet = 0, pamti = 0;
    for(i = 0; i < n; i++){
         temp = kapacitet(skic[i]);
            if ((temp > maxKapacitet) || (temp == maxKapacitet&&skic[i].brojSkiliftovi > skic[pamti].brojSkiliftovi)){
            maxKapacitet = temp;
            pamti = i;
            }
    }
    printf("%s\n%s\n%d", skic[pamti].imeCentar, skic[pamti].drzava, maxKapacitet);
}

int main()
{
    int  i, j, n;
	scanf("%d", &n);
	//char imeC[20];
	//char imeS[20];
	//char drzava[20];
	//int brojNaLiftovi;
	//nt maxKor, vkupnoKor = 0;
	//int pustenSki;
	SkiCentar skic[20];
	for (i = 0; i < n; i++){
		//vnesi ime
		scanf("%s", skic[i].imeCentar);
		//strcpy(skic[i].imeCentar, imeC);
		//vnesi drzava
		scanf("%s", skic[i].drzava);
		//strcpy(skic[i].drzava, drzava);
		//vnesi broj na liftovi
		scanf("%d", &skic[i].brojSkiliftovi);
        //za sekoj ski lift vnese:
        for(j = 0; j < skic[i].brojSkiliftovi; j++){
            //vnesi ime
			scanf("%s", skic[i].niza[j].imeLift);
            //strcpy(skic[i].niza[j].imeLift, imeS);
            //vnesi maksimalen broj korisnici
            scanf("%d", &skic[i].niza[j].maxKorisnici);
            //skic[i].niza[j].maxKorisnici = maxKor;
            //vnesi dali e pusten vo funkcija
            scanf("%d", &skic[i].niza[j].pusten);
            //skic[i].niza[j].pusten = pustenSki;
        }

	}
    //povik na funkcijata najgolemKapacitet
    //najgolemKapacitet(skic, n);
    najgolemKapacitet(skic, n);
    return 0;
}
