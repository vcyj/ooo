package com.example.myapplication;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.freedom.lauzy.playpauseviewlib.PlayPauseView;
import com.xwh.lib.corelib.WHUtil;


public class HomeFragment extends Fragment {

    static boolean isplaying = true;

    private PlayPauseView playPauseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }
    MediaPlayer mediaPlayerxs;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);









        Button button=getView().findViewById(R.id.button);
        PlayandPause playandPause=getView().findViewById(R.id.buttonpap);



          if(mediaPlayerxs==null) mediaPlayerxs=MediaPlayer.create(getActivity(),R.raw.xswly);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isplaying){
                    mediaPlayerxs.start();
                }else {
                    mediaPlayerxs.pause();

                }
                isplaying=!isplaying;
            }
        });
//        View playPauseView=getView().findViewById(R.id.playPauseView);
        playPauseView=getView().findViewById(R.id.playPauseView);
        playPauseView.setPlayPauseListener(new PlayPauseView.PlayPauseListener() {
            @Override
            public void play() {
                // do something

                mediaPlayerxs.start();
            }

            @Override
            public void pause() {
                // do something
                mediaPlayerxs.pause();
            }
        });

        playandPause.setOnStatusChangeListener(new PlayandPause.OnStatusChangeListener() {
            @Override
            public void play() {
                mediaPlayerxs.start();
            }
            @Override
            public void pause() {
                mediaPlayerxs.pause();
            }
        });
    }
}