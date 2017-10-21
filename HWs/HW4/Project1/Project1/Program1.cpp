//HW4 initial code
//Re-Do HW1 using vector/list  and map/list
// VECTOR APPLICATION VERSION
#include <iostream>
#include <vector>
#include <list>
#include <map>
#include <string>
using namespace std;

class course {
public:
	string name;
	int section;
	int credits;
	course(string n, int s, int c) { name = n; section = s; credits = c; }
	course() {}
	bool operator==(course c) { return name == c.name; }//to use find
	bool operator<(course c) { return credits < c.credits; }//to use sort


};


class student {
public:
	int ID;
	list<course> LC;
	student(int id) {
		ID = id;
	}
	student() {}
	bool operator==(student s) { return ID == s.ID; }
	bool operator<(student s) { return ID == s.ID; }
};


// FUNCTION PROTOTYPES
void add_student(vector<student> &DB);
void add_course(vector<student> &DB);
void del_student(vector<student> &DB);
void del_course(vector<student> &DB);
void total_credits(vector<student> &DB);
void student_courses(vector<student> &DB);
void student_check(vector<student> &DB);
void student_course_check(vector<student> &DB);
void printStudents(vector<student> &DB);
void printDB(vector<student> &DB);

int main() {
	vector<student> DB;
	int select;
	do {
		cout << "Enter 0 to quit!" << endl;
		cout << "Enter 1 to add a student" << endl;
		cout << "Enter 2 to add a course to a student" << endl;
		cout << "Enter 3 to delete student" << endl;
		cout << "Enter 4 to delete student course" << endl;
		cout << "Enter 5 to print total credits" << endl;
		cout << "Enter 6 to print student courses" << endl;
		cout << "Enter 7 to check if a student is in the DB" << endl;
		cout << "Enter 8 to check if a student is in a class" << endl;
		cout << "Enter 9 to print the students in the DB" << endl;
		cout << "Enter 10 to print the entire DB" << endl;
		cin >> select;
		switch (select) {
		case 0: break;
		case 1: add_student(DB); break;
		case 2: add_course(DB); break;
		case 3: del_student(DB); break;
		case 4: del_course(DB); break;
		case 5: total_credits(DB); break;
		case 6: student_courses(DB); break;
		case 7: student_check(DB); break;
		case 8: student_course_check(DB); break;
		case 9: printStudents(DB); break;
		case 10: printDB(DB);  // Why no break in case 10
		}
	} while (select != 0);



	system("pause");
	return 0;
}

// FUNCTION definition


void add_student(vector<student> &DB) {
	int id;
	cout << "Input student ID" << endl;
	cin >> id;

	student s(id);
	vector<student>::iterator it1 = DB.begin();

	while (it1 != DB.end()) {

		if (*it1 == s) {
			cout << "ERROR STUDENT ALREADY IN DB" << endl;
			return;
		}
		it1++;

	}// end of while loop


	DB.push_back(s);
	cout << "Student successfully added to DB" << endl;
}

void add_course(vector<student> &DB) {
	//Student input
	int id;
	cout << "Input student ID" << endl;
	cin >> id;

	//Course
	//variables
	string course_name;
	int section;
	int credits;
	//input
	cout << "Enter course name" << endl;
	cin >> course_name;
	cout << "Enter course section" << endl;
	cin >> section;
	cout << "Enter course credit" << endl;
	cin >> credits;
	//declaration
	course c(course_name, section, credits);

	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {

		if (it1->ID == id) {
			it1->LC.push_back(c);
			cout << "Course added to student successfully" << endl;
			break;
		}

		it1++;
	}// end of while loop

	if (it1 == DB.end()) cout << "ERROR STUDENT NOT IN DB" << endl;

}

void del_student(vector<student> &DB) {
	int del = 0;
	// Student input
	int id;
	cout << "Enter student ID" << endl;
	cin >> id;

	vector<student>::iterator it1 = DB.begin();


	while (it1 != DB.end()) {

		if (it1->ID == id) {
			DB.erase(it1);
			cout << "Student deleted successfully" << endl;
			del = 1;
			break;
		}

		it1++;
	}// end of while loop

	if (del = 0) cout << "ERROR STUDENT NOT IN DB" << endl;


}

