package ru.gb.java2.chat.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.gb.java2.chat.client.ClientChat;
import ru.gb.java2.chat.client.dialogs.Dialogs;
import ru.gb.java2.chat.client.model.Network;
import ru.gb.java2.chat.client.model.ReadCommandListener;
import ru.gb.java2.chat.clientserver.CommandType;
import ru.gb.java2.chat.clientserver.commands.ChangeNickCommandData;

import java.io.IOException;

public class NickController {
    @FXML
    public TextField nickFiled;
    @FXML
    public Button changeNick;
    private ReadCommandListener readMessageListener;

    @FXML
    public void executeNick(ActionEvent actionEvent) throws IOException {
        String newNick = nickFiled.getText();
        Network.getInstance().sendChangeNick(newNick);
    }

    public void initMessageHandler() {
        readMessageListener = Network.getInstance().addReadMessageListener(command -> {
            if (command.getType() == CommandType.CHANGE_NICK_OK) {
                ChangeNickCommandData data = (ChangeNickCommandData) command.getData();
                String username = data.getUsername();
                Platform.runLater(() -> ClientChat.INSTANCE.switchToMainChatWindow(username));
            } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
            } else {
                Platform.runLater(Dialogs.AuthError.INVALID_CREDENTIALS::show);
            }
        });
    }

    public void close() {
        Network.getInstance().removeReadMessageListener(readMessageListener);
    }
}
