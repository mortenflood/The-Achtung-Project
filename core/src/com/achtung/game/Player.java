package com.achtung.game;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Created by mortenflood on 29.02.16.
 */
public class Player {
    private Position position;
    private int speed, color;
    private ArrayList<Position> path;

    public Player(Position position, int speed, int color, ArrayList<Position> path) {
        this.position = position;
        this.speed = speed;
        this.color = color;
        this.path = path;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void addToPath(Position position) {
        this.path.add(position);
    }
}
