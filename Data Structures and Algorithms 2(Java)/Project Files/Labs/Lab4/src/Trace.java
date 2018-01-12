import java.io.*;

public class Trace {
    ////////////////////////////////////////////////////////////////////////
    // Class variables
    static int count = 0;
    ////////////////////////////////////////////////////////////////////////
    // Instance variables
    String name;

    ////////////////////////////////////////////////////////////////////////
    // This constructor stores the name String and prints out an
    // ``Entering'' message

    public Trace(String n) {
        name = n;
        Spaces(count*2);
        System.out.println(">>Entering: " + name);

    }

    ////////////////////////////////////////////////////////////////////////
    // This constructor stores the name String and prints out an
    // `Entering'' message using the name and the extra information
    // supplied in the String with.

    public Trace(String n, String with) {
        name = n;
        Spaces(count*2);
        System.out.println(">> Entering: " + name + " with: " + with);
        count++;


    }

    ////////////////////////////////////////////////////////////////////////
    // This method prints a farewell message when invoked.
    public void bye(String s) {
        count--;
        Spaces(count*2);
        System.out.println("<< Leaving:  " + name + s);


    }

    ////////////////////////////////////////////////////////////////////////
    // Given an integer n, this method prints n blank spaces on the
    // current line of output.

    private void Spaces(int n) {
        for (int i=0; i<n; i++ ) System.out.print(" ");
    }
    ////////////////////////////////////////////////////////////////////////
}

