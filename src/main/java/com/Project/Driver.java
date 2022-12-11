package com.Project;

import com.Boxes.ConfirmBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    private static Stage  window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        window.setTitle("Parking System");
        window.setResizable(false);
        window.setScene(scene);
        window.show();

        window.setOnCloseRequest(e ->{
            e.consume();
            closeProgram();
        });

//        SystemController.getStage().show();
    }

    public static Stage getWindow() {
        return window;
    }

    private void closeProgram(){
        if (ConfirmBox.display("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

}