package com.achtung.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Created by Malte on 03.03.2016.
 */
public class AchtungInputProcessor implements InputProcessor {

    private Player player;
    private Player player2;

    public AchtungInputProcessor(Player player) {
        this.player = player;
        this.player2 = null;
    }

    public AchtungInputProcessor(Player player, Player player2 ) {
        this.player = player;
        this.player2 = player2;
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

        if (player2 != null) {
            if (x > 0 && y > 0 && y <
                    9 * Gdx.graphics.getHeight() / 10 && x < 4 * Gdx.graphics.getWidth() / 10) {
                player2.setMoveRight(true);
            }
            else if(x > 6 * Gdx.graphics.getWidth() / 10 && y > 0
                    && y < 9 * Gdx.graphics.getHeight() / 10 && x < Gdx.graphics.getWidth()) {
                player2.setMoveLeft(true);
            }
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

        if (player2 != null) {
            if (x > 0 && y > 0 && y <
                    9 * Gdx.graphics.getHeight() / 10 && x < 4 * Gdx.graphics.getWidth() / 10) {
                player2.setMoveRight(false);
            }
            else if(x > 6 * Gdx.graphics.getWidth() / 10 && y > 0
                    && y < 9 * Gdx.graphics.getHeight() / 10 && x < Gdx.graphics.getWidth()) {
                player2.setMoveLeft(false);
            }
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
