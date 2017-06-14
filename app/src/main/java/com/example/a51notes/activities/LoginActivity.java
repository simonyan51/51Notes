package com.example.a51notes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a51notes.R;
import com.example.a51notes.pojos.User;
import com.example.a51notes.utils.PreferancesHelper;
import com.example.a51notes.utils.user.FileUserStorage;
import com.example.a51notes.utils.user.UserStorage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameEt;
    private EditText passwordEt;

    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEt = (EditText) findViewById(R.id.activity_login_username_et);
        passwordEt = (EditText) findViewById(R.id.activity_login_password_et);

        findViewById(R.id.activity_login_login_btn).setOnClickListener(this);
        findViewById(R.id.activity_login_signup_btn).setOnClickListener(this);

        userStorage = new FileUserStorage();
    }

    private void signUp(){
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    private void login(){
        if(!checkInputValid()){
            return;
        }

        userStorage.checkAndGetUser(usernameEt.getText().toString(), passwordEt.getText().toString(), new UserStorage.UserFoundListener() {
            @Override
            public void onUserFound(User user) {
                handelUserFound(user);
            }
        });
    }

    private void handelUserFound(User user){
        if(user == null){
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            return;
        }

        PreferancesHelper preferancesHelper = PreferancesHelper.getInstance(this);
        preferancesHelper.setLoggedIn(true);
        preferancesHelper.setUserId(user.getId());

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean checkInputValid(){
        boolean valid = true;

        if(usernameEt.getText().length() == 0 || passwordEt.getText().length() == 0){
            Toast.makeText(this, "Username and password fields are required", Toast.LENGTH_SHORT).show();
            valid = false;
        }


        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_login_login_btn:
                login();
                break;

            case R.id.activity_login_signup_btn:
                signUp();
                break;
        }
    }
}