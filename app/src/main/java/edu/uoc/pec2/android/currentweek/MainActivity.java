package edu.uoc.pec2.android.currentweek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();

    private Button mButtonSend;
    private EditText mEditTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    /**
     * Method to init views and their listeners
     */
    private void setViews() {
        // Button
        mButtonSend = (Button) findViewById(R.id.buttonSendInformation);
        mButtonSend.setOnClickListener(this);
        // EditText
        mEditTextDate = (EditText) findViewById(R.id.editTextDate);
    }

    /**
     * Method to go to result activity to check if the date introduced is correct or not.
     */
    private void goToResultActivity() {
        try {
            int weekNumber = Integer.valueOf(mEditTextDate.getText().toString());
            startActivity(ResultActivity.goToResultActivity(this, weekNumber));
        } catch (NumberFormatException ex) {
            Log.e(TAG, ex.getMessage());
            mEditTextDate.setError(getResources().getString(R.string.error_number));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSend) {
            if (mEditTextDate.getText().toString().isEmpty()) {
                // error empty date
                mEditTextDate.setError(getResources().getString(R.string.error_empty_date));
            } else {
                goToResultActivity();
            }
        }
    }
}
