package com.project.music.tabFragments.classic.classicView;


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
import com.project.music.tabFragments.classic.classicPresenter.ClassicImp;
import com.project.music.tabFragments.classic.classicPresenter.ClassicPresenter;
import com.project.music.tabFragments.model.MusicModel;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassicFragment extends BaseFragment implements ClassicView {


    @BindView(R.id.rv_classic_music)
    RecyclerView rvClassicMusic;
    MusicAdapter musicAdapter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.ll_offline_mode)
    LinearLayout tvOfflineMode;
    ClassicPresenter classicPresenter;
    ConnectionDetector connectionDetector;

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
        connectionDetector = new ConnectionDetector(getContext());
        rvClassicMusic.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvClassicMusic.setLayoutManager(layoutManager);

        classicPresenter = new ClassicImp(this);
        classicPresenter.loadClassicData();

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
                    classicPresenter.loadClassicData();
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
