package com.achtung.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by mortenflood on 04.03.16.
 */
public class Achtung extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        //this.setScreen(new SplashScreen(this));


    }
    public void render(){
        super.render();
    }
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
