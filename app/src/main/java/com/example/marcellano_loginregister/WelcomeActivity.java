package com.example.marcellano_loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
//  this is set as volatile so if there are changes made it will be immediately visible to all threads that access it.
    private volatile boolean skipButtonFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//      create a new instance of the Dialog class
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.ads_layout);

//      close dialog on touch outside
//      dialog.setCanceledOnTouchOutside(true);
//      Set title
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // fullscreen
        dialog.setTitle("Advertisement");

//      Creates a new instance of the Handler class
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
            // delay in milliseconds, here it's set to 5 seconds
        }, 5000);

        Button skipButton = dialog.findViewById(R.id.skipButton);
        View skipThread = findViewById(R.id.skipButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipThread (5);
                dialog.dismiss();
            }
        });
    }

    public void skipThread (int seconds)
    {
        for(int i =0; i< seconds; i++){
            Log.d("THREAD ACTIVITY", "Start Thread : " + i);
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AdsRunnable runnable = new AdsRunnable(10);
        new Thread(runnable).start();
    }

    class AdsRunnable implements Runnable
    {
        int seconds;

        //Non-Default Constructor
        AdsRunnable(int seconds)
        {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i =0; i< seconds; i++)
            {
                if(skipButtonFlag){

                    return;
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