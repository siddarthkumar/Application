package com.example.morphine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
    private EditText etc;
    private EditText etcs;
    private Button b;
    private TextView txti;
    private ImageView imgs;
     private static User usd;
    private TextView intro;
    FirebaseDatabase fdb;
    DatabaseReference dbr;
    static User user;
    public static String phone_number;
    final Usersetter usc = new Usersetter();
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fdb= FirebaseDatabase.getInstance();
        dbr= fdb.getReference("Morphineuser");
        Toast.makeText(getApplicationContext(),"Connection successfull!",Toast.LENGTH_SHORT).show();
        Thread tld = new Thread() {
            public void run() {
                try {
                    etc = (EditText) findViewById(R.id.et1);
                    etcs = (EditText) findViewById(R.id.etc);
                    imgs = (ImageView) findViewById(R.id.imgs);
                    intro = (TextView) findViewById(R.id.lbc);
                    b = (Button) findViewById(R.id.bt1);
                    txti = findViewById(R.id.signin);
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        };
        tld.start();
        checkAndCreate();

    }

    public void checkAndCreate()
    {
        File rootPath = new File(Environment.getExternalStorageDirectory(),"morphinestuff");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
            Toast.makeText(this, "Directory is created successfully!.", Toast.LENGTH_SHORT).show();
        }
        else{Toast.makeText(this, "Directory already exists", Toast.LENGTH_SHORT).show();}
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Cannot use storage.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void switchit(View view) {
        Intent intj = new Intent(MainActivity.this,Signupact.class);
        startActivity(intj);
    }
    public void dotask(String message)
    {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

    }

    public void login(View view) throws IOException {
        final String textuser= etc.getText().toString();
        phone_number=textuser;
        final String passwords=etcs.getText().toString();

            /*-----------------------------------------------------------------------------*/



        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //line below prints data of given uid
               user= (User) dataSnapshot.child(textuser).getValue(User.class);
                System.out.println("Hello everyone User for iot logging is "+user);
                usc.setUser(user);
                if (user.getPassword().equalsIgnoreCase(passwords)){
                    checked=true;
                    Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(getApplicationContext(), "wrong phone number or password!", Toast.LENGTH_SHORT).show();
                    etc.setText(null);
                    etcs.setText(null);
                    checked=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });

System.out.println("Outside method user object is "+user);
        if(checked==true){Intent euler = new Intent(this,Menu.class);
            startActivity(euler);}
        else{
            Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_LONG);
        }

            //--------------------------------------------------------------------------------------------

        /*   if (user.getPassword().equalsIgnoreCase(passwords)){
                Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG);
                Intent euler = new Intent(this, Home.class);
                startActivity(euler);
            }
      else{
                Toast.makeText(this, "wrong phone number or password!", Toast.LENGTH_SHORT).show();
                etc.setText(null);
                etcs.setText(null);
            }*/

  }

public static User incommingObject()
{
    return user;
}
}
