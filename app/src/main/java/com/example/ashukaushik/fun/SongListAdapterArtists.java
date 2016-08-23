package com.example.ashukaushik.fun;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashukaushik on 23/08/16.
 */
//public class SongListAdapterArtists extends ArrayAdapter<Songs> {
//    ArrayList<Songs> mData;
//    Context context;
//
//    public SongListAdapterArtists(Context context,ArrayList<Songs> Obj){
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
//        TextView albumName=(TextView) v.findViewById(R.id.songNameTextViewinSongLayout);
////        TextView songArtist=(TextView)v.findViewById(R.id.songMetadataTextViewinSongLayout);
//        ImageView albumArt=(ImageView)v.findViewById(R.id.albumArtImageViewinSongLayout);
////        if(obj.getCoverArt()!=null){
////            Bitmap bm = BitmapFactory.decodeByteArray(obj.coverArt, 0, obj.coverArt.length);
////            albumArt.setImageBitmap(bm);
////        }
//        albumName.setText(obj.getSongArtist());
////        songArtist.setText(obj.getSongArtist());
//        return v;
//    }
//}

public class SongListAdapterArtists extends RecyclerView.Adapter<SongListAdapterArtists.ourHolder> {
    Context mcontext;
    ArrayList<Songs> mData;
    songsListListener listener;

    public interface songsListListener{
        void songClicked(Songs s);
    }

    public SongListAdapterArtists(Context context, ArrayList<Songs> songs,songsListListener songsListListener) {
        super();
        this.mcontext = context;
        this.mData = songs;
        this.listener=songsListListener;
    }

    public class ourHolder extends RecyclerView.ViewHolder {

        public ourHolder(View itemView) {
            super(itemView);
        }

        TextView tv1 = (TextView) itemView.findViewById(R.id.songNameTextViewinSongLayout);
        ImageView albumArt=(ImageView)itemView.findViewById(R.id.albumArtImageViewinSongLayout);
    }

    @Override
    public SongListAdapterArtists.ourHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.songlayoutinlistview, parent, false);
        return new ourHolder(v);
    }

    @Override
    public void onBindViewHolder(SongListAdapterArtists.ourHolder holder, int position) {
        final Songs songs = mData.get(position);
        holder.tv1.setText(songs.getSongArtist());
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