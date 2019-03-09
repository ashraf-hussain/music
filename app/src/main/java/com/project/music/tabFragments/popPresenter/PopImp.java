package com.project.music.tabFragments.popPresenter;


import android.util.Log;

import com.project.music.common.SetupRetrofit;
import com.project.music.interfaceApi.MusicApiInterface;
import com.project.music.tabFragments.musicModel.MusicModel;
import com.project.music.tabFragments.musicModel.MusicModelResult;
import com.project.music.tabFragments.popView.PopView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopImp implements PopPresenter {

    private List<MusicModel> musicModelList;
    private PopView popView;

    public PopImp(PopView popView) {
        this.popView = popView;
    }


    @Override
    public void loadPopMusic() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        //starting interface using retrofit object
        MusicApiInterface musicApiInterface = retrofit.create(MusicApiInterface.class);
        musicApiInterface.getPopList().enqueue(new Callback<MusicModelResult<MusicModel>>() {
            @Override
            public void onResponse(Call<MusicModelResult<MusicModel>> call,
                                   Response<MusicModelResult<MusicModel>> response) {

                MusicModelResult musicModelResult = response.body();
                musicModelList = musicModelResult.getData();
                popView.showPopMusicList(musicModelList);

            }

            @Override
            public void onFailure(Call<MusicModelResult<MusicModel>> call, Throwable t) {

            }
        });
    }

}
