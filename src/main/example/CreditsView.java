package com.example.Semester_Project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class CreditsView {

    private Runnable onGoBack;
    private Node node;

    public Node getNode() {
        if (node == null) {
            var stack = new StackPane();
            stack.setPadding(new Insets(10));

            var label = new Label("Credits View");
            stack.getChildren().add(label);

            var goBackBtn = new Button("Go back");
            goBackBtn.setOnAction(e -> {
                e.consume();
                if (onGoBack != null) {
                    onGoBack.run();
                }
            });
            stack.getChildren().add(goBackBtn);
            StackPane.setAlignment(goBackBtn, Pos.TOP_LEFT);

            node = stack;
        }
        return node;
    }

    public void setOnGoBack(Runnable action) {
        onGoBack = action;
    }
}
