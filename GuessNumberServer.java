import java.io.*;
import java.net.*;
import java.util.Random;

public class GuessNumberServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Server Host: " + serverSocket.getInetAddress().getHostName()); // Display the server host information

            while (true) { // Server loop to allow multiple games
                Socket clientSocket = serverSocket.accept(); // Accept the client connection here

                // Display detailed client connection information
                InetSocketAddress remoteSocketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                String clientIP = remoteSocketAddress.getAddress().getHostAddress();
                int clientPort = remoteSocketAddress.getPort();
                System.out.println("Client connected - IP: " + clientIP + ", Port: " + clientPort);

                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    // Include server and connection details in the welcome message
                    String welcomeMessage = "\nServer Host: " + serverSocket.getInetAddress().getHostName() + "\n" +
                            "Server IP: " + InetAddress.getLocalHost().getHostAddress() + "\n" +
                            "Server Port: " + serverSocket.getLocalPort() + "\n" +
                            "Connected from IP: " + clientSocket.getInetAddress().getHostAddress() + "\n" + // get client IP
                            "Connected from Port: " + clientSocket.getPort() + "\n\n" + // get client port
                            "Welcome to the 'Guess the Number' Game!\n\n" +
                             // ASCII Art
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
                        int number = random.nextInt(11); // Generate a new random number for each new game
                    
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
                                    gameContinue = false; // End the game if the player chooses to exit
                                    break;
                                } else if (inputLine.trim().isEmpty()) { // Start a new game if the player presses ENTER
                                    // No need to do anything here, the loop will continue and a new number will be generated
                                }
                            } else if (guess < number) {
                                out.println("Higher");
                            } else {
                                out.println("Lower");
                            }
                        }
                    }                          
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
                    System.out.println(e.getMessage());
                }
                System.out.println("Client disconnected. Waiting for a new connection..."); // This line should be here, inside the while loop but outside the try-catch block
            }
        }
    }
}