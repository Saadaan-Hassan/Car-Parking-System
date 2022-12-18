package com.Project;

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
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Floor implements Serializable {
    @Serial
    private static final long serialVersionUID = 733648599315947096L;
    private final String floorName;
    private final ArrayList<Slots> slots;
    private final int noOfSlots;

    private int id;

    Floor(int id, String floorName, int slotsNumber) {

        this.id = id;

        this.floorName = floorName;
        slots = new ArrayList<>(slotsNumber);
        for (int i = 0; i < slotsNumber; i++) {
            slots.add(new Slots());
        }

        this.noOfSlots = slotsNumber;
    }

    //Constructor
    Floor(String floorName, int slotsNumber) {
        SecureRandom random = new SecureRandom();
        ArrayList<Floor> floors = FileHandling.readFromFile(Files.getFloorFile());
        int ran = random.nextInt(1,10000);

        for (Floor f :
                floors) {
            if (ran != f.id) {
                id = ran;
            }

        }

        this.floorName = floorName;
        slots = new ArrayList<>(slotsNumber);
        for (int i = 0; i < slotsNumber; i++) {
            slots.add(new Slots());
        }

        this.noOfSlots = slotsNumber;
    }

    //Add Floor Function
    public static void addFloor(Floor newFloor, TableView<Floor> table, ComboBox<String> cbFloor){
        FileHandling.appendToFile(Files.getFloorFile(), newFloor);

//        FileHandling.writeToFile(Files.getSlotsFile(), newFloor.getSlots());

        Boxes.alertBox("", "New floor Added Successfully");

        cbFloor.getItems().add(newFloor.getFloorName());
        table.getItems().add(newFloor);
    }

    /*Show Floor Functions
    * This Function returns the Scroll Pane which shows the parking slots.
    * If a slot is reserved, then it is shown with red color.
    * If a slot is available, then it is shown with green color.
    * */
    public static ScrollPane showFloor(int index){
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 20, 10));

        //Reads the floors Slots from File Data file
        ArrayList<Floor> floor = FileHandling.readFromFile(Files.getFloorFile());
        ArrayList<Slots> slots = floor.get(index).getSlots();

        //Stores the number of slots of the selected floor
        double numberOfSlots = floor.get(index).getSlots().size();

        // Columns of grid pane
        double columns = 8;

        //Rows for grid pane
        double rows = Math.ceil(numberOfSlots/columns);

        //The below code is showing the slots on the screen. If a slot is reserved then it is shown in "Red Color" otherwise in "Green Color"
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns && count < numberOfSlots; j++) {
                //Vbox is used to show slot on screen.
                VBox vBox = new VBox(15);
                vBox.setAlignment(Pos.CENTER);
                vBox.setPadding(new Insets(25));

                String status;  //It is used to store status of slots i.e. reserved/available.

                if (slots.get(count).isReserved()){
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

            if (tfName.getText().isEmpty() || tfSlots.getText().isEmpty()){
                Boxes.alertBox("Empty Field", "Enter the ID!");
            }
            else {
                ArrayList<Floor> floorsArray = FileHandling.readFromFile(Files.getFloorFile());
                if (Boxes.confirmBox("Edit Floor", "Do you want to save changes?")) {
                    File file = new File(Files.getFloorFile());
                    file.delete();

                    for (Floor f :
                            floorsArray) {
                        if (f.id == Integer.parseInt(tId.getText())) {
                            FileHandling.writeToFile(Files.getFloorFile(), new Floor(f.id,tfName.getText(), Integer.parseInt(tfSlots.getText())));
                        } else
                            FileHandling.writeToFile(Files.getFloorFile(), f);
                    }

                    table.getItems().clear();
                    table.setItems(Floor.showFloor());

                    Slots.showSlots(pagination);
                }
            }
        });

        Scene scene = new Scene(grid, 330, 150);

        stage.setTitle("Edit Floor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

    }

    //Delete floor Function
    public static void delFloor(TableView<Floor> table, Pagination pagination){
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
            ArrayList<Floor> floorsArray = FileHandling.readFromFile(Files.getFloorFile());

            if (textField.getText().isEmpty()){
                Boxes.alertBox("Empty Field", "Enter the ID!");
            }
            else {
                if (floorsArray.size() == 1)
                    Boxes.alertBox("", "At least one Floor is required!");
                else {
                    if (Boxes.confirmBox("Delete Floor", "Are you sure you want to delete Floor?")) {
                        File file = new File(Files.getFloorFile());
                        file.delete();

                        for (Floor f :
                                floorsArray) {
                            if (!(f.id == Integer.parseInt(textField.getText()))) {
                                FileHandling.writeToFile(Files.getFloorFile(), f);
                            }
                        }


                        if (!(file.exists())) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                        textField.clear();
                        table.getItems().clear();
                        table.setItems(Floor.showFloor());
                        Slots.showSlots(pagination);
                    }
                }
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

//    public Slots[] getSlots() {
//        return slots;
//    }


    public ArrayList<Slots> getSlots() {
        return slots;
    }

    public int getNoOfSlots() {
        return slots.size();
    }

    @Override
    public String toString() {
        return String.format("%d %s %d", id, floorName, slots.size());
    }
}
