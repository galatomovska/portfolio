#include<iostream>
#include<cstring>
using namespace std;
 
class Transport
{
protected:
    char destinacija[20];
    int osnovna_cena;
    int rastojanie;
public:
    Transport(char destinacija[20]="",int osnovna_cena=0,int rastojanie=0)
    {
        this->rastojanie=rastojanie;
        this->osnovna_cena=osnovna_cena;
        strcpy(this->destinacija,destinacija);
    }
    virtual float cenaTransport() { return 0; }
 
    bool operator<(const Transport &t)
    {
        return (this->rastojanie < t.rastojanie);
    }
    char *getDestinacija() { return this->destinacija; }
    int getOsnovna_cena() { return this->osnovna_cena; }
    int getRastojanie() { return this->rastojanie; }
};
class AvtomobilTransport : public Transport
{
private:
    bool shofer;
public:
    AvtomobilTransport(char destinacija[20]="",int osnovna_cena=0,int rastojanie=0,bool shofer=false):Transport(destinacija,osnovna_cena,rastojanie)
    {
        this->shofer=shofer;
    }
    virtual float cenaTransport()
    {
        if(this->shofer)
            return osnovna_cena + (osnovna_cena * 0.2);
        return osnovna_cena;
    }
 
};
class KombeTransport : public Transport
{
private:
    int patnici;
public:
    KombeTransport(char destinacija[20]="",int osnovna_cena=0,int rastojanie=0,int patnici=0):Transport(destinacija,osnovna_cena,rastojanie)
    {
        this->patnici=patnici;
    }
    virtual float cenaTransport()
    {
        return osnovna_cena - (this->patnici * 200);
    }
 
};
void pecatiPoloshiPonudi(Transport **ponudi,int n,Transport &t)
{
    Transport *s;
    for(int i=0;i<n;i++)
    {
        for(int j=i+1;j<n;j++)
        {
            if(ponudi[i]->cenaTransport() > ponudi[j]->cenaTransport())
            {
                s=ponudi[i];
                ponudi[i]=ponudi[j];
                ponudi[j]=s;
            }
        }
    }
    for(int i=0;i<n;i++)
    {
        if(ponudi[i]->cenaTransport()>t.cenaTransport())
        {
            cout<<ponudi[i]->getDestinacija()<<" ";
            cout<<ponudi[i]->getRastojanie()<<" ";
            cout<<ponudi[i]->cenaTransport()<<endl;
        }
    }
}
 
int main(){
 
char destinacija[20];
int tip,cena,rastojanie,lugje;
bool shofer;
int n;
cin>>n;
Transport  **ponudi;
ponudi=new Transport *[n];
 
for (int i=0;i<n;i++){
 
    cin>>tip>>destinacija>>cena>>rastojanie;
    if (tip==1) {
        cin>>shofer;
        ponudi[i]=new AvtomobilTransport(destinacija,cena,rastojanie,shofer);
 
    }
    else {
        cin>>lugje;
        ponudi[i]=new KombeTransport(destinacija,cena,rastojanie,lugje);
    }
 
 
}
AvtomobilTransport nov("Ohrid",2000,600,false);
pecatiPoloshiPonudi(ponudi,n,nov);
 
for (int i=0;i<n;i++) delete ponudi[i];
delete [] ponudi;
return 0;
}