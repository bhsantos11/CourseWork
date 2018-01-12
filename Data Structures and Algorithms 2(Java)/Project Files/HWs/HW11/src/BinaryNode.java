public class BinaryNode
{
    BinaryNode(Integer theKey) {
        key = theKey;
        left = right = null;
    }

    Integer key;          // The data in the node
    BinaryNode left;         // Left child
    BinaryNode right;        // Right child
}