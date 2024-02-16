import java.io.*;
import java.net.*;
import java.util.Random;

public class Guess_Number_Server {
    // Define constants for the number range at the top for easy management
    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 10;

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
        // Generate a random number within the specified range
        int number = MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);

        out.println("New game started. Please input your guessed number from " + MIN_NUMBER + " to " + MAX_NUMBER + " and press ENTER");

        while (true) {
            String inputLine = in.readLine();

            if (inputLine == null) break;

            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                return false;
            }

            try {
                int guess = Integer.parseInt(inputLine);

                if (guess == number) {
                    String trophyArt = AsciiArtReader.readAsciiArt("ASCII_Art_Trophy.txt");
                    out.println("Correct!\n" + trophyArt + "\nYou won! Enter 'exit' to leave or press ENTER to play again:");
                    inputLine = in.readLine();

                    if ("exit".equalsIgnoreCase(inputLine.trim())) {
                        return false;
                    } else {
                        number = MIN_NUMBER + random.nextInt(MAX_NUMBER - MIN_NUMBER + 1);
                        out.println("New game started. Please input your guessed number from " + MIN_NUMBER + " to " + MAX_NUMBER + " and press ENTER");
                        continue;
                    }
                } else if (guess < number) {
                    out.println("Higher");
                } else {
                    out.println("Lower");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please guess a number from " + MIN_NUMBER + " to " + MAX_NUMBER + ".");
            }
        }

        return false;
    }
}
