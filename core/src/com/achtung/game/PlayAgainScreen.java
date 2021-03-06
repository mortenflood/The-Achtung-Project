package com.achtung.game;

/**
 * Created by mortenflood on 08.03.16.
 */
import com.achtung.game.multiplayer.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

/**
 * Created by mortenflood on 08.03.16.
 */
public class PlayAgainScreen implements Screen {

    Game game;
    SpriteBatch batcher;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Stage stage;
    Texture playAgainButton, mainMenuButton, youWinLabel, youLoseLabel;
    Rectangle playagainBounds, mainMenuBounds;
    boolean playerWin;



    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;


    public PlayAgainScreen(Game game, boolean playerWin) {
        this.game = game;
        this.playerWin = playerWin;

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();

        playAgainButton = new Texture("playagain.png");
        mainMenuButton = new Texture("mainmenu.png");
        youWinLabel= new Texture("youwin.png");
        youLoseLabel= new Texture("youlose.png");
        playagainBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        mainMenuBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.25f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        draw();

    }
    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);


        batcher.disableBlending();
        batcher.begin();
        if (playerWin) {
            batcher.draw(youWinLabel, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.7f, WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 12);
        }
        else {
            batcher.draw(youLoseLabel, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.7f, WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 12);
        }
        batcher.draw(playAgainButton, WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12 );
        batcher.draw(mainMenuButton, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.25f, WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 12);

        batcher.end();


    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playagainBounds.contains(touchPoint.x, touchPoint.y)) {
                WarpController.getInstance().startApp(getRandomHexString(10));
                game.setScreen(new WaitingScreen(game));
                return;
            }
            if (mainMenuBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenuScreen(game));
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

    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }
}
