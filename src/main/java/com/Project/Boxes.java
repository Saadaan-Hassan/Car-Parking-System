package com.Project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

/*  This class contains the two boxes i.e.
    1- Alert Box : To display alerts in the program
    2- Confirm Box : To ask for user confirmation in the program
* */

public class Boxes {

    /*================================== Alert Box ==================================*/
    public static void alertBox(String title, String message){
        Stage box = new Stage();

        box.setTitle(title);
        box.setMinWidth(300);
        box.initModality(Modality.APPLICATION_MODAL);

        Text text = new Text(message);
        text.setFont(Font.font(16));

        Button btn = new Button("OK");
        btn.setPrefWidth(80);
        btn.setPrefHeight(25);
        btn.setOnAction(e ->box.close());

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(text, btn);

        Scene scene = new Scene(vBox);
        box.setScene(scene);

        box.showAndWait();
    }

    //==================================================================================//

    /*================================== Confirm Box ================================== */
    public static boolean confirmBox(String title, String message){
        AtomicReference<Boolean> answer = new AtomicReference<>(false);

        Stage box = new Stage();
        box.setTitle(title);
        box.setMinWidth(300);
        box.initModality(Modality.APPLICATION_MODAL);

        Text text = new Text(message);
        text.setFont(Font.font(18));

        Button yesBtn = new Button("Yes");
        yesBtn.setPrefWidth(80);
        yesBtn.setPrefHeight(25);

        Button noBtn = new Button("No");
        noBtn.setPrefWidth(80);
        noBtn.setPrefHeight(25);

        //Yes Button Action
        yesBtn.setOnAction(e -> {
            answer.set(true);
            box.close();
        });

        //No Button Action
        noBtn.setOnAction(e -> {
            answer.set(false);
            box.close();
        });

        HBox hBox = new HBox(30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll(noBtn, yesBtn);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(text, hBox);

        Scene scene = new Scene(vBox);
        box.setScene(scene);

        box.showAndWait();

        return answer.get();
    }
}
