package com.example.balajimt.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Balaji MT on 16-02-2018.
 */

public class VoiceAssistant extends AppCompatActivity implements RecognitionListener,TextToSpeech.OnInitListener {

    private TextView returnedText;
    ImageButton recordbtn;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    static final int REQUEST_PERMISSION_KEY = 1;
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Regular.ttf");
        setContentView(R.layout.voice_assistant);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        recordbtn = (ImageButton) findViewById(R.id.voiceButton);
        returnedText.setTypeface(typeface);
        tts = new TextToSpeech(this, this);

        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
        if (!Function.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }


        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        /*
        Minimum time to listen in millis. Here 5 seconds
         */
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 6000);
        recognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);




        recordbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                progressBar.setVisibility(View.VISIBLE);
                speech.startListening(recognizerIntent);
                recordbtn.setEnabled(false);

                /*To stop listening
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    recordbtn.setEnabled(true);
                 */
            }


        });



    }

    private void speakOut(String s) {
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.d("Log", "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("Log", "onBeginningOfSpeech");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Log", "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Log", "onEndOfSpeech");
        progressBar.setVisibility(View.INVISIBLE);
        String temp = (String) returnedText.getText();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        //QR
        //currency,cash,rupee
        //bill receipt
        temp = temp.toLowerCase();
        if(temp.contains("qr")){
            speakOut("QR Module is now active move left or right to detect a QR");
            Toast toast=Toast.makeText(context, "QR Module", duration);
            toast.show();
            Intent intent = new Intent(context, ClassifierActivity.class);
            String getrec="qr";
            Bundle bundle = new Bundle();
            bundle.putString("flag", getrec);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }else if(temp.contains("bill")||(temp.contains("receipt"))||(temp.contains("reader"))){
            Toast toast=Toast.makeText(context, "Bill Module", duration);
            toast.show();
            speakOut("Bill reader module is now active touch to scan");
            Intent intent = new Intent(context, BillReader.class);
            String getrec="bill";
            Bundle bundle = new Bundle();
            bundle.putString("flag", getrec);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }else if(temp.contains("currency")||temp.contains("money")||temp.contains("rupee")||temp.contains("note")) {
            speakOut("Currency module is now active");
            Toast toast=Toast.makeText(context, "Currency Module", duration);
            toast.show();
            Intent intent = new Intent(context, ClassifierActivity.class);
            String getrec="money";
            Bundle bundle = new Bundle();
            bundle.putString("flag", getrec);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }else if(temp.contains("coin")||temp.contains("change")||temp.contains("paisa")) {
            speakOut("Coin module is now active");
            Toast toast=Toast.makeText(context, "Currency Module", duration);
            toast.show();
            Intent intent = new Intent(context, ClassifierActivity.class);
            String getrec="coin";
            Bundle bundle = new Bundle();
            bundle.putString("flag", getrec);
            intent.putExtras(bundle);
            startActivity(intent);
            this.finish();
        }else if(temp.contains("back")||temp.contains("home")){
            speakOut("Back to home");
            Toast toast=Toast.makeText(context, "Home", duration);
            toast.show();
            this.finish();
        }else if(temp.contains("help")){
            speakOut("The commands available are as follows,, 1 Bill reader 2 Currency identifier 3 Coin Identifier 4 QR identifier");
            Toast toast=Toast.makeText(context, "Say something like read my bill", duration);
        }else if(temp.contains("god")||temp.contains("created")||temp.contains("creator")){
            speakOut("Built by Balaji jaga and varsha");
            //Toast toast=Toast.makeText(context, "Say something like read my bill", duration);
        }else{
            speakOut("I did not understand the command, please try again");
        }
        recordbtn.setEnabled(true);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d("Log", "FAILED " + errorMessage);
        progressBar.setVisibility(View.INVISIBLE);
        returnedText.setText(errorMessage);
        recordbtn.setEnabled(true);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.d("Log", "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.d("Log", "onPartialResults");

        ArrayList<String> matches = arg0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        /* To get all close matchs
        for (String result : matches)
        {
            text += result + "\n";
        }
        */
        text = matches.get(0); //  Remove this line while uncommenting above codes
        returnedText.setText(text);
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.d("Log", "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("Log", "onResults");

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Log", "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
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
                speakOut("Tap to say your commands");
                //return;
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

}
