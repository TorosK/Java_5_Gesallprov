import java.io.*;
import java.net.*;

public class GuessNumberClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // Server hostname or IP
        int port = 12345; // Server port
        
        try (Socket socket = new Socket(hostName, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server says: " + in.readLine());
            }
        }
    }
}
