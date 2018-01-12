import java.io.*;
import java.util.*;

public class Test8 {
    public static final int MINSIZE = 6;
    //////////////////////////////////////////////////////////////////////
    /** Strip off "s'" and "s'" from the end of input strings.
     * <p><b>Example:</b> <code>tidy("Sam's")</code> returns "Sam".
     * @param str the String to tidy up
     * @return the result of the tidying
     */
    public static String tidy(String str) {
        String ans = str.toLowerCase();
        if (ans.endsWith("'s") || ans.endsWith("s'"))
            return ans.substring(0,ans.length()-2);
        else
            return ans;
    }
    //////////////////////////////////////////////////////////////////////
    /** Convert an Ordered list to an array
     * @param lst the list to convert
     * @return the array version of the list
     */
    public static String[] toArray(Ordered<String> lst) {
        String[] ans = new String[lst.size()];
        ListIterator<String> it = lst.listIterator(0);
        int i=0;
        while (it.hasNext()) { ans[i] = it.next(); i++; }
        return ans;
    }

    //////////////////////////////////////////////////////////////////////
    /** Test an application of the add method the Ordered class 
     * @param lst the list to be added to
     * @param str the String to add
     * @param target what the result should be (as an array of strings)
     */

    public static void checkAdd(Ordered<String> lst, String str, String... target) {
        System.out.print(" Testing: lst.add("+str+") = ");
        lst.add(str);
        if (Arrays.equals(toArray(lst),target))
            System.out.println(lst + "\n\tCHECK!");
        else
            System.out.println(lst + "\n\tWRONG! It should be: "+Arrays.toString(target));
        lst.linkCheck();
    }

    //////////////////////////////////////////////////////////////////////    
    /** Test an application of the neighbors method the Ordered class 
     * @param lst the list to be searched
     * @param str the String to find the neighbors of
     * @param target what the result should be (as an array of strings)
     */
    public static void checkNeighbors(Ordered<String> lst, String str, String... target) {
        System.out.print(" Testing: lst.neighbors("+str+") = ");
        Ordered<String> result = lst.neighbors(str);
        if (Arrays.equals(toArray(result),target))
            System.out.println(result + "\n\tCHECK!");
        else
            System.out.println(result + "\n\tWRONG! It should be: "+Arrays.toString(target));
        result.linkCheck();
    }


    //////////////////////////////////////////////////////////////////////
    /**
     *  ProcessFile()
     *    @param s   scanner for the file
     *    @param t1  table using one hash method
     *    @param t2  table using anothe hash method
     */

    public static void processFile(Scanner s, ChainedHashTable<Entry> t1,
                                   ChainedHashTable<Entry> t2) {
        // Add code to process a file
        // At the end of this pass, both hash tables should be
        // properly loaded.
        int lineNumber = 0;


        while(s.hasNext()){
            String word = s.next();
            System.out.println(word);
            if(word.length() >= 6){ // counting only important works

            }

        }// end of while loop

    }

    //////////////////////////////////////////////////////////////////////
    public static void main(String[] argv) throws IOException  {
        // PART I
        Ordered<String> emp  = new Ordered<String>();
        Ordered<String> wrds = new Ordered<String>();

        System.out.println("\n**TESTING add**\n");
        checkAdd(wrds,"owl",  "owl");
        checkAdd(wrds,"gnu",  "gnu","owl");
        checkAdd(wrds,"yak",  "gnu","owl","yak");
        checkAdd(wrds,"bee",  "bee","gnu","owl","yak");
        checkAdd(wrds,"cat",  "bee","cat","gnu","owl","yak");
        checkAdd(wrds,"eel",  "bee","cat","eel","gnu","owl","yak");
        checkAdd(wrds,"dog",  "bee","cat","dog","eel","gnu","owl","yak");
        checkAdd(wrds,"cow",  "bee","cat","cow","dog","eel","gnu","owl","yak");
        checkAdd(wrds,"rat",  "bee","cat","cow","dog","eel","gnu","owl","rat","yak");
        checkAdd(wrds,"ram",  "bee","cat","cow","dog","eel","gnu","owl","ram","rat","yak");

        System.out.println("\n**TESTING neighbors**\n");
        checkNeighbors(emp, "gnu");
        checkNeighbors(wrds,"ant",   "bee");
        checkNeighbors(wrds,"bee",   "bee","cat");
        checkNeighbors(wrds,"zebra", "yak");
        checkNeighbors(wrds,"yak",   "rat","yak");
        checkNeighbors(wrds,"eel",   "dog","eel","gnu");
        checkNeighbors(wrds,"ewe",   "eel","gnu");


        Iterator<String> it = wrds.iterator();
        System.out.print("The list wrds: " );
        while (it.hasNext()) {
            String wrd = it.next();
            System.out.print(wrd+" ");
        }
        System.out.println("\n");


        // PART II
        System.out.println("\n**Comparing Hash Functions**\n");

        /** two tables of Entry's to compare the hash functions */
        ChainedHashTable<Entry> tab1;
        ChainedHashTable<Entry> tab2;

        String[] inFiles = {"jabberwocky.txt", "fox.txt", "linuxwords.txt"};
        String[] hashOutputs = {
                "Buckets =     32  Elements =     29  Bucket sizes ::  min =  0  max =  3  avg = 1.103  std.dev = 0.935",
                "Buckets =     32  Elements =     29  Bucket sizes ::  min =  0  max =  6  avg = 1.103  std.dev = 1.346",
                "Buckets =     64  Elements =     48  Bucket sizes ::  min =  0  max =  3  avg = 1.333  std.dev = 1.102",
                "Buckets =     64  Elements =     48  Bucket sizes ::  min =  0  max = 14  avg = 1.333  std.dev = 2.045",
                "Buckets =  65536  Elements =  38378  Bucket sizes ::  min =  0  max =  6  avg = 1.708  std.dev = 1.360",
                "Buckets =  65536  Elements =  38378  Bucket sizes ::  min =  0  max = 42  avg = 1.708  std.dev = 1.754"};

        // initial loop just processes the first file; change
        // loop termination condition to process all three once
        // it's working for the simple case.
        for (int i = 0; i < 1; i++) {
            Scanner inp = new Scanner(new File(inFiles[i]));

            tab1 = new ChainedHashTable<Entry>();
            tab2 = new ChainedHashTable<Entry>();

            if (!tab2.setSelector(false)) {
                System.out.println("Couldn't turn off selector for tab2.");
                break;
            }

            // scan the text, build the hash tables, tab1 and tab2
            // This method does all the work...you have to write it.

            processFile(inp, tab1, tab2);

            // Now check the stats of the hash tables.
            String out1 = tab1.stats();
            String out2 = tab2.stats();

            // Note:  Don't check the output for the original function,
            //        as it depends on a random number and can give slightly
            //        different results each time for max and stddev.  They're
            //        included in the array of outputs for your edification.
            //        They are for the fixed value of z given in ChainedHashTable.java
            //        (commented out in the constructor).
            System.out.println("-------------");
            System.out.println(inFiles[i]);
            System.out.println("T1 results: " + out1);
            System.out.println("T2 results: " + out2);
            if (hashOutputs[(2*i)+1].compareTo(out2) != 0) {
                System.out.println("\tWRONG!!  It should be: \"" +
                        hashOutputs[(2*i)+1] + "\"");
            }
        }

    }

    //////////////////////////////////////////////////////////////////////
}