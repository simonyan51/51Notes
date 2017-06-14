package com.example.a51notes.activities;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a51notes.R;
import com.example.a51notes.fragments.NotesListFragment;
import com.example.a51notes.pojos.Note;
import com.example.a51notes.utils.PreferancesHelper;
import com.example.a51notes.utils.note.FileNoteStorage;
import com.example.a51notes.utils.note.NoteStorage;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_main_logout).setOnClickListener(this);

        transaction = getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_container, new NotesListFragment(), NotesListFragment.TAG)
                .addToBackStack(null);

        transaction.commit();

    }


    private void logout(){
        PreferancesHelper.getInstance(this).resetAll();
        startActivity(new Intent(this, StartupActivity.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_main_logout:
                logout();
                break;
        }
    }
}