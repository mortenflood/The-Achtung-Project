package com.achtung.game.multiplayer;

import com.achtung.game.Player;
import com.achtung.game.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import sun.rmi.runtime.Log;

/**
 * Created by Malte on 06.03.2016.
 */
public class WorldRenderer {

    static final float WORLD_WIDTH = 720;
    static final float WORLD_HEIGHT = 1184;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    Texture left, right;
    ShapeRenderer renderer;
    private int enemyX, enemyY;


    // remote enemy co-ordinate
    private ArrayList<Position> enemyPos;
    private ArrayList<Position> toBeAdded;
    private boolean isAdding;
    private boolean isChecking;
    private int counter;
    private Player p;
    private ArrayList<Integer> newEnemyPos;
    private ArrayList<Integer> newToBeAdded;
    private float boundLines;
    private float playerThickness;
    private int gapCounter;
    private boolean isGap;
    protected boolean isGameOver;

    public WorldRenderer (SpriteBatch batch, World world, Player p) {
        this.world = world;
        this.cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.cam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        this.batch = batch;
        this.p = p;
        enemyPos = new ArrayList<Position>();
        toBeAdded = new ArrayList<Position>();
        newEnemyPos = new ArrayList<Integer>();
        newToBeAdded = new ArrayList<Integer>();
        isAdding = false;
        renderer = new ShapeRenderer();
        left = new Texture("leftarrowfinal.png");
        right = new Texture("rightarrowfinal.png");
        counter = 0;
        boundLines = 8f;
        playerThickness = 4f;
        gapCounter = 30;
        isGap = false;
        isGameOver = false;
    }

    // checkcollision / done?
    // gaps
    // random start point needs fixing / done?
    // what happends when game over?





