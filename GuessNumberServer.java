import java.io.*;
import java.net.*;
import java.util.Random;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            while (true) { // Server loop to allow multiple games
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                    System.out.println("Client connected");
                    out.println("Welcome to the Guess the Number game!\nPlease input your guessed number from 0 to 10 and press ENTER.");
                    
                    Random random = new Random();
                    int number = random.nextInt(11); // Generate random number between 0-10
                    
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(inputLine.trim())) {
                            break; // Exit the loop if the client wants to end the game
                        }

                        int guess = Integer.parseInt(inputLine);
                        if (guess == number) {
// When the guess is correct
// Server-side: After sending the win message and trophy
out.println("Correct!\n" +
            "       ___________\n" +
            "      '._==_==_=_.'\n" +
            "      .-\\:      /-.\n" +
            "     | (|:.     |) |\n" +
            "      '-|:.     |-'\n" +
            "        \\::.    /\n" +
            "         '::. .'\n" +
            "           ) (\n" +
            "         _.' '._\n" +
            "        `\"\"\"\"\"\"\"`\n" +
            "You won! Enter 'exit' to leave or press ENTER to play again.");

// Reset the game for a new round
number = random.nextInt(11); // Generate a new target number for the next game

                        } else if (guess < number) {
                            out.println("Higher");
                        } else {
                            out.println("Lower");
                        }
                    }
                }
                System.out.println("Client disconnected. Waiting for a new connection...");
            }
        }
    }
}
