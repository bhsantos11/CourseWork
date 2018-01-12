import sun.misc.Queue;

public class BinarySearchTree
{
    class Node
    {
        int data;
        Node left;
        Node right;
        public Node(int data)
        {
            this.data = data;
            left = null;
            right = null;
        }
    }
    public static Node root;
    public BinarySearchTree()
    {
        this.root = null;
    }

    public void insert(int id){
        Node newNode = new Node(id);
        if(root==null){
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while(true){
            parent = current;
            if(id<current.data){
                current = current.left;
                if(current==null){
                    parent.left = newNode;
                    return;
                }
            }else{
                current = current.right;
                if(current==null){
                    parent.right = newNode;
                    return;
                }
            }
        }
    }
    public void displayInOrder(Node root)
    {
        if(root!=null)
        {
            displayInOrder(root.left);
            System.out.print(" " + root.data);
            displayInOrder(root.right);
        }
    }

    public int size(Node root){ // Tree size function for testing
        if(root == null) return 0;
        return 1 + size(root.left) + size(root.right); // Will add 1 to return for each node/run not returned 0
    }

    public static void levelOrder(Node root){
        try {
            Queue<Node> queue = new Queue();
            String ans = "";
            Node u = root;
            Node p;
            int depth = 0;

            queue.enqueue(u);
            while (!queue.isEmpty()){
                p = queue.dequeue();
                ans += p.data + ", ";
                if (p.left != null) queue.enqueue(p.left);
                if (p.right != null) queue.enqueue(p.right);
            }
            System.out.println("Level order " + ans);

        }
        catch (java.lang.InterruptedException e){ // My IDE was complaining about it

        }
    }
    public static void main(String arg[]){
        BinarySearchTree b = new BinarySearchTree();
        b.insert(3);b.insert(8);
        b.insert(1);b.insert(4);
        b.insert(6);b.insert(2);
        b.insert(10);b.insert(9);
        b.insert(20);b.insert(25);
        b.insert(15);b.insert(16);
        System.out.println("Original Tree in Inorder : ");
        b.displayInOrder(b.root);
        System.out.println("");

        BinarySearchTree a = new BinarySearchTree(); // Tree given in lab
        a.insert(9);a.insert(7);
        a.insert(22);a.insert(3);
        a.insert(4);a.insert(14);
        a.insert(23);
        System.out.println("Original Tree in Inorder : ");
        a.levelOrder(root);
        System.out.println("Size of tree " + a.size(root));
        System.out.println("");


        BinarySearchTree c = new BinarySearchTree();
        c.insert(5);c.insert(7);
        c.insert(8);
        System.out.println("Original Tree in Inorder : ");
        c.levelOrder(root);
        System.out.println("Size of tree " + c.size(root));
        System.out.println("");

        BinarySearchTree d = new BinarySearchTree();
        d.insert(20);d.insert(15);
        d.insert(25);d.insert(22);
        d.insert(28);d.insert(10);
        d.insert(18);
        System.out.println("Original Tree in Inorder : ");
        d.levelOrder(root);
        System.out.println("Size of tree " + d.size(root));


    }
}
