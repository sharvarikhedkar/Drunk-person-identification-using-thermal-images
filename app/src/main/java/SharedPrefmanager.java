package com.techbuzz.katraj.drunkpersondetection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by admin on 11-Sep-17.
 */

public class SharedPrefmanager {

        //the constants
        private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
        private static final String KEY_FULLNAME = "keyfullname";
        private static final String KEY_EMAIL = "keyemail";
        private static final String KEY_PASSWORD = "keypassword";
        private static final String KEY_MOBILENO = "keymobileno";
        private static final String KEY_GENDER = "keygender";
        private static final String KEY_ID = "keyid";

    private static final String KEY_RECIPEEID="keyrecipeeid";
    private static final String KEY_RID="keyrid";
    private static final String KEY_REMARK="keyremark";
    private static final String KEY_DATE="keydate";
    private static final String KEY_USERID="keyuserid";

        private static SharedPrefmanager mInstance;
        private static Context mCtx;

    private SharedPrefmanager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefmanager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefmanager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, (user.getId()));
        editor.putString(KEY_FULLNAME, user.getFullname());
        editor.putString(KEY_EMAIL, user.getEmailid());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_MOBILENO, user.getMobileno());
        editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }

    //recippe information
    public void recipeeInfo(Person person) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RECIPEEID, (person.getRecipeeid()));
        editor.putInt(KEY_RID, Integer.parseInt(String.valueOf(person.getRid())));

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new User(
                sharedPreferences.getInt(KEY_ID,-1),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_MOBILENO, null),
                sharedPreferences.getString(KEY_GENDER, null));

    }

    //this method will give the ID OF A RECIPPE ADDED

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void PersonDetailsInfo(PersonInfo personInfo) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_RID, String.valueOf(personInfo.getRid()));
        editor.putString(KEY_RECIPEEID, String.valueOf(personInfo.getRecipeeId()));
        editor.putString(KEY_REMARK, personInfo.getRemark());
        editor.putString(KEY_DATE, personInfo.getDate());
        editor.putString(KEY_USERID, String.valueOf(personInfo.getUserid()));


        editor.apply();
    }



    public PersonInfo PersonDetailsInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new PersonInfo(
                sharedPreferences.getString(KEY_RID,null),
                sharedPreferences.getString(KEY_RECIPEEID,null),
                sharedPreferences.getString(KEY_REMARK,null),
                sharedPreferences.getString(KEY_DATE,null),
                sharedPreferences.getString(KEY_USERID,null)

        );
    }
}

