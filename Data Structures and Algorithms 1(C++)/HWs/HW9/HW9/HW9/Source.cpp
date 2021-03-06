#include <iostream>
#include <vector>
#include <string>
using namespace std;

class ThreeD {
public:
	int ht;
	int wid;
	int dep;

	ThreeD(int i, int j, int k) { ht = i; wid = j; dep = k; }
	ThreeD() {}
	friend ostream & operator<< (ostream & stream, ThreeD t);
	friend istream & operator >> (istream & stream, ThreeD & t);
};

ostream & operator<< (ostream & stream, ThreeD t) {
	stream << "(" << t.ht << "," << t.wid << "," << t.dep << ")";
	return stream;
}
istream & operator >> (istream & stream, ThreeD & t) {
	stream >> t.ht >> t.wid >> t.dep;
	return stream;
}

class node {
public:
	int value;
	node * next;
	node(int i) { value = i; next = nullptr; }
	node() { next = nullptr; }
};
class linked_list {
public:
	int num_nodes;
	node * head;
	linked_list() { num_nodes = 0; head = nullptr; }
	friend ostream & operator<< (ostream & stream, linked_list t);

	void make_random_list(int k) {
		node * p;
		for (int i = 0; i < k; i++) {
			p = new node(rand() % 100);
			p->next = head;
			head = p;
			num_nodes++;
		}
	}
};
ostream & operator<< (ostream & stream, linked_list l) {
	node * p;
	p = l.head;
	while (p != nullptr) {
		stream << p->value << " ";
		p = p->next;
	}
	return stream;
}

template <class T> class bag {
public:
	vector<T> vec;


	// structure wide functions
	void clear() {
		vec.clear();
	}
	// item specific functions
	void add_back(T item) {
		vec.push_back(item);
	}
	void remove_back() {
		vec.pop_back();
	}
	// object info functions
	T return_back() {
		return vec.back();
	}
	T return_front() {
		return vec.front();
	}
	int return_size() {
		return vec.size();
	}

	// This is my print fuction
	void show_all() {
		vector<T>::iterator it1 = vec.begin();
		while (it1 != vec.end()) {
			cout << (*it1) << ' ';
			it1++;
		}
		cout << endl;
	}


	T operator[](int i) {
		return vec[i];
	}


};


int main() {

	//int test
	bag<int> int_bag;
	// add a couple of elements
	int_bag.add_back(5);
	int_bag.add_back(4);
	int_bag.add_back(9);
	// to show that index printing works
	cout << int_bag[1] << endl;
	// showing current bag
	int_bag.show_all();

	int_bag.remove_back();
	//showing bag after remove of back item
	int_bag.show_all();
	cout << "End of int example" << endl;
	//double test
	bag<double> double_bag;
	double_bag.add_back(5.6);
	double_bag.add_back(874.44);
	double_bag.show_all();
	double_bag.clear();
	double_bag.show_all();
	cout << "end of double example" << endl;
	//three_D test
	ThreeD objectA(1,2,3);
	ThreeD objectB(5, 10, 15);
	bag<ThreeD> threeD_bag;
	threeD_bag.add_back(objectA);
	threeD_bag.add_back(objectB);
	cout << threeD_bag[1];
	cout << "end of 3d example" << endl;
	//linked_list test
	linked_list linked_listObject;
	linked_listObject.make_random_list(8);
	bag<linked_list> linkedListBag;
	linkedListBag.add_back(linked_listObject);
	linkedListBag.show_all();
	//string test
	bag<string> string_bag;
	string_bag.add_back("Jello");
	string_bag.add_back("Paprika");
	string_bag.add_back("C++ is hard");
	string_bag.show_all();
	string_bag.remove_back();
	string_bag.remove_back();
	cout << "only one item should be left now" << endl;
	string_bag.show_all();


	system("pause");
	return 0;
}