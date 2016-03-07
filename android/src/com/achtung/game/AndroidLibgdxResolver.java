package com.achtung.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.achtung.game.faceRec.MainFaceRecActivity;

/**
 * Created by Zlash on 06/03/16.
 */
public class AndroidLibgdxResolver implements AndroidLibgdxInterface{

    Context context;

    public AndroidLibgdxResolver(Context context) {
        this.context = context;
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(this.context, MainFaceRecActivity.class);
        context.startActivity(intent);
    }
}
