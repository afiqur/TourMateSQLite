package com.example.piash.tourmate.EventActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.piash.tourmate.R;


/**
 * Created by PIASH on 02-May-17.
 */

public class UpdateEventActivity extends AppCompatActivity {
    private EditText editTextFDate;
    private EditText editTextTDate;
    private EditText editTextNote;
    private EditText editTextDestination;
    private EditText editTextBudget;

    private Button buttonAdd;
    private Button buttonView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_update);



        //Initializing views

        editTextFDate = (EditText) findViewById(R.id.FDate);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextTDate = (EditText) findViewById(R.id.TDate);
        editTextDestination = (EditText) findViewById(R.id.editTextDestination);
        editTextBudget = (EditText) findViewById(R.id.editTextBudget);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);


    }

}
