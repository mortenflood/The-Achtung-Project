package com.achtung.game.multiplayer;

import com.achtung.game.AchtungInputProcessor;
import com.achtung.game.Player;
import com.achtung.game.Position;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.json.JSONObject;

/**
 * Created by Malte on 06.03.2016.
 */
public class MultiplayerGameScreen implements Screen, WarpListener {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    Game game;
    private final int WORLD_WIDTH = 181;
    private final int WORLD_HEIGHT = 300;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    SpriteBatch batcher;
    World world;
    World.WorldListener worldListener;
    WorldRenderer renderer;
    int lastScore;
    String scoreString;
    private StartMultiplayerScreen prevScreen;
    private Player player;
    private AchtungInputProcessor processor;


    public MultiplayerGameScreen (Game game, StartMultiplayerScreen prevScreen) {
        this.game = game;
        this.prevScreen = prevScreen;
        state = GAME_RUNNING;
        guiCam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        guiCam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();
        int x = MathUtils.round(100* MathUtils.random());
        int y = MathUtils.round(100* MathUtils.random());
        player = new Player(x, y, 1, Color.BLUE);

        worldListener = new World.WorldListener() {
            @Override
            public void turn () {

            }

            @Override
            public void straightForward () {

            }

            @Override
            public void hit () {

            }
        };

        world = new World(worldListener);
        renderer = new WorldRenderer(batcher, world, player);
        lastScore = 0;
        scoreString = "SCORE: 0";

        processor = new AchtungInputProcessor(player);
        Gdx.input.setInputProcessor(processor);

        WarpController.getInstance().setListener(this);



    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        draw();
        update();
    }

    public void update(){

    }

    public void draw(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        guiCam.update();

        renderer.render();


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
        batcher.dispose();
    }

    @Override
    public void onWaitingStarted(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onGameStarted(String message) {

    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {

    }

    @Override
    public void onGameUpdateReceived(String message) {
        try {
            JSONObject data = new JSONObject(message);
            int x = (int)data.getDouble("x");
            int y = (int)data.getDouble("y");
            Position pos = new Position(x, y);
            renderer.addEnemyPos(pos);
//            renderer.addEnemyXY(x, y);

        } catch (Exception e) {
            // exception in onMoveNotificationReceived
        }
    }
}
