package com.mahmoudjoe3.wasfa.ui.main.interaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.logic.MyLogic;
import com.mahmoudjoe3.wasfa.pojo.Interaction;

import java.util.ArrayList;
import java.util.List;

public class InteractionsRecyclerAdapter extends RecyclerView.Adapter<InteractionsRecyclerAdapter.InteractionsViewHolder> {

    private List<Interaction> interactionList = new ArrayList<>();
    private List<Interaction> copyList=new ArrayList<>();
    private onItemClickListener onItemClickListener;

    @NonNull
    @Override
    public InteractionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interaction_search_item, parent, false);
        return new InteractionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionsViewHolder holder, final int position) {
        holder.interactionTextView.setText(interactionList.get(position).getInteraction());
        String postAccount = getPostAccount(interactionList.get(position).getInteraction());
        holder.postAccountTextView.setText(postAccount);
        holder.interactWithTextView.setText(interactionList.get(position).getInteractWith());
        holder.dateTimeTextView.setText(MyLogic.getTimeFrom(interactionList.get(position).getDateTime()));

        Glide.with(holder.profileImageView).load(interactionList.get(position).getImageUrl()).into(holder.profileImageView);

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteClick(position);
            }
        });
    }

    private String getPostAccount(String interaction) {
        String str;
        if(interaction.equals("Loved") || interaction.equals("Shared") || interaction.equals("DisLoved") || interaction.equals("Commented on")) {
            str = "'s post";
        } else {
            str = "'s account";
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return interactionList.size();
    }

    public void setInteractionList(List<Interaction> interactionList) {
        this.interactionList = interactionList;
        copyList = new ArrayList<>(interactionList);
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        interactionList.remove(position);
        copyList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, interactionList.size());
    }

    public Filter getFilter() {
        return copyFilter;
    }

    private Filter copyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Interaction> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(copyList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Interaction interaction : copyList) {
                    if(interaction.getInteraction().toLowerCase().contains(filterPattern) || interaction.getInteractWith().toLowerCase().contains(filterPattern)) {
                        filteredList.add(interaction);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            interactionList.clear();
            interactionList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public interface onItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(InteractionsRecyclerAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class InteractionsViewHolder extends RecyclerView.ViewHolder {
        TextView interactWithTextView, dateTimeTextView, interactionTextView, postAccountTextView;
        ImageView profileImageView;
        ImageButton deleteImageButton;
        public InteractionsViewHolder(@NonNull View itemView) {
            super(itemView);
            interactWithTextView = itemView.findViewById(R.id.interactsWith_textView);
            dateTimeTextView = itemView.findViewById(R.id.date_time_textView);
            interactionTextView = itemView.findViewById(R.id.interaction_textView);
            postAccountTextView = itemView.findViewById(R.id.post_account_textView);
            profileImageView = itemView.findViewById(R.id.profile_imageView);
            deleteImageButton = itemView.findViewById(R.id.delete_imageButton);

        }
    }
}
