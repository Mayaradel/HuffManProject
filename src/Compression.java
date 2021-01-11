import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compression {
    // Traverse the Huffman Tree and store Huffman Codes in a map.
    public static void encode(Node root, String str, Map<Character, String> encodings) {

        if (root == null) {
            return;
        }
        // Found a leaf node
        if (BuildHuffmanTree.isLeaf(root)) {
            if (str.length() > 0) {
                encodings.put(root.character, str);
            } else {
                encodings.put(root.character, "1");
            }
        }

        encode(root.leftChild, str + '0', encodings);
        encode(root.rightChild, str + '1', encodings);

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
