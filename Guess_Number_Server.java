import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * The Guess_Number_Server class implements a server for a number guessing game.
 * Clients can connect to this server and play a game where they guess a number within a specified range.
 * The server generates a random number and provides feedback on the client's guesses until the correct number is guessed.
 */
public class Guess_Number_Server {

    // Constants defining the range of possible numbers to guess.
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 10;

    /**
     * The main method starts the server, accepts client connections, and manages the game sessions.
     * 
     * @param args Command-line arguments (not used).
     * @throws IOException If an I/O error occurs while handling client connections.
     */
    public static void main(String[] args) throws IOException {
        int port = 12345; // The port number on which the server will listen for connections.

        // Creating a server socket to listen for client connections on the specified port.
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Server loop to continuously accept client connections and start new game sessions.
            while (true) {
                // Accepting a client connection and setting up I/O streams.
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    // Displaying information about the connected client.
                    InetSocketAddress remoteSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                    String clientIP = remoteSocketAddress.getAddress().getHostAddress();
                    int clientPort = remoteSocketAddress.getPort();
                    System.out.println("Client connected - IP: " + clientIP + ", Port: " + clientPort);

                    // Sending a welcome message to the connected client.
                    Send_Welcome_Message.sendWelcomeMessage(out, serverSocket, clientSocket);

                    boolean gameContinue = true;
                    Random random = new Random(); // Random number generator for the game.

                    // Game loop to handle guesses from the connected client.
                    while (gameContinue) {
                        gameContinue = handleClientGuesses(in, out, random);
                    }
                }
                System.out.println("Client disconnected. Waiting for a new connection...");
            }
        }
    }

    /**
     * Handles the client's guesses and provides feedback until the correct number is guessed or the client exits.
     * 
     * @param in The BufferedReader to read messages from the client.
     * @param out The PrintWriter to send messages to the client.
     * @param random The Random instance used to generate the target number.
     * @return false if the client decides to exit, true otherwise to continue the game.
     * @throws IOException If an I/O error occurs while reading from or writing to the client.
     */
    private static boolean handleClientGuesses(BufferedReader in, PrintWriter out, Random random) throws IOException {
        // Generating a new target number for the client to guess.
        int number = MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);

        // Prompting the client to start guessing.
        out.println("New game started. Please input your guessed number from " + MIN_NUMBER + " to " + MAX_NUMBER + " and press ENTER");

        while (true) {
            String inputLine = in.readLine();

            if (inputLine == null) break; // Client has disconnected.

            // Handling the 'exit' command from the client.
            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                return false; // End the game session.
            }

            try {
                int guess = Integer.parseInt(inputLine);

                // Checking the client's guess against the target number.
                if (guess == number) {
                    // Client guessed correctly. Sending a congratulatory message along with ASCII art.
                    String trophyArt = Ascii_Art_Reader.readAsciiArt("ASCII_Art_Trophy.txt");
                    out.println("Correct!\n" + trophyArt + "\nYou won! Enter 'exit' to leave or press ENTER to play again:");
                    inputLine = in.readLine();

                    // Restarting or ending the game based on the client's response.
                    if ("exit".equalsIgnoreCase(inputLine.trim())) {
                        return false; // End the game session.
                    } else {
                        // Generating a new target number for the next round.
                        number = MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);
                        out.println("New game started. Please input your guessed number from " + MIN_NUMBER + " to " + MAX_NUMBER + " and press ENTER");
                        continue;
                    }
                } else if (guess < number) {
                    out.println("Higher"); // Prompting the client to guess a higher number.
                } else {
                    out.println("Lower"); // Prompting the client to guess a lower number.
                }
            } catch (NumberFormatException e) {
                // Handling invalid input from the client.
                out.println("Invalid input. Please guess a number from " + MIN_NUMBER + " to " + MAX_NUMBER + ".");
            }
        }

        return false; // End the game session if the client disconnects.
    }
}
