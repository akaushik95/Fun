package com.example.ashukaushik.fun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ashukaushik on 19/08/16.
 */
public class SongListAdapter extends ArrayAdapter<Songs>{
    ArrayList<Songs> mData;
    Context context;
    public SongListAdapter(Context context,ArrayList<Songs> Obj){
        super(context,0,Obj);
        this.context=context;
        this.mData=Obj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null){
            v= LayoutInflater.from(this.context).inflate(R.layout.songlayoutinlistview,parent,false);
        }
        Songs obj=mData.get(position);
        TextView songName=(TextView) v.findViewById(R.id.songNameTextViewinSongLayout);
        TextView songArtist=(TextView)v.findViewById(R.id.songMetadataTextViewinSongLayout);
        ImageView albumArt=(ImageView)v.findViewById(R.id.albumArtImageViewinSongLayout);
        if(obj.getCoverArt()!=null){
            Bitmap bm = BitmapFactory.decodeByteArray(obj.coverArt, 0, obj.coverArt.length);
            albumArt.setImageBitmap(bm);
        }
        songName.setText(obj.getSongName());
        songArtist.setText(obj.getSongArtist());
        return v;
    }
}
