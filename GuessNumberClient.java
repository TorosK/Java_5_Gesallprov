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
            
            while (true) {
                String fromServer;
                StringBuilder serverMessage = new StringBuilder();
                
                // Read multi-line message from the server
                while ((fromServer = in.readLine()) != null && !fromServer.isEmpty()) {
                    serverMessage.append(fromServer).append("\n");
                    if (fromServer.contains("play again") || fromServer.startsWith("Welcome") || fromServer.startsWith("Higher") || fromServer.startsWith("Lower")) {
                        break; // Break after receiving the complete message
                    }
                }

                // Print the server's message
                System.out.print("Server: " + serverMessage);

                if (serverMessage.toString().contains("play again")) {
                    System.out.print("Press ENTER to play again or type 'exit' to leave: ");
                } else {
                    System.out.print("Your guess: "); // Prompt for the next guess
                }

                // Read the user's input and send it to the server
                String fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                    if ("exit".equalsIgnoreCase(fromUser.trim())) {
                        break; // Exit the loop if the user decides to leave
                    }
                }
            }
        }
    }
}
