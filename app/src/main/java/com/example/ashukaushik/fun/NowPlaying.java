package com.example.ashukaushik.fun;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class NowPlaying extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mp;
    ArrayList<File> songs;
    int position;
    Uri u;
    SeekBar sb;
    Button playb;
    Button prevb;  // changed name of buttons from play to playb and so on. 26-07-2016
    Button nextb;
    Button stopb;
    Thread updateSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        sb = (SeekBar) findViewById(R.id.seekBar);
        updateSeekbar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp.getDuration();
                int currentPosition = 0;


                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mp.getCurrentPosition();
                        sb.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        playb = (Button) findViewById(R.id.play);
        stopb = (Button) findViewById(R.id.stop);
        prevb = (Button) findViewById(R.id.prev);
        nextb = (Button) findViewById(R.id.next);


        playb.setOnClickListener(this);
        stopb.setOnClickListener(this);
        prevb.setOnClickListener(this);
        nextb.setOnClickListener(this);

        if (mp != null) {
            mp.stop();
            mp.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        songs = (ArrayList) b.getParcelableArrayList("List");
        position = b.getInt("pos", 0);
        u = Uri.parse(songs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        playb.setText("PAUSE");
        sb.setMax(mp.getDuration());
        updateSeekbar.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id == R.id.play){
            if(playb.getText()=="PAUSE"){
                playb.setText("PLAY");
                mp.pause();

            }else if(playb.getText()=="PLAY"){
                playb.setText("PAUSE");
                mp.start();
            }
        }else if(id == R.id.stop){
            playb.setText("PLAY");
            mp.stop();
            mp.seekTo(0);

//            sb.setProgress();
        }else if(id == R.id.next){

        }else if(id == R.id.prev){

        }
    }

}
