package com.arusnac.project;

/**
 * @author Andrew Rusnac
 */

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    @FXML
    Button exitButton;
    @FXML
    Button modifyButton;
    @FXML
    Button addPart;

    @FXML
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
    public TableView<Product> productTableView;
    @FXML
    public TableColumn<Product, Integer> productID;
    @FXML
    public TableColumn<Product, String> productName;
    @FXML
    public TableColumn<Product, Integer> productInventory;
    @FXML
    public TableColumn<Product, Double> productCost;
    @FXML
    public TextField partSearch;
    @FXML
    public TextField productSearch;

    private static Product modifyProduct;
    private static Part modifyPart;

    /**
     * Exit the application
     */
    public void onExitButtonClick() {

        exitButton.getId();
        System.exit(0);
    }

    /**
     * Set the product to modify and open modify screen
     *
     * @throws IOException
     */
    public void onModifyProductClick(ActionEvent action) throws IOException {
        modifyProduct = productTableView.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("modify-product.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("modify-product");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Set the part to modify and open modify screen
     *
     * @throws IOException
     */
    public void onModifyPartClick(ActionEvent action) throws IOException {
        modifyPart = partTableView.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("modify-part.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Open screen to add part
     *
     * @throws IOException
     */
    public void openAddPart(ActionEvent action) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-part.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * open screen to add product
     *
     * @throws IOException
     */
    public void openAddProduct(ActionEvent action) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-product.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //parts
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        //products
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * SAMPLE DATA FOR TESTING
         *
        Part samplePart = new Inhouse(0, "Sample Part", 1, 1, 4, 10, 1);
        Part samplePart2 = new Inhouse(1, "Sample Part two", 5.50, 11, 1, 50, 2);
        Part samplePart3 = new Outsourced(2, "Sample Part three", 1.25, 15, 2, 75, "Company");

        Product sampleProduct = new Product(0, "Sample Product 1", 1, 1, 4, 10);
        Product sampleProduct2 = new Product(1, "Sample Product 2", 5, 4, 2, 30);
        Product sampleProduct3 = new Product(2, "Sample Product 3", 11, 11, 3, 50);

        Inventory.addPart(samplePart);
        Inventory.addPart(samplePart2);
        Inventory.addPart(samplePart3);

        Inventory.addProduct(sampleProduct);
        Inventory.addProduct(sampleProduct2);
        Inventory.addProduct(sampleProduct3);
         *
         * */

        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());


        /**
         * Search for parts based on ID or Name from text input
         */
        partSearch.setOnAction(e -> {
            String searchKey = partSearch.getText();

            ObservableList<Part> foundParts = FXCollections.observableArrayList();

            for (Part part : Inventory.getAllParts()){
                if(part.getName().contains(searchKey) || String.valueOf(part.getId()).contains(searchKey)) {
                    foundParts.add(part);
                }

            }

            partTableView.setItems(foundParts);
            if (foundParts.isEmpty()){
                displaySearchError();
            }


            if (partSearch.getText().isEmpty()){
                partTableView.setItems(Inventory.getAllParts());
            }

            });

        /**
         * Search for products based on ID or name from text input
         */
        productSearch.setOnAction(e -> {
            String searchKey = productSearch.getText();

            ObservableList<Product> foundProducts = FXCollections.observableArrayList();

            for (Product product : Inventory.getAllProducts()){
                if(product.getName().contains(searchKey) || String.valueOf(product.getId()).contains(searchKey)){
                    foundProducts.add(product);
                }
            }

            productTableView.setItems(foundProducts);
            if (foundProducts.isEmpty()) {
                displaySearchError();
            }

            if(productSearch.getText().isEmpty()){
                productTableView.setItems(Inventory.getAllProducts());
            }
        });

    }

    /**
     * Returns the product that was selected
     * @return modifyProduct
     */
    public static Product getProduct(){
        return modifyProduct;
    }

    /**
     * Returns the part that was selected
     * @return
     */
    public static Part getPart(){
        return modifyPart;
    }

    /**
     * Confirm and delete selected product
     */
    public void onDeleteClickProduct() {
        Product toDeleteProduct = productTableView.getSelectionModel().getSelectedItem();

        if(toDeleteProduct == null){
            displayFailedDelete();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirm delete?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Inventory.removeProduct(toDeleteProduct);
                }
            });
        }
    }

    /**
     * Confirm and delete selected part
     */
    public void onDeleteClickPart() {
        Part toDelete = partTableView.getSelectionModel().getSelectedItem();

        if(toDelete == null){
            displayFailedDelete();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirm delete?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Inventory.removePart(toDelete);
                }
            });
        }
    }

    /**
     * Display error when search results return empty
     */
    public static void displaySearchError(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Issue");
        alert.setHeaderText("Nothing found to match your search");
        alert.showAndWait();
    }

    public static void displayFailedDelete(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Delete Failed");
        alert.showAndWait();
    }

}
