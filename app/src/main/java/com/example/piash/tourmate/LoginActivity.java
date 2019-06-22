package com.example.piash.tourmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piash.tourmate.Database.UserDataOperation;
import com.example.piash.tourmate.EventActivity.TravelEventsActivity;
import com.example.piash.tourmate.ModelClasses.User;

import java.util.ArrayList;

import static com.example.piash.tourmate.Constants.USER_EMAIL;
import static com.example.piash.tourmate.R.id.enterusername;


public class LoginActivity extends AppCompatActivity {

    private UserDataOperation userDataOperation;
    private EditText userName;
    private EditText password;
    private ArrayList<User> users;
    SharedPreferences sharedPreferences;
    private String UName;
    public static final String LOGINPREF = "Login";

    private ArrayList<String> emails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        userDataOperation = new UserDataOperation(this);
        userName = (EditText) findViewById(enterusername);
        password = (EditText) findViewById(R.id.enterpassword);

        sharedPreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);

    }

    public void signupp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        String emailText = sharedPreferences.getString(USER_EMAIL, "empty");
        if (!emailText.equals("empty")) {
            Intent intent = new Intent(this, TravelEventsActivity.class);
            intent.putExtra(Constants.LOGIN_SIGNUP_ADD_EVENT, "Login");
            intent.putExtra(Constants.USER_EMAIL, emailText);
            startActivity(intent);
            this.finish();
            Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show();
        }
        users = userDataOperation.getAllUsers();
        //Log.e("Login ", "onStart users.size() "+users.size());
        super.onStart();
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
    protected void onResume() {
        emails = new ArrayList<>();
        for (int i=0; i<users.size(); i++) {
            emails.add(users.get(i).getEmail());
        }
        super.onResume();
    }

    public void Login(View view) {
        String emailText = userName.getText().toString();
        String passwordText = password.getText().toString();

        if (!emailText.equals("") && !passwordText.equals("")) {
            if (emails.contains(emailText)) {
                String userPassword = userDataOperation.getUserPassword(emailText);
                if (passwordText.equals(userPassword)) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_EMAIL, emailText);
                    editor.commit();
                    Intent intent = new Intent(this, TravelEventsActivity.class);
                    intent.putExtra(Constants.LOGIN_SIGNUP_ADD_EVENT, "Login");
                    intent.putExtra(Constants.USER_EMAIL, emailText);
                    startActivity(intent);
                    userName.setText(null);
                    password.setText(null);
                } else {
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User not found "+users.size(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Missing Info", Toast.LENGTH_SHORT).show();
        }
    }
}
