package com.example.ashukaushik.fun;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SongListAdapterSongs.songsListListener,
        SongListAdapterAlbums.songsListListener, SongListAdapterArtists.songsListListener {
    static ArrayList<Songs> songs=new ArrayList<>();
    static ArrayList<SongsWithoutCoverArt> songsWithoutCoverArt =new ArrayList<>();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static SongListAdapterSongs songsAdapter;
    static SongListAdapterAlbums albumsAdapter;
    static SongListAdapterArtists artistsAdapter;
//    static ListView lv;
    static RecyclerView rv;
    DrawerLayout drawer;
    Toolbar toolbar;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songs=getSongs(Environment.getExternalStorageDirectory());
        songsWithoutCoverArt =getSongsWithoutCoverArt(Environment.getExternalStorageDirectory());
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.libraryMenu) {
            // Handle the camera action
        } else if (id == R.id.playlistsMenu) {

        } else if (id == R.id.queueMenu) {

        } else if (id == R.id.nowPlayingMenu) {
//            Intent i=new Intent();
//            i.setClass(this,NowPlaying.class);
//            startActivity(i);
        } else if (id == R.id.shareMenu) {

        } else if (id == R.id.sendMenu) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void songClicked(Songs s) {
        int i=songs.indexOf(s);
        Intent intent=new Intent();
        intent.setClass(getApplicationContext(),NowPlaying.class);
        intent.putExtra("pos",i);
        final ArrayList<SongsWithoutCoverArt> finalSongs = songsWithoutCoverArt;
        intent.putExtra("List", finalSongs);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(songs.get(i).getSongName())
                .setContentText("ALBUM"+songs.get(i).getSongAlbum())
                .setSmallIcon(android.R.drawable.btn_radio);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
        startActivity(intent);

    }


    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            rv=(RecyclerView)rootView.findViewById(R.id.recyclerView);
            songsAdapter=new SongListAdapterSongs(getActivity(), songs, (SongListAdapterSongs.songsListListener) getActivity());
            rv.setAdapter(songsAdapter);
            LinearLayoutManager lm=new LinearLayoutManager(getActivity());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
//            ListView lv=(ListView)rootView.findViewById(R.id.listView) ;
//            lv=(ListView)rootView.findViewById(R.id.listView) ;
//            songsAdapter=new SongListAdapterSongs(getActivity(),songs);
//            lv.setAdapter(songsAdapter);
//            final ArrayList<SongsWithoutCoverArt> finalSongs = songsWithoutCoverArt;
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent i=new Intent();
//                    i.setClass(getContext(),NowPlaying.class);
//                    i.putExtra("pos",position);
//                    i.putExtra("List", finalSongs);
//                    startActivity(i);
//                }
//            });
            return rootView;
        }
    }

    public static class PlaceholderFragment1 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number_1";

        public PlaceholderFragment1() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment1 newInstance(int sectionNumber1) {
            PlaceholderFragment1 fragment1 = new PlaceholderFragment1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber1);
            fragment1.setArguments(args);
            return fragment1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            rv=(RecyclerView)rootView.findViewById(R.id.recyclerView);
            albumsAdapter=new SongListAdapterAlbums(getActivity(),songs, (SongListAdapterAlbums.songsListListener) getActivity());
            rv.setAdapter(albumsAdapter);
            LinearLayoutManager lm=new LinearLayoutManager(getActivity());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
//            ListView lv=(ListView)rootView.findViewById(R.id.listView) ;
//            lv=(ListView)rootView.findViewById(R.id.listView) ;
//            albumsAdapter=new SongListAdapterAlbums(getActivity(),songs);
//            lv.setAdapter(albumsAdapter);
//            final ArrayList<SongsWithoutCoverArt> finalSongs = songsWithoutCoverArt;
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent i=new Intent();
//                    i.setClass(getContext(),NowPlaying.class);
//                    i.putExtra("pos",position);
//                    i.putExtra("List", finalSongs);
//                    startActivity(i);
//                }
//            });
            return rootView;
        }
    }

    public static class PlaceholderFragment2 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number_2";

        public PlaceholderFragment2() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment2 newInstance(int sectionNumber2) {
            PlaceholderFragment2 fragment2 = new PlaceholderFragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber2);
            fragment2.setArguments(args);
            return fragment2;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            rv=(RecyclerView)rootView.findViewById(R.id.recyclerView);
            artistsAdapter=new SongListAdapterArtists(getActivity(),songs, (SongListAdapterArtists.songsListListener) getActivity());
            rv.setAdapter(artistsAdapter);
            LinearLayoutManager lm=new LinearLayoutManager(getActivity());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(lm);
//            ListView lv=(ListView)rootView.findViewById(R.id.listView) ;
//            lv=(ListView)rootView.findViewById(R.id.listView) ;
//            artistsAdapter=new SongListAdapterArtists(getActivity(),songs);
//            lv.setAdapter(artistsAdapter);
//            final ArrayList<SongsWithoutCoverArt> finalSongs = songsWithoutCoverArt;
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent i=new Intent();
//                    i.setClass(getContext(),NowPlaying.class);
//                    i.putExtra("pos",position);
//                    i.putExtra("List", finalSongs);
//                    startActivity(i);
//                }
//            });
            return rootView;
        }
    }

    public static ArrayList<Songs> getSongs(File root) {
        ArrayList<Songs> songs=new ArrayList<>();
        Songs songDataClassObj;
        File[] files=root.getAbsoluteFile().listFiles();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        for( File singleFile:files){
            if(singleFile.isDirectory()&& !singleFile.isHidden()){
                songs.addAll(getSongs(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3")){
                    if(singleFile.getAbsoluteFile()!=null) {
                        mmr.setDataSource(singleFile.getAbsolutePath());

                        if (mmr.METADATA_KEY_DURATION <= 50000) {
                            songDataClassObj = new Songs(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION),
                                    singleFile.getPath(), mmr.getEmbeddedPicture());
                            songs.add(songDataClassObj);
                            Log.i("INFO",singleFile.getAbsolutePath());
                        }
                    }
                }
            }

        }
        return songs;
    }

    public static ArrayList<SongsWithoutCoverArt> getSongsWithoutCoverArt(File root) {
        ArrayList<SongsWithoutCoverArt> songs=new ArrayList<>();
        SongsWithoutCoverArt songDataClassObj;
        File[] files=root.getAbsoluteFile().listFiles();
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        for( File singleFile:files){
            if(singleFile.isDirectory()&& !singleFile.isHidden()){
                songs.addAll(getSongsWithoutCoverArt(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3")){
                    if(singleFile.getAbsoluteFile()!=null) {
                        mmr.setDataSource(singleFile.getAbsolutePath());

                        if (mmr.METADATA_KEY_DURATION <= 50000) {
                            songDataClassObj = new SongsWithoutCoverArt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                                    mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION),
                                    singleFile.getPath());
                            songs.add(songDataClassObj);
                            Log.i("INFO",singleFile.getAbsolutePath());
                        }
                    }
                }
            }

        }
        return songs;
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return PlaceholderFragment.newInstance(position+1);
            }else if(position==1){
                return PlaceholderFragment1.newInstance(position+1);
            }else if(position==2){
                return PlaceholderFragment2.newInstance(position+1);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SONGS";
                case 1:
                    return "ALBUMS";
                case 2:
                    return "ARTISTS";
            }
            return null;
        }
    }




}
