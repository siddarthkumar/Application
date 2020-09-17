package com.example.morphine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signupact extends AppCompatActivity {
EditText username;
EditText password;
EditText aioKey;
EditText aiopass;
EditText phone;
Button submit;

    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupact);
        {
            submit = (Button) findViewById(R.id.sig);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);
            aioKey = (EditText) findViewById(R.id.aiokey);
            aiopass = (EditText) findViewById(R.id.aiopass);
            phone = findViewById(R.id.phonenumber);
        }
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 adduser();
             }
         });



    }
    public void adduser()
    {
        String usern=username.getText().toString();
        String pass=password.getText().toString();
        String aiokey=aioKey.getText().toString();
        String aiousername=aiopass.getText().toString();
        String ph = phone.getText().toString();
        //long phones =Long.parseLong(phone.getText().toString());
        try {
            if (TextUtils.isEmpty(usern) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(aiousername) || TextUtils.isEmpty(aiokey)) {
                Toast.makeText(getApplicationContext(), "One or More Fields is Empty! Please fill them up ", Toast.LENGTH_SHORT);
            } else {
                User user = new User(usern, pass, "", aiousername, aiokey,ph);
                //dbr.child(ph).child(usern).setValue(user);
               new Firebaseops().postNormdata(user);
                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                Intent intz = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intz);
            }
        }catch(Exception e){e.printStackTrace();}
    }
    {
        dbr= FirebaseDatabase.getInstance().getReference("Morphineuser");
    }


}
