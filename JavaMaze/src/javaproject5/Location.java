package javaproject5;

/*
 *  @author Keeton Feavel, Cedarville University
 *  @version 1.0
 *  File: Location.java
 *  Created: March 17, 2018
 *  Copyright (c) Keeton Feavel & Cedarville University. All rights reserved.
 * 
 *  Class Description: Location is a basic class which holds information about
 *  its location in the maze as well as the type of location. These locations
 *  populate the 2D array which composes the maze.
 */
public class Location {
    // Declare class variables
    private Coordinate coord;
    private Location parent;
    private char type;
    private int distance;
    
    /**
     * Class constructor that sets the default coordinate to 0,0 and of
     * starting type.
     */
    public Location() {
        this(new Coordinate(), 'S', null);
    }
    
    /**
     * Class constructor that sets the default coordinate to 0,0 and of
     * starting type.
     * @param c Coordinate
     * @param t Location Type
     */
    public Location(Coordinate c, char t) {
        this(c, t, null);
    }
    
    /**
     * Class constructor that takes a Coordinate and type to be used in a Maze
     * @param c Coordinate to be used in location
     * @param t Type of location (i.e. Start, Target, Open, Wall)
     * @param p Parent location pointer.
     */
    public Location(Coordinate c, char t, Location p) {
        coord = c;
        type = t;
        parent = p;
        distance = -1;
    }
    
    /**
     * Returns the coordinate of the location.
     * @return the coordinate object
     */
    public Coordinate getCoordinate() {
        return coord;
    }
    
    /**
     * Returns the location's parent location.
     * @return Parent Location
     */
    public Location getParent() {
        return parent;
    }
    
    /**
     * Returns the type of location as a character.
     * @return the type character
     */
    public char getType() {
        return type;
    }
    
    /**
     * Returns the location's distance.
     * @return Distance in integer form.
     */
    public int getDistance() {
        return distance;
    }
    
    /**
     * Sets the location type (ideally used for marking previous locations with '!').
     * @param t Type to be set.
     * @throws java.lang.Exception
     */
    public void setType(char t) throws Exception {
        if (t == 'S' || t == 'T' || t == 'X' || t == '.' || t == '!') {
            type = t;
        } else {
            // This generic exception would be replaced by a proper exception
            // if this assignment called for proper error checking.
            throw new Exception("Attempted to set invalid location type.");
        }
    }
    
    /**
     * Sets the location's parent location.
     * @param p Parent location
     */
    public void setParent(Location p) {
        parent = p;
    }
    
    /**
     * Sets the location's distance.
     * @param d The distance in integer format.
     */
    public void setDistance(int d) {
        // Would normally do positive value error checking here
        distance = d;
    }
}
