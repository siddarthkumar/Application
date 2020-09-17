package com.example.morphine;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class Firebaseops {
     DatabaseReference dbr;
     static User usd;

    public DatabaseReference init(String name)
    {
        dbr=FirebaseDatabase.getInstance().getReference(name);
        return dbr;
    }
    public void postNormdata(User uli)
    {try {
        dbr = FirebaseDatabase.getInstance().getReference("Morphineuser");
        dbr.child(uli.getPhonenumber()).setValue(uli);
    }catch(Exception e ){e.printStackTrace();}
    }
    public void getDataObj(final String phno, final String username, final Context context)
    {
        dbr=FirebaseDatabase.getInstance().getReference("Morphineuser").child(phno);
     //   ArrayList la = new ArrayList();
         final User ust = null;

        Toast.makeText(context, "Value is bieng fetched", Toast.LENGTH_SHORT).show();
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = null;
                String password = null;
                String phone=null;
                String email=null;
                String aiousername=null;
                String aioKey=null;
                FileOutputStream fs = null;
                try {
                     username = dataSnapshot.child("username").getValue().toString();
                     password = dataSnapshot.child("password").getValue().toString();
                     phone = dataSnapshot.child("phonenumber").getValue().toString();
                     email = dataSnapshot.child("email").getValue().toString();
                     aiousername = dataSnapshot.child("aiousername").getValue().toString();
                     aioKey = dataSnapshot.child("aiokey").getValue().toString();
                   // User ust = new User(username, password, email, aiousername, aioKey, phone);
                    System.out.println("value fetched!");
                    System.out.println(username);
                    System.out.println(password);
                    File out=new File(Environment.getExternalStorageDirectory(),"/morphinestuff/hellobatham.txt");
                    if(!out.exists()) {
                        FileOutputStream fost = new FileOutputStream(out);
                        try {
                            PrintStream outs = new PrintStream(fost);
                            System.setOut(outs);
                            System.out.println(username);
                            System.out.println(password);
                            System.out.println(phone);
                            System.out.println(email);
                            System.out.println(aiousername);
                            System.out.println(aioKey);
                        } catch (NullPointerException e) {
                            System.out.println("no value fetched");
                        }
                    }else{ try {
                        PrintStream outs = new PrintStream(out);
                        System.setOut(outs);
                        System.out.println(username);
                        System.out.println(password);
                        System.out.println(phone);
                        System.out.println(email);
                        System.out.println(aiousername);
                        System.out.println(aioKey);
                    } catch (NullPointerException e) {
                        System.out.println("no value fetched");
                    }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Properties prop = new Properties();
                File rootPath = new File(Environment.getExternalStorageDirectory(),"/morphinestuff/creden.properties");
                if(rootPath.exists()) {
                    System.out.println("properties file already exist ");
                    try {
                        fs = new  FileOutputStream(rootPath);
                        prop.setProperty("username",username);
                        prop.setProperty("password",password);
                        prop.setProperty("email",email);
                        prop.setProperty("phone",phone);
                        prop.setProperty("aiousername",aiousername);
                        prop.setProperty("aiokey",aioKey);
                        prop.store(fs,"generated!");
                        System.out.println("Properties File created!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{

                    try {
                        fs = new  FileOutputStream(rootPath);
                        System.out.println("properties file created");

                        prop.setProperty("username",username);
                        prop.setProperty("password",password);
                        prop.setProperty("email",email);
                        prop.setProperty("phone",phone);
                        prop.setProperty("aiousername",aiousername);
                        prop.setProperty("aiokey",aioKey);
                        prop.store(fs,"generated!");
                        System.out.println("Properties File created!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               System.out.println("value not fetched!");
            }
        });

    }
    public void setTime()
    {
        int a ;
    }
}
