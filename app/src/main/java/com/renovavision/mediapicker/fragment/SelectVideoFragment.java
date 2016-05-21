package com.renovavision.mediapicker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.renovavision.mediapicker.R;
import com.renovavision.mediapicker.video.VideoPicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectVideoFragment extends Fragment {

    @Bind(R.id.video_view)
    VideoView videoView;

    VideoPicker videoPicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_video, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoPicker = new VideoPicker();
    }

    @OnClick(R.id.select_button)
    public void onClick(View view){
        videoPicker.selectImage(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        videoPicker.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String videoPath = videoPicker.onActivityResult(getActivity(),requestCode,resultCode,data);
        videoView.setVideoPath(videoPath);
        videoView.start();
    }
}