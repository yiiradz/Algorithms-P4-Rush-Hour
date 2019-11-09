package com.pa4.rushhour;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

class RushHourGame {
    private Queue<Board> queue;
    private HashMap<String, String> hashMap;
    private int numOfVehicles;
    private String poppedKey;
    Board poppedBoard;

    /**
     * Initialize all of the game requirements
     */
    RushHourGame() {
        // Initialize the board as a 6x6 2D array of vehicles
        poppedBoard = new Board();
        queue = new ArrayDeque<>();
        hashMap = new HashMap<>(); // The strings will be a walkthrough of the vehicle ids
    }

    /**
     * Checks if we've already seen a particular board layout.
     * @param str Hashmap key to be inspected.
     * @return Returns true if the board orientation has been seen before.
     */
    private boolean isBoardNew(String str) {
        return (!hashMap.containsKey(str));
    }

    /**
     * Starts the game solving process.
     */
    void start() {
        // Begin by enqueueing the very first board
        seedQueue();
        // While the queue is not empty and the game is not done
        while (!queue.isEmpty()) {
            // Update the popped board and the poppedKey shorthand variable
            poppedBoard = queue.remove();
            poppedKey = poppedBoard.generateBoardKey();
            // If we satisfy the winning conditions
            if (poppedBoard.isGameDone()) {
                printSolution();
                return;
            }
            // For each vehicle that exists on the board
            for (int vehicleIndex = 0; vehicleIndex < numOfVehicles; vehicleIndex++) {
                // The very first thing we do is clone the board. From here on we should only use clonedBoard.
                Vehicle currVehicle = poppedBoard.vehicles.get(vehicleIndex);
                int LU = poppedBoard.getVehicleAdjacentLowerBound(currVehicle); // - Left/up (lower bound)
                int RD = poppedBoard.getVehicleAdjacentUpperBound(currVehicle); // + Right/down (upper bound)

                // Do horizontal work
                if (currVehicle.direction.equals("h")) {
                    if (LU != 0) { horizontalLeft(currVehicle, LU); }
                    if (RD != 0) { horizontalRight(currVehicle, RD); }
                }
                // Do vertical work
                if (currVehicle.direction.equals("v")) {
                    if (LU != 0) { verticalUp(currVehicle, LU); }
                    if (RD != 0) { verticalDown(currVehicle, RD); }
                }
            }
            // Done with the current item. Jump back up to the top to get the next one.
        }
        // We're either out of items in the queue or we found an answer.
        System.out.println("Queue is empty and we didn't find an answer. :(");
    }

    /**
     * Puts the first board that was read in from the file onto the queue and seeds the rest of the game
     * based on the info we were provided.
     */
    private void seedQueue() {
        numOfVehicles = poppedBoard.vehicles.size();
        queue.add(poppedBoard);
        String poppedKey = poppedBoard.generateBoardKey();
        hashMap.put(poppedKey, "ORIGIN");
    }

    /**
     * Moves a vehicle to the left for each space of LU
     * @param currVehicle Vehicle to be moved
     * @param LU Number of left spaces
     */
    private void horizontalLeft(Vehicle currVehicle, int LU) {
        // We're dealing with negative values, so we need to step up from negative to 0.
        for (int low = LU; low < 0; low++) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, low);
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard);
        }
    }

    /**
     * Moves a vehicle to the right for each space of RD
     * @param currVehicle Vehicle to be moved
     * @param RD Number of right spaces
     */
    private void horizontalRight(Vehicle currVehicle, int RD) {
        // We're dealing with positive values here, so we need to step down from positive to 0
        for (int spaces = RD; spaces > 0; spaces--) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, spaces);
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard);
        }
    }

    /**
     * Moves a vehicle upwards for each space of LU
     * @param currVehicle Vehicle to be moved
     * @param LU Number of upward spaces
     */
    private void verticalUp(Vehicle currVehicle, int LU) {
        // We're dealing with negative values, so we need to step up from negative to 0.
        for (int low = LU; low < 0; low++) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, low);
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard);
        }
    }

    /**
     * Moves a vehicle downwards for each space of LU
     * @param currVehicle Vehicle to be moved
     * @param RD Number of downward spaces
     */
    private void verticalDown(Vehicle currVehicle, int RD) {
        // We're dealing with positive values here, so we need to step down from positive to 0
        for (int high = RD; high > 0; high--) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, high);
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard);
        }
    }

    /**
     * Inserts the cloned board (based on popped board) into the queue and hashmap if it hasn't been seen before.
     * @param clonedBoard The cloned board to be inserted.
     */
    private void insertClonedBoardIfNew(Board clonedBoard) {
        // If the hashmap hasn't seen this board before, then add the cloned board as the key
        // and the popped board as the parent
        String cloneKey = clonedBoard.generateBoardKey();
        if (isBoardNew(cloneKey)) {
            hashMap.put(cloneKey, poppedKey);
            queue.add(clonedBoard);
        }
    }

    /**
     * Prints out the final solution by printing the current board and all of its parent keys.
     */
    private void printSolution() {
        // Print out the game solution
        int counter = 0;
        // Then start printing the boards before this one.
        String key = hashMap.get(poppedKey);
        while (!key.equals("ORIGIN")) {
            System.out.println(key);
            key = hashMap.get(key);
            counter++;
        }
        // Print out the number of moves
        System.out.println(counter + " moves");
    }
}
