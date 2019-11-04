package javaproject5;

/*
 *  @author Keeton Feavel, Cedarville University
 *  @version 1.0
 *  File: Coordinate.java
 *  Created: March 17, 2018
 *  Copyright (c) Keeton Feavel & Cedarville University. All rights reserved.
 * 
 *  Class Description: Coordinate is a simple class used to store an x,y or
 *  row, column pair of coordinates. This class mainly serves to be used in
 *  Location or other Location-like classes.
 */
public class Coordinate {
    // Declare class variables
    private int row;
    private int col;
    
    /**
     * Class constructor which sets the row and column to a default (0,0)
     */
    public Coordinate() {
        // Default constructor
        this(0, 0);
    }
    
    /**
     * Class constructor which takes in a row and column value
     * @param x Row value
     * @param y Column value
     */
    public Coordinate(int x, int y) {
        // Set the rows and column values
        this.row = x;
        this.col = y;
    }
    
    /**
     * Returns the value of row as an integer
     * @return row integer value
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Returns the value of column as an integer
     * @return column integer value
     */
    public int getCol() {
        return col;
    }
}
