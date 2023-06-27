package com.example.semestral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static Scene scene;

    private static Stage modalDialog;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login-view"));
//        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("@stokinLogo.png")));
        stage.setMaximized(true);
        stage.setTitle("Stokin");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void showModal(String fxml) throws java.io.IOException {
        Window primaryStage = scene.getRoot().getScene().getWindow();
        Scene scene = new Scene(loadFXML(fxml));
        modalDialog = new Stage();
        modalDialog.initModality(Modality.APPLICATION_MODAL);
        modalDialog.initOwner(primaryStage);
        modalDialog.setResizable(false);
        modalDialog.setScene(scene);
        modalDialog.showAndWait();
    }

    public static void closeCurrentWindow() {
        modalDialog.close();
    }
}
