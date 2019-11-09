/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.rushhour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Yayira and Keeton
 */
public class MainClass {
    // Class variables
    private static RushHourGame game;

    public static void main(String[] args) {
        game = new RushHourGame();
        boolean isGradel = false;
        // If gradel mode, read in from STDIN
        if (isGradel) {
            Scanner sc = new Scanner(System.in);
            createGameFromFile(sc);
            // Else read in from a file
        } else {
            //String path = "/Users/yiradz/College/JUN_SEM1/GALGORITMS/Algorithms-P4-Rush-Hour/RushHour/input.txt";
            //String path = "/Users/keeton/Documents/Algorithms-P4-Rush-Hour/RushHour/input.txt";
            String path = "/home/keeton/Documents/Algorithms-P4-Rush-Hour/RushHour/input.txt";
            openFile(path);
        }
        // Start the game
        game.start();
    }

    private static void createGameFromFile(Scanner input) {
        // Read in the number of cars and trucks
        int total_vehicles = input.nextInt();
        // For each vehicle, read the next 5 lines and create a new Vehicle
        for (int current_vehicle = 0; current_vehicle < total_vehicles; current_vehicle++) {
            // Grab all of the values
            String type = input.next();
            String color = input.next();
            String orientation = input.next();
            int row = input.nextInt() - 1; // Convert to index
            int col = input.nextInt() - 1; // Convert to index
            Vehicle vehicle = new Vehicle(type, color, orientation, row, col, current_vehicle);
            vehicle.print();
            // Validate the vehicle, print, and insert
            if (vehicle.isValid()) {
                System.out.println("Vehicle is valid!");
                // Insert the vehicle with replace mode set to false since we're doing a first time insert.
                if (!game.poppedBoard.insertVehicle(vehicle, false)) {
                    System.out.println("Unable to insert vehicle onto the board!");
                }
            }
            System.out.println();
        }
    }

    private static void openFile(String s) {
        try {
            Scanner sc = new Scanner(new File(s));
            createGameFromFile(sc);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(2);
        }
    }
}
