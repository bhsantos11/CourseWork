//PROBLEM 5
#include <iostream>
using namespace std;

class node {
public:
	int value;
	node * next;
	node() { next = nullptr; }
	node(int v) { value = v; next = nullptr; }
};
class linked_list {
public:
	int num_nodes;
	node * head;
	linked_list() { num_nodes = 0; head = nullptr; }
	void make_random_linked_list(int k);
	void print();
	//***********   PROBLEM 5   ***********************************************
	//Wrire code for member function remove_all;
	void remove_all(int k); //Remove all nodes with value k
							//*************************************************************************

};

void linked_list::print(){
	node * p = head;
	while (p != nullptr) {
		cout << p->value << " ";
		p = p->next;
	}
	cout << endl;
}

void linked_list::remove_all(int k) {
	node * p = head, *p1 = p->next;

	while (p1 != nullptr) {
		while (head->value == k) head = p1; // In case first item is k
		if (p1->value == k && p1->next == nullptr) {// in case last item is value k
			p->next = nullptr;
			break;
			cout << "final if triggered" << endl;
		}
		if (p1->value == k && !((p1->next)->value == k)) {
			p->next = p1->next;
		}
		else if (p1->value == k && ((p1->next)->value == k)) { 
			p->next = (p1->next)->next;
		}
		p = p->next;
		p1 = p->next;
	}// end of while loop
}

void linked_list::make_random_linked_list(int k) {//This member function creaates a linked list
												  //of k nodes with each node having a random value between 0 and 99
	node * p;
	for (int i = 0; i < k; i++) {
		p = new node(rand() % 6);//create a new node with random value between 0 and 29
		p->next = head;
		head = p;
		num_nodes++;

	}
}

int main() {

	linked_list L;
	L.make_random_linked_list(50);
	L.print();
	L.remove_all(4);
	cout << "Now all 4s should be removed" << endl;
	L.print();
	getchar();
	getchar();
	return 0;

}