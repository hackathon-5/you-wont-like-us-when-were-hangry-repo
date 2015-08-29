package com.r0adkll.hackathon.ui.screens.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Shelter;
import com.r0adkll.hackathon.ui.widget.RatioImageView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.ButterKnife;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class HomeRecyclerAdapter extends BetterRecyclerAdapter<Pet, HomeRecyclerAdapter.PetViewHolder> implements StickyRecyclerHeadersAdapter<HomeRecyclerAdapter.HeaderViewHolder>{

    private Activity mActivity;

    public HomeRecyclerAdapter(Activity activity){
        super();
        mActivity = activity;
    }


    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PetViewHolder(mActivity.getLayoutInflater()
                .inflate(R.layout.item_layout_pet, parent, false));
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int i) {
        super.onBindViewHolder(holder, i);
        Pet pet = getItem(i);

        // Load thumbnail
        Glide.with(mActivity)
                .load(pet.photoUrl)
                .crossFade()
                .into(holder.thumbnail);

    }



    @Override
    public long getHeaderId(int position) {
        if(position > -1) {
            Shelter shelter = getItem(position).shelter;
            return shelter.id;
        }
        return -1;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return new HeaderViewHolder(mActivity.getLayoutInflater()
                .inflate(R.layout.layout_sticky_subheader, viewGroup, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        Shelter shelter = getItem(position).shelter;
        holder.title.setText(shelter.name);
    }

    /**
     * The Header View Holder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = ButterKnife.findById(itemView, R.id.title);

            FontLoader.apply(title, Face.ROBOTO_MEDIUM);
        }
    }

    static class PetViewHolder extends RecyclerView.ViewHolder{

        public RatioImageView thumbnail;

        public PetViewHolder(View itemView) {
            super(itemView);
            thumbnail = (RatioImageView) itemView;
        }
    }

}
