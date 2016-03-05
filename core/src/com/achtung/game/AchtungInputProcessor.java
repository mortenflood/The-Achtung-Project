package com.achtung.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Created by Malte on 03.03.2016.
 */
public class AchtungInputProcessor implements InputProcessor {


    private Player player;

    public AchtungInputProcessor(Player player) {
        this.player = player;
    }

    public boolean keyDown (int keycode) {
        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {

        // DO STUFF

        if (x > 0 && y > 9 * Gdx.graphics.getHeight() / 10 && y < Gdx.graphics.getHeight() && x < 4 * Gdx.graphics.getWidth() / 10) {
            player.setMoveLeft(true);
        }
        else if(x > 6 * Gdx.graphics.getWidth() / 10 && y > 9 * Gdx.graphics.getHeight() / 10
                && y < Gdx.graphics.getHeight() && x < Gdx.graphics.getWidth()) {
            player.setMoveRight(true);
        }
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {

        // DO STUFF
        if (x > 0 && y > 9 * Gdx.graphics.getHeight() / 10 && y < Gdx.graphics.getHeight() && x < 4 * Gdx.graphics.getWidth() / 10) {
            player.setMoveLeft(false);
        }
        else if(x > 6 * Gdx.graphics.getWidth() / 10 && y > 9 * Gdx.graphics.getHeight() / 10
                && y < Gdx.graphics.getHeight() && x < Gdx.graphics.getWidth()) {
            player.setMoveRight(false);
        }
        return true;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }

}
