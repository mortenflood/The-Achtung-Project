package com.achtung.game;

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

import javafx.scene.Camera;
import javafx.scene.input.TouchPoint;

/**
 * Created by mortenflood on 09.03.16.
 */
public class HighScoreScreen implements Screen {

    Game game;
    SpriteBatch batcher;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Stage stage;
    Texture motionONButton, motionOFFButton, mainMenuButton, highScoreLabel;
    Rectangle mainMenuBounds;



    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;


    public HighScoreScreen(Game game) {
        this.game = game;
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        touchPoint = new Vector3();
        batcher = new SpriteBatch();

        mainMenuButton = new Texture("mainmenu.png");
        highScoreLabel = new Texture("highscorelabel.png");
        mainMenuBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.1f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);


        //SKINS, STAGES AND FONTS
        /*
         skin = new Skin(Gdx.files.internal("data/uiskin.json"));
         stage = new Stage();
         Gdx.input.setInputProcessor(stage);
         usernameLabel = new Label(getUserName(), skin);
         stage.addActor(usernameLabel);
         usernameLabel.setPosition(0,0);
         usernameLabel.setFontScale(1);

         /*
         table = new Table();
         table.setFillParent(true);
         stage.addActor(table);

         label = new Label("UserName", skin);
         table.setPosition(WORLD_WIDTH/4,WORLD_HEIGHT*0.6f);
         label.setFontScale(2);
         table.add(label);*/

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
        batcher.draw(highScoreLabel, WORLD_WIDTH/4,WORLD_HEIGHT*0.8f, WORLD_WIDTH/2f, WORLD_HEIGHT/12 );
        batcher.draw(mainMenuButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.1f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.end();

    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

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
}

