package com.renovavision.mediapicker.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.renovavision.mediapicker.R;
import com.renovavision.mediapicker.image.ImagePicker;
import com.renovavision.mediapicker.utils.BitmapUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectImageFragment extends Fragment {

    @Bind(R.id.image_view)
    ImageView imageView;

    ImagePicker imagePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_image, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagePicker = new ImagePicker();
    }

    @OnClick(R.id.select_button)
    public void onClick(View view){
        imagePicker.selectImage(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imagePicker.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String picturePath = imagePicker.onActivityResult(getActivity(),requestCode,resultCode,data);
        imageView.setImageBitmap(BitmapUtils.getBitmap(picturePath, imageView.getWidth(), imageView.getHeight()));
    }
}
