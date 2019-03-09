package com.project.music.tabFragments.classicView;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.project.music.R;
import com.project.music.adapter.MusicAdapter;
import com.project.music.common.BaseFragment;
import com.project.music.tabFragments.classicPresenter.ClassicImp;
import com.project.music.tabFragments.classicPresenter.ClassicPresenter;
import com.project.music.tabFragments.musicModel.MusicModel;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassicFragment extends BaseFragment implements ClassicView {


    @BindView(R.id.rv_classic_music)
    RecyclerView rvClassicMusic;
    MusicAdapter musicAdapter;


    @Override
    public void showClassicMusicList(List<MusicModel> musicModelList) {
        musicAdapter = new MusicAdapter(musicModelList);
        rvClassicMusic.setAdapter(musicAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_classic;
    }

    @Override
    protected void init() {
        rvClassicMusic.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvClassicMusic.setLayoutManager(layoutManager);

        ClassicPresenter classicPresenter = new ClassicImp(this);
        classicPresenter.loadClassicData();

    }


}
