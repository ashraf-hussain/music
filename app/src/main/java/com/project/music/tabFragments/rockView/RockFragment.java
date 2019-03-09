package com.project.music.tabFragments.rockView;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.music.common.BaseFragment;

import com.project.music.adapter.MusicAdapter;
import com.project.music.R;
import com.project.music.tabFragments.musicModel.MusicModel;
import com.project.music.tabFragments.rockPresenter.RockImp;
import com.project.music.tabFragments.rockPresenter.RockPresenter;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RockFragment extends BaseFragment implements RockFragmentView {

    MusicAdapter musicAdapter;
    @BindView(R.id.rv_rock_fragment)
    RecyclerView rvRockFragment;

    @Override
    public void showRecyclerView(List<MusicModel> musicModelList) {

        musicAdapter = new MusicAdapter(musicModelList);
        rvRockFragment.setAdapter(musicAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_rock;
    }

    @Override
    protected void init() {
        rvRockFragment.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvRockFragment.setLayoutManager(layoutManager);

        RockPresenter rockPresenter = new RockImp(this);
        rockPresenter.loadRockMusic();

    }


}