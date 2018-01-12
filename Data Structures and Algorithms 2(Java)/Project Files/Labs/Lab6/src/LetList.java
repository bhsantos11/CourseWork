import java.io.*;

public class LetList {

    ///////////////////////////////////////////////////////////////////
    // INSTANCE VARIABLES
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    // The only instance variable is front, a reference to the first
    // node in the linked list.

    public Node front;

    ///////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////

    public LetList()       { this.front = null; }
    public LetList(Node n) { this.front = n;    }

    ///////////////////////////////////////////////////////////////////
    // INSTANCE METHODS
    ///////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    // This method adds a new node with c as letter to the front of
    // this list.

    public void push(char c) { this.front = new Node(c,this.front); }

    ///////////////////////////////////////////////////////////////////
    // This method returns a list made up of all of the current list
    // EXCEPT the first element. If the list is empty, then an empty
    // list is returned.  (Lispers and Schemers: Think cdr.)

    public LetList rest() {
        if (this.front==null) return new LetList();
        else                  return new LetList(this.front.next);
    }
    ///////////////////////////////////////////////////////////////////
    // This method finds the first element of the list containing the
    // char c and returns a list made up of that node and all the
    // nodes that follow it in the current list.  If c does not occur
    // in the list, then the method returns an empty list.

    public LetList find(char c) {
        Node cursor = this.front;
        while ( (cursor != null) && (cursor.letter!=c) )
            cursor = cursor.next;
        return new LetList(cursor);
    }

    ///////////////////////////////////////////////////////////////////
    // This method changes all occurences of oldChar in the list to
    // occurences of newChar.

    public void changeAll(char oldChar, char newChar) {
        for (Node cursor = this.front; cursor != null; cursor = cursor.next)
            if (cursor.letter == oldChar) cursor.letter = newChar;
    }

    ///////////////////////////////////////////////////////////////////
    // This method constructs a String representation of the LetList.

    public String toString() {
        String ans = "-";
        for (Node cursor = this.front; cursor != null; cursor = cursor.next)
            ans = ans + cursor.letter + "-";
        return ans;
    }
}
