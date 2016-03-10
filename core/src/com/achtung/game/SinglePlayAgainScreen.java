package com.achtung.game;

/**
 * Created by mortenflood on 08.03.16.
 */
import com.achtung.game.multiplayer.StartMultiplayerScreen;
import com.achtung.game.multiplayer.WarpController;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Random;

import javafx.scene.Camera;
import javafx.scene.input.TouchPoint;

/**
 * Created by mortenflood on 08.03.16.
 */
public class SinglePlayAgainScreen implements Screen {

    Game game;
    SpriteBatch batcher;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Stage stage;
    Texture playAgainButton, mainMenuButton, player1WinsLabel, player2WinsLabel;
    Rectangle playagainBounds, mainMenuBounds;
    Player player;
    int winner;



    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;


    public SinglePlayAgainScreen(Game game, int winner) {
        this.game = game;
        this.winner = winner;
        //winner = player.getColor().equals(com.badlogic.gdx.graphics.Color.RED) ? 1 : 2;


        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();

        playAgainButton = new Texture("playagain.png");
        mainMenuButton = new Texture("mainmenu.png");
        player1WinsLabel = new Texture("player1wins.png");
        player2WinsLabel = new Texture("player2wins.png");
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
    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);


        batcher.disableBlending();
        batcher.begin();
        if (winner==1) {//CHANGE
            batcher.draw(player1WinsLabel, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.7f, WORLD_WIDTH * 0.7f, WORLD_HEIGHT / 12);
        }
        else {
            batcher.draw(player2WinsLabel, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.7f, WORLD_WIDTH * 0.7f, WORLD_HEIGHT / 12);
        }
        batcher.draw(playAgainButton, WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12 );
        batcher.draw(mainMenuButton, WORLD_WIDTH / 6, WORLD_HEIGHT * 0.25f, WORLD_WIDTH / 1.5f, WORLD_HEIGHT / 12);

        batcher.end();


    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playagainBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new GameScreen(game));
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