void del_course(vector<student> &DB) {
	int del = 0;
	//Student input
	int id;
	cout << "Enter student ID" << endl;
	cin >> id;
	student s(id);

	// Course input
	string course_name;
	cout << "Enter course name" << endl;
	cin >> course_name;



	// Finding student
	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {
		if (*it1 == s) break;
		it1++;
	}

	if (it1 == DB.end()) {
		cout << "Error student not found" << endl;
		return;
	}

	// Finding and deleting course
	list<course>::iterator it2 = it1->LC.begin();
	while (it2 != it1->LC.end()) {
		if (it2->name == course_name) {
			it1->LC.erase(it2);
			del = 1;
			cout << "Course deletion successfull" << endl;
			break;
		}

	}// end of while

	if (del == 0) cout << "ERROR COURSE NOT FOUND" << endl;

}

void total_credits(vector<student> &DB) {
	// Student
	int id;
	cout << "Input student ID" << endl;
	cin >> id;
	student s(id);

	// Finding student
	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {
		if (*it1 == s) break;
		it1++;
	}

	if (it1 == DB.end()) {
		cout << "Error student not found" << endl;
		return;
	}

	//Gathering credits from courses
	int total_credits = 0;
	list<course>::iterator it2 = it1->LC.begin();

	while (it2 != it1->LC.end()) {
		total_credits += it2->credits;
		it2++;
	}

	cout << "Total number of credits: " << total_credits << endl;


}

void student_courses(vector<student> &DB) {
	// Student
	int id;
	cout << "Input student ID" << endl;
	cin >> id;
	student s(id);

	// Finding student
	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {
		if (*it1 == s) break;
		it1++;
	}

	if (it1 == DB.end()) {
		cout << "Error student not found" << endl;
		return;
	}

	//Printing out courses
	int total_credits = 0;
	list<course>::iterator it2 = it1->LC.begin();

	while (it2 != it1->LC.end()) {
		cout << it2->name << ' ' << it2->section << ' ' << it2->credits << endl;
		it2++;
	}
}

void student_check(vector<student> &DB) {
	// Student
	int id;
	cout << "Input student ID" << endl;
	cin >> id;
	student s(id);

	// Finding student
	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {
		if (*it1 == s) {
			cout << "Student is in DB" << endl;
			break;
		}
		it1++;
	}// end of while

	if (it1 == DB.end()) {
		cout << "Student not in DB" << endl;
	}

}

void student_course_check(vector<student> &DB) {
	//Student input
	int id;
	cout << "Enter student ID" << endl;
	cin >> id;
	student s(id);

	// Course input
	string course_name;
	cout << "Enter course name" << endl;
	cin >> course_name;



	// Finding student
	vector<student>::iterator it1 = DB.begin();
	while (it1 != DB.end()) {
		if (*it1 == s) break;
		it1++;
	}

	if (it1 == DB.end()) {
		cout << "Error student not found" << endl;
		return;
	}

	// Finding course
	list<course>::iterator it2 = it1->LC.begin();
	while (it2 != it1->LC.end()) {
		if (it2->name == course_name) {
			cout << "Student is in course" << endl;
			break;
		}
		it2++;
	}// end of while

	if (it2 == it1->LC.end()) cout << "Student not in course" << endl;
}

void printStudents(vector<student> &DB) {
	vector<student>::iterator it1 = DB.begin();

	cout << "Here are all the students IDs in the DB" << endl;
	while (it1 != DB.end()) {
		cout << it1->ID << endl;
		it1++;
	}


}
void printDB(vector<student> &DB) {

	vector<student>::iterator it1 = DB.begin();

	cout << "Here are all the students in the DB and their courses" << endl;
	while (it1 != DB.end()) {
		cout << "Student: " << it1->ID << endl;

		// Looping through student's courses
		list<course>::iterator it2 = it1->LC.begin();
		while (it2 != it1->LC.end()) {
			cout << it2->name << ' ' << it2->section << ' ' << it2->credits << endl;
			it2++;
		} // end of inner course while loop
		it1++;
	}// end of outer student while loop

}