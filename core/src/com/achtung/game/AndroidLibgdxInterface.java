package com.achtung.game;

import java.util.HashMap;

/**
 * Created by Zlash on 06/03/16.
 */
public interface AndroidLibgdxInterface {

    void logOut();
    void onBackPressed();
    String getUserName();
    HashMap<String, Integer> retrieveHashWins();
    void incrementHashWin(String userName);
    HashMap<String, Integer> retrieveHashLosses();
    void incrementHashLosses(String userName);

}
