package com.example.ashukaushik.fun;

import java.io.Serializable;

/**
 * Created by ashukaushik on 23/08/16.
 */
public class SongsWithoutCoverArt implements Serializable {
    public String songName;
    public String songArtist;
    public String songAlbum;
    public String songDuration;
    public String songPath;

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public SongsWithoutCoverArt(String songName, String songArtist, String songAlbum, String songDuration, String songPath) {
        if(songName!=null)
            this.songName = songName;
        else
            this.songName="Unknown name";
        if(songArtist!=null)
            this.songArtist = songArtist;
        else
            this.songArtist="Unknown Artist";
        if(songAlbum!=null)
            this.songAlbum = songAlbum;
        else
            this.songAlbum="Unknown Album";

        this.songDuration = songDuration;

        this.songPath=songPath;

    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

}
