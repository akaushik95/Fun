package com.example.ashukaushik.fun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ashukaushik on 19/08/16.
 */
//public class SongListAdapterSongs extends ArrayAdapter<Songs>{
//    ArrayList<Songs> mData;
//    Context context;
//
//    public SongListAdapterSongs(Context context, ArrayList<Songs> Obj){
//        super(context,0,Obj);
//        this.context=context;
//        this.mData=Obj;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View v=convertView;
//        if(v==null){
//            v= LayoutInflater.from(this.context).inflate(R.layout.songlayoutinlistview,parent,false);
//        }
//        Songs obj=mData.get(position);
//        TextView songName=(TextView) v.findViewById(R.id.songNameTextViewinSongLayout);
//        ImageView albumArt=(ImageView)v.findViewById(R.id.albumArtImageViewinSongLayout);
//        if(obj.getCoverArt()!=null){
//            Bitmap bm = BitmapFactory.decodeByteArray(obj.coverArt, 0, obj.coverArt.length);
//            albumArt.setImageBitmap(bm);
//        }
//        songName.setText(obj.getSongName());
//        return v;
//    }
//}
public class SongListAdapterSongs extends RecyclerView.Adapter<SongListAdapterSongs.ourHolder> {
    Context mcontext;
    ArrayList<Songs> mData;
    songsListListener listener;

    public interface songsListListener{
        void songClicked(Songs s);
    }

    public SongListAdapterSongs(Context context, ArrayList<Songs> songs, songsListListener songsListListener) {
        super();
        this.mcontext=context;
        this.mData=songs;
        this.listener=songsListListener;
    }

    public class ourHolder extends RecyclerView.ViewHolder{

        public ourHolder(View itemView) {
            super(itemView);
        }
        TextView tv1 = (TextView) itemView.findViewById(R.id.songNameTextViewinSongLayout);
        ImageView albumArt=(ImageView)itemView.findViewById(R.id.albumArtImageViewinSongLayout);
    }
    @Override
    public SongListAdapterSongs.ourHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.songlayoutinlistview, parent, false);
        return new ourHolder(v);
    }
    @Override
    public void onBindViewHolder(SongListAdapterSongs.ourHolder holder, int position) {
        final Songs songs = mData.get(position);
        holder.tv1.setText(songs.getSongName());
        if(songs.getCoverArt()!=null){
            Bitmap bm = BitmapFactory.decodeByteArray(songs.coverArt, 0, songs.coverArt.length);
            holder.albumArt.setImageBitmap(bm);
        }
        holder.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.songClicked(songs);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
}