package com.r0adkll.hackathon.ui.screens.home.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.hannesdorfmann.adapterdelegates.AdapterDelegatesManager;
import com.r0adkll.hackathon.R;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Shelter;
import com.r0adkll.hackathon.ui.widget.RatioImageView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class HomeRecyclerAdapter extends BetterRecyclerAdapter<HomeItem, RecyclerView.ViewHolder>{

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PET = 1;

    private Activity mActivity;

    public HomeRecyclerAdapter(Activity activity){
        super();
        mActivity = activity;
    }

    public GridLayoutManager.SpanSizeLookup getSpanLookup(){
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                HomeItem item = getItem(position);
                return item.type == TYPE_HEADER ? 3 : 1;
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_PET) {
            return new PetViewHolder(mActivity.getLayoutInflater()
                    .inflate(R.layout.item_layout_pet, parent, false));
        }else{
            return new HeaderViewHolder(mActivity.getLayoutInflater()
                    .inflate(R.layout.layout_sticky_subheader, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        int type = getItemViewType(i);
        if(type == TYPE_PET) {
            super.onBindViewHolder(holder, i);
            PetViewHolder petHolder = (PetViewHolder) holder;
            Pet pet = getItem(i).item;

            // Load thumbnail
            Glide.with(mActivity)
                    .load(pet.photoUrl)
                    .crossFade()
                    .into(petHolder.thumbnail);
        }else{
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            Shelter shelter = getItem(i).header;
            headerViewHolder.title.setText(shelter.name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        HomeItem item = getItem(position);
        return item.type;
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

        @Bind(R.id.thumbnail)
        public RatioImageView thumbnail;

        public PetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
