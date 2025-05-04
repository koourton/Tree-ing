package trees;

public class AVLTree extends BST {
    private static class AVLNode extends Node {
        int height;

        AVLNode(int key) {
            super(key);
            height = 1;
        }
    }

    @Override
    protected Node insertRec(Node root, int key) {
        if (root == null) {
            return new AVLNode(key);
        }

        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        } else {
            return root;
        }

        AVLNode avlNode = (AVLNode) root;
        avlNode.height = 1 + Math.max(height(avlNode.left), height(avlNode.right));

        return balance(avlNode);
    }

    @Override
    protected Node deleteRec(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            if (root.left == null || root.right == null) {
                root = (root.left != null) ? root.left : root.right;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = deleteRec(root.right, temp.key);
            }
        }

        if (root == null) {
            return root;
        }

        AVLNode avlNode = (AVLNode) root;
        avlNode.height = 1 + Math.max(height(avlNode.left), height(avlNode.right));

        return balance(avlNode);
    }

    private int height(Node node) {
        return node == null ? 0 : ((AVLNode) node).height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node balance(AVLNode node) {
        int balance = getBalance(node);

        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        ((AVLNode) y).height = Math.max(height(y.left), height(y.right)) + 1;
        ((AVLNode) x).height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        ((AVLNode) x).height = Math.max(height(x.left), height(x.right)) + 1;
        ((AVLNode) y).height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}