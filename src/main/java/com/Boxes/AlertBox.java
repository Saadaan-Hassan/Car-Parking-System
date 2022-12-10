package com.Boxes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage box = new Stage();

        box.setTitle(title);
        box.setMinWidth(300);
        box.initModality(Modality.APPLICATION_MODAL);

        Text text = new Text(message);
        text.setFont(Font.font(16));

        Button btn = new Button("OK");
        btn.setPrefWidth(80);
        btn.setPrefHeight(25);
        btn.setOnAction(e ->{
            box.close();
        });

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(text, btn);

        Scene scene = new Scene(vBox);
        box.setScene(scene);

        box.showAndWait();
    }
}
