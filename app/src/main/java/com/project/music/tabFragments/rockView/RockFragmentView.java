package com.project.music.tabFragments;

import com.project.music.itunesModel.MusicModel;

import java.util.List;

public interface RockFragmentView {
    void showRecyclerView(List<MusicModel> musicModelList);
}
