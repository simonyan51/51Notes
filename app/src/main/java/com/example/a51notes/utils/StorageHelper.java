package com.example.a51notes.utils;

import android.content.Context;
import android.util.Log;

import com.example.a51notes.App;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by simonyan51 on 6/4/17.
 */

public class StorageHelper {

    public static  <T extends Serializable> void serialize(String fileName, T obj) {

        try {
            FileOutputStream fileOut = App.getInstance().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String fileName) {
        Object obj = null;

        try {
            FileInputStream fileIn = App.getInstance().openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
