package com.example.piash.tourmate.EventActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piash.tourmate.Constants;
import com.example.piash.tourmate.Database.EventDataOperation;
import com.example.piash.tourmate.Database.UserDataOperation;
import com.example.piash.tourmate.ModelClasses.Event;
import com.example.piash.tourmate.ModelClasses.User;
import com.example.piash.tourmate.R;

import java.util.Calendar;

/**
 * Created by PIASH on 02-May-17.
 */

public class AddEventActivity extends AppCompatActivity {
    private static TextView FromDate;
    private static TextView ToDate;
    private EditText Emergency;
    private EditText Destination;
    private EditText Budget;

    private EventDataOperation eventDataOperation;
    private UserDataOperation userDataOperation;
    private String userEmail;
    private User user;
    public static boolean date = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);


        FromDate = (TextView) findViewById(R.id.FDate);
        Emergency = (EditText) findViewById(R.id.editTextNote);
        ToDate = (TextView) findViewById(R.id.TDate);
        Destination = (EditText) findViewById(R.id.editTextDestination);
        Budget = (EditText) findViewById(R.id.editTextBudget);

        userDataOperation = new UserDataOperation(this);
        eventDataOperation = new EventDataOperation(this);
        userEmail = getIntent().getStringExtra(Constants.USER_EMAIL);
        user = userDataOperation.getUser(userEmail);


    }

    public void AddEvent(View view) {
        String eventName = Destination.getText().toString();
        String eventBudget = Budget.getText().toString();
        String emergency = Emergency.getText().toString();
        String fromDate = FromDate.getText().toString();
        String toDate = ToDate.getText().toString();
        if (!eventName.equals("Enter event") && !eventBudget.equals("Enter budget") && !emergency
                .equals("Enter emergency contact") && !fromDate.equals("Select date") && !toDate.equals("Select date")) {
            double b;
            try {
                b = Double.parseDouble(eventBudget);
            } catch (Exception e) {
                Toast.makeText(this, "Enter number value for budget", Toast.LENGTH_SHORT).show();
                Budget.setText("");
                return;
            }
            Event event = new Event(eventName, b, emergency, fromDate, toDate);
            eventDataOperation.addEvent(user.getUserId(), event);
            Toast.makeText(this, eventName+" added successfully", Toast.LENGTH_SHORT).show();

            // Go to events activity
            Intent intent = new Intent(this, TravelEventsActivity.class);
            intent.putExtra(Constants.LOGIN_SIGNUP_ADD_EVENT, "AddEvent");
            intent.putExtra(Constants.USER_EMAIL, userEmail);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Enter information properly", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        this.finish();
    }

    public void setDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if (!date) {
                FromDate.setText(day+"/"+month+"/"+year);
                date = !date;
            } else {
                ToDate.setText(day+"/"+month+"/"+year);
                date = !date;
            }
        }
    }

}
