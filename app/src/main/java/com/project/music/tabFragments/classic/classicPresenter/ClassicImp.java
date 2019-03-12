package com.project.music.tabFragments.classic.classicPresenter;


import android.util.Log;

import com.project.music.common.SetupRetrofit;
import com.project.music.tabFragments.model.interfaceApi.MusicApiInterface;
import com.project.music.tabFragments.classic.classicView.ClassicView;
import com.project.music.tabFragments.model.MusicModel;
import com.project.music.tabFragments.model.MusicModelResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClassicImp implements ClassicPresenter {

    private List<MusicModel> musicModelList;
    private ClassicView classicView;

    public ClassicImp(ClassicView classicView) {
        this.classicView = classicView;
    }


    @Override
    public void loadClassicData() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        //starting interface using retrofit object
        MusicApiInterface musicApiInterface = retrofit.create(MusicApiInterface.class);
        musicApiInterface.getClassicList().enqueue(new Callback<MusicModelResult<MusicModel>>() {
            @Override
            public void onResponse(Call<MusicModelResult<MusicModel>> call,
                                   Response<MusicModelResult<MusicModel>> response) {

                  Log.d("classicResponse", String.valueOf(response.code()));

                MusicModelResult musicModelResult = response.body();
                musicModelList = musicModelResult.getData();
                classicView.showClassicMusicList(musicModelList);

            }

            @Override
            public void onFailure(Call<MusicModelResult<MusicModel>> call, Throwable t) {

            }
        });
    }

}
