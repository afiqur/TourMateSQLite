package com.example.piash.tourmate.EventActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.piash.tourmate.Adapter.ExpenseListAdapter;
import com.example.piash.tourmate.Constants;
import com.example.piash.tourmate.Database.EventDataOperation;
import com.example.piash.tourmate.Database.ExpenseMomentOperation;
import com.example.piash.tourmate.ModelClasses.Event;
import com.example.piash.tourmate.ModelClasses.ExpenseMoment;
import com.example.piash.tourmate.R;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    private EventDataOperation eventDataOperation;
    private ExpenseMomentOperation expenseMomentOperation;
    private int eventId;
    private String eventName;
    private String eventFrom;
    private String eventTo;
    private double eventBudget;
    private TextView tvEventName;
    private TextView tvEventFrom;
    private TextView tvEventTo;
    private TextView tvEventBudgetAmount;
    private ListView lvExpenseList;
    private ArrayList<ExpenseMoment> expenseMoments;
    private Event event;
    private ExpenseListAdapter expenselistadapter;
    private ArrayList<com.example.piash.tourmate.ModelClasses.Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        eventId = getIntent().getIntExtra(Constants.EVENT_ID, 0);
        eventDataOperation = new EventDataOperation(this);
        expenseMomentOperation = new ExpenseMomentOperation(this);
        expenseMoments = expenseMomentOperation.getAllExpenseMoments(eventId);


        tvEventName = (TextView) findViewById(R.id.tvExpenseEventName);
        tvEventFrom = (TextView) findViewById(R.id.tvExpenseEventFrom);
        tvEventTo = (TextView) findViewById(R.id.tvExpenseEventTo);
        tvEventBudgetAmount = (TextView) findViewById(R.id.tvExpenseEventBudgetAmount);
        lvExpenseList = (ListView) findViewById(R.id.lvExpenseList);


    }

    @Override
    protected void onStart() {
        Log.e("ExpenseActivity ", "onStart: ");
        getEvent();
        getExpenseMoments();
        populateExpenseMomentActivity();
        super.onStart();
    }

    private void populateExpenseMomentActivity() {
        tvEventName.setText(eventName);
        tvEventFrom.setText(eventFrom);
        tvEventTo.setText(eventTo);
        tvEventBudgetAmount.setText("৳"+eventBudget);
        Log.e("EXPENSE_ACTIVITY", "populateExpenseActivity" + expenseMoments.toString());
        expenselistadapter = new ExpenseListAdapter(this, expenseMoments);
        lvExpenseList.setAdapter(expenselistadapter);
    }

    private void getExpenseMoments() {
        expenseMoments = expenseMomentOperation.getAllExpenseMoments(eventId);
    }

    private void getEvent() {
        event = eventDataOperation.getEvent(eventId);
        eventName = event.getEventName();
        eventFrom = event.getFromDate();
        eventTo = event.getToDate();
        eventBudget = event.getBudget();
    }
}
