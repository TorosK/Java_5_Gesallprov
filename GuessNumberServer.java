import java.io.*;
import java.net.*;
import java.util.Random;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress());

            while (true) { // Server loop to allow multiple games
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    System.out.println("Client connected from " + clientSocket.getRemoteSocketAddress());

                    // Include server and connection details in the welcome message
                    String welcomeMessage = "\nServer Host: " + serverSocket.getInetAddress().getHostName() + "\n" +
                            "Server IP: " + InetAddress.getLocalHost().getHostAddress() + "\n" +
                            "Server Port: " + serverSocket.getLocalPort() + "\n" +
                            "Connected to: " + clientSocket.getLocalAddress().getHostAddress() + "\n\n" +
                            "Welcome to the 'Guess the Number' Game!\n\n" +
                            "            ________\n" +
                            "        _jgN########Ngg_\n" +
                            "      _N##N@@\"\"  \"\"9NN##Np_\n" +
                            "     d###P             N####p\n" +
                            "     \"^^\"               T####\n" +
                            "                        d###P\n" +
                            "                     _g###@F\n" +
                            "                  _gN##@P\n" +
                            "                gN###F\"\n" +
                            "               d###F\n" +
                            "              0###F\n" +
                            "              0###F\n" +
                            "              0###F\n" +
                            "              \"NN@'\n" +
                            "\n" +
                            "               ___\n" +
                            "              q###r\n" +
                            "               \"\"\n" +
                            "\n" +
                            "Please input your guessed number from 0 to 10 and press ENTER.";

                    out.println(welcomeMessage);

                    boolean gameContinue = true;
                    while (gameContinue) {
                        Random random = new Random();
                        int number = random.nextInt(11); // Generate random number between 0-10

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            if ("exit".equalsIgnoreCase(inputLine.trim())) {
                                gameContinue = false; // Exit the loop if the client wants to end the game
                                break;
                            }

                            int guess = Integer.parseInt(inputLine);
                            if (guess == number) {
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
                                        "END OF GAME!\n" +
                                        "You won! Enter 'exit' to leave or press ENTER to play again:");
                                
                                inputLine = in.readLine();
                                if ("exit".equalsIgnoreCase(inputLine.trim())) {
                                    gameContinue = false; // Exit the loop if the client wants to end the game
                                    break;
                                } else if (inputLine.trim().isEmpty()) { // Treat an empty input (just pressing ENTER) as a signal to start a new game
                                    // Implicitly continue to a new game
                                    break; // Break the inner loop to start a new game
                                }
                            } else if (guess < number) {
                                out.println("Higher");
                            } else {
                                out.println("Lower");
                            }
                        }
                    }
                }
                System.out.println("Client disconnected. Waiting for a new connection...");
            }
        }
    }
}
