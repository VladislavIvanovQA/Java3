package ru.gb.java2.chat.server.chat.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gb.java2.chat.server.chat.auth.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbService {
    private Logger log = LoggerFactory.getLogger(DbService.class);
    private static final String DB_URL = "jdbc:sqlite:NetworkChatServer/chat.db";
    private Connection connection;

    public DbService() throws SQLException {
        log.info("Try to connection DB: {}", DB_URL);
        connection = DriverManager.getConnection(DB_URL);
        log.info("Connection DB successes!");
    }

    public String getUsernameByLoginAndPassword(String login, String password) {
        User requiredUser = new User(login, password);
        try {
            return findUserByLoginAndPassword(requiredUser).getUsername();
        } catch (SQLException e) {
            log.error("Sql exception!", e);
        }
        return null;
    }

    public boolean updateUserNickByOldNick(String oldNick, String newNick) throws SQLException {
        return updateUserNick(oldNick, newNick) == 1;
    }

    private User findUserByLoginAndPassword(User user) throws SQLException {
        ResultSet resultSet = connection.createStatement()
                .executeQuery("SELECT * FROM user WHERE login='" + user.getLogin() + "' and password='" + user.getPassword() + "'");
        while (resultSet.next()) {
            user.setUsername(resultSet.getString("username"));
        }
        return user;
    }

    private int updateUserNick(String oldNick, String newNick) throws SQLException {
        int result = connection.createStatement()
                .executeUpdate("UPDATE user SET username='" + newNick + "' WHERE username = '" + oldNick + "'");
        log.info("Result update: {}", result);
        return result;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("Failed to close connection", e);
        }
    }
}
