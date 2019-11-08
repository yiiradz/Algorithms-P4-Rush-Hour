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
        // Do some bounds checking in case we're trying to check off the array
        // I know this means that something somewhere else is screwed up (probably?)
        // but we're desperate at this point since we can't seem to find this bug.
        if (row < 0 || col < 0 || row > 5 || col > 5) {
            return false;
        }
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
        Vehicle redCar = vehicles.get(0);
        // If we're at position 4/5 then the next location has to be open
        // and thus we've won the game.
        System.out.println("Checking " + redCar.color + " position: <" + redCar.row + "," + redCar.col + ">");
        return (redCar.col == 4) && (redCar.row == 2);
    }

    /**
     * Gets the number of available spaces to the left or above, depending on orientation.
     *
     * @param v Vehicle to check adjacencies
     * @return Negative value associated with number of spaces to the left or above.
     */
    int getVehicleAdjacentLowerBound(Vehicle v) {
        int spaces = 0;
        int min = 0;
        if (v.direction.equals("h")) { // Left
            int start = v.col - 1;
            for (int col = start; col >= min; col--) {
                if (isSpaceFree(v.row, col)) {
                    spaces--;
                } else {
                    return spaces;
                }
            }
        }
        if (v.direction.equals("v")) { // Up
            int start = v.row - 1;
            for (int row = start; row >= min; row--) {
                if (isSpaceFree(row, v.col)) {
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
        int max = board.length - 1;
        if (v.direction.equals("h")) {  // Right
            int start = v.col + v.size;
            for (int col = start; col <= max; col++) {
                if (isSpaceFree(v.row, col)) {
                    spaces++;
                } else {
                    return spaces;
                }
            }
        }
        if (v.direction.equals("v")) {  // Down
            int start = v.row + v.size;
            System.out.println("Start: " + start + " Max: " + max);
            for (int row = start; row <= max; row++) {
                System.out.println("Row: " + row + " Max: " + max);
                // the issue is that because we have our own car below the anchor we return not free.
                if (isSpaceFree(row, v.col)) {
                    spaces++;
                } else {
                    return spaces;
                }
            }
        }
        System.out.println("Available spaces: " + spaces);
        return spaces;
    }

    /**
     * Takes in positives and negatives integers and moves vehicle according to parameters
     * Finds the vehicle's anchor, removes the car from the board and reinserts it into the board
     * check orientation if h, add i to the col; if v, add i to the row
     * NOTE: This function does not error check, it assumes that the space given can be used
     */
    void moveVehicle(Vehicle v, int i) {
        // Remove the vehicle from the board
        // TODO: Investigate the issue here.
        removeVehicle(v);
        if (v.direction.equals("h")) {
            // Add `i` to the column (left/right) and reinsert
            v.col = v.col + i;
            // Insert the vehicle with replace mode enabled
            insertVehicle(v, true);
        }
        if (v.direction.equals("v")) {
            // Add `i` to the row (up/down) and reinsert
            v.row = v.row + i;
            // Insert the vehicle with replace mode enabled
            insertVehicle(v, true);
        }
    }

    private void removeVehicle(Vehicle v) {
        if (v.direction.equals("h")) {
            int min = v.col;
            int max = v.col + (v.size - 1);
            for (int delCol = min; delCol <= max; delCol++) {
                // The row remains static in this case, and it's only the Column that changes
                board[v.row][delCol] = null;
            }
        }
        if (v.direction.equals("v")) {
            int min = v.row;
            int max = v.row + (v.size - 1);
            for (int delRow = min; delRow <= max; delRow++) {
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
    boolean insertVehicle(Vehicle vehicle, boolean shouldReplace) {
        if (vehicle.type.equals("car")) {
            return insertCarAtPosition(vehicle, vehicle.row, vehicle.col, shouldReplace);
        }
        if (vehicle.type.equals("truck")) {
            return insertTruckAtPosition(vehicle, vehicle.row, vehicle.col, shouldReplace);
        }
        return false;
    }

    /**
     * Inserts a car on the board if all necesarry spaces are free.
     *
     * @param v   Vehicle to be inserted
     * @param row Row location
     * @param col Column location
     */
    private boolean insertCarAtPosition(Vehicle v, int row, int col, boolean shouldReplace) {
        if (!v.type.equals("car")) {
            System.out.println("Refusing to update truck as a car!");
            return false;
        }
        if (v.direction.equals("v")) {
            board[row][col] = v;
            board[row + 1][col] = v;
        }
        if (v.direction.equals("h")) {
            board[row][col] = v;
            board[row][col + 1] = v;
        }
        // Insert the vehicle into the array
        if (shouldReplace) {
            vehicles.set(v.id, v);
        } else {
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
    private boolean insertTruckAtPosition(Vehicle v, int row, int col, boolean shouldReplace) {
        if (!v.type.equals("truck")) {
            System.out.println("Refusing to update car as a truck!");
            return false;
        }
        // Update the locations if they're available
        if (v.direction.equals("v")) {
            board[row][col] = v;
            board[row + 1][col] = v;
            board[row + 2][col] = v;
        }
        if (v.direction.equals("h")) {
            board[row][col] = v;
            board[row][col + 1] = v;
            board[row][col + 2] = v;
        }
        // Insert the vehicle into the array
        if (shouldReplace) {
            vehicles.set(v.id, v);
        } else {
            vehicles.add(v);
        }
        return true;
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
