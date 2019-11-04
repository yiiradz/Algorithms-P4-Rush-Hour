package javaproject5;
import java.util.Queue;
import java.util.LinkedList;

/*
 *  @author Keeton Feavel, Cedarville University
 *  @version 1.0
 *  File: Maze.java
 *  Created: March 17, 2018
 *  Copyright (c) Keeton Feavel & Cedarville University. All rights reserved.
 * 
 *  Class Description: The maze class contains a 2D layout containing all of the
 *  information about open locations, walls, starting, and ending points.
 */
public class Maze {
    // Declare class variables
    private Location layout[][];
    private Queue<Location> solution;
    private Location start;     // Starting location
    private Location finish;    // Finishing loction - unknown until solved
    private int rowSize;        // Used for bounds checking
    private int colSize;        // Used for bounds checking
    private int distance;       // Total distance of the maze solution
    
    /**
     * Class constructor which takes a 2D Location array and sets
     * the start Location to 0,0 of starting type.
     * @param m 2D Location array for maze
     * @param rs Number of rows
     * @param cs Number of columns
     */
    public Maze(Location m[][], int rs, int cs) {
        this(m, new Location(), rs, cs);
    }
    
    /**
     * Class constructor which takes a 2D Location array and a start Coordinate
     * @param m 2D location array for maze construction
     * @param s Starting coordinate for the maze
     * @param rs Number of rows
     * @param cs Number of columns
     */
    public Maze(Location m[][], Location s, int rs, int cs) {
        solution = new LinkedList<>();
        layout = m;
        start = s;
        finish = null;
        rowSize = rs;
        colSize = cs;
    }
    
    /**
     * Begins to solve the maze and prints the solution once one has been found.
     * Solution status will be printed once the solution has been evaluated. 
     */
    public void solve() {
        // Start at the start location and work from there
        boolean solved = trailBlazer();
        printSolution(solved);
    }
    
    /**
     * Function for finding the next space to take when solving the maze.
     * @return Boolean indicating whether the maze is solvable
     */
    private boolean trailBlazer() {
        boolean solved = false;
        // Add the starting location to the queue.
        solution.add(start);
        // Check if the current location is valid
        while (!solution.isEmpty()) {
            // Grab the last location off of the queue and continue
            Location cur = solution.poll();
            Coordinate coord = cur.getCoordinate();
            distance = cur.getDistance();
            // Get row and col values for bounds checking
            int row = 0;
            int col = 0;
            if (coord != null) {
                row = coord.getRow();
                col = coord.getCol();
            }
            // If the current location is the target
            if (cur.getType() == 'T') {
                // Set the finish location, set solved to true and break
                finish = cur;
                solved = true;
                break;
            }
            Location visit;
            // Search in all four directions for valid location
            // Down
            if (valid(row+1, col)) {
                visit = layout[row+1][col];
                if (visit.getDistance() == -1) {
                    visit.setDistance(distance+1);
                    visit.setParent(cur);
                    solution.add(visit);
                }
            }
            // Right
            if (valid(row, col+1)) {
                visit = layout[row][col+1];
                if (visit.getDistance() == -1) {
                    visit.setDistance(distance+1);
                    visit.setParent(cur);
                    solution.add(visit);
                }
            }
            // Up
            if (valid(row-1, col)) {
                visit = layout[row-1][col];
                if (visit.getDistance() == -1) {
                    visit.setDistance(distance+1);
                    visit.setParent(cur);
                    solution.add(visit);
                }
            }
            // Left
            if (valid(row, col-1)) {
                visit = layout[row][col-1];
                if (visit.getDistance() == -1) {
                    visit.setDistance(distance+1);
                    visit.setParent(cur);
                    solution.add(visit);
                }
            }
        }
        // Solved will still be false if all the elements
        // are dequeued and no 'T' was found. The only time solved
        // is set is when 'T' is found, otherwise it remains false.
        return solved;
    }
    
    /**
     * Checks whether a certain row, col index is valid. Used for bounds checking.
     * @param row Row value to check
     * @param col Column value to check
     * @return Boolean value indicating whether in inputted row, col is valid
     */
    private boolean valid(int row, int col) {
        // Initially false if not valid
        boolean result = false;
        // If the coordinates are within the bounds
        if (row >= 0 && row <= (rowSize-1) && col >= 0 && col <= (colSize-1)) {
            // If it is a valid type (i.e. not a wall)
            Location loc = layout[row][col];
            Coordinate coord = loc.getCoordinate();
            char type = loc.getType();
            if (type == '.' || type == 'S' || type == 'T') {
                result = true;
            }
        }
        // Return validity result
        return result;
    }
    
    /**
     * Print all of the necessary solution details, such as path and distance.
     * @param solvable Flag which indicates that the maze is solvable.
     */
    private void printSolution(boolean solvable) {
        // If the maze was solvable
        if (solvable) {
            // Get last item in queue (which should be target)
            printLinkedListReverse(finish);
            System.out.println("Total Distance: " + finish.getDistance());
        // If the maze was unsolvable
        } else {
            System.out.println("No known solution could be found for the given maze.");
        }
    }
    
    /**
     * Prints a linked list of locations in their proper order, going from start to finish.
     * Recursive function - call once with finish (last) location.
     * @param loc Finish location
     */
    private void printLinkedListReverse(Location loc) {
        if (loc == null) {
            // Exit the loop
        } else {
            printLinkedListReverse(loc.getParent());
            Coordinate coord = loc.getCoordinate();
            int row = coord.getRow();
            int col = coord.getCol();
            String print = "<" + row + "," + col + ">";
            System.out.println(print);
        }
    }
}
