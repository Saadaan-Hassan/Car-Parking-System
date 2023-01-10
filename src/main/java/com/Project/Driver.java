package com.Project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Driver extends Application {

    private static Stage  window;

    public static void main(String[] args) {
        launch();
    }

    @Override

    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("Login.fxml"));
        System.out.println(fxmlLoader);
        Scene scene = new Scene(fxmlLoader.load());

        window.setMinWidth(450);
        window.setMinHeight(300);
        windowSetting();
        window.setResizable(false);

        window.setTitle("Parkers");
        window.setScene(scene);
        window.show();

        window.setOnCloseRequest(e ->{
            e.consume();
            try {
                DatabaseHandling.getCon().close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            closeProgram();
        });

    }

    public static Stage getWindow() {
        return window;
    }

    //Sets the stage according to the size of the user's screen
    public static void windowSetting(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());

        window.setMaximized(true);
        window.setResizable(true);
    }

    private void closeProgram(){
        if (Boxes.confirmBox("Exit", "Are you sure you want to exit?"))
            Platform.exit();
    }

}