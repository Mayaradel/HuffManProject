import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Decompression {

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
//        String ascii = "";
        while ((line = br.readLine()) != null) {
//            ascii += line;
//            System.out.println(ascii);
            binary.append(AsciiToBinary(line));
            System.out.println(line);
        }

        System.out.println(binary);

        if (zeroPadding > 0) {

            binary = binary.delete(binary.length() - zeroPadding, binary.length());
            System.out.println(binary);
        }


        File f = new File("Decompressed file.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        Writer outputStream = new OutputStreamWriter(new FileOutputStream(f.getName(), false));


        int i = 0;
        char c = binary.charAt(i);

        String key = "";
        key += c;
        System.out.println("Content:");
        while (key != null && i < binary.length() - 1) {
            if (decodings.get(key) != null) {
                if (decodings.get(key).equals("\\n")) {
                    System.out.println();
                    //outputStream.write(padding + "\n");
                    outputStream.write("\n");
                } else if (decodings.get(key).equals("\\r")) {
                } else {
                    System.out.print(decodings.get(key));
                    outputStream.write(decodings.get(key));

                }
                key = "";
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
