#include <iostream>
#include <cmath>
#include <cstdlib>
 
using namespace std;
 
template <typename T>
class Array
{
    private:
    T* pole;
    int size;
   
    public:
    Array<T>(int ssize){
        size=ssize;
        pole = new T[size];
    }
    void Erase(){
    for(int i=0;i<size;i++)
        delete []pole;
     pole=new T[size];
    }
   
    T &operator[](int a){
        return pole[a];}
   
    int GetLength(){return size;}
   
    friend ostream &operator<<(ostream &out,const Array<T> &a){  
        for (int i=0;i<a.size;i++){
                if(i==a.size-1) cout<<"Array["<<i<<"] = "<<a.pole[i];
                else out<<"Array["<<i<<"] = "<<a.pole[i]<<", ";}
          return out;
        }
};
 
template <typename T>
void BubbleSort(Array<T> &t){
    T pom;
    for(int i=0;i<t.GetLength();i++)
       for(int j=i;j<t.GetLength()-1;j++)
       if(t[i]>t[j]){
          pom=t[i];
          t[i]=t[j];
          t[j]=pom;}
                }
 
template <typename T>
T Sum(Array<T> &t){
    T zbir=0;
    for(int i=0;i<t.GetLength();i++)
zbir+=t[i];
return zbir;}
 
template <typename T>
double Average(Array<T> &t){      
return Sum(t)/t.GetLength();
     }
 
template <typename T,typename M>
bool Equal(Array<T>& a , Array<M>&b){
    if(a.GetLength()==b.GetLength()){      
    bool flag=true;
        for(int i=0;i<a.GetLength();i++)
    if(a[i]!=b[i]){
        flag=false;
        break;}
 
    if(flag)
    return true;
    else return false;
        }
        else return false;}
 
    template <typename T>
    bool Equal (Array <T>&t, Array<double>&d){
        if(t.GetLength()==d.GetLength()){
            bool flag=true;
                for(int i=0;i<t.GetLength();i++)
                if(fabs(t[i]-d[i])>0.1){
                flag=false;
                        break;}
     if(fabs(Average(t)-Average(d))>0.5)
         flag=false;
 
    if(flag)
    return true;
     else return false;
        }
        else return false;}
 
 
 
 
 
int main()
{
 
  int n;
  double r;
   
  cin>>r;
  cin>>n;
   
  Array<int> anArray(n);
  Array<double> adArray(n);
  Array<int> intArray2(n);
 
  for (int nCount = 0; nCount < n; nCount++)
  {
     
      cin>>anArray[nCount];
      adArray[nCount] = anArray[nCount] + r;
  }
 
  BubbleSort(anArray);
 
  intArray2 = anArray;
 
  cout<<"The arrays: "<<endl;
  cout<<anArray;
  cout<<endl<<"and "<<endl;
  cout<<intArray2<<endl;
  cout<<((Equal(anArray,intArray2))?" ARE":" ARE NOT")<<" same!"<<endl;
  cout<<"The Average of the array adArray is: "<<Average(adArray)<<endl;
 
  cout<<"The arrays: "<<endl;
  cout<<anArray;
  cout<<endl<<"and "<<endl;
  cout<<adArray<<endl;
  cout<<((Equal(anArray,adArray))?" ARE":" ARE NOT")<<" same!";
 
 
return 0;
}