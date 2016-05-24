/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uoc.pec2.android.currentweek;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.uoc.pec2.android.currentweek.utils.DateUtils;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_WEEK = "WEEK_NUMBER";

    private TextView mTextViewResult;
    private Button mButtonTryAgain;

    private MediaPlayer mediaPlayer;

    /**
     * Method to send data to ResultActivity throwing any view
     *
     * @param context: previous activity
     * @param monthDay: month day number
     * @return: intent to be used by startActivity
     */
    public static Intent goToResultActivity(Context context, int monthDay) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_WEEK, monthDay);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get the week number send by previous activity
        int weekNumber = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // default value set as 0
            weekNumber = extras.getInt(EXTRA_WEEK, 0);
        } else {
            // end activity if no data has been send.
            finish();
        }
        setViews();
        checkWeekNumber(weekNumber);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stopping the sound if user leaves the screen and it is playing
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.release();
        }
    }

    /**
     * Method to init views
     */
    private void setViews() {
        // TextView
        mTextViewResult = (TextView) findViewById(R.id.tvResult);
        // Button
        mButtonTryAgain = (Button) findViewById(R.id.buttonTryAgain);
        mButtonTryAgain.setOnClickListener(this);
    }

    /**
     * Method to check the current week number.
     *
     * @param weekNumber
     */
    private void checkWeekNumber(int weekNumber) {
        boolean isTheResultCorrect;
        if (weekNumber == DateUtils.getCurrentWeekNumber()) {
            isTheResultCorrect = true;
        } else {
            isTheResultCorrect = false;
        }
        setTextDependingTheResult(isTheResultCorrect);
        setButtonTextDependingTheResult(isTheResultCorrect);
        setAudioDependingTheResult(isTheResultCorrect);
    }

    /**
     * Method to set the text view depending the result
     *
     * @param isTheResultCorrect
     */
    private void setTextDependingTheResult(boolean isTheResultCorrect) {
        if (isTheResultCorrect) {
            mTextViewResult.setText(getResources().getString(R.string.right_result));
            mTextViewResult.setTextColor(Color.GREEN);
        } else {
            mTextViewResult.setText(getResources().getString(R.string.fail_result));
            mTextViewResult.setTextColor(Color.RED);
        }
    }

    /**
     * Method to set the text of the button depending the result
     *
     * @param isTheResultCorrect
     */
    private void setButtonTextDependingTheResult(boolean isTheResultCorrect) {
        if (isTheResultCorrect) {
            mButtonTryAgain.setText(getResources().getString(R.string.button_start_again));
        } else {
            mButtonTryAgain.setText(getResources().getString(R.string.button_try_again));
        }
    }

    /**
     * Method to play audios depending the result
     *
     * @param isTheResultCorrect
     */
    private void setAudioDependingTheResult(boolean isTheResultCorrect) {
        mediaPlayer = MediaPlayer.create(this, isTheResultCorrect ? R.raw.audio_yes_message : R.raw.audio_no_message);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonTryAgain) {
            // Finish this activity to start again the game.
            finish();
        }
    }
}
