package com.pa4.rushhour;

import java.util.HashMap;
import java.util.Queue;

public class RushHourGame {
    Vehicle[][] board;
    Queue<String> queue;
    HashMap<String, String> hashMap;

    /**
     * Initialize all of the game requirements
     */
    public RushHourGame() {
        // Initialize the board as a 6x6 2D array of vehicles
        board = new Vehicle[6][6];
    }

    /**
     * Prints out the current state of the game board with each location being indicated by the
     * first letter of the vehicle color
     */
    public void print() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Vehicle v = board[i][j];
                if (v == null) {
                    System.out.print("[ ]");
                } else {
                    // We want to emphasize the red car so we uppercase it
                    String color = String.valueOf(v.color.charAt(0));
                    System.out.print("[" + color + "]");
                }
            }
            System.out.println();
        }
    }

    /**
     * Returns a boolean on whether a space is free (null) or not.
     * @param row Row location of vehicle (leftmost).
     * @param col Column location of vehicle (topmost).
     * @return Returns true if the location is free.
     */
    public boolean isFree(int row, int col) {
        return (board[row][col] == null);
    }

    public boolean hasPathBeenSeen(String str) {
        return (hashMap.containsKey(str));
    }

    /**
     * Inserts a vehicle onto the board using the already provided locations stored in
     * the vehicle object. Used primarily for inserting at initialization.
     * @param vehicle Vehicle to be inserted.
     * @return Returns true if the insertion occured.
     */
    public boolean insertVehicle(Vehicle vehicle) {
        if (vehicle.type.equals("car")) {
            return insertCarAtPosition(vehicle, vehicle.row, vehicle.col);
        }
        else if (vehicle.type.equals("truck")) {
            return insertTruckAtPosition(vehicle, vehicle.row, vehicle.col);
        } else {
            return false;
        }
    }

    /**
     * Takes a vehicle and a number of spaces (positive or negative) to move.
     * For verticle, negative is upwards, and for horizontal negative is left.
     * @param vehicle Vehicle to be moved.
     * @param spaces Number of spaces to move, either positive or negative.
     * @return Returns true if movement occured.
     */
    public boolean moveVehicle(Vehicle vehicle, int spaces) {
        // TODO: Create checkLeft, checkRight, checkUp, and checkDown functions which take a spaces input
        return false;
    }

    /**
     *  Inserts a car on the board if all necesarry spaces are free.
     * @param v Vehicle to be inserted
     * @param row Row location
     * @param col Column location
     */
    public boolean insertCarAtPosition(Vehicle v, int row, int col) {
        if (!v.type.equals("car")) {
            System.out.println("Refusing to update truck as a car!");
            return false;
        }
        // Update the locations if they're available
        if (v.direction.equals("v")) {
            if (isFree(row, col) && isFree(row + 1, col)) {
                board[row][col] = v;
                board[row + 1][col] = v;
            } else {
                System.out.println("Cannot place car at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        if (v.direction.equals("h")) {
            if (isFree(row, col) && isFree(row, col + 1)) {
                board[row][col] = v;
                board[row][col + 1] = v;
            } else {
                System.out.println("Cannot place car at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        return true;
    }

    /**
     * Inserts a truck on the board if all necesarry spaces are free.
     * @param v Vehicle to be inserted
     * @param row Row location
     * @param col Column location
     */
    public boolean insertTruckAtPosition(Vehicle v, int row, int col) {
        // Coordinates given should be the uppermost location for vertical
        // Coordinates given should be the leftmost location for horizontal
        if (!v.type.equals("truck")) {
            System.out.println("Refusing to update car as a truck!");
            return false;
        }
        // Update the locations if they're available
        if (v.direction.equals("v")) {
            if (isFree(row, col) && isFree(row + 1, col) && isFree(row + 2, col)) {
                board[row][col] = v;
                board[row + 1][col] = v;
                board[row + 2][col] = v;
            } else {
                System.out.println("Cannot place truck at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        if (v.direction.equals("h")) {
            if (isFree(row, col) && isFree(row, col + 1) && isFree(row, col + 2)) {
                board[row][col] = v;
                board[row][col + 1] = v;
                board[row][col + 2] = v;
            } else {
                System.out.println("Cannot place truck at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        return true;
    }
}
