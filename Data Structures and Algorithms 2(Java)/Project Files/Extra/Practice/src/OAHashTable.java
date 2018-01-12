
public class OAHashTable { // open addressing Hash table
    //OA Table Variables
    String[] t; // the table
    int n; // the size of table
    int d; // t.length = 2^d
    int q; // number of non-null entries in t

    //Hash Function
    private static final int alpha = 26; // Using ADM string hashing

    public static int hash(String x){
        int ans = 0;
        int charVal;
        int length = x.length();
        for(int i = 0; i <= length -1; i++){
            charVal = charVal(x.charAt(i));
            ans += Math.pow(alpha,length - (i+1)) * charVal;
        }
        return ans;
    }
    public String find(String x){
        int i = hash(x);
        return x;
    }

    private static int charVal(char c){
        if (c == 'H') return 8;
        if (c == 'I') return 9;
        return -1;
    }

    public static void main(String[] args){
        String hi = "HIIHHHI";
        System.out.println(hash(hi));


    }
}
