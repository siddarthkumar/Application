package com.example.morphine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Button_act extends AppCompatActivity {
Toolbar tbs;
TextView controls;
RelativeLayout relative;
TextView masterlabel;
Button master_button;
    static String line;
    static BufferedReader reader;
    private static HttpURLConnection connection;
RecyclerView recd;
    User uso=MainActivity.incommingObject();
    Webconnectivity wb = new Webconnectivity(uso);
ArrayList<Feeds> al ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_act);

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
tbs=findViewById(R.id.btntool);
controls=findViewById(R.id.controls);
relative=findViewById(R.id.lower);
 masterlabel=findViewById(R.id.masterlabel);
 master_button=findViewById(R.id.master);
 recd=findViewById(R.id.listfeeds);
        final  Feedlistadapter ad= new Feedlistadapter(al,wb);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recd.setLayoutManager(llm);
      //Arrays.asList(new Job("Description \n This is my first job amd it is all about constructions and working whatever you need just call",  Arrays.asList("compromise \n","Construction","road","bridge","gathering")),new Job("This is my first job amd it is all about constructions and working whatever you need just call", Arrays.asList("compromise","Construction","road","bridge","gathering")),new Job("This is my first job amd it is all about constructions and working whatever you need just call", Arrays.asList("compromise","Construction","road","bridge","gathering")),new Job("This is my first job amd it is all about constructions and working whatever you need just call", Arrays.asList("compromise","Construction","road","bridge","gathering")),new Job("This is my first job amd it is all about constructions and working whatever you need just call", Arrays.asList("compromise","Construction","road","bridge","gathering")));
        //this line adds jobs to the recycler view
       recd.setAdapter(ad);

       master_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if(master_button.getText().toString().equalsIgnoreCase("ON")){
               master_button.setText("OFF");
               master_button.setBackgroundColor(Color.parseColor("#ffff33"));

               for(int i = 0; i<al.size();i++)
               {
                   try {
                       if(Integer.parseInt(al.get(i).getFeed_value())==1)
                       {wb.senddata(al.get(i).getFeed_Key(),false);}
                       else{wb.senddata(al.get(i).getFeed_Key(),false);}
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
               }else{
                   master_button.setText("ON");
                   master_button.setBackgroundColor(Color.parseColor("#00FF80"));
                   for(int i = 0; i<al.size();i++)
                   {
                       try {
                           if(Integer.parseInt(al.get(i).getFeed_value())==1)
                           {wb.senddata(al.get(i).getFeed_Key(),true);}
                           else{wb.senddata(al.get(i).getFeed_Key(),true);}
                       } catch (IOException e) {
                           e.printStackTrace();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
               }

           }

           }
       });

    }



    public ArrayList<Feeds> getFeeds()
    {ArrayList<Feeds> l = null;
        StringBuffer response= new StringBuffer();
        String s1,s2 = null;
        try {
            String head="https://io.adafruit.com/api/v2/"+uso.getAiousername()+"/feeds/";

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
                System.out.println(response.toString());
                l=  (ArrayList<Feeds>) this.extract(response.toString());
            }


        }catch(Exception e){e.printStackTrace();
        }
        finally{connection.disconnect();}


        return l;
    }
    public List<Feeds> extract(String json) throws JSONException
    {//legendary code please dont loose it  you will never find it!
        JSONObject object = null;
        ArrayList<Feeds> l=new ArrayList<Feeds>();

        JSONArray array = new JSONArray(json);
        for(int i = 0;i<array.length();i++){
            object = array.getJSONObject(i);
            //           l.add(object.getString("key"));
           // System.out.println("Feed NO"+"   "+i+"  "+"name of Feed"+"  "+object.getString("name")+"   "+"key of Feed"+"  "+object.getString("key"));
            Feeds fi = new Feeds();
            fi.setFeed_Key(object.getString("key"));
            fi.setFeed_name(object.getString("name"));
            fi.setFeed_value(object.getString("last_value"));
            l.add(fi);
        }
        return l;
    }
}
