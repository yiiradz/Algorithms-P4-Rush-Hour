package com.pa4.rushhour;

public class RushHourGame {
    Vehicle[][] board;

    public RushHourGame() {
        // Initialize the board as a 6x6 2D array of vehicles
        board = new Vehicle[6][6];
    }

    public void SetCarAtPosition(Vehicle v, int row, int col) {
        // TODO: Make this so that it sets the 2 locations to be the same car
        if (v.type != "car") {
            System.out.println("Refusing to update truck as a car!");
            return;
        }
    }

    public void SetTruckAtPosition(Vehicle v, int row, int col) {
        // TODO: Make this so that it sets the 2 locations to be the same car
        // Coordinates given should be the uppermost location for vertical
        // Coordinates given should be the leftmost location for horizontal
        if (v.type != "truck") {
            System.out.println("Refusing to update car as a truck!");
            return;
        }
    }
}
