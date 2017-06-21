package com.example.a51notes.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;

import com.example.a51notes.broadcasts.AlarmDateBroadcastReceiver;
import com.example.a51notes.pojos.Note;

import org.parceler.Parcels;

/**
 * Created by simonyan51 on 6/20/17.
 */

public class NoteAlarmHelper {
    public static void setAlarm(Note note, Activity activity, Context context) {
        if (note.getAlarmDate() == null || note.getAlarmDate().getTime() <= System.currentTimeMillis()) {
            return;
        }

        AlarmManager alarmNote = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        Intent noteIntent = new Intent(activity, AlarmDateBroadcastReceiver.class);

        Parcelable createdNote = Parcels.wrap(note);

        noteIntent.putExtra("currentNote", createdNote);

        final int _id = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, _id, noteIntent, PendingIntent.FLAG_ONE_SHOT);

        alarmNote.set(AlarmManager.RTC_WAKEUP, note.getAlarmDate().getTime(), pendingIntent);
    }
}
