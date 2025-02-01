import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1"; // Local server address
    private static final int SERVER_PORT = 12346;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        try {
            new ChatClient().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        // Connect to the server
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Start a thread to listen for incoming messages from the server
        new Thread(this::listenForMessages).start();

        // Reading input from the user and sending it to the server
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while ((message = userInput.readLine()) != null) {
            out.println(message); // Send message to the server
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message); // Display received message
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
