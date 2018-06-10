/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.example.balajimt.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.balajimt.myapplication.Classifier.Recognition;

import java.util.List;
import java.util.Locale;

public class RecognitionScoreView extends View implements ResultsView,TextToSpeech.OnInitListener {
    private static final float TEXT_SIZE_DIP = 24;
    private List<Recognition> results;
    private final float textSizePx;
    private final Paint fgPaint;
    TextToSpeech tts;
    public RecognitionScoreView(final Context context, final AttributeSet set) {
        super(context, set);
        tts = new TextToSpeech(context, this);
        textSizePx =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
        fgPaint = new Paint();
        fgPaint.setTextSize(textSizePx);
    }

    @Override
    public void setResults(final List<Recognition> results) {
        this.results = results;
        postInvalidate();
    }

    private void speakOut(String s) {
        if(!tts.isSpeaking())
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                tts.stop();
//            }
//        }, 3000);
//        tts.stop();
    }
    @Override
    public void onDraw(final Canvas canvas) {

        fgPaint.setColor(Color.WHITE);

        if (results != null && results.size() > 0) {
            int y = (int) (fgPaint.getTextSize() * 1.4f);
            final Recognition recog = results.get(0);
            final int x = (int)(canvas.getWidth() - fgPaint.measureText(recog.getTitle())) / 2;
            String temp = recog.getTitle();
            canvas.drawText(temp, x, y, fgPaint);
            speakOut(temp);
        }
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
                //speakOut("Tap to say your commands");
                //return;
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
}
