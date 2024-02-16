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
// Inside the client's main loop, after printing the server's message
if (fromServer.contains("Enter 'exit' to leave or press ENTER to play again:")) {
    String userResponse = stdIn.readLine(); // Read the user's decision
    out.println(userResponse.isEmpty() ? "" : userResponse); // Send it back to the server
    if ("exit".equalsIgnoreCase(userResponse.trim())) {
        break; // Break the loop if the user wants to exit
    }
    // No need for an else block here, as the loop will continue and wait for the server's next message
}

                
                

                if (fromServer.startsWith("New game started") || fromServer.equals("Higher") || fromServer.equals("Lower")) {
                    System.out.print("Your guess: ");
                    String userGuess = stdIn.readLine();
                    out.println(userGuess); // Send the next guess or command to the server
                } 
            }
            

        }
    }
}
