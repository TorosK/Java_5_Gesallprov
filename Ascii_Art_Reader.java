import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The AsciiArtReader class provides functionality to read ASCII art from a file.
 * This class is designed to read text files containing ASCII art, returning the content as a single string.
 */
public class Ascii_Art_Reader {

    /**
     * Reads ASCII art from a specified file and returns it as a string.
     * This method opens the file, reads its contents line by line, appends each line to a StringBuilder,
     * and finally returns the complete ASCII art as a single string. If the file cannot be read,
     * an error message is printed to the standard error stream.
     *
     * @param fileName The name of the file containing ASCII art.
     * @return A string containing the ASCII art read from the file. Returns an empty string if an error occurs.
     */
    public static String readAsciiArt(String fileName) {
        // StringBuilder to accumulate the lines of ASCII art.
        StringBuilder asciiArt = new StringBuilder();

        // Try-with-resources to ensure the BufferedReader is closed after use.
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Reading the file line by line.
            while ((line = reader.readLine()) != null) {
                // Appending each line to the StringBuilder with a newline character.
                asciiArt.append(line).append("\n");
            }
        } catch (IOException e) {
            // Printing the stack trace to standard error if an IOException occurs.
            e.printStackTrace();
        }

        // Returning the complete ASCII art as a string.
        return asciiArt.toString();
    }
}
