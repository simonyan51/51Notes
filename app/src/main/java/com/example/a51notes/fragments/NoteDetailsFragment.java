package com.example.a51notes.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a51notes.R;
import com.example.a51notes.pojos.Note;

import org.parceler.Parcels;


public class NoteDetailsFragment extends Fragment {

    public static final String TAG = "a51notes.fragments.NoteDetailsFragment";

    private Note currentNote;

    private TextView title;
    private TextView desc;
    private TextView alarmDate;
    private TextView createDate;
    private TextView color;
    private CheckBox important;

    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_details, container, false);
        initializeViews(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCurrentNoteAndSet();
    }

    private void getCurrentNoteAndSet() {

        currentNote = Parcels.unwrap(getArguments().getParcelable("currentNote"));

        title.setText(currentNote.getTitle());
        desc.setText(currentNote.getDescription());
        alarmDate.setText(currentNote.getAlarmDate() != null? currentNote.getAlarmDate().toString() : "");
        createDate.setText(currentNote.getCreateDate().toString());
        color.setBackgroundColor(currentNote.getColor());
        important.setChecked(currentNote.isImportant());
    }

    private void initializeViews(View v) {
        title = (TextView) v.findViewById(R.id.fragment_note_details_title);
        desc = (TextView) v.findViewById(R.id.fragment_note_details_desc);
        alarmDate = (TextView) v.findViewById(R.id.fragment_note_details_alarm_date);
        createDate = (TextView) v.findViewById(R.id.fragment_note_details_create_date);
        color = (TextView) v.findViewById(R.id.fragment_note_details_color_btn);
        important = (CheckBox) v.findViewById(R.id.fragment_note_details_important);
    }

}
