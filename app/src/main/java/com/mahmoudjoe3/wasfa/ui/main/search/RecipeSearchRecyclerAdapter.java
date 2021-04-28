package com.mahmoudjoe3.wasfa.ui.main.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Recipe;

import java.util.ArrayList;
import java.util.List;

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
        holder.createdDateTextView.setText(MyLogic.getTimeFrom(recipeList.get(position).getPostTime()));
        String desc = recipeList.get(position).getDescription();
        if(desc.length() > 250) {
            desc = desc.substring(0,247);
            desc += "...";
        }
        holder.descriptionTextView.setText(desc);
        holder.topCategoryTextView.setText(recipeList.get(position).getCategories().get(0));
        holder.loveCountTextView.setText(String.valueOf(recipeList.get(position).getNumberOfLike()));
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
        TextView nameTextView, createdDateTextView, descriptionTextView, topCategoryTextView, loveCountTextView, commentCountTextView, imageCountTextView;
        ImageView postImageView;
        View imageBackgroundView;
        public RecipeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textView);
            createdDateTextView = itemView.findViewById(R.id.createdDate_textView);
            descriptionTextView = itemView.findViewById(R.id.description_textView);
            topCategoryTextView = itemView.findViewById(R.id.topCategory_textView);
            loveCountTextView = itemView.findViewById(R.id.loveCount_textView);
            commentCountTextView = itemView.findViewById(R.id.commentCount_textView);
            postImageView = itemView.findViewById(R.id.post_imageView);
            imageCountTextView = itemView.findViewById(R.id.imageCount_textView);
            imageBackgroundView = itemView.findViewById(R.id.imageBackground_view);
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
