package com.example.postgre_1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class ButtonClass extends AppCompatActivity {
    Context context;
    String message ="asassasa";
    ButtonClass(Context context1){context=context1;}
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // storing ID of the button
        // in a variable
        Button button = (Button)findViewById(R.id.button1);

        // operations to be performed
        // when user tap on the button
        if (button != null) {
            button.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    Log.i("button","button");

                    // displaying a toast message
                    Toast.makeText(context, R.string.message, Toast.LENGTH_LONG).show();
                }
            }));
        }
    }
}
