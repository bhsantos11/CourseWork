//CSE283 HW2:  Sparse Vector Operations  by C.-Y. (Roger) Chen
#include <iostream>
using namespace std;

class element {
public:
	int value;
	int position;
	element * next;
	element(int v, int p) { value = v; position = p; next = nullptr; }
	element() { next = nullptr; }
};

class vector { // How are the elements being assigned in the vector?
public:
	int vec_size;
	int num_elements;
	element * head;
	vector(int v) { vec_size = v; num_elements = 0; head = nullptr; }
	vector() { vec_size = 0; num_elements = 0; head = nullptr; }

	void add_element(int v, int p);
	void input_vector();
	void print_vector();
	vector operator+(vector &v);
	int operator*(vector &v);

};

void vector::add_element(int v, int p) {
	element * p1 = new element(v, p); 
	if (num_elements == 0) {
		head = p1; num_elements++; return;
	}
	if (p < head->position) { // Checks to see if p1 should be first vector
		p1->next = head; head = p1; num_elements++; return;
	}
	element * p2 = head;
	while (p2->next != nullptr && !(p > p2->position && p < p2->next->position)) { 
		p2 = p2->next; 
	}
	if (p2->next == nullptr) { p2->next = p1; num_elements++; return; }
	p1->next = p2->next; 
	p2->next = p1; 
	num_elements++;
}

void vector::input_vector() {
	int v_size, n_ele;
	cout << "Enter vector size and number of elements" << endl;
	cin >> v_size >> n_ele;
	vec_size = v_size;
	int v, p;
	cout << "Enter elements in value position pairs" << endl;
	for (int i = 0; i < n_ele; i++) {
		cin >> v >> p;
		add_element(v, p);
	}
}
void vector::print_vector() {
	element * p = head;
	while (p != nullptr) {
		cout << p->value << " " << p->position << "   ";
		p = p->next;
	}


}

vector vector::operator+(vector &v) {
	element * p1 = head, *p2 = v.head;
	if (vec_size != v.vec_size) { return -1; } // You can't add vectors of different sizes
	vector temp;
	temp.vec_size = vec_size;


	while (p1 != nullptr || p2 != nullptr) { // Check if && will work
		if (p1 == nullptr) { temp.add_element(p2->value, p2->position); p2 = p2->next; }
		else if (p2 == nullptr) { temp.add_element(p1->value, p1->position); p1 = p1->next; }

		else if (p1->position == p2->position) {
			int temp_val = p1->value + p2->value;
			temp.add_element(temp_val, p1->position);
			p1 = p1->next; // move to next element
			p2 = p2->next; // move to next element
		}
		else if (p1->position < p2->position) {
			temp.add_element(p1->value, p1->position);
			p1 = p1->next;
		}

		else if (p2->position < p1->position) {
			temp.add_element(p2->value, p2->position);
			p2 = p2->next;
		}
		
		

	}// end of while loop

	return temp;

}

int vector::operator*(vector &v) {
	element *p1 = head, *p2 = v.head;
	int result = 0;
	
	if (vec_size != v.vec_size) { return -1; }// You cant get inner products of vectors with different sizes
	if (head == nullptr) { return 0; }
	if (v.head == nullptr) { return 0; }

	while (p1 != nullptr && p2 != nullptr) {
		if (p1->position == p2->position) {
			int temp_val = (p1->value * p2->value);
			result += temp_val;
			p1 = p1->next; // move to next element
			p2 = p2->next; // move to next element
		}
		else if (p1->position < p2->position) {
			
			p1 = p1->next;
		}

		else if (p2->position < p1->position) {
			
			p2 = p2->next;
		}

		if (p1 == nullptr) {  }
		if (p2 == nullptr) {  }

		
	} // end of while loop
	return result;
}

int main() {
	vector v1, v2, v3;
	v1.input_vector();
	v1.print_vector();
	
	v2.input_vector();
	v2.print_vector();
	cout << endl << "V3" << endl; // To make output easier to read
	v3 = v1 + v2;
	v3.print_vector();
	cout << v1*v2 << endl;
	

	getchar();
	getchar();
	return 0;


}