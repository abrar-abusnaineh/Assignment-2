package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
private Spinner Hours;
private Spinner Minutes;
private Spinner Seconds;
    private int seconds;
    private boolean running;
//    private int left_seconds=started_seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Hours=findViewById(R.id.hours);
        Minutes=findViewById(R.id.minuite);
        Seconds=findViewById(R.id.second);
        String [] noHours=hoursSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,noHours);
        //to add values to spinner
        Hours.setAdapter(adapter);
        String [] noMin=minSpinner();
        ArrayAdapter<String> adapterMin = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,noMin);
        //to add values to spinner
       Minutes.setAdapter(adapterMin);
        String [] noSec=secSpinner();
        ArrayAdapter<String> adapterSec = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,noSec);
        //to add values to spinner
        Seconds.setAdapter(adapterSec);
        if(savedInstanceState !=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }


        runTimer();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
        bundle.putInt("seconds", seconds);
        bundle.putBoolean("running", running);
    }
    public void updateTextView(View view){
        final TextView txtView = (TextView) findViewById(R.id.txtView);
        String sec=Seconds.getSelectedItem().toString();
        String min=Minutes.getSelectedItem().toString();
        String hou=Hours.getSelectedItem().toString();
        if(sec.length()==0 && min.length()==0 && hou.length()==0)
        {
            Toast toast= Toast.makeText(this,"Determine the time please",Toast.LENGTH_SHORT);
            toast.show();
            return;}
        if(hou.length()!=0){
            seconds=seconds+(Integer.parseInt(hou)*3600);
        }
        if(min.length()!=0)
            seconds=seconds+(Integer.parseInt(min)*60);
        if(sec.length()!=0)
            seconds=seconds+(Integer.parseInt(sec));

        int hours = seconds/3600;
        int minutes = seconds % 3600 /60;
        int snds = seconds % 60;
        String time="";
        if(hours!=0){
            time = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, snds);}
        else{
            time = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, snds);}
//        String time = String.format(Locale.getDefault(),
//                "%d:%02d:%02d", hours, minutes, snds);
        txtView.setText(time);
    }
    public void onClickStop(View view) {
        seconds = 0;
        running = false;
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickPause(View view) {
        running = false;
    }
    public String[] hoursSpinner(){
        String[] hours=new String[24];
        for(int i=0;i<=23;i++)
            hours[i]=String.valueOf(i);
        return hours;

    } public String[] minSpinner(){
        String[] min=new String[60];
        for(int i=0;i<=59;i++)
            min[i]=String.valueOf(i);
        return min;

    } public String[] secSpinner(){
        String[] sec=new String[60];
        for(int i=0;i<=59;i++)
            sec[i]=String.valueOf(i);
        return sec;

    }
    private void runTimer(){
        final TextView txtView = (TextView) findViewById(R.id.txtView);
        final Button resumeButton = (Button) findViewById(R.id.btnStart);
        final Handler handler = new Handler();
        Toast  toast= Toast.makeText(this,"Timer is finished",Toast.LENGTH_SHORT);

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = seconds % 3600 /60;
                int snds = seconds % 60;
                String time="";
                if(hours!=0){
                time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, snds);}
                else{
                    time = String.format(Locale.getDefault(),
                            "%02d:%02d", minutes, snds);}


                txtView.setText(time);
                if(running){
                    resumeButton.setText("Resume");
                    if(seconds>0)
                    --seconds;
                    else{
                    toast.show();
                        seconds=0;
                        resumeButton.setText("Start");
                    }
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}