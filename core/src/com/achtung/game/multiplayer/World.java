package com.achtung.game.multiplayer;

/**
 * Created by Malte on 06.03.2016.
 */
public class World {

    public interface WorldListener {
        public void turn ();

        public void straightForward ();

        public void hit ();

    }

    public World (WorldListener listener) {

    }

}
