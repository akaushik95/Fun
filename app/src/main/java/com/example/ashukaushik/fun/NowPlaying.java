package com.example.ashukaushik.fun;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class NowPlaying extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mMediaPlayer;
    ArrayList<File> songsList;
    int position;
    Uri u;
    SeekBar mSeekBar;
    Button mPlayButton;
    Button mPreviousSongButton;  // changed name of buttons from play to playb and so on. 26-07-2016
    Button mNextSongButton;
    Thread mThreadSeekBar;

    TextView mSongNameTextView;
    ImageView mAlbumArtImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        mSongNameTextView=(TextView)findViewById(R.id.songNameTextView);
        mAlbumArtImageView=(ImageView)findViewById(R.id.albumArtImageView);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mThreadSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mMediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mMediaPlayer.getCurrentPosition();
                        mSeekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        mPlayButton=(Button)findViewById(R.id.playButton);
        mPreviousSongButton=(Button)findViewById(R.id.previousSongButton);
        mNextSongButton=(Button)findViewById(R.id.nextSongButton);


        mPlayButton.setOnClickListener(this);
        mPreviousSongButton.setOnClickListener(this);
        mNextSongButton.setOnClickListener(this);

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        songsList = (ArrayList) b.getParcelableArrayList("List");
        position = b.getInt("pos", 0);
        setScreen(position,songsList);
        u = Uri.parse(songsList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), u);
        mMediaPlayer.start();

//        setScreen(position,songsList);
        mSeekBar.setMax(mMediaPlayer.getDuration());
        mThreadSeekBar.start();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });

    }



    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id == R.id.playButton){
            if(mMediaPlayer.isPlaying()){
                mPlayButton.setBackground(getDrawable(R.drawable.ic_play_circle_filled_white_black_48dp));
                mMediaPlayer.pause();
            }else{
                mMediaPlayer.start();
                mPlayButton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_48dp));
            }
        }else if(id == R.id.nextSongButton){
            if(mMediaPlayer != null) {
                try{
                    mMediaPlayer.stop(); //error
                    mMediaPlayer.reset();
                    mMediaPlayer.release();
                }catch(Exception e){
                    Log.d("Notify", e.toString());
                }
            }
            position=(position+1)%songsList.size();
            u=Uri.parse(songsList.get(position).toString());
            mMediaPlayer=MediaPlayer.create(getApplicationContext(),u);
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            setScreen(position,songsList);
            mPlayButton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_48dp));
        }else if(id == R.id.previousSongButton){
            if(mMediaPlayer != null) {
                try{
                    mMediaPlayer.stop(); //error
                    mMediaPlayer.reset();
                    mMediaPlayer.release();
                }catch(Exception e){
                    Log.d("Notify", e.toString());
                }
            }
            position=(position-1)<0?songsList.size()-1:position-1;
            u=Uri.parse(songsList.get(position).toString());
            mMediaPlayer=MediaPlayer.create(getApplicationContext(),u);
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            setScreen(position,songsList);
            mPlayButton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_48dp));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setScreen(int position, ArrayList<File> songsList){
        MediaMetadataRetriever mMediaMetadataRetriever=new MediaMetadataRetriever();
        mMediaMetadataRetriever.setDataSource(songsList.get(position).getPath());
        String titleName = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if(titleName==null){
            mSongNameTextView.setText("No meta data");
        }
        else {
            mSongNameTextView.setText(titleName);
        }
        byte[] artBytes =  mMediaMetadataRetriever.getEmbeddedPicture();
        if(artBytes!=null)
        {

            Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
            mAlbumArtImageView.setImageBitmap(bm);
        }
        else
        {
            mAlbumArtImageView.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_48dp));
        }
    }
}
