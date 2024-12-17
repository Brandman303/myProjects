package cs250.hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cs250.hw4.TreeStructure;

public class BinaryTree implements TreeStructure {

    // Driver code
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(args[0]);
        FileReader fReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fReader);
        TreeStructure tree = new BinaryTree();
        Random rng = new Random(42);
        ArrayList<Integer> nodesToRemove = new ArrayList<>();
        ArrayList<Integer> nodesToGet = new ArrayList<>();
        String line = bufferedReader.readLine();

        while (line != null) {
            Integer lineInt = Integer.parseInt(line);
            tree.insert(lineInt);
            Integer rand = rng.nextInt(10);
            if (rand < 5)
                nodesToRemove.add(lineInt);
            else if (rand >= 5)
                nodesToGet.add(lineInt);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        for (int i = 0; i < 10; i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }

        System.out.println("Max depth: " + tree.findMaxDepth());
        System.out.println("Min depth: " + tree.findMinDepth());
        for (Integer num : nodesToRemove) {
            tree.remove(num);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(nodesToGet.get(i) + " inserted at " + tree.get(nodesToGet.get(i)));
        }

        System.out.println("Max depth: " + tree.findMaxDepth());
        System.out.println("min depth: " + tree.findMinDepth());

    }

    // Data member
    private Node root = null;

    // Node class (Node data members, constructor, and methods)
    private class Node {
        int key;
        long timeStamp;
        Node leftChild;
        Node rightChild;
        Node parent;

        public Node(int item, long timeStamp) {
            key = item;
            this.timeStamp = timeStamp;
            leftChild = null;
            rightChild = null;
            parent = null;
        }
    }

    // Binary tree default constrctor
    public BinaryTree() {
        root = null;
    }

    // Binary Tree insert num method
    @Override
    public void insert(Integer num) {
        long time = System.nanoTime();
        root = inserting(root, num, time);
        // throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    // private helper method for inserting a node into Binary tree
    private Node inserting(Node root, int key, long time) {
        if (root == null) {
            root = new Node(key, time);
            return root;
        }
        if (key < root.key) {
            root.leftChild = inserting(root.leftChild, key, time);
        }
        if (key > root.key) {
            root.rightChild = inserting(root.rightChild, key, time);
        }
        return root;
    }

    // Binary tree method for removing a node.
    @Override
    public Boolean remove(Integer num) {
        root = removing(root, num); // root is the current node
        if (root != null) {
            return true;
        }
        return false;
        // throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    // Helper method to remove node
    private Node removing(Node root, int key) {

        // There is no node to remove, return root which is null
        if (root == null) {
            return root;
        }

        /*
         * Traverses the tree recursively.
         * If the given key is less than the current nodes key,
         * call this method with the left node and our original given key.
         * Repeat this process if the given key is greater than the current nodes
         * key for the rightChild child
         */
        if (key < root.key) {
            root.leftChild = removing(root.leftChild, key);
        } else if (key > root.key) {
            root.rightChild = removing(root.rightChild, key);
        }

        /*
         * The given key is neither < or > the current nodes key and is
         * an equivalent value. Therefore, we have found the node we are looking
         * for. Three cases are handled below. If there is only one child
         * simply remove the node and update the previous nodes key. However,
         * if there are two children assign the current nodes key the value of
         * the smallest value of it's rightChild subtree.
         */
        else {
            if (root.leftChild == null) {
                return root.rightChild;
            }
            if (root.rightChild == null) {
                return root.leftChild;
            }
            root.key = minValue(root.rightChild);
            root.rightChild = removing(root.rightChild, root.key);
        }

        return root;
    }

    // Finds the leftmost node of a subTree. 
    private int minValue(Node root) {

        int minValue = root.key;
        while (root.leftChild != null) {
            minValue = root.leftChild.key;
            root = root.leftChild;
        }
        return minValue;
    }

    //BinaryTree get method
    @Override
    public Long get(Integer num) {

        Node node = getting(root, num);
        if (node != null) {
            return node.timeStamp;
        }
        return null;

        // throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    // get helper method for getting and returning a node
    private Node getting(Node root, int key) {

        if (root == null || root.key == key) {
            return root;
        }
        if (key < root.key) {
            return getting(root.leftChild, key);
        } else {
            return getting(root.rightChild, key);
        }
    }

    //BinaryTree findMaxDepth method
    @Override
    public Integer findMaxDepth() {

        return findMaxDepth(root);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findMaxDepth'");
    }

    //findMaxDepth overloaded method to help find max depth using DFS (DepthFirstSearch)
    private Integer findMaxDepth(Node node) {

        if (node == null) {
            return 0; // 0 or null?
        }

        int leftNodeDepth = findMaxDepth(node.leftChild);
        int rightChildNodeDepth = findMaxDepth(node.rightChild);

        return Math.max(leftNodeDepth, rightChildNodeDepth) + 1;

    }

    @Override
    public Integer findMinDepth() {
        return findMinDepth(root);
        // throw new UnsupportedOperationException("Unimplemented method
        // 'findMinDepth'");
    }

    private Integer findMinDepth(Node node) {

        if (node == null) {
            return 0; // null or 0?
        }

        if (node.leftChild == null) {
            return findMinDepth(node.rightChild) + 1;
        }

        if (node.rightChild == null) {
            return findMinDepth(node.leftChild) + 1;
        }

        return Math.min(findMinDepth(node.leftChild), findMinDepth(node.rightChild)) + 1;
    }

}