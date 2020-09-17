package com.example.morphine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Webconnectivity {
    static BufferedReader reader;
    static String line;
    private static User usl;
    private static String aiousername;
    String aiokey;
    private static HttpURLConnection connection;
public Webconnectivity(User usdo)
{
    usl=usdo;
    System.out.println(usdo);
    aiousername=usl.getAiousername();
    aiokey=usl.getAiokey();
}
public Webconnectivity()
{

}
    public  String getData(String s) {
        StringBuffer response = new StringBuffer();

        String s1, s2 = null;
        try {
            String head = "https://io.adafruit.com/api/v2/"+aiousername+"/feeds/";
            String foot = "/data?limit=1";
            //String device = JOptionPane.showInputDialog("Please Enter the device");
            String devurl = head.concat(s).concat(foot);
            System.out.println(devurl);
            URL url = new URL(devurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.setRequestMethod("GET");
            connection.connect();
            int responsecode = connection.getResponseCode();
            if (responsecode > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                s1 = removeCharAt(response.toString(), 0);
                s2 = removeCharAt(s1, s1.length() - 1);
                System.out.println(s2);
            }
            //System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return s2;
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }


    public String senddata(String s, Boolean bval) throws MalformedURLException, IOException, JSONException {
        String response = null;
        URL url = null;
        JSONObject jon = null;
        try {
            String head = "https://io.adafruit.com/api/v2/"+aiousername+"/feeds/";
            String foot = "/data";
            // String device = JOptionPane.showInputDialog("Please Enter the device");
            String devurl = head.concat(s).concat(foot);
            System.out.println(devurl);
            url = new URL(devurl);
            JSONObject jo = new JSONObject();
            // String value =JOptionPane.showInputDialog("Please Enter the value");
            jon = new JSONObject();
            if (bval == true) {
                jo.put("value", 1);
                jon.put("datum", jo);
            } else if (bval == false) {
                jo.put("value", 0);
                jon.put("datum", jo);
            } else {
                System.out.println("no value selected");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-AIO-Key", usl.getAiokey());
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.connect();
        System.out.println(jon.toString());
        OutputStream os = con.getOutputStream();

        byte[] b = jon.toString().getBytes("utf-8");
        os.write(b);
        System.out.println("successfully sent");


        response = con.getResponseMessage();
        System.out.println(response);
        //String resp = "ok";
        //  if(response.equalsIgnoreCase(resp)==true){voice.speak("sent successfully your fan is on");}
        //else{voice.speak("failed to send");}
        con.disconnect();
        System.out.println(jon.toString());
        return response;
    }

    public ArrayList<Feeds> getFeeds()
    {ArrayList<Feeds> l = null;
        StringBuffer response= new StringBuffer();
        String s1,s2 = null;
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
            Feeds fi = new Feeds();
            object = array.getJSONObject(i);
 //           l.add(object.getString("key"));
            System.out.println("Feed NO"+"   "+i+"  "+"name of Feed"+"  "+object.getString("name")+"   "+"key of Feed"+"  "+object.getString("key"));
            fi.setFeed_Key(object.getString("key"));
            fi.setFeed_name(object.getString("name"));
            fi.setFeed_value(object.getString("last_value"));
            l.add(fi);
        }
        return l;
    }
    public  List<String> getDashboards() throws MalformedURLException {
        ArrayList<String> l = null;
        StringBuffer response= new StringBuffer();
        String s1,s2 = null;
        URL  url = new URL("https://io.adafruit.com/api/v2/"+aiousername+"/dashboards");
        try {

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
                l=  (ArrayList<String>) new Webconnectivity().extractdashboards(response.toString());
            }


        }catch(Exception e){e.printStackTrace();
        }
        finally{connection.disconnect();}

        return l;
    }

    public List<String> extractdashboards(String json) throws JSONException
    {//legendary code please dont loose it  you will never find it!

        JSONObject object = null;
        ArrayList<String> l=new ArrayList<String>();
        JSONArray array = new JSONArray(json);
        for(int i = 0;i<array.length();i++){
            object = array.getJSONObject(i);
            l.add(object.getString("name"));
            System.out.println("Feed NO"+"   "+i+"  "+"name of Feed"+"  "+object.getString("name")+"   "+"key of Feed"+"  "+object.getString("key")+"  "+"value of feed  "+object.getString("value"));

        }
        return l;
    }

}


