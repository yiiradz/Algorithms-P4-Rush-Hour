package com.pa4.rushhour;

public class Board {
    Vehicle[][] board;
    Vehicle[] vehicles;

    Board() {
        board = new Vehicle[6][6];
    }

    Board(Board original) {
        board = new Vehicle[6][6];
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
        for (int i = 0; i < original.vehicles.length; i++) {
            vehicles[i] = new Vehicle(original.vehicles[i]);
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
    boolean isSpaceFree(int row, int col) {
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
            Vehicle redCar = vehicles[0];

            // If we're at position 4/5 then the next location has to be open
            // and thus we've won the game.
            if ((redCar.row == 4) && (redCar.col == 2)) {
                return true;
            }
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
        // Horizontal orientation
        if (v.direction == "h") {
            // Take the vehicle, get it's size, remove it from the current board
            int vRow = v.row;
            int vCol = v.col;

            for (int delCol = vCol; delCol < vCol + v.size; delCol++) {
                // The row remains static in this case, and it's only the Column that changes
                board[vRow][delCol] = null;
            }

            // Now reinsert the car into the given parameters
            int newCol = vCol + i;
            v.col = newCol;

            // Make a loop that reinserts the vehicle in all of the positions which match its size
            for (int iCol = v.col; iCol < v.col + v.size; iCol++) {
                board[v.row][v.col] = v;
            }
        }

        // Vertical orientation
        if (v.direction == "v") {
            // Take the vehicle, get it's size, remove it from the current board
            int vRow = v.row;
            int vCol = v.col;

            for (int delRow = vRow; delRow < vRow + v.size; delRow++) {
                // The row remains static in this case, and it's only the Column that changes
                board[delRow][vCol] = null;
            }

            // Now reinsert the car into the given parameters
            int newRow = vRow + i;
            v.row = newRow;

            // Make a loop that reinserts the vehicle in all of the positions which match its size
            for (int iRow = v.col; iRow < v.row + v.size; iRow++) {
                board[v.row][v.col] = v;
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
    boolean insertCarAtPosition(Vehicle v, int row, int col) {
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
                System.out.println("Cannot place car at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        if (v.direction.equals("h")) {
            if (isSpaceFree(row, col) && isSpaceFree(row, col + 1)) {
                board[row][col] = v;
                board[row][col + 1] = v;
            } else {
                System.out.println("Cannot place car at location <" + row + "," + "col" + "> because it won't fit!");
            }
        }
        // Insert the vehicle into the array
        if (vehicles != null) {
            vehicles[vehicles.length] = v;
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
    boolean insertTruckAtPosition(Vehicle v, int row, int col) {
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
            vehicles[vehicles.length] = v;
        }
        return true;
    }

    /**
     * The following two functions for getting the number of free spaces are nearly identical. I know that if
     * I were to dedicate more time to this I could come up with some elegant way of getting the values with as little
     * code repeated as possible, but I don't feel like doing that for this project. Hope that's okay.
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
            // Start at the vehicle anchor and keep moving so long as we're on the board
            for (int col = v.col; col > -1; col--) {
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
        if (v.direction.equals("v")) {
            int col = v.col;
            // Start at the vehicle anchor and keep moving upwards (toward 0) so long as we're on the board
            for (int row = v.row; row > -1; row--) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                if (isSpaceFree(row, col)) {
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
            for (int col = v.col; col < board.length; col++) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                if (isSpaceFree(row, col)) {
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
            for (int row = v.row; row < board.length; row++) {
                // If the space is free, decrement spaces because we're getting the lower bound
                // If the space isn't free, just return because there's no point in continuing to look
                if (isSpaceFree(row, col)) {
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
