#include <iostream>
using namespace std;

class Agol {
    int stepeni, minuti, sekundi;
    public:
    void set_stepeni(int a){
    	stepeni=a;
    }
    void set_minuti(int a){
    	minuti=a;
    }
    void set_sekundi(int a){
    	sekundi=a;
    }
    int to_sekundi(int stepeni, int minuti, int sekundi){
        return stepeni*3600 + minuti*60 + sekundi;
    }
};

/*void Agol::set_values(int ste, int min, int sek){
	stepeni=ste;
    minuti=min;
    sekundi=sek;
}*/

int proveri(int ste, int min, int sek){
	if(min<60&&sek<60)
        return 1;
    else
        return 0;
}

int main() {
    
    //da se instancira objekt od klasata Agol
    Agol a1;
    int deg, min, sec;
    cin >> deg >> min >> sec;
    
    if (proveri(deg, min, sec)) {
    
    	a1.set_stepeni(deg);
        a1.set_minuti(min);
        a1.set_sekundi(sec);
        cout << a1.to_sekundi(deg, min, sec);
        
    }
    else{
    	cout << "Nevalidni vrednosti za agol";
    }
    return 0;
}
