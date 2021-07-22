package com.s2icode.codemeterandroid2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class CodemeterActivity extends AppCompatActivity {
    static {
        System.loadLibrary("native-lib-codemeter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codemeter);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    public native String stringFromJNI();
}
