/*HW12:  Sudoku games
Due: 11:59PM, April 28, 2017 (Friday)
Develop a program to solve Sudoku games.*/


#include<iostream>

using namespace std;

void entergrid(int grid[][9]);
void viewgrid(int grid[][9]);
//void elimi(int grid[][9], int); // not exactly sure what this function is supposed to do
bool line_check(int grid[][9], int n, int x, int y); // function will check if number can go in based on horizontal and vertical axis'
bool square_check(int grid[][9], int n, int z); // function will check if number can go in based on square


int main()
{
	//int i, j, k, i1, i2, j1, j2, p, count, po, po_x, po_y;

	int grid[9][9] =
	{

		{ 0, 7, 5, 0, 0, 0, 8, 4, 0 },
		{ 2, 0, 0, 5, 0, 8, 0, 3, 0 },
		{ 0, 0, 1, 0, 0, 3, 0, 2, 5 },
		{ 5, 2, 0, 3, 0, 0, 0, 1, 0 },
		{ 0, 0, 0, 9, 0, 5, 0, 0, 0 },
		{ 0, 9, 0, 0, 0, 4, 0, 6, 3 },
		{ 4, 5, 0, 7, 0, 0, 3, 0, 0 },
		{ 0, 8, 0, 4, 0, 6, 0, 0, 7 },
		{ 0, 1, 3, 0, 0, 0, 6, 8, 0 }
	};


	viewgrid(grid);
	bool test_val = line_check(grid, 3, 0, 0);
	

	getchar();
	getchar();

	return 0;
}

//
//You can modify it to allow reading the input from a file
void entergrid(int grid[][9])
{


	cout << "Enter grid\n\n";
	for (int i = 0; i < 9; i++)
		for (int j = 0; j < 9; j++)
			cin >> grid[i][j];
}

void viewgrid(int grid[][9])
{
	cout << "The following are the grid values: \n\n";
	for (int i = 0; i < 9; i++)
	{
		cout << " -------------------------------------\n";

		for (int j = 0; j < 9; j++)
			cout << " | " << grid[i][j];

		cout << " |\n";
	}
}

bool line_check(int grid[][9], int n, int x, int y) {
	bool horizontal = true;
	bool vertical = true;

	for (int i = 0; i <= 8; i++) {
		if (grid[x][i] == n) {
			horizontal = false;
			cout << "Horizontal value found equal to n at x = " << x << " and y = " << i << endl;
			cout << "With value being " << grid[x][i] << endl;
		}
		if (grid[i][y] == n) {
			vertical = false;
			cout << "Vertical value found equal to n at x = " << i << " and y = " << y << endl;
			cout << "With value being " << grid[i][y] << endl;
		}
	} // end of for loop

	if (horizontal && vertical)
		cout << "Both horizontal and vertical are valid" << endl;
	else if (horizontal && !vertical)
		cout << "Horizontal is valid but vertical is not" << endl;
	else if (!horizontal && vertical)
		cout << "Vertical is valid but horizontal is not" << endl;
	else if (!horizontal && !vertical)
		cout << "Both horizontal and vertical are invalid" << endl;

	return (horizontal && vertical);


}
bool square_check(int grid[][9], int n, int z) {

}