#include <iostream>
using namespace std;

class Krug {
    float radius;
    static const float pi = 3.14;
    public:
    
    Krug(float r){
    	radius=r;
    }
    
    void set_radius(float a){
    	radius=a;
    }
    float plostina(){
    	return radius*radius*pi;
    }
    float perimetar(){
    	return 2*radius*pi;
    }
    int ednakvi(){
    	float plo=plostina();
        float per=perimetar();
        if(plo==per)
            return 1;
        else
            return 0;
    }

};

int main() {
	float r;
	cin >> r;
    Krug k(r);
	//instanciraj objekt od klasata Krug cij radius e vrednosta procitana od tastatura
	cout << k.perimetar() << endl;
	cout << k.plostina() << endl;
	cout << k.ednakvi() <<endl;
    //instanciraj objekt od klasata Krug cij radius ne e definiran
	return 0;
}