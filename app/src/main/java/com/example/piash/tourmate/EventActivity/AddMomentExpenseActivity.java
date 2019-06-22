package com.example.piash.tourmate.EventActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piash.tourmate.Constants;
import com.example.piash.tourmate.Database.ExpenseMomentOperation;
import com.example.piash.tourmate.ModelClasses.ExpenseMoment;
import com.example.piash.tourmate.R;

/**
 * Created by PIASH on 11-May-17.
 */

public class AddMomentExpenseActivity extends AppCompatActivity {

    private ExpenseMomentOperation expenseMomentOperation;
    private int eventId;
    private int userId;
    private EditText etCostTitle;
    private EditText etCostAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment_cost);

        expenseMomentOperation = new ExpenseMomentOperation(this);
        eventId = getIntent().getIntExtra(Constants.EVENT_ID, 0);
        userId = getIntent().getIntExtra(Constants.USER_ID,0);

        findView();
    }

    private void findView() {
        etCostTitle = (EditText) findViewById(R.id.etCostTitle);
        etCostAmount = (EditText) findViewById(R.id.etCostAmount);
    }

    public void save(View view) {
        String title = etCostTitle.getText().toString();
        String amount = etCostAmount.getText().toString();

        if (!title.equals("") && !amount.equals("")) {
            boolean inserted = expenseMomentOperation.addExpenseMoment(new ExpenseMoment(title,
                    Double.parseDouble(amount), eventId));
            if (inserted) {
                Toast.makeText(this, "Your moment cost inserted successfully", Toast.LENGTH_SHORT)
                        .show();
                this.finish();
            } else {
                Toast.makeText(this, "Failed to insert", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter values properly", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        this.finish();
    }
}
