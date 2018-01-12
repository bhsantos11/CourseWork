import java.util.Iterator;

// File: Test6.java
/********************************************************************
 * Tests for the SList and Deck classes.
 * @author
 *   Bernardo Santos
 * @version
 *   20-Oct-2017
 *******************************************************************/

public class Test6 {



    ////////////////////////////////////////////////////////////////////
    // Testing Utilities
    ////////////////////////////////////////////////////////////////////
    public static SList<Character> make(String str) {
        SList<Character> ans = new SList<Character>();
        for (int i=0; i<str.length(); i++)
            ans.add(str.charAt(i));
        return ans;
    }

    ////////////////////////////////////////////////////////////////////
    public static void addAllTest(SList<Character> lst,
                                  SList<Character> arg,
                                  SList<Character>target)
    {
        System.out.print("For lst = "+lst+" and arg = "+arg+":\n  after lst.addAll(arg), lst =");
        lst.addAll(arg);
        System.out.print(lst);
        System.out.println((target.equals(lst))?" Check!":" **WRONG**, it should be " + target);
    }
    ////////////////////////////////////////////////////////////////////
    public static void locTest(SList<Character> lst,
                               Character e,
                               SList<Integer>target)
    {
        SList<Integer> locs = lst.locationsOf(e);
        System.out.print("locationsOf says the locations of " +
                e + " in " + lst + " are " + locs);
        if (!target.equals(locs))
            System.out.println("  **WRONG!** \n\tThese should be "+target);
        else
            System.out.println("  Check!");
    }
    ////////////////////////////////////////////////////////////////////
    public static void dropTest(SList<Character> lst, int k, SList<Character> target) {
        System.out.print("Starting with lst = "+lst);
        lst.drop(k);
        System.out.print(", lst.drop("+k+") results with lst = " + lst);
        if (!target.equals(lst))
            System.out.println("  **WRONG!** \n\tIt should be "+target);
        else
            System.out.println("  Check!");
    }

    ////////////////////////////////////////////////////////////////////
    public static void splitTest(SList<Character> lst,
                                 int k,
                                 SList<Character> front,
                                 SList<Character> back) {
        System.out.print("Starting with lst = "+lst);
        SList<Character> result = lst.splitAt(k);
        System.out.print(", lst.splitAt("+k+") returns " + result +" and lst becomes " +lst);
        if (!front.equals(result) || !back.equals(lst))
            System.out.println("\n  **WRONG!**\tIt should return "+front + " and lst should be " + back);
        else
            System.out.println("  Check!");
    }

    ////////////////////////////////////////////////////////////////////
    public static void revCopyTest(SList<Character> lst, SList<Character> target) {
        SList<Character> original = lst.copy(),
                result   = lst.reverseCopy();
        System.out.print("Starting with lst = "+ original);
        System.out.print(", lst.reverseCopy() returns " + result +" and lst becomes " +lst);
        if (!target.equals(result) || !original.equals(lst))
            System.out.println("\n  **WRONG!** \tIt should return "+target + " and lst should be " + original);
        else
            System.out.println("  Check!");
    }

    ////////////////////////////////////////////////////////////////////
    public static void revTest(SList<Character> lst, SList<Character> target) {
        SList<Character> original = lst.copy();
        lst.reverse();
        System.out.print("Starting with lst = "+ original);
        System.out.print(", after lst.reverse() lst becomes " +lst);
        if (!target.equals(lst) )
            System.out.println("\n  **WRONG!** \tlst should be "+target);
        else
            System.out.println("  Check!");
    }

    ////////////////////////////////////////////////////////////////////
    public static void main(String... argv) {
        System.out.println("*** Preliminary messing about");

        SList<Character> sl0 = new SList<Character>();
        SList<Character> sl1 = make("abcde");
        SList<Character> sl2 = make("fghij");
        SList<Character> copy1, target1;

        System.out.println("sl1 = " + sl1);
        System.out.println("sl2 = " + sl2);

        Iterator<Character> it = sl1.iterator();
        String sum = "";
        while (it.hasNext()) {
            char val = it.next();
            sum = sum + val;
            System.out.print(val + "  ");
        }
        System.out.println("concatenate to " + sum);

        /////////////////////////////////////////////////////////////////
        System.out.println("\n*** Testing addAll \n");

        addAllTest(make("abcde"),make("fghij"),make("abcdefghij"));
        addAllTest(make(""),make("fghij"),make("fghij"));
        addAllTest(make("abcde"),make(""),make("abcde"));


        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for contains\n");

        for(char ch='a'; ch<='e'; ch++)
            System.out.println(" Checking " + ch + "\t"
                    + ((sl1.contains(ch))?"Check!":"**WRONG!**"));
        for(char ch='f'; ch<='z'; ch++)
            System.out.println(" Checking " + ch + "\t"
                    + ((!sl1.contains(ch))?"Check!":"**WRONG!**"));

        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for locationsOf\n");

        copy1 = sl1.copy();
        copy1.addAll(sl1);

        locTest(sl0,'z',new SList<Integer>());
        locTest(sl1,'z',new SList<Integer>());
        locTest(sl1,'a',new SList<Integer>(0));
        locTest(sl1,'b',new SList<Integer>(1));
        locTest(sl1,'c',new SList<Integer>(2));
        locTest(sl1,'d',new SList<Integer>(3));
        locTest(sl1,'e',new SList<Integer>(4));
        locTest(copy1,'z',new SList<Integer>());
        locTest(copy1,'a',new SList<Integer>(0,5));
        locTest(copy1,'b',new SList<Integer>(1,6));
        locTest(copy1,'c',new SList<Integer>(2,7));
        locTest(copy1,'d',new SList<Integer>(3,8));
        locTest(copy1,'e',new SList<Integer>(4,9));

        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for drop\n");
        sl1 = make("abcde");
        dropTest(sl1,0,make("abcde"));
        dropTest(sl1,1,make("bcde"));
        dropTest(sl1,3,make("e"));
        dropTest(sl1,27,make(""));
        dropTest(sl1,27,make(""));

        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for splitAt\n");
        sl1 = make("abcde");
        splitTest(sl1,0,make(""),make("abcde"));
        splitTest(sl1,1,make("a"),make("bcde"));
        splitTest(sl1,3,make("bcd"),make("e"));
        splitTest(sl1,27,make("e"),make(""));

        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for reverseCopy\n");
        sl1 = make("");
        revCopyTest(sl1,make(""));
        sl1 = make("X");
        revCopyTest(sl1,make("X"));
        sl1 = make("AZ");
        revCopyTest(sl1,make("ZA"));
        sl1 = make("dunderpate");
        revCopyTest(sl1,make("etaprednud"));

        /////////////////////////////////////////////////////////////////
        System.out.println("\n\n*** Testing for reverse\n");
        sl1 = make("");
        revTest(sl1,make(""));
        sl1 = make("X");
        revTest(sl1,make("X"));
        sl1 = make("AZ");
        revTest(sl1,make("ZA"));
        sl1 = make("dunderpate");
        revTest(sl1,make("etaprednud"));
        /////////////////////////////////////////////////////////////////
    }

}