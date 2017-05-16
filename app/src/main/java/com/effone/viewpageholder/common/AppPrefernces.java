package com.effone.viewpageholder.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sumanth.peddinti on 5/16/2017.
 */

public class AppPrefernces {


    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private String APP_SHARED_PREFS	= AppPrefernces.class.getSimpleName();




    private  String ORDER_ID="order_id";
    private String RESTAURANT_NAME="rest_name";

    public String getORDER_ID() {

        return sharedPrefs.getString(ORDER_ID, "");
    }

    public void setORDER_ID(String order_id) {
        prefsEditor.putString(ORDER_ID, order_id);
        prefsEditor.commit();
    }

    private  String TANLE_NAME="table_name";

    public AppPrefernces(Context context) {
        this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = sharedPrefs.edit();
    }

    public int getTABLE_NAME(){
        return  sharedPrefs.getInt(TANLE_NAME,0);
    }
    public void setTABLE_NAME(int table_name) {
        prefsEditor.putInt(TANLE_NAME, table_name);
        prefsEditor.commit();
    }

    public String getRESTAURANT_NAME() {
        return sharedPrefs.getString(RESTAURANT_NAME, "");
    }
    public void setRRESTAURANT_NAME(String rest_name) {
        prefsEditor.putString(RESTAURANT_NAME, rest_name);
        prefsEditor.commit();
    }




}
