package com.example.morphine;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Recordinggut extends AppCompatActivity {
    MediaRecorder myAudioRecorder;
    Button rec;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordinggut);
        rec=findViewById(R.id.rec);
        text = findViewById(R.id.text);
        File rootPath = new File(Environment.getExternalStorageDirectory(),"/morphinestuff/Andro.webm");
     myAudioRecorder   = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.WEBM);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.WEBM);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            myAudioRecorder.setOutputFile(rootPath);
        }
        rec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    try {
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException ise) {
                        ise.printStackTrace();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    Toast.makeText(getApplicationContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }
}
