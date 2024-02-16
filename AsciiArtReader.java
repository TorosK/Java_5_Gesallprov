import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AsciiArtReader {
    public static String readAsciiArt(String fileName) {
        StringBuilder asciiArt = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                asciiArt.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return asciiArt.toString();
    }
}
