import java.io.*;
import java.net.*;
import java.util.Random;

public class Guess_Number_Server {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Server Host: " + serverSocket.getInetAddress().getHostName());

            while (true) { // Server loop to allow multiple games
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    InetSocketAddress remoteSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                    String clientIP = remoteSocketAddress.getAddress().getHostAddress();
                    int clientPort = remoteSocketAddress.getPort();
                    System.out.println("Client connected - IP: " + clientIP + ", Port: " + clientPort);

                    // Call to the separated method in ServerUtils
                    Send_Welcome_Message.sendWelcomeMessage(out, serverSocket, clientSocket);

                    boolean gameContinue = true;
                    Random random = new Random(); // Initialize Random outside the game loop

                    while (gameContinue) {
                        // Pass the Random instance to the game handling method
                        gameContinue = handleClientGuesses(in, out, random);
                    }
                }
                System.out.println("Client disconnected. Waiting for a new connection...");
            }
        }
    }

    private static boolean handleClientGuesses(BufferedReader in, PrintWriter out, Random random) throws IOException {
        boolean newGame = true; // Flag to indicate the start of a new game
    
        while (true) { // Loop for handling multiple game sessions
            int number = random.nextInt(11); // Generate a new random number for each game session
    
            if (newGame) {
                out.println("New game started. \"Please input your guessed number from 0 to 10 and press ENTER"); // Inform the client that a new game has started
                newGame = false; // Reset the flag after the message is sent
            }
    
            String inputLine = in.readLine(); // Read the client's guess
            if (inputLine == null) break; // Break out of the loop if the connection is lost
    
            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                return false; // Exit the game if the client wants to end
            }
    
            try {
                int guess = Integer.parseInt(inputLine);
                if (guess == number) {
                    String trophyArt = AsciiArtReader.readAsciiArt("ASCII_Art_Trophy.txt");
                    out.println("Correct!\n" + trophyArt +
                            "END OF GAME!\n" +
                            "You won! Enter 'exit' to leave or press ENTER to play again:");
    
                    inputLine = in.readLine(); // Wait for the client's decision to exit or play again
                    if ("exit".equalsIgnoreCase(inputLine.trim())) {
                        return false; // End the game if the player chooses to exit
                    }
                    newGame = true; // Set flag for new game if the client chooses to continue
                } else if (guess < number) {
                    out.println("Higher");
                } else {
                    out.println("Lower");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please guess a number from 0 to 10.");
            }
        }
    
        return true; // Default to continue the game if the loop exits normally
    }    
}
