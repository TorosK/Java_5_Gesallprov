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
            
            // Display the welcome message as a single block
            String fromServer;
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null) break;
                System.out.println("Server: " + fromServer);
                if (fromServer.contains("press ENTER")) break; // Stop after the welcome message
            }
            
            while (true) {
                System.out.print("Your guess: "); // Prompt for the first guess
                String fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser); // Send the guess to the server
                    if ("exit".equalsIgnoreCase(fromUser.trim())) break; // Exit if the user types 'exit'
                }

                // Read the server's response after sending the guess
                StringBuilder serverResponse = new StringBuilder();
                while ((fromServer = in.readLine()) != null && !fromServer.isEmpty()) {
                    serverResponse.append(fromServer).append("\n");
                    if (fromServer.contains("play again") || fromServer.contains("Higher") || fromServer.contains("Lower")) break;
                }
                
                // Print the server's response
                System.out.println("Server: " + serverResponse.toString());

                if (serverResponse.toString().contains("play again")) {
// Client-side: After receiving the win message
System.out.print("Press ENTER or input a number to play again or type 'exit' to leave: ");
String decision = stdIn.readLine();
if ("exit".equalsIgnoreCase(decision.trim())) {
    out.println("exit"); // Inform the server the client is exiting
    break; // Exit the client loop
} else {
    out.println("new game"); // Inform the server to start a new game
    if (!decision.isEmpty()) {
        out.println(decision); // Send the first guess for the new game
    }
}
                }
            }
        }
    }
}
