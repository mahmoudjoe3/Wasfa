package com.mahmoudjoe3.wasfa.ui.main.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudjoe3.wasfa.R;
import com.mahmoudjoe3.wasfa.pojo.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleSearchRecyclerAdapter extends RecyclerView.Adapter<PeopleSearchRecyclerAdapter.PeopleSearchViewHolder> {
    private List<User> userList = new ArrayList<>();

    @NonNull
    @Override
    public PeopleSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_search_item, parent, false);
        return new PeopleSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleSearchViewHolder holder, int position) {
        holder.nameTextView.setText(userList.get(position).getName());
        holder.bioTextView.setText(userList.get(position).getBio());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class PeopleSearchViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileCircleImageView;
        TextView nameTextView, bioTextView;
        public PeopleSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            profileCircleImageView = itemView.findViewById(R.id.profile_imageView);
            nameTextView = itemView.findViewById(R.id.name_textView);
            bioTextView = itemView.findViewById(R.id.bio_textView);
        }
    }
}
