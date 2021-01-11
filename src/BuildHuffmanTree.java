import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

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
            if (str.length() > 0) {
                encodings.put(root.character, str);
            } else {
                encodings.put(root.character, "1");
            }
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

    static void DecompressFile() throws IOException {

        File file = new File("Compressed file.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

        Map<String, String> decodings = new HashMap<>();
        int zeroPadding;
        int hashMapSize;

        String line = br.readLine();
        zeroPadding = Integer.parseInt(line);
        line = br.readLine();
        hashMapSize = Integer.parseInt(line);

        for (int i = 0; i < hashMapSize; i++) {
            line = br.readLine();
            String[] hashmapEntires = line.split("=", 2);
            if (hashmapEntires.length >= 2) {
                decodings.put(hashmapEntires[1], hashmapEntires[0]);
            }
        }

        System.out.println(zeroPadding + "\n" + hashMapSize);
        for (Map.Entry<String, String> entry : decodings.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        StringBuilder binary = new StringBuilder();
        while ((line = br.readLine()) != null) {
            binary.append(AsciiToBinary(line));
            System.out.println(line);
        }
        System.out.println(binary);
        if (zeroPadding > 0) {
            binary.deleteCharAt(binary.length() - 1);
            binary.deleteCharAt(binary.length() - 1);
            binary.deleteCharAt(binary.length() - 1);
            binary.deleteCharAt(binary.length() - 1);
            System.out.println(binary);
        }
        File f = new File("Decompressed file.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        Writer outputStream = new OutputStreamWriter(new FileOutputStream(f.getName(), false));
        int i=0;
        char c = binary.charAt(i);
        String key = "";
        key += c;
        System.out.println("Content:");
        while (key != null && i<binary.length()-1){
            if (decodings.get(key) != null){
                if(decodings.get(key).equals("\\n")){
                    System.out.println();
                    //outputStream.write(padding + "\n");
                    outputStream.write("\n");
                }
                else if(decodings.get(key).equals("\\r")){}
                else{
                    System.out.print(decodings.get(key));
                    outputStream.write(decodings.get(key));
                }
                key  = "";
            }
            i++;
            c = binary.charAt(i);
            key += c;
        }
        outputStream.flush();
        outputStream.close();
        System.out.println();
    }

    public static String AsciiToBinary(String asciiString) {

        byte[] bytes = asciiString.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder binary = new StringBuilder(bytes.length * 8);

        for (int i =0 ; i<bytes.length ; i++){
            int value = bytes[i];
            for (int j= 0 ; j<8 ; j++) {
                if ((value & 128) == 0) {
                    binary.append(0);
                } else {
                    binary.append(1);
                }
                value <<=1;
            }
        }
        return binary.toString();
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
            asciii.append(x);
        }
        FileHandler.fileWriter(encodings, zeroPaddingNum, asciii);
    }
}