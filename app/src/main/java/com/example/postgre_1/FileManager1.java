package com.example.postgre_1;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager1 extends AppCompatActivity {
    private final static String FILE_NAME = "content.txt";

    public FileManager1() {


    }

    public void writeText(String textToWrite) throws IOException {
        try {
            FileOutputStream myFile = openFileOutput("logText.txt", MODE_PRIVATE);
            myFile.write(textToWrite.getBytes());
            myFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
