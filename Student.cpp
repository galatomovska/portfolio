#include<iostream>
#include<string.h>
using namespace std;
class BadInputException{
};
class StudentKurs{
   protected:
       char ime[30];
       int ocenka;
       bool daliUsno;
       static int MAX;
   public:
    static const int MINOCENKA=6;
    StudentKurs(){}
    StudentKurs(char* ime,int finalenIspit){
       strcpy(this->ime,ime);
        if(finalenIspit<=MAX)
        {
            this->ocenka=finalenIspit;
        }
       this->daliUsno=false;
     }
    bool getDaliUsno(){
    return daliUsno;
    }
    char* getIme()
    {
    return ime;
    }
   virtual void setDaliUsno(bool t)
    {
        daliUsno=t;
    }
    virtual int ocena(){
    return ocenka;
    }
    static void setMAX(int i)
    {
        MAX=i;
    }
    friend ostream &operator<<(ostream &o,StudentKurs &s)
    {
        if(s.ocena()<=MAX)
        o<<s.getIme()<<" --- "<<s.ocena()<<endl;
        else
        o<<s.getIme()<<" --- "<<MAX<<endl;
        return o;
    }
    virtual ~StudentKurs(){}
};
int StudentKurs::MAX=10;

//insert vode for StudentKursUsno 
class StudentKursUsno:public StudentKurs{
protected:
    char *opOcena;
public:
    StudentKursUsno(char*i,int o):StudentKurs(i,o){
    this->setDaliUsno(true);
    }
    virtual int ocena(){
    int n=this->StudentKurs::ocena();
        if(strcmp(this->opOcena,"odlicen")==0)
           return n+2;
        if(strcmp(this->opOcena,"dobro")==0)
            return n+1;
        if(strcmp(this->opOcena,"losho")==0)
            return n-1;
        return n;
    }
    StudentKursUsno &operator+=(char *o)
    {
        this->opOcena=new char[strlen(o)+1];
        char *q=new char[strlen(o)];
        int n=strlen(o)+1;
        bool t=false;
        for(int i=0,j=0;i<n-1;i++)
        {
            if(o[i]>=97&&o[i]<=122)
               q[j++]=o[i];
            else
            {
                throw BadInputException();
                
            }
        }
        strcpy(this->opOcena,q);
       
            return *this;
    }
    virtual ~StudentKursUsno(){delete []opOcena;}
};

class KursFakultet{
private:
    char naziv[30];
    StudentKurs *studenti[20];
    int broj;
    
public:
    KursFakultet(char *naziv, StudentKurs** studenti,int broj ){
      strcpy(this->naziv,naziv);
      for (int i=0;i<broj;i++){
        //ako studentot ima usno isprashuvanje
        if (studenti[i]->getDaliUsno()){
            this->studenti[i]=new StudentKursUsno(*dynamic_cast<StudentKursUsno*>(studenti[i]));
        }
        else this->studenti[i]=new StudentKurs(*studenti[i]);
      }
      this->broj=broj;
    }
    ~KursFakultet(){
    for (int i=0;i<broj;i++) delete studenti[i];
    }

    void pecatiStudenti() {
    
    cout<<"Kursot "<<this->naziv<<" go polozile:"<<endl;
    for(int i=0;i<broj;i++)
    {
        if(this->studenti[i]->ocena()>=StudentKurs::MINOCENKA)
            cout<<*(this->studenti[i]);
    }
        
    }
    void postaviOpisnaOcenka(char * ime, char* opisnaOcenka){
    for(int i=0;i<broj;i++)
    {
        if(strcmp(this->studenti[i]->getIme(),ime)==0&&this->studenti[i]->getDaliUsno())
        {   
            StudentKursUsno *s=dynamic_cast<StudentKursUsno*>(studenti[i]);
            *s+=opisnaOcenka;
        }
    }
    }
    
};

int main(){

StudentKurs **niza;
int n,m,ocenka;
char ime[30],opisna[10];
bool daliUsno;
cin>>n;
niza=new StudentKurs*[n];
for (int i=0;i<n;i++){
   cin>>ime;
   cin>>ocenka;
   cin>>daliUsno;
   if (!daliUsno)
    niza[i]=new StudentKurs(ime,ocenka);
   else
    niza[i]=new StudentKursUsno(ime,ocenka);
}

KursFakultet programiranje("OOP",niza,n);
for (int i=0;i<n;i++) delete niza[i];
delete [] niza;
cin>>m;
    
for (int i=0;i<m;i++){
   cin>>ime>>opisna;
    try{ 
        programiranje.postaviOpisnaOcenka(ime,opisna);
    }
    catch(BadInputException)
    {
        cout<<"Greshna opisna ocenka"<<endl;
        char *n=new char[strlen(opisna)+1];
        int q=strlen(opisna)+1;
        for(int i=0,j=0;i<q;i++)
        {
            if(isalpha(opisna[i]))
                n[j++]=opisna[i];
            
        }
       // cout<<n<<endl;
        programiranje.postaviOpisnaOcenka(ime,n);
    }
}

StudentKurs::setMAX(9);

programiranje.pecatiStudenti();

}