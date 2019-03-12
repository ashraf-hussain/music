package com.project.music.tabFragments.rock.rockView;


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
import com.project.music.tabFragments.rock.rockPresenter.RockImp;
import com.project.music.tabFragments.rock.rockPresenter.RockPresenter;

import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RockFragment extends BaseFragment implements RockFragmentView {

    MusicAdapter musicAdapter;
    @BindView(R.id.rv_rock_fragment)
    RecyclerView rvRockFragment;
    ConnectionDetector connectionDetector;
    @BindView(R.id.ll_offline_mode)
    LinearLayout tvOfflineMode;
    RockPresenter rockPresenter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout pullToRefresh;


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
        connectionDetector = new ConnectionDetector(getContext());

        rvRockFragment.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvRockFragment.setLayoutManager(layoutManager);

        rockPresenter = new RockImp(this);
        rockPresenter.loadRockMusic();


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
                    rockPresenter.loadRockMusic();
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