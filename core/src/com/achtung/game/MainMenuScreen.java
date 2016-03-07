package com.achtung.game;

import com.achtung.game.multiplayer.WarpController;
import com.achtung.game.multiplayer.MultiplayerGameScreen;
import com.achtung.game.multiplayer.StartMultiplayerScreen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;


public class MainMenuScreen implements Screen {

    Game game;

    OrthographicCamera camera;
    SpriteBatch batcher;
    Rectangle testBounds;
    Rectangle playBounds;
    Rectangle highscoresBounds;
    Rectangle helpBounds;
    Rectangle multiplayerBounds;
    Vector3 touchPoint;
    Texture exit;

    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;

    private int screenX = Gdx.graphics.getWidth();
    private int screenY = Gdx.graphics.getHeight();

     public MainMenuScreen(Game game){
         this.game = game;
         camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
         camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

         batcher = new SpriteBatch();
         testBounds = new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT/2);
         /*playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
         highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
         helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);*/
         multiplayerBounds = new Rectangle(0, WORLD_HEIGHT/2, WORLD_WIDTH, WORLD_HEIGHT);
         touchPoint = new Vector3();
         exit = new Texture("exitfinal.png");
     }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        draw();

    }

    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (testBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (multiplayerBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                //WarpController.getInstance().startApp(getRandomHexString(10));
                game.setScreen(new StartMultiplayerScreen(game));
                return;
            }
            /*if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                Settings.soundEnabled = !Settings.soundEnabled;
                if (Settings.soundEnabled)
                    Assets.music.play();
                else
                    Assets.music.pause();
            }*/
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);


        batcher.disableBlending();
        batcher.begin();
        batcher.draw(exit, 0, 0, WORLD_WIDTH, WORLD_HEIGHT/2);
        batcher.end();

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


    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }
}
