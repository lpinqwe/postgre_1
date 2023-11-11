package com.example.postgre_1;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager1 extends AppCompatActivity {
    String saveData = "";

    public void addData(String dataToSaveAndSend){
        saveData = saveData+dataToSaveAndSend;
    }
    public String msgCurrentTime(){
        return String.valueOf((System.currentTimeMillis()));
    }
    public String getData(){
        return this.saveData;
    }
    public void clearData(){
        saveData="";
    }


}
