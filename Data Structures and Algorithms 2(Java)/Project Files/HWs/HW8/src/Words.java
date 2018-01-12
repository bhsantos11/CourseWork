import java.io.*;
import java.util.Scanner;


/** The program  reads through the file fox.txt and, for each occurrence 
 * of a word of MINSIZE or more letters, it prints the word together
 * with the line number on which the word occured.
 */
public class Words {
    /** Minimal length of an input string to print. */
    public static final int MINSIZE = 6;
    ///////////////////////////////////////////////////////////////////////
    /** Takes a String, reduces it to lower case and, if the String ends
     * with "'s" or "s'", it strips off these endings and returns the
     * altered string.
     * <p>  E.g., tidy("Peter's") will return "peter".
     * @param str the string to tidy
     * @return the tidied string
     */
    public static String tidy(String str) {
        String ans = str.toLowerCase();
        if (ans.endsWith("'s") || ans.endsWith("s'"))
            return ans.substring(0,ans.length()-2);
        else
            return ans;
    }
    ///////////////////////////////////////////////////////////////////////
    /** the main program
     * @param argv not used
     * @throws IOException when "fox.txt" is not found.
     */
    public static void main (String argv[]) throws IOException {
        final String inFile = "fox.txt";

        // Build a Scanner for infile.
        Scanner inp = new Scanner(new File(inFile));
        int lineNumber = 1; // current line number of the input text

        System.out.println("Line#  Word\n-----------------");
        // Work through the input file line by line.
        while (inp.hasNextLine()) {
            // Get the next input line and build a Scanner out of it.
            Scanner inpLine = new Scanner(inp.nextLine());

            // Delimit tokens by whitespace (that is the \\s)
            // and any of: ',' '.' '!' '?' '-'.
            inpLine.useDelimiter("[\\s,.!:?-]");

            // Scan the line for tokens & print the ones of length >= MINSIZE
            while (inpLine.hasNext()) {
                String str = tidy(inpLine.next());
                if (str.length()>=MINSIZE)
                    System.out.printf("%5d: %s%n", lineNumber, str);
            }
            lineNumber++;
        }
    }
}