package com.achtung.game.faceRec;

/**
 * Created by Malte on 04.03.2016.
 */
import com.achtung.game.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

public class AchtungGame extends Game {

    boolean firstTimeCreate = true;
    FPSLogger fps;

    @Override
    public void create () {
        //Settings.load();
        //Assets.load();
        setScreen(new MainMenuScreen(this));
        fps = new FPSLogger();
    }

    @Override
    public void render() {
        super.render();
		//fps.log();
    }

    /** {@link Game#dispose()} only calls {@link Screen#hide()} so you need to override {@link Game#dispose()} in order to call
     * {@link Screen#dispose()} on each of your screens which still need to dispose of their resources. SuperJumper doesn't
     * actually have such resources so this is only to complete the example. */


    @Override
    public void dispose () {
        super.dispose();
        getScreen().dispose();
    }

}