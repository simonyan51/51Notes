package com.example.a51notes.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a51notes.R;
import com.example.a51notes.pojos.Note;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;


public class CreateNoteFragment extends Fragment implements View.OnClickListener  {

    public static final String TAG = ".a51notes.fragments.CreateNoteFragment";

    private EditText title;
    private EditText desc;
    private TextView alarmDate;
    private TextView color;
    private CheckBox important;
    private Button createBtn;

    private Note newNote;

    private Date selectedDate = null;

    private int selectedColor = Color.RED;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_note, container, false);

        getActivity().setTitle("Create Note");

        title = (EditText) v.findViewById(R.id.fragment_create_note_title);

        desc = (EditText) v.findViewById(R.id.fragment_create_note_desc);

        alarmDate = (TextView) v.findViewById(R.id.fragment_create_note_alarm_date);
        alarmDate.setOnClickListener(this);

        color = (TextView) v.findViewById(R.id.fragment_create_note_color_btn);
        color.setOnClickListener(this);

        important = (CheckBox) v.findViewById(R.id.fragment_create_note_important);

        createBtn = (Button) v.findViewById(R.id.fragment_create_note_create_btn);
        createBtn.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_create_note_alarm_date:
                selectDateTime();
                break;
            case R.id.fragment_create_note_color_btn:
                chooseColor();
                break;
            case R.id.fragment_create_note_create_btn:
                createUser();
                break;
        }
    }

    private void selectDateTime() {
        final SlideDateTimeListener listener = new SlideDateTimeListener() {

            @Override
            public void onDateTimeSet(Date date)
            {
                alarmDate.setText(date.toString());
                selectedDate = date;
            }

            @Override
            public void onDateTimeCancel()
            {
                alarmDate.setText("");
                selectedDate = null;
            }
        };

        new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .build()
                .show();

    }


    private void createUser() {
        Bundle note = new Bundle();
        newNote = new Note(title.getText().toString(),
                             desc.getText().toString(),
                             selectedColor,
                             null,
                             important.isChecked());
        note.putSerializable("newNote", newNote);

        NotesListFragment notesListFragment = new NotesListFragment();
        notesListFragment.setArguments(note);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container, notesListFragment, notesListFragment.TAG);
        transaction.commit();
    }

    private void chooseColor() {
        ColorPicker colorPicker = new ColorPicker(getActivity());
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                selectedColor = color;
                getActivity().findViewById(R.id.fragment_create_note_color_btn).setBackgroundColor(selectedColor);
            }

            @Override
            public void onCancel() {
                selectedColor = Color.RED;
            }
        });
    }
}
