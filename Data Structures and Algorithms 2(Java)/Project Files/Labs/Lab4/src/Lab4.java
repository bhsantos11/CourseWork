import java.io.*;

public class Lab4 {

    public static void main (String... argv) {
        System.out.println("fact(4) = "  + fact(4) + "\n");
        System.out.println("Even(4) = "  + Even(4) + "\n");
        System.out.println("fib(5) = "   + fib(5)  + "\n");
    }

    ////////////////////////////////////////////////////////////////////////
    // Our old friend the factorial function done recursively with tracing.
    public static int fact(int n) {
        Trace t = new Trace("fact","argument n = " + n);
        int result;
        if (n==0)
            result = 1;
        else
            result = n * fact(n-1);

        t.bye(" with result = " + result);
        return result;
    }

    ////////////////////////////////////////////////////////////////////////
    // This method tests whether an integer is even.
    public static boolean Even(int n) {
        Trace t = new Trace("Even","argument n = " + n);
        boolean result;
        if (n==0)
            result = true;
        else
            result = Odd(n-1);

        t.bye(" with result = " + result);
        return result;
    }

    ////////////////////////////////////////////////////////////////////////
    // This method tests whether an integer is even.

    public static boolean Odd(int n) {
        Trace t = new Trace("Odd","argument n = " + n);
        boolean result;
        if (n==0)
            result = false;
        else
            result = Even(n-1);

        t.bye(" with result = " + result);
        return result;
    }

    ////////////////////////////////////////////////////////////////////////
    // fib(n) = the nth Fibonacci number

    public static int fib(int n) {
        Trace t = new Trace("Fib","argument n = " + n);
        int result;

        if (n<2)
            result = n;
        else
            result = fib(n-2) + fib(n-1);

        t.bye(" with result = " + result);
        return result;
    }
    ////////////////////////////////////////////////////////////////////////
}

/*
//TEST Cases
Here I have the test cases showing that I did the modifications correctly and demonstrating what they output
Explanations are at the bottom
//Fact
>> Entering: fact with: argument n = 4
  >> Entering: fact with: argument n = 3
    >> Entering: fact with: argument n = 2
      >> Entering: fact with: argument n = 1
        >> Entering: fact with: argument n = 0
        << Leaving:  fact with result = 1
      << Leaving:  fact with result = 1
    << Leaving:  fact with result = 2
  << Leaving:  fact with result = 6
<< Leaving:  fact with result = 24
fact(4) = 24

//ODD EVEN
>> Entering: Even with: argument n = 4
  >> Entering: Odd with: argument n = 3
    >> Entering: Even with: argument n = 2
      >> Entering: Odd with: argument n = 1
        >> Entering: Even with: argument n = 0
        << Leaving:  Even with result = true
      << Leaving:  Odd with result = true
    << Leaving:  Even with result = true
  << Leaving:  Odd with result = true
<< Leaving:  Even with result = true
Even(4) = true

fib(5) = 5
// My indenting trick is just about having a static variable that keeps track of the depth,
    I named it count. So every time a new Trace object was created the count was incremented,
    and everytime bye was called(i.e exiting) the count was decremented.
    It was also important that I increment after printing to the console, and that I decremented before I printed to the console.

 */