package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Texas hold 'em");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Scene s = new Scene(root, width, height - 30);
        s.getStylesheets().addAll(getClass().getResource("main.css").toExternalForm());
        primaryStage.setFullScreenExitHint("Press F11 to toggle full-sceen mode");
        s.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.F11) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen());
            }
        });

        primaryStage.setScene(s);
        primaryStage.show();
        primaryStage.setFullScreen(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
