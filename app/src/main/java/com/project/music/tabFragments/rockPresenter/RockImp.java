package com.project.music.tabFragments.rockPresenter;


import com.project.music.tabFragments.musicModel.MusicModelResult;
import com.project.music.common.SetupRetrofit;
import com.project.music.interfaceApi.MusicApiInterface;
import com.project.music.tabFragments.musicModel.MusicModel;
import com.project.music.tabFragments.rockView.RockFragmentView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RockFragmentImp implements RockFragmentPresenter {
    private List<MusicModel> musicModelList;
    private RockFragmentView rockFragmentView;

    public RockFragmentImp(RockFragmentView rockFragmentView) {
        this.rockFragmentView = rockFragmentView;
    }

    @Override
    public void loadRockMusic() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        //starting interface using retrofit object
        MusicApiInterface musicApiInterface = retrofit.create(MusicApiInterface.class);
        musicApiInterface.getRockList().enqueue(new Callback<MusicModelResult<MusicModel>>() {
            @Override
            public void onResponse(Call<MusicModelResult<MusicModel>> call,
                                   Response<MusicModelResult<MusicModel>> response) {

                //   Log.d("rockResponse", String.valueOf(response.code()));

                MusicModelResult musicModelResult = response.body();
                musicModelList = musicModelResult.getData();
                rockFragmentView.showRecyclerView(musicModelList);

            }

            @Override
            public void onFailure(Call<MusicModelResult<MusicModel>> call, Throwable t) {

            }
        });
    }
}
