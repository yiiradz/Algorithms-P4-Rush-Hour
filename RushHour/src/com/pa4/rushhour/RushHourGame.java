package com.pa4.rushhour;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class RushHourGame {
    private Queue<Board> queue;
    private HashMap<String, String> hashMap;
    private int numOfVehicles;
    public Board poppedBoard;

    /**
     * Initialize all of the game requirements
     */
    public RushHourGame() {
        // Initialize the board as a 6x6 2D array of vehicles
        poppedBoard = new Board();
        queue = new ArrayDeque<Board>();
        hashMap = new HashMap<String, String>(); // The strings will be a walkthrough of the vehicle ids
    }

    /**
     * Checks if we've already seen a particular board layout.
     * @param str Hashmap key to be inspected.
     * @return Returns true if the board orientation has been seen before.
     */
    public boolean hasBoardBeenSeen(String str) {
        return (hashMap.containsKey(str));
    }

    /**
     * Starts the game solving process.
     */
    public void start() {
        // Begin by enqueueing the very first board
        seedQueue();
        // While the queue is not empty and the game is not done
        while (!queue.isEmpty() && !poppedBoard.isGameDone()) {
            // Update the popped board
            // ONLY UPDATES AFTER EVERY POSSIBLE MOVE HAS BEEN MADE!!
            poppedBoard = queue.remove();
            String poppedKey = poppedBoard.generateBoardKey();

            // For each vehicle that exists on the board
            // CLONES THE CURRENT BOARD EACH TIME. ALL MODIFICATIONS SHOULD BE MADE TO "clonedBoard"!!
            for (int vehicleIndex = 0; vehicleIndex < numOfVehicles; vehicleIndex++) {
                Vehicle currVehicle = poppedBoard.vehicles[vehicleIndex];
                String direction = currVehicle.direction;
                int LU; // - Left/up (lower bound)
                int DR; // + Right/down (upper bound)
                Board clonedBoard = new Board(poppedBoard);

            }
        }
    }

    /**
     * Puts the first board that was read in from the file onto the queue and seeds the rest of the game
     * based on the info we were provided.
     */
    void seedQueue() {
        numOfVehicles = poppedBoard.vehicles.length;
        queue.add(poppedBoard);
        String poppedKey = poppedBoard.generateBoardKey();
        hashMap.put(poppedKey, null);
    }

    /**
     * Prints out the current, active game board.
     */
    public void print() {
        poppedBoard.print();
    }

    /**
     * Prints the solution sequence to prove we beat the game.
     */
    private void printSolution() {
        // Print out the game solution
    }
}
