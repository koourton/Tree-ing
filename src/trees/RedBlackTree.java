package trees;

public class RedBlackTree extends BST {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    public static class RedBlackNode extends Node {
        public boolean color;

        public RedBlackNode(int key) {
            super(key);
            color = RED;
        }
    }

    @Override
    protected Node insertRec(Node root, int key) {
        // Create RedBlackNode instead of regular Node
        if (root == null) {
            return new RedBlackNode(key);
        }

        // Basic BST insertion
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        } else {
            return root; // Duplicate keys not allowed
        }

        // Cast to RedBlackNode for color operations
        RedBlackNode rbNode = (RedBlackNode) root;

        // Apply Red-Black Tree balancing rules
        if (isRed(rbNode.right) && !isRed(rbNode.left))
            rbNode = rotateLeft(rbNode);
        if (isRed(rbNode.left) && isRed(rbNode.left.left))
            rbNode = rotateRight(rbNode);
        if (isRed(rbNode.left) && isRed(rbNode.right))
            flipColors(rbNode);

        return rbNode;
    }

    @Override
    public void insert(int key) {
        super.insert(key); // This will call our overridden insertRec
        if (getRoot() != null) {
            ((RedBlackNode) getRoot()).color = BLACK; // Root must be black
        }
    }

    @Override
    protected Node deleteRec(Node root, int key) {
        if (root == null) return null;

        RedBlackNode h = (RedBlackNode) root;

        if (key < h.key) {
            if (!isRed(h.left) && h.left != null && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = deleteRec(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);

            if (key == h.key && (h.right == null))
                return null;

            if (!isRed(h.right) && h.right != null && !isRed(h.right.left))
                h = moveRedRight(h);

            if (key == h.key) {
                RedBlackNode smallest = min((RedBlackNode) h.right);
                h.key = smallest.key;
                h.right = deleteMin((RedBlackNode) h.right);
            } else {
                h.right = deleteRec(h.right, key);
            }
        }

        return balance(h);
    }

    @Override
    public void deleteKey(int key) {
        if (getRoot() == null) return;

        // If both children of root are black, set root to red
        if (!isRed(getRoot().left) && !isRed(getRoot().right))
            ((RedBlackNode) getRoot()).color = RED;

        super.deleteKey(key); // This will call our overridden deleteRec

        // Ensure root is black if tree is not empty
        if (getRoot() != null)
            ((RedBlackNode) getRoot()).color = BLACK;
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return ((RedBlackNode) node).color == RED;
    }

    private RedBlackNode rotateLeft(RedBlackNode h) {
        RedBlackNode x = (RedBlackNode) h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private RedBlackNode rotateRight(RedBlackNode h) {
        RedBlackNode x = (RedBlackNode) h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(RedBlackNode h) {
        h.color = !h.color;
        if (h.left != null) ((RedBlackNode) h.left).color = !((RedBlackNode) h.left).color;
        if (h.right != null) ((RedBlackNode) h.right).color = !((RedBlackNode) h.right).color;
    }

    private RedBlackNode moveRedLeft(RedBlackNode h) {
        flipColors(h);
        if (h.right != null && isRed(h.right.left)) {
            h.right = rotateRight((RedBlackNode) h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private RedBlackNode moveRedRight(RedBlackNode h) {
        flipColors(h);
        if (h.left != null && isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private RedBlackNode min(RedBlackNode h) {
        if (h.left == null) return h;
        return min((RedBlackNode) h.left);
    }

    private RedBlackNode deleteMin(RedBlackNode h) {
        if (h.left == null) return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin((RedBlackNode) h.left);

        return balance(h);
    }

    private RedBlackNode balance(RedBlackNode h) {
        if (isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        return h;
    }
}