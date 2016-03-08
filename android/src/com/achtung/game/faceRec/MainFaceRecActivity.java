package com.achtung.game.faceRec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.achtung.game.MainMenuScreenLauncher;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;
import com.achtung.game.R;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import android.support.v7.app.AppCompatActivity;

public class MainFaceRecActivity extends Activity {


//    private GridView gridView;
    public final String TAG = "com.mygdx.game.MainFaceRecActivity";
    public static FacialProcessing faceObj;
    public final int confidence_value = 58;
    public static boolean activityStartedOnce = false;
    public static final String ALBUM_NAME = "serialize_deserialize";
    public static final String HASH_NAME = "HashMap";
    HashMap<String, String> hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hash = retrieveHash(getApplicationContext());

        if (!activityStartedOnce) // Check to make sure FacialProcessing object
        // is not created multiple times.
        {
            activityStartedOnce = true;
            // Check if Facial Recognition feature is supported in the device
            boolean isSupported = FacialProcessing
                    .isFeatureSupported(FacialProcessing.FEATURE_LIST.FEATURE_FACIAL_RECOGNITION);
            if (isSupported) {
                Log.d(TAG, "Feature Facial Recognition is supported");
                faceObj = (FacialProcessing) FacialProcessing.getInstance();
                loadAlbum(); // De-serialize a previously stored album.
                if (faceObj != null) {
                    faceObj.setRecognitionConfidence(confidence_value);
                    faceObj.setProcessingMode(FacialProcessing.FP_MODES.FP_MODE_STILL);
                }
            } else // If Facial recognition feature is not supported then
            // display an alert box.
            {
                Log.e(TAG, "Feature Facial Recognition is NOT supported");
                new AlertDialog.Builder(this)
                        .setMessage(
                                "Your device does NOT support Qualcomm's Facial Recognition feature. ")
                        .setCancelable(false)
                        .setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        //MainFaceRecActivity.this.finish();
                                    }
                                }).show();
            }
        }

