package com.pa4.rushhour;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class RushHourGame {
    Queue<Board> queue;
    HashMap<String, String> hashMap;
    Board activeBoard;

    /**
     * Initialize all of the game requirements
     */
    public RushHourGame() {
        // Initialize the board as a 6x6 2D array of vehicles
        activeBoard = new Board();
        queue = new ArrayDeque<Board>();
        hashMap = new HashMap<String, String>(); // The strings will be a walkthrough of the vehicle ids
    }

    public boolean hasPathBeenSeen(String str) {
        return (hashMap.containsKey(str));
    }

    public void start() {
        // Enqueue the board we just created and add it to the map with a parent of null
        queue.add(activeBoard);
        String parentStr = activeBoard.generateBoardKey();
        hashMap.put(parentStr, null);
        // While the queue is not empty and the game is not done
        while (!queue.isEmpty() && !activeBoard.isGameDone()) {
            // Remove the last board from the queue and identify it as the parent
            // Generate a new board as the parent
            Board parentBoard = queue.remove();
            String parentKey = parentBoard.generateBoardKey();
            // Check adjacency and then insert them into the hashmap.
            // Cycle through all vehicles on the board and find their adjacent locations to enqueue.
            int numOfVehicles = activeBoard.vehicles.length;
            for (int vehicleIndex = 0; vehicleIndex < numOfVehicles; vehicleIndex++) {
                Vehicle current = activeBoard.vehicles[vehicleIndex];
                // Now that we have a vehicle to check adjacent locations with, clone the board, move to an
                // adjacent location, enqueue it, add that board's key to the hashmap with this board as the parent.
                // Set the active board to now be the dequeued board
                activeBoard = new Board(parentBoard);
                // TODO: Inset check for adjacent locations here
                // ...
                // Do our moves and then generate a key
                String activeKey = activeBoard.generateBoardKey();
            }
        }
    }

    /**
     * Prints out the current, active game board.
     */
    public void print() {
        activeBoard.print();
    }

    /**
     * Prints the solution sequence to prove we beat the game.
     */
    private void printSolution() {
        // Print out the game solution
    }
}
