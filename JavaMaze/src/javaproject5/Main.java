package javaproject5;

/*
 *  @author Keeton Feavel, Cedarville University
 *  @version 1.0
 *  File: Main.java
 *  Created: March 17, 2018
 *  Copyright (c) Keeton Feavel & Cedarville University. All rights reserved.
 * 
 *  Class Description: The main class used to load and solve a maze using a
 *  queue from the default Java library (or a queue of my own creation if so
 *  desired.)
 */
public class Main {
    // Declare class variables
    static Maze maze;
    
    /**
     * The main function to be called from a command line.
     * The same code could be used in a JUnit test if so desired.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO: Replace file with args[0]
        String file = "/Users/keeton/Google Drive/Cedarville/2018 Spring/Java/Project 5/project5_maze.i2";
        MazeParser parser;
        // Try to load in file and solve the maze.
        parser = new MazeParser(file);
        maze = parser.parseFile();
        // Solve the maze. Solution will be printed automatically.
        maze.solve();
    }
    
}
