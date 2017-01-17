package com.skeleton.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.plugin.ImageChooser;
import com.skeleton.retrofit2.APIError;
import com.skeleton.retrofit2.MultipartParams;
import com.skeleton.retrofit2.ResponseResolver;
import com.skeleton.retrofit2.RestClient;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * Created by oem on 16/1/17.
 */

public class ImagePickerFragment extends SuperFragment implements ImageChooser.OnImageSelectListener {

    private ImageChooser mImageChooser;

    //Butter knife implementation
    @BindView(R.id.butter_knife_text)
    TextView butterKnifeText;

    @BindView(R.id.chosen_image)
    ImageView chosenImage;

    private boolean toggle  ;


    private View iPView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        iPView = inflater.inflate(R.layout.image_picker_frag , container , false);

        mImageChooser = new ImageChooser(this);
        ButterKnife.bind(this , iPView);





        return iPView;
    }
    @OnClick(R.id.butter_knife_text)
    public void onTextViewClick(){
            mImageChooser.selectImage();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Picker.PICK_IMAGE_DEVICE || requestCode == Picker.PICK_IMAGE_CAMERA){
            mImageChooser.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ImageChooser.PERMISSION_REQUEST_CODE_CAMERA_READ_WRITE)
            mImageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void loadImage(List<ChosenImage> list) {



        for(ChosenImage cImage : list) {
            File toUpload = new File(cImage.getOriginalPath());

            Uri originalFilePathUri = Uri.fromFile(toUpload);

            //if we want to send this image to server
        Map<String , RequestBody> wrappedFile = new MultipartParams.Builder()
                .addFile("temp_key" , toUpload)
                .build().getMap();


            RestClient.getApiInterface().uploadImage(wrappedFile)
                    .enqueue(new ResponseResolver<Void>(getActivity()) {
                        @Override
                        public void success(Void aVoid) {

                        }

                        @Override
                        public void failure(APIError error) {

                        }
                    });

            Glide.with(this)
                    .load(originalFilePathUri)
                    .crossFade()
                    .fitCenter()
                    .into(chosenImage);

        }
        //chosenImage.setImageURI(originalFilePathUri);
    }
}
