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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;


public class MainMenuScreen implements Screen{

    Game game;

    OrthographicCamera camera;
    SpriteBatch batcher;
    Rectangle testBounds;
    //Rectangle playBounds, highscoresBounds, helpBounds;
    Rectangle multiplayerBounds, singleplayerBounds, logoutBounds, optionsBound, addImagesBound;
    Vector3 touchPoint;
    Texture exit, singleplayerButton, multiplayerButton, logoutButton, logo, optionsButton, addImagesButton;
    Stage stage;
    Skin skin;
    Table table;
    Label label;



    private final int WORLD_WIDTH = 640;
    private final int WORLD_HEIGHT = 960;

    private int screenX = Gdx.graphics.getWidth();
    private int screenY = Gdx.graphics.getHeight();

     public MainMenuScreen(Game game){
         this.game = game;
         camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
         camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

         batcher = new SpriteBatch();

         //BOUNDS
         multiplayerBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
         singleplayerBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.5f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
         logoutBounds = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.1f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/10);
         optionsBound = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.3f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
         addImagesBound = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.2f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);

         //SKINS, STAGES AND FONTS
        /*
         skin = new Skin(Gdx.files.internal("data/uiskin.json"));
         stage = new Stage();
         Gdx.input.setInputProcessor(stage);
         table = new Table();
         table.setFillParent(true);
         stage.addActor(table);

         label = new Label("UserName", skin);
         table.setPosition(WORLD_WIDTH/4,WORLD_HEIGHT*0.6f);
         label.setFontScale(2);
         table.add(label);*/



         //TEXTURES
         touchPoint = new Vector3();
         singleplayerButton = new Texture("singleplayer.png");
         multiplayerButton = new Texture("multiplayer.png");
         logoutButton = new Texture("logout.png");
         logo = new Texture("achtung.jpg");
         optionsButton = new Texture("options.png");
         addImagesButton = new Texture("addimages.png");

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

            if (singleplayerBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (multiplayerBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                WarpController.getInstance().startApp(getRandomHexString(10));
                game.setScreen(new StartMultiplayerScreen(game));
                return;
            }
            if (logoutBounds.contains(touchPoint.x, touchPoint.y)) {
                //Gdx.app.exit();
                AchtungGame tempGame = (AchtungGame) this.game;
                tempGame.resolver.logOut();
//                tempGame.resolver.updateFaceRec();

            }
            if (optionsBound.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new OptionsScreen(game));

            }
            if (addImagesBound.contains(touchPoint.x, touchPoint.y)) {
                //AchtungGame tempGame = (AchtungGame) this.game;
                //tempGame.resolver.logOut();


            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);

        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();


        batcher.disableBlending();
        batcher.begin();

        batcher.draw(logo,WORLD_WIDTH/3,WORLD_HEIGHT*0.7f, WORLD_WIDTH/3, WORLD_WIDTH/3);
        batcher.draw(singleplayerButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.5f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(multiplayerButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(optionsButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.3f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(addImagesButton, WORLD_WIDTH/6,WORLD_HEIGHT*0.2f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(logoutButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.1f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
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
        /*batcher.dispose();
        exit.dispose();
        singleplayerButton.dispose();
        multiplayerButton.dispose();
        logoutButton.dispose();
        logo.dispose();
        addImagesButton.dispose();
        optionsButton.dispose();
        stage.dispose();*/


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
