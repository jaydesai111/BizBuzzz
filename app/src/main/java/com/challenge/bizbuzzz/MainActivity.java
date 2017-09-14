package com.challenge.bizbuzzz;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.challenge.bizbuzzz.DialogFragment.SavePhotoDialogFragment;
import com.challenge.bizbuzzz.Fragment.SavePhotoListFragment;
import com.challenge.bizbuzzz.Utility.NavigationDrawerUtility;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String[] mPlanetTitles = {"Save photo", "show Photos"};
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private String TAG = "MAINACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setUpToolBar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout,toolbar,
                R.string.drawer_open, R.string.drawer_close);


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        SavePhotoListFragment savePhotoListFragment = new SavePhotoListFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, savePhotoListFragment, "SavePhotoListFragment");
        ft.commit();


    }

    public void setUpToolBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        mDrawerLayout.closeDrawer(mDrawerList);
        Log.i(TAG,"this is selected Item "+position);
        switch (position)
        {
            case NavigationDrawerUtility.SAVEPHOTO:
                SavePhotoDialogFragment savePhotoDialogFragment = new SavePhotoDialogFragment();
                savePhotoDialogFragment.show(MainActivity.this.getSupportFragmentManager(),"SavePhotoDialogFragment");

        }
    }


}
