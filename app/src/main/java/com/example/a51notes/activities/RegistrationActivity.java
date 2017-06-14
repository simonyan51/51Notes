package com.example.a51notes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a51notes.R;
import com.example.a51notes.pojos.User;
import com.example.a51notes.utils.PreferancesHelper;
import com.example.a51notes.utils.user.FileUserStorage;
import com.example.a51notes.utils.user.UserStorage;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView fNameTv;
    private TextView lNameTv;
    private TextView emailTv;
    private TextView usernameTv;
    private TextView password1Tv;
    private TextView password2Tv;
    private RadioGroup genderRg;
    private TextView ageTv;

    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Registration");

        fNameTv = (TextView) findViewById(R.id.activity_reg_fname_et);
        lNameTv = (TextView) findViewById(R.id.activity_reg_lname_et);
        emailTv = (TextView) findViewById(R.id.activity_reg_email_et);
        usernameTv = (TextView) findViewById(R.id.activity_reg_username_et);
        password1Tv = (TextView) findViewById(R.id.activity_reg_password1_et);
        password2Tv = (TextView) findViewById(R.id.activity_reg_password2_et);
        genderRg = (RadioGroup) findViewById(R.id.activity_reg_gender_rg);
        ageTv = (TextView) findViewById(R.id.activity_reg_age_et);

        findViewById(R.id.activity_reg_btn).setOnClickListener(this);

        userStorage = new FileUserStorage();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_reg_btn:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        if(!checkAllFiledsValud()){
            return;
        }

        String fName = fNameTv.getText().toString();
        String lName = lNameTv.getText().toString();
        String email = emailTv.getText().toString();
        String username = usernameTv.getText().toString();
        String password = password1Tv.getText().toString();
        User.Gender gender = genderRg.getCheckedRadioButtonId() == R.id.activity_reg_gender_rb_male ? User.Gender.MALE : User.Gender.FEMALE;
        int age = Integer.valueOf(ageTv.getText().toString());

        User user = new User(username, email, password, fName, lName, gender, age);

        userStorage.registerUser(user, new UserStorage.UserFoundListener() {
            @Override
            public void onUserFound(User user) {
                handelUserFound(user);
            }
        });

    }

    private void handelUserFound(User user){
        if(user == null){
            // Very bad thing to hardcode strings in java file!!!!!!!
            Toast.makeText(this, "Something went wrong during registration", Toast.LENGTH_SHORT).show();
            return;
        }

        PreferancesHelper preferancesHelper = PreferancesHelper.getInstance(this);
        preferancesHelper.setLoggedIn(true);
        preferancesHelper.setUserId(user.getId());

        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    private boolean checkAllFiledsValud(){
        // TODO check all fields
        return true;
    }
}