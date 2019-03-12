package com.project.music.tabFragments.pop.popView;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.project.music.R;
import com.project.music.tabFragments.view.MusicAdapter;
import com.project.music.common.BaseFragment;
import com.project.music.common.ConnectionDetector;
import com.project.music.tabFragments.model.MusicModel;
import com.project.music.tabFragments.pop.popPresenter.PopImp;
import com.project.music.tabFragments.pop.popPresenter.PopPresenter;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PopFragment extends BaseFragment implements PopView {

    @BindView(R.id.rv_pop_music)
    RecyclerView rvPopMusic;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.ll_offline_mode)
    LinearLayout tvOfflineMode;
    PopPresenter popPresenter;
    ConnectionDetector connectionDetector;

    MusicAdapter musicAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_pop;
    }

    @Override
    protected void init() {
        connectionDetector = new ConnectionDetector(getContext());
        rvPopMusic.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPopMusic.setLayoutManager(layoutManager);

        popPresenter = new PopImp(this);
        popPresenter.loadPopMusic();
    }

    @Override
    public void showPopMusicList(List<MusicModel> musicModelList) {
        musicAdapter = new MusicAdapter(musicModelList);
        rvPopMusic.setAdapter(musicAdapter);
    }


    //swipe to refresh
    private void swipeToRefresh() {
        if (!connectionDetector.isConnected()) {
            tvOfflineMode.setVisibility(View.VISIBLE);

            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                  snackBar("No Internet Connection !");
                    pullToRefresh.setRefreshing(false);
                }
            });

        } else {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    popPresenter.loadPopMusic();
                    tvOfflineMode.setVisibility(View.GONE);
                    pullToRefresh.setRefreshing(false);
                    snackBar("Data Refreshed");
                }
            });

        }

    }


    private void checkpoint() {

        if (!connectionDetector.isConnected()) {
            snackBar("No Internet Connection !");

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        checkpoint();
        swipeToRefresh();

    }


    private void snackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(pullToRefresh, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        snackbar.show();

    }

}