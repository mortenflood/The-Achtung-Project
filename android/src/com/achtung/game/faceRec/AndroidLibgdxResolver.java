package com.achtung.game.faceRec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.achtung.game.AndroidLibgdxInterface;
import com.achtung.game.MainMenuScreenLauncher;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zlash on 06/03/16.
 */
public class AndroidLibgdxResolver implements AndroidLibgdxInterface {

    private Context context;
    private HashMap<String, String> hash;
    private String userName;

    private static final String HASH_WINS = "HashWins";
    private static final String HASH_LOSSES = "HashLosses";

    public AndroidLibgdxResolver(Context context, String userName) {
        this.context = context;
        this.userName = userName;
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(this.context, MainFaceRecActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        MainMenuScreenLauncher temp = (MainMenuScreenLauncher) this.context;
        temp.onBackPressed();
    }

    public String getUserName() {
        return userName;
    }

    /*
     *  Return HashMap with key = user, value = number of wins.
     */
    @Override
    public HashMap<String, Integer> retrieveHashWins() {
        SharedPreferences data = this.context.getSharedPreferences(HASH_WINS, 0);

        HashMap<String, Integer> hashWins = new HashMap<String, Integer>();
        hashWins.putAll((Map<? extends String, ? extends Integer>) data.getAll());
        return hashWins;
    }


    /*
     *  Used to save the HashMap to SharedPreferences in other methods. Should not have to use this method outside of the class.
     */
    private void saveHash(HashMap<String, Integer> hash) {
        SharedPreferences data = this.context.getSharedPreferences(HASH_WINS, 0);

        SharedPreferences.Editor editor = data.edit();
        editor.clear();

        for (String s : hash.keySet()) {
            editor.putInt(s, hash.get(s));
        }
        editor.commit();
    }

    /*
     *  Use to increment number of wins for a user by one
     */
    @Override
    public void incrementHashWin(String userName) {
        HashMap<String, Integer> hashWins = retrieveHashWins();

        int wins = hashWins.get(userName);
        wins += 1;
        hashWins.put(userName, wins);

        saveHash(hashWins);

    }


    /*
     *  Return HashMap with key = user, value = number of losses.
     */
    @Override
    public HashMap<String, Integer> retrieveHashLosses() {
        SharedPreferences data = this.context.getSharedPreferences(HASH_LOSSES, 0);

        HashMap<String, Integer> hashLosses = new HashMap<String, Integer>();
        hashLosses.putAll((Map<? extends String, ? extends Integer>) data.getAll());
        return hashLosses;
    }

    /*
     *  Use to increment number of losses for a user by one
     */
    @Override
    public void incrementHashLosses(String userName) {
        HashMap<String, Integer> hashLosses = retrieveHashLosses();

        int losses = hashLosses.get(userName);
        losses += 1;
        hashLosses.put(userName, losses);

        saveHash(hashLosses);

    }

}
