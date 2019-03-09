package com.project.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.music.itunesModel.MusicModel;
import com.project.music.tabFragments.RockFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private MusicModel musicModel;
   private List<MusicModel> musicModelList;
    Context context;


    public MusicAdapter(List<MusicModel> musicModelList) {
        this.musicModelList = musicModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_adapter, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        musicModel = musicModelList.get(position);
        try {
            Log.d("ooooo", "onBindViewHolder: ");
            holder.tvBandName.setText(musicModel.getArtistName());
            holder.tvSongTitle.setText(musicModel.getTrackName());
            holder.tvPrice.setText(musicModel.getTrackPrice()+" ");
            Picasso.get().load(musicModel.getArtworkUrl60()).into(holder.ivUserProfileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return musicModelList == null ? 0 : musicModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_profile_list)
        ImageView ivUserProfileList;
        @BindView(R.id.tv_band_name)
        TextView tvBandName;
        @BindView(R.id.tv_song_title)
        TextView tvSongTitle;
        @BindView(R.id.tv_price)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
