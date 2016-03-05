package com.achtung.game;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Created by mortenflood on 29.02.16.
 */
public class Player {

    private Position position;
    private int speed;
    private int xPos;
    private int yPos;
    private int dx;
    private boolean moveLeft;
    private boolean moveRight;



    private int dy;
    private com.badlogic.gdx.graphics.Color color;
    private ArrayList<Position> path;
    protected float radians;
    protected float rotationSpeed;

    public Player(int xPos, int yPos, int speed, com.badlogic.gdx.graphics.Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.color = color;
        path = new ArrayList<Position>();
        radians = 3.1415f/2;
        rotationSpeed = 0.1f;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        if(moveRight && moveLeft) {
            moveRight = false;
        }
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        if(moveLeft && moveRight) {
            moveLeft = false;
        }
        this.moveRight = moveRight;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getRadians() {
        return radians;
    }

    public void setRadians(float radians) {
        this.radians = radians;
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

    public com.badlogic.gdx.graphics.Color getColor() {
        return color;
    }

    public void setColor(com.badlogic.gdx.graphics.Color color) {
        this.color = color;
    }

    public ArrayList<Position> getPath() {
        return path;
    }

    public void addToPath(Position position) {
        this.path.add(position);
    }
}
