package com.s2icode.codemeterandroid2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TCPEchoClient tcpEchoClient;
    private TCPEchoServer tcpEchoServer;
    // Used to load the 'native-lib' library on application startup.
    static {
        //System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        Button button = findViewById(R.id.button);
        button.setOnClickListener(e-> {
            tcpEchoServer = new TCPEchoServer();
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(e-> {
            tcpEchoClient.sendMsgInt(2);
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(e-> {
            tcpEchoClient.sendMsgInt(3);
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(e-> {
            tcpEchoClient = new TCPEchoClient();
        });
        //

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}