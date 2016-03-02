package com.achtung.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by mortenflood on 01.03.16.
 */
public class MainMenuScreen implements Screen {

    final  MyGdxGame game;
    OrthographicCamera camera;
     public MainMenuScreen(final MyGdxGame game){
         this.game = game;
         camera = new OrthographicCamera();
         camera.setToOrtho(false, 800, 480);
     }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

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