//        gridView = (GridView) findViewById(R.id.gridview);
//        gridView.setAdapter(new ImageAdapter(this));
//
////        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            public void onItemClick(AdapterView<?> parent, View v,
////                                    int position, long id) {
////                switch (position) {
////
////                    case 0: // Adding a person
////                        addNewPerson();
////                        break;
////
////                    case 1: // Updating an existing person
////                        updateExistingPerson();
////                        break;
////
////                    case 2: // Identifying a person.
////                        identifyPerson();
////                        break;
////
////
////                }
////            }
////        });

        //Buttons and behaviour
        final Button existingUserButton = (Button) this.findViewById(R.id.button_existing_user);
        final Button newUserButton = (Button) this.findViewById(R.id.button_new_user);
        final Button guestUserButton = (Button) this.findViewById(R.id.button_guest_user);
        final Button deleteButton = (Button) this.findViewById(R.id.button_delete);

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int numberOfButtons = 4;

        RelativeLayout.LayoutParams buttonParamsExistingUser = (RelativeLayout.LayoutParams) existingUserButton.getLayoutParams();
        buttonParamsExistingUser.width = size.x;
        buttonParamsExistingUser.height = size.y/numberOfButtons;

        RelativeLayout.LayoutParams buttonParamsNewUser = (RelativeLayout.LayoutParams) newUserButton.getLayoutParams();
        buttonParamsNewUser.width = size.x;
        buttonParamsNewUser.height = size.y/numberOfButtons;

        RelativeLayout.LayoutParams buttonParamsGuestUser = (RelativeLayout.LayoutParams) guestUserButton.getLayoutParams();
        buttonParamsGuestUser.width = size.x;
        buttonParamsGuestUser.height = size.y/numberOfButtons;

        RelativeLayout.LayoutParams buttonParamsDelete = (RelativeLayout.LayoutParams) deleteButton.getLayoutParams();
        buttonParamsDelete.width = size.x;
        buttonParamsDelete.height = size.y/numberOfButtons;

        existingUserButton.setLayoutParams(buttonParamsExistingUser);
        newUserButton.setLayoutParams(buttonParamsNewUser);
        guestUserButton.setLayoutParams(buttonParamsGuestUser);
        deleteButton.setLayoutParams(buttonParamsDelete);

        newUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addNewPerson();
            }
        });

        existingUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyPerson();
            }
        });

        guestUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                guestUser();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });
    }


    private void guestUser() {
        Intent intent = new Intent(MainFaceRecActivity.this,
                MainMenuScreenLauncher.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Username", "Guest");

        startActivity(intent);
    }

    /*
     * Method to handle adding a new person to the recognition album
     */
    private void addNewPerson() {
        Intent intent = new Intent(this, AddNewUser.class);
        intent.putExtra("Username", "null");
        intent.putExtra("PersonId", -1);
        intent.putExtra("UpdatePerson", false);
        intent.putExtra("IdentifyPerson", false);
        startActivity(intent);
    }

    /*
     * Method to handle updating of an existing person from the recognition
     * album
     */
    private void updateExistingPerson() {
        Intent intent = new Intent(this, ChooseUser.class);
        intent.putExtra("DeleteUser", false);
        intent.putExtra("UpdateUser", true);
        startActivity(intent);
    }

    /*
     * Method to handle identification of an existing person from the
     * recognition album
     */
    private void identifyPerson() {
        Intent intent = new Intent(this, AddNewUser.class);
        intent.putExtra("Username", "Not Identified");
        intent.putExtra("PersonId", -1);
        intent.putExtra("UpdatePerson", false);
        intent.putExtra("IdentifyPerson", true);
        startActivity(intent);
    }

    /*
     * Method to handle deletion of an existing person from the recognition
     * album
     */
    private void deletePerson() {
        Intent intent = new Intent(this, ChooseUser.class);
        intent.putExtra("DeleteUser", true);
        intent.putExtra("UpdateUser", false);
        startActivity(intent);
    }

    /*
     * Method to handle reset of the recognition album
     */
    private void resetAlbum() {
        // Alert box to confirm before reset the album
        new AlertDialog.Builder(this)
                .setMessage(
                        "Are you sure you want to RESET the album? All the photos saved will be LOST")
                .setCancelable(true)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                boolean result = faceObj.resetAlbum();
                                if (result) {
                                    HashMap<String, String> hashMap = retrieveHash(getApplicationContext());
                                    hashMap.clear();
                                    saveHash(hashMap, getApplicationContext());
                                    saveAlbum();
                                    Toast.makeText(getApplicationContext(),
                                            "Album Reset Successful.",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Internal Error. Reset album failed",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.facial_recognition, menu);
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
        if (faceObj != null) // If FacialProcessing object is not released, then
        // release it and set it to null
        {
            faceObj.release();
            faceObj = null;
            Log.d(TAG, "Face Recog Obj released");
        } else {
            Log.d(TAG, "In Destroy - Face Recog Obj = NULL");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() { // Destroy the activity to avoid stacking of
        // android activities
        super.onBackPressed();
        MainFaceRecActivity.this.finishAffinity();
        activityStartedOnce = false;
    }

    /*
     * Function to retrieve a HashMap from the Shared preferences.
     * @return
     */
    protected HashMap<String, String> retrieveHash(Context context) {
        SharedPreferences settings = context.getSharedPreferences(HASH_NAME, 0);
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.putAll((Map<? extends String, ? extends String>) settings.getAll());
        return hash;
    }

    /*
     * Function to store a HashMap to shared preferences.
     * @param hash
     */
    protected void saveHash(HashMap<String, String> hashMap, Context context) {
        SharedPreferences settings = context.getSharedPreferences(HASH_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        Log.e(TAG, "Hash Save Size = " + hashMap.size());
        for (String s : hashMap.keySet()) {
            editor.putString(s, hashMap.get(s));
        }
        editor.commit();
    }

    /*
     * Function to retrieve the byte array from the Shared Preferences.
     */
    public void loadAlbum() {
        SharedPreferences settings = getSharedPreferences(ALBUM_NAME, 0);
        String arrayOfString = settings.getString("albumArray", null);

        byte[] albumArray = null;
        if (arrayOfString != null) {
            String[] splitStringArray = arrayOfString.substring(1,
                    arrayOfString.length() - 1).split(", ");

            albumArray = new byte[splitStringArray.length];
            for (int i = 0; i < splitStringArray.length; i++) {
                albumArray[i] = Byte.parseByte(splitStringArray[i]);
            }
            faceObj.deserializeRecognitionAlbum(albumArray);
            Log.e("TAG", "De-Serialized my album");
        }
    }

    /*
     * Method to save the recognition album to a permanent device memory
     */
    public void saveAlbum() {
        byte[] albumBuffer = faceObj.serializeRecogntionAlbum();
        SharedPreferences settings = getSharedPreferences(ALBUM_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("albumArray", Arrays.toString(albumBuffer));
        editor.commit();
    }

}
