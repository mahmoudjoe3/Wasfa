package com.mahmoudjoe3.wasfaty.ui.main.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudjoe3.wasfaty.R;
import com.mahmoudjoe3.wasfaty.logic.MyLogic;
import com.mahmoudjoe3.wasfaty.pojo.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecipeSearchRecyclerAdapter extends RecyclerView.Adapter<RecipeSearchRecyclerAdapter.RecipeSearchViewHolder> {

    private List<Recipe> recipeList = new ArrayList<>();

    @NonNull
    @Override
    public RecipeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_search_item, parent, false);
        return new RecipeSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSearchViewHolder holder, int position) {
        holder.nameTextView.setText(recipeList.get(position).getUserName());
        holder.createdDateTextView.setText(MyLogic.getTimeFrom(recipeList.get(position).getCreatedDate()));
        String desc = recipeList.get(position).getDescription();
        Picasso.get().load(recipeList.get(position).getImgUrls().get(0)).fit().centerCrop().into(holder.postImageView);
        Picasso.get().load(recipeList.get(position).getUserProfileThumbnail()).fit().centerCrop().into(holder.profile_circleImageView);

        if(desc.length() > 250) {
            desc = desc.substring(0,247);
            desc += "...";
        }
        holder.post_rate_search.setRating((float) (recipeList.get(position).getReviewsRateTotal()/2));
        holder.descriptionTextView.setText(desc);
        holder.topCategoryTextView.setText(recipeList.get(position).getCategories().get(0));
        holder.loveCountTextView.setText(String.valueOf(recipeList.get(position).getLoveCount()));
        holder.commentCountTextView.setText(String.valueOf(recipeList.get(position).getComments().size()));
        int imgSize = recipeList.get(position).getImgUrls().size();
        if(imgSize > 1) {
            holder.imageCountTextView.setText("+" + (imgSize-1));
        } else {
            holder.imageBackgroundView.setVisibility(View.GONE);
            holder.imageCountTextView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(recipeList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public static class RecipeSearchViewHolder extends RecyclerView.ViewHolder {
        RatingBar post_rate_search;
        TextView nameTextView, createdDateTextView, descriptionTextView, topCategoryTextView, loveCountTextView, commentCountTextView, imageCountTextView;
        ImageView postImageView;
        CircleImageView profile_circleImageView;
        View imageBackgroundView;
        public RecipeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            post_rate_search=itemView.findViewById(R.id.post_rate_search);
            nameTextView = itemView.findViewById(R.id.name_textView);
            createdDateTextView = itemView.findViewById(R.id.createdDate_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
            topCategoryTextView = itemView.findViewById(R.id.topCategory_textView);
            loveCountTextView = itemView.findViewById(R.id.loveCount_textView);
            commentCountTextView = itemView.findViewById(R.id.commentCount_textView);
            postImageView = itemView.findViewById(R.id.post_imageView);
            imageCountTextView = itemView.findViewById(R.id.imageCount_textView);
            imageBackgroundView = itemView.findViewById(R.id.imageBackground_view);
            profile_circleImageView=itemView.findViewById(R.id.profile_circleImageView);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener{
        void onClick(Recipe recipe);
    }

}
