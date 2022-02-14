package com.arusnac.project;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models a product
 * @author Andrew Rusnac
 * FUTURE ENHANCEMENT Add a photo of the product
 */

public class Product {
    /**
     * List of parts associated with product
     */
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private int stock;
    private int min;
    private int max;


    public Product(int id, String name, double price, int stock, int min, int max) {
        //super(id, name, price, stock, min, max);
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * @return the id
     */
    public int getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add associated part
     * @param part add associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     *
     * @return the list of associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}

