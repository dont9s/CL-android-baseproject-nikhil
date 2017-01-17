package com.skeleton.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.skeleton.BuildConfig;
import com.skeleton.R;
import com.skeleton.adapters.MyRecyclerAdapter;

import com.skeleton.adapters.ViewPagerAdapter;
import com.skeleton.database.CommonData;
import com.skeleton.fragment.BaseFragment;
import com.skeleton.fragment.ImagePickerFragment;
import com.skeleton.fragment.SuperFragment;
import com.skeleton.model.Movie;
import com.skeleton.model.MovieResponse;
import com.skeleton.plugin.Foreground;
import com.skeleton.plugin.Log;
import com.skeleton.retrofit2.APIError;
import com.skeleton.retrofit2.ApiInterface;
import com.skeleton.retrofit2.ResponseResolver;
import com.skeleton.retrofit2.RestClient;
import com.skeleton.util.EndlessRecyclerViewScrollListener;
import com.skeleton.util.PermissionsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTestCase extends BaseActivity /*implements PermissionsHelper.OnPermissionResult , MyRecyclerAdapter.MovieListOnClickListener, Foreground.Listener */{


    //New Implementation

    private Toolbar mtToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //listener for interacting b/w activity and BaseFragment
    private FilterDataInFragment filterListener;
    //New Implementation


   /* private static final String TAG = "MyTestCase";
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager  mLayoutManager;
    private RecyclerView moviesList;
    private MyRecyclerAdapter myRecyclerAdapter;
    private int firstPage = 1;
    private MyTestCase testCaseActivity = this;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test_case);
        //New Implementation
        mtToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mtToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.my_viewpager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //New Implementation

        //Foreground.get(this).addListener(this );



       /* moviesList = (RecyclerView) findViewById(R.id.movies_list);
        mLayoutManager = new LinearLayoutManager(this);
        createEndlessListener();
        moviesList.setLayoutManager(mLayoutManager);
        moviesList.addOnScrollListener(scrollListener);
        myRecyclerAdapter = new MyRecyclerAdapter(this ,  new ArrayList<Movie>(), this );
        moviesList.setAdapter(myRecyclerAdapter);

        usePaperDB();

        requestPermission();





        //request and handle code
        RestClient.getApiInterface().getTopRatedMovies(BuildConfig.API_KEY , firstPage).
                enqueue(new ResponseResolver<MovieResponse>(this) {
            @Override
            public void success(MovieResponse movieResponse) {
                Log.d("myTAG" , movieResponse.getResults().toString());
                myRecyclerAdapter.add(movieResponse.getResults());


            }

            @Override
            public void failure(APIError error) {
                Log.d(TAG , error.getMessage() + " and status code : " + error.getStatusCode());
            }
        });*/
    }

    public interface FilterDataInFragment{
        void updateAccordingToFilter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* switch (item.getItemId()){
            case R.id.sortByPopularity:

                filterListener.updateAccordingToFilter();
                return true;



        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(MyRecyclerAdapter.isCheckBoxVisible){
            MyRecyclerAdapter.updateVisibilityOfCheckBoxes(false);
        }
        else {
            super.onBackPressed();
        }
    }

    public synchronized void registerFilterUpdateListener(FilterDataInFragment listener){
        filterListener = listener;
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {

      *//*  getMenuInflater().inflate(R.menu.menu, menu);*//*
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager (ViewPager mViewPager){

        /*ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());*/
        /*mViewPagerAdapter.*/


        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new BaseFragment() , getResources().getString(R.string.top_rated_movies));
        mAdapter.addFragment(new ImagePickerFragment() , getResources().getString(R.string.choose_image));
        mViewPager.setAdapter(mAdapter);


    }




  /*  private void createEndlessListener(){
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(getApplicationContext() , "You are at the end !!!! " + page , Toast.LENGTH_LONG).show();
                RestClient.getApiInterface().getTopRatedMovies(BuildConfig.API_KEY , ++page).
                        enqueue(new ResponseResolver<MovieResponse>(testCaseActivity) {
                            @Override
                            public void success(MovieResponse movieResponse) {
                                Log.d("myTAG" , movieResponse.getResults().toString());
                                myRecyclerAdapter.add(movieResponse.getResults());


                            }

                            @Override
                            public void failure(APIError error) {
                                Log.d(TAG , error.getMessage() + " and status code : " + error.getStatusCode());
                            }
                        });

            }
        };
    }
*/

   /* public void requestPermission(){
        //

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsHelper.permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .checkPermissions(this, this);

        }else{
            Toast.makeText(getApplicationContext() , R.string.granted , Toast.LENGTH_LONG).show();
        }

    }*/

  /*  public void usePaperDB(){

        CommonData.putDeviceName(Build.MODEL);
        Log.d(TAG ,CommonData.getDeviceToken() );
        Log.d(TAG , CommonData.getDeviceName());


    }*/

    /*@Override
    public void onGranted() {
        Log.d(TAG , "Granted");
    }*/

   /* @Override
    public void notGranted() {

    }*/

    /*@Override
    public void onMovieItemClick(String movieTitle, String releaseDate) {

        Log.d(TAG , movieTitle   + " and  "  + releaseDate);
        Toast.makeText(this , movieTitle +  "  Released on :  "+ releaseDate  , Toast.LENGTH_LONG ).show();
    }*/

    /*@Override
    public void onBecameForeground() {
        Log.d(TAG , "Became Foreground");
    }
*/
   /* @Override
    public void onBecameBackground() {
        Log.d(TAG , "Became Background");
    }*/
}
