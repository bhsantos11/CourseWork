//PROBLEM 4
#include <iostream>
#include <list>
#include <vector>
#include <map>
using namespace std;

void print_DB(list<list<int>> &DB);
void DB_to_DB1(list<list<int>> &DB, vector<vector<int>> &DB1);
void print_DB1(vector<vector<int>> &DB1);
void DB_to_DB5(list<list<int>> &DB, vector<list<int> *> &DB5);
void print_DB5(vector<list<int> *> &DB5);


//********  PROBLEM 4 ***************************
//Write code for the following two functions
void DB5_to_DB6(vector<list<int> *> &DB5, list<vector<int> *> &DB6);
void print_DB6(list<vector<int> *> &DB6);
//**********************************************

void DB5_to_DB6(vector<list<int> *> &DB5, list<vector<int> *> &DB6) {
	vector<list<int> *>::iterator it1 = DB5.begin();
	while (it1 != DB5.end()) { // outer loop to iterate through pointers in vector
		vector<int> * p = new vector<int>; // vector that will be pushed into DB6
		list<int>::iterator it2 = (*it1)->begin();
		while (it2 != (*it1)->begin()) { // inner loop to iterate through list
			p->push_back(*it2); // Adding int from DB5 list to new vector
		}
		DB6.push_back(p); // Pushing created vector pointer to DB6 list
		it1++;
	} // end of outer loop
}

void print_DB6(list<vector<int> *> &DB6) {
	list<vector<int> *>::iterator it1 = DB6.begin();

	while (it1 != DB6.end()) { // out loop to iterate through pointers in listlist
		vector<int>::iterator it2 = (*it1)->begin(); // ? (*it1) dereferences the iterator, -> dereferences the pointer
		while (it2 != (*it1)->end()) { // inner loop to iterate through vector
			cout << *it2 << endl;
			it2++;
		}
		it1++; // Makes it1 point to the next pointer which is pointing to the next list
	}
}

int main() {

	list< list<int> > DB = { { 2,3,4 },{ 11,15 },{ 4,6,8,11,5 } };
	print_DB(DB);


	vector<list<int> *> DB5;

	DB_to_DB5(DB, DB5);
	print_DB5(DB5);

	list<vector<int> *> DB6;

	getchar();
	getchar();
	return 0;

}





