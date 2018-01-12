/** A tester for Lab 5.
 * @author Bernardo Santos
 */
public class Test5 {




    ////////////////////////////////////////////////////////////////////
    public static void splitTest(DLList<Character> lst,
                                 int k,
                                 DLList<Character> front,
                                 DLList<Character> back) {
        System.out.print("Starting with lst = "+lst);
        DLList<Character> result = lst.splitAt(k);
        System.out.print(", lst.splitAt("+k+") returns " + result +" and lst becomes " +lst);
        if (!front.equals(result) || !back.equals(lst))
            System.out.println("\n  **WRONG!**\tIt should return "+front + " and lst should be " + back);
        else
            System.out.println("  Check!");
        lst.linkCheck();
        result.linkCheck();
    }
    ////////////////////////////////////////////////////////////////////
    public static void revCopyTest(DLList<Character> lst, DLList<Character> target) {
        DLList<Character> original = lst.copy();
        DLList<Character> result   = lst.reverseCopy();
        System.out.print("Starting with lst = "+ original);
        System.out.print(", lst.reverseCopy() returns " + result +" and lst becomes " +lst);
        if (!target.equals(result) || !original.equals(lst))
            System.out.println("\n  **WRONG!** \tIt should return "+target + " and lst should be " + original);
        else
            System.out.println("  Check!");
        result.linkCheck();
    }

    ////////////////////////////////////////////////////////////////////
    public static void revTest(DLList<Character> lst, DLList<Character> target) {
        DLList<Character> original = lst.copy();
        lst.reverse();
        System.out.print("Starting with lst = "+ original);
        System.out.print(", after lst.reverse() lst becomes " +lst);
        if (!target.equals(lst) )
            System.out.println("\n  **WRONG!** \tlst should be "+target);
        else
            System.out.println("  Check!");
        lst.linkCheck();
    }

    ////////////////////////////////////////////////////////////////////
    public static void mergeTest(DLList<Integer> lst1,
                                 DLList<Integer> lst2,
                                 DLList<Integer> target) {

        System.out.print("Starting with lst1 = "+ lst1 + " and lst2 = " + lst2);
        DLList<Integer> result = DLList.merge(lst1,lst2);

        System.out.print(" merge(lst1,lst2) returns " + result);
        if (!target.equals(result) )
            System.out.println("\n  **WRONG!** \tthe merge should be "+target);
        else if (lst1.size()!=0 && lst2.size()!=0)
            System.out.println("\n  **WRONG!** Both lst1 and lst2 should be empty and "
                    +  " lst1 = " + lst1 + " and lst2 = " + lst2);
        else
            System.out.println("  Check!");
        result.linkCheck();
    }
    ////////////////////////////////////////////////////////////////////
    public static void mergeSortTest(DLList<Integer> lst,
                                     DLList<Integer> target) {

        System.out.print("Starting with lst = "+ lst);
        DLList<Integer> result = DLList.mergesort(lst);

        System.out.print(" mergesort(lst) returns " + result);
        if (!target.equals(result) )
            System.out.println("\n  **WRONG!** \tthe result should be "+target);
        else
            System.out.println("  Check!");
        result.linkCheck();
    }


