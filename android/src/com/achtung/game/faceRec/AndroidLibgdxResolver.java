package com.achtung.game.faceRec;

import android.content.Context;
import android.content.Intent;

import com.achtung.game.AndroidLibgdxInterface;
import com.achtung.game.MainMenuScreenLauncher;


import java.util.HashMap;

/**
 * Created by Zlash on 06/03/16.
 */
public class AndroidLibgdxResolver implements AndroidLibgdxInterface {

    private Context context;
    private MainFaceRecActivity faceRecog;
    private HashMap<String, String> hash;
    private String userName;

    public AndroidLibgdxResolver(Context context, String userName) {
        this.context = context;
        this.faceRecog = new MainFaceRecActivity();
        this.hash = faceRecog.retrieveHash(context);
        this.userName = userName;
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(this.context, MainFaceRecActivity.class);
        context.startActivity(intent);
    }

    /*
    * Method to handle updating of an existing person from the recognition
    * album
    */

    @Override
    public void updateFaceRec() {
        Intent intent = new Intent(this.context, AddNewUser.class);
        intent.putExtra("Username", this.userName);
        intent.putExtra("PersonId", hash.get(this.userName));
        intent.putExtra("UpdatePerson", true);
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
}
