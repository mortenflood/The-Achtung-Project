package com.achtung.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Malte on 04.03.2016.
 */
public class GameScreen implements Screen {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    private int screenX = 1440;
    private int screenY = 2392;


    Game game;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    SpriteBatch batcher;
    private Texture left, right, left2, right2, exit;
    private ShapeRenderer renderer;

    private ArrayList<Player> players, losingplayers;
    private AchtungInputProcessor processor;
    private Player mainPlayer;
    private Player player2;
    private int gapCounter;
    private Player gapPlayer;
    private boolean motionControl = true;

    //checkcollision doesnt work
    //add listener to the buttons



    World world;
    //WorldListener worldListener;
    //WorldRenderer renderer;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    int lastScore;
    String scoreString;



    public GameScreen (Game game) {
        this.game = game;

        state = GAME_READY;
        guiCam = new OrthographicCamera(screenX, screenY);
        guiCam.position.set(screenX / 2, screenY / 2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();
        left = new Texture("leftarrowfinal.png");
        right = new Texture("rightarrowfinal.png");

        right2 = new Texture("leftarrowfinal.png");
        left2 = new Texture("rightarrowfinal.png");

        renderer = new ShapeRenderer();

        players = new ArrayList<Player>();
        losingplayers = new ArrayList<Player>();

        mainPlayer = new Player(screenX/2, screenY/2, 4, com.badlogic.gdx.graphics.Color.BLUE);
        player2 = new Player(screenX/2, screenY/4, 4, com.badlogic.gdx.graphics.Color.RED);

        players.add(mainPlayer);
        players.add(player2);

        processor = new AchtungInputProcessor(mainPlayer, player2);
        Gdx.input.setInputProcessor(processor);

    }



    @Override
    public void show() {

    }

    public void draw () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.setProjectionMatrix(guiCam.combined);
        renderer.setProjectionMatrix(guiCam.combined);

        guiCam.update();

        // renderer in combo with camera?

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        renderer.rectLine(10f, screenY / 10 + 5f, 10f, 9 * screenY / 10 - 5f, 10f);
        renderer.rectLine(10f, screenY / 10 + 5f, screenX - 10f, screenY / 10 + 5f, 10f);
        renderer.rectLine(10f, 9 * screenY / 10 - 5f, screenX - 10f, 9 * screenY / 10 - 5f, 10f);
        renderer.rectLine(screenX - 10f, screenY / 10 + 5f, screenX - 10f, 9 * screenY / 10 - 5f, 10f);
        renderer.end();

        batcher.begin();
        batcher.draw(left, 0f, 0f, 4 * screenX / 10, screenY / 10);
        batcher.draw(right, 6 * screenX / 10, 0f, 4 * screenX / 10, screenY / 10);

        batcher.draw(right2, 0f, screenY - screenY / 10, 4 * screenX / 10, screenY / 10);
        batcher.draw(left2, 6 * screenX / 10, screenY - screenY / 10, 4 * screenX / 10, screenY / 10);

        //sprites.draw(exit, Gdx.graphics.getWidth()/2, 500f, 300f, 300f);
        batcher.end();

        for (Player p : players) {
            if (checkCollision(p)) {
                p.setSpeed(0);
                //improve performance if needed
                //remove from game
                // make new list with all players, remove from list, add to new?
                losingplayers.add(p);
            }

            //update players list, remove losingplayers

            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(p.getColor());
            for (Position pos : p.getPath()) {
                renderer.circle(pos.getxPos(), pos.getyPos(), 5);
            }
            renderer.circle(p.getxPos(), p.getyPos(), 5);
            renderer.end();
        }
    }

    public void update() {

        //Accelerometer used to control with phone motion
        if (this.motionControl = true) {
            Gdx.app.log("ACCELEROMETER", Float.toString(Gdx.input.getAccelerometerX()));

            int threshold = 3;
            if (Gdx.input.getAccelerometerX() > threshold ) {
                mainPlayer.setMoveLeft(true);
            }

            else if (Gdx.input.getAccelerometerX() < -threshold ) {
                mainPlayer.setMoveRight(true);
            }

            else if (Gdx.input.getAccelerometerX() < threshold && Gdx.input.getAccelerometerX() > -threshold ) {
                mainPlayer.setMoveLeft(false);
                mainPlayer.setMoveRight(false);
            }

        }

        for (Player p : players) {

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
                    gapPlayer = p;
                    return;
                }
            }

            if (!p.equals(gapPlayer)) {
                p.addToPath(new Position(p.getxPos(), p.getyPos()));
            }

            if (p.equals(gapPlayer)) {
                gapCounter++;
                if (gapCounter >= 30) {
                    gapCounter = 0;
                    gapPlayer = null;
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    public boolean checkCollision(Player p) {

        if(p.getxPos() <= 19f || p.getxPos() >= screenX - 19f
                || p.getyPos() <= screenY/10 + 19f || p.getyPos() >= screenY - 19) {
            return true;
        }

        for(Player plyr:players) {


            if (p.equals(plyr) && p.getPath().size() > 10) {
                for(int i = 0; i < p.getPath().size() - 10; i++ ) {
                    if((p.getxPos() < p.getPath().get(i).getxPos() + 6 && p.getxPos() > p.getPath().get(i).getxPos() - 6)
                            && (p.getyPos() < p.getPath().get(i).getyPos() + 6 && p.getyPos() > p.getPath().get(i).getyPos() - 6)) {
                        return true;
                    }
                }
            }

            if(p.equals(plyr)) {
                continue;
            }

            for(Position pos:plyr.getPath()) {
                if((p.getxPos() < pos.getxPos() + 6 && p.getxPos() > pos.getxPos() - 6)
                        && (p.getyPos() < pos.getyPos() + 6 && p.getyPos() > pos.getyPos() - 6)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
