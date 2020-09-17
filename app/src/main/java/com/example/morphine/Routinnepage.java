package com.example.morphine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class Routinnepage extends AppCompatActivity {
    User uso=MainActivity.incommingObject();
    Webconnectivity wb = new Webconnectivity(uso);
    ArrayList<Feeds> al;
    Button btr;
    Spinner speed;
    TextView intro;
    Switch swit;
    String chosenfeed;
    boolean state;
    ArrayList<String>dataamt;
    TimePicker tm ;
   static  String feed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routinnepage);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        {
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here
                this.al = wb.getFeeds();
            }
        }

       tm=findViewById(R.id.timePicker);
           swit = findViewById(R.id.tog);
       intro=findViewById(R.id.onside);
          btr = findViewById(R.id.set_alarm);
          speed=findViewById(R.id.Device);

        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){state=true;}
                else{state=false;}

            }
        });
        String[] stringArray = new String[al.size()];
        for(int i=0;i<al.size();i++)
        {
            stringArray[i]=al.get(i).getFeed_Key();
        }

        ArrayAdapter<String>  arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringArray );

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speed.setAdapter(arrayAdapter);
    }

    public void popupandside(View view) throws IOException {

        File rootPath = new File(Environment.getExternalStorageDirectory(),"/morphinestuff/schedule.properties");
        chosenfeed=speed.getSelectedItem().toString();
        Toast.makeText(this, chosenfeed, Toast.LENGTH_SHORT).show();
        Properties prop = new Properties();
        FileOutputStream fos = new FileOutputStream(rootPath);
        prop.setProperty("chosenone",chosenfeed);
        prop.setProperty("state",state+"");
        for(int i =0;i<al.size();i++){
            prop.setProperty(al.get(i).getFeed_Key(),al.get(i).getFeed_value());
        }
        prop.store(fos,"done saving");
        Toast.makeText(this, "Done saving"+chosenfeed, Toast.LENGTH_SHORT).show();

        /*------------------*/
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cal.set(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                    tm.getCurrentHour(),
                    tm.getCurrentMinute(),
                    0
            );
        }
        setAlarm(cal.getTimeInMillis());
    }

    public static String getScheduledFeed()
    {
        return feed;
    }
    public void setAlarm(long time)
    {
        AlarmManager aws = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent inm = new Intent(this,Myalarm.class);
        PendingIntent pdf = PendingIntent.getBroadcast(this,0,inm,0);
        aws.setRepeating(AlarmManager.RTC_WAKEUP,time,AlarmManager.INTERVAL_DAY,pdf);
        Toast.makeText(this, "This Alarm is set", Toast.LENGTH_SHORT).show();

    }


}
