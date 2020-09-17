package com.example.morphine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Home extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT =1000;
    private static User ush=MainActivity.incommingObject();
    String aiousername=ush.getAiousername();
    Intent idk;
    TextView tli;
    TextView tlc;
    TextView tlk;
    Switch swi;
    TextView tv;
    Button blk;
    Spinner spe;
   private static ArrayList<String> l;
    static BufferedReader reader;
    static String line;
    AlertDialog.Builder builder;
    Button buc;
    AlertDialog alert;
    private static HttpURLConnection connection;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        tli=findViewById(R.id.rout);
        tlc=findViewById(R.id.or);
        tlk=findViewById(R.id.choice);
        swi=findViewById(R.id.switc);
        blk=findViewById(R.id.applet);
        spe=findViewById(R.id.spinner);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            this.l=getFeeds();
        }

       System.out.println(l.toString());
       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,l);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spe.setAdapter(arrayAdapter);
        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            //Your code goes here
                            if (isChecked == true) {
                                s = spe.getSelectedItem().toString();
                                try {
                                    String sto = new Webconnectivity().senddata(s, true);
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                                builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Your Device is now Switched on!");
                                alert = builder.create();
                                alert.show();
                            } else {
                                s = spe.getSelectedItem().toString();
                                try {
                                    String sto = new Webconnectivity().senddata(s, false);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                builder = new AlertDialog.Builder(getApplicationContext());
                                builder.setMessage("Your Device is now Switched OFF");
                                alert = builder.create();
                                alert.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:{
                if(requestCode==RESULT_OK&&null!=data){
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(1).toString());
                    tv.setText(result.get(1).toString());
                }
                break;
            }
        }
    }

    public ArrayList<String> getFeeds()
    {
        StringBuffer response= new StringBuffer();
        String s1,s2 = null;
        ArrayList<String>  li = null;
        try {
            String head="https://io.adafruit.com/api/v2/"+aiousername+"/feeds/";

            System.out.println(head);
            URL url = new URL(head);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();
            int responsecode = connection.getResponseCode();
            if(responsecode>299){reader= new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line=reader.readLine())!=null){response.append(line);}
                reader.close();
            }else{reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=reader.readLine())!=null){response.append(line);}
                reader.close();
                System.out.println("else executed!");
                System.out.println(response.toString());
                li= extract(response.toString());

            }


        }catch(Exception e){e.printStackTrace();
        }
        finally{connection.disconnect();}


        return li;
    }
    public ArrayList<String> extract(String json) throws JSONException
    {//legendary code please dont loose it  you will never find it!
        JSONObject object = null;
        ArrayList<String> l=new ArrayList<String>();
        JSONArray array = new JSONArray(json);
        for(int i = 0;i<array.length();i++){
            object = array.getJSONObject(i);
            l.add(object.getString("key"));
            System.out.println("Feed NO"+"   "+i+"  "+"name of Feed"+"  "+object.getString("name")+"   "+"key of Feed"+"  "+object.getString("key"));
        }
        return l;
    }

    public void routinpage(View view) {
    startActivity(new Intent(this,Routinnepage.class));

    }
    public static ArrayList<String> getfeedlist()
    {return l;}
}
