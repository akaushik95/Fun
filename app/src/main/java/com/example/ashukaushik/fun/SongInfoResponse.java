package com.example.ashukaushik.fun;

import java.util.ArrayList;

/**
 * Created by ashukaushik on 20/08/16.
 */
public class SongInfoResponse {
    public ArrayList<Data> results;

    public ArrayList<Data> getResults() {
        return results;
    }

    public void setResults(ArrayList<Data> results) {
        this.results = results;
    }

    public class Data{
        private String artworkUrl100;

        public Data(String artworkUrl100) {
            this.artworkUrl100 = artworkUrl100;
        }

        public String getArtworkUrl100() {
            return artworkUrl100;
        }

        public void setArtworkUrl100(String artworkUrl100) {
            this.artworkUrl100 = artworkUrl100;
        }
    }
}
