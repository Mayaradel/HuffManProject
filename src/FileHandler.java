import java.io.*;
import java.util.Map;

public class FileHandler {


    static void fileReader() throws IOException {

        //Scanner scanner = new Scanner(System.in);
        File file = new File("huff.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = br.readLine()) != null) {

            stringBuilder.append(line);
            stringBuilder.append(System.getProperty("line.separator"));

        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);


        long startTime = System.nanoTime();
        BuildHuffmanTree.buildHuffmanTree(stringBuilder.toString());
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total execution time: " + elapsedTime / 1000000 + "ms");

        br.close();

    }

    static void fileWriter(Map<Character, String> huffmanCode, int padding, StringBuilder ascii) throws IOException {

        File file = new File("output.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        Writer outputStream = new OutputStreamWriter(new FileOutputStream(file.getName(), false), "ISO-8859-1");

//        FileWriter fw = new FileWriter(file, false);
        outputStream.write(padding + "\n");
        outputStream.write(huffmanCode.size() + "\n");

        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            if (entry.getKey() == '\n')
                outputStream.write("\\n" + "=" + entry.getValue() + "\n");
            else if (entry.getKey() == '\r')
                outputStream.write("\\r" + "=" + entry.getValue() + "\n");
            else
                outputStream.write(entry.getKey() + "=" + entry.getValue() + "\n");

        }

        outputStream.write(String.valueOf(ascii));

        outputStream.flush();
        System.out.println("File written Successfully");
        outputStream.close();

    }
}