    public void render() {

        cam.update();

        batch.setProjectionMatrix(cam.combined);
        renderer.setProjectionMatrix(cam.combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        renderer.rectLine(5f, WORLD_HEIGHT / 10, 5f, WORLD_HEIGHT - 5f, boundLines);
        renderer.rectLine(5f, WORLD_HEIGHT / 10 + 4f, WORLD_WIDTH - 5f, WORLD_HEIGHT / 10 + 4f, boundLines);
        renderer.rectLine(1f, WORLD_HEIGHT - 5f, WORLD_WIDTH - 1f, WORLD_HEIGHT - 5f, boundLines);
        renderer.rectLine(WORLD_WIDTH - 5f, WORLD_HEIGHT / 10, WORLD_WIDTH - 5f, WORLD_HEIGHT - 5f, boundLines);
        renderer.end();

        batch.begin();
        batch.draw(left, 0f, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        batch.draw(right, 6 * WORLD_WIDTH / 10, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        //sprites.draw(exit, Gdx.graphics.getWidth()/2, 500f, 300f, 300f);
        batch.end();

        update();

        if (checkCollision(p)) {
            // stop game if true
            // write winner
            // go to main menu
            // empty game room
            isGameOver = true;
            p.setSpeed(0);
        }


        renderPlayer();
        renderEnemy();

    }

    public boolean checkCollision(Player p) {

        float width = (5f + boundLines/2 + playerThickness - 1);
        float height = (5f + boundLines/2 + playerThickness - 1);


        if(p.getxPos() <= width || p.getxPos() >= WORLD_WIDTH - width
                || p.getyPos() <= WORLD_HEIGHT/10 + height || p.getyPos() >= WORLD_HEIGHT - height) {
            return true;
        }


        if (p.getPath().size() > 10) {
            for(int i = 0; i < p.getPath().size() - 10; i++ ) {
                if((p.getxPos() < p.getPath().get(i).getxPos() + (playerThickness*2 - 1)
                        && p.getxPos() > p.getPath().get(i).getxPos() - (playerThickness*2 - 1))
                        && (p.getyPos() < p.getPath().get(i).getyPos() + (playerThickness*2 - 1)
                        && p.getyPos() > p.getPath().get(i).getyPos() - (playerThickness*2 - 1))) {
                    return true;
                }
            }
        }

        enemyPos.addAll(toBeAdded);
        toBeAdded.clear();
        isChecking = true;
        for(Position pos:enemyPos) {
            if((p.getxPos() < pos.getxPos() + (playerThickness*2 - 1)
                    && p.getxPos() > pos.getxPos() - (playerThickness*2 - 1))
                    && (p.getyPos() < pos.getyPos() + (playerThickness*2 - 1)
                    && p.getyPos() > pos.getyPos() - (playerThickness*2 - 1))) {
                return true;
            }
        }
        isChecking = false;
        return false;
    }

    public void update() {

        if (p.isMoveLeft()) {
            p.setRadians(p.getRadians() + p.getRotationSpeed());
        } else if (p.isMoveRight()) {
            p.setRadians(p.getRadians() - p.getRotationSpeed());
        }

        p.setxPos(MathUtils.round(p.getxPos() + (MathUtils.cos(p.getRadians()) * p.getSpeed())));
        p.setyPos(MathUtils.round(p.getyPos() + (MathUtils.sin(p.getRadians()) * p.getSpeed())));


        //sometimes dont add to path
        //to create illusion of not being drawn

        if (gapCounter == 0) {
            if (MathUtils.random() > 0.99) {
                gapCounter++;
                isGap = true;
                return;
            }
            else {
                isGap = false;
                p.addToPath(new Position(p.getxPos(), p.getyPos()));
            }
        }

        if (gapCounter != 0) {
            gapCounter++;
            if (gapCounter >= 30) {
                gapCounter = 0;
            }
        }
    }

    public void renderPlayer() {
        //call sendLocation so it updates
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(p.getColor());
        for(Position pos:p.getPath()) {
            renderer.circle(pos.getxPos(), pos.getyPos(), playerThickness);
        }

        renderer.circle(p.getxPos(), p.getyPos(), playerThickness);
        renderer.end();

        sendLocation(p.getxPos(), p.getyPos());

    }


    public void renderEnemy() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        enemyPos.addAll(toBeAdded);
        //newEnemyPos.addAll(newToBeAdded);
        newToBeAdded.clear();
        isAdding = true;
        for (Position pos:enemyPos) {
            renderer.circle(pos.getxPos(), pos.getyPos(), playerThickness);
        }

//        for(int i = 0; i < newEnemyPos.size() - 1; i = i + 2) {
//            renderer.circle(newEnemyPos.get(i), newEnemyPos.get(i + 1), 5);
//        }

        renderer.circle(enemyX, enemyY, playerThickness);
        isAdding = false;

//        for (Iterator<Position> iterator = enemyPos.iterator(); iterator.hasNext(); ) {
//            Position pos = iterator.next();
//            if (!pos.equals(null)) {
//                renderer.circle(pos.getxPos(), pos.getyPos(), 5);
//            }
//        }

        renderer.end();
    }

    public void addEnemyPos(Position pos, boolean isGap) {
        if ((isAdding || isChecking) && !isGap) {
            toBeAdded.add(pos);
        }
        else if (!isGap){
            enemyPos.add(pos);
        }
        enemyX = pos.getxPos();
        enemyY = pos.getyPos();

    }

    public void addEnemyXY(int x, int y) {
        if (isAdding) {
            newToBeAdded.add(x);
            newToBeAdded.add(y);
        }
        else {
            newEnemyPos.add(x);
            newEnemyPos.add(y);
        }

        enemyX = x;
        enemyY = y;

    }

    private void sendLocation(int x, int y){
        try {
            JSONObject data = new JSONObject();
            data.put("x", x);
            data.put("y", y);
            data.put("isGap", isGap);
            data.put("isGameOver", isGameOver);
            WarpController.getInstance().sendGameUpdate(data.toString());

        } catch (Exception e) {
            // exception in sendLocation
            System.out.print("hmm");
        }
    }
}
