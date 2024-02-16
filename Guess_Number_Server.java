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

                    sendWelcomeMessage(out, serverSocket, clientSocket);

                    boolean gameContinue = true;
                    Random random = new Random(); // Initialize Random outside the game loop

                    while (gameContinue) {
                        int number = random.nextInt(11); // Generate a new random number for each new game

                        gameContinue = handleClientGuesses(in, out, number);
                    }
                }
                System.out.println("Client disconnected. Waiting for a new connection...");
            }
        }
    }

    private static void sendWelcomeMessage(PrintWriter out, ServerSocket serverSocket, Socket clientSocket) throws IOException {
        String questionMarkArt = AsciiArtReader.readAsciiArt("ASCII_Art_Question_Mark.txt");
        String welcomeMessage = "\nServer Host: " + serverSocket.getInetAddress().getHostName() + "\n" +
                "Server IP: " + InetAddress.getLocalHost().getHostAddress() + "\n" +
                "Server Port: " + serverSocket.getLocalPort() + "\n" +
                "Connected from IP: " + clientSocket.getInetAddress().getHostAddress() + "\n" + 
                "Connected from Port: " + clientSocket.getPort() + "\n\n" +
                "Welcome to the 'Guess the Number' Game!\n\n" +
                questionMarkArt +
                "Please input your guessed number from 0 to 10 and press ENTER.";
        out.println(welcomeMessage);
    }

    private static boolean handleClientGuesses(BufferedReader in, PrintWriter out, int number) throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                return false; // Exit the game if the client wants to end
            }

            int guess = Integer.parseInt(inputLine);
            if (guess == number) {
                String trophyArt = AsciiArtReader.readAsciiArt("ASCII_Art_Trophy.txt");
                out.println("Correct!\n" + trophyArt +
                        "END OF GAME!\n" +
                        "You won! Enter 'exit' to leave or press ENTER to play again:");
                
                inputLine = in.readLine();
                if ("exit".equalsIgnoreCase(inputLine.trim())) {
                    return false; // End the game if the player chooses to exit
                } else if (inputLine.trim().isEmpty()) {
                    return true; // Start a new game if the player presses ENTER
                }
            } else if (guess < number) {
                out.println("Higher");
            } else {
                out.println("Lower");
            }
        }
        return true; // Default to continue the game
    }
}
