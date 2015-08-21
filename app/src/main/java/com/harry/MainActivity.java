package com.harry;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static NetworkConnection networkConnection;
    MyHandler myHandler;
    static TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        myHandler = new MyHandler();

    }

    public void fetchDataOnButton(View view) {
        networkConnection = new NetworkConnection();
        networkConnection.fetchData(myHandler);

        String s = "give hello";
        Message giveMessage = networkConnection.testHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("giveString", s);
        giveMessage.setData(bundle);
        networkConnection.testHandler.sendMessage(giveMessage);

    }



    static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d("test", "on Handle message");
            String s = message.getData().getString("string");
            text.setText(s);
        }
    }
}
