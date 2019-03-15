#include <stdio.h>

typedef struct tocka {
    // vasiot kod ovde
    double x;
    double y;
} tocka;

typedef struct otsecka {
    // vasiot kod ovde
    tocka tocka1;
    tocka tocka2;
    
} otsecka;

int se_secat(otsecka o1, otsecka o2) {
	// vashiot kod ovde
    
    float b1=(o1.tocka2.y-o1.tocka1.y)/(o1.tocka2.x-o1.tocka1.x);
    float b2=(o2.tocka2.y-o2.tocka1.y)/(o2.tocka2.x-o2.tocka1.x);
    if(b1!=b2){
        float c1, c2, a;
    	c1=o1.tocka1.y-b1*o1.tocka1.x;
    	c2=o2.tocka1.y-b1*o2.tocka1.x;
    	a=(c2-c1)/(b1-b2);
        if(a<o1.tocka2.x&&a>o1.tocka1.x)
        	return 1;
        else if(o1.tocka1.x == o2.tocka1.x || o1.tocka2.y==o2.tocka2.y)
            return 1;
        else
            return 0;
    }
    else 
        return 0;
}

int main() {
    double x1, y1, x2, y2;
    scanf("%f %f %f %f", &x1, &y1, &x2, &y2);
    tocka t1 = { x1, y1 };
    tocka t2 = { x2, y2 };
    otsecka o1 = { t1, t2 };
    scanf("%f %f %f %f", &x1, &y1, &x2, &y2);
    tocka t3 = { x1, y1 };
    tocka t4 = { x2, y2 };
    otsecka o2 = { t3, t4 };
    printf("%d\n", se_secat(o1, o2));
    return 0;
}