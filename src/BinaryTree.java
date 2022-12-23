import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {
    TreeNode root;
    List<TreeList> nodes;
    // Used to store nodes in the range for option E
    List<String> rangeNodes;
    // For AVL
    void AVLUpdateHeight(TreeNode node) {
        // the height is 1 more than the highest subtree
        if (AVLHeight(node.left) > AVLHeight(node.right)) {
            node.height = 1 + AVLHeight(node.left);
        } else {
            node.height = 1 + AVLHeight(node.right);
        }
    }

    int AVLHeight(TreeNode node) {
        // if it is a leaf node
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    int AVLGetBalance(TreeNode node) {
        // if it is a leaf node
        if (node == null) {
            return 0;
        }
        return AVLHeight(node.right) - AVLHeight(node.left);
    }

    TreeNode AVLRotateRight(TreeNode node) {
        TreeNode left = node.left;
        TreeNode leftRight = left.right;
        // set the node as right child of the left child node
        left.right = node;
        node.left = leftRight;
        AVLUpdateHeight(node);
        AVLUpdateHeight(left);

        return left;
    }

    TreeNode AVLRotateLeft(TreeNode node) {
        TreeNode right = node.right;
        TreeNode rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;
        AVLUpdateHeight(node);
        AVLUpdateHeight(right);

        return right;
    }

    TreeNode AVLBalance(TreeNode node) {
        AVLUpdateHeight(node);
        int balance = AVLGetBalance(node);
        // Check if the node's balance is outside the range [-1...1]
        if (balance > 1) {
            // check right subtree
            if (AVLHeight(node.right.right) <= AVLHeight(node.right.left)) {
                // increase the height of the right subtree
                node.right = AVLRotateRight(node.right);
            }
            // balance the node
            node = AVLRotateLeft(node);
        } else if (balance < -1) {
            // check left subtree
            if (AVLHeight(node.left.left) <= AVLHeight(node.left.right)) {
                // increase the height of the left subtree
                node.left = AVLRotateLeft(node.left);
            }
            // balance the node
            node = AVLRotateRight(node);
        }
        return node;
    }

    public void addNode(long value, String name) {
        root = insertNode(root, value, name);
    }

    public void AVLAddNode(long value, String name) {
        root = AVLInsertNode(root, value, name);
    }


    private TreeNode insertNode(TreeNode current, long value, String name) {
        if (current == null) {
            return new TreeNode(name, value);
        }

        if (value < current.value) {
            current.left = insertNode(current.left, value, name);
        } else if (value > current.value) {
            current.right = insertNode(current.right, value, name);
        } else {
            // value already exists (should never happen)
            return current;
        }

        return current;
    }

    // returns the new root node
    public TreeNode deleteNode(TreeNode current, long value) {
        if (current == null) {
            return null;
        }
        if (value == current.value) {
            // the node is a leaf node
            if (current.left == null && current.right == null) {
                return null;
            }
            // the node has one child
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            // the node has two children
            TreeNode smallestValue = smallestValue(current.right);
            current.value = smallestValue.value;
            current.name = smallestValue.name;
            current.right = deleteNode(current.right, smallestValue.value);
            return current;
        }
        if (value < current.value) {
            current.left = deleteNode(current.left, value);
            return current;
        }
        current.right = deleteNode(current.right, value);
        return current;
    }

    private TreeNode AVLInsertNode(TreeNode current, long value, String name) {
        if (current == null) {
            return new TreeNode(name, value);
        }

        if (value < current.value) {
            current.left = AVLInsertNode(current.left, value, name);
        } else if (value > current.value) {
            current.right = AVLInsertNode(current.right, value, name);
        } else {
            // value already exists (should never happen)
            return current;
        }

        return AVLBalance(current);
    }

    // returns the new root node
    public TreeNode AVLDeleteNode(TreeNode current, long value) {
        if (current == null) {
            return null;
        }
        if (value < current.value) {
            current.left = AVLDeleteNode(current.left, value);
        } else if (value > current.value) {
            current.right = AVLDeleteNode(current.right, value);
        } else {
            // the node has one child or it is a leaf node
            if (current.right == null) {
                current = current.left;
            } else if (current.left == null) {
                current = current.right;
            } else {
                // the node has two children
                TreeNode smallestValue = smallestValue(current.right);
                current.value = smallestValue.value;
                current.name = smallestValue.name;
                current.right = AVLDeleteNode(current.right, smallestValue.value);
            }
        }

        if (current != null) {
            current = AVLBalance(current);
        }

        return current;
    }

    private TreeNode smallestValue(TreeNode root) {
        if (root.left == null) {
            return root;
        } else {
            return smallestValue(root.left);
        }
    }

    public void preorder(TreeNode node) {
        // We show first the root node, then the left subtree, then the right subtree
        if (node != null) {
            System.out.println(show(node));
            preorder(node.left);
            preorder(node.right);
        }
    }

    public void postorder(TreeNode node) {
        // We show first the left subtree, then the right subtree, then the root node
        if (node != null) {
            preorder(node.left);
            preorder(node.right);
            System.out.println(show(node));
        }
    }

    public void inorder(TreeNode node) {
        // We show first the left subtree, then the root node, then the right subtree.
        if (node != null) {
            preorder(node.left);
            System.out.println(show(node));
            preorder(node.right);
        }
    }

    public void levels() {
        Queue<TreeNode> nodes = new LinkedList<>();
        // Start from root node
        nodes.add(root);

        // Visit all levels of the tree from left to right (inorder)
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.remove();

            System.out.println(show(node));

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }
    }

    private String show(TreeNode node) {
        // Add a . every 3 digits
        StringBuilder sValue = new StringBuilder();
        String temp = Long.toString(node.value);
        int valueLength = temp.length();
        for (int i = 0; i < valueLength; i++) {
            if ((valueLength - i) % 3 == 0 && i != 0) {
                sValue.append(".");
            }
            sValue.append(temp.charAt(i));
        }

        return node.name + " - " + sValue.toString() + " doubloons";
    }

    private boolean containsNode(TreeNode current, long value) {
        // if we reach a leaf node
        if (current == null) {
            return false;
        }
        // if we find the node
        if (value == current.value) {
            System.out.println("\nA treasure with this value was found: " + current.name);
            return true;
        }

        // if we don't find the node and we are not at a leaf node
        if (value < current.value) {
            return containsNode(current.left, value);
        } else {
            return containsNode(current.right, value);
        }
    }

    public boolean isNode(long value) {
        // search for the node starting from the root node
        return containsNode(root, value);
    }

    // Returns the number of nodes within the range
    public int searchRange(TreeNode current, long low, long high) {
        // if we reach a leaf node
        if (current == null) {
            return 0;
        }

        // If current node is in range search for its children
        if (current.value >= low && current.value <= high) {
            rangeNodes.add(show(current));
            return 1 + searchRange(current.left, low, high) + searchRange(current.right, low, high);
        } else if (current.value > high) {
            // If current node is greater than high search left children
            return searchRange(current.left, low, high);
        } else {
            // If current node is smaller than low search right children
            return searchRange(current.right, low, high);
        }
    }

    public long findValueFrom(String name) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.equals(name)) {
                return nodes.remove(i).value;
            }
        }
        // Node not found
        return -1;
    }

}

class TreeNode {
    String name;
    long value;
    TreeNode right;
    TreeNode left;
    // For AVL
    int height;

    TreeNode(String name, long value) {
        this.name = name;
        this.value = value;
        right = null;
        left = null;
        height = 0;
    }
}

class TreeList {
    String name;
    long value;

    TreeList(String name, long value) {
        this.name = name;
        this.value = value;
    }
}
