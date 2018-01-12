/**
 * Implements a top-down splay tree for Integers
 *
 * Baseed on code by Danny Sleator, available at http://www.link.cs.cmu.edu/splay/
 * This code is in the public domain.
 *
 * @author Bernardo Bicalho Hauer Santos
 *
 */
public class IntSplayTree
{
    private BinaryNode root;

    public IntSplayTree() {
        root = null;
    }

    /**
     * Split the tree at a key. 
     * @param key the item on which to split.
     * @return a pair of nodes which are the roots of two trees.
     *
     *  If x is not in the tree, then split at the
     *  smallest greater key, or greatest smaller key, whichever was the last
     *  one examined in the binary search.
     *
     * DONE:  HOMEWORK PROBLEM
     */
    public BinaryNode[] split(Integer key) {
        BinaryNode[] ans = {null, null};
        // From docs "If x is not in the tree, then split at the smallest greater key,
        // or greatest smaller key, whichever was the last one examined in the binary search."
        // From the last line "whichever was the last one examined in the binary search."
        //  I am interpreting that this means the node as in splayTrees the "last one examined" is brought to the root

        BinaryNode Finder = root;
        while(Finder.right != null) Finder = Finder.right;
        int max = Finder.key;
        Finder = root;
        while(Finder.left != null) Finder = Finder.left;
        int min = Finder.key;

        if (find(key) == null){ // If key is not in tree
            if(key > max){ // If key is bigger than the biggest value in the tree
//                System.out.println("Max & root " + max + " " + root.key );
                ans[0] = root; //If key is bigger than all elements then all elements will go in its left subtree
            }
            else if(key < min){ // If key is smaller than any value in the tree
//                System.out.println("Min & root " + min + " " + root.key );
                ans[1] = root; //All values are bigger so it must go in the right subtree
            }
            else { // In case key is not in tree and value is between two values

                // While we havent found where it goes between
                try {
                    if(root.left != null && root.right != null) {
                        while (!((key > root.left.key) && (key < root.right.key))) { //Key must be smaller than the right, and bigger than the left to find pos
                            if (key < root.left.key)
                                splay(root.left.key); // If the key is smaller than the left, we go left to find a smaller value
                            else
                                splay(root.right.key);

                        }// end of while loop
                    }// end of if


                // u would be the parent of key
                if(key > root.key) { // Key would be right child, u is smaller than key and it should go on the left subtree
                    ans = split(root.key);
                }
                else{ //key would be left child, u is bigger than key and should go on right tree
                    // If u should go on the right tree we can just apply split to the value to its left
                    // Because then it would be
                    if(root.left != null)ans = split(root.left.key); // If its null then the left tree will be empty so we can ignore it
                    else split(root.key);
                }
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                    System.out.println("NullPointerException from while when min < key < max");
                    System.out.println("Key is " + key);
                    System.out.println("u val is " + root.key);
                    System.out.println("Subtree rooted at u is");
                    BTreePrinter.printNode(root);
                    throw e;
                }
            }// end of case where key is between two vals
            return ans;
        }// end of case where key is not in tree


        splay(key); //Key is now the root of the tree and therefore all values in the right subtree
        // of root are larger than the key and all values in the left subtree of root are smaller

        ans[1] = root.right; // second item of array is now subtree with vals > key
        root.right = null; // right subtree removed

        ans[0] = root; // First item in array is now subtree with vals < x and x
        return ans;
    }

    /**
     * Insert into the tree.
     * @param key the item to insert.
     *
     * FIX ME:  HOMEWORK PROBLEM
     */
    public void insert(Integer key) {
        // IN PROGRESS --- modifying to use split(), as per the alternative method for insert at
        //            https://en.wikipedia.org/wiki/Splay_tree
        BinaryNode newNode = new BinaryNode(key);
        if(root == null) { // In case current tree is empty
            root = newNode;
            return;
        }
        splay(key);
        if(key.compareTo(root.key) == 0) return;

        BinaryNode[] treeArr = split(key);
//        if((treeArr[0] == null) || (treeArr[1] == null)) System.out.println("False array item at key " + key );
        newNode.left = treeArr[0];
        newNode.right = treeArr[1];
        this.root = newNode;
        if(find(key) == null){
            System.out.println("Key not successfully added");
            System.out.println("key = " + key);
        }
//        else {
//            System.out.println("Key " + key + " successfully added");
//
//        }

    }

    public void defaultInsert(Integer key){

        BinaryNode n;
        int c;
        if (root == null) {
            root = new BinaryNode(key);
            return;
        }
        splay(key);
        if ((c = key.compareTo(root.key)) == 0) {
            return;
        }
        n = new BinaryNode(key);
        if (c < 0) {
            n.left = root.left;
            n.right = root;
            root.left = null;
        } else {
            n.right = root.right;
            n.left = root;
            root.right = null;
        }
        root = n;
    }


