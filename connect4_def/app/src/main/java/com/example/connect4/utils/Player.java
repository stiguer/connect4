package com.example.connect4.utils;

public class Player{

    private final String name;
    private final int colour ;

    public Player(String name, int colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public int getColour() {
        return colour;
    }
}
