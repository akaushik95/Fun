package com.example.ashukaushik.fun;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NowPlaying extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mMediaPlayer;
    ArrayList<SongsWithoutCoverArt> songsList;
    int position;
    Uri u;
    SeekBar mSeekBar;
    Button mPlayButton;
    Button mPreviousSongButton;  // changed name of buttons from play to playb and so on. 26-07-2016
    Button mNextSongButton;
    Thread mThreadSeekBar;
    TextView mSongNameTextView;
    ImageView mAlbumArtImageView;
    String temp;
    EditText mgetInfoEditText;
    Button mgetInfoOKButton;

    @Override
    public void onBackPressed() {
        mMediaPlayer.stop();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        mSongNameTextView=(TextView)findViewById(R.id.songNameTextView);
        mAlbumArtImageView=(ImageView) findViewById(R.id.albumArtImageView);
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
        u = Uri.parse(songsList.get(position).getSongPath().toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), u);

        mMediaPlayer.start();

        setScreen(position,songsList);
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

//    public static String removeUnwantedChararctersFromString(String s){
//        String str=s.toLowerCase();
//        String snew="";
//        int count=0;
//        for(int i=0;i<s.length();i++){
//            if((str.charAt(i)>='a' && str.charAt(i)<='z')||(str.charAt(i)==' ')){
//                if(str.charAt(i)==' ' && snew!=null){
//                    count++;
//                }
//                snew=snew+str.charAt(i);
//                if(count==2){
//                    return snew;
//                }
//            }else{
//                continue;
//            }
//        }
//        return snew;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.now_playing_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.getInfo){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setId(0);
            builder.setView(input);
            builder.setTitle("GET INFO");
            builder.setMessage("Enter Correct song name");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    Retrofit retrofit=new Retrofit.Builder().baseUrl("https://itunes.apple.com/")
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();

                    final SongInfoInterface songInterface=retrofit.create(SongInfoInterface.class);
                    Call<SongInfoResponse> call=songInterface.getSong(value,"in",1);
                    call.enqueue(new Callback<SongInfoResponse>() {
                        @Override
                        public void onResponse(Call<SongInfoResponse> call, Response<SongInfoResponse> response) {
                            SongInfoResponse obj=response.body();
                            if(obj.getResults().size()==0){
                                return;
                            }
                            else{
                                Picasso.with(getApplicationContext()).load(obj.getResults().get(0).getArtworkUrl100()).into(mAlbumArtImageView);
                            }
                        }

                        @Override
                        public void onFailure(Call<SongInfoResponse> call, Throwable t) {

                        }
                    });
                    dialog.cancel();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog alert = builder.create();
            alert.setTitle("GET INFO");
            alert.show();


//            Retrofit retrofit=new Retrofit.Builder().baseUrl("https://itunes.apple.com/")
//                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();
//
//            final SongInfoInterface songInterface=retrofit.create(SongInfoInterface.class);
//            Call<SongInfoResponse> call=songInterface.getSong(msongNameforInfo,"in",1);
//            call.enqueue(new Callback<SongInfoResponse>() {
//                @Override
//                public void onResponse(Call<SongInfoResponse> call, Response<SongInfoResponse> response) {
//                    SongInfoResponse obj=response.body();
//                    if(obj.getResults().size()==0){
//                        return;
//                    }
//                    else{
//                        Picasso.with(getApplicationContext()).load(obj.getResults().get(0).getArtworkUrl100()).into(mAlbumArtImageView);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<SongInfoResponse> call, Throwable t) {
//
//                }
//            });

            return true;
        }
        return super.onOptionsItemSelected(item);
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
        }
        else if(id == R.id.nextSongButton){
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
            u=Uri.parse(songsList.get(position).getSongPath().toString());
            mMediaPlayer=MediaPlayer.create(getApplicationContext(),u);
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mSeekBar.setProgress(0);
            setScreen(position,songsList);
            mPlayButton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_48dp));
        }
        else if(id == R.id.previousSongButton){
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
            u=Uri.parse(songsList.get(position).getSongPath().toString());
            mMediaPlayer=MediaPlayer.create(getApplicationContext(),u);
            mMediaPlayer.start();
            mSeekBar.setMax(mMediaPlayer.getDuration());
            mSeekBar.setProgress(0);
            setScreen(position,songsList);
            mPlayButton.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_48dp));
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setScreen(int position, ArrayList<SongsWithoutCoverArt> songsList){
        MediaMetadataRetriever mMediaMetadataRetriever=new MediaMetadataRetriever();
        mMediaMetadataRetriever.setDataSource(songsList.get(position).getSongPath());

        String titleName = mMediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if(titleName==null){
            mSongNameTextView.setText("JUST ENJOY");
        }
        else {
            mSongNameTextView.setText(titleName);
        }
        byte[] artBytes =  mMediaMetadataRetriever.getEmbeddedPicture();
        if(artBytes!=null){
            Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
            mAlbumArtImageView.setImageBitmap(bm);
        }
        else{
            mAlbumArtImageView.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_48dp));
        }
    }
}
