#include <iostream>
#include <fstream>
#include <stack>
#include <string>


using namespace std;


class tree_node {
public:
	string value;
	tree_node * left_child;
	tree_node * right_child;
	tree_node(string s) { value = s; left_child = right_child = nullptr; }
	tree_node() {}
};


double stack_eval(string f);
tree_node* tree_build(string f);
double tree_eval(tree_node*);

void print_tree(tree_node * p);
int main() {

	cout << stack_eval("exp.txt") << endl; // Non Tree Evaluate
	tree_node* node = tree_build("exp.txt"); // Forming the Tree
	double r = tree_eval(node); // Evaluating the Tree
	cout << r << endl;
	print_tree(node); // Printing the Tree
	system("pause");
	return -1;
}

void print_tree(tree_node * p) {
	if (p->value != "*" && p->value != "/" && p->value != "+"  && p->value != "-") {
		cout << p->value << " ";
		return;
	}
	print_tree(p->left_child);
	print_tree(p->right_child);
	cout << p->value << " ";
}

double stack_eval(string f) {
	double d, x, y;
	string s;
	stack<double> stack;
	ifstream in(f);


	while (in >> s) {// while (in >>s)
		if (s == "*" || s == "+" || s == "/" || s == "-") {
			x = stack.top();
			stack.pop();
			y = stack.top();
			stack.pop();
			double r;
			if (s == "*") r = y * x;
			else if (s == "+") r = y + x;
			else if (s == "-") r = y - x;
			else if (s == "/") r = y / x;
			stack.push(r);
		}
		else {
			d = stod(s);//string to double
			stack.push(d);
		}
	}// end of while
	return stack.top();

}

tree_node* tree_build(string f) {

	// Build Tree
	ifstream in(f);
	string s;
	stack<tree_node *> stack;

	while (in >> s) {// while (in >>s)
		if (s == "*" || s == "+" || s == "/" || s == "-") {
			tree_node *x = stack.top();
			stack.pop();
			tree_node *y = stack.top();
			stack.pop();
			tree_node * node = new tree_node(s);
			node->right_child = hhhx; // Revise why its not *x
			node->left_child = y;
			stack.push(node);
		}
		else {
			tree_node *node = new tree_node(s);
			stack.push(node);
		}
	}// end of while

	 //Evaluating Tree
	return stack.top();

}

double tree_eval(tree_node* root) {

	if (!(root->value == "*" || root->value == "+" || root->value == "/" || root->value == "-")) return stod(root->value);

	double y = tree_eval(root->left_child);
	double x = tree_eval(root->right_child);

	double r;

	if (root->value == "*") r = y * x;
	else if (root->value == "+") r = y + x;
	else if (root->value == "-") r = y - x;
	else if (root->value == "/") r = y / x;

	return r;
}
