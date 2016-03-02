package com.achtung.game.multiplayer;

/**
 * Created by mortenflood on 01.03.16.
 */
public interface WarpListener {

    public void onWaitingStarted(String message);

    public void onError(String message);

    public void onGameStarted(String message);

    public void onGameFinished(int code, boolean isRemote);

    public void onGameUpdateReceived(String message);
}

