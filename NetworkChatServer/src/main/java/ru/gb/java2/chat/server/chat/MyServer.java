package ru.gb.java2.chat.server.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.server.chat.db.DbService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private Logger log = LoggerFactory.getLogger(MyServer.class);
    private final List<ClientHandler> clients = new ArrayList<>();
    private DbService dbService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server has been started");
            dbService = new DbService();
            while (true) {
                waitAndProcessNewClientConnection(serverSocket);
            }
        } catch (IOException e) {
            log.error("Failed to bind port " + port, e);
        } catch (SQLException e) {
            log.error("Failed to connect DB", e);
        }
        finally {
            if (dbService != null) {
                dbService.closeConnection();
            }
        }
    }

    private void waitAndProcessNewClientConnection(ServerSocket serverSocket) throws IOException {
        log.info("Waiting for new client connection...");
        Socket clientSocket = serverSocket.accept();
        log.info("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), message));
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        notifyClientsUsersListUpdated();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        notifyClientsUsersListUpdated();
    }

    public DbService getDbService() {
        return dbService;
    }

    public synchronized boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender && client.getUsername().equals(recipient)) {
                client.sendCommand(Command.clientMessageCommand(sender.getUsername() + " личное", privateMessage));
                break;
            }
        }
    }

    protected void notifyClientsUsersListUpdated() throws IOException {
        List<String> users = new ArrayList<>();
        for (ClientHandler client : clients) {
            users.add(client.getUsername());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUsersListCommand(users));
        }

    }
}
