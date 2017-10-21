#include <iostream>
#include <stack>
using namespace std;

class D {
public:
	int d1;

};


class C {
public:
	D c1;

};

class B {
public:
	C  *b1;

};

class A {
public:
	int x;
	B  a1;
	A(int i) {
		x = i;
	}
};





int main() {
	A a(1);
	a.a1.b1->c1.d1 = 100;
	cout << a.a1.b1->c1.d1 << endl;

	system("pause");
	return 0;
}