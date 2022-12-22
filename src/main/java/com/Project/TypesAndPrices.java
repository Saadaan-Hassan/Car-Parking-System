package com.Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;

public class TypesAndPrices implements Serializable {
    @Serial
    private static final long serialVersionUID = 23492012L;
    private int id;
    private final String vehicleType;
    private final double pricePerHour;

    /*======================================== Constructors ========================================*/
    public TypesAndPrices(int id, String vehicleType, double pricePerHour) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.pricePerHour = pricePerHour;
    }

    public TypesAndPrices(String vehicleType, double pricePerHour) {
        SecureRandom random = new SecureRandom();
        ArrayList<TypesAndPrices> typesAndPrices = FileHandling.readFromFile(Files.getTypesAndPricesFile());
        int ran = random.nextInt(1,500);

        for (TypesAndPrices tp :
                typesAndPrices) {
            if (ran!= tp.id)
                id = ran;

        }
        this.vehicleType = vehicleType;
        this.pricePerHour = pricePerHour;
    }

    /*========================================= Add TypesAndPrices ==========================================*/
    public static void addTypesAndPrices(TypesAndPrices newTypeAndPrice, TableView<TypesAndPrices> table, ComboBox<String> cbVehicleType) {
        //Write new User data on TypesAndPrice
        FileHandling.writeToFile(Files.getTypesAndPricesFile(), newTypeAndPrice);

        //Add new Floor to Combo Box of Vehicle Entry Pane
        cbVehicleType.getItems().add(newTypeAndPrice.getVehicleType());

        //Add new Floor to Table of Floor Pane
        table.getItems().add(newTypeAndPrice);

        Boxes.alertBox("", "New Vehicle Type And Price Added Successfully");
    }

    //=======================================================================================================//

    /*========================================= Show TypesAndPrices =========================================*/

    //Reads all the TypesAndPrices from the TypesAndPricesData.ser and return them as ObservableList
    public static ObservableList<TypesAndPrices> showTypesAndPrices() {
        ArrayList<TypesAndPrices> typesAndPrices = FileHandling.readFromFile(Files.getTypesAndPricesFile());
        return FXCollections.observableList(typesAndPrices);
    }

    //=======================================================================================================//

    /*========================================= Edit TypesAndPrices =========================================*/

    public static void editTypesAndPrices(TableView<TypesAndPrices> table, TypesAndPrices editTypesAndPrices){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setX(100);
        stage.setY(100);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        //----- Id Area -----
        Label labelId = new Label("ID");
        grid.add(labelId, 0,0);

        Text tId = new Text();
        tId.setText(Integer.toString(editTypesAndPrices.id));
        grid.add(tId, 1,0);

        //----- Vehicle Type Area -----
        Label labelType = new Label("Vehicle Type");
        grid.add(labelType, 0,1);

        TextField tfType = new TextField();
        tfType.setPromptText("Enter Vehicle");
        tfType.setText(editTypesAndPrices.vehicleType);
        grid.add(tfType, 1,1);

        //----- Price Per Hour Area -----
        Label labelPrice = new Label("Price Per Hour");
        grid.add(labelPrice, 0,2);

        TextField tfPrice = new TextField();
        tfPrice.setPromptText("Enter Password");
        tfPrice.setText(Double.toString(editTypesAndPrices.pricePerHour));
        grid.add(tfPrice, 1,2);

        //----- Button -----
        Button btn = new Button("OK");
        btn.setPrefWidth(110);
        btn.setPrefHeight(30);
        HBox hBox = new HBox(btn);
        hBox.setAlignment(Pos.CENTER);

        grid.add(hBox, 0,3, 2,1);

        btn.setOnAction(e ->{

            if (tfType.getText().isEmpty() || tfPrice.getText().isEmpty()){
                Boxes.alertBox("Empty Fields", "Fields are empty!");
            }
            else {

                if (Boxes.confirmBox("Edit Vehicle Type And Price", "Do you want to save changes?")) {
                    //Reading the TypesAndPrices data from TypesAndPricesData.ser
                    ArrayList<TypesAndPrices> typesAndPrices = FileHandling.readFromFile(Files.getTypesAndPricesFile());

                    File file = new File(Files.getTypesAndPricesFile());
                    file.delete();

                    for (TypesAndPrices tp :
                            typesAndPrices) {
                        if (tp.id == Integer.parseInt(tId.getText())) {
                            FileHandling.writeToFile(Files.getTypesAndPricesFile(), new TypesAndPrices(tp.id, tfType.getText(), Double.parseDouble(tfPrice.getText())));
                        } else
                            FileHandling.writeToFile(Files.getTypesAndPricesFile(), tp);
                    }

                    //Updating the Types And Prices Table in Types And Prices Pane
                    table.getItems().clear();
                    table.setItems(TypesAndPrices.showTypesAndPrices());
                }
            }
        });

        Scene scene = new Scene(grid, 330, 200);

        stage.setTitle("Edit Vehicle Type And Price");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

    }

    //=======================================================================================================//

    /*======================================== Delete TypesAndPrices ========================================*/
    public static void delTypesAndPrices(TableView<TypesAndPrices> table, ComboBox<String> cbVehicleType){
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

            if (textField.getText().isEmpty()){
                Boxes.alertBox("Empty Field", "Enter the ID!");
            }
            else {
                //Reading the users data from TypesAndPricesData.ser
                ArrayList<TypesAndPrices> typesAndPrices = FileHandling.readFromFile(Files.getTypesAndPricesFile());

                if (validateId(typesAndPrices, Integer.parseInt(textField.getText()))) {
                    if (typesAndPrices.size() == 1) {
                        Boxes.alertBox("", "At least one Type is required!");
                    } else {
                        if (Boxes.confirmBox("Delete Type And Price", "Are you sure you want to delete Vehicle Type and Price?")) {


                            File file = new File(Files.getTypesAndPricesFile());
                            file.delete();

                            for (TypesAndPrices tp :
                                    typesAndPrices) {
                                if (tp.id == Integer.parseInt(textField.getText()))
                                    //Removing the Type from the ComboBox in Vehicle Type And Price Pane
                                    cbVehicleType.getItems().remove(tp.vehicleType);

                                if (!(tp.id == Integer.parseInt(textField.getText())))
                                    FileHandling.writeToFile(Files.getTypesAndPricesFile(), tp);
                            }


                            if (!(file.exists())) {
                                try {
                                    file.createNewFile();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            //Clearing the text field
                            textField.clear();

                            //Updating the User table in User Pane
                            table.getItems().clear();
                            table.setItems(TypesAndPrices.showTypesAndPrices());
                        }
                    }
                }else
                    Boxes.alertBox("Delete Vehicle Types And Price", "Invalid ID!");
            }
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(hBox, btn);

        Scene scene = new Scene(vBox, 300, 150);

        stage.setTitle("Delete User");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }

    private static boolean validateId(ArrayList<TypesAndPrices> typesAndPricesArrayList, Integer id) {
        boolean status = false;

        //Checking if the entered id is valid or not
        for (TypesAndPrices tp : typesAndPricesArrayList) {
            if (tp.id == id) {
                status = true;
                break;
            }
        }
        return status;
    }

    //==============================================================================================//

    /*========================================== Getters ===========================================*/

    public int getId() {
        return id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    @Override
    public String toString() {
        return String.format("%d %s %.2f", id,vehicleType,pricePerHour);
    }
}
