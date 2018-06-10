package com.example.balajimt.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {

//    Button btnCTime;
//    EditText txtCTime;
    TextView textLogo;
    TextView name;
    ConstraintLayout mainScr;
    TextToSpeech tts;
    AnimationDrawable rocketAnimation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Regular.ttf");
        tts = new TextToSpeech(this, this);
        setContentView(R.layout.activity_main);
        textLogo=(TextView) findViewById(R.id.logoText);
        textLogo.setTypeface(typeface);
        name = (TextView) findViewById(R.id.name);
        name.setTypeface(typeface);
        mainScr = findViewById(R.id.mainScreen);
        final Context context = this;
        mainScr.setOnClickListener(new OnClickListener() {
            public void onClick(View vw) {
//                txtCTime.setText(new Date().toString());

                Intent intent = new Intent(context, VoiceAssistant.class);
                startActivity(intent);

            }
        });

        ImageView rocketImage = (ImageView) findViewById(R.id.imageView2);
        rocketImage.setBackgroundResource(R.drawable.custom_progressbar);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
//        btnCTime=(Button)findViewById(R.id.buttonSpeak);
//        btnCTime.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View vw) {
//                speakOut("Welcome");
//            }
//        });
//        txtCTime=(EditText)findViewById(R.id.editText);
//        txtCTime.setTypeface(typeface);
//        txtCTime.setInputType(InputType.TYPE_NULL);
    }

    private void speakOut(String s) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.UK);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.setPitch(0.85f);
                tts.setSpeechRate(0.80f);
                speakOut("Welcome to eye opener      touch anywhere on the screen to talk");
                //return;
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
        rocketAnimation.start();

    }
}
