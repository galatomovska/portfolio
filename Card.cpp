#include <iostream>
#include <cstring>
using namespace std;

class OutOfBoundException
{
public:
    void msg()
    {
        cout<<"Brojot na pin kodovi ne moze da go nadmine dozvolenoto"<<endl;
    }
};
class Karticka
{
protected:
    char trSmetka[15];
    int pin;
    bool daliPinovi;
public:
    char *getTrSmetka()
    {
        return this->trSmetka;
    }
    int getPin()
    {
        return this->pin;
    }
    bool getDaliPinovi()
    {
        return this->daliPinovi;
    }
    Karticka(char trSmetka[15]="",int pin=0,bool daliPinovi=false)
    {
        strcpy(this->trSmetka,trSmetka);
        this->pin=pin;
        this->daliPinovi=daliPinovi;
    }
    virtual int tezinaProbivanje()
    {
        int pin1=this->pin;
        int br=0;
        while(pin1)
        {
            br++;
            pin1/=10;
        }
        return br;
    }
    friend ostream &operator<<(ostream &output,Karticka &k)
    {
        output<<k.trSmetka<<": "<<k.tezinaProbivanje()<<endl;
        return output;
    }

};
class SpecijalnaKarticka : public Karticka
{
private:
    int *dopolnitelniPinovi;
    int n;
    static const int P=4;
public:
    int *getDopolnitelniPinovi()
    {
        return this->dopolnitelniPinovi;
    }
    int  getN()
    {
        return this->n;
    }
    SpecijalnaKarticka(char trSmetka[15]="",int pin=0,bool daliPinovi=false,int *dopolnitelniPinovi=0,int n=0):Karticka(trSmetka,pin,daliPinovi)
    {
        this->n=n;
        this->dopolnitelniPinovi=new int[n];
        for(int i=0; i<n; i++)
        {
            this->dopolnitelniPinovi[i]=dopolnitelniPinovi[i];
        }
    }
    int tezinaProbivanje()
    {
        return Karticka::tezinaProbivanje() + n;
    }
    friend ostream &operator<<(ostream &output,SpecijalnaKarticka &k)
    {
        output<<k.trSmetka<<": "<<k.tezinaProbivanje()<<endl;
        return output;
    }
    SpecijalnaKarticka &operator+=(int novPin)
    {
        if(this->n == SpecijalnaKarticka::P) throw OutOfBoundException();
        int *tmp=new int[n+1];
        for(int i=0; i<this->n; i++)
        {
            tmp[i]=this->dopolnitelniPinovi[i];
        }
        delete [] this->dopolnitelniPinovi;
        dopolnitelniPinovi=tmp;
        this->dopolnitelniPinovi[n]=novPin;
        this->n++;

        return *this;
    }

};
class Banka
{
private:
    char ime[30];
    Karticka *karticki[20];
    int n;
    static int LIMIT;

public:
    static void setLIMIT(int l)
    {
        LIMIT=l;
    }
    Banka(char ime[30]="",Karticka *karticki[20]=NULL,int n=0)
    {
        strcpy(this->ime,ime);
        this->n=n;
        for(int i=0; i<n; i++)
        {
            SpecijalnaKarticka *s=dynamic_cast<SpecijalnaKarticka*>(karticki[i]);

            if(s)
            {
                this->karticki[i]=new SpecijalnaKarticka(s->getTrSmetka(),s->getPin(),s->getDaliPinovi(),s->getDopolnitelniPinovi(),s->getN());
            }
            else
            {
                this->karticki[i]=new Karticka(karticki[i]->getTrSmetka(),karticki[i]->getPin(),karticki[i]->getDaliPinovi());
            }
        }
    }
    bool daliSeProbiva(Karticka *k)
    {
        if(k->tezinaProbivanje()<=Banka::LIMIT)
            return true;

        return false;
    }

    void pecatiKarticki()
    {
        cout<<"Vo bankata "<<this->ime<<" moze da se probijat kartickite:"<<endl;
        for(int i=0; i<n; i++)
        {
            if(daliSeProbiva(karticki[i]))
                cout<<*karticki[i];
        }
    }
    void dodadiDopolnitelenPin(char *smetka,int novPin)
    {
        for(int i=0; i<this->n; i++)
        {

            if(strcmp(karticki[i]->getTrSmetka(),smetka)==0)
            {
                SpecijalnaKarticka *sk=dynamic_cast<SpecijalnaKarticka*>(karticki[i]);
                if(sk)
                {
                    *(sk)+=novPin;
                }

            }
        }
    }

};

int Banka::LIMIT=7;

int main()
{

    Karticka **niza;
    int n,m,pin;
    char smetka[16];
    bool daliDopolnitelniPin;
    cin>>n;
    niza=new Karticka*[n];
    for (int i=0; i<n; i++)
    {
        cin>>smetka;
        cin>>pin;
        cin>>daliDopolnitelniPin;
        if (!daliDopolnitelniPin)
            niza[i]=new Karticka(smetka,pin);
        else
            niza[i]=new SpecijalnaKarticka(smetka,pin);
    }

    Banka komercijalna("Komercijalna",niza,n);
    for (int i=0; i<n; i++) delete niza[i];
    delete [] niza;
    cin>>m;
    for (int i=0; i<m; i++)
    {
        cin>>smetka>>pin;

        try{
        komercijalna.dodadiDopolnitelenPin(smetka,pin);
        }
        catch(OutOfBoundException e)
        {
            e.msg();
        }

    }

    Banka::setLIMIT(5);

    komercijalna.pecatiKarticki();

}