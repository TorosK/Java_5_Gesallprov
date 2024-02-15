import java.io.*;
import java.net.*;
import java.util.Random;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                
                System.out.println("Client connected");
                
                // Send a welcome message to the client
                out.println("Welcome to the Guess the Number game!\nPlease input your guessed number and press ENTER.");
                
                Random random = new Random();
                int number = random.nextInt(100) + 1; // Generate random number between 1-100
                
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    int guess = Integer.parseInt(inputLine);
                    if (guess == number) {
                        out.println("Correct!");
                        break;
                    } else if (guess < number) {
                        out.println("Higher");
                    } else {
                        out.println("Lower");
                    }
                }
            }
            System.out.println("Client disconnected");
        }
    }
}
