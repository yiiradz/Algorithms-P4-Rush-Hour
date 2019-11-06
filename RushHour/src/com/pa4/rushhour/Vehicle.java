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
    public int size = 0;
    public int id = -1;

    /**
     * Constructs a new vehicle for use in a board.
     * @param t Type of vehicle. Either car or truck.
     * @param c Color of vehicle. Honeslty doesn't matter.
     * @param d Direction of vehicle. Either v or h.
     * @param row Anchor row (leftmost / topmost)
     * @param col Anchor column (leftmost / topmost)
     * @param id Vehicle identifier value
     */
    public Vehicle(String t, String c, String d, int row, int col, int id) {
        this.type = t;
        this.color = c;
        this.direction = d;
        this.row = row;
        this.col = col;
        this.id = id;
        // Set the size depending on type. Assumes validity.
        if (type.equals("car")) {
            size = 2;
        }
        else if (type.equals("truck")) {
            size = 3;
        }
        // Red car should always have an ID of 0.
    }

    /**
     * Copy Constructor
     * @param orig Original vehicle to copy
     */
    public Vehicle(Vehicle orig) {
        this(orig.type, orig.color, orig.direction, orig.row, orig.col, orig.id);
    }

    /**
     * Prints out all of the vehicle information.
     */
    public void print() {
        System.out.println("Type: " + type);
        System.out.println("Color: " + color);
        System.out.println("Direction: " + direction);
        System.out.println("Row: " + row);
        System.out.println("Col: " + col);
        System.out.println("ID: " + id);
    }

    /**
     * @brief Validates the values which define the current vehicle.
     * Note that we don't check color since that wouldn't really be useful.
     */
    public boolean isValid() {
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
        if (id < 0) {
            System.out.println("Vehicle has invalid identifier!");
            isValid = false;
        }
        // Check if the coordinates are valid
        if (isValid) {
            // The type is valid, so we can check direction
            if (type.equals("car")) {
                if (direction.equals("v") && (row > 4 || row < 0)) {
                    System.out.println("Car row is invalid! <" + row + "," + col + ">");
                }
                if (direction.equals("h") && (col > 4 || col < 0)) {
                    System.out.println("Car col is invalid! <" + row + "," + col + ">");
                }
            }
            if (type.equals("truck")) {
                if (direction.equals("v") && (row > 3 || row < 0)) {
                    System.out.println("Truck row is invalid! <" + row + "," + col + ">");
                }
                if (direction.equals("h") && (col > 3 || col < 0)) {
                    System.out.println("Truck col is invalid! <" + row + "," + col + ">");
                }
            }
        } else {
            System.out.println("Skipping coordinate check since vehicle is invalid...");
        }
        return isValid;
    }
}
