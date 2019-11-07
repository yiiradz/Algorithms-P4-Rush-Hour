package com.pa4.rushhour;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

class RushHourGame {
    private Queue<Board> queue;
    private HashMap<String, String> hashMap;
    private int numOfVehicles;
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
        while (!queue.isEmpty() && !poppedBoard.isGameDone()) {
            // Update the popped board
            // ONLY UPDATES AFTER EVERY POSSIBLE MOVE HAS BEEN MADE!!
            poppedBoard = queue.remove();
            String poppedKey = poppedBoard.generateBoardKey();
            System.out.println("Popped key: " + poppedKey);

            // For each vehicle that exists on the board
            for (int vehicleIndex = 0; vehicleIndex < numOfVehicles; vehicleIndex++) {
                // The very first thing we do is clone the board. From here on we should only use clonedBoard.
                Vehicle currVehicle = poppedBoard.vehicles.get(vehicleIndex);
                String direction = currVehicle.direction;
                int LU = poppedBoard.getVehicleAdjacentLowerBound(currVehicle); // - Left/up (lower bound)
                int RD = poppedBoard.getVehicleAdjacentUpperBound(currVehicle); // + Right/down (upper bound)
                // CLONES THE CURRENT BOARD EACH TIME. ALL MODIFICATIONS SHOULD BE MADE TO "clonedBoard"!!
                // Do horizontal work
                if (direction.equals("h")) {
                    if (LU != 0) {
                        // Make two loops, one for left/up and one for right/down where we clone the board and move
                        for (int low = currVehicle.col; low >= low + LU; low--) {
                            Board clonedBoard = new Board(poppedBoard);
                            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
                            clonedBoard.moveVehicle(clonedVehicle, low);
                            String cloneKey = clonedBoard.generateBoardKey();
                            // If the hashmap hasn't seen this board before, then add the cloned board as the key
                            // and the popped board as the parent
                            if (isBoardNew(cloneKey)) {
                                System.out.println("Inserting new board " + cloneKey + " with parent " + poppedKey);
                                hashMap.put(cloneKey, poppedKey);
                                queue.add(clonedBoard);
                            } else {
                                System.out.println("Board " + cloneKey + " has already been seen.");
                            }
                        }
                    }
                    if (RD != 0) {
                        for (int high = currVehicle.col; high < high + RD; high++) {
                            Board clonedBoard = new Board(poppedBoard);
                            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
                            clonedBoard.moveVehicle(clonedVehicle, high);
                            String cloneKey = clonedBoard.generateBoardKey();
                            // If the hashmap hasn't seen this board before, then add the cloned board as the key
                            // and the popped board as the parent
                            if (isBoardNew(cloneKey)) {
                                System.out.println("Inserting new board " + cloneKey + " with parent " + poppedKey);
                                hashMap.put(cloneKey, poppedKey);
                                queue.add(clonedBoard);
                            } else {
                                System.out.println("Board " + cloneKey + " has already been seen.");
                            }
                        }
                    }
                }
                // Do vertical work
                if (direction.equals("v")) {
                    if (LU != 0) {
                        // Make two loops, one for left/up and one for right/down where we clone the board and move
                        for (int low = currVehicle.row; low >= low + LU; low--) {
                            Board clonedBoard = new Board(poppedBoard);
                            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
                            clonedBoard.moveVehicle(clonedVehicle, low);
                            String cloneKey = clonedBoard.generateBoardKey();
                            // If the hashmap hasn't seen this board before, then add the cloned board as the key
                            // and the popped board as the parent
                            if (isBoardNew(cloneKey)) {
                                System.out.println("Inserting new board " + cloneKey + " with parent " + poppedKey);
                                hashMap.put(cloneKey, poppedKey);
                                queue.add(clonedBoard);
                            } else {
                                System.out.println("Board " + cloneKey + " has already been seen.");
                            }
                        }
                    }

                    if (RD != 0) {
                        for (int high = currVehicle.row; high < high + RD; high++) {
                            Board clonedBoard = new Board(poppedBoard);
                            Vehicle clonedVehicle = clonedBoard.board[currVehicle.row][currVehicle.col];
                            clonedBoard.moveVehicle(clonedVehicle, high);
                            String cloneKey = clonedBoard.generateBoardKey();
                            // If the hashmap hasn't seen this board before, then add the cloned board as the key
                            // and the popped board as the parent
                            if (isBoardNew(cloneKey)) {
                                System.out.println("Inserting new board " + cloneKey + " with parent " + poppedKey);
                                hashMap.put(cloneKey, poppedKey);
                                queue.add(clonedBoard);
                            } else {
                                System.out.println("Board " + cloneKey + " has already been seen.");
                            }
                        }
                    }
                }
            }

            System.out.println("Popping the next item in the queue.");
        }
    }

    /**
     * Puts the first board that was read in from the file onto the queue and seeds the rest of the game
     * based on the info we were provided.
     */
    private void seedQueue() {
        numOfVehicles = poppedBoard.vehicles.size();
        queue.add(poppedBoard);
        String poppedKey = poppedBoard.generateBoardKey();
        hashMap.put(poppedKey, null);
    }

    /**
     * Prints out the current, active game board.
     */
    void print() {
        poppedBoard.print();
    }

    /*
    private void printSolution() {
        // Print out the game solution
    }
    */
}
