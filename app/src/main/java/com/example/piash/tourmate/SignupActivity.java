package com.example.piash.tourmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piash.tourmate.Database.UserDataOperation;
import com.example.piash.tourmate.ModelClasses.User;

import java.util.ArrayList;

/**
 * Created by PIASH on 07-Apr-17.
 */

public class SignupActivity extends AppCompatActivity {
    private EditText Name;
    private EditText userName;
    private EditText password;
    private EditText rePassword;
    private UserDataOperation userDataOperation;
    private ArrayList<User> users;
    private ArrayList<String> emails;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Name = (EditText) findViewById(R.id.name);
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.repassword);
        userDataOperation = new UserDataOperation(this);

        sharedPreferences = getSharedPreferences(LoginActivity.LOGINPREF, Context.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        users = userDataOperation.getAllUsers();
        Log.e("SignUp ", "onStart "+ users.size());
        super.onStart();
    }

    @Override
    protected void onResume() {
        emails = new ArrayList<>();
        for (int i=0; i<users.size(); i++) {
            emails.add(users.get(i).getEmail());
        }
        super.onResume();
    }

    public void signup(View view) {
        String userNameText = Name.getText().toString();
        String emailText = userName.getText().toString();
        String passwordText = password.getText().toString();
        String rePasswordText = rePassword.getText().toString();
        User user = null;

        if (!userNameText.equals("") && !emailText.equals("") && !passwordText.equals("") &&
                !rePasswordText.equals("")) {
            if (passwordText.length() > 3 && rePasswordText.length() > 3) {
                if (passwordText.equals(rePasswordText)) {
                    if (emails.contains(emailText)) {
                        Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        user = new User(userNameText, emailText, passwordText);
                        userDataOperation.addUser(user);
                        Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.USER_EMAIL, emailText);
                        editor.commit();
                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.putExtra(Constants.LOGIN_SIGNUP_ADD_EVENT, "SignUp");
                        intent.putExtra(Constants.USER_EMAIL, emailText);
                        startActivity(intent);
                        Name.setText(null);
                        userName.setText(null);
                        password.setText(null);
                        rePassword.setText(null);
                    }
                } else {
                    Toast.makeText(this, "Passwords didn't Match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter at least 4 characters as password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void backtologin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