    /**
     * Joins two BST, t1 and t2
     * @param t1 a BST 
     * @param t2 a BST.  We are given that every element in t1 is less than every element in t2
     * @return a single node 
     *
     * Note that this does not update root in our IntSplayTree.  The caller will have to do that if
     * needed/desired.
     *
     * DONE:  HOMEWORK PROBLEM
     */

    public BinaryNode join(BinaryNode t1, BinaryNode t2) {// t1 values < t2 value

        if (t1 == null) return t2;
        if (t2 == null) return t1;

        IntSplayTree finalSplayTree = new IntSplayTree();
        finalSplayTree.root = t1;
        int max = finalSplayTree.findMax();
        //finalSplayTree is now t1 with max value at root and a null value at root.right as we just put the max value as root
        finalSplayTree.root.right = t2;

        return finalSplayTree.root;
    }

    /**
     * Remove from the tree.
     * @param key the item to remove.
     *
     * DONE:  HOMEWORK PROBLEM
     */
    public void remove(Integer key) {
        // DONE --- modified to use join() as per the alternative method for deletion at
        //            https://en.wikipedia.org/wiki/Splay_tree
        if(find(key) == null) {System.out.println("Key "+ key + " not present"); return;}

        BinaryNode[] treeArr = split(key);
        //Key is now at treeArr[0].root subtree and has no right child
        root = join(treeArr[0].left, treeArr[1]);
        if(find(key) != null) {
            System.out.println(key + " not removed");
        }
// Original Method
//        BinaryNode x;
//        splay(key);
//        if (key.compareTo(root.key) != 0) {
//            return;
//        }
//        // Now delete the root
//        if (root.left == null) {
//            root = root.right;
//        } else {
//            x = root.right;
//            root = root.left;
//            splay(key);
//            root.right = x;
//        }



    }// end of remove method

