public class Node {
    public char letter;
    public Node next;

    public Node() {
        this.letter = '@';
        this.next = null;
    }

    public Node(char c) {
        this.letter = c;
        this.next = null;
    }

    public Node(char c, Node n){
        this.letter = c;
        this.next = n;
    }

    public void addAfter(char c) {
        this.next = new Node(c,this.next);
    }

    public void deleteAfter() {
        if (this.next!=null)
            this.next = this.next.next;
    }
}
