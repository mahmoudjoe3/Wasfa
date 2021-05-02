package com.mahmoudjoe3.wasfa.ui.main.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Recipe;
import com.mahmoudjoe3.wasfa.prevalent.prevalent;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class profilePostItemAdapter extends RecyclerView.Adapter<profilePostItemAdapter.VH> {
    private List<Recipe> recipeList;
    boolean profileItem;
    public profilePostItemAdapter(boolean profileItem) {
        this.profileItem = profileItem;
        recipeList=new ArrayList<>();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_post_item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Recipe recipe=recipeList.get(position);
        if(profileItem == prevalent.COMMON_ITEM){
            Glide.with(h.post_user_image.getContext()).load(recipe.getUserProfileThumbnail())
                    .into(h.post_user_image);
            h.post_username.setText(recipe.getUserName());
            h.post_user_image.setVisibility(View.VISIBLE);
            h.post_username.setVisibility(View.VISIBLE);
        }
        if(profileItem == prevalent.PROFILE_ITEM){
            h.post_user_image.setVisibility(View.GONE);
            h.post_username.setVisibility(View.GONE);
        }
        Glide.with(h.post_top_image.getContext()).load(recipe.getImgUrls().get(0))
                .into(h.post_top_image);

        //h.post_rate.setText(recipe.getUserName());
        h.post_caption.setText(recipe.getTitle());
        h.post_love_number.setText(""+recipe.getNumberOfLike());
        h.post_comment_number.setText(""+recipe.getComments().size());
        h.post_share_number.setText(""+recipe.getNumberOfShare());
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(recipe);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class VH extends RecyclerView.ViewHolder{
        ImageView post_top_image;
        CircleImageView post_user_image;
        TextView post_username;
        TextView post_rate;
        CardView cardRate;
        TextView post_caption,post_love_number,post_comment_number,post_share_number;
        public VH(@NonNull View itemView) {
            super(itemView);
            post_top_image=itemView.findViewById(R.id.post_top_image);
            post_user_image=itemView.findViewById(R.id.post_user_image);
            post_username=itemView.findViewById(R.id.post_username);
            post_rate=itemView.findViewById(R.id.post_rate);
            post_caption=itemView.findViewById(R.id.post_caption);
            post_love_number=itemView.findViewById(R.id.post_love_number);
            post_comment_number=itemView.findViewById(R.id.post_comment_number);
            post_share_number=itemView.findViewById(R.id.post_share_number);
            cardRate=itemView.findViewById(R.id.cardRate);

        }
    }

    OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(Recipe recipe);
    }
}
