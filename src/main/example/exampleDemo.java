package com.example.Semester_Project;

import java.util.function.Consumer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class exampleDemo extends Application {

    @Override
    public void start(Stage primaryStage) {
        var root = new BorderPane();
        root.setTop(createMenuBar(root::setCenter));
        root.setCenter(new TitleView().getNode());

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Demo");
        primaryStage.show();
    }

    private MenuBar createMenuBar(Consumer<Node> onUpdateView) {
        var exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());

        var creditsItem = new MenuItem("Credits");
        creditsItem.setOnAction(e -> {
            e.consume();

            var view = new CreditsView();
            view.setOnGoBack(() -> onUpdateView.accept(new TitleView().getNode()));
            onUpdateView.accept(view.getNode());
        });

        return new MenuBar(
                new Menu("File", null, exitItem),
                new Menu("Extras", null, creditsItem)
        );
    }
}
