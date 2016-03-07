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

import java.util.ArrayList;

/**
 * Created by Malte on 06.03.2016.
 */
public class WorldRenderer {

    static final float WORLD_WIDTH = 1440;
    static final float WORLD_HEIGHT = 2392;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    Texture left, right;
    ShapeRenderer renderer;


    // remote enemy co-ordinate
    private ArrayList<Position> enemyPos;
    private int counter;
    private Player p;

    public WorldRenderer (SpriteBatch batch, World world, Player p) {
        this.world = world;
        this.cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.cam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        this.batch = batch;
        this.p = p;
        enemyPos = new ArrayList<Position>();
        renderer = new ShapeRenderer();
        left = new Texture("leftarrowfinal.png");
        right = new Texture("rightarrowfinal.png");
        counter = 0;
    }




    public void render() {

        cam.update();

        batch.setProjectionMatrix(cam.combined);
        renderer.setProjectionMatrix(cam.combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        renderer.rectLine(10f, WORLD_HEIGHT / 10, 10f, WORLD_HEIGHT - 5f, 10f);
        renderer.rectLine(10f, WORLD_HEIGHT / 10 + 5f, WORLD_WIDTH - 10f, WORLD_HEIGHT / 10 + 5f, 10f);
        renderer.rectLine(10f, WORLD_HEIGHT - 10f, WORLD_WIDTH - 10f, WORLD_HEIGHT - 10f, 10f);
        renderer.rectLine(WORLD_WIDTH - 10f, WORLD_HEIGHT / 10, WORLD_WIDTH - 10f, WORLD_HEIGHT - 5f, 10f);
        renderer.end();

        batch.begin();
        batch.draw(left, 0f, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        batch.draw(right, 6 * WORLD_WIDTH / 10, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        //sprites.draw(exit, Gdx.graphics.getWidth()/2, 500f, 300f, 300f);
        batch.end();

        update();

        renderPlayer();
        renderEnemy();

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


    }

    public void renderPlayer() {
        //call sendLocation so it updates
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(p.getColor());
        for(Position pos:p.getPath()) {
            renderer.circle(pos.getxPos(), pos.getyPos(), 5);
        }
        renderer.end();

        sendLocation(p.getxPos(), p.getyPos());

    }

    public void renderEnemy() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        for (Position pos:enemyPos) {
            renderer.circle(pos.getxPos(), pos.getyPos(), 5);
        }
        renderer.end();
    }

    public void addEnemyPos(Position pos) {
        enemyPos.add(pos);
    }

    private void sendLocation(int x, int y){
        counter++;
        try {
            JSONObject data = new JSONObject();
            data.put("x", x);
            data.put("y", y);

            if(counter%10==0){
                counter=0;
                WarpController.getInstance().sendGameUpdate(data.toString());
            }
        } catch (Exception e) {
            // exception in sendLocation
        }
    }
}
