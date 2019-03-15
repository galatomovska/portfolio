#include<iostream>
#include<string.h>
#include<cmath>
using namespace std;

class Vozac {
private:
	char name[100];
	int age;
	int noRaces;
	bool veteran;
public:
	//constructor
	Vozac(char *name = " ", int age = 0, int noRaces = 0, bool veteran = false) {
		strcpy(this->name, name);
		this->age = age;
		this->noRaces = noRaces;
		this->veteran = veteran;
	}

	//overloading operator <<
	friend ostream &operator<<(ostream &o, const Vozac &tmp) {
		o << tmp.name << endl << tmp.age << endl << tmp.noRaces;
		if (tmp.veteran) {
			o << endl;
			o << "VETERAN";
		}
		o << endl;
		return o;
	}

	//overloading operator ==
	bool operator==(Vozac &tmp) {
		return this->payment() == tmp.payment();
	}

	//pure virtual method for overloading in the derived classes
	virtual float payment() = 0;

	//pure virtual method for overloadinf in the derived classes
	virtual float danok() = 0;

	//virtual destructor
	virtual ~Vozac(){}

	//get/set methods
	char *getName() { return name; }
	int getAge() { return age; }
	int getNoRaces() { return noRaces; }
	bool getVeteran() { return veteran; }
};

class Avtomobilist : public Vozac {
private:
	float autoPrice;
public:
	//constructor
	Avtomobilist(char *name = " ", int age = 0, int noRaces = 0, bool veteran = false, float autoPrice = 0) :Vozac(name, age, noRaces, veteran) {
		this->autoPrice = autoPrice;
	}

	//redefining payment
	float payment() {
		return autoPrice / 5;
	}

	//overloading tax
	float danok() {
		if (getNoRaces() > 10) {
			return payment() * (float)0.15;
		}
		else {
			return payment() * (float)0.1;
		}
	}
};

class Motociklist : public Vozac {
private:
	int motoPower;
public:
	//constructor
	Motociklist(char *name = " ", int age = 0, int noRaces = 0, bool veteran = false, int motoPower = 0) :Vozac(name, age, noRaces, veteran) {
		this->motoPower = motoPower;
	}

	//redefining payment
	float payment() {
		return motoPower * 20;
	}

	//overloading tax
	float danok() {
		if (getVeteran()) {
			return payment() * (float)0.25;
		}
		else {
			return payment() * (float)0.2;
		}
	}
};

int soIstaZarabotuvachka(Vozac **v, int n, Vozac *v1) {
	int counter = 0;
	for (int i = 0; i < n; i++) {
		if (*v[i] == *v1) {
			counter++;
		}
	}
	return counter;
}
int main() {
	int n, x;
	cin >> n >> x;
	Vozac **v = new Vozac*[n];
	char ime[100];
	int vozrast;
	int trki;
	bool vet;
	for (int i = 0; i < n; ++i) {
		cin >> ime >> vozrast >> trki >> vet;
		if (i < x) {
			float cena_avto;
			cin >> cena_avto;
			v[i] = new Avtomobilist(ime, vozrast, trki, vet, cena_avto);
		}
		else {
			int mokjnost;
			cin >> mokjnost;
			v[i] = new Motociklist(ime, vozrast, trki, vet, mokjnost);
		}
	}
	cout << "=== DANOK ===" << endl;
	for (int i = 0; i < n; ++i) {
		cout << *v[i];
		cout << v[i]->danok() << endl;
	}
	cin >> ime >> vozrast >> trki >> vet;
	int mokjnost;
	cin >> mokjnost;
	Vozac *vx = new Motociklist(ime, vozrast, trki, vet, mokjnost);
	cout << "=== VOZAC X ===" << endl;
	cout << *vx;
	cout << "=== SO ISTA ZARABOTUVACKA KAKO VOZAC X ===" << endl;
	cout << soIstaZarabotuvachka(v, n, vx);
	for (int i = 0; i < n; ++i) {
		delete v[i];
	}
	delete[] v;
	delete vx;
	return 0;
}