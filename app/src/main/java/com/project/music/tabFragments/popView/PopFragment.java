package com.project.music.tabFragments.popView;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.music.R;
import com.project.music.adapter.MusicAdapter;
import com.project.music.common.BaseFragment;
import com.project.music.tabFragments.musicModel.MusicModel;
import com.project.music.tabFragments.popPresenter.PopImp;
import com.project.music.tabFragments.popPresenter.PopPresenter;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopFragment extends BaseFragment implements PopView{

    @BindView(R.id.rv_pop_music)
    RecyclerView rvPopMusic;
    MusicAdapter musicAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_pop;
    }

    @Override
    protected void init() {
        rvPopMusic.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPopMusic.setLayoutManager(layoutManager);


        PopPresenter popPresenter = new PopImp(this);
        popPresenter.loadPopMusic();
    }

    @Override
    public void showPopMusicList(List<MusicModel> musicModelList) {
        musicAdapter = new MusicAdapter(musicModelList);
        rvPopMusic.setAdapter(musicAdapter);
    }
}
