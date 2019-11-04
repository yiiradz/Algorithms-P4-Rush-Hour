package javaproject5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  @author Keeton Feavel, Cedarville University
 *  @version 1.0
 *  File: MazeParser.java
 *  Created: March 17, 2018
 *  Copyright (c) Keeton Feavel & Cedarville University. All rights reserved.
 * 
 *  Class Description: Parses an input file (usually of extension ".in") and
 *  returns a Maze value from the constructor of the parsed file. This class
 *  is more of a utility than it is a class, much like java.util.Scanner is.
 */
public class MazeParser {
    // Declare class variables
    private Scanner scan;
    
    /**
     * Class constructor which takes a text filename of the maze to be parsed.
     * @param file Input filename to be parsed
     */
    public MazeParser(String file) {
        // Try to load file and continue
        File in = new File(file);
        // Netbeans made by do a try-catch block here
        try {
            scan = new Scanner(in);
        }
        // Catch "File not found" and print the error to the user.
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            // Unable to continue so exit cleanly.
            System.exit(1);
        }
    }
    
    /**
     * Parses the file assigned to the Scanner in the constructor.
     * @return Maze Returns parsed file into a Maze class
     */
    public Maze parseFile() {
        // Initialize all variables to blank values in the event scanner fails
        Maze maze;
        Location layout[][] = new Location[0][0];
        Location start = new Location();
        int rowSize = 0; // Maze row size
        int colSize = 0; // Maze col size
        // Scan for integers at beginning of file that define size
        while (scan.hasNextInt()) {
            // Because we have no error checking per the assignment details,
            // it is safe to assume that the first two parts of the file will
            // be the row and column integers.
            rowSize = scan.nextInt();
            colSize = scan.nextInt();
            // Initialize layout variable to be array of size row*col
            layout = new Location[rowSize][colSize];
        }
        // While the file still has any of the accepted maze types
        for (int i = 0; i < rowSize; i++) {
            // Get next token and check validity
            String line = scan.next();
            // Go through the line and grab all characters and create locations
            for (int j = 0; j < colSize; j++) {
                // Get the character and create a coordinate and location pair
                char token = line.charAt(j);
                Coordinate coord = new Coordinate(i, j);
                Location loc = new Location(coord, token);
                layout[i][j] = loc;
                // Set start value if the token is 'S'
                if ('S' == token) {
                    Coordinate c = new Coordinate(i, j);
                    start = new Location(c, token);
                    // Set the distance to 0 and not -1 so that the solving
                    // algorithm does not overlook it or think it is "unfound"
                    start.setDistance(0);
                }
            }
        }
        // Construct maze and return it
        maze = new Maze(layout, start, rowSize, colSize);
        return maze;
    }
}
