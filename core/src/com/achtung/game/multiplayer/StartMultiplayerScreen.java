package com.achtung.game.multiplayer;

import com.achtung.game.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.awt.Color;

/**
 * Created by Malte on 06.03.2016.
 */
public class StartMultiplayerScreen implements Screen, WarpListener{

    Game game;

    OrthographicCamera guiCam;
    SpriteBatch batcher;
    Rectangle backBounds;
    Vector3 touchPoint;

    private final int WORLD_HEIGHT = 2392;
    private final int WORLD_WIDTH = 1440;

    float xOffset = 0;

    private final String[] tryingToConnect = {"Connecting","to AppWarp"};
    private final String[] waitForOtherUser = {"Waiting for","other user"};
    private final String[] errorInConnection = {"Error in","Connection", "Go Back"};

    private final String[] game_win = {"Congrats You Win!", "Enemy Defeated"};
    private final String[] game_loose = {"Oops You Loose!","Target Achieved","By Enemy"};
    private final String[] enemy_left = {"Congrats You Win!", "Enemy Left the Game"};

    private String[] msg = tryingToConnect;

    private BitmapFont font;
    private Texture left, right, exit;
    private ShapeRenderer renderer;

    public StartMultiplayerScreen (Game game) {
        this.game = game;

        guiCam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        guiCam.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        backBounds = new Rectangle(0, 0, 128, 128);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();
        xOffset = 80;
        font = new BitmapFont();
        left = new Texture("leftarrowfinal.png");
        right = new Texture("rightarrowfinal.png");
        renderer = new ShapeRenderer();
        WarpController.getInstance().setListener(this);

    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                WarpController.getInstance().handleLeave();
                return;
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();

        batcher.setProjectionMatrix(guiCam.combined);
        batcher.disableBlending();
        batcher.begin();
        batcher.draw(left, 0f, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        batcher.draw(right, 6 * WORLD_WIDTH / 10, 0f, 4 * WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        batcher.end();


        batcher.enableBlending();
        batcher.begin();

        font.setColor(com.badlogic.gdx.graphics.Color.CYAN);
        font.getData().setScale(5, 5);
        font.draw(batcher, "Trying to connect", WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        //batcher.draw(Assets.arrow, 0, 0, 64, 64);
        batcher.end();
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }

    @Override
    public void show() {

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

    @Override
    public void onError (String message) {
        this.msg = errorInConnection;
        update();
    }

    @Override
    public void onGameStarted (String message) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                game.setScreen(new MultiplayerGameScreen(game)); //StartMultiplayerScreen.this));
            }
        });

    }

    @Override
    public void onGameFinished (int code, boolean isRemote) {
        if(code==WarpController.GAME_WIN){
            this.msg = game_loose;
        }else if(code==WarpController.GAME_LOOSE){
            this.msg = game_win;
        }else if(code==WarpController.ENEMY_LEFT){
            this.msg = enemy_left;
        }
        update();
        game.setScreen(this);
    }

    @Override
    public void onGameUpdateReceived (String message) {

    }

    @Override
    public void onWaitingStarted(String message) {
        this.msg = waitForOtherUser;
        update();
    }
}
