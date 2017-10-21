//HW1 Initial Code by C-Y (Roger) Chen

#include <iostream>
#include <string>
using namespace std;
class course {
public:
	string name;//such as "MAT295"
	int section;
	int credits;
	course * next;
	course(string n, int s, int c) {
		name = n;
		section = s;
		credits = c;
		next = nullptr;
	}
	course() { next = nullptr; }
};
class student {
public:
	int ID;
	course * head;
	student() { head = nullptr; }
	student(int i) { ID = i; head = nullptr; }
};

void add_student(student DB[], int &num_stu);
void add_course(student DB[], int &num_stu);
void printDB(student DB[], int &num_stu);
void del_student(student DB[], int &num_stu);
void del_course(student DB[], int &num_stu);
void total_credits(student DB[], int &num_stu);
void student_courses(student DB[], int &num_stu);
void student_check(student DB[], int &num_stu);
void student_course_check(student DB[], int &num_stu);
void printStudents(student DB[], int &num_stu);

int main() {
	int num_stu = 0;
	student DB[10];
	int select;
	do {
		cout << " Enter 0 to quit!" << endl;
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
		case 1: add_student(DB, num_stu); break;
		case 2: add_course(DB, num_stu); break;
		case 3: del_student(DB, num_stu); break;
		case 4: del_course(DB, num_stu); break;
		case 5: total_credits(DB, num_stu); break;
		case 6: student_courses(DB, num_stu); break;
		case 7: student_check(DB, num_stu); break;
		case 8: student_course_check(DB, num_stu); break;
		case 9: printStudents(DB, num_stu); break;
		case 10: printDB(DB, num_stu);  // Why the breaks? Why no break in case 9
		}
	} while (select != 0);




	getchar();
	getchar();
	return 0;
}

void add_student(student DB[], int &num_stu) {
	int id;
	cout << "Enter ID" << endl;
	cin >> id;
	int found = 0;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = 1;
	}
	if (found == 1) {
		cout << "The student is already in DB";
		return;
	}
	DB[num_stu].ID = id;
	DB[num_stu].head = nullptr;
	num_stu++;
	return;

}

void del_student(student DB[], int &num_stu) {
	int id;
	cout << "Enter ID" << endl;
	cin >> id;
	int found = -1;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = i ; // i != index, found is the index of student
	}
	if (found == -1) {
		cout << "No such student exists in the DB";
		return;
	}
	// Unsure on how to delete student
	// For loop to bump up any students with indexes > 1
	for (int i = (found + 1); i < num_stu; i++) { // loop starts at found as only students with index > found need to be changed
		DB[i - 1] = DB[i];

	}
	num_stu--;

}


void add_course(student DB[], int &num_stu) {
	int id;
	cout << "Enter ID" << endl;
	cin >> id;
	int found = 0;
	int index;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = 1;
		index = i;
	}
	if (found == 0) {
		cout << "The student is not in DB";
		return;
	}
	string n;
	int s, c;
	cout << "Enter course name, section, credits separated by space" << endl;
	cin >> n >> s >> c;
	course * p = new course(n, s, c);
	p->next = DB[index].head;
	DB[index].head = p;
	return;
}

void del_course(student DB[], int &num_stu) {
	int id;
	int index;
	cout << "Enter ID" << endl;
	cin >> id;
	int found = -1;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = 1;
		index = i;
	}
	if (found == 0) {
		cout << "The student is not in DB";
		return;
	}

	string n;
	cout << "Enter course name" << endl;
	cin >> n;
	course * current = DB[index].head;
	course * previous = nullptr;
	while (current != nullptr) {
		if (current->name == n) { // If course name is found

			if (previous == nullptr) { // In case
				DB[index].head = current->next;
			}
			else
			{
				previous->next = current->next;
			}
			
			return;
		}
		previous = current;
		current = current->next;


	}

}
void printDB(student DB[], int &num_stu) {
	course * p;
	for (int i = 0; i < num_stu; i++) {
		p = DB[i].head;
		cout << DB[i].ID << endl;
		while (p != nullptr) {
			cout << p->name << " " << p->section << " " << p->credits << endl;
			p = p->next;
		}

	}
}

void printStudents(student DB[], int &num_stu) {
	for (int i = 0; i < num_stu; i++) {
		cout << DB[i].ID << endl;
	}
}

void total_credits(student DB[], int &num_stu) {
	int id;
	int index;
	
	cout << "Enter ID" << endl;
	cin >> id;
	int found = -1;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = 1;
		index = i;
	}
	if (found == 0) {
		cout << "The student is not in DB";
		return;
	}

	course * p;
	p = DB[index].head;
	int credit_totals = 0;
	while (p != nullptr) {
		credit_totals += p->credits;
		p = p->next;
	}
	cout << credit_totals << endl;
}

void student_courses(student DB[], int &num_stu) {
	int id;
	int index;

	cout << "Enter ID" << endl;
	cin >> id;
	int found = -1;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) found = 1;
		index = i;
	}
	if (found == -1) {
		cout << "The student is not in DB";
		return;
	}
	course * p;
	p = DB[index].head;

	while (p != nullptr) {
		cout << p->name << " " << p->section << " " << p->credits << endl;
		p = p->next;
	}
}

void student_check(student DB[], int &num_stu) {
	int id;

	cout << "Enter ID" << endl;
	cin >> id;
	int found = 0;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) {
			cout << "Student is in DB" << endl;
			found = 1;
		}
	}
	if (found == 0) {
		cout << "The student is not in DB";
		return;
	}
}

void student_course_check(student DB[], int &num_stu) {
	int id;
	int idx;
	string cn;
	cout << "Enter ID" << endl;
	cin >> id;
	int found = -1;
	for (int i = 0; i < num_stu; i++) {
		if (DB[i].ID == id) {
			found = i;
			idx = i;
		}
	}
	if (found == -1) {
		cout << "The student is not in DB";
		return;
	}

	cout << "Course name" << endl;
	cin >> cn;

	course * p;
	found = -1;
	p = DB[idx].head;
	while (p != nullptr) {
		if (p->name == cn) {
			cout << "Student is in class" << endl;
			found = 1;
		}
		p = p->next;
	}

	if (found == -1) {
		cout << "Student not in class" << endl;
	}

}