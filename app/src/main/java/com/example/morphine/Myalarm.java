package com.example.morphine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Myalarm extends BroadcastReceiver {
Webconnectivity wbs = new Webconnectivity();
    @Override
    public void onReceive(Context context, Intent intent) {
       Properties pro = new Properties();
        File file = new File(Environment.getExternalStorageDirectory(),"/morphinestuff/schedule.properties");

        FileInputStream fist = null;
        try {
            fist = new FileInputStream(file);
            pro.load(fist);
            String s = pro.getProperty("chosenone");
            String sta=pro.getProperty("state");
          Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
          String so = wbs.senddata(s,Boolean.parseBoolean(sta));
            Toast.makeText(context, sta, Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }



    }
}
