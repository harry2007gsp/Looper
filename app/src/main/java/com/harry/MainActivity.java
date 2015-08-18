package com.harry;

import android.os.Bundle;
import android.os.Handler;
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
//        Runnable runnable = new Runnable() {
//            public String runnableString = "abcd";
//
//            @Override
//            //this runnable is just like first wait for something to get executed and processed and then this
//            // would be displayed after that.
//            public void run() {
//                Log.d("test", runnableString);
//            }
//        };
        networkConnection = new NetworkConnection();
        networkConnection.fetchData(myHandler);
        networkConnection.handlerThreadMethod();

    }

    public static class MyRunnable implements Runnable {
        private final String message;

        MyRunnable(final String message) {
            this.message = message;
        }
        public void run() {
            Log.d("test", message);
            text.setText(message); // gettting data from background thread and publishing on UI
            networkConnection.testHandler.sendMessage(networkConnection.testHandler.obtainMessage());
            networkConnection.testHandler2.sendMessage(networkConnection.testHandler2.obtainMessage());

        }
    }


    static class MyHandler extends Handler {

//        @Override
//        public void handleMessage(Runnable runnable) {
//            super.handleMessage(runnable);
//            Log.d("test", "on Handle message");
//            String s = runnable.getData().getString("string");
//            text.setText(s);
//        }
    }
}
