package com.project.music.tabFragments.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.music.R;
import com.project.music.common.AppConstant;
import com.project.music.musicDetail.MusicPlayerDetailActivity;
import com.project.music.tabFragments.model.MusicModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<MusicModel> musicModelList;
    private Context context;


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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MusicModel musicModel = musicModelList.get(position);
        try {
            holder.tvBandName.setText(musicModel.getArtistName());
            holder.tvSongTitle.setText(musicModel.getTrackName());
            holder.tvPrice.setText(musicModel.getTrackPrice() + " " + "USD");
            Picasso.get().load(musicModel.getArtworkUrl60()).into(holder.ivUserProfileList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.cvMusicPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MusicPlayerDetailActivity.class);
                intent.putExtra(AppConstant.MUSIC_DETAIL_DATA, musicModel);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return musicModelList == null ? 0 : musicModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_profile_list)
        ImageView ivUserProfileList;
        @BindView(R.id.cv_music_adapter)
        CardView cvMusicPlayer;
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
