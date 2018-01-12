//HW5 Doubly Linked List
//Due: 2017_02_24  11:59pm
//Implement the member functions for class double_link
#include <iostream>
using namespace std;

class node {
public:
	int value;
	node * next;
	node * previous;
	node(int i) { value = i; next = previous = nullptr; }
	node() { next = previous = nullptr; }
};
class double_link {
public:
	node * head;
	node * tail;
	int num_nodes;
	double_link() { num_nodes = 0; head = tail = nullptr; }
	//Implement the following member functions:
	void random_create(int k);//create a double_link with k nodes, whose values are random in 0..99
	void reverse();//reverse the double_link
	void print_double_list();//print all values form head to tail
	void remove_all(int k);//remove all nodes with value = k
	double_link copy();//return a copy of the current double_link
	double_link combine(double_link &d);//return a double_link which combines current double_link with d
};
void double_link::random_create(int k) {
	node * p;
	for (int i = 0; i < k; i++) {
		p = new node(rand() % 100);
		if (num_nodes == 0) {
			head = tail = p;
			num_nodes++;
		}
		else {
			head->previous = p;
			p->next = head;
			head = p;
			num_nodes++;
		}
	}
}


void double_link::print_double_list() {
	node * p = head;
	cout << endl;
	while (p != nullptr) {
		cout << p->value << " ";
		p = p->next;
	}
	cout << endl;
}

double_link double_link::copy() {
	double_link L1;
	node * p1 = tail, *p2;
	while (p1 != nullptr) {

		p2 = new node(p1->value);
		if (L1.num_nodes == 0) {
			L1.head = L1.tail = p2;
			L1.num_nodes++;
		}
		else {
			L1.head->previous = p2;
			p2->next = L1.head;
			L1.head = p2;
			L1.num_nodes++;
		}
		p1 = p1->previous; // It has to go backwards ?

	}// end of while loop
	return L1;

}
 
double_link double_link::combine(double_link &dl) {
	double_link L;
	node * p;
	L.head = head;
	p = L.head;
	while (p->next != nullptr) p = p->next;
	p->next = dl.head;

	return L;
}


void double_link::reverse() {
	if (num_nodes <= 1) return; // If there is only one node or less there is nothing to reverse

	node * p1 = head, *p2 = p1->next, *p3;
	while (p2 != nullptr) {
		p3 = p2->next;
		p2->next = p1; // actual reversing
		p2->previous = p3; // actual reversing
		if (p1 == head) { p1->next = nullptr; p1->previous = p2; }
		if (p3 == tail) { p3->previous = nullptr; p3->next = p3->previous; } // Is this right?
		p1 = p2;
		p2 = p3;
	}// end of while loop
	head = p1;
}

void double_link::remove_all(int k) {
	node * p1 = head, * p2, * p3;

	while (p1 != nullptr) {
		while(p1->previous == nullptr && p1->value == k){ // Meaning value is head
			head = p1->next; // Making head skip prevous first element
			p1 = head; // Making p1 now point to first
		}
		p2 = p1->previous;
		p3 = p1->next;

		if (p1->value == k) {

			p2->next = p3;
			p3->previous = p2;
		}
		p1 = p1->next;

	}// end of while
}

int main() {
	double_link L1, L2, L3, L4;
	L1.random_create(7);
	L1.print_double_list();

	
	L2.random_create(5);
	L1.reverse();
	L1.print_double_list();
	L3 = L1.copy();
	L4 = L1.combine(L2);
	
	//create some tests for your implementation
	getchar();
	getchar();
	return 0;
}

