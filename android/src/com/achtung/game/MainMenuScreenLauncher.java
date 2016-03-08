package com.achtung.game;

import android.content.Intent;
import android.os.Bundle;

import com.achtung.game.faceRec.AndroidLibgdxResolver;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainMenuScreenLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = true;


		Bundle extras = getIntent().getExtras();
		String userName = extras.getString("Username");

		AndroidLibgdxInterface resolver = new AndroidLibgdxResolver(this, userName);
		initialize(new AchtungGame(resolver), config);
	}


	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
