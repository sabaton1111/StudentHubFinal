package com.mdsolutions.dimitar.studenthub;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements OnClickSignUpNextButtonListener
{
    String username;
    String password;
    String firstName;
    String middleName;
    String lastName;
    String email;
    String organization;
    Boolean isStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Fragment usernameFragment;
        usernameFragment = new UsernameFragment();
        ChangeCurrentFragment(usernameFragment);
    }

    public void registerUser()
    {
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(username);
        parseUser.setPassword(password);
        parseUser.setEmail(email);
        parseUser.put("firstName", firstName);
        parseUser.put("middleName", middleName);
        parseUser.put("lastName", lastName);
        parseUser.put("organization", organization);
        parseUser.put("isStudent", isStudent);
        parseUser.signUpInBackground(new SignUpCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    onRegisterSuccessful();
                }
                else
                {
                    onRegisterFailed(e);
                }
            }
        });
        /*Toast.makeText(getApplicationContext(), "Error:" + username + ", " + password + ", " + ", " +
                firstName + ", " + middleName + ", " + lastName + ", " + email + ", " + organization + ", " + isStudent, Toast.LENGTH_SHORT).show();*/
    }

    public void onRegisterSuccessful()
    {
        Intent homeActivity = new Intent(this, HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    public void onRegisterFailed(ParseException e)
    {
        Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void onPasswordsNotMatching()
    {
        Toast.makeText(getApplicationContext(), "Error: Passwords not matching", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickSignUpNextButton(int id)
    {
        switch (id)
        {
            case R.id.fragment_username:
            {
                this.username = ((EditText) findViewById(R.id.usernameSignUpField)).getText().toString();
                Fragment passwordFragment;
                passwordFragment = new PasswordFragment();
                ChangeCurrentFragment(passwordFragment);
                break;
            }
            case R.id.fragment_password:
            {
                String tPassword = ((EditText) findViewById(R.id.passwordSignUpField)).getText().toString();
                String tRepeatPassword = ((EditText) findViewById(R.id.repeatedPasswordSignUpField)).getText().toString();
                if(tPassword.equals(tRepeatPassword)) {
                    this.password = tPassword;
                    Fragment emailFragment;
                    emailFragment = new EmailFragment();
                    ChangeCurrentFragment(emailFragment);
                }
                else
                {
                    onPasswordsNotMatching();
                }
                break;
            }
            case R.id.fragment_email:
            {
                this.email = ((EditText) findViewById(R.id.emailSignUpField)).getText().toString();
                Fragment namesFragment;
                namesFragment = new NamesFragment();
                ChangeCurrentFragment(namesFragment);
                break;
            }
            case R.id.fragment_names:
            {
                this.firstName =   ((EditText) findViewById(R.id.firstNameSignUpField)).getText().toString();
                this.middleName =  ((EditText) findViewById(R.id.middleNameSignUpField)).getText().toString();
                this.lastName =    ((EditText) findViewById(R.id.lastNameSignUpField)).getText().toString();
                Fragment lastFragment;
                lastFragment = new LastFragment();
                ChangeCurrentFragment(lastFragment);
                break;
            }
            case R.id.fragment_last:
            {
                this.organization = ((EditText) findViewById(R.id.organizationSignUpField)).getText().toString();
                this.isStudent =    ((CheckBox) findViewById(R.id.isStudentSignUpCheckBox)).isChecked();
                registerUser();
            }
        }
    }

    public void ChangeCurrentFragment(Fragment newFragment)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.parent_fragment, newFragment);
        ft.commit();
    }
}
