package com.example.Semester_Project;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class TitleView {

    private Node node;

    public Node getNode() {
        if (node == null) {
            node = new StackPane(new Label("Welcome!"));
        }
        return node;
    }
}