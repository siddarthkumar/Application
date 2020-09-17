package com.example.morphine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
Button b1;
Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        b1= findViewById(R.id.mannual_controll);
        b2=findViewById(R.id.task_scheduler);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent euler = new Intent(getApplicationContext(),Button_act.class);
                startActivity(euler);
            }
        });

    b2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(getApplicationContext(),Routinnepage.class));
        }
    });
    }
}
