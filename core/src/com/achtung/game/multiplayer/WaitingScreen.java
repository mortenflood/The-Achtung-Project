package com.achtung.game.multiplayer;

/**
 * Created by mortenflood on 08.03.16.
 */
import com.achtung.game.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by mortenflood on 08.03.16.
 */
public class WaitingScreen implements Screen, WarpListener {

    Game game;

    float xOffset = 0;

    private final String[] tryingToConnect = {"Connecting","to AppWarp"};
    private final String[] waitForOtherUser = {"Waiting for","other user"};
    private final String[] errorInConnection = {"Error in","Connection", "Go Back"};

    private final String[] game_win = {"Congrats You Win!", "Enemy Defeated"};
    private final String[] game_loose = {"Oops You Loose!","Target Achieved","By Enemy"};
    private final String[] enemy_left = {"Congrats You Win!", "Enemy Left the Game"};

    private String[] msg = tryingToConnect;

    SpriteBatch batcher;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Texture waitingLabel, frame0, frame1,frame2, frame3, frame4,frame5,frame6,frame7,frame8,frame9,frame10,
            frame11,frame12,frame13,frame14,frame15,frame16,frame17,frame18,frame19;
    Texture frame;
    ArrayList<Texture> frames;
    Rectangle backBounds;
    public float timeSinceLastFrame = 0;




    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;


    public WaitingScreen(Game game) {
        this.game = game;
        setup();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        timeSinceLastFrame += delta;

        if (timeSinceLastFrame >= 0.03f){
            draw();
            timeSinceLastFrame = 0;
        }

    }
    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);
        batcher.disableBlending();
        batcher.begin();

        frame = frames.get(0);
        frames.remove(0);
        frames.add(frame);
        batcher.draw(waitingLabel, WORLD_WIDTH / 8, WORLD_HEIGHT * 0.7f, WORLD_WIDTH*0.8f, WORLD_HEIGHT / 16);
        batcher.draw(frame, WORLD_WIDTH / 4, WORLD_HEIGHT * 0.2f, WORLD_WIDTH/2f, WORLD_WIDTH/2f);

        batcher.end();


    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                WarpController.getInstance().handleLeave();
                return;
            }
        }
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
    public void setup(){
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();

        backBounds = new Rectangle(WORLD_WIDTH / 4, WORLD_HEIGHT * 0.2f, WORLD_WIDTH/2f, WORLD_WIDTH/2f);

        waitingLabel = new Texture("waitinglabel.png");
        frame0 = new Texture("gifs/frame_0_delay-0.04s.gif");
        frame1 = new Texture("gifs/frame_1_delay-0.04s.gif");
        frame2 = new Texture("gifs/frame_2_delay-0.04s.gif");
        frame3 = new Texture("gifs/frame_3_delay-0.04s.gif");
        frame4 = new Texture("gifs/frame_4_delay-0.04s.gif");
        frame5 = new Texture("gifs/frame_5_delay-0.04s.gif");
        frame6 = new Texture("gifs/frame_6_delay-0.04s.gif");
        frame7 = new Texture("gifs/frame_7_delay-0.04s.gif");
        frame8 = new Texture("gifs/frame_8_delay-0.04s.gif");
        frame9 = new Texture("gifs/frame_9_delay-0.04s.gif");
        frame10 = new Texture("gifs/frame_10_delay-0.04s.gif");
        frame11 = new Texture("gifs/frame_11_delay-0.04s.gif");
        frame12 = new Texture("gifs/frame_12_delay-0.04s.gif");
        frame13 = new Texture("gifs/frame_13_delay-0.04s.gif");
        frame14 = new Texture("gifs/frame_14_delay-0.04s.gif");
        frame15 = new Texture("gifs/frame_15_delay-0.04s.gif");
        frame16 = new Texture("gifs/frame_16_delay-0.04s.gif");
        frame17 = new Texture("gifs/frame_17_delay-0.04s.gif");
        frame18 = new Texture("gifs/frame_18_delay-0.04s.gif");
        frame19 = new Texture("gifs/frame_19_delay-0.04s.gif");

        frames = new ArrayList<Texture>();
        frames.add(frame0);
        frames.add(frame1);
        frames.add(frame2);
        frames.add(frame3);
        frames.add(frame4);
        frames.add(frame5);
        frames.add(frame6);
        frames.add(frame7);
        frames.add(frame8);
        frames.add(frame9);
        frames.add(frame10);
        frames.add(frame11);
        frames.add(frame12);
        frames.add(frame13);
        frames.add(frame14);
        frames.add(frame15);
        frames.add(frame16);
        frames.add(frame17);
        frames.add(frame18);
        frames.add(frame19);
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
                //game.setScreen(new MultiplayerGameScreen(game, WaitingScreen.this));
            }
        });

    }

    @Override
    public void onGameFinished (int code, boolean isRemote) {
        if(code== WarpController.GAME_WIN){
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

