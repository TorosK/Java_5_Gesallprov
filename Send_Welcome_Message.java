import java.io.*;
import java.net.*;

public class Send_Welcome_Message {
    public static void sendWelcomeMessage(PrintWriter out, ServerSocket serverSocket, Socket clientSocket) throws IOException {
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
}
