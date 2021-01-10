import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose 1 to compress a file, 2 to decompress a file: ");
        System.out.println("1. Compress a file: ");
        System.out.println("2. Decompress a file: ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            FileHandler.fileReader();
        } else {
            System.out.println("later");
        }
//        if (LeafNode(root)) {
//            // Special case: For input like a, aa, aaa, etc.
//            while (root.data-- > 0) {
//                System.out.print(root.c);
//            }
//        } else {
//            // Traverse the Huffman Tree again and this time,
//            // decode the encoded string
//            int index = -1;
//            while (index < sb.length() - 1) {
//                index = decompress(root, index, sb);
//            }
//        }
    }
}
