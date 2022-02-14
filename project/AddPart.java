/**
 * @author Andrew Rusnac
 */
package com.arusnac.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class AddPart implements Initializable {

    public TextField idInput;
    public TextField nameInput;
    public TextField inventoryInput;
    public TextField costInput;
    public TextField maxInput;
    public TextField minInput;
    public TextField machineIdInput;
    public Button savePartButton;
    public Button cancelPartButton;
    public Label changeText;
    @FXML
    public RadioButton inHouseRadio;
    boolean radioSetToInhouse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Change label depending on the radio selection
     */
    public void onOutsourcedSet(){
        changeText.setText("Company Name");
        radioSetToInhouse = false;
    }

    public void onInhouseSet(){
        changeText.setText("Machine ID");
        radioSetToInhouse = true;
    }

    /**
     * Parse data, check input and save a new part to the list of all parts when the save button is clicked
     */
    public void onSaveButtonClick(){

        try {
            int id;
            if(Inventory.getAllParts().isEmpty()){
                id = 0;
            }
            else {
                id = Inventory.getNewId();
            }
            String name = nameInput.getText();
            double cost = Double.parseDouble(costInput.getText());
            int stock = Integer.parseInt(inventoryInput.getText());
            int min = Integer.parseInt(minInput.getText());
            int max = Integer.parseInt(maxInput.getText());

            if (radioSetToInhouse && min < max) {
                int machineId = Integer.parseInt(machineIdInput.getText());
                Inhouse part = new Inhouse(id, name, cost, stock, min, max, machineId);
                Inventory.addPart(part);
            }
            else if (!radioSetToInhouse && min < max) {
                String companyName = machineIdInput.getText();
                Outsourced part = new Outsourced(id, name, cost, stock, min, max, companyName);
                Inventory.addPart(part);
            }
            else {
                displayError();
            }




        }catch(Exception e){
            displayError();
        }
        Stage stage = (Stage) savePartButton.getScene().getWindow();
        stage.close();
    }

    /**
     * return to main screen if cancelled is clicked
     */
    public void onCancelButtonClick(){
        Stage stage = (Stage) cancelPartButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Error to display when input is incorrect
     */
    public void displayError(){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText("Invalid Input, Please Retry");
        invalidIput.showAndWait();

    }
}
