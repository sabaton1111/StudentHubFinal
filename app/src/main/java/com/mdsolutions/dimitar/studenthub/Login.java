package com.mdsolutions.dimitar.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        onClickListener();
    }

    public void onClickListener() {
        Button buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent("com.mdsolutions.dimitar.studenthub.SignUp");
                startActivity(intentSignUp);
            }
        });
    }

    public void onClickLoginButton(View view) {
        Button loginButton = (Button) view;
        disableButton(loginButton);
        EditText usernameField = (EditText) findViewById(R.id.editText6);
        EditText passwordField = (EditText) findViewById(R.id.editText7);
        LoginUser(usernameField.getText().toString(), passwordField.getText().toString(), loginButton);
    }

    public void LoginUser(String username, String password, final Button loginButton) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    onLoginSuccessful();
                } else {
                    onLoginFailed(e);
                }
                enableButton(loginButton);
            }
        });
    }

    public void onLoginSuccessful() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed(ParseException e)
    {
        Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void disableButton(Button button)
    {
        button.setClickable(false);
    }

    public void enableButton(Button button)
    {
        button.setClickable(true);
    }
}
