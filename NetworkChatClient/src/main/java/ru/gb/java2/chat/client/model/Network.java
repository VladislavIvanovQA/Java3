package ru.gb.java2.chat.client.model;

import ru.gb.java2.chat.clientserver.Command;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Network {

    private static final int SERVER_PORT = 8189;
    private static final String SERVER_HOST = "localhost";

    private static Network INSTANCE;

    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;

    private List<ReadCommandListener> listeners = new CopyOnWriteArrayList<>();
    private Thread readMessageProcess;
    private boolean connected;
    public static String username;
    private String filename = "history_";
    private String filetype = ".txt";

    public static Network getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Network();
        }

        return INSTANCE;
    }

    private Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Network() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            socketInput = new ObjectInputStream(socket.getInputStream());
            readMessageProcess = startReadMessageProcess();
            connected = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to establish connection");
            return false;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    private Thread startReadMessageProcess() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Command command = readCommand();
                    if (command == null) {
                        continue;
                    }
                    for (ReadCommandListener messageListener : listeners) {
                        messageListener.processReceivedCommand(command);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to read message from server");
                    close();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) socketInput.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read Command class");
            e.printStackTrace();
        }
        return command;
    }

    public void sendPrivateMessage(String recipient, String message) throws IOException {
        sendCommand(Command.privateMessageCommand(recipient, message));
    }

    public void sendMessage(String message) throws IOException {
        sendCommand(Command.publicMessageCommand(message));
    }

    public void sendAuthMessage(String login, String password) throws IOException {
        sendCommand(Command.authCommand(login, password));
    }

    public void sendChangeNick(String newNick) throws IOException {
        sendCommand(Command.updateNickCommand(newNick));
    }

    private void sendCommand(Command command) throws IOException {
        try {
            System.out.println("Client: " + command);
            socketOutput.writeObject(command);
        } catch (IOException e) {
            System.err.println("Failed to send message to server");
            throw e;
        }
    }

    public ReadCommandListener addReadMessageListener(ReadCommandListener listener) {
        listeners.add(listener);
        return listener;
    }

    public void removeReadMessageListener(ReadCommandListener listener) {
        listeners.remove(listener);
    }

    public String readMessage(String username) {
        System.out.println("USERNAME: " + username);
        File file = new File(filename + username + filetype);
        System.out.println("FILE: " + file);
        System.out.println();
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader in = new InputStreamReader(new FileInputStream(file))) {
            int x;
            while ((x = in.read()) > -1) {
                builder.append((char) x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void saveMessages(String message) {
        File file = new File(filename + username + filetype);
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file))) {
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connected = false;
            readMessageProcess.interrupt();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
