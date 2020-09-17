package com.example.morphine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
Toolbar tb ;
TextView Dtitle;
RelativeLayout mainpart;
ImageView icog;
TextView wastetext;
Spinner spinnit;
Button next;
Webconnectivity wb = new Webconnectivity();
ArrayList<String> al=new ArrayList<String>() ;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        {
            tb = findViewById(R.id.toolbar);
            Dtitle = findViewById(R.id.Dtitle);
            mainpart = findViewById(R.id.mainpart);
            spinnit = findViewById(R.id.spinnit);
            icog = findViewById(R.id.icog);
            wastetext = findViewById(R.id.wastetext);
            next = findViewById(R.id.next);
        }

       user = MainActivity.incommingObject();
        try {
            al=(ArrayList<String>) wb.getDashboards();
            System.out.println(al);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnit.setAdapter(arrayAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
System.out.println("List of dashboards is "+al);
            }
        });
    }

}