    /**
     * Find the smallest item in the tree.
     * @return the smallest key in the tree, null if the tree is empty
     */
    public Integer findMin() {
        BinaryNode x = root;
        if(root == null) return null;
        while(x.left != null) x = x.left;
        splay(x.key);
        return x.key;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest key in the tree, null if the tree is empty
     */
    public Integer findMax() {
        BinaryNode x = root;
        if(root == null) return null;
        while(x.right != null) x = x.right;
        splay(x.key);
        return x.key;
    }

    /**
     * Find an item in the tree, and splay it to the top.
     * @param key the key to find
     * @return the key, if found; null, if the key is not in the tree
     */
    public Integer find(Integer key) {
        if (root == null) return null;
        splay(key);
        if(root.key.compareTo(key) != 0) return null;
        return root.key;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    ///** this method just illustrates the top-down method of
    // * implementing the move-to-root operation 
    // */
    //    private void moveToRoot(Comparable key) {
    //        BinaryNode l, r, t, y;
    //        l = r = header;
    //        t = root;
    //        header.left = header.right = null;
    //        for (;;) {
    //            if (key.compareTo(t.key) < 0) {
    //                if (t.left == null) break;
    //                r.left = t;                                 /* link right */
    //                r = t;
    //                t = t.left;
    //            } else if (key.compareTo(t.key) > 0) {
    //                if (t.right == null) break;
    //                l.right = t;                                /* link left */
    //                l = t;
    //                t = t.right;
    //            } else {
    //                break;
    //            }
    //        }
    //        l.right = t.left;                                   /* assemble */
    //        r.left = t.right;
    //        t.left = header.right;
    //        t.right = header.left;
    //        root = t;
    //    }

    private BinaryNode header = new BinaryNode(null); // For splay

    /**
     * Internal method to perform a top-down splay.  
     * @param key the key to splay
     *
     *   splay(key) does the splay operation on the given key.
     *   If key is in the tree, then the BinaryNode containing
     *   that key becomes the root.  If key is not in the tree,
     *   then after the splay, root.key is either the greatest key
     *   < key in the tree, or the least key > key in the tree.
     *
     *   This means, among other things, that if you splay with
     *   a key that's larger than any in the tree, the rightmost
     *   node of the tree becomes the root.  This property is used
     *   in the remove() method.
     */

    private void splay(Integer key) {
        BinaryNode l, r, t, y;
        l = r = header;
        t = root;
        header.left = header.right = null;
        for (;;) {
            if (key.compareTo(t.key) < 0) {
                if (t.left == null) break;
                if (key.compareTo(t.left.key) < 0) {
                    y = t.left;                            /* rotate right */
                    t.left = y.right;
                    y.right = t;
                    t = y;
                    if (t.left == null) break;
                }
                r.left = t;                                 /* link right */
                r = t;
                t = t.left;
            } else if (key.compareTo(t.key) > 0) {
                if (t.right == null) break;
                if (key.compareTo(t.right.key) > 0) {
                    y = t.right;                            /* rotate left */
                    t.right = y.left;
                    y.left = t;
                    t = y;
                    if (t.right == null) break;
                }
                l.right = t;                                /* link left */
                l = t;
                t = t.right;
            } else {
                break;
            }
        }
        l.right = t.left;                                   /* assemble */
        r.left = t.right;
        t.left = header.right;
        t.right = header.left;
        root = t;
    }

    // test code stolen from Weiss
    public static void main(String [ ] args)
    {
        // Comparing my method of insert vs default one
        IntSplayTree m = new IntSplayTree();
        IntSplayTree d = new IntSplayTree();
        int y = 0;
        m.insert(1);
        d.defaultInsert(1);
        for(int x = 0; x < 5; x++){
            y = (int) (Math.random() * 10);
            if(x == 0) System.out.println("First to be added " + y);
            m.insert(y);
            d.defaultInsert(y);
        }
        System.out.println("Using my add");
        BTreePrinter.printNode(m.root);
        System.out.println("Using original add");
        BTreePrinter.printNode(d.root);
        System.out.println("Last to be inserted " + y);

        System.out.println("Splits");
        BinaryNode[] mine = m.split(3);
        BinaryNode[] notMine = d.split(3);

        System.out.println("Using my add");
        BTreePrinter.printNode(mine[0]);
        System.out.println("Larger");
        BTreePrinter.printNode(mine[1]);

        System.out.println("Using original add");
        BTreePrinter.printNode(notMine[0]);
        System.out.println("Larger");
        BTreePrinter.printNode(notMine[1]);








        IntSplayTree t = new IntSplayTree();
        IntSplayTree ti = new IntSplayTree();




        final int NUMS = 4000;
        final int GAP  =   307;

        System.out.println("Checking... (no bad output means success)");


        for(int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insert(new Integer(i));

        System.out.println("Inserts complete");


        for(int i = 1; i < NUMS; i+= 2)
            t.remove(new Integer(i));
        System.out.println("Removes complete");

        if(((Integer)(t.findMin())).intValue() != 2 ||
                ((Integer)(t.findMax())).intValue() != NUMS - 2)
            System.out.println("FindMin or FindMax error!");

        int ii = 0;
        try {

            for (int i = 2; i < NUMS; i += 2) {
                ii = i;
                if (((Integer) t.find(new Integer(i))).intValue() != i)
                    System.out.println("Error: find fails for " + i);
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("NPE at find() test");
            System.out.println("i = " + ii);
            System.out.println("t.root " + t.root.key);
            System.out.println(t.find(ii));
            return;
        }

        for(int i = 1; i < NUMS; i+=2)
            if(t.find(new Integer(i))  != null)
                System.out.println("Error: Found deleted item " + i);

        //  TESTS FOR JOIN
        //Please see BTreePrinter for an important academic integrity note
        System.out.println("TESTING JOIN \n");

        IntSplayTree s = new IntSplayTree();
        IntSplayTree l = new IntSplayTree();
        for(int x = 9; x > 0; x--){
            s.insert((int) (Math.random()  * 10));
            l.insert((int) (Math.random()  * 10) + 10);
        }
        // s is now populated with a random amount of values, n, where n <= 6, and all values are < 10
        // l is now populated with a random amount of values, n, where n <= 6, and all values are bigger 10 but less
        // then 20


        System.out.println("Small tree");
        BTreePrinter.printNode(s.root);
        System.out.println("Large tree");
        BTreePrinter.printNode(l.root);

        //Here we are doing s.join but it does not matter, we could even do a completely separate tree that is neither s or l
        //  this is because the join function does not alter the IntSplayTree instance we call it from
        BinaryNode joinedRoot = s.join(s.root, l.root);
        System.out.println("Joined small and large trees");
        BTreePrinter.printNode(joinedRoot);

        //TESTS FOR SPLIT
        System.out.println("TESTING SPLIT \n");

        //Initializations and executions
        IntSplayTree joined = new IntSplayTree();
        joined.root = joinedRoot;
        BinaryNode[] joinedSplitArr = joined.split(joinedRoot.key);// This should give two evenly split trees

        //Tree visualizations
        System.out.println("Split test at joined tree root");
        System.out.println("Key = " + joinedRoot.key);
        System.out.println("Tree with smaller values and key");
        BTreePrinter.printNode(joinedSplitArr[0]);
        System.out.println("Tree with larger values");
        BTreePrinter.printNode(joinedSplitArr[1]);

        // Joining the tree again does will both test if join is working and enable to test split again with the same tree
        joinedRoot = s.join(joinedSplitArr[0], joinedSplitArr[1]);
        System.out.println("Split tree has been rejoined");
        BTreePrinter.printNode(joinedRoot);

        //Splitting again but now in the left tree at depth 2
        System.out.println("Split test at joined-split-rejoined tree root");
        System.out.println("Splitting again but now in the left tree at depth 2");
        System.out.println("Key = " + joinedRoot.left.left.key);
        joinedSplitArr = joined.split(joinedRoot.left.left.key);// This should give two evenly split trees
        //Tree visualizations

        System.out.println("Tree with smaller values and key");
        BTreePrinter.printNode(joinedSplitArr[0]);
        System.out.println("Tree with larger values");
        BTreePrinter.printNode(joinedSplitArr[1]);




    }

}