package com.achtung.game;

import com.achtung.game.multiplayer.*;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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


public class MainMenuScreen implements Screen{

    Game game;

    OrthographicCamera camera;
    SpriteBatch batcher;
    Rectangle testBounds;
    Rectangle multiplayerBounds, singleplayerBounds, logoutBounds, optionsBound, highScoreBound;
    Vector3 touchPoint;
    Texture exit, singleplayerButton, multiplayerButton, logoutButton, logo, optionsButton, highScoreButton;
    Music music;

    Label usernameLabel;



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
         highScoreBound = new Rectangle(WORLD_WIDTH/6,WORLD_HEIGHT*0.2f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);

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



         music = Gdx.audio.newMusic(Gdx.files.internal("music/circus.mp3"));

         if(AchtungGame.soundEnabled) {
             music.play();
         }
         music.setVolume(0.5f);



         //TEXTURES
         touchPoint = new Vector3();
         singleplayerButton = new Texture("twoplayer.png");
         multiplayerButton = new Texture("multiplayer.png");
         logoutButton = new Texture("logout.png");
         logo = new Texture("achtung.jpg");
         optionsButton = new Texture("options.png");
         highScoreButton = new Texture("highscore.png");

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
                music.stop();
                game.setScreen(new GameScreen(game));
                return;
            }
            if (multiplayerBounds.contains(touchPoint.x, touchPoint.y)) {
                //Assets.playSound(Assets.clickSound);
                WarpController.getInstance().startApp(getRandomHexString(10));
                music.stop();
                game.setScreen(new StartMultiplayerScreen(game));
                return;
            }
            if (logoutBounds.contains(touchPoint.x, touchPoint.y)) {
                //Gdx.app.exit();
                AchtungGame tempGame = (AchtungGame) this.game;
                tempGame.getResolver().logOut();

            }

            if (optionsBound.contains(touchPoint.x, touchPoint.y)) {
                music.stop();
                game.setScreen(new OptionsScreen(game));

            }
            if (highScoreBound.contains(touchPoint.x, touchPoint.y)) {
                //TODO: Removed adding multiple faceRec images. Change to highscores?
                music.stop();
                game.setScreen(new HighScoreScreen(game));

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

        batcher.draw(logo, WORLD_WIDTH /3,WORLD_HEIGHT*0.7f, WORLD_WIDTH / 3, WORLD_WIDTH / 3);
        batcher.draw(singleplayerButton, WORLD_WIDTH / 6, WORLD_HEIGHT*0.5f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(multiplayerButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.4f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(optionsButton,WORLD_WIDTH/6,WORLD_HEIGHT*0.3f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
        batcher.draw(highScoreButton, WORLD_WIDTH/6,WORLD_HEIGHT*0.2f, WORLD_WIDTH/1.5f, WORLD_HEIGHT/12);
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
        music.dispose();


    }


    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }

    private String getUserName() {
        AchtungGame tempGame = (AchtungGame) this.game;
        return tempGame.getResolver().getUserName();
    }
}
