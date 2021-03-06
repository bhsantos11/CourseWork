#include<iostream>
#include<vector>
#include <assert.h>
#include <time.h>
#include <thread>
using namespace std;

/* I have multiple functions to demonstrate each approach to threading and how it affects runtime
each function will incrementally use more threading with thread_1 using no threading despite its name

*/

void quick_sort(vector<int> &v1, int i, int j);
void print_vector(vector<int> v, int z) {
	for (int i = 0; i < z; i++) { cout << v[i] << ','; }
	cout << endl;
}
void thread_1(int n);
void thread_2(int n);
void thread_3(int n);
void thread_4(int n);
void thread_5(int n);


int split(vector<int> &v1, int b, int e, int p) {
	while (b <= e) {
		while (v1[b] < p) {
			b++;
		}
		while (v1[e] > p) {
			e--;
		}
		if (b <= e) {
			//swaps ints
			int temp = v1[b];
			v1[b] = v1[e];
			v1[e] = temp;

			b++;
			e--;
		}
	}// end of outter while loop
	return b;
}



void main()
{

	thread_1(800000);
	thread_2(800000);
	thread_3(800000);
	thread_4(800000);
	thread_5(800000);
	// as can be seen in each case the time it takes to sort has been reduced
	
	getchar();
	getchar();
	return;

}



void quick_sort(vector<int> &v1, int b, int e) {

	//terminating case
	if (b >= e) { return; }
	int pivot = v1[(b + e) / 2];
	int index = split(v1, b, e, pivot);
	quick_sort(v1, b, index - 1);
	quick_sort(v1, index, e);

}

void thread_1(int n) {
	vector<int> v(n + 1);
	cout << "Threading 1(featuring no threading)" << endl;
	for (int i = 0; i <= n; i++) { //i <= 800,000
		v[i] = (rand() % 100);
	}

	//Clock variables
	double run_time = 0;
	clock_t start, stop;

	// start clock
	assert((start = clock()) != -1);

	//sorting
	quick_sort(v, 0, n);

	//end sort
	stop = clock();
	run_time = (double)(stop - start) / CLOCKS_PER_SEC;
	cout << "Quick sorting took " << run_time << " time" << endl;

}

void thread_2(int n) {
	vector<int> v(n + 1);
	cout << "Threading 2, 2 threads working" << endl;
	for (int i = 0; i <= n; i++) { //i <= 800,000
		v[i] = (rand() % 100);
	}
	// Clock variables
	double run_time = 0;
	clock_t start, stop;

	// start clock
	assert((start = clock()) != -1);

	//sort
	thread t1(quick_sort, v, 0, (n / 2));
	thread t2(quick_sort, v, (n / 2) + 1, n);

	t1.join();
	t2.join();

	//end sort
	stop = clock();
	run_time = (double)(stop - start) / CLOCKS_PER_SEC;
	cout << "Quick sorting took " << run_time << " time" << endl;

}

void thread_3(int n) {
	vector<int> v(n + 1);
	cout << "Threading 3, 4 threads working" << endl;
	for (int i = 0; i <= n; i++) { //i <= 800,000
		v[i] = (rand() % 100);
	}
	// Clock variables
	double run_time = 0;
	clock_t start, stop;

	// start clock
	assert((start = clock()) != -1);

	//sort
	// Here each thread will be working through 1/4 of the vector
	thread t1(quick_sort, v, 0, (n / 4)); //from 0 to 1/4 of the vector
	thread t2(quick_sort, v, (n / 4) + 1, (n / 2)); // from 1/4 +1 to 1/2 of the vector
	thread t3(quick_sort, v, (n / 2) + 1, ((n / 4) * 3)); // from 1/2 +1 to 3/4 of the v
	thread t4(quick_sort, v, ((n / 4) * 3 + 1), n);

	t1.join();
	t2.join();
	t3.join();
	t4.join();
	//end sort

	stop = clock();
	run_time = (double)(stop - start) / CLOCKS_PER_SEC;
	cout << "Quick sorting took " << run_time << " time" << endl;

}

void thread_4(int n) {
	vector<int> v(n + 1);
	cout << "Threading 4, 8 threads working" << endl;
	for (int i = 0; i <= n; i++) { //i <= 800,000
		v[i] = (rand() % 100);
	}
	// Clock variables
	double run_time = 0;
	clock_t start, stop;

	// start clock
	assert((start = clock()) != -1);

	//sort
	// div is the number of elements each thread will be sorting
	int div = (n / 8);
	// Here each thread will be working through 1/4 of the vector
	thread t1(quick_sort, v, 0, div); //from 0 to 1/4 of the vector
	thread t2(quick_sort, v, div + 1, div * 2); // from 1/4 +1 to 1/2 of the vector
	thread t3(quick_sort, v, (div * 2) + 1, (div * 3)); // from 1/2 +1 to 3/4 of the v
	thread t4(quick_sort, v, (div * 3 + 1), (div * 4));
	thread t5(quick_sort, v, (div * 4) + 1, (div * 5));
	thread t6(quick_sort, v, (div * 5) + 1, (div * 6));
	thread t7(quick_sort, v, (div * 6) + 1, (div * 7));
	thread t8(quick_sort, v, (div * 7) + 1, (div * 8)); // div * 8 is equal to one but ill keep it as this for the sake of consistency


	t1.join();
	t2.join();
	t3.join();
	t4.join();
	t5.join();
	t6.join();
	t7.join();
	t8.join();
	//end sort

	stop = clock();
	run_time = (double)(stop - start) / CLOCKS_PER_SEC;
	cout << "Quick sorting took " << run_time << " time" << endl;

}

void thread_5(int n) {
	if (n < 1000) { cout << "Sorry n must be bigger than 1000" << endl; }
	vector<int> v(n + 1);
	cout << "Threading 5, 10 threads working" << endl;
	for (int i = 0; i <= n; i++) { //i <= 800,000
		v[i] = (rand() % 100);
	}
	// Clock variables
	double run_time = 0;
	clock_t start, stop;

	// start clock
	assert((start = clock()) != -1);

	//sort
	int div = n / 10;
	
	// Here each thread will be working through 1/4 of the vector
	thread t1(quick_sort, v, 0, div); //from 0 to 1/4 of the vector
	thread t2(quick_sort, v, div + 1, div * 2); // from 1/4 +1 to 1/2 of the vector
	thread t3(quick_sort, v, (div * 2) + 1, (div * 3)); // from 1/2 +1 to 3/4 of the v
	thread t4(quick_sort, v, (div * 3 + 1), (div * 4));
	thread t5(quick_sort, v, (div * 4) + 1, (div * 5));
	thread t6(quick_sort, v, (div * 5) + 1, (div * 6));
	thread t7(quick_sort, v, (div * 6) + 1, (div * 7));
	thread t8(quick_sort, v, (div * 7) + 1, (div * 8));
	thread t9(quick_sort, v, (div * 8) + 1, (div * 9));
	thread t10(quick_sort, v, (div * 9) + 1, (div * 10));

	t1.join();
	t2.join();
	t3.join();
	t4.join();
	t5.join();
	t6.join();
	t7.join();
	t8.join();
	t9.join();
	t10.join();

	//end sort

	stop = clock();
	run_time = (double)(stop - start) / CLOCKS_PER_SEC;
	cout << "Quick sorting took " << run_time << " time" << endl;

}