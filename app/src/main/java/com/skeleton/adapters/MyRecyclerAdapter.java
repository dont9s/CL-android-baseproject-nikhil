package com.skeleton.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.skeleton.R;
import com.skeleton.model.Movie;
import com.skeleton.plugin.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oem on 12/1/17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<Movie> dataSet;
    MyCustomClickListener mClickListener;

    public static boolean isSelectedEnabled;

    public static  boolean isCheckBoxVisible = true ;

    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;

    private MovieListOnClickListener clickListener;
    public MyRecyclerAdapter(Context context , ArrayList<Movie> dataSet , MovieListOnClickListener clickListener) {
        this.context = context;
        this.dataSet =  dataSet;
        this.clickListener = clickListener;
        mClickListener = new MyCustomClickListener();

    }

    @Override
    public void onClick(View v) {
        String movieName = ((TextView)v.findViewById(R.id.title)).getText().toString();
        String releasedOn = ((TextView)v.findViewById(R.id.release_date)).getText().toString();
        switch ((int)v.getTag()){
            case TYPE_ONE:
                clickListener.onMovieItemClick(movieName , releasedOn);
                break;
            case TYPE_TWO:
                clickListener.onMovieItemClick(movieName, releasedOn);
                break;
        }
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        MyCustomClickListener myCustomClickListener = new MyCustomClickListener();
        MyCustomLongClickListener myCustomLongClickListener = new MyCustomLongClickListener();
        MyCheckChangedListener myCheckChangedListener = new MyCheckChangedListener();

        public MyViewHolder(View itemView ) {
            super(itemView);


        }

    }

    public class TypeOneViewHolder extends  MyViewHolder{


        TextView title , release_date;
        @BindView(R.id.moive_image_one)
        ImageView movieImage;

        @BindView(R.id.addToFavCheck_one)
        CheckBox addToFavCheck;

        public TypeOneViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this , itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            release_date = (TextView) itemView.findViewById(R.id.release_date);

            itemView.setOnClickListener(myCustomClickListener);

            itemView.setOnLongClickListener(myCustomLongClickListener);
        }

    }


    private static final String TAG = "MyRecyclerAdapter";

    public class MyCustomLongClickListener implements View.OnLongClickListener{

        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public boolean onLongClick(View v) {

            Toast.makeText(context , "LONG CLICK" , Toast.LENGTH_LONG).show();
            if(isCheckBoxVisible) {
                updateVisibilityOfCheckBoxes(false);
                if(isSelectedEnabled){

                }


            }else{
                updateVisibilityOfCheckBoxes(true);
                Movie toCheckAfterLongClick = dataSet.get(position);
                toCheckAfterLongClick.setSelected(true);

                isSelectedEnabled = true;



            }
            notifyDataSetChanged();
            return false;
        }
    }

    public class MyCustomClickListener implements View.OnClickListener{

        int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch ((int)v.getTag()){
                case TYPE_ONE:
                    clickListener.onMovieItemClick(dataSet.get(position).getTitle()
                            , dataSet.get(position).getReleaseDate());
                    break;
                case TYPE_TWO:
                    clickListener.onMovieItemClick(dataSet.get(position).getTitle()
                            , dataSet.get(position).getReleaseDate());
                    break;
            }
            //Toast.makeText(context ,dataSet.get(position).getTitle() , Toast.LENGTH_LONG).show();
            //android.util.Log.d(TAG, "onClick: "+position);
        }
    }

    public class TypeTwoViewHolder extends MyViewHolder{

        TextView title , release_date;

        @BindView(R.id.addToFavCheck_two)
        CheckBox addToFavCheck;


        @BindView(R.id.movie_image_two)
        ImageView movieImage;

        public TypeTwoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this , itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            release_date = (TextView) itemView.findViewById(R.id.release_date);



            itemView.setOnClickListener(myCustomClickListener);

            itemView.setOnLongClickListener(myCustomLongClickListener);
        }



    }


    public void add(List<Movie> listToAddInDataSet){
        if(listToAddInDataSet !=null){
            dataSet.addAll(listToAddInDataSet);
            notifyDataSetChanged();
        }
    }
    public void removeAll(){
        if(dataSet != null){
            dataSet.clear();
            notifyDataSetChanged();
        }
    }
    public interface MovieListOnClickListener{
        public void onMovieItemClick(String movieTitle , String releaseDate);

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myView;
        switch (viewType){
            case TYPE_ONE:
                 myView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.type_one_card , parent , false);
                myView.setTag(TYPE_ONE);
                //myView.setOnClickListener(mClickListener);
                return new TypeOneViewHolder(myView);


            case TYPE_TWO:
                myView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.type_two_card, parent , false);
                myView.setTag(TYPE_TWO);
                //myView.setOnClickListener(this);
                return new TypeTwoViewHolder(myView);

        }




        return null;
    }

    public class MyCheckChangedListener implements CompoundButton.OnCheckedChangeListener{

        int position;
        Movie movie;
        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            movie = dataSet.get(position);
            movie.setSelected(isChecked);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Movie movie = dataSet.get(position);
        holder.myCustomClickListener.setPosition(position);
        holder.myCustomLongClickListener.setPosition(position);
        holder.myCheckChangedListener.setPosition(position);
        switch (holder.getItemViewType()){

            case TYPE_ONE:
                TypeOneViewHolder typeOneViewHolder = (TypeOneViewHolder) holder;

                typeOneViewHolder.title.setText(dataSet.get(position).getTitle());
                typeOneViewHolder.release_date.setText(dataSet.get(position).getReleaseDate());

                typeOneViewHolder.addToFavCheck.setOnCheckedChangeListener(null);

                typeOneViewHolder.addToFavCheck.setChecked(movie.isSelected());


                typeOneViewHolder.addToFavCheck.setOnCheckedChangeListener(holder.myCheckChangedListener);


                if(isCheckBoxVisible) {
                    typeOneViewHolder.addToFavCheck.setVisibility(View.GONE);
                    updateVisibilityOfCheckBoxes(false);
                }else{
                    typeOneViewHolder.addToFavCheck.setVisibility(View.VISIBLE);
                    updateVisibilityOfCheckBoxes(true);
                }
                Glide
                        .with(context)
                        .load("http://simpleicon.com/wp-content/uploads/movie-1.png")
                        .into((ImageView) typeOneViewHolder.movieImage);

                /*typeOneViewHolder.movieImage*/
                break;
            case TYPE_TWO:
                TypeTwoViewHolder typeTwoViewHolder = (TypeTwoViewHolder) holder;
                typeTwoViewHolder.title.setText(dataSet.get(position).getTitle());
                typeTwoViewHolder.release_date.setText(dataSet.get(position).getReleaseDate());

                typeTwoViewHolder.addToFavCheck.setOnCheckedChangeListener(null);

                typeTwoViewHolder.addToFavCheck.setChecked(movie.isSelected());


                typeTwoViewHolder.addToFavCheck.setOnCheckedChangeListener(holder.myCheckChangedListener);
                if(isCheckBoxVisible){
                    typeTwoViewHolder.addToFavCheck.setVisibility(View.GONE);
                }else {
                    typeTwoViewHolder.addToFavCheck.setVisibility(View.VISIBLE);
                }

                Glide
                        .with(context)
                        .load("http://simpleicon.com/wp-content/uploads/movie-1.png")
                        .into((ImageView) typeTwoViewHolder.movieImage);

                break;
        }
    }

    public static void updateVisibilityOfCheckBoxes(boolean newValue){
        isCheckBoxVisible = newValue;

    }

    @Override
    public int getItemViewType(int position) {
        if(position%2 == 0){
            return TYPE_ONE;
        }else {
            return TYPE_TWO;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
