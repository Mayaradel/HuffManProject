import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException {
        while(true)
            menu();

    }

    static void menu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose 1 to compress a file, 2 to decompress a file: ");
        System.out.println("1. Compress a file: ");
        System.out.println("2. Decompress a file: ");
        System.out.println("3. exit ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            try {
                FileHandler.fileReader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice == 2) {
            try {
                Decompression.DecompressFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice == 3){
            System.exit(0);
        }
        else
            System.err.println("not recognized input, try again");
    }
}
