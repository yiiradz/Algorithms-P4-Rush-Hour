/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.rushhour;

/**
 *
 * @author yiradz
 */
// TODO: Do we want to have a Car and Truck class that implements / extends Vehicle?
public class Vehicle {
    // Declare vehicle variables
    public String type = "";       // car or truck
    public String color = "";      // red, blue, purple, etc.
    public String direction = "";  // (h)orizontal or (v)ertical
    public int row = 0;
    public int col = 0;

    public Vehicle() {
        this.type = null;
        this.color = null;
        this.direction = null;
        this.row = -1;
        this.col = -1;
    }

    public Vehicle(String t, String c, String d, int row, int col) {
        this.type = t;
        this.color = c;
        this.direction = d;
        this.row = row;
        this.col = col;
    }

    public void print() {
        System.out.println("Type: " + type);
        System.out.println("Color: " + color);
        System.out.println("Direction: " + direction);
        System.out.println("Row: " + row);
        System.out.println("Col: " + col);
    }

    /**
     * @brief Validates the values which define the current vehicle.
     * Note that we don't check color since that wouldn't really be useful.
     */
    public boolean validate() {
        boolean isCar = (type.equals("car"));
        boolean isTruck = (type.equals("truck"));
        boolean isVertical = (direction.equals("v"));
        boolean isHorizontal = (direction.equals("h"));
        boolean isValid = true;
        if (!isCar) {
            if (!isTruck) {
                System.out.println("Vehicle has invalid type!");
                isValid = false;
            }
        }
        if (!isVertical) {
            if (!isHorizontal) {
                System.out.println("Vehicle has invalid direction!");
                isValid = false;
            }
        }
        // Check if the coordinates are valid
        if (isValid) {
            // The type is valid, so we can check direction
            if (type == "car") {
                if (direction == "v" && (row > 3 || row < 0)) {
                    System.out.println("Car row is invalid! <" + row + "," + col + ">");
                }
                if (direction == "h" && (col > 3 || col < 0)) {
                    System.out.println("Car col is invalid! <" + row + "," + col + ">");
                }
            }
            if (type == "truck") {
                if (direction == "v" && (row > 2 || row < 0)) {
                    System.out.println("Truck row is invalid! <" + row + "," + col + ">");
                }
                if (direction == "h" && (col > 2 || col < 0)) {
                    System.out.println("Truck col is invalid! <" + row + "," + col + ">");
                }
            }
        } else {
            System.out.println("Skipping coordinate check since vehicle is invalid...");
        }
        return isValid;
    }
}
