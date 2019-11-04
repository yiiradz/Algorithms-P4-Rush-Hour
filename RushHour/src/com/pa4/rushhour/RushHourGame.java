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
        // Enqueue the board we just created
        queue.add(activeBoard);
        // While the queue is not empty
        while (!queue.isEmpty()) {
            Board copy = new Board(activeBoard);
            // Check adjacencies and then insert them into the hashmap.
            // Generate a new board with the moved component, not overwritting the current one, then
            // we need to generate the new board's string, then insert that as the key and the value is
            // the current board (i.e. the parent board)
        }
        System.out.println(activeBoard.generateBoardKey());
    }

    /**
     * Prints out the current, active game board.
     */
    public void print() {
        activeBoard.print();
    }
}
