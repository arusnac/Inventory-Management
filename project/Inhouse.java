package com.arusnac.project;

/**
 * Models an inhouse part, inherits part class
 * @author Andrew Rusnac
 */

public class Inhouse extends Part {
    private int machineID;

    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**
     *
     * @return MachineID
     */
    public int getMachineID(){
        return machineID;
    }
}