    ////////////////////////////////////////////////////////////////////
    public static DLList<Character> make(String str) {
        DLList<Character> ans = new DLList<Character>();
        for(int i=0; i<str.length(); i++)
            ans.add(str.charAt(i));
        return ans;
    }
    ////////////////////////////////////////////////////////////////////
    public static DLList<Integer> make(int... elems) {
        DLList<Integer> ans = new DLList<Integer>();
        for(int x : elems) ans.add(x);
        return ans;
    }
    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    public static void main(String... argv) {
        DLList<Character> lst1 = new DLList<Character>();
        DLList<Character> lst2 = new DLList<Character>();
        DLList<Integer> il1 = new DLList<Integer>();
        DLList<Integer> il2 = new DLList<Integer>();
        DLList<Integer> il3 = new DLList<Integer>();

        /////////////////////////////////////////////////////////////////

        System.out.println("\n\n*** Testing for splitAt\n");
        DLList<Character> dl1 = make("abcde");
        splitTest(dl1,0,make(""),make("abcde"));
        splitTest(dl1,1,make("a"),make("bcde"));
        splitTest(dl1,3,make("bcd"),make("e"));
        splitTest(dl1,27,make("e"),make(""));
        // ADD THREE NONTRIVIAL TESTS
        DLList<Character> dl2 = make("12345678");
        splitTest(dl1,-1,make(""),make(""));
        splitTest(dl2,2,make("12"),make("345678"));
        splitTest(dl2,20,make("345678"),make(""));

        /////////////////////////////////////////////////////////////////

        System.out.println("\n\n*** Testing for reverseCopy\n");
        dl1 = make("");
        revCopyTest(dl1,make(""));
        dl1 = make("X");
        revCopyTest(dl1,make("X"));
        dl1 = make("AZ");
        revCopyTest(dl1,make("ZA"));
        dl1 = make("dunderpate");
        revCopyTest(dl1,make("etaprednud"));
        // ADD THREE NONTRIVIAL TESTS
        dl1 = make("11111");
        revCopyTest(dl1,make("11111"));
        dl1 = make("-1-1");
        revCopyTest(dl1,make("1-1-"));
        dl1 = make("@");
        revCopyTest(dl1,make("@"));


        /////////////////////////////////////////////////////////////////

        System.out.println("\n\n*** Testing for reverse\n");
        dl1 = make("");
        revTest(dl1,make(""));
        dl1 = make("X");
        revTest(dl1,make("X"));
        dl1 = make("AZ");
        revTest(dl1,make("ZA"));
        dl1 = make("dunderpate");
        revTest(dl1,make("etaprednud"));
        // ADD THREE NONTRIVIAL TESTS, I used the same tests as before because the logic application is the same
        dl1 = make("11111");
        revCopyTest(dl1,make("11111"));
        dl1 = make("-1-1");
        revCopyTest(dl1,make("1-1-"));
        dl1 = make("@");
        revCopyTest(dl1,make("@"));

        /////////////////////////////////////////////////////////////////

        System.out.println("\n\n*** Testing for merge\n");
        mergeTest(il1,il2,il3);
        il1 = make(2,4,8); il2 = make(); il3 = make(2,4,8);
        mergeTest(il1,il2,il3);
        il1 = make(); il2 = make(2,4,8); il3 = make(2,4,8);
        mergeTest(il1,il2,il3);
        il1 = make(2,4,8); il2 = make(1,9); il3 = make(1,2,4,8,9);
        mergeTest(il1,il2,il3);
        il1 = make(2,4,8); il2 = make(1,3,6,9); il3 = make(1,2,3,4,6,8,9);
        mergeTest(il1,il2,il3);
        // ADD THREE NONTRIVIAL TESTS
        il1 = make(); il2 = make(); il3 = make();
        mergeTest(il1,il2,il3);
        il1 = make(4); il2 = make(); il3 = make(4);
        mergeTest(il1,il2,il3);
        il1 = make(0); il2 = make(1); il3 = make(0,1);
        mergeTest(il1,il2,il3);

        /////////////////////////////////////////////////////////////////

        System.out.println("\n\n*** Testing for mergesort\n");
        il1 = make(); il2 = make();
        mergeSortTest(il1,il2);
        il1 = make(2); il2 = make(2);
        mergeSortTest(il1,il2);
        il1 = make(1,2,3,4,5,6,7); il2 = make(1,2,3,4,5,6,7);
        mergeSortTest(il1,il2);
        il1 = make(7,6,5,4,3,2,1);
        mergeSortTest(il1,il2);
        il1 = make(4,1,7,6,5,3,2);
        mergeSortTest(il1,il2);
        // ADD THREE NONTRIVIAL TESTS
        il1 = make(15,16,17,18); il2 = make(15,16,17,18);
        mergeSortTest(il1,il2);
        il1 = make(17,18,16,15);
        mergeSortTest(il1,il2);
        il2 = make(9999);
        il1 = make(9999);
        mergeSortTest(il1,il2);
    }
}