package com.skeleton.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skeleton.BuildConfig;
import com.skeleton.R;
import com.skeleton.activity.MyTestCase;
import com.skeleton.adapters.MyRecyclerAdapter;
import com.skeleton.constant.AppConstant;
import com.skeleton.database.CommonData;
import com.skeleton.model.Movie;
import com.skeleton.model.MovieResponse;
import com.skeleton.plugin.Foreground;
import com.skeleton.plugin.Log;
import com.skeleton.retrofit2.APIError;
import com.skeleton.retrofit2.ResponseResolver;
import com.skeleton.retrofit2.RestClient;
import com.skeleton.util.EndlessRecyclerViewScrollListener;
import com.skeleton.util.PermissionsHelper;

import java.util.ArrayList;

/**
 * Created by kashish nalwa on 12/15/16.
 */
public class BaseFragment extends SuperFragment implements PermissionsHelper.OnPermissionResult , MyRecyclerAdapter.MovieListOnClickListener, Foreground.Listener
    , MyTestCase.FilterDataInFragment
{


    private boolean updateAccordingToFilter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager  mLayoutManager;
    private RecyclerView moviesList;
    private MyRecyclerAdapter myRecyclerAdapter;
    private int firstPage = 1;
    private BaseFragment testCaseActivity = this;
    public static final String SORT_BY = "popularity.desc";
    private Context context;
    private View mFragmentView;
    private static final String TAG = "BaseFragment";

    public BaseFragment() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentView =  inflater.inflate(R.layout.base_fragment_layout, container, false);
        initialize(mFragmentView);
        return mFragmentView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sortByPopularity){
            updateAccordingToFilter();
            //android.util.Log.d(TAG, "onOptionsItemSelected: "+"Sort By Popularity");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialize(View v){
        Foreground.get(getActivity()).addListener(this );
        moviesList = (RecyclerView) v.findViewById(R.id.movies_list);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        createEndlessListener();
        moviesList.setLayoutManager(mLayoutManager);
        moviesList.addOnScrollListener(scrollListener);
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity().getApplicationContext()
                ,  new ArrayList<Movie>(), this );
        moviesList.setAdapter(myRecyclerAdapter);

        usePaperDB();

        requestPermission();

        RestClient.getApiInterface().getTopRatedMovies(BuildConfig.API_KEY , firstPage , null).
                enqueue(new ResponseResolver<MovieResponse>(getActivity()) {
                    @Override
                    public void success(MovieResponse movieResponse) {
                        /*Log.d("myTAG" , movieResponse.getResults().toString());*/
                        myRecyclerAdapter.add(movieResponse.getResults());


                    }

                    @Override
                    public void failure(APIError error) {
                        Log.d(TAG , error.getMessage() + " and status code : " + error.getStatusCode());
                    }
                });

    }

    public void requestPermission(){
        //

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsHelper.permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .checkPermissions(getActivity(), this);

        }else{
            Toast.makeText(getActivity().getApplicationContext() , R.string.granted , Toast.LENGTH_LONG).show();
        }

    }


    public void usePaperDB(){

        CommonData.putDeviceName(Build.MODEL);
        Log.d(TAG ,CommonData.getDeviceToken() );
        Log.d(TAG , CommonData.getDeviceName());


    }



    private void createEndlessListener(){
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(getActivity().getApplicationContext() , "You are at the end !!!! " + page , Toast.LENGTH_LONG).show();
                RestClient.getApiInterface().getTopRatedMovies(BuildConfig.API_KEY , ++page , null).
                        enqueue(new ResponseResolver<MovieResponse>(getActivity()) {
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


    @Override
    public void onMovieItemClick(String movieTitle, String releaseDate) {
        Log.d(TAG , movieTitle   + " and  "  + releaseDate);
        Toast.makeText(getActivity() , movieTitle +  "  Released on :  "+ releaseDate  , Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onBecameForeground() {
        Log.d(TAG , "Became Foreground");
    }

    @Override
    public void onBecameBackground() {
        Log.d(TAG , "Became Background");
    }

    @Override
    public void onGranted() {
        Log.d(TAG , "Granted");
    }

    @Override
    public void notGranted() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //((MyTestCase)getActivity()).registerFilterUpdateListener(this);

    }

    @Override
    public void updateAccordingToFilter() {
        Toast.makeText(getActivity() , "HI" , Toast.LENGTH_LONG).show();

        if(!updateAccordingToFilter){

        RestClient.getApiInterface().getTopRatedMovies(BuildConfig.API_KEY, firstPage, SORT_BY).
                enqueue(new ResponseResolver<MovieResponse>(getActivity()) {
                    @Override
                    public void success(MovieResponse movieResponse) {
                        /*Log.d("myTAG", movieResponse.getResults().toString());*/
                        myRecyclerAdapter.removeAll();
                        myRecyclerAdapter.add(movieResponse.getResults());


                    }

                    @Override
                    public void failure(APIError error) {
                        Log.d(TAG, error.getMessage() + " and status code : " + error.getStatusCode());
                    }
                });
    }
    updateAccordingToFilter  =true;
    }
}
