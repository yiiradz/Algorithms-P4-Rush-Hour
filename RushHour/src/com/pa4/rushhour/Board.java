package com.pa4.rushhour;
import java.util.ArrayList;
import java.util.List;

class Board {
    Vehicle[][] board;
    List<Vehicle> vehicles;

    Board() {
        board = new Vehicle[6][6];
        vehicles = new ArrayList<>();
    }

    Board(Board original) {
        board = new Vehicle[6][6];
        vehicles = new ArrayList<>();
        // Do a deep copy of the board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Vehicle orig = original.board[i][j];
                if (orig != null) {
                    Vehicle copy = new Vehicle(orig);
                    board[i][j] = copy;
                } else {
                    board[i][j] = null;
                }
            }
        }
        // Do a deep copy of the vehicles array
        for (int i = 0; i < original.vehicles.size(); i++) {
            Vehicle originalVehicle = original.vehicles.get(i);
            Vehicle newVehicle = new Vehicle(originalVehicle);
            vehicles.add(newVehicle);
        }
    }

    /**
     * Prints out the current state of the game board with each location being indicated by the
     * first letter of the vehicle color
     */
    void print() {
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
            if (i == 2) {
                System.out.print("<--");
            }
            System.out.println();
        }
    }

    /**
     * Returns a boolean on whether a space is free (null) or not.
     *
     * @param row Row location of vehicle (leftmost).
     * @param col Column location of vehicle (topmost).
     * @return Returns true if the location is free.
     */
    private boolean isSpaceFree(int row, int col) {
        return (board[row][col] == null);
    }

    /**
     * Checks if the red car is in the winning position.
     *
     * @return Returns true if the red car is right in front of the exit, and thus wins.
     */
    boolean isGameDone() {
        // The red car should always be the first one.
        // We won't do any error checking for that though.
        if (vehicles != null) {
            Vehicle redCar = vehicles.get(0);

            // If we're at position 4/5 then the next location has to be open
            // and thus we've won the game.
            return (redCar.row == 4) && (redCar.col == 2);
        }
        return false;
    }

    /**
     * // Takes in positives and negatives integers and moves vehicle according to parameters
     * // Finds the vehicle's anchor, removes the car from the board and reinserts it into the board
     * // check orientation if h, add i to the col; if v, add i to the row
     * // NOTE: This function does not error check, it assumes that the space given can be used
     */
    void moveVehicle(Vehicle v, int i) {
        // Remove the vehicle from the board
        removeVehicle(v);
        // Horizontal orientation
        if (v.direction.equals("h")) {
            // Add `i` to the column (left/right) and reinsert
            v.col = v.col + i;
            insertVehicle(v);
        }

        // Vertical orientation
        if (v.direction.equals("v")) {
            // Add `i` to the row (up/down) and reinsert
            v.row = v.row + i;
            insertVehicle(v);
        }
    }

    private void removeVehicle(Vehicle v) {
        if (v.direction.equals("h")) {
            int max = v.col + (v.size);
            for (int delCol = v.col; delCol < max; delCol++) {
                // The row remains static in this case, and it's only the Column that changes
                board[v.row][delCol] = null;
            }
        }
        if (v.direction.equals("v")) {
            int max = v.row + (v.size - 1);
            for (int delRow = v.row; delRow < max; delRow++) {
                // The row remains static in this case, and it's only the Column that changes
                board[delRow][v.col] = null;
            }
        }
    }

    /**
     * Inserts a vehicle onto the board using the already provided locations stored in
     * the vehicle object. Used primarily for inserting at initialization.
     *
     * @param vehicle Vehicle to be inserted.
     * @return Returns true if the insertion occured.
     */
    boolean insertVehicle(Vehicle vehicle) {
        if (vehicle.type.equals("car")) {
            return insertCarAtPosition(vehicle, vehicle.row, vehicle.col);
        } else if (vehicle.type.equals("truck")) {
            return insertTruckAtPosition(vehicle, vehicle.row, vehicle.col);
        } else {
            return false;
        }
    }

    /**
     * Inserts a car on the board if all necesarry spaces are free.
     *
     * @param v   Vehicle to be inserted
     * @param row Row location
     * @param col Column location
     */
    private boolean insertCarAtPosition(Vehicle v, int row, int col) {
        if (!v.type.equals("car")) {
            System.out.println("Refusing to update truck as a car!");
            return false;
        }
        // Update the locations if they're available
        if (v.direction.equals("v")) {
            if (isSpaceFree(row, col) && isSpaceFree(row + 1, col)) {
                board[row][col] = v;
                board[row + 1][col] = v;
            } else {
                System.out.println("Cannot place car at location <" + row + "," + col + "> because it won't fit!");
            }
        }
        if (v.direction.equals("h")) {
            if (isSpaceFree(row, col) && isSpaceFree(row, col + 1)) {
                board[row][col] = v;
                board[row][col + 1] = v;
            } else {
                System.out.println("Cannot place car at location <" + row + "," + col + "> because it won't fit!");
            }
        }
        // Insert the vehicle into the array
        if (vehicles != null) {
            vehicles.add(v);
        }
        return true;
    }

    /**
     * Inserts a truck on the board if all necessary spaces are free.
     *
     * @param v   Vehicle to be inserted
     * @param row Row location
     * @param col Column location
     */
    private boolean insertTruckAtPosition(Vehicle v, int row, int col) {
        // Coordinates given should be the uppermost location for vertical
        // Coordinates given should be the leftmost location for horizontal
        if (!v.type.equals("truck")) {
            System.out.println("Refusing to update car as a truck!");
            return false;
        }
        // Update the locations if they're available
        if (v.direction.equals("v")) {
            if (isSpaceFree(row, col) && isSpaceFree(row + 1, col) && isSpaceFree(row + 2, col)) {
                board[row][col] = v;
                board[row + 1][col] = v;
                board[row + 2][col] = v;
            } else {
                System.out.println("Cannot place truck at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        if (v.direction.equals("h")) {
            if (isSpaceFree(row, col) && isSpaceFree(row, col + 1) && isSpaceFree(row, col + 2)) {
                board[row][col] = v;
                board[row][col + 1] = v;
                board[row][col + 2] = v;
            } else {
                System.out.println("Cannot place truck at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        if (vehicles != null) {
            vehicles.add(v);
        }
        return true;
    }

    /*
      The following two functions for getting the number of free spaces are nearly identical. I know that if
      I were to dedicate more time to this I could come up with some elegant way of getting the values with as little
      code repeated as possible, but I don't feel like doing that for this project. Hope that's okay.
     */

    /**
     * Gets the number of available spaces to the left or above, depending on orientation.
     *
     * @param v Vehicle to check adjacencies
     * @return Negative value associated with number of spaces to the left or above.
     */
    int getVehicleAdjacentLowerBound(Vehicle v) {
        int spaces = 0;
        // If we're horizontal then we check left
        if (v.direction.equals("h")) {
            int row = v.row;
            // Start at the space before the vehicle anchor and keep going left
            for (int col = v.col - 1; col > -1; col--) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                if (isSpaceFree(row, col)) {
                    spaces--;
                } else {
                    return spaces;
                }
            }
        }
        // If we're vertical then we check up
        // TODO: Need to test this section of code
        if (v.direction.equals("v")) {
            int col = v.col;
            // Start at the vehicle anchor and keep moving upwards (toward 0) so long as we're on the board
            for (int row = v.row; row > -1; row--) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                int rowAfterV = row + v.size;
                if (isSpaceFree(rowAfterV, col)) {
                    spaces--;
                } else {
                    return spaces;
                }
            }
        }
        return spaces;
    }

    /**
     * Gets the number of spaces to the right or below, depending on orientation.
     *
     * @param v Vehicle to check adjacencies.
     * @return Positive value associated with number of spaces to the right or below.
     */
    int getVehicleAdjacentUpperBound(Vehicle v) {
        int spaces = 0;
        // If we're horizontal then we check left
        if (v.direction.equals("h")) {
            int row = v.row;
            // Start at the vehicle anchor and keep moving so long as we're on the board
            int max = board.length - v.size;
            for (int col = v.col; col < max; col++) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                int colAfterV = col + v.size;
                if (isSpaceFree(row, colAfterV)) {
                    spaces++;
                } else {
                    return spaces;
                }
            }
        }
        // If we're vertical then we check up
        if (v.direction.equals("v")) {
            int col = v.col;
            // Start at the vehicle anchor and keep moving upwards (toward 0) so long as we're on the board
            int max = board.length - v.size;
            for (int row = v.row; row < max; row++) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                int colAfterV = col + v.size;
                if (isSpaceFree(row, colAfterV)) {
                    spaces++;
                } else {
                    return spaces;
                }
            }
        }
        return spaces;
    }

    /**
     * Generates a key representing the state of the board. "X" represents an empty location and each number
     * represents the ID of the vehicle at that location. Goes from top leftmost location to bottom rightmost.
     *
     * @return Returns the generated board state key.
     */
    String generateBoardKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Vehicle curr = board[i][j];
                if (curr != null) {
                    key.append(curr.id);
                } else {
                    key.append("X");
                }
            }
        }
        return key.toString();
    }
}
