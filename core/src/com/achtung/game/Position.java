package com.achtung.game;

/**
 * Created by mortenflood on 29.02.16.
 */
public class Position {

    private int xPos;
    private int yPos;

    public Position(int xPos, int yPos) {
        this.yPos = yPos;
        this.xPos = xPos;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (xPos != position.xPos) return false;
        return yPos == position.yPos;

    }

    @Override
    public int hashCode() {
        int result = xPos;
        result = 31 * result + yPos;
        return result;
    }
}
