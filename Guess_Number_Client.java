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

            while (true) {
                fromServer = in.readLine();
                if (fromServer == null) break; // Server connection lost
                System.out.println("Server: " + fromServer);

                if (fromServer.contains("Enter 'exit' to leave or press ENTER to play again:")) {
                    String userResponse = stdIn.readLine(); // Read the user's decision
                    out.println(userResponse.isEmpty() ? "" : userResponse); // Send it back to the server
                    if ("exit".equalsIgnoreCase(userResponse.trim())) {
                        break; // Break the loop if the user wants to exit
                    }
                }

                // Check for prompts where a guess is expected
                if (fromServer.startsWith("New game started") || fromServer.equals("Higher") || fromServer.equals("Lower") || fromServer.startsWith("Invalid input")) {
                    boolean validGuess = false;
                    while (!validGuess) {
                        System.out.print("Your guess: ");
                        String userGuess = stdIn.readLine();

                        // Check if the user input is a number or the exit command
                        if (userGuess.matches("\\d+") || "exit".equalsIgnoreCase(userGuess.trim())) {
                            out.println(userGuess); // Send the guess or command to the server
                            validGuess = true; // Exit the loop after a valid guess
                        } else {
                            System.out.println("Invalid input. Please enter a number or 'exit' to leave.");
                        }
                    }
                }
            }
        }
    }
}
