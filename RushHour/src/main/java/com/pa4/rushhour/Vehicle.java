/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pa4.rushhour;

/**
 *
 * @author yiradz
 */
public class Vehicle {

    public String type = "";         // car or truck
    public String color = "";       // red, blue, purple, etc.
    public String direction = "";  // horizontal(h) or vertical(v)
    public int row = 0;
    public int col = 0;

    public Vehicle() {
        this.type = "";
        this.color = "";
        this.direction = "";
        this.row = 0;
        this.col = 0;

    }

    //public Vehicle(String t){
    //    this.type = t;
    //    this.color = "";
    //    this.direction = "";
    //    this.row = 0;
    //    this.col = 0;
    //
    //}
    public Vehicle(String t, String c, String d, int row, int col) {
        this.type = t;
        this.color = c;
        this.direction = d;
        this.row = row;
        this.col = col;

    }

}
