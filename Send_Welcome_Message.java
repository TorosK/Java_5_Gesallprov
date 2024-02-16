import java.io.*;
import java.net.*;

/**
 * The Send_Welcome_Message class provides a utility method to send a welcome message to clients.
 * This class is designed to encapsulate the functionality of sending a custom welcome message,
 * which includes server details and a greeting, to clients upon connection.
 */
public class Send_Welcome_Message {

    /**
     * Sends a custom welcome message to the client connected to the server.
     * The message includes ASCII art, server host name, server IP, server port, and the client's IP and port.
     * This method leverages the {@link Ascii_Art_Reader} to read ASCII art from a file and include it in the welcome message.
     *
     * @param out The PrintWriter object used to send messages to the client.
     * @param serverSocket The ServerSocket object representing the server.
     * @param clientSocket The Socket object representing the client connection.
     * @throws IOException If an I/O error occurs while reading the ASCII art file.
     */
    public static void sendWelcomeMessage(PrintWriter out, ServerSocket serverSocket, Socket clientSocket) throws IOException {
        // Reading ASCII art from a file to include in the welcome message.
        String questionMarkArt = Ascii_Art_Reader.readAsciiArt("ASCII_Art_Question_Mark.txt");
        
        // Constructing the welcome message with server and client details.
        String welcomeMessage = "\nServer Host: " + serverSocket.getInetAddress().getHostName() + "\n" +
                "Server IP: " + InetAddress.getLocalHost().getHostAddress() + "\n" +
                "Server Port: " + serverSocket.getLocalPort() + "\n" +
                "Connected from IP: " + clientSocket.getInetAddress().getHostAddress() + "\n" + 
                "Connected from Port: " + clientSocket.getPort() + "\n\n" +
                "Welcome to the 'Guess the Number' Game!\n\n" +
                questionMarkArt;
        
        // Sending the constructed welcome message to the client.
        out.println(welcomeMessage);
    }
}
