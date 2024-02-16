import java.io.*;
import java.net.*;

public class Guess_Number_Client {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // Server hostname or IP
        int port = 12345; // Server port

        try (Socket socket = new Socket(hostName, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String fromServer;
            // Display server messages
            // Inside the main method of the Guess_Number_Client class
            while (true) {
                fromServer = in.readLine();
                if (fromServer == null) break; // Server connection lost
                System.out.println("Server: " + fromServer);
            
                if (fromServer.startsWith("New game started") || fromServer.equals("Higher") || fromServer.equals("Lower")) {
                    System.out.print("Your guess: ");
                    String userGuess = stdIn.readLine();
                    out.println(userGuess); // Send the next guess or command to the server
                } else if (fromServer.contains("END OF GAME!")) {
                    // Handle end-of-game scenario, waiting for user's decision
                    String userDecision = stdIn.readLine(); // Read user's decision to play again or exit
                    out.println(userDecision.isEmpty() ? "new game" : userDecision.trim()); // Send decision to server
                    if ("exit".equalsIgnoreCase(userDecision.trim())) break; // Exit the client if user decides to
                }
            }
            

        }
    }
}
