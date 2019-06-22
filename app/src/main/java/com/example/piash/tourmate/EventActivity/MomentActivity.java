package com.example.piash.tourmate.EventActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.piash.tourmate.Adapter.CustomListAdapterDialog;
import com.example.piash.tourmate.Adapter.PhotoMomentAdapter;
import com.example.piash.tourmate.Constants;
import com.example.piash.tourmate.Database.EventDataOperation;
import com.example.piash.tourmate.Database.PhotoMomentOperation;
import com.example.piash.tourmate.Database.UserDataOperation;
import com.example.piash.tourmate.LoginActivity;
import com.example.piash.tourmate.ModelClasses.Event;
import com.example.piash.tourmate.ModelClasses.PhotoMoment;
import com.example.piash.tourmate.ModelClasses.User;
import com.example.piash.tourmate.NearBy.MapsActivity;
import com.example.piash.tourmate.R;
import com.example.piash.tourmate.Weather.WeatherActivity;

import java.util.ArrayList;

public class MomentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventDataOperation eventDataOperation;
    private UserDataOperation userDataOperation;
    private PhotoMomentOperation photoMomentOperation;
    private Event event;
    private int eventId;
    private String eventName;
    private String eventFrom;
    private String eventTo;
    private double eventBudget;
    private TextView tvEventName;
    private TextView tvEventFrom;
    private TextView tvEventTo;
    private TextView tvEventBudgetAmount;
    private ListView lvMomentList;
    private PhotoMomentAdapter photoMomentAdapter;
    private ArrayList<PhotoMoment> photoMoments;

    private TextView tvUserName;
    private TextView tvUserEmail;
    private String email;
    private String loginOrSignUp;
    private User user;
    private int userId;
    private ArrayList<com.example.piash.tourmate.ModelClasses.Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventId = getIntent().getIntExtra(Constants.EVENT_ID, 0);
        userId = getIntent().getIntExtra(Constants.USER_ID, 0);
        eventDataOperation = new EventDataOperation(this);
        photoMomentOperation = new PhotoMomentOperation(this);
        userDataOperation = new UserDataOperation(this);

        tvEventName = (TextView) findViewById(R.id.tvExpenseEventName);
        tvEventFrom = (TextView) findViewById(R.id.tvExpenseEventFrom);
        tvEventTo = (TextView) findViewById(R.id.tvExpenseEventTo);
        tvEventBudgetAmount = (TextView) findViewById(R.id.tvExpenseEventBudgetAmount);
        lvMomentList = (ListView) findViewById(R.id.lvMomentList);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tvEvnetUserName);
        tvUserEmail = (TextView) header.findViewById(R.id.tvEventUserEmail);

        loginOrSignUp = getIntent().getStringExtra(Constants.LOGIN_SIGNUP_ADD_EVENT);
        email = getIntent().getStringExtra(Constants.USER_EMAIL);
        user = userDataOperation.getUser(email);
        userId = user.getUserId();
        events = eventDataOperation.getAllEvents(userId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> myListOfItems = new ArrayList<>();
                myListOfItems.add("Add Photo Moment");
                myListOfItems.add("Add Expense Moment");

                final Dialog dialog = new Dialog(MomentActivity.this);

                View view1 = getLayoutInflater().inflate(R.layout.dialog_main, null);

                ListView lv = (ListView) view1.findViewById(R.id.custom_list);

                // Change MyActivity.this and myListOfItems to your own values
                CustomListAdapterDialog clad = new CustomListAdapterDialog(MomentActivity.this,
                        myListOfItems);

                lv.setAdapter(clad);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Intent intent = new Intent(MomentActivity.this, AddMomentPhotoActivity.class);
                            intent.putExtra(Constants.EVENT_ID, eventId);
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            Intent intent = new Intent(MomentActivity.this, AddMomentExpenseActivity.class);
                            intent.putExtra(Constants.EVENT_ID, eventId);
                            intent.putExtra(Constants.USER_ID, userId);
                            startActivity(intent);
                            dialog.dismiss();
                        }
            }
        });

                dialog.setContentView(view1);
                dialog.setTitle("Select an option");

                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        Log.e("MomentsActivity ", "onStart: ");
        getEvent();
        getPhotoMoments();
        populateMomentActivity();
        super.onStart();

    }

    private void getPhotoMoments() {
        photoMoments = photoMomentOperation.getAllPhotoMoments(eventId);
    }

    private void getEvent() {
        event = eventDataOperation.getEvent(eventId);

        eventName = event.getEventName();
        eventFrom = event.getFromDate();
        eventTo = event.getToDate();
        eventBudget = event.getBudget();
    }

    private void populateMomentActivity() {
        tvEventName.setText(eventName);
        tvEventFrom.setText(eventFrom);
        tvEventTo.setText(eventTo);
        tvEventBudgetAmount.setText("৳" + eventBudget);
        Log.e("MOMENTS_ACTIVITY", "populateMomentActivity: " + photoMoments.toString());
        photoMomentAdapter = new PhotoMomentAdapter(this, photoMoments);
        lvMomentList.setAdapter(photoMomentAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_photo_moment) {
            Intent intent = new Intent(MomentActivity.this, AddMomentPhotoActivity.class);
            intent.putExtra(Constants.EVENT_ID, eventId);
            intent.putExtra(Constants.USER_ID, userId);
            startActivity(intent);
        } else if (id == R.id.nav_expense_moment) {
            Intent intent = new Intent(MomentActivity.this, AddMomentExpenseActivity.class);
            intent.putExtra(Constants.EVENT_ID, eventId);
            startActivity(intent);
        } else if (id == R.id.nav_weather_moment) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_nearby) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_all_expense) {
            Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout_moment) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.LOGINPREF,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();

        } else if (id == R.id.nav_about_moment) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callEmergency(View view) {
        String emergencyNumber = event.getEmergencyNumber();
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + emergencyNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }
}
