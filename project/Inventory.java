package com.arusnac.project;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Handles persistent data during runtime
 *
 * @author Andrew Rusnac
 * FUTURE ENHANCEMENT Make it so that the inventory saves to a database
 */

public class Inventory {
    /**
     * List of all parts
     */
    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    /**
     * List of all products
     */
    private static ObservableList<Product> products = FXCollections.observableArrayList();

    /**
     * @param
     */
    public static void addPart(Part part){
        parts.add(part);
    }

    /**
     * @param
     */
    public static void updatePart(int i, Part part){
        parts.set(i, part);
    }

    /**
     * @param product in list products
     */
    public static void updateProduct(int i, Product product){
        products.set(i, product);
    }

    /**
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts(){
        return parts;
    }

    /**
     * Adds product to list products
     * @param product
     */
    public static void addProduct(Product product){
        products.add(product);
    }

    /**
     * returns list of all products
     * @return products
     */
    public static ObservableList<Product> getAllProducts(){
        return products;
    }


    /**
     * Assign part a new ID
     * @return id
     */
    public static int getNewId(){
        int id = parts.size();
        return id++;
    }
    /**
     * Assign prouct a new ID
     * @return id
     */
    public static int getNewProductId(){
        int id = products.size();
        return id++;
    }

    /**
     * Removes a part from the list
     * @param partToRemove
     */
    public static void removePart(Part partToRemove){
        parts.remove(partToRemove);
        }

    /**
     * Removes a product from the list
     * @param productToRemove
     */
    public static void removeProduct(Product productToRemove){
        products.remove(productToRemove);
    }
}


