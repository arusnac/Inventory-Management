package com.arusnac.project;

/**
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

public class AddProduct implements Initializable {

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
     * List to hold associated parts temporarily
     */
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    public Part newAssociatedPart;
    Product product = new Product(0,"", 0, 0, 0, 0);


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

        /**
         * Search for parts by name or id based on text input
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
    }

    /**
     * Parse, check input and add new product to inventory. Close and return to main screen after save button is clicked
     */

    public void onSaveButtonClick(){

        try {
            int id;
            if(Inventory.getAllProducts().isEmpty()){
                id = 0;
            }
            else {
                id = Inventory.getNewProductId();
            }
            String name = nameInput.getText();
            double cost = Double.parseDouble(costInput.getText());
            int stock = Integer.parseInt(inventoryInput.getText());
            int min = Integer.parseInt(minInput.getText());
            int max = Integer.parseInt(maxInput.getText());

            if (min < max) {
                product = new Product(id, name, cost, stock, min, max);
                Inventory.addProduct(product);

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

    public void onCancelButtonClick(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void displayError(){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText("Invalid Input, Please Retry");
        invalidIput.showAndWait();

    }

    public void onAddButtonClick(ActionEvent actionEvent) {
        newAssociatedPart = partTableView.getSelectionModel().getSelectedItem();
        associatedPart.add(newAssociatedPart);
        aPartTableView.setItems(associatedPart);

    }

    public void onRemoveButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm delete?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                newAssociatedPart = partTableView.getSelectionModel().getSelectedItem();
                associatedPart.remove(newAssociatedPart);
                aPartTableView.setItems(associatedPart);
            }
        });
    }


}
