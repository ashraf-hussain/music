package com.project.music;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.music.common.BaseActivity;
import com.project.music.common.ConnectionDetector;
import com.project.music.dashboard.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Ashraf.
 */


public class SplashScreenActivity extends BaseActivity {

    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_layout)
    LinearLayout relativeLayout;
    @BindView(R.id.iv_main)
    ImageView ivMain;


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;


    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        connectionDetector = new ConnectionDetector(this);
        startAnimation();
        this.getSupportActionBar().hide();

    }

    private void checkpoint() {

        if (!connectionDetector.isConnected()) {
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "No Internet Connection !", Snackbar.LENGTH_INDEFINITE);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            snackbar.show();

        } else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }


    private void startAnimation() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        relativeLayout.clearAnimation();
        relativeLayout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ivMain.clearAnimation();
        ivMain.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 5000;
                    // Splash screen pause time
                    while (waited < 7000) {
                        sleep(5000);
                        waited += 3000;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    // SplashScreenActivity.this.finish();
                }

            }
        };
        splashTread.start();
        checkpoint();


    }

    @OnClick({R.id.button_retry, R.id.button_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_retry:

                checkpoint();
                break;
            case R.id.button_cancel:
                finish();
                break;
        }
    }

}




