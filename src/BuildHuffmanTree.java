import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BuildHuffmanTree {
    public static boolean isLeaf(Node root) {
        return root.leftChild == null && root.rightChild == null;
    }

    // Traverse the Huffman Tree and store Huffman Codes in a map.
    public static void encode(Node root, String str, Map<Character, String> encodings) {

        if (root == null) {
            return;
        }
        // Found a leaf node
        if (isLeaf(root)) {
//            if (str.length() > 0) {
//                encodings.put(root.character, str);
//            } else {
//                encodings.put(root.character, "1");
//            }
            encodings.put(root.character, str.length() > 0 ? str : "1");
        }

        encode(root.leftChild, str + '0', encodings);
        encode(root.rightChild, str + '1', encodings);

    }

    public static void buildHuffmanTree(String s) throws IOException {

        char[] characters = s.toCharArray();


        Map<Character, Integer> frequencies = new HashMap<>();

        for (char c : characters) {
            if (frequencies.containsKey(c)) {
                frequencies.put(c, frequencies.get(c) + 1);

            } else {
                frequencies.put(c, 1);
            }

        }
        PriorityQueue<Node> Queue = new PriorityQueue<>(frequencies.size() + 1, new MyComparator());

        frequencies.entrySet().forEach(entry -> {
            Node node = new Node();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.leftChild = null;
            node.rightChild = null;
            Queue.add(node);
        });

        // create a root node
        Node root = null;


        while (Queue.size() > 1) {

            Node firstMinNode = Queue.peek();
            Queue.poll();
            Node secondMinNode = Queue.peek();
            Queue.poll();

            Node sum = new Node();
            sum.frequency = firstMinNode.frequency + secondMinNode.frequency;
            sum.character = 0;
            sum.leftChild = firstMinNode;
            sum.rightChild = secondMinNode;
            root = sum;

            Queue.add(sum);
        }

        compressFile(root, characters);

    }

    public static void compressFile(Node root, char[] characters) throws IOException {


        Map<Character, String> encodings = new HashMap<>();
        encode(root, "", encodings);

        // Print the Huffman codes
        System.out.println("Huffman Codes are: " + encodings);

        // Print encoded string
        StringBuilder encodedString = new StringBuilder();
        for (char c : characters) {
            encodedString.append(encodings.get(c));
        }


        System.out.println("Encoded string is: " + encodedString);


        int zeroPaddingNum = 0;
        if (((encodedString.length()) % 8) != 0) {
            zeroPaddingNum = 8 - (((encodedString.length()) % 8));
            for (int i = 0; i < zeroPaddingNum; i++) {
                encodedString.append("0");
            }
            System.out.println("Encoded string with zero padding: " + encodedString);
        }


        StringBuilder asciii = new StringBuilder();

        for (int j = 0; j < (encodedString.length()) / 8; j++) {

            int ascii = Integer.parseInt(encodedString.substring(8 * j, (j + 1) * 8), 2);
            char x = (char) ascii;
//            System.out.println(x);
            asciii.append(x);
        }

        FileHandler.fileWriter(encodings, zeroPaddingNum, asciii);

//        String buffer = "";
//        long asciiCost = (characters.length)*8;
//        long compressedCost = 0;
    }

}
