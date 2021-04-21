package com.mahmoudjoe3.wasfa.ui.main.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mahmoudjoe3.wasfa.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientStepsAdapter extends RecyclerView.Adapter<IngredientStepsAdapter.IngredientViewHolder> {

    private List<String> List = new ArrayList<>();
    private onItemClickListener mListener;

    public IngredientStepsAdapter() {

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.ingredientTextView.setText(List.get(position));
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public void setList(List<String> list) {
        this.List = list;
    }

    public void removeAt(int position) {
        List.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, List.size());
    }

    public interface onItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        ImageButton deleteImageButton;
        public IngredientViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.ingredient_textView);
            deleteImageButton = itemView.findViewById(R.id.delete_imageButton);

            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });
        }
    }
}
