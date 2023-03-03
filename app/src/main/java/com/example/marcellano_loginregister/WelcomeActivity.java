package com.example.marcellano_loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    Button closeThread, nextThread;
    ImageView adImage;
    private volatile boolean closeThreadFlag = false;
    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        adImage = findViewById(R.id.adImageView);
        adImage.setImageResource(R.drawable.ad1);
        nextThread = findViewById(R.id.nextButton);
        closeThread = findViewById(R.id.closeButton);

        nextThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextThread(5);
            }
        });

        closeThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeThread();
            }
        });
    }

    public void nextThread(int seconds)
    {
        for(int i =0; i< seconds; i++){
            Log.d("THREAD ACTIVITY", "Start Thread : " + i);
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        AdsRunnable runnable = new AdsRunnable(5);
        new Thread(runnable).start();
    }

    public void closeThread()
    {
        closeThreadFlag = true;
        adImage.setVisibility(View.GONE);
        nextThread.setEnabled(false);
        closeThread.setEnabled(false);
    }

    class AdsRunnable implements Runnable
    {
        int seconds;

        //Non-Default Constructor
        AdsRunnable(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i =0; i< seconds; i++){

                if(closeThreadFlag)
                {
                    return;
                }

                if(i == 2) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adImage.setImageResource(R.drawable.ad2);
                        }
                    });
                }

                Log.d("THREAD ACTIVITY", "Start Thread : " + i);

                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}