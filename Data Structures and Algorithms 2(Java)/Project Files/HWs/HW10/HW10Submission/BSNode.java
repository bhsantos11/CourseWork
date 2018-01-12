public class BSNode<T extends Comparable> {
    // instance variables
    T         data;
    BSNode<T> left, right, parent;
    // Constructors
    protected BSNode(T dat, BSNode<T> lft, BSNode<T> rght, BSNode<T> par) {
        data = dat;  left = lft;  right = rght;  parent = par;
    }
    protected BSNode(T dat) { this(dat,null,null,null); }

    // DISPLAY UTILITIES
    private static void spaces(int s) {
        for (int i=0; i<s; i++) System.out.print("   ");
    }
    public void show(int d) {
        if (right==null) { spaces(d+1); System.out.println("--"); } else  right.show(d+1);
        spaces(d); System.out.println(data);
        if (left==null)  { spaces(d+1); System.out.println("--"); } else  left.show(d+1);
    }
}