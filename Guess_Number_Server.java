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

// Inside the Guess_Number_Server class

private static boolean handleClientGuesses(BufferedReader in, PrintWriter out, Random random) throws IOException {
    int number = random.nextInt(11); // Initialize the number for the first game

    // Send the initial prompt for the first game
    out.println("New game started. Please input your guessed number from 0 to 10 and press ENTER");

    while (true) { // Loop for handling multiple game sessions
        String inputLine = in.readLine(); // Read the client's guess
        if (inputLine == null) break; // Break out of the loop if the connection is lost

        if ("exit".equalsIgnoreCase(inputLine.trim())) {
            return false; // End the game if the player chooses to exit
        }

        try {
            int guess = Integer.parseInt(inputLine); // Parse the guess inside the try block

            if (guess == number) {
                String trophyArt = AsciiArtReader.readAsciiArt("ASCII_Art_Trophy.txt");
                out.println("Correct!\n" + trophyArt +
                        "END OF GAME!\n" +
                        "You won! Enter 'exit' to leave or press ENTER to play again:");

                inputLine = in.readLine(); // Wait for the client's decision to exit or play again
                if ("exit".equalsIgnoreCase(inputLine.trim())) {
                    return false; // End the game if the player chooses to exit
                }
                number = random.nextInt(11); // Generate a new random number for the next game immediately
                // Prompt for the next game is moved outside the if-else blocks
            } else if (guess < number) {
                out.println("Higher");
            } else {
                out.println("Lower");
            }
        } catch (NumberFormatException e) {
            out.println("Invalid input. Please guess a number from 0 to 10.");
        }

        // Prompt for the next guess is sent here, ensuring it's always sent after a guess is made or a new game starts
        out.println("Please input your guessed number from 0 to 10 and press ENTER");
    }
    return true; // Keep the game going unless 'exit' is received
}

}
