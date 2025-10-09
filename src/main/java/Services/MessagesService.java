package Services;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.function.Consumer;

public class MessagesService {
    private final String host;
    private final int port;
    private final Gson gson = new Gson();
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Thread listenerThread;
    private boolean running = false;
    private final String clientId;

    // Callback to handle incoming messages
    private final Consumer<String> onMessageReceived;

    public MessagesService(String host, int port, Consumer<String> onMessageReceived) {
        this.host = host;
        this.port = port;
        this.onMessageReceived = onMessageReceived;
        this.clientId = UUID.randomUUID().toString();
    }

    public void connect() throws IOException {
        System.out.println("[MessagesService] Client ID: " + clientId);
        System.out.println("[MessagesService] Attempting to connect to " + host + ":" + port);

        socket = new Socket(host, port);
        System.out.println("[MessagesService] Connected successfully!");

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        running = true;

        // Send client ID to identify this connection
        System.out.println("[MessagesService] Sending client ID: " + clientId);
        writer.println(clientId);

        listenerThread = new Thread(this::listenLoop, "MessageClient-Listener");
        listenerThread.setDaemon(false);
        listenerThread.start();
        System.out.println("[MessagesService] Listener thread started");
    }

    private void listenLoop() {
        try {
            String line;
            System.out.println("[MessagesService] Waiting for messages...");
            while (running && (line = reader.readLine()) != null) {
                System.out.println("[MessagesService] Received raw line: " + line);
                // Pass the raw JSON string to the callback
                onMessageReceived.accept(line);
            }
            System.out.println("[MessagesService] Listen loop ended. running=" + running);
        } catch (IOException e) {
            if (running) {
                System.err.println("[MessagesService] IOException in listen loop:");
                e.printStackTrace();
            }
        } finally {
            close();
        }
    }

    public void close() {
        running = false;
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[MessagesService] Closed");
    }
}