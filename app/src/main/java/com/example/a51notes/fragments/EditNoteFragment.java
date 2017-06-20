package com.example.a51notes.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a51notes.R;
import com.example.a51notes.pojos.Note;
import com.example.a51notes.utils.NoteAlarmHelper;
import com.example.a51notes.utils.note.FileNoteStorage;
import com.example.a51notes.utils.note.NoteStorage;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.parceler.Parcels;

import java.util.Date;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "a51notes.fragments.EditNoteFragment";

    private EditText title;
    private EditText desc;
    private TextView alarmDate;
    private TextView color;
    private CheckBox important;
    private Button createBtn;
    private FragmentTransaction transaction;

    private int selectedColor;

    private NoteStorage storage;

    private Note note;


    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_edit_note, container, false);

        storage = new FileNoteStorage();

        title = (EditText) v.findViewById(R.id.fragment_edit_title);

        desc = (EditText) v.findViewById(R.id.fragment_edit_desc);

        alarmDate = (TextView) v.findViewById(R.id.fragment_edit_alarm_date);
        alarmDate.setOnClickListener(this);

        color = (TextView) v.findViewById(R.id.fragment_edit_color_btn);
        color.setOnClickListener(this);

        important = (CheckBox) v.findViewById(R.id.fragment_edit_important);

        createBtn = (Button) v.findViewById(R.id.fragment_edit_btn);
        createBtn.setOnClickListener(this);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        note = Parcels.unwrap(getArguments().getParcelable("editNote"));

        selectedColor = note.getColor();

        title.setText(note.getTitle());
        desc.setText(note.getDescription());
        alarmDate.setText(note.getAlarmDate() != null? note.getAlarmDate().toString() : "No Alarm Date");
        color.setBackgroundColor(note.getColor());
        important.setChecked(note.isImportant());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_edit_alarm_date:
                selectDateTime();
                break;
            case R.id.fragment_edit_color_btn:
                chooseColor();
                break;
            case R.id.fragment_edit_btn:
                editNote();
                break;
        }
    }

    private void selectDateTime() {
        final SlideDateTimeListener listener = new SlideDateTimeListener() {

            @Override
            public void onDateTimeSet(Date date)
            {
                alarmDate.setText(date.toString());
                note.setAlarmDate(date);
            }

            @Override
            public void onDateTimeCancel()
            {
                alarmDate.setText(note.getAlarmDate() != null? note.getAlarmDate().toString() : "");
            }
        };

        new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .build()
                .show();

    }

    private void chooseColor() {
        ColorPicker colorPicker = new ColorPicker(getActivity());
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                selectedColor = color;
                getActivity().findViewById(R.id.fragment_edit_color_btn).setBackgroundColor(selectedColor);
            }

            @Override
            public void onCancel() {
                selectedColor = note.getColor();
            }
        });
    }

    private void editNote() {
        note.setTitle(title.getText().toString());
        note.setDescription(desc.getText().toString());
        note.setColor(selectedColor);
        note.setImportant(important.isChecked());
        NoteAlarmHelper.setAlarm(note, getActivity(), getContext());
        storage.updateNote(note, null);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container, new NotesListFragment()).commit();

    }
}
