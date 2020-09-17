package com.example.morphine;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.core.app.ActivityCompat;

import java.util.Locale;


public class Speech {
    private static final int REQUEST_CODE_SPEECH_INPUT =1000;
    public static Intent speak()
    {
       Intent itl = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
          itl.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
          itl.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
          itl.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say what u want to do");
          return itl;
    }

}
