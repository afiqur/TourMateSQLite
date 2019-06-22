package com.example.piash.tourmate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.piash.tourmate.ModelClasses.ExpenseMoment;
import com.example.piash.tourmate.R;

import java.util.ArrayList;

/**
 * Created by PIASH on 11-May-17.
 */

public class ExpenseListAdapter extends ArrayAdapter<ExpenseMoment> {

    Context context;
    ArrayList<ExpenseMoment> expenseMoments;

    public ExpenseListAdapter(Context context, ArrayList<ExpenseMoment> expenseMoments) {
        super(context, R.layout.custom_expense_row, expenseMoments);
        this.context = context;
        this.expenseMoments = expenseMoments;
    }

    private static class ViewHolder {
        TextView tvExpenseTitle;
        TextView tvExpenseAmount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_expense_row, null, true);

            // Initialize variables
            viewHolder.tvExpenseTitle = (TextView) convertView.findViewById(R.id.tvExpenseTitle);
            viewHolder.tvExpenseAmount = (TextView) convertView.findViewById(R.id.tvExpenseAmount);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the views
        viewHolder.tvExpenseTitle.setText(expenseMoments.get(position).getName());
        viewHolder.tvExpenseAmount.setText("৳" + String.valueOf(expenseMoments.get(position).getAmount()));

        return convertView;
    }
}
