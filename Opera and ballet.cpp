#include<iostream>
#include<string.h>
using namespace std;

class Delo{
protected:
    char ime[50];
    int godina;
    char zemjaNaPoteklo[50];
    public:
    Delo(){}
   int getGodina(){
    return godina;
    }
    char* getZemja(){
    return zemjaNaPoteklo;
    }
    char* getIme(){
    return ime;
    }
    void setGodina(int i)
    {
        godina=i;
    }
    void setZemja(char *z)
    {
        strcpy(zemjaNaPoteklo,z);
    }
    void setIme(char *i)
    {
        strcpy(ime,i);
    }
    Delo(char *i,int g,char *z)
    {
        strcpy(ime,i);
        godina=g;
        strcpy(zemjaNaPoteklo,z);
    }
    friend bool &operator==(Delo d,Delo d1)
    {
        bool t=0;
        if(strcmp(d.getIme(),d1.getIme())==0)
            t=1;
        return t;
    }
    
};
class Pretstava{
protected:
    Delo delo;
    int brKarti;
    char data[15];
public:
    Pretstava(Delo d,int br,char *date){
    delo=d;
        brKarti=br;
        strcpy(data,date);
        
    }
    Pretstava(){}
    char *getData()
    {
        return data;
    }
    int getKarti(){
    return brKarti;}
    void setKarti(int i)
    {
        brKarti=i;
    }
    void setData(char *d){
    strcpy(data,d);
    }
    Delo getDelo()
    {
        return delo;
    }
    void setDelo(Delo &d)
    {
        delo=d;
    }
   virtual int cena(){
    int n,m;
        if(delo.getGodina()<1801) m=100;
        else if(delo.getGodina()>1800&&delo.getGodina()<1901) m=75;
        else if(delo.getGodina()>1901) m=50;
        if(strcmp(delo.getZemja(),"Italija")==0)
         n=100;
         else if(strcmp(delo.getZemja(),"Rusija")==0)
            n=150;
            else
            n=80;
            
            return n+m;
    }
    
};
class Balet:public Pretstava{
protected:
    static int Atr;
    public:
    Balet(){
    }
    Balet(Delo d,int br,char *date):Pretstava(d,br,date){}
    virtual int cena()
    {
        return this->Pretstava::cena()+Atr;
    }
    static void setCenaBalet(int i){
    Atr=i;
    }
    
};
int Balet::Atr=150;

class Opera:public Pretstava{
public:
    Opera(Delo d,int br,char *date):Pretstava(d,br,date){}
    Opera(){}
    
};
long int prihod(Pretstava **p,int n)
{
    long int rez=0;
    for(int i=0;i<n;i++)
    { rez=rez+p[i]->cena()*p[i]->getKarti();
       //cout<<p[i]->cena();
    }
    return rez;
}
int brojPretstaviNaDelo(Pretstava **p,int n,Delo f)
{
    int rez=0;
    for(int i=0;i<n;i++)
    {
        if(p[i]->getDelo()==f)
            rez++;
    }
    return rez;
}
//citanje na delo
Delo readDelo(){
    char ime[50];
    int godina;
    char zemja[50];
    cin>>ime>>godina>>zemja;
    return Delo(ime,godina,zemja);
}
//citanje na pretstava
Pretstava* readPretstava(){
    int tip; //0 za Balet , 1 za Opera
    cin>>tip;
    Delo d=readDelo();
    int brojProdadeni;
    char data[15];
    cin>>brojProdadeni>>data;
    if (tip==0)  return new Balet(d,brojProdadeni,data);
    else return new Opera(d,brojProdadeni,data);
}
int main(){
    int test_case;
    cin>>test_case;
    
    switch(test_case){
    case 1:
    //Testiranje na klasite Opera i Balet
    {
        cout<<"======TEST CASE 1======="<<endl;
        Pretstava* p1=readPretstava();
        cout<<p1->getDelo().getIme()<<endl;
        Pretstava* p2=readPretstava();
        cout<<p2->getDelo().getIme()<<endl;
    }break;
        
    case 2:
    //Testiranje na  klasite Opera i Balet so cena
    {
        cout<<"======TEST CASE 2======="<<endl;
        Pretstava* p1=readPretstava();
        cout<<p1->cena()<<endl;
        Pretstava* p2=readPretstava();
        cout<<p2->cena()<<endl;
    }break;
    
    case 3:
    //Testiranje na operator ==
    {
        cout<<"======TEST CASE 3======="<<endl;
        Delo f1=readDelo();
        Delo f2=readDelo();
        Delo f3=readDelo();
        
        if (f1==f2) cout<<"Isti se"<<endl; else cout<<"Ne se isti"<<endl;
        if (f1==f3) cout<<"Isti se"<<endl; else cout<<"Ne se isti"<<endl;
    
    }break;
    
    case 4:
    //testiranje na funkcijata prihod
    {
        cout<<"======TEST CASE 4======="<<endl;
        int n;
        cin>>n;
        Pretstava **pole=new Pretstava*[n];
        for (int i=0;i<n;i++){
            pole[i]=readPretstava();
        
        }
        cout<<prihod(pole,n);
    }break;
    
    case 5:
    //testiranje na prihod so izmena na cena za 3d proekcii
    {
        cout<<"======TEST CASE 5======="<<endl;
        int cenaBalet;
        cin>>cenaBalet;
        Balet::setCenaBalet(cenaBalet);
        int n;
        cin>>n;
        Pretstava **pole=new Pretstava*[n];
        for (int i=0;i<n;i++){
            pole[i]=readPretstava();
        }
        cout<<prihod(pole,n);
        }break;
        
    case 6:
    //testiranje na brojPretstaviNaDelo
    {
        cout<<"======TEST CASE 6======="<<endl;
        int n;
        cin>>n;
        Pretstava **pole=new Pretstava*[n];
        for (int i=0;i<n;i++){
            pole[i]=readPretstava();
        }
        
        Delo f=readDelo();
        cout<<brojPretstaviNaDelo(pole,n,f);
    }break;
    
    };


return 0;
}