package com.example.ashukaushik.fun;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static ArrayList<Songs> songs=new ArrayList<>();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    static SongListAdapter adapter;

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

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
            ListView lv=(ListView)rootView.findViewById(R.id.listView) ;
            adapter=new SongListAdapter(getActivity(),songs);
            lv.setAdapter(adapter);
            final ArrayList<Songs> finalSongs = songs;
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i=new Intent();
                    i.setClass(getContext(),NowPlaying.class);
                    i.putExtra("pos",position);
                    i.putExtra("List", finalSongs);
                    startActivity(i);
                }
            });

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
                            songDataClassObj = new Songs(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE), mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST), mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM), mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION), singleFile.getPath(), mmr.getEmbeddedPicture());
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
