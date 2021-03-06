package ru.gb.java2.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.gb.java2.chat.client.controllers.AuthController;
import ru.gb.java2.chat.client.controllers.ChatController;
import ru.gb.java2.chat.client.controllers.NickController;
import ru.gb.java2.chat.client.model.Network;

import java.io.IOException;


public class ClientChat extends Application {
    public static ClientChat INSTANCE;

    private static final String CHAT_WINDOW_FXML = "chat.fxml";
    private static final String AUTH_DIALOG_FXML = "authDialog.fxml";
    private static final String NICK_DIALOG_FXML = "nickDialog.fxml";

    private Stage primaryStage;
    private Stage authStage;
    private Stage nickStage;
    private FXMLLoader chatWindowLoader;
    private FXMLLoader authLoader;
    private FXMLLoader nickLoader;


    @Override
    public void init() {
        INSTANCE = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initViews();
        getChatStage().show();
        getAuthStage().show();
        getAuthController().initMessageHandler();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getNickStage() {
        return nickStage;
    }

    private AuthController getAuthController() {
        return authLoader.getController();
    }

    public ChatController getChatController() {
        return chatWindowLoader.getController();
    }

    public NickController getNickController() {
        return nickLoader.getController();
    }

    private void initViews() throws IOException {
        initChatWindow();
        initAuthDialog();
        initNickDialog();
    }

    private void initChatWindow() throws IOException {
        chatWindowLoader = new FXMLLoader();
        chatWindowLoader.setLocation(ClientChat.class.getResource(CHAT_WINDOW_FXML));

        Parent root = chatWindowLoader.load();
        this.primaryStage.setScene(new Scene(root));

        setStageForSecondScreen(primaryStage);
    }

    private void initAuthDialog() throws java.io.IOException {
        authLoader = new FXMLLoader();
        authLoader.setLocation(ClientChat.class.getResource(AUTH_DIALOG_FXML));
        Parent authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(primaryStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));
    }

    public void initNickDialog() throws java.io.IOException {
        nickLoader = new FXMLLoader();
        nickLoader.setLocation(ClientChat.class.getResource(NICK_DIALOG_FXML));
        Parent authDialogPanel = nickLoader.load();

        nickStage = new Stage();
        nickStage.initOwner(primaryStage);
        nickStage.initModality(Modality.WINDOW_MODAL);
        nickStage.setScene(new Scene(authDialogPanel));
    }


    private void setStageForSecondScreen(Stage primaryStage) {
        Screen secondScreen = getSecondScreen();
        Rectangle2D bounds = secondScreen.getBounds();
        primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - 300) / 2);
        primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - 200) / 2);
    }

    private Screen getSecondScreen() {
        for (Screen screen : Screen.getScreens()) {
            if (!screen.equals(Screen.getPrimary())) {
                return screen;
            }
        }
        return Screen.getPrimary();
    }

    @Override
    public void stop() throws Exception {
        Network.getInstance().saveMessages(getChatController().getChatHistory());
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getChatStage() {
        return primaryStage;
    }

    public void switchToMainChatWindow(String username) {
        getNickController().close();
        Network.username = username;
        getPrimaryStage().setTitle(username);
        getChatController().initMessageHandler();
        getAuthController().close();
        getNickStage().close();
        getAuthStage().close();
    }
}
