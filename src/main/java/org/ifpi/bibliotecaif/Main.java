package org.ifpi.bibliotecaif;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.ifpi.bibliotecaif.factory.DbConnectionFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    private static Scene mainScene;
    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlLocation = getClass().getResource("MainView.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            ScrollPane scrollPane = loader.load();
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Biblioteca IFPI");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
