package com.pa4.rushhour;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

class RushHourGame {
    private Queue<Board> queue;
    private HashMap<String, String> hashMap;
    private int numOfVehicles;
    Board poppedBoard;
    String poppedKey;

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
            // Print out debugging information
            System.out.println("Popped key: " + poppedKey);
            System.out.println("Popped Board: ");
            poppedBoard.print();
            System.out.println();

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
            System.out.println("\nPopping the next item in the queue.");
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

    private void horizontalLeft(Vehicle currVehicle, int LU) {
        // Make two loops, one for left/up and one for right/down where we clone the board and move
        int min = currVehicle.col + LU;
        for (int low = currVehicle.col; low >= min; low--) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, low);
            String cloneKey = clonedBoard.generateBoardKey();
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard, cloneKey);
        }
    }

    private void horizontalRight(Vehicle currVehicle, int RD) {
        int max = currVehicle.col + RD;
        for (int high = currVehicle.col; high < max; high++) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, high);
            String cloneKey = clonedBoard.generateBoardKey();
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard, cloneKey);
        }
    }

    private void verticalUp(Vehicle currVehicle, int LU) {
        // Make two loops, one for left/up and one for right/down where we clone the board and move
        int min = currVehicle.row + LU;
        for (int low = currVehicle.row; low > min; low--) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, low);
            String cloneKey = clonedBoard.generateBoardKey();
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard, cloneKey);
        }
    }

    private void verticalDown(Vehicle currVehicle, int RD) {
        int max = currVehicle.row + RD;
        for (int high = currVehicle.row; high < max; high++) {
            Board clonedBoard = new Board(poppedBoard);
            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
            clonedBoard.moveVehicle(clonedVehicle, high);
            String cloneKey = clonedBoard.generateBoardKey();
            // Check if the board with the move is new and insert if necessary
            insertClonedBoardIfNew(clonedBoard, cloneKey);
        }
    }

    private void insertClonedBoardIfNew(Board clonedBoard, String cloneKey) {
        // If the hashmap hasn't seen this board before, then add the cloned board as the key
        // and the popped board as the parent
        if (isBoardNew(cloneKey)) {
            System.out.println("Inserting new board " + cloneKey);// + " with parent " + poppedKey);
            hashMap.put(cloneKey, poppedKey);
            queue.add(clonedBoard);
        } else {
            System.out.println("Board " + cloneKey + " has already been seen.");
        }
    }

    /**
     *
     */
    private void printSolution() {
        // Print out the game solution
        System.out.println("Solution:");
        int counter = 0;
        // Print the board we're working with now
        System.out.println(poppedBoard.generateBoardKey());
        // Then start printing the boards before this one.
        String key = poppedBoard.generateBoardKey();
        while (!key.equals("ORIGIN")) {
            String parent = hashMap.get(key);
            System.out.println(parent);
            key = parent;
            counter++;
        }
        // Print out the number of moves
        System.out.println(counter + " moves");
    }
}
