package cs250.hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BTree implements TreeStructure {
    private Node root = null;

    private class Node {
        static final int Degree = 64; // Node degree. A node holds 63 keys (N) and 64 child pointers (N+1)
        static final int N = Degree - 1; // Max number of keys

        int n; // current number of keys
        int[] keys;
        Node[] pointers;
        boolean isLeaf;
        long timeStamp;

        public Node(boolean isLeaf, long timeStamp) {
            this.n = 0;
            this.isLeaf = isLeaf;
            this.timeStamp = timeStamp;
            this.keys = new int[N];
            this.pointers = new Node[N + 1];
        }

        public void inserting(int num) {
            int i = n - 1;
            if (isLeaf) {
                while (i >= 0 && keys[i] > num) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                keys[i + 1] = num;
                n++;
            } 
            else {
                while (i >= 0 && keys[i] > num) {
                    i--;
                }
                if (pointers[i + 1].n == (N)) {
                    split(i + 1, pointers[i + 1]);
                    if (keys[i + 1] < num) {
                        i++;
                    }
                }
                pointers[i + 1].inserting(num);
            }
        }

        public void split(int i, Node splittingNode) {
            long timeStamp = System.nanoTime();
            Node siblingNode = new Node(splittingNode.isLeaf, timeStamp);
            siblingNode.n = (N / 2);

            // Transfer keys from splittingNode to siblingNode
            for (int j = 0; j < (N / 2); j++) {
                siblingNode.keys[j] = splittingNode.keys[j + (N / 2 + 1)];
            }

            // Transfer child pointers from splittingNode to siblingNode
            if (!splittingNode.isLeaf) {
                for (int j = 0; j <= (N / 2); j++) {
                    siblingNode.pointers[j] = splittingNode.pointers[j + (N / 2 + 1)];
                }
            }

            splittingNode.n = (N / 2);

            // Shift the parent's child pointers to make space for the new sibling
            for (int j = n; j > i; j--) {
                pointers[j + 1] = pointers[j];
            }
            pointers[i + 1] = siblingNode;

            // Shift the parent's keys to make space for the promoted key
            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }
            keys[i] = splittingNode.keys[N / 2];

            n++;

            // Clear the transferred keys and pointers from the splittingNode to avoid IndexOutOfBounds
            for (int j = N / 2 + 1; j <= N; j++) {
                if (j < N) {
                    splittingNode.keys[j] = 0;
                }
                if (j < N + 1) {
                    splittingNode.pointers[j] = null;
                }
            }
        }

        public void removing(int num) {
            for (int i = 0; i < n; i++) {
                if (keys[i] == num && isLeaf) {
                    for (int j = i; j < n - 1; j++) {
                        keys[j] = keys[j + 1];
                    }
                    n--;
                    return;
                }
                if (keys[i] == num && !isLeaf) { // Idk if this is correct, I think it may need to be recursive
                    if (pointers[i].n >= N / 2) {
                        int pred = getPredecessor(pointers[i]);
                        keys[i] = pred;
                        pointers[i].removing(pred);
                    } 
                    else if (pointers[i + 1].n >= N / 2) {
                        int successor = getSuccessor(pointers[i + 1]);
                        keys[i] = successor;
                        pointers[i + 1].removing(successor);
                    } 
                    else {
                        merge(keys[i], this, pointers[i], pointers[i + 1]);
                    }
                }
            }
        }

        public Long getting(Integer num) {
            int i = 0;
            for (; i < n && num > keys[i]; i++) {
            }

            if (i < n && keys[i] == num) {
                return timeStamp;
            }

            if (isLeaf) {
                return null;
            } 
            else {
                return pointers[i].getting(num);
            }
        }
    }

    private int getPredecessor(Node node) {
        while (!node.isLeaf) {
            node = node.pointers[node.n];
        }
        return node.keys[node.n - 1];
    }

    private int getSuccessor(Node node) {
        while (!node.isLeaf) {
            node = node.pointers[0];
        }
        return node.keys[0];
    }

    public void merge(int num, Node parent, Node x, Node y) {
        x.keys[x.n] = num;

        for (int i = 0; i < y.n; i++) {
            x.keys[x.n + 1 + i] = y.keys[i];
        }

        if (!x.isLeaf) {
            for (int i = 0; i <= y.n; i++) {
                x.pointers[x.n + 1 + i] = y.pointers[i];
            }
        }

        x.n += y.n + 1;

        int i = 0;
        while (i < parent.n && parent.keys[i] != num) {
            i++;
        }

        for (int j = i; j < parent.n - 1; j++) {
            parent.keys[j] = parent.keys[j + 1];
        }

        for (int j = i + 1; j <= parent.n; j++) {
            parent.pointers[j] = parent.pointers[j + 1];
        }

        parent.n--;

        x.removing(num);
    }

    @Override
    public void insert(Integer num) {
        long timeStamp = System.nanoTime(); // I think this should be here, but possibly spread throughout this method?
        if (root == null) {
            root = new Node(true, timeStamp); // correct parent pass?
            root.keys[0] = num;
            root.n = 1;
        } 
        else {
            if (root.n == (Node.N)) {
                Node newNode = new Node(false, timeStamp);
                newNode.pointers[0] = root;
                newNode.split(0, root);
                int i = 0;
                if (newNode.keys[0] < num) {
                    i++;
                }
                newNode.pointers[i].inserting(num);
                root = newNode;
            } 
            else {
                root.inserting(num);
            }
        }
        // throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public Boolean remove(Integer num) {
        if (root == null) {
            return false;
        }

        root.removing(num);

        if (root.n == 0) {
            if (root.isLeaf) {
                root = null;
            } 
            else {
                root = root.pointers[0];
            }
        }
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Long get(Integer num) {
        if (root == null) {
            return null;
        }
        return root.getting(num);
    }

    @Override
    public Integer findMaxDepth() {
        return findingMaxDepth(root);
    }

    private Integer findingMaxDepth(Node node) {
        if (node == null) {
            return 0;
        }

        int maximum = 0;
        for (int i = 0; i <= node.n; i++) {
            maximum = Math.max(maximum, findingMaxDepth(node.pointers[i]));
        }
        return maximum + 1;
    }

    @Override
    public Integer findMinDepth() {
        return findingMinDepth(root);
    }

    private Integer findingMinDepth(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.isLeaf) {
            return 1;
        }
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i <= node.n; i++) {
            minimum = Math.min(minimum, findingMinDepth(node.pointers[i]));
        }
        return minimum + 1;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(args[0]);
        // File file = new File("data.txt");
        FileReader fReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fReader);
        TreeStructure tree = new BTree();
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
        System.out.println("Min depth: " + tree.findMinDepth());
    }
}
