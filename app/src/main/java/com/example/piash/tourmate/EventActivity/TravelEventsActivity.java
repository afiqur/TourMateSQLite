package com.example.piash.tourmate.EventActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.piash.tourmate.Adapter.EventListAdapter;
import com.example.piash.tourmate.Constants;
import com.example.piash.tourmate.Database.EventDataOperation;
import com.example.piash.tourmate.Database.UserDataOperation;
import com.example.piash.tourmate.LoginActivity;
import com.example.piash.tourmate.ModelClasses.User;
import com.example.piash.tourmate.NearBy.MapsActivity;
import com.example.piash.tourmate.R;
import com.example.piash.tourmate.Weather.WeatherActivity;

import java.util.ArrayList;

public class TravelEventsActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lvTravelEvents;
    private TextView tvUserName;
    private TextView tvUserEmail;
    private EventDataOperation eventDataOperation;
    private UserDataOperation userDataOperation;
    private String email;
    private User user;
    private int userId;
    private String loginOrSignUp;
    private ArrayList<com.example.piash.tourmate.ModelClasses.Event> events;
    private ArrayAdapter<com.example.piash.tourmate.ModelClasses.Event> eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvTravelEvents = (ListView) findViewById(R.id.lvEventList);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tvEvnetUserName);
        tvUserEmail = (TextView) header.findViewById(R.id.tvEventUserEmail);

        eventDataOperation = new EventDataOperation(this);
        userDataOperation = new UserDataOperation(this);

        // user info
        loginOrSignUp = getIntent().getStringExtra(Constants.LOGIN_SIGNUP_ADD_EVENT);
        email = getIntent().getStringExtra(Constants.USER_EMAIL);
        user = userDataOperation.getUser(email);
        userId = user.getUserId();
        events = eventDataOperation.getAllEvents(userId);

        /*// for location
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
        geocoder = new Geocoder(this, Locale.getDefault());*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelEventsActivity.this, AddEventActivity.class);
                intent.putExtra(Constants.USER_EMAIL, email);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }


    @Override
    protected void onStart() {
        //googleApiClient.connect();
        if (loginOrSignUp.equals("Login")) {
            if (events.size() == 0) {
                View parentLayout = findViewById(R.id.lvEventList);
                Snackbar.make(parentLayout, "No event added",
                        Snackbar.LENGTH_LONG).show();
            } else {
                // Populate events list view
                eventsAdapter = new EventListAdapter(this, events);
                lvTravelEvents.setAdapter(eventsAdapter);

            }
        } else if (loginOrSignUp.equals("SignUp")){
            /*email = getIntent().getStringExtra(Constants.USER_EMAIL);
            user = usersDataSource.getUser(email);
            userId = user.getUserId();
            events = eventsDataSource.getAllEvents(userId);*/

            // Populate event list view
            View parentLayout = findViewById(R.id.lvEventList);
            Snackbar.make(parentLayout, "No event added", Snackbar.LENGTH_LONG).show();
        } else if (loginOrSignUp.equals("AddEvent")) {
            if (events.size() == 0) {
                View parentLayout = findViewById(R.id.lvEventList);
                Snackbar.make(parentLayout, "No event added",
                        Snackbar.LENGTH_LONG).show();
            } else {
                // Populate events list view
                eventsAdapter = new EventListAdapter(this, events);
                lvTravelEvents.setAdapter(eventsAdapter);
            }
        }

        tvUserName.setText(user.getUserName());
        Log.e("TEA", "onStart: " + email);
        tvUserEmail.setText(email);

        lvTravelEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TravelEventsActivity.this, MomentActivity.class);
                intent.putExtra(Constants.USER_EMAIL, email);
                intent.putExtra(Constants.EVENT_ID, events.get(position).getEventId());
                startActivity(intent);
            }
        });

        super.onStart();
    }

    /*@Override
    protected void onPause() {
        googleApiClient.disconnect();
        super.onPause();
    }*/

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/




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

        if (id == R.id.nav_travel_event) {
            Intent intent = new Intent(TravelEventsActivity.this, AddEventActivity.class);
            intent.putExtra(Constants.USER_EMAIL, email);
            startActivity(intent);
        } else if (id == R.id.nav_weather) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        }   else if (id == R.id.nav_nearby) {
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.LOGINPREF,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onLocationChanged(Location location) {
        if (!isLatLonSet) {
            LATITUDE = (int) location.getLatitude();
            LONGITUDE = (int) location.getLongitude();
        }

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        CITY = addresses.get(0).getLocality();
    }*/
/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.ActivityCompat.requestPermissions(this,
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
}
