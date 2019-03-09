package com.project.music.interfaceApi;


import com.project.music.tabFragments.musicModel.MusicModelResult;
import com.project.music.tabFragments.musicModel.MusicModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicApiInterface {
  // "https://itunes.apple.com/search?term=rock&amp;media=music&amp;entity=song&amp;limit=50"

    @GET("/search?term=rock")
    Call <MusicModelResult<MusicModel>> getRockList();

    @GET("/search?term=classic")
    Call<MusicModelResult<MusicModel>> getClassicList();

    @GET("/search?term=pop")
    Call<MusicModelResult<MusicModel>> getPopList();
}
