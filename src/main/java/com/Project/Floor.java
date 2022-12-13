package com.Project;

import com.Boxes.AlertBox;
import com.Boxes.ConfirmBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Floor implements Serializable {
    @Serial
    private static final long serialVersionUID = 733648599315947096L;
    private final String floorName;
    private final Slots [] slots;
    private final int noOfSlots;

    private int id;

    //Constructor
    Floor(String floorName, int slotsNumber) {
        SecureRandom random = new SecureRandom();
        ArrayList<Floor> floors = FileHandling.readFromFile(Files.getFloorFile());
        int ran = random.nextInt(1,1000);

        for (Floor f :
                floors) {
            if (ran != f.id) {
                id = ran;
            }

        }

        this.floorName = floorName;
        slots = new Slots[slotsNumber];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slots();
        }

        this.noOfSlots = slotsNumber;
    }

    //Add Floor Function
    public static void addFloor(Floor newFloor, TableView<Floor> table){
        FileHandling.appendToFile(Files.getFloorFile(), newFloor);

        AlertBox.display("", "New floor Added Successfully");

        table.getItems().add(newFloor);
    }

    //Show Floor Functions
    public static ScrollPane showFloor(int index){
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));
        ArrayList<Floor> floor = FileHandling.readFromFile(Files.getFloorFile());

        double numberOfSlots = floor.get(index).getSlots().length;
        double columns = 8;
        double rows = Math.ceil(numberOfSlots/columns);

        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns && count <= numberOfSlots; j++) {
                VBox vBox = new VBox(15);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(25));
                String status;

                if (floor.get(index).getSlots()[j].isReserved()){
                    status = "Reserved";
                    vBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else{
                    status = "Available";
                    vBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                Text tStatus = new Text(status);
                tStatus.setFill(Color.WHITE);
                Text tSlotNumber = new Text(String.format("Slot-%d",count));
                tSlotNumber.setFill(Color.WHITE);
                vBox.getChildren().addAll(tStatus,tSlotNumber);
                grid.add(vBox,j,i);
                count++;
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);
        return scrollPane;
    }

    public static ObservableList<Floor> showFloor() {
        ArrayList<Floor> floorsArray = FileHandling.readFromFile(Files.getFloorFile());
        return FXCollections.observableList(floorsArray);
    }

    //Edit Floor Function
    public static void editFloor(TableView<Floor> table, Pagination pagination, Floor editFloor){
        ArrayList<Floor> floorsArray = FileHandling.readFromFile(Files.getFloorFile());

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(100);
        stage.setY(100);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        //Id Area
        Label labelId = new Label("ID");
        grid.add(labelId, 0,0);

        Text tId = new Text();
        tId.setText(Integer.toString(editFloor.id));
        grid.add(tId, 1,0);

        //Name Area
        Label labelName = new Label("Floor Name");
        grid.add(labelName, 0,1);

        TextField tfName = new TextField();
        tfName.setPromptText("Enter Floor Name");
        tfName.setText(editFloor.floorName);
        grid.add(tfName, 1,1);

        //Slots Area
        Label labelSlots = new Label("Number of Slots");
        grid.add(labelSlots, 0,2);

        TextField tfSlots = new TextField();
        tfSlots.setPromptText("Enter Number of Slots");
        tfSlots.setText(Integer.toString(editFloor.noOfSlots));
        grid.add(tfSlots, 1,2);

        //Button
        Button btn = new Button("OK");
        btn.setPrefWidth(110);
        btn.setPrefHeight(30);
        HBox hBox = new HBox(btn);
        hBox.setAlignment(Pos.CENTER);

        grid.add(hBox, 0,3, 2,1);

        btn.setOnAction(e ->{
        if (ConfirmBox.display("Edit Floor", "Do you want to save changes?")) {
            File file = new File(Files.getFloorFile());
            file.delete();

            for (Floor f :
                    floorsArray) {
                if (f.id == Integer.parseInt(tId.getText())) {
                    FileHandling.writeToFile(Files.getFloorFile(), new Floor(tfName.getText(), Integer.parseInt(tfSlots.getText())));
                } else
                    FileHandling.writeToFile(Files.getFloorFile(), f);
            }

            table.getItems().clear();
            table.setItems(Floor.showFloor());

            Slots.showSlots(pagination);
        }
        });

        Scene scene = new Scene(grid, 330, 150);

        stage.setTitle("Delete Floor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

    }

    //Delete floor Function
    public static void delFloor(TableView<Floor> table, Pagination pagination){
        ArrayList<Floor> floorsArray = FileHandling.readFromFile(Files.getFloorFile());

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(100);
        stage.setY(100);

        Label label = new Label("ID");

        TextField textField = new TextField();
        textField.setPromptText("Enter the ID");

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label,textField);

        Button btn = new Button("OK");
        btn.setPrefWidth(110);
        btn.setPrefHeight(30);
        btn.setOnAction(e->{
            if (ConfirmBox.display("Delete Floor", "Are you sure you want to delete Floor?")) {
                File file = new File(Files.getFloorFile());
                file.delete();

                for (Floor f :
                        floorsArray) {
                    if (!(f.id == Integer.parseInt(textField.getText()))) {
                        FileHandling.writeToFile(Files.getFloorFile(), f);
                    }
                }

                textField.clear();

                table.getItems().clear();
                table.setItems(Floor.showFloor());
                Slots.showSlots(pagination);
            }
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(hBox, btn);

        Scene scene = new Scene(vBox, 300, 150);

        stage.setTitle("Delete Floor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }


    public String getFloorName() {
        return floorName;
    }

    public int getId() {
        return id;
    }

    public Slots[] getSlots() {
        return slots;
    }

    public int getNoOfSlots() {
        return noOfSlots;
    }

    @Override
    public String toString() {
        return String.format("%d %s %d", id, floorName, slots.length);
    }
}
