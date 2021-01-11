import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BuildHuffmanTree {

    public static boolean isLeaf(Node root) {
        return root.leftChild == null && root.rightChild == null;
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
        Compression.compressFile(root, characters);
    }


    public static String AsciiToBinary(String asciiString) {

        byte[] bytes = asciiString.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder binary = new StringBuilder(bytes.length * 8);

        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i];
            for (int j = 0; j < 8; j++) {
                if ((value & 128) == 0) {
                    binary.append(0);
                } else {
                    binary.append(1);
                }
                value <<= 1;
            }
        }
        return binary.toString();
    }
}
