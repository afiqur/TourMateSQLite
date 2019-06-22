package com.example.piash.tourmate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.piash.tourmate.ModelClasses.Event;
import com.example.piash.tourmate.R;

import java.util.ArrayList;

/**
 * Created by PIASH on 11-May-17.
 */

public class EventListAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> events;
    Context context;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        super(context, R.layout.custom_event_row, events);
        this.context = context;
        this.events = events;
    }

    // view holder class
    private static class ViewHolder {
        TextView tvPlaceText;
        TextView tvFromDate;
        TextView tvToDate;
        TextView tvBudget;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            // inflate the custom row
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_event_row, null, true);

            // initialize the variables
            viewHolder.tvPlaceText = (TextView) convertView.findViewById(R.id.tvPlaceText);
            viewHolder.tvFromDate = (TextView) convertView.findViewById(R.id.tvFromDate);
            viewHolder.tvToDate = (TextView) convertView.findViewById(R.id.tvToDate);
            viewHolder.tvBudget = (TextView) convertView.findViewById(R.id.tvBudgetText);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set contents to the views
        viewHolder.tvPlaceText.setText(events.get(position).getEventName());
        viewHolder.tvFromDate.setText(events.get(position).getFromDate());
        viewHolder.tvToDate.setText(events.get(position).getToDate());
        viewHolder.tvBudget.setText("৳" + String.valueOf(events.get(position).getBudget()));

        return convertView;
    }

}
