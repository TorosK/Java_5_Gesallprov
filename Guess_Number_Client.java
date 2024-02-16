import java.io.*;
import java.net.*;

/**
 * The {@code Guess_Number_Client} class represents a client for a number guessing game.
 * It connects to a server, reads prompts from the server, and sends user guesses or commands back to the server.
 */
public class Guess_Number_Client {

    /**
     * The main method of the client, which establishes a connection to the server
     * and handles user inputs and server responses.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an I/O error occurs when opening the socket or reading from/writing to the socket.
     */
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // The server's hostname or IP address
        int port = 12345; // The port number on which the server is listening

        // Try-with-resources to ensure proper closure of socket and I/O streams
        try (
            Socket socket = new Socket(hostName, port); // Establish a connection to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Output stream to send data to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input stream to receive data from the server
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)) // Standard input stream to read user input
        ) {
            String fromServer; // Variable to hold the server's responses

            // Main loop to continuously read responses from the server and process user input
            while (true) {
                fromServer = in.readLine(); // Read the server's response
                if (fromServer == null) break; // If the server closes the connection, exit the loop

                System.out.println("Server: " + fromServer); // Display the server's response

                // Check for a specific prompt from the server indicating the game can be restarted or exited
                if (fromServer.contains("Enter 'exit' to leave or press ENTER to play again:")) {
                    String userResponse = stdIn.readLine(); // Read the user's decision
                    out.println(userResponse.isEmpty() ? "" : userResponse); // Send the response back to the server
                    if ("exit".equalsIgnoreCase(userResponse.trim())) {
                        break; // Exit the loop if the user chooses to leave the game
                    }
                }

                // Check if the server is expecting a guess from the user
                if (fromServer.startsWith("New game started") || fromServer.equals("Higher") || fromServer.equals("Lower") || fromServer.startsWith("Invalid input")) {
                    boolean validGuess = false; // Flag to track valid user input

                    // Loop until a valid guess is made
                    while (!validGuess) {
                        System.out.print("Your guess: ");
                        String userGuess = stdIn.readLine(); // Read the user's guess

                        // Validate the user's guess to ensure it is a number or an 'exit' command
                        if (userGuess.matches("\\d+") || "exit".equalsIgnoreCase(userGuess.trim())) {
                            out.println(userGuess); // Send the guess or command to the server
                            validGuess = true; // Mark the guess as valid to exit the loop
                        } else {
                            System.out.println("Invalid input. Please enter a number or 'exit' to leave."); // Inform the user of invalid input
                        }
                    }
                }
            }
        }
    }
}
