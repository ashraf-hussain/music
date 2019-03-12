package com.project.music.musicDetail;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.music.R;
import com.project.music.common.AppConstant;
import com.project.music.tabFragments.model.MusicModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicPlayerDetailActivity extends AppCompatActivity {
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_artist)
    ImageView ivArtist;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_band_name_music_detail)
    TextView tvBandNameMusicDetail;
    @BindView(R.id.tv_music_title_detail)
    TextView tvMusicTitleDetail;
    @BindView(R.id.seekBar_music)
    SeekBar seekBarMusic;
    @BindView(R.id.main_ll_music_detail)
    LinearLayout mainLlMusicDetail;
    // @BindView(R.id.tv_track_time)
    //TextView tvTrackTime;
    private MediaPlayer mediaPlayer;
    private MusicModel musicModel;
    Handler handler;
    Runnable runnable;
    long milli;
    String hms;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail_land);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        this.getSupportActionBar().hide();
        mediaPlayer = new MediaPlayer();
        handler = new Handler();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Do some stuff
            Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
        }
        //getting data via intent
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            musicModel = (MusicModel) intent.getSerializableExtra(AppConstant.MUSIC_DETAIL_DATA);
        }

        milli = musicModel.getTrackTimeMillis();
//        hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milli),
//                TimeUnit.MILLISECONDS.toMinutes(milli) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milli)),
//                TimeUnit.MILLISECONDS.toSeconds(milli) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milli)));

        tvBandNameMusicDetail.setText(musicModel.getArtistName());
        tvMusicTitleDetail.setText(musicModel.getTrackName());
        //tvTrackTime.setText(hms + "");

        Picasso.get().load(musicModel.getArtworkUrl60()).into(ivArtist);
        Log.d("image", musicModel.getPreviewUrl());

        playMusic();
    }

    private void playMusic() {
        try {
            mediaPlayer.setDataSource(musicModel.getPreviewUrl());

            // Prepare the media player
            mediaPlayer.prepare();

            // Start playing audio from http url
            mediaPlayer.start();

            // Inform user for audio streaming
            Snackbar snackbar = (Snackbar) Snackbar
                    .make(mainLlMusicDetail, "Playing", Snackbar.LENGTH_SHORT);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            snackbar.show();

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBarMusic.setMax(mediaPlayer.getDuration());
                    playCycle();
                    mediaPlayer.start();
                }
            });


            seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ivPlay.setBackground(ContextCompat.getDrawable(MusicPlayerDetailActivity.this,
                        R.drawable.ic_play_circle_outline_black_24dp));

                playCycle();

            }
        });
        playCycle();


    }

    @OnClick({R.id.toolbar, R.id.iv_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                mediaPlayer.stop();
                onBackPressed();
                break;
            case R.id.iv_play:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playCycle();
                    ivPlay.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.ic_play_circle_outline_black_24dp));

                } else {
                    mediaPlayer.start();
                    playCycle();
                    ivPlay.setBackground(ContextCompat.getDrawable(this,
                            R.drawable.ic_pause_circle_outline_black_24dp));
                }

                break;
        }
    }


    void playCycle() {
        seekBarMusic.setProgress(mediaPlayer.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {

                    playCycle();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //setContentView(R.layout.activity_music_detail_land);
        //Toast.makeText(this, "change", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        ivPlay.setBackground(ContextCompat.getDrawable(this,
                R.drawable.ic_play_circle_outline_black_24dp));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivPlay.setBackground(ContextCompat.getDrawable(this,
                R.drawable.ic_pause_circle_outline_black_24dp));
        mediaPlayer.start();
        playCycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }
}

