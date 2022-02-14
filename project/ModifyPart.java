package com.arusnac.project;

/**
 * Controller for the modify part screen
 * @author Andrew Rusnac
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

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
    public RadioButton outsourcedRadio;
    boolean radioSetToInhouse;
    Part modifyPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPart = MainFormController.getPart();
        idInput.setText(String.valueOf(modifyPart.getId()));
        nameInput.setText(modifyPart.getName());
        inventoryInput.setText(String.valueOf(modifyPart.getStock()));
        costInput.setText(String.valueOf(modifyPart.getPrice()));
        maxInput.setText(String.valueOf(modifyPart.getMax()));
        minInput.setText(String.valueOf(modifyPart.getMin()));

        /**
         * Check to see if the part is an instance of Inhouse or Outsourced and set the radio button accordingly
         */
        System.out.print(modifyPart instanceof Inhouse);
        System.out.print(modifyPart instanceof Outsourced);
        if(modifyPart instanceof Inhouse) {
            inHouseRadio.setSelected(true);
            machineIdInput.setText(String.valueOf(((Inhouse) modifyPart).getMachineID()));
            onInhouseSet();
        }
        else if(modifyPart instanceof  Outsourced){
            outsourcedRadio.setSelected(true);
            machineIdInput.setText(((Outsourced) modifyPart).getCompanyName());
            onOutsourcedSet();
        }
    }

    /**
     * Change label based on instance type
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
     * Parse data, check input and save the modified part to the list of all parts when the save button is clicked
     */
    public void onSaveButtonClick(){

        try {
            String name = nameInput.getText();
            double cost = Double.parseDouble(costInput.getText());
            int stock = Integer.parseInt(inventoryInput.getText());
            int min = Integer.parseInt(minInput.getText());
            int max = Integer.parseInt(maxInput.getText());

            if (radioSetToInhouse && min < max) {
                int machineId = Integer.parseInt(machineIdInput.getText());
                Inhouse part = new Inhouse(modifyPart.getId(), name, cost, stock, min, max, machineId);
                Inventory.updatePart(modifyPart.getId(), part);
            }
            else if (!radioSetToInhouse && min < max) {
                String companyName = machineIdInput.getText();
                Outsourced part = new Outsourced(modifyPart.getId(), name, cost, stock, min, max, companyName);
                Inventory.updatePart(modifyPart.getId(), part);
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
     * Close screen and return to the main form
     */
    public void onCancelButtonClick(){
        Stage stage = (Stage) cancelPartButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Error for incorrect input
     */
    public void displayError(){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText("Invalid Input, Please Retry");
        invalidIput.showAndWait();

    }
}
