package com.arusnac.project;

/**
 * Modify product form controller
 * @author Andrew Rusnac
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProduct implements Initializable {
    public TextField idInput;
    public TextField nameInput;
    public TextField inventoryInput;
    public TextField costInput;
    public TextField maxInput;
    public TextField minInput;
    @FXML
    public Button saveButton;

    public TableView<Part> partTableView;
    @FXML
    public TableColumn<Product, Integer> partID;
    @FXML
    public TableColumn<Product, String> partName;
    @FXML
    public TableColumn<Product, Integer> partInventory;
    @FXML
    public TableColumn<Product, Double> partCost;
    @FXML
    public TableView<Part> aPartTableView;
    @FXML
    public TableColumn<Product, Integer> aPartID;
    @FXML
    public TableColumn<Product, String> aPartName;
    @FXML
    public TableColumn<Product, Integer> aPartInventory;
    @FXML
    public TableColumn<Product, Double> aPartCost;
    @FXML
    public Button cancelButton;
    @FXML
    public TextField partSearch;

    /**
     * Temporary list to hold associated parts
     */
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    public Part newAssociatedPart;
    Product product = new Product(0,"", 0, 0, 0, 0);

    Product toModify;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView.setItems(Inventory.getAllParts());

        aPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        toModify = MainFormController.getProduct();
        idInput.setText(String.valueOf(toModify.getId()));
        nameInput.setText(toModify.getName());
        inventoryInput.setText(String.valueOf(toModify.getStock()));
        costInput.setText(String.valueOf(toModify.getPrice()));
        minInput.setText(String.valueOf(toModify.getMin()));
        maxInput.setText(String.valueOf(toModify.getMax()));
        aPartTableView.setItems(toModify.getAssociatedParts());

        /**
         * Search for parts by name or id based on input text
         */
        partSearch.setOnAction(e -> {
            String searchKey = partSearch.getText();

            ObservableList<Part> foundParts = FXCollections.observableArrayList();

            for (Part part : Inventory.getAllParts()){
                if(part.getName().contains(searchKey) || String.valueOf(part.getId()).contains(searchKey)){
                    foundParts.add(part);
                }
            }

            partTableView.setItems(foundParts);
            if (foundParts.isEmpty()) {
                MainFormController.displaySearchError();
            }

            if (partSearch.getText().isEmpty()){
                partTableView.setItems(Inventory.getAllParts());
            }

        });

        /**
         * update associated lists to match currently selected product
         */
        if(toModify.getAssociatedParts() != null) {
            associatedPart = toModify.getAssociatedParts();
        }

    }

    /**
     * Parse, check input and update modified product to inventory. Close and return to main screen after save button is clicked
     */
    public void onSaveButtonClick(){

        try {
            String name = nameInput.getText();
            double cost = Double.parseDouble(costInput.getText());
            int stock = Integer.parseInt(inventoryInput.getText());
            int min = Integer.parseInt(minInput.getText());
            int max = Integer.parseInt(maxInput.getText());

            if (min < max) {
                product = new Product(toModify.getId(), name, cost, stock, min, max);
                Inventory.updateProduct(toModify.getId(), product);
            }

            else {
                displayError();
            }

            for(Part part : associatedPart){
                product.addAssociatedPart(part);
            }


        }catch(Exception e){
            displayError();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Exit screen and return to main form
     */
    public void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Error to display on invalid input
     */
    public void displayError(){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText("Invalid Input, Please Retry");
        invalidIput.showAndWait();

    }

    /**
     * Add new associated part to associated part list and display
     *
     */
    public void onAddButtonClick(ActionEvent actionEvent) {
        newAssociatedPart = partTableView.getSelectionModel().getSelectedItem();
        associatedPart.add(newAssociatedPart);
        aPartTableView.setItems(associatedPart);

    }

    /**
     *Remove the associated part
     */
    public void onRemoveButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm delete?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                newAssociatedPart = aPartTableView.getSelectionModel().getSelectedItem();
                associatedPart.remove(newAssociatedPart);
                aPartTableView.setItems(associatedPart);
            }
        });
    }

}